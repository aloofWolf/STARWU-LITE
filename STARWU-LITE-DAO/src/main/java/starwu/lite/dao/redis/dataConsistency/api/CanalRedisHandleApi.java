package starwu.lite.dao.redis.dataConsistency.api;


import starwu.lite.dao.redis.dataConsistency.factory.CanalRedisFactory;
import starwu.lite.design.factory.FactoryHandleApi;
import starwu.lite.metadata.bean.canal.CanalDataBean;

public interface CanalRedisHandleApi extends FactoryHandleApi<String> {

	@Override
	public default Class<CanalRedisFactory> getFactoryCls() {
		return CanalRedisFactory.class;
	}

	public void insert(CanalDataBean canalDataBean);

	public void update(CanalDataBean canalDataBean);

	public void delete(CanalDataBean canalDataBean);

}
