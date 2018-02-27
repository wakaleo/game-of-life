package com.huilian.hlej.jet.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

@SuppressWarnings("deprecation")
public class HttpUtil {
	private static final Logger logger = Logger.getLogger(HttpUtil.class);

	/**
	 * http post请求
	 * 
	 * @param url 地址
	 * @param postContent post内容格式为param1=value&param2=value2&param3=value3
	 * @return
	 * @throws IOException
	 */
	public static String httpPostRequest(URL url, String postContent)
			throws Exception {
		OutputStream outputstream = null;
		BufferedReader in = null;
		try {
			URLConnection httpurlconnection = url.openConnection();
			httpurlconnection.setConnectTimeout(10 * 1000);
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setUseCaches(false);
			OutputStreamWriter out = new OutputStreamWriter(
					httpurlconnection.getOutputStream(), "UTF-8");
			out.write(postContent);
			out.flush();

			StringBuffer result = new StringBuffer();
			in = new BufferedReader(new InputStreamReader(
					httpurlconnection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception ex) {
			logger.error("post请求异常：" + ex.getMessage());
			throw new Exception("post请求异常：" + ex.getMessage());
		} finally {
			if (outputstream != null) {
				try {
					outputstream.close();
				} catch (IOException e) {
					outputstream = null;
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					in = null;
				}
			}
		}
	}
	
	/**
	 * http post请求
	 * 
	 * @param url 地址
	 * @param postContent post内容格式为param1=value&param2=value2&param3=value3
	 * @return
	 * @throws IOException
	 */
	public static String httpPostRequest(String urlPath, String postContent)
			throws Exception {
		OutputStream outputstream = null;
		BufferedReader in = null;
		URL url = new URL(urlPath);
		try {
			URLConnection httpurlconnection = url.openConnection();
			httpurlconnection.setConnectTimeout(10 * 1000);
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setUseCaches(false);
			OutputStreamWriter out = new OutputStreamWriter(
					httpurlconnection.getOutputStream(), "UTF-8");
			out.write(postContent);
			out.flush();

			StringBuffer result = new StringBuffer();
			in = new BufferedReader(new InputStreamReader(
					httpurlconnection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception ex) {
			logger.error("post请求异常：" + ex.getMessage());
			throw new Exception("post请求异常：" + ex.getMessage());
		} finally {
			if (outputstream != null) {
				try {
					outputstream.close();
				} catch (IOException e) {
					outputstream = null;
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					in = null;
				}
			}
		}
	}

	/**
	 * 通过httpClient进行post提交
	 * 
	 * @param url 提交url地址
	 * @param charset 字符集
	 * @param keys 参数名
	 * @param values 参数值
	 * @return
	 * @throws Exception
	 */
	public static String HttpClientPost(String url, String charset,
			String[] keys, String[] values) throws Exception {
		HttpClient client = null;
		PostMethod post = null;
		String result = "";
		int status = 200;
		try {
			client = new HttpClient();
			// PostMethod对象用于存放地址
			// 总账户的测试方法
			post = new PostMethod(url);
			// NameValuePair数组对象用于传入参数
			post.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=" + charset);// 在头文件中设置转码

			String key = "";
			String value = "";
			NameValuePair temp = null;
			NameValuePair[] params = new NameValuePair[keys.length];
			for (int i = 0; i < keys.length; i++) {
				key = (String) keys[i];
				value = (String) values[i];
				temp = new NameValuePair(key, value);
				params[i] = temp;
				temp = null;
			}
			post.setRequestBody(params);
			// 执行的状态
			status = client.executeMethod(post);
			logger.info("status = " + status);

			if (status == 200) {
				result = post.getResponseBodyAsString();
			}

		} catch (Exception ex) {
			// TODO: handle exception
			throw new Exception("通过httpClient进行post提交异常：" + ex.getMessage()
					+ " status = " + status);
		} finally {
			post.releaseConnection();
		}
		return result;
	}

	/**
	 * 字符串处理,如果输入字符串为null则返回"",否则返回本字符串去前后空格。
	 * 
	 * @param inputStr 输入字符串
	 * @return string 输出字符串
	 */
	public static String doString(String inputStr) {
		// 如果为null返回""
		if (inputStr == null || "".equals(inputStr) || "null".equals(inputStr)) {
			return "";
		}
		// 否则返回本字符串把前后空格去掉
		return inputStr.trim();
	}

	/**
	 * 对象处理，如果输入对象为null返回"",否则则返回本字符对象信息，去掉前后空格
	 * 
	 * @param object
	 * @return
	 */
	public static String doString(Object object) {
		// 如果为null返回""
		if (object == null || "null".equals(object) || "".equals(object)) {
			return "";
		}
		// 否则返回本字符串把前后空格去掉
		return object.toString().trim();
	}
	
	/**
	 * 通过httpClient进行post提交
	 * 
	 * @param url 提交url地址
	 * @param charset 字符集
	 * @param keys 参数名
	 * @param values 参数值
	 * @return
	 * @throws Exception
	 */
	public static String HttpClientJsonPost(String url, String content) throws Exception {
		@SuppressWarnings("resource")
		DefaultHttpClient client = new DefaultHttpClient();
		String result = null;
		try {
			HttpPost request = new HttpPost(url);
			request.setHeader("Content-Type",
					"application/json;charset=utf-8");
			request.setEntity(new StringEntity(content,"UTF-8"));

			HttpResponse res = client.execute(request);
			int statusCode = res.getStatusLine().getStatusCode();
			logger.info("http response status code :" + statusCode);
			BufferedReader br = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return result;
	}
}
