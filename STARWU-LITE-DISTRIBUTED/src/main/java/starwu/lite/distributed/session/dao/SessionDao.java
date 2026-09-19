package starwu.lite.distributed.session.dao;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.util.StringUtil;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionDao {

	private final RedisTemplate<String, String> redisTemplate;

	private static final String userTokenKeyPrefix = "STARTWU-LITE-SESSION-USERTOKEN:";
	private static final String tokenKeyPrefix = "STARTWU-LITE-SESSION-TOKEN:";
	private static final String userOnlineKey= "STARTWU-LITE-SESSION-ONLINE";

	public <T> void saveSession(Long userId,Integer deviceType,String token, UserSession<T> session,Integer sessionPeriod) {
		String userTokenKey = getUserTokenKey(userId,deviceType);
		String tokenKey = getTokenKey(token);
		redisTemplate.opsForValue().set(tokenKey, JSONObject.toJSONString(session), sessionPeriod,
				TimeUnit.MINUTES);
		redisTemplate.opsForValue().set(userTokenKey, token, sessionPeriod,
				TimeUnit.MINUTES);
		redisTemplate.opsForZSet().add(userOnlineKey,String.valueOf(userId),(double)System.currentTimeMillis());
	}

	@SuppressWarnings("unchecked")
	public <T> UserSession<T> getSession(String token) {
		String tokenKey = getTokenKey(token);
		String redisValue = redisTemplate.opsForValue().get(tokenKey);
		if (redisValue == null) {
			return null;
		}
		return JSONObject.parseObject(redisValue, UserSession.class);
	}

	public void resetSessionPeriod(Long userId,Integer deviceType,String token,Integer sessionPeriod) {
		String userTokenKey = getUserTokenKey(userId,deviceType);
		String tokenKey = getTokenKey(token);
		redisTemplate.expire(tokenKey, sessionPeriod, TimeUnit.MINUTES);
		redisTemplate.expire(userTokenKey, sessionPeriod, TimeUnit.MINUTES);
		redisTemplate.opsForZSet().add(userOnlineKey,String.valueOf(userId),System.currentTimeMillis());
	}

	public String getToken(Long userId,Integer deviceType){
		String userTokenKey = getUserTokenKey(userId,deviceType);
		String token = redisTemplate.opsForValue().get(userTokenKey);
		return token;
	}

	public void deleteSession(Long userId,Integer deviceType, String token) {
		String userTokenKey = getUserTokenKey(userId,deviceType);
		String tokenKey = getTokenKey(token);
		redisTemplate.delete(tokenKey);
		redisTemplate.delete(userTokenKey);
		redisTemplate.opsForZSet().remove(userOnlineKey,String.valueOf(userId));
	}

	public Long userOnlineCount(Date startDate, Date endDate) {
		return redisTemplate.opsForZSet().count(userOnlineKey,startDate.getTime(),endDate.getTime());
	}


	private String getUserTokenKey(Long userId,Integer deviceType){
		String userTokenKey = null;
		if(deviceType != null){
			userTokenKey = StringUtil.appendWithUnSafe(userTokenKeyPrefix,String.valueOf(userId),":",deviceType);
		}else{
			userTokenKey = StringUtil.appendWithUnSafe(userTokenKeyPrefix,String.valueOf(userId));
		}
		return userTokenKey;
	}

	private String getTokenKey(String token){
		String tokenKey = StringUtil.appendWithUnSafe(tokenKeyPrefix,token);
		return tokenKey;
	}

}
