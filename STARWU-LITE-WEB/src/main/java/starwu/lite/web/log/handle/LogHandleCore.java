package starwu.lite.web.log.handle;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.bean.web.ResponseBean;
import starwu.lite.metadata.entity.web.RequestLog;

import java.util.Date;

@Data
@Service
public class LogHandleCore {

    public LogHandleApi logHandleApi = null;

    @Async("recordLog")
    public void handleLog(Object body, Date endTime, RequestLog requestLog, UserSession<?> session) {
        logHandleApi.handleLog(body, endTime, requestLog, session);
    }
}
