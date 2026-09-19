package starwu.lite.metadata.config.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "starwu.lite.metadata.config.web")
public class WebConfig {

	private final SwaggerConfig swaggerConfig;

	private Long projectId = 1l; // 项目id

	private List<WebExcludeRecordLogConfig> excludeRecordLogUrls = new ArrayList<>();

	private List<WebExcludeUnifyRespConfig> excludeUnifyRespUrls = new ArrayList<>();

	public List<String> getAllExcludeRecordLogUrls(){
		List<String> allExcludeUrls = new ArrayList<>();
		allExcludeUrls.addAll(swaggerConfig.getSwaggerUrls());
		for(WebExcludeRecordLogConfig config : excludeRecordLogUrls){
			allExcludeUrls.add(config.getExcludeUrl());
		}
		return allExcludeUrls;
	}

	public List<String> getAllExcludeUnifyRespUrls(){
		List<String> allExcludeUrls = new ArrayList<>();
		allExcludeUrls.addAll(swaggerConfig.getSwaggerUrls());
		for(WebExcludeUnifyRespConfig config : excludeUnifyRespUrls){
			allExcludeUrls.add(config.getExcludeUrl());
		}
		return allExcludeUrls;
	}


}
