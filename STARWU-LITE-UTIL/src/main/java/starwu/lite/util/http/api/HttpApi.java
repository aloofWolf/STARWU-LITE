package starwu.lite.util.http.api;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * 
 * @ClassName: HttpApi
 * @Description:
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public interface HttpApi {

	/**
	 * 
	 * @Title: getPoolingHttpClientConnectionManager
	 * @Description: 获取连接池对象
	 * @return
	 */
	public PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager();

	/**
	 * 
	 * @Title: getRequestConfig
	 * @Description: 获取配置信息
	 * @return
	 */
	public RequestConfig getRequestConfig();

	/**
	 * 
	 * @Title: getSSLConnectionSocketFactory
	 * @Description: 获取SSLConnectionSocketFactory对象
	 * @return
	 */
	public SSLConnectionSocketFactory getSSLConnectionSocketFactory();

	/**
	 * 
	 * @Title: getHttpClient
	 * @Description: 获取httpClient对象
	 * @return
	 */
	public CloseableHttpClient getHttpClient();

	/**
	 * 
	 * @Title: getHttpGet
	 * @Description: 获取HttpGet对象
	 * @param url
	 * @param requestBean
	 * @return
	 */
	public <REQ> HttpGet getHttpGet(String url, REQ requestBean);

	/**
	 * 
	 * @Title: getHttpPost
	 * @Description: 获取HttpGet对象
	 * @param url
	 * @param requestBean
	 * @return
	 */
	public <REQ> HttpPost getHttpPost(String url, REQ requestBean);

	/**
	 * 
	 * @Title: parseCloseableHttpResponse
	 * @Description: 解析CloseableHttpResponse
	 * @param httpResponse
	 * @param responseBeanCls
	 * @return
	 */
	public <RESP> RESP parseCloseableHttpResponse(CloseableHttpResponse httpResponse, Class<RESP> responseBeanCls);

	/**
	 * 
	 * @Title: getRequestCharset
	 * @Description: 获取request的字符集
	 * @return
	 */
	public String getRequestCharset();

	/**
	 * 
	 * @Title: getResponseCharset
	 * @Description: 获取response的字符集
	 * @return
	 */
	public String getResponseCharset();

}
