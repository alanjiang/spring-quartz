package com.agilean.lessons.timer.util;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class SystemPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	private Properties properties;
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		properties = props;
	}
	
	public Properties getProperties(){
		return properties;
	}

}
