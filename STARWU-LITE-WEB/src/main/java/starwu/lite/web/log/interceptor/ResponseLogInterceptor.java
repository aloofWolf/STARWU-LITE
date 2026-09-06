package starwu.lite.web.log.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.bean.web.ResponseBean;
import starwu.lite.metadata.constant.plus.threadLocal.ThreadLocalKey;
import starwu.lite.metadata.entity.web.RequestLog;
import starwu.lite.manage.threadLocal.ThreadLocalPlus;
import starwu.lite.web.log.handle.LogHandleCore;

import java.util.Date;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
@Order(2)
public class ResponseLogInterceptor implements ResponseBodyAdvice<Object> {

    private final LogHandleCore logHandleCore;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	/**
	 * 打印并记录请求日志
	 */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if(body instanceof ResponseBean) {
			RequestLog requestLog = ThreadLocalPlus.get(ThreadLocalKey.REQUEST_LOG_KEY);
			UserSession<?> session = ThreadLocalPlus.get(ThreadLocalKey.SESSION_KEY);
			Date endTime = new Date();
			ResponseBean responseData = (ResponseBean) body;
			log.info("响应报文:{}", body);
			logHandleCore.handleLog(responseData, endTime,requestLog,session);
		}
		return body;
	}

}
