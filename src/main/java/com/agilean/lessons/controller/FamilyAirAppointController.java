package com.agilean.lessons.controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.agilean.lessons.timer.constant.HttpHeaderEnum;
import com.agilean.lessons.timer.exception.ErrorReqBodyException;
import com.agilean.lessons.timer.exception.JSONFormatException;

import com.agilean.lessons.timer.service.TaskService;
import com.agilean.lessons.timer.util.HeaderUtil;
import com.agilean.lessons.timer.util.JacksonTool;
@Controller
@RequestMapping("/task")
public class FamilyAirAppointController extends BaseController {
	private final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private  TaskService taskService;
	@Autowired
	private SchedulerFactoryBean schedulerFactory;
	@RequestMapping(value = "/setFamilySceneAppoint", method = RequestMethod.POST)
	public ResponseEntity<String> setFamilySceneAppoint(@RequestBody String input) throws Throwable {
		System.out.println("---setFamilySceneAppoint="+input);
		String result = taskService.setTask(input,schedulerFactory);
		System.out.println("******result="+result);
		
		return new ResponseEntity<String>(result, HeaderUtil.getHeader(HttpHeaderEnum.JSON), HttpStatus.OK);
	}
	@RequestMapping(value = "/closeFamilySceneAppoint", method = RequestMethod.POST)
	public ResponseEntity<String> closeFamilySceneAppoint(@RequestBody String input) throws Throwable {
		logger.info("---closeFamilySceneAppointForm="+input);
		String result = taskService.setTask(input,schedulerFactory);
		return new ResponseEntity<String>(result, HeaderUtil.getHeader(HttpHeaderEnum.JSON), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/openOrClose", method = RequestMethod.POST)
	public ResponseEntity<String> openOrClose(@RequestBody String input) throws Throwable {
		System.out.println("--openOrClose familySceneAppointForm="+input);
		String result = taskService.setTask(input,schedulerFactory);
		return new ResponseEntity<String>(result, HeaderUtil.getHeader(HttpHeaderEnum.JSON), HttpStatus.OK);
	}
}
