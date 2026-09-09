package starwu.lite.dao.interceptor.sql;

import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.entity.web.RequestLog;
import starwu.lite.util.SpringUtil;

import javax.annotation.PostConstruct;
import java.util.Date;

public interface SlowSqlHandleApi {

    public void handleLog(RequestLog requestLog, UserSession<?> session,Date startTime, Date endTime,Long consumTime,
                          String sql,Long threadId);

    @PostConstruct
    public default void init(){
        SlowSqlHnadleCore core = SpringUtil.getBean(SlowSqlHnadleCore.class);
        core.setSlowSqlHandleApi(this);
    }
}
