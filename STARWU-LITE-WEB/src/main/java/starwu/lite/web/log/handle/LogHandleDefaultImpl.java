package starwu.lite.web.log.handle;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.bean.web.ResponseBean;
import starwu.lite.metadata.entity.web.RequestLog;
import starwu.lite.metadata.enums.web.ResponseResult;
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
        if(body instanceof ResponseBean){
            ResponseBean responseData = (ResponseBean) body;
            requestLog.setResponseParam(JSONObject.toJSONString(body));
            requestLog.setResult(responseData.getResult());
            requestLog.setErrMsg(responseData.getErrMsg());
        }else if (body instanceof String){
            requestLog.setResponseParam((String) body);
            requestLog.setResult(ResponseResult.SUCCESS);
        }else{
            requestLog.setResult(ResponseResult.UNKNOWN);
            if(body != null){
                requestLog.setResponseParam(JSONObject.toJSONString(body));
            }
        }
        if (session != null) {
            requestLog.setUserId(session.getUserId()).setUserName(session.getUserName());
        }
        String logJsonString = JSONObject.toJSONString(requestLog);
        log.info("详细报文:{}", logJsonString);
        dao.insert(requestLog);
    }
}
