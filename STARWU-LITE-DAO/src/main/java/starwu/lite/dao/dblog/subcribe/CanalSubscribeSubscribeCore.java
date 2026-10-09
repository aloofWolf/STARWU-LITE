package starwu.lite.dao.dblog.subcribe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import starwu.lite.canal.notify.CanalObserverApi;
import starwu.lite.dao.dblog.api.CanalDblogHandleApi;
import starwu.lite.dao.dblog.factory.CanalDblogFactory;
import starwu.lite.metadata.bean.canal.CanalDataBean;

@RequiredArgsConstructor
@Component
@Slf4j
public class CanalSubscribeSubscribeCore implements CanalObserverApi {

    private final CanalDblogFactory factory;
    @Override
    public boolean isReceived(CanalDataBean canalDataBean) {
        return !"starwu_lite_db_update_log".equals(canalDataBean.getTableName());
    }

    @Override
    public void received(CanalDataBean canalDataBean) {
        String table = canalDataBean.getTableName();
        CanalDblogHandleApi api = factory.get(table);
        api.handle(canalDataBean);
    }
}
