package starwu.lite.orm.redis;

import starwu.lite.metadata.bean.orm.EntityMetadata;

public interface RedisApi<ID,RESULT> {

    public String getRedisKey(ID identifier);

    public RESULT getFromDatabase(ID identifier);

    public boolean isNeedTryLock();

    public int getReTryCount();

    public long getSleepTime();

    public RESULT convertToResult(Object object);


    public RESULT getTtryLockFailResult(ID identifier);

    public int redisTimeOut();
}
