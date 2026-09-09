package starwu.lite.dao.core;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import starwu.lite.dao.redis.cooperation.RedisCooperationApi;
import starwu.lite.dao.redis.cooperation.RedisCooperationCore;
import starwu.lite.metadata.bean.org.page.BasePageRequest;
import starwu.lite.metadata.bean.org.page.BasePageResponse;
import starwu.lite.metadata.bean.orm.EntityMetadata;
import starwu.lite.metadata.entity.orm.base.BaseEntity;
import starwu.lite.dao.metadata.MetadataCore;
import starwu.lite.util.SpringUtil;

public interface BaseDao<T extends BaseEntity> extends BaseMapper<T>{

    default T getById(Long id) {
        MetadataCore metadataCore = SpringUtil.getBean(MetadataCore.class);
        EntityMetadata metadata = metadataCore.getEntityMetadataByDaoCls((Class<? extends BaseDao>) this.getClass().getInterfaces()[0]);
        if(metadata.isNeedCache()){
            RedisCooperationApi<Long,T> redisApi = metadataCore.getRedisApiByDaoCls(this);
            RedisCooperationCore redis = SpringUtil.getBean(RedisCooperationCore.class);
            return redis.getFromRedis(redisApi,id);
        }
        return this.selectById(id);
    }

    default BasePageResponse<T> getPage(BasePageRequest basePageRequest, QueryWrapper<T> wrapper) {
        IPage<T> page = new Page<>(basePageRequest.getPageNum(), basePageRequest.getSizeNum());
        page = this.selectPage(page, wrapper);
        return new BasePageResponse<T>(page);
    }
}
