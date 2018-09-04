package com.agilean.lessons.timer.util;

public interface SystemInit {
	
	public void init();
	
	public void afterApplicationContextSet() throws Exception;
	
	abstract void destroy();
}
