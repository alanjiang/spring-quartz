package com.agilean.lessons.timer.exception;
import com.agilean.lessons.timer.exception.MyErrorException;


/**
 * jackson transfer exception
 * @author jiangpeng
 *
 */
public class InvalidCallHttpException extends MyErrorException
{
	private final static String message = "{\"errCode\":\"10045\",\"errMsg\":\"调用外部http请求不合法\"}";
	public InvalidCallHttpException() {
		super(message);
	}

	public InvalidCallHttpException(Throwable cause) {
		super(message, cause);
	}
     
}
