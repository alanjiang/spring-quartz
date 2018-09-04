package com.agilean.lessons.timer.http.job;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
public abstract class AbstractJob implements Job 
{

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
	}
	
  
  
}
