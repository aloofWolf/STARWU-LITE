package starwu.lite.dao.dblog.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.bean.canal.CanalDataBean;
import starwu.lite.metadata.entity.dao.canal.DbUpdateLog;
import starwu.lite.metadata.enums.canal.DbUpdateType;
import starwu.lite.dao.dblog.api.CanalDblogHandleApi;
import starwu.lite.dao.dblog.dao.DbUpdateLogDao;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class CanalDblogHandleDefaultImpl implements CanalDblogHandleApi {

    private final DbUpdateLogDao dao;
    @Override
    public boolean match(String key) {
        return false;
    }

    @Override
    public void handle(CanalDataBean canalDataBean) {

        DbUpdateLog log = new DbUpdateLog();
        log.setDatabaseName(canalDataBean.getDatabaseName());
        log.setTableName(canalDataBean.getTableName());
        log.setExecuteTime(canalDataBean.getExecuteTime());
        log.setBefores(canalDataBean.getBefore());
        log.setAfters(canalDataBean.getAfer());
        log.setPkValue(getPkValue(canalDataBean));
        log.setCreateTime(new Date());
        dao.insert(log);
    }

    protected Long getPkValue(CanalDataBean canalDataBean) {
        DbUpdateType operatorType = canalDataBean.getType();
        if ("INSERT".equals(operatorType.name()) || "UPDATE".equals(operatorType.name())) {
            return canalDataBean.getAfer().getLong("id");
        }else{
            return canalDataBean.getBefore().getLong("id");
        }
    }
}
