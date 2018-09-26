package com.agilean.lessons.timer.http.job;
import java.io.File;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import com.agilean.lessons.timer.form.FamilyAirAppointForm;
import com.agilean.lessons.timer.util.JacksonTool;
public class FamilyAirAppointJob extends BaseJob 
{
	private final Log logger = LogFactory.getLog(getClass());
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		 JobKey jobKey = context.getJobDetail().getKey();
	     JobDataMap data = context.getJobDetail().getJobDataMap();
	     String time=data.getString("time");
	     String familyAirAppointForm=data.getString("familyAirAppointForm");
	     //do something, control the appliance
	     System.out.println("---FamilyAirAppointJob is triggered-at-"+time);
	     FamilyAirAppointForm f=(FamilyAirAppointForm)JacksonTool.fromJsonToObject(familyAirAppointForm, FamilyAirAppointForm.class);
	     if(f.getStatus().intValue()==2)// when home model is start
	     {
	        super.openOrCloseAppSend(familyAirAppointForm);
	     }   
	}
		 
}
