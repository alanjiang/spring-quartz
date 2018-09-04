package com.agilean.lessons.timer.service;

import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public interface TaskService 
{
  abstract String setTask(String familyAirAppointForm,SchedulerFactoryBean schedulerFactory) throws Exception;
  abstract String getTask(String familyAirAppointForm,SchedulerFactoryBean schedulerFactory) throws Exception;
}
