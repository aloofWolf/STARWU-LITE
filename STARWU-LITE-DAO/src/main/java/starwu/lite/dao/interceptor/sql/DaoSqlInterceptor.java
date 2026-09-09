package starwu.lite.dao.interceptor.sql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.bean.distributed.session.UserSession;
import starwu.lite.metadata.config.orm.OrmConfig;
import starwu.lite.metadata.constant.plus.threadLocal.ThreadLocalKey;
import starwu.lite.metadata.entity.web.RequestLog;
import starwu.lite.manage.threadLocal.ThreadLocalPlus;

import java.sql.Statement;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
@Intercepts({
        @Signature(method = "query", type = StatementHandler.class, args = {Statement.class, ResultHandler.class}),
        @Signature(method = "queryCursor", type = StatementHandler.class, args = {Statement.class}),
        @Signature(method = "update", type = StatementHandler.class, args = {Statement.class}),
        @Signature(method = "batch", type = StatementHandler.class, args = {Statement.class})})
public class DaoSqlInterceptor implements Interceptor {

    private final OrmConfig ormConfig;

    private final SlowSqlHnadleCore slowSqlHnadleCore;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Date startTime = new Date();
        Object proceed = null;
        try{
            proceed = invocation.proceed();
        }catch(Throwable e){
            Date endTime = new Date();
            recordLog(startTime,endTime,invocation);
            throw e;
        }
        Date endTime = new Date();
        recordLog(startTime,endTime,invocation);
        return proceed;

    }

    public void recordLog(Date startTime, Date endTime, Invocation invocation) {

        long start = startTime.getTime();
        long end = endTime.getTime();
        long consumTime = end - start;

        if (consumTime > ormConfig.getSqlExecTimeOut()) {
            // 获取查询sql
            RoutingStatementHandler statement = (RoutingStatementHandler) invocation.getTarget();
            String sql = statement.getBoundSql().getSql();
            RequestLog requestLog = ThreadLocalPlus.get(ThreadLocalKey.REQUEST_LOG_KEY);
            UserSession<?> session = ThreadLocalPlus.get(ThreadLocalKey.SESSION_KEY);
            slowSqlHnadleCore.handleLog(requestLog, session,startTime,endTime,consumTime,sql,Thread.currentThread().getId());
        }

    }

}
