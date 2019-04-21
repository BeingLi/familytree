package abcreate.fb.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import abcreate.fb.common.mapper.JsonMapper;
import abcreate.fb.common.rest.HongshuResult;
import abcreate.fb.common.rest.Result;
import abcreate.fb.modules.sys.utils.UserUtils;

public class HttpUtils {

	private static final int TIMEOUT_SOCKET = 5000;

	private static final int TIMEOUT_CONNECT = 5000;

	private static final int POOL_SIZE = 20;

	private static CloseableHttpClient httpclient;

	// 创建包含connection pool与超时设置的client
	static {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT_SOCKET)
				.setConnectTimeout(TIMEOUT_CONNECT).setCookieSpec(CookieSpecs.STANDARD).build();
		httpclient = HttpClients.custom().setMaxConnTotal(POOL_SIZE).setMaxConnPerRoute(POOL_SIZE)
				.setDefaultRequestConfig(requestConfig).build();
	}

	// get请求
	public static String doGet(String url) {
		return doGet(url, null);
	}

	// get请求(冠新接口，默认传入当前登录用户token)
	public static Result getForResult(String url) {
		return getForResult(url, CacheUtils.getUserToken());
	}
	
	// get请求(冠新接口)
	public static Result getForResult(String url, String token) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("token", token);
		String result = doGet(url, header);
		return (Result) JsonMapper.fromJsonString(result, Result.class);
	}

	public static String doGet(String url, Map<String, String> header) {
		HttpGet httpGet = new HttpGet(url);
		if (header != null) {
			for (String key : header.keySet()) {
				httpGet.addHeader(key, header.get(key));
			}
		}
		String content = null;
		try {
			CloseableHttpResponse remoteResponse = httpclient.execute(httpGet);
			content = handleResponse(remoteResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	// post请求
	public static String doPost(String url, Map<String, String> param) {
		return doPost(url, null, param, false);
	}

	// post请求(冠新接口，默认传入当前登录用户token)
	public static Result postForResult(String url, Map<String, String> param) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("token", CacheUtils.getUserToken());
		return postForResult(url, header, param);
	}
	
	// post请求(冠新接口)
	public static Result postForResult(String url, Map<String, String> header, Map<String, String> param) {
		String result = doPost(url, header, param, true);
		return (Result) JsonMapper.fromJsonString(result, Result.class);
	}
	
	// post请求(宏数接口)
	public static HongshuResult postForHongshuResult(String url, Map<String, String> param) {
		param.put("token", UserUtils.getHongshuToken());
		String result = doPost(url, null, param, true);
		return (HongshuResult) JsonMapper.fromJsonString(result, HongshuResult.class);
	}

	public static String doPost(String url, Map<String, String> header, Map<String, String> param, boolean isJson) {
		HttpPost httpPost = new HttpPost(url);
		if (header != null) {
			for (String key : header.keySet()) {
				httpPost.addHeader(key, header.get(key));
			}
		}
		String content = null;
		try {
			if (param != null) {
				if (isJson) { // 参数为json格式
					httpPost.setEntity(new StringEntity(JsonMapper.toJsonString(param), ContentType.APPLICATION_JSON));
				} else { // 参数为表单格式
					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					for (String key : param.keySet()) {
						nvps.add(new BasicNameValuePair(key, param.get(key)));
					}
					httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
				}
			}
			CloseableHttpResponse remoteResponse = httpclient.execute(httpPost);
			content = handleResponse(remoteResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	// 处理response
	public static String handleResponse(final CloseableHttpResponse response) throws IOException {
		StatusLine statusLine = response.getStatusLine();
		HttpEntity entity = response.getEntity();
		int status = statusLine.getStatusCode();
		String result = null;
		try {
			if (status >= 200 && status < 300) {
				result = EntityUtils.toString(entity);
			} else {
				EntityUtils.consume(entity);
				throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}
		} finally {
			response.close();
		}
		return result;
	}

}