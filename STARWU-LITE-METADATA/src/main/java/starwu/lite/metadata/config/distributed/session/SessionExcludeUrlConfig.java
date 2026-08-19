package starwu.lite.metadata.config.distributed.session;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
public class SessionExcludeUrlConfig {

    private String excludeUrl;
}
