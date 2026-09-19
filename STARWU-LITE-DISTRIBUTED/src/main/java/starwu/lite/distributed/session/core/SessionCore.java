package starwu.lite.distributed.session.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.constant.plus.threadLocal.ThreadLocalKey;
import starwu.lite.metadata.enums.distributed.session.LoginKickOutType;
import starwu.lite.metadata.exception.SessionTimeOutException;
import starwu.lite.distributed.session.dao.SessionDao;
import starwu.lite.manage.threadLocal.ThreadLocalCore;
import starwu.lite.metadata.config.distributed.session.SessionConfig;
import starwu.lite.util.DateUtil;
import starwu.lite.util.SnowflakeIdGenerator;
import starwu.lite.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionCore {

	private final SessionDao dao;
	private final SessionConfig sessionConfig;


	public <T> String createSession(Long userId, Integer deviceType,String userName, T obj, HttpServletResponse response) {
		// 首先看当前用户有没有登录，如果有登陆，将之前登录的token删除，实现踢出的功能
		deviceType = kickOut(userId, deviceType);
		long serialNumber = SnowflakeIdGenerator.nextId();
		String token = StringUtil.appendWithUnSafe("TOKEN-", serialNumber);
		UserSession<T> session = new UserSession<T>(userId, userName, deviceType,obj);
		dao.saveSession(userId,deviceType,token, session,sessionConfig.getSessionPeriod());
		response.setHeader("token", token);
		return token;
	}

	public Integer kickOut(Long userId,Integer deviceType){
		if(LoginKickOutType.NONE.getId().equals(sessionConfig.getLoginKickOutType())){
			return deviceType;
		}

		if(LoginKickOutType.USER.getId().equals(sessionConfig.getLoginKickOutType())){
			deviceType = null;
		}
		String oldToken = dao.getToken(userId,deviceType);
		if (oldToken != null) {
			dao.deleteSession(userId, deviceType,oldToken);
		}
		return deviceType;
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
		dao.resetSessionPeriod(session.getUserId(),session.getDeviceType(),token,sessionConfig.getSessionPeriod());
		ThreadLocalCore.put(ThreadLocalKey.SESSION_KEY, session);
		return session;
	}

	public <T> void deleteSession(HttpServletRequest request) {
		String token = request.getHeader("token");
		UserSession<T> session = dao.getSession(token);
		Long userId = null;
		if (session != null) {
			userId = session.getUserId();
		}
		dao.deleteSession(userId, session.getDeviceType(),token);
	}

	public List<String> getExcexcludeUrls(){
		return sessionConfig.getAllExcludeUrls();
	}


	public Long getUserOnlineCount() {
		Date endDate = new Date();
		Date startDate = DateUtil.getDateBeforeMinute(sessionConfig.getSessionPeriod());
		return dao.userOnlineCount(startDate,endDate);
	}

}
