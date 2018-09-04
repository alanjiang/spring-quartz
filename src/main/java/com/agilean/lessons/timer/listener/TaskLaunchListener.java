package com.agilean.lessons.timer.listener;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;
import static org.quartz.impl.matchers.KeyMatcher.keyEquals;
import static org.quartz.impl.matchers.GroupMatcher.jobGroupEquals;
import static org.quartz.impl.matchers.GroupMatcher.groupEquals;
import static org.quartz.impl.matchers.GroupMatcher.triggerGroupEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Matcher;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.agilean.lessons.timer.constant.Constants;
import com.agilean.lessons.timer.http.job.FamilyAirAppointJob;
import com.agilean.lessons.timer.util.JacksonTool;

public class TaskLaunchListener implements ServletContextListener 
{
	protected final transient Log log = LogFactory.getLog(getClass());
	private ApplicationContext applicationContext;
	private ThreadPoolTaskExecutor taskExecutor;
	@Override
	public void contextDestroyed(ServletContextEvent context)
	{
		   log.info("**********************");
		   log.info("*                    *");
		   log.info("*   server           *");
		   log.info("*   shut down        *");
		   log.info("* Author: Jiangpeng 2015  *");
		   log.info("**********************");
		   log.info("server stopped...");

	}

	@Override
	public void contextInitialized(ServletContextEvent e) 
	{  
		   try
		   {
		   log.info("**********************");
		   log.info("*                    *");
		   log.info("*   server           *");
		   log.info("*   start up         *");
		   log.info("* Jiangpeng 2015  *");
		   log.info("**********************");
		   applicationContext = WebApplicationContextUtils.getWebApplicationContext(e.getServletContext());
		   taskExecutor=(ThreadPoolTaskExecutor)applicationContext.getBean("taskExecutor");
	       StdScheduler scheduler =( StdScheduler) applicationContext.getBean("schedulerFactory");
	       log.info("*********spring scheduler=*************"+scheduler);
	       for(String group: scheduler.getTriggerGroupNames()) 
	       { 
	    	
              for(TriggerKey triggerKey : scheduler.getTriggerKeys(triggerGroupEquals(group))) 
              { 
                 log.info("---Found trigger identified by: " + triggerKey); 
              } 
           }
	        
	       for(String group: scheduler.getJobGroupNames()) 
	       { 
	    	   
	         for(JobKey jobKey : scheduler.getJobKeys(jobGroupEquals(group)))
	         { 
	        	 log.info("----Found job identified by: " + jobKey); 
	          } 
	       }
           /*
	       JobDetail job=scheduler.getJobDetail(jobKey(Constants.JOB_KEY_FAMILY_APPONIT, Constants.JOB_GROUP_APPOINT));
	        log.info("---job="+job);
	        if(job!=null)
	        {
	        	 scheduler.deleteJob(jobKey(Constants.JOB_KEY_FAMILY_APPONIT, Constants.JOB_GROUP_APPOINT));
		    }
	         job = newJob(FamilyAirAppointJob.class) 
		                .withIdentity(Constants.JOB_KEY_FAMILY_APPONIT, Constants.JOB_GROUP_APPOINT)
		                .build();
	         
	          CronExpression cronExpression=null;
	          try
	          {
	        	  cronExpression= new CronExpression(""); 
	        	  cronExpression.setTimeZone( timeZone);
	          }catch(ParseException e2)
	          {
	        	  e2.printStackTrace();
	        	  System.exit(0);
	          }
	          CronScheduleBuilder c=CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionFireAndProceed();
	          CronTrigger trigger = newTrigger()
		                 .withIdentity(Constants.TRIGGER_KEY_FAMILY_APPONIT, Constants.JOB_GROUP_APPOINT)
		                 .withSchedule(c)
		                 .build();
	        
	         Date date=scheduler.scheduleJob(job,trigger);
 	         scheduler.start();
	         */
		   }catch(SchedulerException ex)
		   {
			   log.error(ex);
		   }
		
		
	}

	

}
