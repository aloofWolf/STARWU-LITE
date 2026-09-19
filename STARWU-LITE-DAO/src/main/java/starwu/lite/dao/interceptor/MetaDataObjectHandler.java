package starwu.lite.dao.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.constant.plus.threadLocal.ThreadLocalKey;
import starwu.lite.metadata.entity.web.RequestLog;
import starwu.lite.manage.threadLocal.ThreadLocalCore;

@Component
public class MetaDataObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        RequestLog requestLog = ThreadLocalCore.get(ThreadLocalKey.REQUEST_LOG_KEY);
        if(requestLog != null){
            this.strictInsertFill(metaObject,"createRequestLogCode", String.class,requestLog.getCode());
            this.strictInsertFill(metaObject,"updateRequestLogCode", String.class,requestLog.getCode());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        RequestLog requestLog = ThreadLocalCore.get(ThreadLocalKey.REQUEST_LOG_KEY);
        if(requestLog != null){
            this.strictUpdateFill(metaObject,"updateRequestLogCode", String.class,requestLog.getCode());
        }
    }
}
