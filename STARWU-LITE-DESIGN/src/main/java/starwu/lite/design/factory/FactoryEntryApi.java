package starwu.lite.design.factory;

import starwu.lite.util.SpringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class FactoryEntryApi<K,V extends FactoryHandleApi> {

	private List<V> globalList = new ArrayList<>();

	private ConcurrentHashMap<K, V> globalMap = new ConcurrentHashMap<>();

	protected void  regist(V v){
		globalList.add(v);
	}

	public V get(K key){

		V value = globalMap.get(key);
		if (value != null) {
			return value;
		}
		for(V v : globalList) {
			if(v.match(key)){
				return fillGlobalMap(key,v);
			}
		}
		Class<V> cls  = getDefaultCls();
		V v = SpringUtil.getBean(cls);
		return fillGlobalMap(key,getDefault());
	}

	private V fillGlobalMap(K key,V value){
		globalMap.put(key,value);
		return value;
	}

	public abstract <C extends V> Class<C> getDefaultCls();

	protected <C extends V> C getDefault(){
		return SpringUtil.getBean(getDefaultCls());
	}
	

}
