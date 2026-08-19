package starwu.lite.metadata.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class AsyncException implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("async error,threadName:{},message:{} ",t.getName(),e);
    }
}
