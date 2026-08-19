package starwu.lite.distributed.session.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.config.distributed.session.SessionExcludeUrlConfig;
import starwu.lite.metadata.constant.plus.threadLocal.ThreadLocalKey;
import starwu.lite.metadata.exception.SessionTimeOutException;
import starwu.lite.distributed.session.dao.SessionDao;
import starwu.lite.plus.snowflake.SnowFlakePlus;
import starwu.lite.plus.threadLocal.ThreadLocalPlus;
import starwu.lite.metadata.config.distributed.session.SessionConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionCore {

	private final SessionDao dao;
	private final SessionConfig sessionConfig;


	public <T> String createSession(Long userId, String userName, T obj, HttpServletResponse response) {
		String token = SnowFlakePlus.getSerialNumber("SESSION-");
		UserSession<T> session = new UserSession<T>(userId, userName, obj);
		long begin = System.currentTimeMillis();
		dao.saveSession(token, session,sessionConfig.getSessionPeriod());
		long end = System.currentTimeMillis();
		long time = end - begin;
		log.info("createSession-redis耗时："+time);
		response.setHeader("token", token);
		return token;
	}

	public <T> UserSession<T> validateSession(HttpServletRequest request) {
		String token = request.getHeader("token");
		if (token == null) {
			throw new SessionTimeOutException("请重新登录");
		}
		UserSession<T> session = dao.getSession(token);
		if (session == null) {
			throw new SessionTimeOutException("请重新登录");
		}
		dao.resetSessionPeriod(token,sessionConfig.getSessionPeriod());
		ThreadLocalPlus.put(ThreadLocalKey.SESSION_KEY, session);
		return session;
	}

	public <T> void deleteSession(HttpServletResponse response) {
		String token = response.getHeader("token");
		UserSession<T> session = dao.getSession(token);
		Long userId = null;
		if (session != null) {
			userId = session.getUserId();
		}
		dao.deleteSession(userId, token);
	}

	public List<String> getExcexcludeUrls(){
		return sessionConfig.getAllExcludeUrls();
	}


	public Integer getUserOnlineCount(HttpServletResponse response) {
		return dao.userOnlineCount();
	}

}
