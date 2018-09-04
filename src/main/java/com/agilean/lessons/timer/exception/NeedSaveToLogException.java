package com.agilean.lessons.timer.exception;
/*
 * @ need to save to mongodb storage  Exception
 */


public class NeedSaveToLogException extends MyException 
{

		protected NeedSaveToLogException(String message) {
			super(message);
		}

		public NeedSaveToLogException(String message, Throwable cause) {
			super(message, cause);
		}

	

}
