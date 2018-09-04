package com.agilean.lessons.timer.aop;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import com.agilean.lessons.controller.BaseController;
import com.agilean.lessons.timer.constant.HttpHeaderEnum;
import com.agilean.lessons.timer.exception.MyErrorException;
import com.agilean.lessons.timer.exception.MyWarnException;
import com.agilean.lessons.timer.exception.NeedSaveToLogException;
import com.agilean.lessons.timer.form.LogInfo;
import com.agilean.lessons.timer.util.HeaderUtil;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
@Aspect
public class ControllerAdvice {
	public static final ClassPool pool = ClassPool.getDefault(); 
	
	//private  LogInfoService logInfoService;
	@Around("bean(*Controller)")
	public Object catchControllerException(ProceedingJoinPoint point){
		System.out.println("---apo interceptor launch---"+point.getThis().getClass());
		Log logger = null;
		try{
			logger = LogFactory.getLog(point.getThis().getClass());
			return point.proceed();
		}catch(MyWarnException e){
			if(logger.isWarnEnabled()){
				StringBuilder sb = new StringBuilder(e.getClass().toString());
				sb.append(":").append(e.getMessage()).append("\n");
				StackTraceElement[] stes = e.getStackTrace();
				for(StackTraceElement ste:stes){
					if(ste.getClassName().contains("midea")){
						sb.append("\t").append(ste.getClassName()).append(" - ").append(ste.getLineNumber()).append("\n");
					}
				}
				logger.warn(sb.toString());
			}
			return new ResponseEntity<String>(e.getMessage(),HeaderUtil.getHeader(HttpHeaderEnum.JSON), HttpStatus.OK);
		}catch (MyErrorException e) {
			if(logger.isErrorEnabled()){
				StringBuilder sb = new StringBuilder(e.getClass().toString());
				sb.append(":").append(e.getMessage()).append("\n");
				StackTraceElement[] stes = e.getStackTrace();
				for(StackTraceElement ste:stes){
					sb.append("\t").append(ste.getClassName()).append(" - ").append(ste.getLineNumber()).append("\n");
				}
				logger.error(sb.toString());
			}
			return new ResponseEntity<String>(e.getMessage(),HeaderUtil.getHeader(HttpHeaderEnum.JSON), HttpStatus.OK);
		}catch (NeedSaveToLogException e) 
		{
			 /*LogInfo logInfo= getLogInfo(logger, point,e);
			 if(logInfo!=null)
			 {
				 try
				 {
					logInfoService.saveLogInfo(logInfo);
				 }catch(Exception mongoexception)
				 {
					
				 }
			 }   */
			 
			return new ResponseEntity<String>(e.getMessage(),HeaderUtil.getHeader(HttpHeaderEnum.JSON), HttpStatus.OK);
		}
		catch(ConnectTimeoutException e)
		{
			String message="{\"errCode\":\"10039\",\"errMsg\":\"http连接超时\"}";
			return new ResponseEntity<String>(message,HeaderUtil.getHeader(HttpHeaderEnum.JSON), HttpStatus.OK);
		
		}catch(NoRouteToHostException e)
		{
			String message="{\"errCode\":\"10039\",\"errMsg\":\"http连接超时\"}";
			return new ResponseEntity<String>(message,HeaderUtil.getHeader(HttpHeaderEnum.JSON), HttpStatus.OK);
		}catch(HttpResponseException e)
		{
			String message="{\"errCode\":\"10041\",\"errMsg\":\"调用外部http返回状态码异常\"}";
			return new ResponseEntity<String>(message,HeaderUtil.getHeader(HttpHeaderEnum.JSON), HttpStatus.OK);
		}
		catch(ClientProtocolException e)
		{
			String message="{\"errCode\":\"10042\",\"errMsg\":\"调用外部http请求无返回内容异常\"}";
			return new ResponseEntity<String>(message,HeaderUtil.getHeader(HttpHeaderEnum.JSON), HttpStatus.OK);
		}
		catch(SocketTimeoutException e)
		{
			String message="{\"errCode\":\"10039\",\"errMsg\":\"外部http连接或者读写超时\"}";
			return new ResponseEntity<String>(message,HeaderUtil.getHeader(HttpHeaderEnum.JSON), HttpStatus.OK);
		}
		catch (Throwable e) 
		{
			StringBuilder sb = new StringBuilder(e.getClass().toString());
			sb.append(":").append(e.getMessage()).append("\n");
			StackTraceElement[] stes = e.getStackTrace();
			for(StackTraceElement ste:stes){
				sb.append("\t").append(ste.getClassName()).append(" - ").append(ste.getLineNumber()).append("\n");
			}
			logger.error(sb.toString());
			
			return new ResponseEntity<String>("{\"errCode\":9901,\"errMsg\":\"未知错误\"}",HeaderUtil.getHeader(HttpHeaderEnum.JSON), HttpStatus.OK);
		}
	}
	

    private LogInfo getLogInfo(Log logger, ProceedingJoinPoint point,NeedSaveToLogException e){
		String className = point.getSignature().getDeclaringTypeName();
		String methodName = point.getSignature().getName();
		BaseController bc = (BaseController)point.getThis();
		String reqUrl=bc.request.getRequestURL().toString();
		String reqMethod=bc.request.getMethod();
		String parameters=bc.request.getQueryString();
		
		if(parameters!=null&&!parameters.equals(""))
		{
			reqUrl="?"+parameters;
		}
		LogInfo logInfo=new LogInfo();
		StringBuffer reqSb=new StringBuffer();
		String[] paramNames = getParamNames(logger, className, methodName);
		Object[] args = point.getArgs();
		if(args!=null&&args.length>0)
		{
		  for(int i = 0; i < paramNames.length; i++)
		  {
			  reqSb.append(paramNames[i]).append(":").append(args[i]).append("\n");
		  }
			reqSb.append(point.getArgs()[0]);
			
		}
		logInfo.setErrMsg(e.getMessage());
		String reqBody= reqSb.toString();
		logInfo.setReqBody(reqBody);
		logInfo.setReqMethod(reqMethod);
		logInfo.setReqTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		logInfo.setReqUrl(reqUrl);
		logInfo.setReqLogId("");
		return logInfo;
	}
	
	private RequestMapping getPath(Log logger, String className,String methodName){
		try{
	        Class<?> cls = Class.forName(className);
	        
		}catch(Throwable e){
			logger.error("Javassist获取方法参数名出错", e);
		}
		return null;
	}
	
	private String[] getParamNames(Log logger, String className,String methodName){
		String[] paramNames = null;
		try{
	        CtClass cc = pool.get(className);  
	        CtMethod cm = cc.getDeclaredMethod(methodName);  
	        MethodInfo methodInfo = cm.getMethodInfo();  
	        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
	        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);  
	        if (attr == null) {  
	        	logger.error("Javassist获取方法参数名,LocalVariableAttribute为空");
	            return null;
	        }  
	        paramNames = new String[cm.getParameterTypes().length];  
	        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
	        for (int i = 0; i < paramNames.length; i++){
	            paramNames[i] = attr.variableName(i + pos);
	        }
		}catch(Throwable e){
			logger.error("Javassist获取方法参数名出错", e);
		}
		return paramNames;
	}
	 
	
	
}
