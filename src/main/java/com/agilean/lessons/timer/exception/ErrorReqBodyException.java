package com.agilean.lessons.timer.exception;
import com.agilean.lessons.timer.exception.MyErrorException;

/**
 * jackson transfer exception
 * @author jiangpeng
 *
 */
public class ErrorReqBodyException extends MyErrorException 
{
	
	private final static String message = "{\"errCode\":\"10029\",\"errMsg\":\"请求的必要参数不可为空或请求数据非法\"}";
	public ErrorReqBodyException() {
		super(message);
	}

	public ErrorReqBodyException(Throwable cause) {
		super(message, cause);
	}
     
}
