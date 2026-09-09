package starwu.lite.dao.redis.dataConsistency.factory;

import org.springframework.stereotype.Component;
import starwu.lite.dao.redis.dataConsistency.api.CanalRedisHandleApi;
import starwu.lite.dao.redis.dataConsistency.impl.CanalRedisDefaultHandleImpl;
import starwu.lite.design.factory.FactoryEntryApi;

@Component
public class CanalRedisFactory extends FactoryEntryApi<String, CanalRedisHandleApi> {

    @Override
    public <C extends CanalRedisHandleApi> Class<C> getDefaultCls() {
        return (Class<C>) CanalRedisDefaultHandleImpl.class;
    }
}
