package starwu.lite.metadata.config.web;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConfigurationProperties(prefix = "starwu.lite.metadata.config.web")
public class WebConfig {

	private Long projectId = 1l; // 项目id
}
