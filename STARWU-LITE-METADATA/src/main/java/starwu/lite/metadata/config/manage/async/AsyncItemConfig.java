package starwu.lite.metadata.config.manage.async;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
public class AsyncItemConfig {
	
	private String name; 
	
	private Integer corePoolSize; 
	
	private Integer maxPoolSize; 
	
	private Integer queueCapacity; 

	private Integer keepAliveSeconds; 
	
	private String threadNamePrefix; 
	
	private Integer waitForTasksToCompleteOnShutdown;
	
	private Integer rejectedExecutionHandler; 

}
