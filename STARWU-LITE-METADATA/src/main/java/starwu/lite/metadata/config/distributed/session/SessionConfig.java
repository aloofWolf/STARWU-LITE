package starwu.lite.metadata.config.distributed.session;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.config.plus.async.AsyncItemConfig;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "starwu.lite.metadata.config.distributed.session")
public class SessionConfig {

    private Integer sessionPeriod = 30; // session有效期

    private List<SessionExcludeUrlConfig> excludeUrls = new ArrayList<>();

    public List<String> getAllExcludeUrls(){
        List<String> allExcludeUrls = new ArrayList<>();
        allExcludeUrls.add("/test/**");
        allExcludeUrls.add("/user/login");
        allExcludeUrls.add("/error");
        allExcludeUrls.add("/webjars/**");
        allExcludeUrls.add("/doc.html");
        allExcludeUrls.add("/swagger-resources/**");
        allExcludeUrls.add("/v2/api-docs-ext");
        allExcludeUrls.add("/swagger-ui.html");
        for(SessionExcludeUrlConfig config : excludeUrls){
            allExcludeUrls.add(config.getExcludeUrl());
        }
        return allExcludeUrls;
    }
}
