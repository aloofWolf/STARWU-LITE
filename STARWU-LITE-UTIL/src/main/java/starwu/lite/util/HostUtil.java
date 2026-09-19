package starwu.lite.util;

import lombok.SneakyThrows;
import org.springframework.util.AntPathMatcher;

import java.net.InetAddress;

/**
 * 
 * @ClassName: HostUtil
 * @Description: host工具类
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public class HostUtil {

	/**
	 * 
	 * @Title: getCurrHostIp
	 * @Description:获取当前服务器ip
	 * @return
	 * @throws Exception
	 */
	@SneakyThrows
	public static String getCurrHostIp() {
		InetAddress address = InetAddress.getLocalHost();
		String hostIp = address.getHostAddress();
		address.getHostName();
		return hostIp;

	}

	public static void main(String[] args) {
		AntPathMatcher matcher = new AntPathMatcher();
		System.out.println(matcher.match("/**/swagger-resources", "/swagger-resources"));
	}
}
