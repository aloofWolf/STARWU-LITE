package starwu.lite.metadata.enums.threadPool;


import lombok.Getter;
import starwu.lite.metadata.enumPlus.EnumApi;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Getter
public enum ThreadPoolRejectedExecutionHandlerEnum implements EnumApi<Integer> {

	ABORT(1, new ThreadPoolExecutor.AbortPolicy(), "抛出异常"), 
	CALLERRUN(2, new ThreadPoolExecutor.CallerRunsPolicy(),"创建新线程执行"), 
	DISCARD(3, new ThreadPoolExecutor.DiscardPolicy(),"直接丢弃"), 
	DISCARDOLDSET(4, new ThreadPoolExecutor.DiscardOldestPolicy(), "丢弃队列中最旧的任务");

	private Integer id;

	private RejectedExecutionHandler handler;

	private String name;

	private ThreadPoolRejectedExecutionHandlerEnum(int id, RejectedExecutionHandler handler, String name) {
		this.id = id;
		this.handler = handler;
		this.name = name;
	}

}
