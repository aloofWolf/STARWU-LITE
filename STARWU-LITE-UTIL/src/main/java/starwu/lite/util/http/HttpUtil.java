package starwu.lite.util.http;

import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import starwu.lite.util.http.api.HttpApi;
import starwu.lite.util.http.impl.HttpDefaultImpl;

/**
 * 
    * @ClassName: HttpUtil
    * @Description: http工具类
    * @author Lone Wolf
    * @date 2023年11月2日
 */
public class HttpUtil {
	
	private static HttpDefaultImpl httpDefaultImpl = new HttpDefaultImpl();
	
	/**
	 * 发送https get请求
	 * @param <RESP>
	 *
	 * @throws Exception
	 */
	@SneakyThrows
	public static <REQ, RESP> RESP sendGet(String url,REQ requestBean,  Class<RESP> responseBeanCls) {
		return sendGet(url,requestBean,responseBeanCls,httpDefaultImpl);
	}
	
	/**
	 * 发送https get请求
	 * @param <RESP>
	 *
	 * @throws Exception
	 */
	@SneakyThrows
	public static <REQ, RESP> RESP sendGet(String url, REQ requestBean, Class<RESP> responseBeanCls, HttpApi api) {
		HttpGet httpGet = api.getHttpGet(url, requestBean);
		return send(httpGet,responseBeanCls,api);
	}
	
	@SneakyThrows
	public static <REQ, RESP> RESP sendPost(String url,REQ requestBean,  Class<RESP> responseBeanCls) {
		return sendPost(url,requestBean,responseBeanCls,httpDefaultImpl);
	}
	

	/**
	 * 发送https请求
	 *
	 * @throws Exception
	 */
	public static <REQ, RESP> RESP sendPost(String url,REQ requestBean,  Class<RESP> responseBeanCls,HttpApi api) {
		HttpPost httpPost = api.getHttpPost(url, requestBean);
		return send(httpPost,responseBeanCls,api);
	}

	@SneakyThrows
	public static <T> T send(HttpUriRequest request, Class<T> responseBeanCls,HttpApi api) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		try {
			httpClient = api.getHttpClient();
			httpResponse = httpClient.execute(request);
			return api.parseCloseableHttpResponse(httpResponse, responseBeanCls);
		} finally {
			httpResponse.close();
		}
	}
}
