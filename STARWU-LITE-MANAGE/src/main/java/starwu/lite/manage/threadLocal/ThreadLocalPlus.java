package starwu.lite.manage.threadLocal;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: ThreadLocalPlus
 * @Description: ThreadLocalPlus
 * @author Lone Wolf
 * @date 2019年9月10日
 */
@Component
public class ThreadLocalPlus {

	private static ThreadLocal<Map<Object, Object>> tl = new ThreadLocal<Map<Object, Object>>();
	private static ThreadLocal<Map<Object, Object>> tlNoRemove = new ThreadLocal<Map<Object, Object>>();

	/**
	 * 
	 * @Title: get
	 * @Description: 从ThreadLocal中取值
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Object key) {
		return get(key,true);
	}

	public static <T> T get(Object key,boolean isNeedRemove) {
		ThreadLocal<Map<Object, Object>> local;
		if(isNeedRemove){
			local = tl;
		}else{
			local = tlNoRemove;
		}
		Map<Object, Object> map = local.get();
		if (map == null) {
			map = new HashMap<Object, Object>();
			local.set(map);
		}
		Object value = map.get(key);
		return (T) value;
	}

	/**
	 * 
	 * @Title: put
	 * @Description: 向ThreadLocal中添加值
	 * @param key
	 * @param value
	 */
	public static void put(Object key, Object value) {
		put(key, value,true);

	}

	public static void put(Object key, Object value,boolean isNeedRemove) {
		ThreadLocal<Map<Object, Object>> local;
		if(isNeedRemove){
			local = tl;
		}else{
			local = tlNoRemove;
		}
		Map<Object, Object> map = local.get();
		if (map == null) {
			map = new HashMap<Object, Object>();
			local.set(map);
		}
		map.put(key, value);

	}

	/**
	 * 
	 * @Title: clear
	 * @Description: 清空ThreadLocal
	 */
	public static void clear() {
		Map<Object, Object> map = tl.get();
		if (map != null) {
			map.clear();
		}

		tl.remove();
	}
}
