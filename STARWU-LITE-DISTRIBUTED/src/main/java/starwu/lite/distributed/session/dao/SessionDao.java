package starwu.lite.distributed.session.dao;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.config.distributed.session.SessionConfig;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionDao {

	private final RedisTemplate<String, String> redisTemplate;

	public <T> void saveSession(String token, UserSession<T> session,Integer sessionPeriod) {
		long begin = System.currentTimeMillis();
		redisTemplate.opsForHash().put("userOnline", String.valueOf(session.getUserId()), token);
		long end = System.currentTimeMillis();
		long time = end - begin;
		log.info("saveSessionUserOnline-redis耗时："+time);
		begin = System.currentTimeMillis();
		redisTemplate.opsForValue().set(token, JSONObject.toJSONString(session), sessionPeriod,
				TimeUnit.MINUTES);
	    end = System.currentTimeMillis();
		time = end - begin;
		log.info("createSessionToken-redis耗时："+time);
	}

	@SuppressWarnings("unchecked")
	public <T> UserSession<T> getSession(String token) {
		long begin = System.currentTimeMillis();
		String redisValue = redisTemplate.opsForValue().get(token);
		long end = System.currentTimeMillis();
		long time = end - begin;
		log.info("getSession-redis耗时："+time);
		
		if (redisValue == null) {
			return null;
		}
		return JSONObject.parseObject(redisValue, UserSession.class);
	}

	public void resetSessionPeriod(String token,Integer sessionPeriod) {
		long begin = System.currentTimeMillis();
		redisTemplate.expire(token, sessionPeriod, TimeUnit.MINUTES);
		long end = System.currentTimeMillis();
		long time = end - begin;
		log.info("resetSessionPeriod-redis耗时："+time);
		
	}

	public void deleteSession(Long userId, String token) {
		redisTemplate.delete(token);
		redisTemplate.opsForHash().delete("userOnline", String.valueOf(userId));
	}

	public Integer userOnlineCount() {
		return (int) redisTemplate.opsForHash().size("userOnline").longValue();
	}

}
