package starwu.lite.web.log.handle;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.bean.web.ResponseBean;
import starwu.lite.metadata.config.plus.async.AsyncItemConfig;
import starwu.lite.metadata.entity.web.RequestLog;
import starwu.lite.plus.async.threadPool.AsyncThreadPool;

import javax.annotation.PostConstruct;
import java.util.Date;

@Data
@Service
public class LogHandleCore {

    public LogHandleApi logHandleApi = null;

    private final AsyncThreadPool asyncThreadPool;

    @PostConstruct
    public void init(){
        AsyncItemConfig item = new AsyncItemConfig();
        item.setName("recordLog");
        item.setCorePoolSize(6);
        item.setMaxPoolSize(6);
        item.setQueueCapacity(Integer.MAX_VALUE);
        item.setKeepAliveSeconds(3);
        item.setThreadNamePrefix("recordLog111-");
        item.setWaitForTasksToCompleteOnShutdown(0);
        item.setRejectedExecutionHandler(3);
        asyncThreadPool.addThreadPoolTaskExecutor(item);
    }

    @Async("recordLog")
    public void handleLog(Object body, Date endTime, RequestLog requestLog, UserSession<?> session) {
        logHandleApi.handleLog(body, endTime, requestLog, session);
    }
}
