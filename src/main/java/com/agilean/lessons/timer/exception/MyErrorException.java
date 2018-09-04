package com.agilean.lessons.timer.exception;

public class MyErrorException extends MyException 
{

		protected MyErrorException(String message) {
			super(message);
		}

		public MyErrorException(String message, Throwable cause) {
			super(message, cause);
		}

	

}
