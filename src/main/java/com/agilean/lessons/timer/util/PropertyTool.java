package com.agilean.lessons.timer.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class PropertyTool 
{
    public static String getPropertyValue(String key)
    {
    	Locale locale = Locale.getDefault();   
        ResourceBundle localResource = ResourceBundle.getBundle("conn", locale);   
        String value = localResource.getString(key);   
        System.out.println(key+"=" + value);  
        return value;

    	
    }
    
    
    public static void main(String[] args)
    {
    	getPropertyValue("cloud.service.address");
    }
}
