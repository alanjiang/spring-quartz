package com.agilean.test.quartz;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilean.examples.example13.SimpleRecoveryJob;
import com.agilean.lessons.timer.constant.Constants;
import com.agilean.lessons.timer.form.FamilyAirAppointForm;
import com.agilean.lessons.timer.service.TaskService;
import com.agilean.lessons.timer.util.JacksonTool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:common.xml")
public class QuartzAPITestCase {

	@Autowired
	private TaskService taskService;
	@Autowired
	private SchedulerFactoryBean schedulerFactory;
	
	
	@Test
	public void testAddSimpleJob() throws Exception{
		
		System.out.println("----taskService="+taskService);
		FamilyAirAppointForm form=new FamilyAirAppointForm();
		
		form.setApplianceId(12L);
		form.setRepeat(2);
		
		form.setStatus(2);//open the appliance
		
		form.setSwitcher(2);//open
		String time="11:16";
		form.setTime(time);
		
		form.setTimeZone(8);
		int[] weeks=new int[] {1,2,3,4,5,6,7};
		form.setWeek(weeks);
		
		String cronExpress=JacksonTool.generateCronExpression(weeks, time);
		
		System.out.println(">>>>cron express="+cronExpress);
		
		String input=JacksonTool.fromObjectToJson(form);
		System.out.println(">>>>input="+input);
		String result = taskService.setTask(input,schedulerFactory);
		System.out.println(">>>>result="+result);
		while(true) {
			
			
		}
	}
	
	@Ignore
	@Test
	public void testJob() throws Exception{
		
		   
		    StdScheduler scheduler =(StdScheduler)schedulerFactory.getScheduler();
	        System.out.println(">>>scheduler="+scheduler);
	        String schedId = scheduler.getSchedulerInstanceId();
	        
	        System.out.println(">>>schedId="+schedId);
	       
	        
	        JobDetail job = newJob(SimpleRecoveryJob.class) 
	                .withIdentity("job_" + 1, schedId) // put triggers in group named after the cluster node instance just to distinguish (in logging) what was scheduled from where
	                .requestRecovery() // ask scheduler to re-execute this job if it was in progress when the scheduler went down...
	                .build();
	        TimeZone tz=JacksonTool.getTimeZone(8);
	        JobKey key=new JobKey("job_" + 1, schedId);
	        System.out.println("---key="+key);
	        if (scheduler.getJobDetail(key)!=null) {
	        	 scheduler.deleteJob(key);
	        }  
	        SimpleTrigger trigger = newTrigger()
	                .withIdentity("triger_" + 1, schedId)
	                .startAt(futureDate(1, IntervalUnit.SECOND))
	                .withSchedule(simpleSchedule()
	                        .withRepeatCount(20)
	                        .withIntervalInSeconds(5))
	                .build();
	       
	        
	            
	        scheduler.scheduleJob(job, trigger); 
	           System.out.println(job.getKey() +
	                    " will run at: " + trigger.getNextFireTime() +  
	                    " and repeat: " + trigger.getRepeatCount() + 
	                    " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");
	            
	           scheduler.start();
   
	           while(true) {
	        	   
	           }
	}
}
