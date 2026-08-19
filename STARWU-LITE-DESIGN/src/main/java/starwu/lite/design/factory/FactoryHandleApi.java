package starwu.lite.design.factory;

import starwu.lite.util.SpringUtil;

import javax.annotation.PostConstruct;


public interface FactoryHandleApi<K> {

	public boolean match(K key);


	public Class<? extends FactoryEntryApi> getFactoryCls();

	@PostConstruct
	public default void regist(){
		FactoryEntryApi fac = SpringUtil.getBean(getFactoryCls());
		fac.regist(this);

	}
}
