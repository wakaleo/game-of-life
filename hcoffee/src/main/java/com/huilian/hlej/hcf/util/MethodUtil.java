package com.huilian.hlej.hcf.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class MethodUtil {
	
	private static Logger log = LoggerFactory.getLogger(MethodUtil.class);
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		// PrintWriter out = null;
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			// out = new PrintWriter(conn.getOutputStream());
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");// 防止中文乱码
			// 发送请求参数
			// out.print(param);
			out.write(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("【发送 POST 请求出现异常】" + e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	

	 /** 
    * md5加密方法 
    * @param password 
    * @return 
    */  
	public static String md5Password(String password) {  
   	System.out.println("需要加密的字符串："+password);
       try {  
           // 得到一个信息摘要器  
           MessageDigest digest = MessageDigest.getInstance("md5");  
           byte[] result = digest.digest(password.getBytes());  
           StringBuffer buffer = new StringBuffer();     
           // 把没一个byte 做一个与运算 0xff;  
           for (byte b : result) {  
               // 与运算  
               int number = b & 0xff;// 加盐  
               String str = Integer.toHexString(number);  
               if (str.length() == 1) {  
                   buffer.append("0");  
               }  
               buffer.append(str);  
           }  
 
           // 标准的md5加密后的结果  
           return buffer.toString();  
       } catch (NoSuchAlgorithmException e) {  
           return "";  
       }  
   }  
	
   public static String sendHttp(String httpUrl,String param){
	   StringBuffer result = new StringBuffer();
		try {
			URL url = new URL(httpUrl);
			
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("content-type", "text/xml");
			httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
			httpURLConnection.setRequestProperty("contentType", "UTF-8");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.connect();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
			out.write(JSONObject.parse(param).toString());
			out.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				} else {
					result.append(line);
				}
			}
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
   }
   
   /**
    * 转换字符串的格式  11111,22222 --> '11111','22222'
    * @param str
    * @return
    */
   public static String getFormatStr(String str){
 	StringBuffer sb = new StringBuffer();
 	String[] temp = str.split(",");
 	for (int i = 0; i < temp.length; i++) {
 		if (!"".equals(temp[i]) && temp[i] != null)
 				sb.append("'" + temp[i] + "',");
 	}
 	String result = sb.toString();
 	String tp = result.substring(result.length() - 1, result.length());
 	if (",".equals(tp))
 		return result.substring(0, result.length() - 1);
 	else
 		return result;
   }
   
	/**
	 * 发短信统一接口
	 * @param phoneNo
	 * @param content
	 * "【汇联金融汇有房】您好，你的验证码是123456";
	 */
	public static void hyfSendMsm(String phoneNum, String content) {
		
		String url = "http://sapi.253.com/msg/HttpBatchSendSM";// 应用地址
		String account = "huilian_1";// 账号
		String pswd = "Tch123456";// 密码
		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String extno = null;// 扩展码

		try {
			String result = hyfBatchSend(url, account, pswd, phoneNum, content, needstatus, extno);
			log.info("发送手机号为" + phoneNum + "的验证码返回信息########：" + result);
			if (result.substring(result.indexOf(",") + 1, result.indexOf("\n")).equals("0")) {
				log.info("短信已经发送到-汇咖啡");
			} else {
				log.info("售货机短信发送成功-汇咖啡"+content + "发送失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("售货机短信发送成功-汇咖啡"+content + "发送失败");
		}
	}
	
	/**
	 * 
	 * @param url 应用地址，类似于http://ip:port/msg/
	 * @param account 账号
	 * @param pswd 密码
	 * @param mobile 手机号码，多个号码使用","分割
	 * @param msg 短信内容
	 * @param needstatus 是否需要状态报告，需要true，不需要false
	 * @return 返回值定义参见HTTP协议文档
	 * @throws Exception
	 */
	private static String hyfBatchSend(String url, String account, String pswd, String mobile, String msg,
			boolean needstatus, String extno) throws Exception {
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(url, false);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
			method.setQueryString(new NameValuePair[] { 
					new NameValuePair("account", account),
					new NameValuePair("pswd", pswd), 
					new NameValuePair("mobile", mobile),
					new NameValuePair("needstatus", String.valueOf(needstatus)), 
					new NameValuePair("msg", msg),
					new NameValuePair("extno", extno), 
				});
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				return URLDecoder.decode(baos.toString(), "UTF-8");
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			method.releaseConnection();
			}
		}
	
	/**
	 * 计算两个时间段相差的月数
	 * @param date1 开始时间
	 * @param date2 结束时间
	 * @param pattern 时间格式
	 * @return 返date1与date2之间相差的月数
	 * @throws ParseException
	 */
	public static int countMonths(String date1,String date2,String pattern){
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        int monthNum = 0;
        try {
			c1.setTime(sdf.parse(date1));
			c2.setTime(sdf.parse(date2));
			
			int year =c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
	        
	        if(year<0){
	            year=-year;
	            monthNum = year*12+c1.get(Calendar.MONTH)-c2.get(Calendar.MONTH);
	        }
	        monthNum = year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return monthNum;
    }
	
	/**
	 * 获取上一个月的年月字符串
	 * @return
	 */
	public static String beforeMonths(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c=Calendar.getInstance();
        String resultStr = "";
        try {
			c.setTime(sdf.parse(sdf.format(new Date())));
			int year =c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DATE);
	        resultStr = String.valueOf(year) + "-" + String.valueOf(month)+"-" + String.valueOf(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
       return resultStr;
    }
}
