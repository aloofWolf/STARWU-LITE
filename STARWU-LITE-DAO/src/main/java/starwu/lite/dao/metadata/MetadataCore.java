package starwu.lite.dao.metadata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import starwu.lite.dao.redis.cooperation.RedisCooperationApi;
import starwu.lite.dao.redis.cooperation.RedisCooperationDefaultImpl;
import starwu.lite.metadata.ann.orm.Cache;
import starwu.lite.metadata.bean.dao.EntityMetadata;
import starwu.lite.metadata.entity.dao.base.BaseEntity;
import starwu.lite.dao.core.BaseDao;
import starwu.lite.util.GenericityUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class MetadataCore {

    private Map<Class<? extends BaseEntity>, EntityMetadata> entityToetadataMap = new ConcurrentHashMap<Class<? extends BaseEntity>, EntityMetadata>();

    private Map<Class<? extends BaseDao>, EntityMetadata> daoToMetadataMap = new ConcurrentHashMap<>();

    private Map<BaseDao, RedisCooperationApi> daoToRedisApiMap = new ConcurrentHashMap<>();

    public EntityMetadata getEntityMetadataByDaoCls(Class<? extends BaseDao> daoCls) {
        EntityMetadata metadata = daoToMetadataMap.get(daoCls);
        if (metadata == null) {
            metadata = genEntityMetadataByDaoCls(daoCls);
        }
        return metadata;
    }


    private EntityMetadata genEntityMetadataByDaoCls(Class<? extends BaseDao> daoCls) {
        synchronized (daoCls) {
            EntityMetadata metadata = daoToMetadataMap.get(daoCls);
            if (metadata == null) {
                Class<? extends BaseEntity> entityCls = GenericityUtil.getObjSuperInterGenerCls(daoCls);
                metadata = getEntityMetadata(entityCls);
                daoToMetadataMap.put(daoCls, metadata);
            }
            return metadata;
        }
    }

    public EntityMetadata getEntityMetadata(Class<? extends BaseEntity> entityCls) {
        EntityMetadata metadata = entityToetadataMap.get(entityCls);
        if (metadata == null) {
            metadata = genEntityMetadata(entityCls);
        }
        return metadata;
    }

    private EntityMetadata genEntityMetadata(Class<? extends BaseEntity> entityCls) {
        synchronized (entityCls) {
            EntityMetadata metadata = entityToetadataMap.get(entityCls);
            if (metadata == null) {
                Cache cache = entityCls.getDeclaredAnnotation(Cache.class);
                metadata = new EntityMetadata(entityCls, cache);
                entityToetadataMap.put(entityCls, metadata);
            }
            return metadata;
        }
    }

    public RedisCooperationApi getRedisApiByDaoCls(BaseDao baseDao) {
        RedisCooperationApi redisApi = daoToRedisApiMap.get(baseDao);
        if (redisApi == null) {
            redisApi = genRedisApiByDaoCls(baseDao);
        }
        return redisApi;
    }


    private RedisCooperationApi genRedisApiByDaoCls(BaseDao baseDao) {
        synchronized (baseDao) {
            RedisCooperationApi redisApi = daoToRedisApiMap.get(baseDao);
            if (redisApi == null) {
                redisApi = new RedisCooperationDefaultImpl(baseDao);
                daoToRedisApiMap.put(baseDao, redisApi);
            }
            return redisApi;
        }
    }
}
