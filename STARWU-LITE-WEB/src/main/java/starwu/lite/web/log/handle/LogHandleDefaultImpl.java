package starwu.lite.web.log.handle;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.bean.web.ResponseBean;
import starwu.lite.metadata.entity.web.RequestLog;
import starwu.lite.web.log.dao.RequestLogDao;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogHandleDefaultImpl implements LogHandleApi{

    private final RequestLogDao dao;

    private final LogHandleCore core;

    @PostConstruct
    @Override
    public void init(){
        if(core.getLogHandleApi() == null){
            core.setLogHandleApi(this);
        }
    }

    @Override
    public void handleLog(Object body, Date endTime, RequestLog requestLog, UserSession<?> session) {
        long consumTime = endTime.getTime() - requestLog.getStartTime().getTime();
        requestLog.setEndTime(endTime);
        requestLog.setConsumTime(consumTime);
        ResponseBean responseData = (ResponseBean) body;
        String responseString = JSONObject.toJSONString(responseData);
        requestLog.setResponseParam(responseString);
        requestLog.setResult(responseData.getResult());
        requestLog.setErrMsg(responseData.getErrMsg());
        if (session != null) {
            requestLog.setUserId(session.getUserId()).setUserName(session.getUserName());
        }
        String logJsonString = JSONObject.toJSONString(requestLog);
        log.info("详细报文:{}", logJsonString);
        dao.insert(requestLog);
    }
}
