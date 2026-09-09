package starwu.lite.dao.redis.dataConsistency.subscribe;

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
