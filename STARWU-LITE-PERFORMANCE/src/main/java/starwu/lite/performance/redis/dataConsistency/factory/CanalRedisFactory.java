package starwu.lite.performance.redis.dataConsistency.factory;

import org.springframework.stereotype.Component;
import starwu.lite.design.factory.FactoryEntryApi;
import starwu.lite.performance.redis.dataConsistency.api.CanalRedisHandleApi;
import starwu.lite.performance.redis.dataConsistency.impl.CanalRedisDefaultHandleImpl;

@Component
public class CanalRedisFactory extends FactoryEntryApi<String, CanalRedisHandleApi> {

    @Override
    public <C extends CanalRedisHandleApi> Class<C> getDefaultCls() {
        return (Class<C>) CanalRedisDefaultHandleImpl.class;
    }
}
