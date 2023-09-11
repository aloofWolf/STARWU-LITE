package starwu.lite.dao.redis.dataConsistency.subscribe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import starwu.lite.canal.notify.CanalObserverApi;
import starwu.lite.dao.redis.dataConsistency.api.CanalRedisHandleApi;
import starwu.lite.dao.redis.dataConsistency.factory.CanalRedisFactory;
import starwu.lite.metadata.bean.canal.CanalDataBean;
import starwu.lite.metadata.enums.canal.DbUpdateType;

@RequiredArgsConstructor
@Component
@Slf4j
public class CanalRedisSubscribeApi implements CanalObserverApi {

    private final CanalRedisFactory factory;
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


    }
}
