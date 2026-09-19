package starwu.lite.web.log.filter;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
