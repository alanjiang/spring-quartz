package com.agilean.lessons.timer.exception;
import com.agilean.lessons.timer.exception.MyErrorException;

/**
 * IOReadWriteException exception
 * @author jiangpeng
 *
 */
public class IOReadWriteException extends MyErrorException {

	private final static String message = "{\"errCode\":\"10014\",\"errMsg\":\"HTTP读写错误\"}";
	public IOReadWriteException() {
		super( message);
	}

	public IOReadWriteException(Throwable cause) {
		super( message, cause);
	}

}
