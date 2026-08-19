package starwu.lite.web.log.filter;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 
 * @ClassName: RequestFilter
 * @Description: 记录请求日志的过滤器
 * @author yunxuewen
 * @date 2025年9月10日
 */
@RestControllerAdvice
public class RequestFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String contentType = servletRequest.getContentType();
		String method = "multipart/form-data";
		if("POST".equals(request.getMethod()) && !contentType.contains(method)){
			filterChain.doFilter(new RequestWrapper(request), servletResponse);
		}else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

}
