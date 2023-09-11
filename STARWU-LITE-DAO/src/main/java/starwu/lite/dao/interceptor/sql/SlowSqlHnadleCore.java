package starwu.lite.dao.interceptor.sql;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.config.manage.async.AsyncItemConfig;
import starwu.lite.metadata.entity.web.RequestLog;
import starwu.lite.manage.async.threadPool.AsyncThreadPool;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Data
public class SlowSqlHnadleCore {

    public SlowSqlHandleApi slowSqlHandleApi = null;

    private final AsyncThreadPool asyncThreadPool;

    @PostConstruct
    public void init(){
        AsyncItemConfig item = new AsyncItemConfig();
        item.setName("recordSlowSql");
        asyncThreadPool.addThreadPoolTaskExecutor(item);
    }

    @Async("recordSlowSql")
    public void handleLog(RequestLog requestLog, UserSession<?> session,Date startTime, Date endTime,Long consumTime,
                          String sql,Long threadId) {
        if(sql.contains("starwu_lite_slowsql_log")){
            return;
        }
        slowSqlHandleApi.handleLog(requestLog, session,startTime,endTime, consumTime,sql, threadId);
    }
}
