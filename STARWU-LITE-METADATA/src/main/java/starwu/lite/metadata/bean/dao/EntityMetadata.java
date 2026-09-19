package starwu.lite.metadata.bean.dao;

import lombok.Data;
import starwu.lite.metadata.ann.orm.Cache;
import starwu.lite.metadata.config.dao.DaoConfig;
import starwu.lite.metadata.entity.dao.base.BaseEntity;
import starwu.lite.util.SpringUtil;
import starwu.lite.util.StringUtil;

@Data
public class EntityMetadata {

    private static DaoConfig daoConfig = SpringUtil.getBean(DaoConfig.class);

    private static final String cachePrefix = "STARWU-LITE-CACHE:";

    private Class<? extends BaseEntity> entityCls; // 实体类的class类型

    private boolean isNeedCache; // 是否需要缓存

    private Cache cache; // 缓存注解对象

    private String clsName; // 实体类名

    private int period;

    public EntityMetadata(Class<? extends BaseEntity> entityCls, Cache cache) {
        super();
        this.entityCls = entityCls;
        this.cache = cache;
        this.clsName = entityCls.getSimpleName();
        if (cache != null) {
            this.isNeedCache = true;
            int period = cache.period();
            if (period <= 0) {
                period = daoConfig.getCachePeriod();
            }
            this.period = period;
        } else {
            this.isNeedCache = false;
        }
    }

    public String genEntityCacheKey(Long id) {
        return StringUtil.appendWithUnSafe(cachePrefix,this.getClsName(), ":", id);
    }
}
