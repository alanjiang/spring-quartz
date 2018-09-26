package com.agilean.lessons.timer.util;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agilean.lessons.timer.constant.Constants;

public class SystemInitImpl implements SystemInit{
	 private static final Log log = LogFactory.getLog(SystemInitImpl.class);
	 private static SimpleDateFormat ymdFormat= new SimpleDateFormat("yyyy-MM-dd");
	public void init() 
	{
       System.out.println(">>>>>> System Init initial start <<<<<<<");
	}
	public void afterApplicationContextSet() throws Exception  
	{
		SystemPropertyPlaceholderConfigurer sppc = SpringContextUtil.getBean("sppc",SystemPropertyPlaceholderConfigurer.class);
		Properties p = sppc.getProperties();	
		if( containKeys(p,"control.server"))			
		{
			  Constants.CONTROL_SERVER=p.getProperty("control.server");
			  System.out.println("---The necessary properties have been initilized successfully----");
		}
		else
		{
			throw new Exception("---some necessary properties were not set in conn.properties");
		}
	
	}
	
	public void destroy()
	{
		 log.info("-----SystemInitImpl hase been destroyed----");
	}
	protected boolean containKeys(Properties p,String ...keys)
	{
		for(String key:keys)
		{
			if(!p.containsKey(key))
				return false;
		}
		return true;
	}
}
