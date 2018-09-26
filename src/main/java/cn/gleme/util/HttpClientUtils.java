package cn.gleme.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;

/*
 * @{#} HttpclientUtil.java
 *
 * Copyright (c) 2008-2009 All Rights Reserved.
 */
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * Apache Httpclient 4.0 工具包装类
 *
 * @author lixiang
 */
@SuppressWarnings("all")
public class HttpClientUtils {
	private static final String CHARSET_UTF8 = "UTF-8";
	private static final String CHARSET_GBK = "GBK";
	private static final String SSL_DEFAULT_SCHEME = "https";
	private static final int SSL_DEFAULT_PORT = 443;
	// 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
	private static HttpRequestRetryHandler requestRetryHandler = (IOException exception, int executionCount, HttpContext context) -> {
		// 设置恢复策略，在发生异常时候将自动重试3次
		if (executionCount >= 3) {
			// Do not retry if over max retry count
			return false;
		}
		if (exception instanceof NoHttpResponseException) {
			// Retry if the server dropped connection on us
			return true;
		}
		if (exception instanceof SSLHandshakeException) {
			// Do not retry on SSL handshake exception
			return false;
		}
		HttpRequest request = (HttpRequest) context
				.getAttribute(ExecutionContext.HTTP_REQUEST);
		boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
		if (!idempotent) {
			// Retry if the request is considered idempotent
			return true;
		}
		return false;
	};

