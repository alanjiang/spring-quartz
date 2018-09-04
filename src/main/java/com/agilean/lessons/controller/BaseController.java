package com.agilean.lessons.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
public class BaseController {
	
	public HttpServletRequest request;
	public HttpServletResponse responst;
	
	
	@ModelAttribute
	private void setup(HttpServletRequest request, HttpServletResponse responst){
		this.request = request;
		this.responst = responst;
	}

	
		
}
