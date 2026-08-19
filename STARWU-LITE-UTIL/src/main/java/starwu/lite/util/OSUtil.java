package starwu.lite.util;

import java.util.Properties;

/**
 * 
 * @ClassName: OSUtil
 * @Description: 操作系统工具类
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public class OSUtil {

	static final Properties PROPERTIES = new Properties(System.getProperties());

	/**
	 * 
	 * @Title: getLineSeparator
	 * @Description:获取行分隔符
	 * @return
	 */
	public static String getLineSeparator() {
		return PROPERTIES.getProperty("line.separator");
	}

	/**
	 * 
	 * @Title: getPathSeparator
	 * @Description:获取文件路径分隔符
	 * @return
	 */
	public static String getPathSeparator() {
		return PROPERTIES.getProperty("file.separator");
	}

}
