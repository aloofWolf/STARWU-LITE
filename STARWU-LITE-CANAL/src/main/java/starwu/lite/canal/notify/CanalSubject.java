package starwu.lite.canal.notify;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.bean.canal.CanalDataBean;
import starwu.lite.metadata.config.canal.CanalConfig;
import starwu.lite.design.observer.SubjectApi;

import java.util.concurrent.*;

@RequiredArgsConstructor
@Component
public class CanalSubject extends SubjectApi<CanalDataBean> {

    private final CanalConfig config;

    @Override
    public ExecutorService getExecutorService() {
        ExecutorService executor = new ThreadPoolExecutor(config.getNotifyDataCorePoolSize()
                ,config.getNotifyDataMaxPoolSize()
                ,config.getNotifyDataKeepAliveSeconds()
                , TimeUnit.SECONDS,new LinkedBlockingQueue<>(),new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}
