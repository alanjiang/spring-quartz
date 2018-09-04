package com.agilean.lessons.timer.service;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.CronScheduleBuilder.weeklyOnDayAndHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;
import static org.quartz.impl.matchers.GroupMatcher.jobGroupEquals;
import static org.quartz.impl.matchers.GroupMatcher.triggerGroupEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.agilean.lessons.timer.constant.Constants;
import com.agilean.lessons.timer.exception.FamilyAirAppointSetException;
import com.agilean.lessons.timer.form.BasePojo;
import com.agilean.lessons.timer.form.FamilyAirAppointForm;
import com.agilean.lessons.timer.http.job.FamilyAirAppointJob;
import com.agilean.lessons.timer.util.JacksonTool;
@Service("taskService")
public class TaskServiceImpl implements TaskService {
	private final Log log = LogFactory.getLog(getClass());
	@Override
	public String setTask(String familyAirAppointForm,SchedulerFactoryBean schedulerFactory) throws Exception 
	{  
		try
		{
		FamilyAirAppointForm form = null;
		form = (FamilyAirAppointForm)JacksonTool.fromJsonToObject(familyAirAppointForm, FamilyAirAppointForm.class);
		StdScheduler scheduler=(StdScheduler)schedulerFactory.getScheduler();
		log.info("----spring scheduler="+scheduler);
	    for(String group: scheduler.getTriggerGroupNames()) 
	    { 
              for(TriggerKey triggerKey : scheduler.getTriggerKeys(triggerGroupEquals(group))) 
              { 
                 log.info("---Found trigger identified by: " + triggerKey); 
              }
        } 
	     for(String group: scheduler.getJobGroupNames()) 
	     { 
	       // enumerate each job in group 
	         for(JobKey jobKey : scheduler.getJobKeys(jobGroupEquals(group)))
	         { 
	        	 log.info("----Found job identified by: " + jobKey); 
	          } 
	      }
	        Long familyId=form.getFamilyId();
	        Integer status=form.getStatus();//open or close the timer
	        int[] weeks=form.getWeek();
	        Integer repeat=form.getRepeat();
	        String time=form.getTime();
	        log.info("----time="+time);
	        Integer timeZone=form.getTimeZone();
	        String schedId = scheduler.getSchedulerInstanceId();
	        TimeZone tz=JacksonTool.getTimeZone(timeZone);
	        String jobkey=Constants.APPOINT_JOB_GROUP_PREFIX+familyId;
	        String triggerkey=Constants.APPOINT_TRIGGER_GROUP_PREFIX+familyId;
	        JobDetail job=scheduler.getJobDetail(jobKey(jobkey, Constants.APPOINT_JOB_GROUP));
	        //CronScheduleBuilder cb=cronSchedule(JacksonTool.generateCronExpression(weeks, time));
	        if(repeat.intValue()==2)// repeat,the CronTrigger trigger should be used.
	        {
	        	CronTrigger trigger = newTrigger()
	                    .withIdentity(triggerkey, Constants.APPOINT_JOB_GROUP)
	                    .withSchedule(cronSchedule(JacksonTool.generateCronExpression(weeks, time)))
	                    .build();
	        	
	        	 trigger.getTimeZone().setDefault(tz);
	        	 if(job!=null)
	        	 {
	        		 log.info("---job="+job.getKey());
	        		 scheduler.deleteJob(jobKey(jobkey,Constants.APPOINT_JOB_GROUP));
	        	 }
	        		 job = newJob(FamilyAirAppointJob.class) 
	 			                .withIdentity(jobkey, Constants.APPOINT_JOB_GROUP)
	 			                .build();
	        		 job.getJobDataMap().put("familyId", familyId);
	       	         job.getJobDataMap().put("status", status);
	       	         job.getJobDataMap().put("time",time);
	       	         job.getJobDataMap().put("repeat",repeat);
	       	         job.getJobDataMap().put("familyAirAppointForm", familyAirAppointForm); 
	 	        	 scheduler.scheduleJob(job, trigger);
	 	        	
	        }else // not repeat SimpleJob
	        {
	        	Map<JobDetail,Set<? extends Trigger>> triggerMap=new HashMap<JobDetail,Set<? extends Trigger>>();
	        	Set<SimpleTrigger> triggers=new HashSet<SimpleTrigger>();
	        	//1,remove all the triggers of job in case job has been existed
	        	 if(job!=null)
	        	 {
	        		 scheduler.deleteJob(jobKey(jobkey));
	        		 /*job.getJobDataMap().put("familyId", familyId);
	       	         job.getJobDataMap().put("status", status);
	       	         job.getJobDataMap().put("time",time);
	       	         job.getJobDataMap().put("repeat",repeat);
	       	         job.getJobDataMap().put("familyAirAppointForm", familyAirAppointForm);
	        	     List<Trigger> jobTriggers =(List<Trigger>)scheduler.getTriggersOfJob(jobKey(jobkey, Constants.APPOINT_JOB_GROUP));
	        	     for(Trigger t:jobTriggers)
	        	     {
	        		   log.info("---trigger key="+t.getKey().getName());
	        		   scheduler.unscheduleJob(t.getKey()) ;
	        	     }*/
	        	   
	        	 }
	        		 job = newJob(FamilyAirAppointJob.class) 
	 			                .withIdentity(jobkey, Constants.APPOINT_JOB_GROUP).storeDurably()
	 			                .build(); 
	        		 job.getJobDataMap().put("familyId", familyId);
	       	         job.getJobDataMap().put("status", status);
	       	         job.getJobDataMap().put("time",time);
	       	         job.getJobDataMap().put("repeat",repeat);
	       	         job.getJobDataMap().put("familyAirAppointForm", familyAirAppointForm);
	                //2, generate all the triggers
	        	   Set<Date> triggerDates=JacksonTool.getTiggerTimes(weeks, timeZone, time);
	        	   int i=0;
	        	   for(Date date:triggerDates)
	        	   {   
	        		        Calendar calendar=Calendar.getInstance(JacksonTool.getTimeZone(timeZone));
	        		        calendar.setTime(new Date());
	        		        Date appointDate=calendar.getTime();
	        		        log.info("--appointDate="+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(appointDate));
	        		       
	        		        log.info("--date time="+date.getTime());
	        		        log.info("--new Date().getTime()="+new Date().getTime());
	        		        log.info("--System.currentTimeMillis()="+System.currentTimeMillis());
	        		        if(date.getTime()>System.currentTimeMillis())
	        		        {
	        		         i++;
	        	             SimpleTrigger trigger=(SimpleTrigger) newTrigger() 
		                    .withIdentity(triggerkey+"_"+i, Constants.APPOINT_JOB_GROUP)
		                    .startAt(date)
		                    .build();
		        	        triggers.add(trigger);
		        	       
	        		        }
		        	
	        	   }
	        	   triggerMap.put(job, triggers);
	        	   scheduler.addJob(job, true);
	        	   scheduler.scheduleJobs(triggerMap, true); 
	        }
	      scheduler.start();
	      BasePojo model=new  BasePojo();
	      model.setErrCode("0");
	      model.setErrMsg("success");
	      String res=JacksonTool.fromObjectToJson(model);
	      return res;
		}catch(Exception e)    
		{
			log.error(e);
			throw new FamilyAirAppointSetException(e);
		}  	
	}
	

	public String getTask(String familyAirAppointForm,SchedulerFactoryBean schedulerFactory) throws Exception 
	{
		try
		{
		FamilyAirAppointForm form=(FamilyAirAppointForm)JacksonTool.fromJsonToObject(familyAirAppointForm, FamilyAirAppointForm.class);
	    Long familyId=form.getFamilyId();
	    String jobkey=Constants.APPOINT_JOB_GROUP_PREFIX+familyId;
	    StdScheduler scheduler=(StdScheduler)schedulerFactory.getScheduler();
	    JobDetail job=scheduler.getJobDetail(jobKey(jobkey, Constants.APPOINT_JOB_GROUP));
	    
	     familyAirAppointForm=job.getJobDataMap().getString("familyAirAppointForm");
	     log.info("---- getTask familyAirAppointForm="+familyAirAppointForm);
	     return familyAirAppointForm;
	     
		}catch(Exception e)   
		{
			e.printStackTrace();
			throw new FamilyAirAppointSetException(e);
		}
	    
		
	}

}
