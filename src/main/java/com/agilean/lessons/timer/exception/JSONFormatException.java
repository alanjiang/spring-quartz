package com.agilean.lessons.timer.exception;
/**
 * JSON格式异常<br>
 * errCode:1000
 * @author zx
 *
 */
public class JSONFormatException extends MyWarnException {

	private final static String message = "{\"errCode\":\"10001\",\"errMsg\":\"JSON数据转换错误\"}";

	
	public JSONFormatException()
	{
		super(message);
	}

	public JSONFormatException(Throwable cause) {
		super(message, cause);
	}
	
	
}
