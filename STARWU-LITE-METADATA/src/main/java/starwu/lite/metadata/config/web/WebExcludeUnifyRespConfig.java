package starwu.lite.metadata.config.web;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
public class WebExcludeUnifyRespConfig {

    private String excludeUrl;
}
