package com.agilean.lessons.timer.exception;
import com.agilean.lessons.timer.exception.MyErrorException;
/**
 * jackson transfer exception
 * @author jiangpeng
 *
 */
public class HttpHandlerException extends NeedSaveToLogException 
{
	private final static String message = "{\"errCode\":\"10002\",\"errMsg\":\"外部HTTP连接超时或处理异常\"}";
	public HttpHandlerException() {
		super(message);
	}

	public HttpHandlerException(Throwable cause) {
		super(message, cause);
	}
     
}
