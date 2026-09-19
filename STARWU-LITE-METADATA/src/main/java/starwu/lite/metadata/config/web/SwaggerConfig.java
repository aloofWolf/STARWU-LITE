package starwu.lite.metadata.config.web;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "starwu.lite.metadata.config.web.swagger")
public class SwaggerConfig {

    private String groupName;
    private String title;
    private String description;
    private String version;
    private String author;
    private String authorUrl;
    private String authorEmail;
    private boolean enable;
    private List<String> authHeaders;

    private final List<String> swaggerUrls = Arrays.asList(
            "/**/v2/api-docs",
            "/**/favicon.ico",
            "/**/doc.html",
            "/**/webjars/**",
            "/**/swagger-resources/**",
            "/**/v2/api-docs-ext",
            "/**/swagger-ui.html",
            "/**/error"
    );


}
