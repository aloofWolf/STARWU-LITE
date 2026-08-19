package starwu.lite.metadata.enumPlus;


import java.util.ArrayList;
import java.util.List;

public class EnumPlus {

	/**
	 * 
	 * @Title: getByID
	 * @Description: 根据枚举id获取枚举对象
	 * @param cls
	 * @param id
	 * @return
	 */
	public static <E extends EnumApi,T> E getByID(Class<E> cls, T id) {
		E[] arr = cls.getEnumConstants();
		for (E e : arr) {
			if (id.equals(e.getId())) {
				return e;
			}
		}
		throw new RuntimeException(String.format(E.idNoExistExceptionMessage, cls.getSimpleName(), id));
	}

	/**
	 * 
	 * @Title: getByID
	 * @Description:根据枚举id获取枚举对象,未获取到返回默认值 ֵ
	 * @param cls
	 * @param id
	 * @param defaultValue
	 * @return
	 */
	public static <E extends EnumApi,T> E getByID(Class<E> cls, T id, E defaultValue) {
		E[] arr = cls.getEnumConstants();
		for (E e : arr) {
			if (id == e.getId()) {
				return e;
			}
		}
		return defaultValue;
	}

	/**
	 * 
	 * @Title: getAllIds
	 * @Description: 获取枚举的所有id集合
	 * @param cls
	 * @return
	 */
	public static <E extends EnumApi,T> List<T> getAllIds(Class<E> cls) {
		E[] values = cls.getEnumConstants();
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < values.length; i++) {
			list.add((T) values[i].getId());
		}
		return list;

	}

	/**
	 * 
	 * @Title: getAllValues
	 * @Description: 获取枚举值数组
	 * @param cls
	 * @return
	 */
	public static <E extends EnumApi> E[] getAllValues(Class<E> cls) {
		return cls.getEnumConstants();
	}
}
