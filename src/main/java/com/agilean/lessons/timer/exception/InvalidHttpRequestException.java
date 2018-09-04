package com.agilean.lessons.timer.exception;

import com.agilean.lessons.timer.exception.MyErrorException;


/**
 * jackson transfer exception
 * @author jiangpeng
 *
 */
public class InvalidHttpRequestException extends MyErrorException {
	
	//10020,无效的http请求
	private final static String message = "{\"errCode\":\"10020\",\"errMsg\":\"无效的http请求\"}";

	public InvalidHttpRequestException() {
		super(message);
	}

	public InvalidHttpRequestException(Throwable cause) {
		super(message, cause);
	}

}
