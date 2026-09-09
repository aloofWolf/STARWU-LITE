package starwu.lite.dao.redis.cooperation;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import starwu.lite.util.StringUtil;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCooperationCore {

    private final RedisTemplate<String,String> stringRedisTemplate;

    private final RedissonClient redssion;

    public <ID,RESULT> RESULT getFromRedis(RedisCooperationApi<ID,RESULT> api, ID id){
        String redisKey = api.getRedisKey(id);
        String redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        if("".equals(redisValue)) {
            return null;
        }
        if (redisValue != null){
            return api.convertToResult(redisValue);
        }

        if(!api.isNeedTryLock()){
            RESULT resp = api.getFromDatabase(id);
            writeBackToCache(api,redisKey,resp);
            return resp;
        }

        return getFromRedisWithLock(api,id,1);
    }

    @SneakyThrows
    private <ID,RESULT> RESULT getFromRedisWithLock(RedisCooperationApi<ID,RESULT> api, ID id, int count) {
        if (count > api.getReTryCount()) {
            return api.getTtryLockFailResult(id); // 递归6次，返回null
        }

        String redisKey = api.getRedisKey(id);
        String redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        if("".equals(redisValue)) {
            return null;
        }
        if (redisValue != null){
            return api.convertToResult(redisValue);
        }


        String lockKey = StringUtil.appendWithUnSafe(redisKey,"-LOCK");
        RLock lock = redssion.getLock(lockKey);
        boolean flag = lock.tryLock();
        if (!flag) {
            Thread.sleep(api.getSleepTime());
            return getFromRedisWithLock(api, id,count + 1);
        }
        RESULT resp = api.getFromDatabase(id);
        writeBackToCache(api,redisKey,resp);
        lock.unlock();// 释放锁
        return resp;

    }

    private <ID,RESULT>  void writeBackToCache(RedisCooperationApi<ID,RESULT> api, String redisKey, RESULT resp) {
        if (resp != null) {
            stringRedisTemplate.opsForValue().set(redisKey, JSONObject.toJSONString(resp),
                    api.redisTimeOut(), TimeUnit.MINUTES);
        } else {
            stringRedisTemplate.opsForValue().set(redisKey, "", api.redisTimeOut(), TimeUnit.MINUTES);
        }
    }
}
