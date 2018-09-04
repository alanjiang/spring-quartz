package com.agilean.lessons.timer.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.impl.StdScheduler;
import org.quartz.listeners.TriggerListenerSupport;

public class AirConStatisTriggerListener extends TriggerListenerSupport
{
	
	
	protected final transient Log log = LogFactory.getLog(getClass());
	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) 
	{
		// TODO Auto-generated method stub
		super.triggerFired(trigger, context);
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		// TODO Auto-generated method stub
		return super.vetoJobExecution(trigger, context);
	}

	@Override
	public void triggerMisfired(Trigger trigger)
	{
		super.triggerMisfired(trigger);
		
		
		
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		// TODO Auto-generated method stub
		super.triggerComplete(trigger, context, triggerInstructionCode);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
