package com.agilean.lessons.timer.constant;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
public class Constants
{
	public final static String APPOINT_JOB_GROUP="APPOINT_JOB_GROUP";
	public final static String APPOINT_JOB_GROUP_PREFIX="job_";
	public final static String APPOINT_TRIGGER_GROUP_PREFIX="trigger_";
	public final  static String JOB_GROUP_APPOINT="job_group_appoint";	
	public final  static String JOB_KEY_FAMILY_APPONIT="job_key_family_appoint";
	public final  static String TRIGGER_KEY_FAMILY_APPONIT="trigger_key_family_appoint";
	public static String CONTROL_SERVER="";
}
