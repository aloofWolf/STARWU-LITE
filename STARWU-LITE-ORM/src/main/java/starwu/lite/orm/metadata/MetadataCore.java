package starwu.lite.orm.metadata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.ann.orm.Cache;
import starwu.lite.metadata.bean.orm.EntityMetadata;
import starwu.lite.metadata.entity.orm.base.BaseEntity;
import starwu.lite.orm.dao.BaseDao;
import starwu.lite.orm.redis.RedisApi;
import starwu.lite.orm.redis.RedisDefaultImpl;
import starwu.lite.util.GenericityUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class MetadataCore {

    private Map<Class<? extends BaseEntity>, EntityMetadata> entityToetadataMap = new ConcurrentHashMap<Class<? extends BaseEntity>, EntityMetadata>();

    private Map<Class<? extends BaseDao>, EntityMetadata> daoToMetadataMap = new ConcurrentHashMap<>();

    private Map<BaseDao, RedisApi> daoToRedisApiMap = new ConcurrentHashMap<>();

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

    public RedisApi getRedisApiByDaoCls(BaseDao baseDao) {
        RedisApi redisApi = daoToRedisApiMap.get(baseDao);
        if (redisApi == null) {
            redisApi = genRedisApiByDaoCls(baseDao);
        }
        return redisApi;
    }


    private RedisApi genRedisApiByDaoCls(BaseDao baseDao) {
        synchronized (baseDao) {
            RedisApi redisApi = daoToRedisApiMap.get(baseDao);
            if (redisApi == null) {
                redisApi = new RedisDefaultImpl(baseDao);
                daoToRedisApiMap.put(baseDao, redisApi);
            }
            return redisApi;
        }
    }
}
