package starwu.lite.dao.interceptor.sql;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.entity.orm.base.slowSql.SlowSqlLog;
import starwu.lite.metadata.entity.web.RequestLog;
import starwu.lite.dao.core.SlowSqlLogDao;
import starwu.lite.util.EnvironmentUtil;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlowSqlHandleDeaultImpl implements SlowSqlHandleApi{

    private final SlowSqlLogDao dao;

    private final SlowSqlHnadleCore core;

    private Integer containerId = EnvironmentUtil.getContainerId();

    @PostConstruct
    @Override
    public void init(){
        if(core.getSlowSqlHandleApi() == null){
            core.setSlowSqlHandleApi(this);
        }
    }

    @Override
    public void handleLog(RequestLog requestLog, UserSession<?> session, Date startTime, Date endTime, Long consumTime, String sql, Long threadId) {
        SlowSqlLog slowSqlLog = new SlowSqlLog();
        if(requestLog != null){
            slowSqlLog.setRequestCode(requestLog.getCode());
        }
        if(session != null){
            slowSqlLog.setUserId(session.getUserId());
            slowSqlLog.setUserName(session.getUserName());
        }
        slowSqlLog.setStartTime(startTime);
        slowSqlLog.setEndTime(endTime);
        slowSqlLog.setConsumTime(consumTime);
        slowSqlLog.setSlowSql(sql);
        slowSqlLog.setThreadId(threadId);
        slowSqlLog.setContainerId(containerId);
        String logJsonString = JSONObject.toJSONString(slowSqlLog);
        log.info("慢sql详细信息:{}", logJsonString);
        dao.insert(slowSqlLog);



    }
}
