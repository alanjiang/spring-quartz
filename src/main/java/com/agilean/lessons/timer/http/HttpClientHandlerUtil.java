package com.agilean.lessons.timer.http;
import java.io.IOException;


import java.io.InterruptedIOException;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.agilean.lessons.timer.exception.GetHttpConnectionException;
import com.agilean.lessons.timer.exception.HttpConnOrReadException;
import com.agilean.lessons.timer.exception.InvalidCallHttpException;
import com.agilean.lessons.timer.exception.InvalidHttpRequestException;
/**
 * @ This class is a common interface to call the remote httpserver 
 * @ and get a  Object, json  as response
 *@author : jiangpeng, 2014-10-14
 */
public class HttpClientHandlerUtil 
{
	private  static final Log log = LogFactory.getLog(HttpClientHandlerUtil.class);
	@Autowired
	private static PoolingHttpClientConnectionManager httpPool;
	@Autowired
	private static ConnectionKeepAliveStrategy keepAlive;
	@Autowired
	private static RetryStrategy retryStrategy;
	
	private static final int STATUS_CODE=400;// offical doc said 300
	private static final Boolean CHUNCKED=false;
	private static   RequestConfig requestConfig;
	static 
	{
		requestConfig = RequestConfig.custom().setSocketTimeout(4000).setConnectTimeout(3000).build();
	}
	/*
	 * httprequest method: POST
	 * 
	 * params: request parameters 
	 * 
	 * return a single object
	 */
	/*
	 * @http request with or without parameters and without @requestBodyreturn string,not a object json
	 */
	public static String getRepString(String httpURL,Map<String,String> parameters,String method)
			throws IOException,ClientProtocolException,NoRouteToHostException,ConnectTimeoutException
	{
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = getConnection();
		
	   try
	   {
		HttpGet httpGet=null;
		HttpPut httpPut =null;
		HttpHead httpHead=null;
		HttpDelete httpDelete=null;
    	//add the parameters
  	    ContentType.create("plain/text", Consts.UTF_8);
  	    /*
  	     * The simplest and the most convenient way to handle responses is by using the ResponseHandler
interface, which includes the handleResponse(HttpResponse response) method. This method
completely relieves the user from having to worry about connection management. When using a
ResponseHandler, HttpClient will automatically take care of ensuring release of the connection back
to the connection manager regardless whether the request execution succeeds or causes an exception
  	     */
    	ResponseHandler<String> rh = new ResponseHandler<String>() 
    	{
    	    @Override
    	    public String handleResponse(final HttpResponse response) throws IOException,ClientProtocolException
    	        {
    	        StatusLine statusLine = response.getStatusLine();
    	        HttpEntity entity = response.getEntity();
    	        if (statusLine.getStatusCode() >= STATUS_CODE) 
    	        {
    	        	
    	            throw new HttpResponseException(
    	                    statusLine.getStatusCode(),
    	                    statusLine.getReasonPhrase());
    	        }
    	        if (entity == null) 
    	        {
    	            throw new ClientProtocolException("Response contains no content");
    	        	
    	        }
    	        String contentString = EntityUtils.toString(entity);
    	        return contentString;
    	    }
    	};
    	 String s="";
    	 if(method.equalsIgnoreCase("POST"))
    	 { 
    		 throw new InvalidHttpRequestException();
    	 }
    	 else if((method.equalsIgnoreCase("GET")))
    	 {
    		 httpGet=new HttpGet(httpURL);
    		 httpGet.setConfig(requestConfig);
    		 s=httpclient.execute(httpGet, rh); 
    		 httpGet.abort();
    	 }
    	 else if((method.equalsIgnoreCase("PUT")))
    	 {
    		 throw new InvalidHttpRequestException();
    	 }
    	 else if((method.equalsIgnoreCase("DELETE")))
    	 {
    		 httpDelete=new  HttpDelete(httpURL);
    		 httpDelete.setConfig(requestConfig);
    		 s=httpclient.execute(httpDelete, rh); 
    		 httpDelete.abort();
    	 }
    	 else if((method.equalsIgnoreCase("HEAD")))
    	 {
    		 httpHead=new  HttpHead(httpURL);
    		 httpHead.setConfig(requestConfig);
    		 s=httpclient.execute(httpHead, rh); 
    		 httpHead.abort();
    	 }
    	  return s;
	   }catch(HttpResponseException e)
	   {
		   throw new InvalidCallHttpException(e);
	   }
	   catch(Exception e)
	   {
		   throw new HttpConnOrReadException(e);
	   }
	   finally
	   {
		   httpclient.close();
	   }
	}
	/*
	 * @http request with @RequestBody, only support POST and PUT
	 */
	public static String getRepBodyString(String httpURL,String reqBody, String method)
			throws IOException,ClientProtocolException,NoRouteToHostException,ConnectTimeoutException
	{
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = getConnection();
		try
		{
		HttpPost httpPost=null;
		HttpPut httpPut =null;
        if(method.equalsIgnoreCase("POST"))
    	{
        	     httpPost=new HttpPost(httpURL);
    			 StringEntity entity = new  StringEntity(reqBody, Consts.UTF_8);
    			 httpPost.setEntity(entity);
    	      	 entity.setChunked(CHUNCKED);
    	}
        if(method.equalsIgnoreCase("GET"))
    	{
    			 throw new InvalidHttpRequestException();
    	}
        if(method.equalsIgnoreCase("HEAD"))
    	{
        	 throw new InvalidHttpRequestException(); 	
    	}
        if(method.equalsIgnoreCase("DELETE"))
    	{
        	 throw new InvalidHttpRequestException();
    	}
        if(method.equalsIgnoreCase("PUT"))
    	{   
        	     httpPut=new HttpPut(httpURL);
    			 StringEntity entity = new  StringEntity(reqBody, Consts.UTF_8);
    			 httpPut.setEntity(entity);
    	      	 entity.setChunked(CHUNCKED);
    	}
        ContentType.create("plain/text", Consts.UTF_8);
    	ResponseHandler<String> rh = new ResponseHandler<String>() 
    	{
    	    @Override
    	    public String handleResponse(final HttpResponse response) throws IOException,ClientProtocolException
    	    {
    	        StatusLine statusLine = response.getStatusLine();
    	        HttpEntity entity = response.getEntity();
    	       // log.info("==statusLine.getStatusCode()="+statusLine.getStatusCode());
    	        if (statusLine.getStatusCode() >= STATUS_CODE) 
    	        {
    	            throw new HttpResponseException(
    	                    statusLine.getStatusCode(),
    	                    statusLine.getReasonPhrase());
    	        }
    	        if (entity == null) 
    	        {
    	            throw new ClientProtocolException("Response contains no content");
    	        }
    	        String contentString = EntityUtils.toString(entity);
    	        return contentString;
    	    }
    	};
    	 String s="";
    	 if(method.equalsIgnoreCase("POST"))
    	 {   
    		 httpPost.setConfig(requestConfig);
    		 s=httpclient.execute(httpPost, rh);
    		 httpPost.abort();
    		
    	 }
    	 if(method.equalsIgnoreCase("PUT"))
    	 {
    		 httpPut.setConfig(requestConfig);
    		 s=httpclient.execute(httpPut, rh);
    		 httpPut.abort();
    	 }
    	  return s;	
		}catch(HttpResponseException e)
		{
			 e.printStackTrace();
			   throw new InvalidCallHttpException(e);
		}
		catch(Exception e)
		{
			throw new HttpConnOrReadException(e);
		}
		finally
		{
			httpclient.close();
			
		}
	}	
	
	/**
	 * 从连接池获取连接
	 * @return
	 */
	public static CloseableHttpClient getConnection() 
	{
		CloseableHttpClient client=null;
		try
		{
		client= HttpClients.custom().setConnectionManager(httpPool)
				.setKeepAliveStrategy(keepAlive).setRetryHandler(retryStrategy)
				.build();
		 return client;
		}catch(Exception e)
		{
			e.printStackTrace();
			 throw new GetHttpConnectionException(e);
		}
	}
}

