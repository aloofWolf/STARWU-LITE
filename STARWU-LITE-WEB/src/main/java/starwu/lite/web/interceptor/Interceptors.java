package starwu.lite.web.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import starwu.lite.metadata.config.web.WebConfig;
import starwu.lite.web.interceptor.outer.SessionInterceptor;
import starwu.lite.web.interceptor.outer.ThreadLocalInterceptor;
import starwu.lite.web.log.interceptor.RequestLogInterceptor;
import starwu.lite.web.multipart.MultipartInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: Interceptors
 * @Description: 拦截器
 * @author Lone Wolf
 * @date 2019年9月10日
 */
@Configuration
@RequiredArgsConstructor
public class Interceptors implements WebMvcConfigurer {

	private final SessionInterceptor sessionInterceptor;
	private final ThreadLocalInterceptor threadLocalInterceptor;
	private final RequestLogInterceptor requestLogInterceptor;
	private final MultipartInterceptor multipartInterceptor;
	private final WebConfig webConfig;


	/**
	 * 自定义转换器
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(multipartInterceptor);
	}

	/**
	 * 自定义拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> requestLogInterceptorPatterns = new ArrayList<>();
		requestLogInterceptorPatterns.addAll(webConfig.getAllExcludeRecordLogUrls());
		List<String> sessionInterceptorPatterns = new ArrayList<>();
		sessionInterceptorPatterns.addAll(sessionInterceptor.getExcexcludeUrls());
		registry.addInterceptor(threadLocalInterceptor).addPathPatterns("/**");
		registry.addInterceptor(requestLogInterceptor).addPathPatterns("/**").excludePathPatterns(requestLogInterceptorPatterns);
		registry.addInterceptor(sessionInterceptor).addPathPatterns("/**").excludePathPatterns(sessionInterceptorPatterns);
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/swagger-ui.html/swagger-resources/configuration/ui").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html/swagger-resources/configuration/ui");
		registry.addResourceHandler("/swagger-resources/configuration/security").addResourceLocations("classpath:/META-INF/resources/swagger-resources/configuration/security");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/swagger-resources").addResourceLocations("classpath:/META-INF/resources/swagger-resources");
    }


}
