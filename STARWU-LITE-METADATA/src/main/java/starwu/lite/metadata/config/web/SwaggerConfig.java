package starwu.lite.metadata.config.web;


import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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


}
