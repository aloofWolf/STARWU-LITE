package starwu.lite.web.util;

import lombok.SneakyThrows;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 
 * @ClassName: HttpServletUtil
 * @Description: HttpServlet工具类
 * @author yunxuewen
 * @date 2025年9月10日
 */
public class HttpServletUtil {

	/**
	 * 
	 * @Title: getBodyString
	 * @Description: 从HttpServletRequest中读取body信息
	 * @param request
	 * @return
	 */
	@SneakyThrows
	public static String getBodyString(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = request.getInputStream();
		try (
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")))) {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} finally {
			inputStream.close();
		}
		return sb.toString();
	}

}
