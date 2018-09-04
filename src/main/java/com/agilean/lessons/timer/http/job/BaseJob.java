package com.agilean.lessons.timer.http.job;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.agilean.lessons.timer.constant.Constants;
import com.agilean.lessons.timer.exception.HttpHandlerException;
import com.agilean.lessons.timer.http.HttpClientHandlerUtil;

public abstract class BaseJob implements Job 
{
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
	}
    public void openOrCloseAppSend(String familyAirAppointForm)
    {
    	//String reqURL="http://localhost:8080/timer/control";
    	try
		{
		   HttpClientHandlerUtil.getRepBodyString(Constants.CONTROL_SERVER, familyAirAppointForm, "POST");
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new HttpHandlerException(e);
		}
    }
}
