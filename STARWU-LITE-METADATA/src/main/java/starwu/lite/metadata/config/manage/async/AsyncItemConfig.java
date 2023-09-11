package starwu.lite.metadata.config.manage.async;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;
import starwu.lite.util.StringUtil;

@Component
@Data
@ToString
public class AsyncItemConfig {
	
	private String name; 
	
	private Integer corePoolSize = 6;
	
	private Integer maxPoolSize = 6;
	
	private Integer queueCapacity = Integer.MAX_VALUE;

	private Integer keepAliveSeconds = 3;
	
	private String threadNamePrefix = StringUtil.appendWithUnSafe(this.name,"-");
	
	private Integer waitForTasksToCompleteOnShutdown = 0;
	
	private Integer rejectedExecutionHandler = 3;

}
