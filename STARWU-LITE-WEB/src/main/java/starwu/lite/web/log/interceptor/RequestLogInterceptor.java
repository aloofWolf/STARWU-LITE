package starwu.lite.web.log.interceptor;

import com.alibaba.fastjson2.JSONObject;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import starwu.lite.metadata.constant.plus.threadLocal.ThreadLocalKey;
import starwu.lite.metadata.entity.web.RequestLog;
import starwu.lite.manage.threadLocal.ThreadLocalCore;
import starwu.lite.util.EnvironmentUtil;
import starwu.lite.util.SnowflakeIdGenerator;
import starwu.lite.util.StringUtil;
import starwu.lite.web.util.HttpServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class RequestLogInterceptor implements HandlerInterceptor {

	private Integer containerId = EnvironmentUtil.getContainerId();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
		String requestParam = "";

		log.info("请求URL:{}", request.getRequestURI());
		if ("POST".equals(request.getMethod())) {
			requestParam = getRequestLogForformpPost(request);
		} else {
			requestParam = getRequestLogForformpGet(request);
		}


		log.info("请求报文:{}", requestParam);
		RequestLog requestLog = new RequestLog();
		long serialNumber = SnowflakeIdGenerator.nextId();
		String code = StringUtil.appendWithUnSafe("REQ-", serialNumber);
		requestLog.setCode(code).setStartTime(new Date()).setClientType(userAgent.getOperatingSystem().getDeviceType().getName())
				.setOsType(userAgent.getOperatingSystem().getName()).setClientIp(request.getRemoteAddr())
				.setClientPort(request.getRemotePort()).setRequestMethod(request.getMethod())
				.setUrl(request.getRequestURI()).setRequestParam(requestParam)
				.setContainerId(containerId).setThreadId(Thread.currentThread().getId());

		ThreadLocalCore.put(ThreadLocalKey.REQUEST_LOG_KEY, requestLog);
		return true;
	}

	/**
	 * 
	 * @Title: getRequestLogForformpPost
	 * @Description: 打印POST请求日志
	 * @param request
	 * @throws Exception
	 */
	private String getRequestLogForformpPost(HttpServletRequest request) throws Exception {
		String contentType = request.getContentType();
		String method = "multipart/form-data";
		if (contentType.contains(method)) {
			return getRequestLogForformData(request);
		} else {
			return getRequestLogForformJson(request);
		}
	}

	/**
	 * 
	 * @Title: printRequestLogForformpGet
	 * @Description: 打印GET请求日志
	 * @param request
	 * @throws Exception
	 */
	private String getRequestLogForformpGet(HttpServletRequest request) throws Exception {
		return request.getQueryString();
	}

	/**
	 * 
	 * @Title: printRequestLogForformData
	 * @Description: 打印form-data请求的日志
	 * @param request
	 * @throws Exception
	 */
	private String getRequestLogForformData(HttpServletRequest request) throws Exception {
		JSONObject paramsJson = new JSONObject();
		Map<String, String[]> map = request.getParameterMap();
		for (String key : map.keySet()) {
			String value = request.getParameter(key);
			paramsJson.put(key, value);
		}
		Collection<Part> parts = request.getParts();
		for (Part part : parts) {
			String fileName = part.getSubmittedFileName();
			if (fileName != null) {
				JSONObject partJson = new JSONObject();
				partJson.put("fileName", fileName);
				partJson.put("size", part.getSize());
				paramsJson.put(part.getName(), partJson);
			}

		}
		return paramsJson.toJSONString();
	}

	/**
	 * 
	 * @Title: printRequestLogForformJson
	 * @Description: 打印json请求的日志
	 * @param request
	 * @throws Exception
	 */
	private String getRequestLogForformJson(HttpServletRequest request) throws Exception {
		return HttpServletUtil.getBodyString(request);
	}

}
