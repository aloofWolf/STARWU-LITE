package starwu.lite.util;

/**
 * 
 * @ClassName: ClassUtil
 * @Description: Class工具类
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public class ClassUtil {

	/**
	 * 
	 * @Title: clsIsCustom
	 * @Description: 判断class类是否是用户自定义类型
	 * @param cls
	 * @return
	 */
	public static boolean clsIsCustom(Class<?> cls) {
		return cls.getClassLoader() != null;
	}
}
