package com.bets.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * HttpClientUtil类
 * 
 */
public class HttpClientUtil {
	
	private static RequestConfig requestConfig;
	private static ConnectionConfig connectionConfig;
	private static SocketConfig socketConfig;
	static{
		socketConfig=SocketConfig.custom()
				 .setSoTimeout(60000)					 
				 .build();
		connectionConfig=ConnectionConfig.custom()
				 .setBufferSize(1024*20)
				 .setCharset(Charset.forName("UTF-8"))
				 .build();
		
		requestConfig=RequestConfig.custom()
							.setConnectTimeout(30000)
							.setSocketTimeout(60000)
							.setConnectionRequestTimeout(60000)
							.setExpectContinueEnabled(true)
							.build();
	}
	/**
     * 通用get方法,并发送头信息
     * @param url     请求地址
     * @param headers 请求头信息(不允许重复参数)
     * @return
     * @throws Exception
     */
    public static String doHttpGet(String url,Map<String, String> headers) throws Exception{
    	HttpClient httpClient = HttpClients.custom()
    							.setDefaultRequestConfig(requestConfig)
    							.setDefaultConnectionConfig(connectionConfig)
    							.setDefaultSocketConfig(socketConfig)
    							.build();
        HttpGet httpGet = new HttpGet(url);
        try {
            //1.处理头信息
        	httpGet.addHeader("Connection", "close");
            if(headers!=null&&headers.size()>0){
            	for (String key : headers.keySet()) { 
            		httpGet.addHeader(key,headers.get(key));//给请求中增加头信息	
            	}
            }
            //2.获取返回的结果
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();/** 返回状态 **/
            if(statusCode == HttpStatus.SC_OK){
                return EntityUtils.toString(response.getEntity(),"UTF-8");
            }else{
            	throw new Exception("HttpGet错误:"+response.getStatusLine().getReasonPhrase());
            }
		} catch (Exception e) {
			throw new Exception("HttpGet请求异常:"+e.getMessage());
		} finally{
			httpGet.releaseConnection();
		}
    }
    /**发送get请求，不传递头信息*/
	public static String doHttpGet(String url)throws Exception{
		return doHttpGet(url, null);
	}
    /**
     * 通用post方法
     * @param url    请求的URL
     * @param param  键值对参数(不允许重复参数)
     * @param Header 头信息(不允许重复参数)
     * @return
     * @throws Exception
     */
	public static String doHttpPost(String url, Map<String, String> postParam,Map<String, String> headers) throws Exception{
		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.setDefaultConnectionConfig(connectionConfig)
				.setDefaultSocketConfig(socketConfig)
				.build();
		HttpPost httpPost=new HttpPost(url);
		try {
			//1.处理头部信息
			httpPost.addHeader("Connection", "close");
			if(headers!=null&&headers.size()>0){
				for (String key : headers.keySet()) {
					httpPost.addHeader(key, headers.get(key));
				}
			}
			//2.输入参数处理
			if(postParam!=null&&postParam.size()>0){
				List<NameValuePair> param = new ArrayList<NameValuePair>(postParam.size());
				for (Map.Entry<String, String> entry : postParam.entrySet()) {
					param.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(param,"UTF-8"));
			}
			//3.获取返回的结果
			HttpResponse response=httpClient.execute(httpPost);
//			int statusCode = response.getStatusLine().getStatusCode();/** 返回状态 **/
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				return EntityUtils.toString(response.getEntity(),"UTF-8");
			}else{
				throw new Exception("HttpPost请求错误:"+response.getStatusLine().getReasonPhrase());
			}
		} catch (Exception e) {
			    throw new Exception("HttpPost请求异常:"+e.getMessage());
		} finally{
			httpPost.releaseConnection();
		}
    }
	/**执行post请求
     * @param url  请求的URL
     * @param postParam  键值对参数(不允许重复参数)
     * */
    public static String doHttpPost(String url, Map<String, String> postParam)throws Exception{
    	return doHttpPost(url, postParam,new HashMap<String, String>());
    }
	/***
	 * 
	 * @param baseUrl     基地址请求的地址的最前面部分
	 * @param methodName  请求地址的方法部分.对应action的名称
	 * @param postParam       传递给请求的参数 (不允许重复参数)
	 * @param Header      头信息(不允许重复参数)
	 * @return		               返回请求的结果
	 * @throws Exception
	 */
	public static String doHttpPost(String baseUrl, String methodName,Map<String, String> postParam,Map<String, String> header)throws Exception{
		if(!baseUrl.endsWith("/")) baseUrl+="/";//如果不以斜杠结尾,则增加斜线	
		return doHttpPost(baseUrl+methodName, postParam,header);
	}
	/**
	 * 不传递头信息
	 * @param  baseUrl    基本地址
	 * @param  methodName 请求的方法名称(接口名字)
	 * @param  postParam      请求的参数(不允许重复参数)
	 * @return
	 * @throws Exception
	 */
	public static String doHttpPost(String baseUrl, String methodName,Map<String, String> postParam) throws Exception{
		return doHttpPost(baseUrl,methodName,postParam,new HashMap<String, String>());
	}
	public static void main(String[] args) throws Exception {
		/*String url = "http://cqhb.3tong.net/http/SendSms";
		String sb = null;
		Map<String, String> param = new HashMap<String, String>();
        param.put("Account", "whcqjy140627");
        param.put("Phone", "18908367829");
        sb=doHttpPost(url, param);
        System.out.println(sb);*/
		String url = "http://v.juhe.cn/sms/send";
		String key = "6875d054b0e7fb0d745362d0cb3d5ad4";
		String sb = null;
		Map<String, String> param = new HashMap<String, String>();
        param.put("mobile", "18908367829");
        param.put("tpl_id", "9443"); 	
        param.put("tpl_value", "9443");
        param.put("key", key);
        sb=doHttpGet(url, param);
        System.out.println(sb);
	}
}
