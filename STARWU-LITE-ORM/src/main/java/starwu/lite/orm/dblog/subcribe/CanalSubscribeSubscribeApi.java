package starwu.lite.orm.dblog.subcribe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import starwu.lite.canal.notify.CanalObserverApi;
import starwu.lite.metadata.bean.canal.CanalDataBean;
import starwu.lite.orm.dblog.api.CanalDblogHandleApi;
import starwu.lite.orm.dblog.factory.CanalDblogFactory;

/*@RequiredArgsConstructor
@Component
@Slf4j*/
public class CanalSubscribeSubscribeApi /*implements CanalObserverApi*/ {

    /*private final CanalDblogFactory factory;
    @Override
    public boolean isReceived(CanalDataBean canalDataBean) {
        return true;
    }

    @Override
    public void received(CanalDataBean canalDataBean) {
            String table = canalDataBean.getTableName();
            CanalDblogHandleApi api = factory.get(table);
            api.handle(canalDataBean);

    }*/
}
