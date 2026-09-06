package starwu.lite.metadata.config.manage.async;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "starwu.lite.metadata.config.manage.async")
public class AsyncConfig {
	
	private List<AsyncItemConfig> items = new ArrayList<>();

}