	// 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放，解决了对连接的释放管理
	private static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		// 自定义响应处理
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String charset = EntityUtils.getContentCharSet(entity) == null ? CHARSET_GBK
						: EntityUtils.getContentCharSet(entity);
				return new String(EntityUtils.toByteArray(entity), charset);
			} else {
				return null;
			}
		}
	};

	/**
	 * Get方式提交,URL中包含查询参数, 格式：http://www.g.cn?search=p&name=s.....
	 *
	 * @param url
	 *            提交地址
	 * @return 响应消息
	 * @throws Exception
	 */
	public static String get(String url) throws Exception {
		return get(url, null, null);
	}

	/**
	 * Get方式提交,URL中不包含查询参数, 格式：http://www.g.cn
	 *
	 * @param url
	 *            提交地址
	 * @param params
	 *            查询参数集, 键/值对
	 * @return 响应消息
	 * @throws Exception
	 */
	public static String get(String url, Map<String, String> params) throws Exception {
		return get(url, params, null);
	}

	/**
	 * Get方式提交,URL中不包含查询参数, 格式：http://www.g.cn
	 *
	 * @param url
	 *            提交地址
	 * @param params
	 *            查询参数集, 键/值对
	 * @param charset
	 *            参数提交编码集
	 * @return 响应消息
	 * @throws Exception
	 */
	public static String get(String url, Map<String, String> params,
							 String charset) throws Exception {
		if (url == null || StringUtils.isEmpty(url)) {
			return null;
		}
		List<NameValuePair> qparams = getParamsList(params);
		if (qparams != null && qparams.size() > 0) {
			charset = (charset == null ? CHARSET_GBK : charset);
			String formatParams = URLEncodedUtils.format(qparams, charset);
			url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url
					.substring(0, url.indexOf("?") + 1) + formatParams);
		}
		DefaultHttpClient httpclient = getDefaultHttpClient(charset);
		HttpGet hg = new HttpGet(url);
		// 发送请求，得到响应
		String responseStr = null;
		try {
			responseStr = httpclient.execute(hg, responseHandler);
		} catch (ClientProtocolException e) {
			throw new Exception("客户端连接协议错误", e);
		} catch (IOException e) {
			throw new Exception("IO操作异常", e);
		} finally {
			abortConnection(hg, httpclient);
		}
		return responseStr;
	}

	/**
	 * Post方式提交,URL中不包含提交参数, 格式：http://www.g.cn
	 *
	 * @param url
	 *            提交地址
	 * @param params
	 *            提交参数集, 键/值对
	 * @return 响应消息
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params) throws Exception {
		return post(url,null,params, null);
	}

	public static String post(String url,Map<String, String> headers, Map<String, String> params) throws Exception {
		return post(url,headers,params, null);
	}

	public static String post(String url, String str) throws Exception {
		return post(url, str, null);
	}

	public static String httpsPost(String url, String str) throws Exception {
		return httpsPost(url, str, null);
	}

	/**
	 * Post方式提交,URL中不包含提交参数, 格式：http://www.g.cn
	 *
	 * @param url
	 *            提交地址
	 * @param params
	 *            提交参数集, 键/值对
	 * @param charset
	 *            参数提交编码集
	 * @return 响应消息
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params,
							  String charset) throws Exception {
		return post(url,null,params, charset);
	}
	/**
	 * Post方式提交,URL中不包含提交参数, 格式：http://www.g.cn
	 *
	 * @param url 提交地址
	 * @param headers 请求头
	 * @param params 提交参数集, 键/值对
	 * @param charset  参数提交编码集
	 * @return 响应消息
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> headers, Map<String, String> params,
							  String charset) throws Exception {
		if (url == null || StringUtils.isEmpty(url)) {
			return null;
		}

		// 创建HttpClient实例
		DefaultHttpClient httpclient = getDefaultHttpClient(charset);
		UrlEncodedFormEntity formEntity = null;
		try {
			if (charset == null || StringUtils.isEmpty(charset)) {
				formEntity = new UrlEncodedFormEntity(getParamsList(params));
			} else {
				formEntity = new UrlEncodedFormEntity(getParamsList(params),
						charset);
			}
		} catch (UnsupportedEncodingException e) {
			throw new Exception("不支持的编码集", e);
		}
		HttpPost hp = new HttpPost(url);
		if(headers != null){
			for (Map.Entry<String, String> e : headers.entrySet()) {
				hp.addHeader(e.getKey(), e.getValue());
			}
		}
		hp.setEntity(formEntity);
		// 发送请求，得到响应
		String responseStr = null;
		try {
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (ClientProtocolException e) {
			throw new Exception("客户端连接协议错误", e);
		} catch (IOException e) {
			throw new Exception("IO操作异常", e);
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}


	public static String post(String url, String str, String charset)
			throws Exception {
		if (url == null || StringUtils.isEmpty(url)) {
			return null;
		}
		// 创建HttpClient实例
		HttpClient httpclient = getDefaultHttpClient(charset);
		StringEntity formEntity = null;
		try {
			if (charset == null || StringUtils.isEmpty(charset)) {
				formEntity = new StringEntity(str);
			} else {
				formEntity = new StringEntity(str, charset);
			}
		} catch (UnsupportedEncodingException e) {
			throw new Exception("不支持的编码集", e);
		}
		HttpPost hp = new HttpPost(url);
		hp.setEntity(formEntity);
		// 发送请求，得到响应
		String responseStr = null;
		try {
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (ClientProtocolException e) {
			throw new Exception("客户端连接协议错误:"+e.getMessage(), e);
		} catch (IOException e) {
			throw new Exception("IO操作异常:"+e.getMessage(), e);
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}

	/**
	 * Post方式提交,忽略URL中包含的参数,解决SSL双向数字证书认证
	 *
	 * @param url
	 *            提交地址
	 * @param params
	 *            提交参数集, 键/值对
	 * @param charset
	 *            参数编码集
	 * @param keystoreUrl
	 *            密钥存储库路径
	 * @param keystorePassword
	 *            密钥存储库访问密码
	 * @param truststoreUrl
	 *            信任存储库绝路径
	 * @param truststorePassword
	 *            信任存储库访问密码, 可为null
	 * @return 响应消息
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params,
							  String charset, final URL keystoreUrl,
							  final String keystorePassword, final URL truststoreUrl,
							  final String truststorePassword) throws Exception {
		if (url == null || StringUtils.isEmpty(url)) {
			return null;
		}
		DefaultHttpClient httpclient = getDefaultHttpClient(charset);
		UrlEncodedFormEntity formEntity = null;
		try {
			if (charset == null || StringUtils.isEmpty(charset)) {
				formEntity = new UrlEncodedFormEntity(getParamsList(params));
			} else {
				formEntity = new UrlEncodedFormEntity(getParamsList(params),
						charset);
			}
		} catch (UnsupportedEncodingException e) {
			throw new Exception("不支持的编码集", e);
		}
		HttpPost hp = null;
		String responseStr = null;
		try {
			KeyStore keyStore = createKeyStore(keystoreUrl, keystorePassword);
			KeyStore trustStore = createKeyStore(truststoreUrl,
					keystorePassword);
			SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore,
					keystorePassword, trustStore);
			Scheme scheme = new Scheme(SSL_DEFAULT_SCHEME, socketFactory,
					SSL_DEFAULT_PORT);
			httpclient.getConnectionManager().getSchemeRegistry()
					.register(scheme);
			hp = new HttpPost(url);
			hp.setEntity(formEntity);
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("指定的加密算法不可用", e);
		} catch (KeyStoreException e) {
			throw new Exception("keytore解析异常", e);
		} catch (CertificateException e) {
			throw new Exception("信任证书过期或解析异常", e);
		} catch (FileNotFoundException e) {
			throw new Exception("keystore文件不存在", e);
		} catch (IOException e) {
			throw new Exception("I/O操作失败或中断 ", e);
		} catch (UnrecoverableKeyException e) {
			throw new Exception("keystore中的密钥无法恢复异常", e);
		} catch (KeyManagementException e) {
			throw new Exception("处理密钥管理的操作异常", e);
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}


	public static X509TrustManager tm = new X509TrustManager() {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}
	};

	public static String httpsPost(String url,String str,String charset) throws Exception {
		if (url == null || StringUtils.isEmpty(url)) {
			return null;
		}
		DefaultHttpClient httpclient = getDefaultHttpClient(charset);

		StringEntity formEntity = null;
		try {
			if(StringUtils.isNotEmpty(str)){
 				if (charset == null || StringUtils.isEmpty(charset)) {
					formEntity = new StringEntity(str);
				} else {
					formEntity = new StringEntity(str,charset);
				}
			}
		} catch (UnsupportedEncodingException e) {
			throw new Exception("不支持的编码集", e);
		}

		HttpPost hp = null;
		String responseStr = null;
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[]{tm}, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(sslcontext);
			socketFactory.setHostnameVerifier(new X509HostnameVerifier() {

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
				@Override
				public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {

				}
				@Override
				public void verify(String arg0, X509Certificate arg1) throws SSLException {

				}
				@Override
				public void verify(String arg0, SSLSocket arg1) throws IOException {
				}
			});
			Scheme scheme = new Scheme(SSL_DEFAULT_SCHEME, socketFactory,SSL_DEFAULT_PORT);

			httpclient.getConnectionManager().getSchemeRegistry()
					.register(scheme);
			hp = new HttpPost(url);
			hp.setEntity(formEntity);
			responseStr = httpclient.execute(hp, responseHandler);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("指定的加密算法“TLS”不可用:"+e.getMessage(), e);
		}  catch (IOException e) {
			throw new Exception("IO操作异常:"+e.getMessage(), e);
		} catch (KeyManagementException e) {
			throw new Exception("处理密钥管理的操作异常:"+e.getMessage(), e);
		} finally {
			abortConnection(hp, httpclient);
		}
		return responseStr;
	}


    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) throws Exception {
        StringBuffer buffer = new StringBuffer();
        // 创建SSLContext对象，并使用我们指定的信任管理器初始化
        TrustManager[] tm = { new MyX509TrustManager() };
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象
        javax.net.ssl.SSLSocketFactory ssf = sslContext.getSocketFactory();

        URL url = new URL(requestUrl);
        HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
        httpUrlConn.setSSLSocketFactory(ssf);

        httpUrlConn.setDoOutput(true);
        httpUrlConn.setDoInput(true);
        httpUrlConn.setUseCaches(false);
        // 设置请求方式（GET/POST）
        httpUrlConn.setRequestMethod(requestMethod);

        if ("GET".equalsIgnoreCase(requestMethod))
            httpUrlConn.connect();

        // 当有数据需要提交时
        if (null != outputStr) {
            OutputStream outputStream = httpUrlConn.getOutputStream();
            // 注意编码格式，防止中文乱码
            outputStream.write(outputStr.getBytes("UTF-8"));
            outputStream.close();
        }

        // 将返回的输入流转换成字符串
        InputStream inputStream = httpUrlConn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        // 释放资源
        inputStream.close();
        inputStream = null;
        httpUrlConn.disconnect();
        return buffer.toString();
    }


	/**
	 * 获取DefaultHttpClient实例
	 *
	 * @param charset
	 *            参数编码集, 可空
	 * @return DefaultHttpClient 对象
	 */
	private static DefaultHttpClient getDefaultHttpClient(final String charset) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		httpclient.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT,10000);
		httpclient.getParams().setParameter(
				CoreConnectionPNames.SO_TIMEOUT,20000);
		httpclient.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET,
				charset == null ? CHARSET_GBK : charset);
		httpclient.setHttpRequestRetryHandler(requestRetryHandler);
		return httpclient;
	}


	/**
	 * 释放HttpClient连接
	 *
	 * @param hrb
	 *            请求对象
	 * @param httpclient
	 *            client对象
	 */
	private static void abortConnection(final HttpRequestBase hrb,
										final HttpClient httpclient) {
		if (hrb != null) {
			hrb.abort();
		}
		if (httpclient != null) {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 从给定的路径中加载此 KeyStore
	 *
	 * @param url
	 *            keystore URL路径
	 * @param password
	 *            keystore访问密钥
	 * @return keystore 对象
	 */
	private static KeyStore createKeyStore(final URL url, final String password)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException {
		if (url == null) {
			throw new IllegalArgumentException("Keystore url may not be null");
		}
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream is = null;
		try {
			is = url.openStream();
			keystore.load(is, password != null ? password.toCharArray() : null);
		} finally {
			if (is != null) {
				is.close();
				is = null;
			}
		}
		return keystore;
	}

	/**
	 * 将传入的键/值对参数转换为NameValuePair参数集
	 *
	 * @param paramsMap
	 *            参数集, 键/值对
	 * @return NameValuePair参数集
	 */
	private static List<NameValuePair> getParamsList(
			Map<String, String> paramsMap) {
		if (paramsMap == null || paramsMap.size() == 0) {
			return null;
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> map : paramsMap.entrySet()) {
			params.add(new BasicNameValuePair(map.getKey(), String.valueOf(map.getValue())));
		}
		return params;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(post("http://www.znzv.cn", "a"));
	}

	public static String getBrowserInfo(String userAgent) {
		String browserInfo = "other";
		String ua = userAgent.toLowerCase();
		String s;
		String version;
		String msieP = "msie ([\\d.]+)";
		String ieheighP = "rv:([\\d.]+)";
		String firefoxP = "firefox\\/([\\d.]+)";
		String chromeP = "chrome\\/([\\d.]+)";
		String operaP = "opr.([\\d.]+)";
		String safariP = "version\\/([\\d.]+).*safari";
		String wcBrowser = "micromessenger\\/([\\d.]+)";

		Pattern pattern = Pattern.compile(msieP);
		Matcher mat = pattern.matcher(ua);


		if (mat.find()) {

			s = mat.group();
			version = s.split(" ")[1];
			browserInfo = "ie " + version.substring(0, version.indexOf("."));
			return browserInfo;

		}
		pattern = Pattern.compile(wcBrowser);
		mat = pattern.matcher(ua);
		if (mat.find()) {

			s = mat.group();
			version = s.split("/")[1];
			browserInfo = "MicroMessenger " + version.substring(0, version.indexOf("."));
			return browserInfo;

		} else {
			pattern = Pattern.compile(safariP);
			mat = pattern.matcher(ua);
			if (mat.find()) {

				s = mat.group();
				version = s.split("/")[1].split(" ")[0];
				browserInfo = "safari " + version.substring(0, version.indexOf("."));
				return browserInfo;

			}
		}
		pattern = Pattern.compile(firefoxP);
		mat = pattern.matcher(ua);
		if (mat.find()) {

			s = mat.group();
			version = s.split("/")[1];
			browserInfo = "firefox " + version.substring(0, version.indexOf("."));
			return browserInfo;

		}

		pattern = Pattern.compile(ieheighP);
		mat = pattern.matcher(ua);
		if (mat.find()) {

			s = mat.group();
			version = s.split(":")[1];
			browserInfo = "ie " + version.substring(0, version.indexOf("."));
			return browserInfo;

		}

		pattern = Pattern.compile(operaP);
		mat = pattern.matcher(ua);
		if (mat.find()) {

			s = mat.group();
			version = s.split("/")[1];
			browserInfo = "opera " + version.substring(0, version.indexOf("."));
			return browserInfo;

		}

		pattern = Pattern.compile(chromeP);
		mat = pattern.matcher(ua);
		if (mat.find()) {

			s = mat.group();
			version = s.split("/")[1];
			browserInfo = "chrome " + version.substring(0, version.indexOf("."));
			return browserInfo;

		}
		return browserInfo;
	}



}
