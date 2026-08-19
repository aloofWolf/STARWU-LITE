package starwu.lite.util;

/**
 * 
 * @ClassName: EnvironmentUtil
 * @Description: 环境工具类
 * @author Lone Wolf
 * @date 2023年12月15日
 */
public class EnvironmentUtil {

	private static Integer containerId; // 当前容器id

	/**
	 * 初始化当前容器id
	 */
	static {
		String containerIdStr = System.getProperty("containerId");
		if (containerIdStr == null) {
			containerIdStr = "1";
		}
		containerId = Integer.parseInt(containerIdStr);
	}


	/**
	 * 
	 * @Title: getContainerId
	 * @Description: 获取当前容器id
	 * @return
	 */
	public static Integer getContainerId() {
		return containerId;
	}

}
