package com.agilean.lessons.timer.exception;

import com.agilean.lessons.timer.exception.NeedSaveToLogException;

/**
 * jackson transfer exception
 * @author jiangpeng
 *
 */
public class GetHttpConnectionException extends NeedSaveToLogException 
{
	
	private final static String message = "{\"errCode\":\"10040\",\"errMsg\":\"获取http连接异常\"}";
	public GetHttpConnectionException() {
		super(message);
	}

	public GetHttpConnectionException(Throwable cause) {
		super(message, cause);
	}

}
