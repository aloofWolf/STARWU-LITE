package starwu.lite.web.response;

import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ErrorCloseConfig {

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return registry -> {
        };
    }

    @Bean
    public WebMvcConfigurer disableErrorForward() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
                // 最核心：直接清空 error path
                System.setProperty("server.error.path", "IGNORE");
                System.setProperty("spring.mvc.throw-exception-if-no-handler-found", "true");
            }
        };
    }
}
