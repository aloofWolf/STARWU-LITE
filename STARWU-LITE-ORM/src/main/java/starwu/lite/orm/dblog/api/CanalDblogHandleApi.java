package starwu.lite.orm.dblog.api;

import starwu.lite.design.factory.FactoryHandleApi;
import starwu.lite.metadata.bean.canal.CanalDataBean;
import starwu.lite.orm.dblog.factory.CanalDblogFactory;

public interface CanalDblogHandleApi extends FactoryHandleApi<String> {

    @Override
    public default Class<CanalDblogFactory> getFactoryCls() {
        return CanalDblogFactory.class;
    }

    public void handle(CanalDataBean canalDataBean);
}
