package starwu.lite.web.response;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import starwu.lite.metadata.bean.web.ResponseBean;
import starwu.lite.metadata.config.web.WebConfig;

import java.util.List;

@RestControllerAdvice
@Order(1)
@RequiredArgsConstructor
public class ResponseUnifyInterceptor implements ResponseBodyAdvice<Object> {

	private final WebConfig webConfig;

	private static final AntPathMatcher matcher = new AntPathMatcher();

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
		String path = request.getURI().getPath();
		List<String> excludeUnifyRespUrls = webConfig.getAllExcludeUnifyRespUrls();
		for(String excludeUnifyRespUrl : excludeUnifyRespUrls){
			if(matcher.match(excludeUnifyRespUrl,path)){
				return body;
			}
		}
		ResponseBean responseData;
		if (body instanceof ResponseBean) {
			responseData = (ResponseBean) body;
		} else if(String.class.equals(returnType.getParameterType())) {
			// 这里对String类型做特殊处理，因为如果是返回类型是String，会用StringHttpMessageConverter转换器，
			// 将ResponseBean对象转成String，报ClassCastException异常，所以这里返回ResponseBean对象的json字符串，
			// 并将ContentType设置成APPLICATION_JSON，这样前端可以按照json类解析
			ResponseBean resp = ResponseBean.success(body);
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			return JSON.toJSONString(resp);
		}else{
			responseData = ResponseBean.success(body);
		}
		return responseData;
	}

}
