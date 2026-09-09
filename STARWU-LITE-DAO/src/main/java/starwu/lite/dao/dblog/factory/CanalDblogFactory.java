package starwu.lite.dao.dblog.factory;

import org.springframework.stereotype.Component;
import starwu.lite.design.factory.FactoryEntryApi;
import starwu.lite.dao.dblog.api.CanalDblogHandleApi;
import starwu.lite.dao.dblog.impl.CanalDblogHandleDefaultImpl;

@Component
public class CanalDblogFactory extends FactoryEntryApi<String, CanalDblogHandleApi> {
    @Override
    public <C extends CanalDblogHandleApi> Class<C> getDefaultCls() {
        return (Class<C>) CanalDblogHandleDefaultImpl.class;
    }
}
