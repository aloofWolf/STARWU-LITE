package starwu.lite.metadata.config.distributed.session;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.config.web.SwaggerConfig;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "starwu.lite.metadata.config.distributed.session")
@RequiredArgsConstructor
public class SessionConfig {

    private final SwaggerConfig swaggerConfig;

    private Integer sessionPeriod = 30; // session有效期

    private Integer loginKickOutType = 0;

    private List<SessionExcludeUrlConfig> excludeUrls = new ArrayList<>();

    public List<String> getAllExcludeUrls(){
        List<String> allExcludeUrls = new ArrayList<>();
        allExcludeUrls.addAll(swaggerConfig.getSwaggerUrls());
        for(SessionExcludeUrlConfig config : excludeUrls){
            allExcludeUrls.add(config.getExcludeUrl());
        }
        return allExcludeUrls;
    }
}
