/**
 * Copyright: Copyright (c) 2012-2013 
 * Company:  Citycloud
 */
package com.agilean.lessons.timer.util;

import org.springframework.http.HttpHeaders;

import com.agilean.lessons.timer.constant.HttpHeaderEnum;
public class HeaderUtil {
	private HeaderUtil() {
	}

	/**
	 * 设置请求头
	 * 
	 * @return HttpHeaders
	 * "Content-Type", "application/json; charset=utf-8"
	 * "Content-Type", "text/html; charset=utf-8"
	 * "Content-Type", "text/xml; charset=utf-8"
	 */
	public static HttpHeaders getHeader(HttpHeaderEnum en) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", en.getValue());
		return headers;
	}

}
