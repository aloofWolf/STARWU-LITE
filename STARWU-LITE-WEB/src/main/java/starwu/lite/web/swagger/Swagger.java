package starwu.lite.web.swagger;


import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import starwu.lite.metadata.config.web.SwaggerConfig;

import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ConditionalOnClass({Docket.class, ApiInfo.class})
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@RequiredArgsConstructor
@Slf4j
public class Swagger {

    private final SwaggerConfig swaggerConfig;

    @Bean
    public ApplicationListener createPrintConfig() {
        return (ApplicationListener<WebServerInitializedEvent>) event -> {
            try {
                // 获取IP
                String hostAddress = Inet4Address.getLocalHost().getHostAddress();
                // 获取端口号
                int port = event.getWebServer().getPort();
                // 获取应用名
                String applicationName = event.getApplicationContext().getApplicationName();
                if(swaggerConfig.isEnable()){
                    // 打印 swagger 文档地址
                    log.info("项目启动启动成功！swagger 接口文档地址: http://" + hostAddress + ":" + port + applicationName + "/doc.html");
                }
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerConfig.getGroupName())
                .apiInfo(apiInfo())
                .select()
                //  加了api注解Controller
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .enable(swaggerConfig.isEnable())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .globalOperationParameters(globalParams());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 接口文档头
                .title(swaggerConfig.getTitle())
                // 接口文档描述
                .description(swaggerConfig.getDescription())
                // 服务器地址
                .termsOfServiceUrl(swaggerConfig.getAuthorUrl())
                .contact(new Contact(swaggerConfig.getAuthor(), swaggerConfig.getAuthorUrl(), swaggerConfig.getAuthorEmail()))
                // 项目版本号
                .version(swaggerConfig.getVersion())
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(swaggerConfig.getAuthHeaders())) {
            swaggerConfig.getAuthHeaders().forEach(authHeader -> {
                apiKeyList.add(new ApiKey(authHeader, authHeader, "header"));
            });
        }
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        if (!CollectionUtils.isEmpty(swaggerConfig.getAuthHeaders())) {
            swaggerConfig.getAuthHeaders().forEach(authHeader ->
                    securityReferences.add(new SecurityReference(authHeader, authorizationScopes)));
        }
        return securityReferences;
    }

    private List<Parameter> globalParams(){
        List<Parameter> globalParams = new ArrayList<>();

        Parameter tokenHeader = new ParameterBuilder()
                .name("token") // header名称
                .description("登录令牌")
                .parameterType("header") // 关键：放在header
                .modelRef(new ModelRef("string"))
                .required(false) // 是否必填
                .build();
        globalParams.add(tokenHeader);
        return globalParams;
    }

    /**
     * 解决SpringBoot和Swagger2冲突
     *
     * @return
     */
    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider/* || bean instanceof WebFluxRequestHandlerProvider*/) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    log.info("修改WebMvcRequestHandlerProvider的属性：handlerMappings出错，可能导致swagger不可用", e);
                    throw new IllegalStateException(e);
                }
            }
        };
    }
}

