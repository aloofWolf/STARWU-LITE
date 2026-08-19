package starwu.lite.web.response;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import starwu.lite.metadata.bean.web.ResponseBean;

/**
 * 
 * @ClassName: ResponseUnifyInterceptor
 * @Description: 封装response的拦截器
 * @author Lone Wolf
 * @date 2019年9月10日
 */
@RestControllerAdvice
@Order(1)
public class ResponseUnifyInterceptor implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	/**
	 * 封装response
	 */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		ResponseBean responseData;
		if (body instanceof ResponseBean) {
			responseData = (ResponseBean) body;
		} else {
			if(!request.getMethod().equals(HttpMethod.GET)){
				responseData = ResponseBean.success(body);
			}else{
				return body;
			}
			
		}
		return responseData;
	}

}
