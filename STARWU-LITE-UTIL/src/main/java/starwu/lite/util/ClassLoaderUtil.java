package starwu.lite.util;

import lombok.SneakyThrows;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 
 * @ClassName: ClassLoaderUtil
 * @Description: 类加载器工具类
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public class ClassLoaderUtil {

	private static MyClassLoader loader = new MyClassLoader();

	/**
	 * 
	 * @Title: getOutCls
	 * @Description: 加载外部jar包中的类
	 * @param path
	 * @param clsName
	 * @return
	 */
	@SneakyThrows
	public static Class<?> getOutCls(String path, String clsName) {
		loader.addPath(path);
		Class<?> cls = loader.loadClass(clsName);
		return cls;
	}

	/**
	 * 
	 * @Title: getLoader
	 * @Description: 获取自定义类加载器
	 * @return
	 */
	public static MyClassLoader getLoader() {
		return loader;
	}

	/**
	 * 
	 * @ClassName: MyClassLoader
	 * @Description: 自定义类加载器
	 * @author Lone Wolf
	 * @date 2019年9月10日
	 */
	static class MyClassLoader extends URLClassLoader {

		/**
		 * 
		 * 创建一个新的实例 MyClassLoader.
		 *
		 */
		public MyClassLoader() {
			super(new URL[] {}, ClassLoaderUtil.class.getClassLoader());
		}

		/**
		 * 
		 * @Title: addPath
		 * @Description: 添加URL
		 * @param path
		 */
		@SneakyThrows
		public void addPath(String path) {
			super.addURL(new URL(path));
		}
	}
}
