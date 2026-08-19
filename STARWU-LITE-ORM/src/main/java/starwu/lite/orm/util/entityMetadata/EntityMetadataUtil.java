package starwu.lite.orm.util.entityMetadata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.ann.orm.Cache;
import starwu.lite.metadata.bean.orm.EntityMetadata;
import starwu.lite.metadata.entity.orm.base.BaseEntity;
import starwu.lite.util.StringUtil;
import starwu.lite.metadata.config.orm.OrmConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class EntityMetadataUtil {

    private final OrmConfig ormConfig;

    private  Map<Class<? extends BaseEntity>, EntityMetadata> metadatas = new ConcurrentHashMap<Class<? extends BaseEntity>, EntityMetadata>();

    public EntityMetadata getEntityMetadata(Class<? extends BaseEntity> entityCls) {
        EntityMetadata metadata = metadatas.get(entityCls);
        if (metadata == null) {
            metadata = genEntityMetadata(entityCls);
        }
        return metadata;
    }

    private EntityMetadata genEntityMetadata(Class<? extends BaseEntity> entityCls) {
        synchronized (entityCls) {
            EntityMetadata metadata = metadatas.get(entityCls);
            if (metadata == null) {
                Cache cache = entityCls.getDeclaredAnnotation(Cache.class);
                metadata = new EntityMetadata(entityCls, cache);
                metadatas.put(entityCls, metadata);
            }
            return metadata;
        }
    }

    public String genEntityCacheKey(EntityMetadata entityMetadata,Long id) {
        String cacheKey = StringUtil.appendWithUnSafe(entityMetadata.getClsName(), "-", id);
        return cacheKey;
    }

    public String genEntityCacheLockKey(EntityMetadata entityMetadata,Long id) {
        String cacheKeyLock = StringUtil.appendWithUnSafe(entityMetadata.getClsName(), "-", id, "-lock");
        return cacheKeyLock;
    }

    public int genEntityCacheTimeout(EntityMetadata entityMetadata) {
        int period = entityMetadata.getCache().period();
        if (period <= 0) {
            period = ormConfig.getCachePeriod();
        }
        return period;
    }

}
