package starwu.lite.plus.async.threadPool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.config.plus.async.AsyncConfig;
import starwu.lite.metadata.config.plus.async.AsyncItemConfig;
import starwu.lite.metadata.enumPlus.EnumPlus;
import starwu.lite.metadata.enums.common.BooleanType;
import starwu.lite.metadata.enums.threadPool.ThreadPoolRejectedExecutionHandlerEnum;

import javax.annotation.PostConstruct;


@Component
@Slf4j
@RequiredArgsConstructor
public class AsyncThreadPool {

	private final AsyncConfig config;

	private final ConfigurableListableBeanFactory beanFactory;

	@PostConstruct
	public void init(){
		for(AsyncItemConfig item : config.getItems()){
			ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
			t.setCorePoolSize(item.getCorePoolSize());
			t.setMaxPoolSize(item.getMaxPoolSize());
			t.setQueueCapacity(item.getQueueCapacity());
			t.setKeepAliveSeconds(item.getKeepAliveSeconds());
			t.setWaitForTasksToCompleteOnShutdown(EnumPlus.getByID(BooleanType.class, item.getWaitForTasksToCompleteOnShutdown()).isFlag());
			t.setThreadNamePrefix("baseThread-"+item.getThreadNamePrefix());
			t.setRejectedExecutionHandler(EnumPlus.getByID(ThreadPoolRejectedExecutionHandlerEnum.class, item.getRejectedExecutionHandler()).getHandler());
			t.initialize();
			beanFactory.registerSingleton(item.getName(), t);
		}
	}

}
