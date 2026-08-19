package starwu.lite.orm.interceptor;

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
import starwu.lite.metadata.config.orm.OrmConfig;

import java.sql.Statement;

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

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = invocation.proceed();
        long endTime = System.currentTimeMillis();
        long consumTime = endTime - startTime;
        if (consumTime > ormConfig.getSqlExecTimeOut()) {
            recordLog(consumTime, invocation);
        }

        return proceed;
    }

    public void recordLog(long consumTime, Invocation invocation) {

        if (consumTime > ormConfig.getSqlExecTimeOut()) {
            // 获取查询sql
            RoutingStatementHandler statement = (RoutingStatementHandler) invocation.getTarget();
            String sql = statement.getBoundSql().getSql();
            // 打印日志信息
            log.info("sql执行耗时 {}ms", consumTime);
            log.info("sql is : {}", sql);
        }

    }

}
