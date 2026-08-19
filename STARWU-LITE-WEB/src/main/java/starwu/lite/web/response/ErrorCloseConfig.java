package starwu.lite.web.response;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ErrorCloseConfig {

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return registry -> {
            // 清空所有错误页面 → 完全禁用错误转发
            // 空实现 = 不注册任何错误页
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
