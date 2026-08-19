package starwu.lite.util;

import java.util.List;

/**
 * 
 * @ClassName: CollectionUtil
 * @Description: 集合工具类
 * @author Lone Wolf
 * @date 2023年11月25日
 */
public class CollectionUtil {

	/**
	 * 
	 * @Title: findElementFromArr
	 * @Description: 返回数组中指定元素的index
	 * @param element
	 * @param arr
	 * @return
	 */
	public static <E> int findElementFromArr(E element, E[] arr) {
		if (arr == null || arr.length == 0 || element == null) {
			return -1;
		}
		for (int i = 0; i < arr.length; i++) {
			E e = arr[i];
			if (element.equals(e)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 
	 * @Title: findElementFromList
	 * @Description: 返回list中指定元素的index
	 * @param element
	 * @param list
	 * @return
	 */
	public static <E> int findElementFromList(E element, List<E> list) {
		if (list == null || list.size() == 0 || element == null) {
			return -1;
		}
		for (int i = 0; i < list.size(); i++) {
			E e = list.get(i);
			if (element.equals(e)) {
				return i;
			}
		}
		return -1;
	}

}
