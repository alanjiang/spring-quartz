package com.agilean.lessons.timer.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class BodyReaderRequestWrapper extends
		HttpServletRequestWrapper {

	private final byte[] body;
	private final char[] chars;

	public BodyReaderRequestWrapper(HttpServletRequest request)
			throws IOException {
		super(request);
		int length = request.getContentLength();
		body = new byte[length];
		chars = new char[length];
		request.getInputStream().read(body);
		for(int i = 0; i < length; i++){
			chars[i] = (char)body[i];
		}
	}
	
	public char[] getChars(){
		return chars;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int read() throws IOException {
				return bais.read();
			}
		};
	}

}
