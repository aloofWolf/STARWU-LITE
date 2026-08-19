package starwu.lite.web.log.handle;

import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.entity.web.RequestLog;
import starwu.lite.util.SpringUtil;

import javax.annotation.PostConstruct;
import java.util.Date;

public interface LogHandleApi {

    public static LogHandleApi logHandleApi = null;
    public void handleLog(Object body, Date endTime, RequestLog requestLog, UserSession<?> session);

    @PostConstruct
    public default void init(){
        LogHandleCore core = SpringUtil.getBean(LogHandleCore.class);
        core.setLogHandleApi(this);
    }
}
