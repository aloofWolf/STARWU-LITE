package starwu.lite.util;

/**
 * 
 * @ClassName: PathUtil
 * @Description: 路径工具类
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public class PathUtil {

	/**
	 * 
	 * @Title: getRootPath
	 * @Description:获取项目根路径
	 * @return
	 */
	public static String getRootPath() {
		return PathUtil.class.getClassLoader().getResource("").getPath();
	}
}
