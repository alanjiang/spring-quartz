package com.agilean.test.jmockit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.agilean.lessons.controller.FamilyAirAppointController;
import com.agilean.lessons.timer.form.BasePojo;
import com.agilean.lessons.timer.form.FamilyAirAppointForm;
import com.agilean.lessons.timer.service.TaskService;
import com.agilean.lessons.timer.util.JacksonTool;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
@RunWith(JMockit.class)
public class ControllerTestCase {
	    @Tested
	    private FamilyAirAppointController familyAirAppointController;
	
	    @Injectable
	    TaskService taskService;
	    @Injectable
	    SchedulerFactoryBean schedulerFactory;

	    @Test
	    public void testSetFamilySceneAppoint() throws Throwable {
	        new Expectations()
	        { 
	        	{
	        	 taskService.setTask(anyString, schedulerFactory);
	        	 BasePojo model=new  BasePojo();
	   	         model.setErrCode("0");
	   	         model.setErrMsg("success1");
	   	         String res=JacksonTool.fromObjectToJson(model);//{"errCode":"0","errMsg":"success"}
	             result = res;
	           } 
	      };

	     /* FamilyAirAppointForm form=new FamilyAirAppointForm();
  		  form.setApplianceId(10L);
	      form.setApplianceName("Air");
	      form.setApplianceType(2);
	      form.setErrCode("0");
	      form.setErrMsg("success");
	      form.setRepeat(2);
	      form.setStatus(2);
	      form.setSwitcher(2);
	      form.setTime("22:15");
	      form.setTimeZone(8);
	      form.setWeek(new int[] {1,2,3});
	      String body=JacksonTool.fromObjectToJson(form);*/
	      String body="{hhhh}";
	      ResponseEntity<String> result = familyAirAppointController.setFamilySceneAppoint(body);
	      System.out.println("***** 2="+result.getBody());
	      BasePojo model=new  BasePojo();
	      model.setErrCode("0");
	      model.setErrMsg("success1");
	      String res=JacksonTool.fromObjectToJson(model);
	      Assert.assertEquals(result.getBody(),res);
	      //assertThat(result.getBody(), is(res));
	      /*
	       * @RunWith(JMockit.class): 指定单元测试的执行类为JMockit.class;
             @Tested: 这个Annotate是指被测试类，在这个测试案例中我们要测试的是FamilyAirAppointController，所以我们给其打上这个标签;
             @Injectable: 这个Annotate可以将对象进行mock并自动关联到被测试类，而不需要通过其他文件类似spring的配置文件等来进行关联;
             @Expectations: mock对象 taskService的setTask方法，让其返回一个String对象;
	       * http://jmockit.github.io/
	       * 
	       * 
	       */
	      new Verifications() {
	    	  {
	    		  taskService.setTask(anyString, schedulerFactory);
	    		  times=0;
	    		  familyAirAppointController.setFamilySceneAppoint(anyString);
	    		  times=0;
	    		  
	    	  }
	    	  
	    	  
	      };
	    }
}
