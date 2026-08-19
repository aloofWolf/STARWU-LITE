package starwu.lite.orm.dao;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.bean.orm.EntityMetadata;
import starwu.lite.metadata.entity.orm.base.BaseEntity;
import starwu.lite.util.GenericityUtil;
import starwu.lite.orm.util.entityMetadata.EntityMetadataUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class BaseDaoExt {

    /*private static BaseDaoExt instance;

    protected static BaseDaoExt get() {
        return instance;
    }

    {
        BaseDaoExt.instance = this;
    }*/

    @Autowired
    protected RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    private RedissonClient redssion;

    @Autowired
    private EntityMetadataUtil entityMetadataUtil;

    @SuppressWarnings("rawtypes")
    private static Map<Class<? extends BaseDao>, EntityMetadata> daoToMetadataMap = new ConcurrentHashMap<>();

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected BaseEntity getById(BaseDao dao, Long id) {
        Class<? extends BaseDao> daoCls = (Class<? extends BaseDao>) dao.getClass().getInterfaces()[0];
        EntityMetadata metadata = getEntityMetadataByDaoCls(daoCls);
        boolean isNeedCache = metadata.isNeedCache();
        if (isNeedCache) {
            return getByCache(metadata, dao, id, 1);
        } else {

            return getByDataBase(dao, id);
        }

    }

    @SneakyThrows
    @SuppressWarnings({"rawtypes", "unchecked"})
    private BaseEntity getByCache(EntityMetadata metadata, BaseDao dao, Long id, int count) {
        if (count >= 5) {
            return null; // 递归6次，返回null
        }

        String cacheValue = getByCache(metadata, id); // 从缓存中查询，从缓存中查询回来的可能是空串，getByCache方法中会对空串做处理
        if ("".equals(cacheValue)) {
            return null;
        } else if (cacheValue != null) {
            return JSONObject.parseObject(cacheValue, metadata.getEntityCls());
        }
        RLock lock = getLock(metadata, id);

        boolean flag = tryLock(lock); // 获取锁
        if (!flag) {  // 竞争锁失败，睡眠50毫秒，进入下一次递归
            Thread.sleep(50);
            return getByCache(metadata, dao, id, count + 1);
        }
        BaseEntity entity = getByDataBase(dao, id); // 查询数据库
        writeBackToCache(metadata, entity, id); // 回写至缓存，如果数据库中没有查到数据，则在缓存中回写空串
        unLock(lock); // 释放锁
        return entity;

    }


    @SuppressWarnings("rawtypes")
    protected EntityMetadata getEntityMetadataByDaoCls(Class<? extends BaseDao> daoCls) {
        EntityMetadata metadata = daoToMetadataMap.get(daoCls);
        if (metadata == null) {
            metadata = genEntityMetadataByDaoCls(daoCls);
        }
        return metadata;
    }


    @SuppressWarnings("rawtypes")
    private EntityMetadata genEntityMetadataByDaoCls(Class<? extends BaseDao> daoCls) {
        synchronized (daoCls) {
            EntityMetadata metadata = daoToMetadataMap.get(daoCls);
            if (metadata == null) {
                Class<? extends BaseEntity> entityCls = GenericityUtil.getObjSuperInterGenerCls(daoCls);
                metadata = entityMetadataUtil.getEntityMetadata(entityCls);
                daoToMetadataMap.put(daoCls, metadata);
            }
            return metadata;
        }
    }

    private String getByCache(EntityMetadata metadata, Long id) {
        String cacheKey = entityMetadataUtil.genEntityCacheKey(metadata,id);
        String result = stringRedisTemplate.opsForValue().get(cacheKey);
        return result;

    }


    private BaseEntity getByDataBase(BaseDao<? extends BaseEntity> dao, Long id) {
        BaseEntity entity = dao.selectById(id);
        return entity;
    }

    private void writeBackToCache(EntityMetadata metadata, BaseEntity entity, Long id) {
        if (metadata.isNeedCache()) {
            String cacheKey;
            if (entity != null) {
                cacheKey = entityMetadataUtil.genEntityCacheKey(metadata,entity.getId());
                long begin = System.currentTimeMillis();
                stringRedisTemplate.opsForValue().set(cacheKey, JSONObject.toJSONString(entity),
                        entityMetadataUtil.genEntityCacheTimeout(metadata), TimeUnit.MINUTES);
                long end = System.currentTimeMillis();
                long time = end - begin;
                log.info("writeBackToCache-redis耗时：" + time);
            } else {
                cacheKey = entityMetadataUtil.genEntityCacheKey(metadata,id);
                stringRedisTemplate.opsForValue().set(cacheKey, "", entityMetadataUtil.genEntityCacheTimeout(metadata), TimeUnit.MINUTES);
            }
        }
    }


    protected void unLock(RLock lock) {
        lock.unlock();
    }

    protected RLock getLock(EntityMetadata metadata, Long id) {
        String cacheKeyLock = entityMetadataUtil.genEntityCacheLockKey(metadata,id);
        RLock lock = redssion.getLock(cacheKeyLock);
        return lock;
    }


    @SneakyThrows
    protected boolean tryLock(RLock lock) {
        return lock.tryLock(200, TimeUnit.MILLISECONDS);
    }

}
