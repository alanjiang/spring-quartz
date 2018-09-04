package com.agilean.lessons.timer.exception;


import com.agilean.lessons.timer.exception.NeedSaveToLogException;

/**
 * jackson transfer exception
 * @author jiangpeng
 *
 */
public class HttpConnOrReadException extends NeedSaveToLogException 
{
	private final static String message = "{\"errCode\":\"10039\",\"errMsg\":\"调用外部http连接或者读超时\"}";
	public HttpConnOrReadException() {
		super(message);
	}
	public HttpConnOrReadException(Throwable cause) {
		super(message, cause);
	}
     
}
