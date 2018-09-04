package com.agilean.lessons.timer.constant;

public enum HttpHeaderEnum {

	XML( "text/xml; charset=utf-8"),
	HTML("text/html; charset=utf-8"),
	JSON("text/html; charset=utf-8");
	/*"Content-Type", "application/json; charset=utf-8"
	 * "Content-Type", "text/html; charset=utf-8"
	 * "Content-Type", "text/xml; charset=utf-8"*/
	
	private String value;
	HttpHeaderEnum( String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	
}
