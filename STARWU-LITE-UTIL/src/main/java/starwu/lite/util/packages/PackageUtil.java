package starwu.lite.util.packages;

import lombok.SneakyThrows;
import starwu.lite.util.packages.filter.PackageUtilFilter;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 
 * @ClassName: PackageUtil
 * @Description: package工具类
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public class PackageUtil {

	/**
	 * 
	 * @Title: getPackageClss
	 * @Description:获取包下的所有class类
	 * @param packageName
	 * @return
	 */
	public static Collection<?> getPackageClss(String packageName, Class<?> resultCls) {
		return process(packageName, resultCls, false, null);
	}

	public static Collection<?> getPackageObjs(String packageName, Class<?> resultCls) {
		return process(packageName, resultCls, true, null);
	}

	public static Collection<?> getPackageClss(String packageName, Class<?> resultCls, PackageUtilFilter filter) {
		return process(packageName, resultCls, false, filter);
	}

	public static Collection<?> getPackageObjs(String packageName, Class<?> resultCls, PackageUtilFilter filter) {
		return process(packageName, resultCls, true, filter);
	}

	/**
	 * 
	 * @Title: getPackageClss
	 * @Description:获取包下的所有class类,指定过滤器
	 * @param packageName
	 * @param filter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Collection<Class<?>> process(String packageName, Class<?> resultCls, boolean isNewObj,
			PackageUtilFilter filter) {
		Collection<Class<?>> result;
		try {
			result = (Collection<Class<?>>) resultCls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		String filePath = packageName.replace(".", "/");
		ClassLoader loader = PackageUtil.class.getClassLoader();
		Enumeration<URL> urls = null;
		try {
			urls = loader.getResources(filePath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			if (url != null) {
				String protocol = url.getProtocol();
				String pkgPath = url.getPath();
				if ("file".equals(protocol)) {
					result.addAll(processFile(pkgPath, packageName, resultCls, isNewObj, filter));
				} else if ("jar".equals(protocol)) {
					result.addAll(processJar(url, filePath, resultCls, isNewObj, filter));
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @param <T>
	 * @Title: processFile
	 * @Description:递归获取file包下的所有class类
	 * @param filePath
	 * @param packageName
	 * @param clss
	 * @param filter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@SneakyThrows
	private static <T> Collection<T> processFile(String filePath, String packageName, Class<?> resultCls,
			boolean isNewObj, PackageUtilFilter filter) {
		Collection<T> result = (Collection<T>) resultCls.newInstance();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		if (childFiles == null || childFiles.length == 0) {
			return result;
		}
		for (File childFile : childFiles) {
			if (childFile.isDirectory()) {
				String filePathBak = new StringBuffer(filePath).append("/").append(childFile.getName()).toString();
				String packageNameBak = new StringBuffer(packageName).append(".").append(childFile.getName())
						.toString();
				result.addAll(processFile(filePathBak, packageNameBak, resultCls, isNewObj, filter));
			} else {
				String childFileName = childFile.getName();
				if (childFileName.endsWith(".class")) {
					childFileName = new StringBuffer(packageName).append(".")
							.append(childFileName.substring(0, childFileName.lastIndexOf("."))).toString();
					childFileName = childFileName.replace("/", ".");
					Class<?> cls = Class.forName(childFileName);
					if (filter == null || !filter.doFilter(cls)) {
						if (isNewObj) {
							result.add((T) cls.newInstance());
						} else {
							result.add((T) cls);
						}
					}
				}

			}
		}
		return result;
	}

	/**
	 * 
	 * @Title: processJar
	 * @Description:递归获取jar包下的所有class类
	 * @param url
	 * @param packageName
	 * @param filter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@SneakyThrows
	private static <T> Collection<T> processJar(URL url, String packageName, Class<?> resultCls, boolean isNewObj,
			PackageUtilFilter filter) {
		Collection<T> result = (Collection<T>) resultCls.newInstance();
		JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文
			JarEntry jarEntry = entries.nextElement();

			String name = jarEntry.getName();
			// 如果是以/开头的
			if (name.charAt(0) == '/') {
				// 获取后面的字符串
				name = name.substring(1);
			}

			if (jarEntry.isDirectory() || !name.startsWith(packageName) || !name.endsWith(".class")) {
				continue;
			}
			// 如果是一个.class文件 而且不是目录
			// 去掉后面的".class" 获取真正的类名
			String className = name.substring(0, name.length() - 6).replace("/", ".");
			Class<?> cls = Class.forName(className);
			if (filter == null || !filter.doFilter(cls)) {
				if (isNewObj) {
					result.add((T) cls.newInstance());
				} else {
					result.add((T) cls);
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @Title: getPackageName
	 * @Description:根据cls获取packageName
	 * @param cls
	 * @return
	 */
	public static String getPackageName(Class<?> cls) {
		String packageName = cls.getPackage().getName();
		return packageName;
	}

}
