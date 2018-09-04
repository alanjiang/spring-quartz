package com.agilean.lessons.timer.exception;

public class MyException extends RuntimeException {

	protected MyException(String msg) {
		super(msg);
	}

	protected MyException(String message, Throwable cause) {
		super(message, cause);
	}

}
