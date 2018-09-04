package com.agilean.lessons.timer.exception;

public class MyWarnException extends MyException 
{
	protected MyWarnException(String message) {
		super(message);
	}

	public MyWarnException(String message, Throwable cause) {
		super(message, cause);
	}
}
