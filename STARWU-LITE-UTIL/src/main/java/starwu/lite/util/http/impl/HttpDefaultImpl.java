package starwu.lite.util.http.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import starwu.lite.util.http.api.HttpApi;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * 
 * @ClassName: HttpDefaultImpl
 * @Description: HttpApi的默认实现
 * @author Lone Wolf
 * @date 2023年11月2日
 */
@Slf4j
public class HttpDefaultImpl implements HttpApi {

	private String charset = "UTF-8";

	private CloseableHttpClient httpclient;

	public HttpDefaultImpl() {
		PoolingHttpClientConnectionManager connMgr = getPoolingHttpClientConnectionManager();
		RequestConfig requestConfig = getRequestConfig();
		this.httpclient = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig)
				.build();
	}

	@Override
	public PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", getSSLConnectionSocketFactory()).build();
		// 设置连接池
		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
		return connMgr;
	}

	@Override
	public RequestConfig getRequestConfig() {
		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(1000);
		// 设置读取超时
		configBuilder.setSocketTimeout(3000);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(1000);
		RequestConfig requestConfig = configBuilder.build();
		return requestConfig;
	}

	@Override
	@SneakyThrows
	public SSLConnectionSocketFactory getSSLConnectionSocketFactory() {
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			// 信任所有
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		}).build();
		HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
		return sslsf;
	}

	@Override
	@SneakyThrows
	public CloseableHttpClient getHttpClient() {
		return this.httpclient;
	}

	@SuppressWarnings("unchecked")
	@Override
	@SneakyThrows
	public <REQ> HttpGet getHttpGet(String url, REQ requestBean) {
		URIBuilder uriBuilder = new URIBuilder(url);
		uriBuilder.setCharset(Charset.forName(getRequestCharset()));
		Class<REQ> cls = (Class<REQ>) requestBean.getClass();
		Field[] fields = cls.getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);
			Object value = field.get(requestBean);
			field.setAccessible(false);
			if (value == null) {
				value = "";
			}
			uriBuilder.setParameter(field.getName(), value.toString());
		}
		List<NameValuePair> params = uriBuilder.getQueryParams();
		log.info("requestParam:{}", params.toString());
		HttpGet httpGet = new HttpGet(uriBuilder.build());
		return httpGet;
	}

	@Override
	public <REQ> HttpPost getHttpPost(String url, REQ requestBean) {
		HttpPost httpPost = new HttpPost(url);
		String requestParam = JSONObject.toJSONString(requestBean);
		log.info("requestParam:{}", requestParam);
		httpPost.setEntity(new StringEntity(JSONObject.toJSONString(requestBean), getRequestCharset()));
		return httpPost;
	}

	@Override
	@SneakyThrows
	public <RESP> RESP parseCloseableHttpResponse(CloseableHttpResponse httpResponse, Class<RESP> responseBeanCls) {
		HttpEntity httpEntity = httpResponse.getEntity();
		String responseStr;
		if (httpEntity != null) {
			responseStr = EntityUtils.toString(httpEntity, getResponseCharset());
		} else {
			responseStr = "";
		}
		log.info("responseParam:{}", responseStr);
		RESP resp = JSON.parseObject(responseStr, responseBeanCls);
		return resp;
	}

	@Override
	public String getRequestCharset() {
		return charset;
	}

	@Override
	public String getResponseCharset() {
		return charset;
	}

}
