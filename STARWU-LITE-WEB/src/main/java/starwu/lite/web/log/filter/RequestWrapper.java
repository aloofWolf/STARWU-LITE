package starwu.lite.web.log.filter;

import starwu.lite.web.util.HttpServletUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 
 * @ClassName: RequestWrapper
 * @Description: 记录请求日志的RequestWrapper
 * @author yunxuewen
 * @date 2025年9月10日
 */
public class RequestWrapper extends HttpServletRequestWrapper {

	private final byte[] body;

	public RequestWrapper(HttpServletRequest request) {
		super(request);
		body = HttpServletUtil.getBodyString(request).getBytes(Charset.forName("UTF-8"));
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}
		};
	}

}
