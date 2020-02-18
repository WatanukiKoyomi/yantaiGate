package com.huadong.hdgate.common.utils.webapi;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于模拟HTTP请求中GET/POST方式
 * @author lit
 *
 */
public class HttpUtils {

	static
	{
		try
		{
			trustAllHttpsCertificates();
			HttpsURLConnection.setDefaultHostnameVerifier(
				new HostnameVerifier() {
					public boolean verify(String urlHostName, SSLSession session)
					{
						return true;
					}
				}
			);
		} catch (Exception e)  {}
	}

	/**
	 * 发送GET请求
	 *
	 * @param url 目的地址
	 * @param parameters 请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendGet(String url, Map<String, String> parameters) {
		String result="";
		BufferedReader in = null;// 读取响应输入流
		StringBuffer sb = new StringBuffer();// 存储参数
		sb.append("?");
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if(parameters.size()==1){
				for(String name:parameters.keySet()){
					//sb.append(name).append("=").append( java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
					sb.append(java.net.URLEncoder.encode(parameters.get(name),"UTF-8"));
				}
				params=sb.toString();
			}else{
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append( java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
				}
				String temp_params = sb.toString();
				params = temp_params.substring(0, temp_params.length() - 1);
			}
			String full_url = url + "/" + params;
			System.out.println(full_url);
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(full_url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();

			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 建立实际的连接
			httpConn.connect();
			// 响应头部获取
			Map<String, List<String>> headers = httpConn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : headers.keySet()) {
				System.out.println(key + "\t：\t" + headers.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应,并设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result ;
	}

	/**
	 * 发送POST请求
	 *
	 * @param url 目的地址
	 * @param parameters 请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendPost(String url, Map<String, String> parameters) {
		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		PrintWriter out = null;
		StringBuffer sb = new StringBuffer();// 处理请求参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if (parameters.size() == 1) {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append( java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
				}
				params = sb.toString();
			} else {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append( java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
				}
				String temp_params = sb.toString();
				params = temp_params.substring(0, temp_params.length() - 1);
			}
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 设置POST方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 获取HttpURLConnection对象对应的输出流
			out = new PrintWriter(httpConn.getOutputStream());
			// 发送请求参数
			out.write(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	public static String postRequest(String url,JSONObject jsonParam) throws IOException{

		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");

		StringEntity se = new StringEntity(jsonParam.toString(),"utf-8");
		se.setContentType("text/json");
		httpPost.setEntity(se);

		CloseableHttpResponse response = client.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity, "UTF-8");

		return result;
	}
	private static void trustAllHttpsCertificates() throws NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[1];
		trustAllCerts[0] = new TrustAllManager();
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	private static class TrustAllManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers()
		{
			return null;
		}
		public void checkServerTrusted(X509Certificate[] certs, String authType)
				throws CertificateException {
		}
		public void checkClientTrusted(X509Certificate[] certs, String authType)
				throws CertificateException {
		}
	}
	/**
	 * 主函数，测试请求
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> parameters = new HashMap<String, String>();
		//AutoGateBusiness business = new AutoGateBusiness();
		parameters.put("name", "1");
		/*String result =sendGet("http://localhost:8085/hdGate/monitor/laneMonitorApi", parameters);
		System.out.println(result);
		System.out.println("=========");*/
		/*String result = sendPost("http://localhost:8085/hdGate/monitor/laneMonitorApi",parameters);
		System.out.println(result);*/
		try {
			//
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("businessId", "sdfwesd-344565-22222");
			jsonParam.put("laneCode", "test");
			jsonParam.put("truckNo", "truckNo");
			jsonParam.put("ocrTruckNo", "ocrTruckNo");
			jsonParam.put("totalWeight", 222.65);
			jsonParam.put("icCardNo", "icCardNo");
			jsonParam.put("countainerNumber", 2);
			jsonParam.put("rfidCardNo", "rfidCardNo");
			jsonParam.put("frontPlcUrl", "http://localhost:8085/hdGate/monitor/laneMonitorApi");
			jsonParam.put("backPlcUrl", "backPlcUrl");
			jsonParam.put("truckPlcUrl", "hdGate/imgs/A01.jpg");
			String str = postRequest("http://localhost:8085/hdGate/monitor/laneMonitorApi",jsonParam);
			System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//jackson方式处理json
		//@JsonIgnore 此注解用于属性上，作用是进行JSON操作时忽略该属性。
		//@JsonFormat 此注解用于属性上，作用是把Date类型直接转化为想要的格式，如@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")。
		//@JsonProperty 此注解用于属性上，作用是把该属性的名称序列化为另外一个名称，如把trueName属性序列化为name，@JsonProperty("name")。
		ObjectMapper mapper = new ObjectMapper(); //转换器
		/*try {
			WtPermission s = mapper.readValue("{\"permissionName\":\"作业管理\",\"permissionDesc\":\"作业管理\"}",WtPermission.class);
			System.out.println(s);

			//方法1
			JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class,WtPermission.class);//转换list
			List<WtPermission> list = mapper.readValue(result,javaType);
			//方法2
			List<WtPermission> list1 = mapper.readValue(result, new TypeReference<List<WtPermission>>() {	});

			System.out.println(list.size()+"  "+list1.size());
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}

}
