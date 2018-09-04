package com.agilean.lessons.timer.exception;

public class FamilyAirAppointSetException extends MyErrorException 
{
	private final static String message = "{\"errCode\":\"11001\",\"errMsg\":\"家庭场景设置异常\"}";
	public FamilyAirAppointSetException() {
		super(message);
	}

	public FamilyAirAppointSetException(Throwable cause) {
		super(message, cause);
	}
     
}
