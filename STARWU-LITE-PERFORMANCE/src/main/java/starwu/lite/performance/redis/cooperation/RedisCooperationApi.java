package starwu.lite.performance.redis.cooperation;

public interface RedisCooperationApi<ID,RESULT> {

    public String getRedisKey(ID identifier);

    public RESULT getFromDatabase(ID identifier);

    public boolean isNeedTryLock();

    public int getReTryCount();

    public long getSleepTime();

    public RESULT convertToResult(Object object);


    public RESULT getTtryLockFailResult(ID identifier);

    public int redisTimeOut();
}
