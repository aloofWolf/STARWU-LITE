package starwu.lite.orm.dataConsistency.subscribe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import starwu.lite.canal.notify.CanalObserverApi;
import starwu.lite.metadata.bean.canal.CanalDataBean;
import starwu.lite.metadata.enums.canal.DbUpdateType;
import starwu.lite.orm.dataConsistency.api.CanalRedisHandleApi;
import starwu.lite.orm.dataConsistency.factory.CanalRedisFactory;

/*@RequiredArgsConstructor
@Component
@Slf4j*/
public class CanalRedisSubscribeApi /*implements CanalObserverApi*/ {

    /*private final CanalRedisFactory factory;
    @Override
    public boolean isReceived(CanalDataBean canalDataBean) {
        return true;
    }

    @Override
    public void received(CanalDataBean canalDataBean) {

        CanalRedisHandleApi api = factory.get(canalDataBean.getTableName());
        DbUpdateType operatorType = canalDataBean.getType();
        if ("INSERT".equals(operatorType.name())) {
            api.insert(canalDataBean);
        } else if ("UPDATE".equals(operatorType.name())) {
            api.update(canalDataBean);
        } else if ("DELETE".equals(operatorType.name())) {
            api.delete(canalDataBean);
        }


    }*/
}
