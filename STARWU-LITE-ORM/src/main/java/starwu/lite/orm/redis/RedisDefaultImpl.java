package starwu.lite.orm.redis;

import com.alibaba.fastjson.JSONObject;
import starwu.lite.metadata.bean.orm.EntityMetadata;
import starwu.lite.metadata.entity.orm.base.BaseEntity;
import starwu.lite.orm.dao.BaseDao;
import starwu.lite.orm.metadata.MetadataCore;
import starwu.lite.util.SpringUtil;

public class RedisDefaultImpl implements RedisApi<Long,BaseEntity>{

    private BaseDao<? extends BaseEntity> baseDao;
    private EntityMetadata metadata;
    private static MetadataCore metadataCore;

    public RedisDefaultImpl(BaseDao<? extends BaseEntity> baseDao) {
        this.baseDao = baseDao;
        if(metadataCore==null){
            metadataCore = SpringUtil.getBean(MetadataCore.class);
        }
        metadata = metadataCore.getEntityMetadataByDaoCls((Class<? extends BaseDao>) baseDao.getClass().getInterfaces()[0]);
    }

    @Override
    public String getRedisKey(Long identifier) {
        return metadata.genEntityCacheKey(identifier);
    }

    @Override
    public BaseEntity getFromDatabase(Long identifier) {
        return baseDao.selectById(identifier);
    }

    @Override
    public boolean isNeedTryLock() {
        return false;
    }

    @Override
    public int getReTryCount() {
        return 0;
    }

    @Override
    public long getSleepTime() {
        return 0;
    }

    @Override
    public BaseEntity convertToResult(Object object) {
        return (BaseEntity) JSONObject.parseObject((String) object, metadata.getEntityCls());
    }

    @Override
    public BaseEntity getTtryLockFailResult(Long identifier) {
        return getFromDatabase(identifier);
    }

    @Override
    public int redisTimeOut() {
        return metadata.getPeriod();
    }
}
