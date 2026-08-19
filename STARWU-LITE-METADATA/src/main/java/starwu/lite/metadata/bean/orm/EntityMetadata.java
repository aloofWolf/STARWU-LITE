package starwu.lite.metadata.bean.orm;

import lombok.Data;
import starwu.lite.metadata.ann.orm.Cache;
import starwu.lite.metadata.entity.orm.base.BaseEntity;

@Data
public class EntityMetadata {

    private Class<? extends BaseEntity> entityCls; // 实体类的class类型

    private boolean isNeedCache; // 是否需要缓存

    private Cache cache; // 缓存注解对象

    private String clsName; // 实体类名

    public EntityMetadata(Class<? extends BaseEntity> entityCls, Cache cache) {
        super();
        this.entityCls = entityCls;
        this.cache = cache;
        this.clsName = entityCls.getSimpleName();
        if (cache != null) {
            this.isNeedCache = true;
        } else {
            this.isNeedCache = false;
        }
    }
}
