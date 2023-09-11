package starwu.lite.manage.async.threadPool;

import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
public class AsyncDefaultConfig implements AsyncConfigurer {

    private final AsyncThreadPool asyncThreadPool;

    private final AsyncExceptionHandle asyncExceptionHandle;

    @Override
    public Executor getAsyncExecutor() {
        return asyncThreadPool.getThreadPoolTaskExecutor("default");
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return asyncExceptionHandle;
    }
}
