package starwu.lite.dao.redis.dataConsistency.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import starwu.lite.dao.redis.dataConsistency.api.CanalRedisHandleApi;
import starwu.lite.metadata.ann.orm.Cache;
import starwu.lite.metadata.bean.canal.CanalDataBean;
import starwu.lite.metadata.bean.orm.EntityMetadata;
import starwu.lite.metadata.entity.orm.base.BaseEntity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@Component
@RequiredArgsConstructor
public class CanalRedisDefaultHandleImpl implements CanalRedisHandleApi {

    private final RedisTemplate<String, String> redis;

    @Override
    public boolean match(String key) {
        return false;
    }

    /**
     * tableEntitys是否已经被初始化
     * 本来可以根据tableEntitys是否为null来判断是否被初始化，但是在初始化时，需要对tableEntitys加锁
     * 如果tableEntitys为null的话，无法加锁，所以新增此变量来判断tableEntitys是否被初始化
     */
    private static boolean isInitTableEntitys = false;

    private static Map<String, EntityMetadata> tableEntitys = new HashMap<String, EntityMetadata>();

    protected boolean isHotSpotData(JSONObject afterJson) {
        return false;
    }

    protected void insertOtherHandle(JSONObject afterJson) {
    }

    protected void updateOtherHandle(JSONObject afterJson) {
    }

    protected void deleteOtherHandle(JSONObject afterJson) {
    }


    @Override
    public void insert(CanalDataBean canalDataBean) {
        if (!isNeedProcessCache(canalDataBean)) {
            return;
        }
        EntityMetadata metadata = tableEntitys.get(canalDataBean.getTableName());
        JSONObject afterJson = canalDataBean.getAfer();
        Long id = afterJson.getLong("id");
        if (isHotSpotData(afterJson)) {
            save(metadata, afterJson, id);

        } else {
            delete(metadata, id);
        }
        insertOtherHandle(afterJson);
    }

    @Override
    public void update(CanalDataBean canalDataBean) {
        if (!isNeedProcessCache(canalDataBean)) {
            return;
        }
        EntityMetadata metadata = tableEntitys.get(canalDataBean.getTableName());
        JSONObject afterJson = canalDataBean.getAfer();
        Long id = afterJson.getLong("id");
        if (isHotSpotData(afterJson)) {
            save(metadata, afterJson, id);
        } else {
            delete(metadata, id);
        }

    }

    @Override
    public void delete(CanalDataBean canalDataBean) {
        if (!isNeedProcessCache(canalDataBean)) {
            return;
        }
        EntityMetadata metadata = tableEntitys.get(canalDataBean.getTableName());
        JSONObject afterJson = canalDataBean.getAfer();
        Long id = afterJson.getLong("id");
        delete(metadata, id);
        deleteOtherHandle(afterJson);
    }

    protected boolean isNeedProcessCache(CanalDataBean canalDataBean) {

        if (!isInitTableEntitys) {
            synchronized (tableEntitys) {
                if (!isInitTableEntitys) {
                    initTableEntitys();
                }
            }
        }

        String tableName = canalDataBean.getTableName();
        EntityMetadata metadata = tableEntitys.get(tableName);
        if (metadata == null) {
            return false;
        }
        if (metadata.isNeedCache()) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    private void initTableEntitys() {
        Class<?> clazz = TableInfoHelper.class;
        Field field = clazz.getDeclaredField("TABLE_INFO_CACHE");
        field.setAccessible(true);
        Map<Class<?>, TableInfo> tables = (Map<Class<?>, TableInfo>) field.get(clazz);
        field.setAccessible(false);
        Iterator<Entry<Class<?>, TableInfo>> entries = tables.entrySet().iterator();
        while (entries.hasNext()) {

            Entry<Class<?>, TableInfo> entry = entries.next();
            parseEntry(entry);
        }
    }

    @SuppressWarnings("unchecked")
    private void parseEntry(Entry<Class<?>, TableInfo> entry) {
        try {
            Class<? extends BaseEntity> entityCls = (Class<? extends BaseEntity>) entry.getKey();
            String tableName = entry.getValue().getTableName().toLowerCase();
            Cache cache = entityCls.getDeclaredAnnotation(Cache.class);
            EntityMetadata metadata = new EntityMetadata(entityCls, cache);
            tableEntitys.put(tableName, metadata);
        } catch (Exception e) {

        }
    }

    protected <ENTITY extends BaseEntity> void save(EntityMetadata metadata, JSONObject afterJson, Long id) {
        String key = metadata.genEntityCacheKey(id);
        ENTITY entity = parseJsonToEntity(metadata, afterJson);
        long timeout = metadata.getPeriod();
        redis.opsForValue().set(key, JSONObject.toJSONString(entity), timeout);
    }

    protected <ENTITY extends BaseEntity> void delete(EntityMetadata metadata, Long id) {
        String key = metadata.genEntityCacheKey(id);
        redis.delete(key);
    }

    @SuppressWarnings("unchecked")
    public <ENTITY extends BaseEntity> ENTITY parseJsonToEntity(EntityMetadata metadata, JSONObject afterJson) {
        return (ENTITY) JSONObject.toJavaObject(afterJson, metadata.getEntityCls());
    }
}
