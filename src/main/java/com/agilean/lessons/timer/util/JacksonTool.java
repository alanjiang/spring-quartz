package com.agilean.lessons.timer.util;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.agilean.lessons.timer.form.BasePojo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonTool {
	private static final Log log = LogFactory.getLog(JacksonTool.class);
	private  final static ObjectMapper mapper = new ObjectMapper(); 
    private static Integer ARRAY_DESC=0;
    private static Integer ARRAY_ASC=1;
    public final static int YEAR=0;
    public final static int MONTH=1;
    public final static int DAY=2;
    static
    {
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
   
    private static final String preP = "GMT+";
	private static final String preS = "GMT";
	private static final String subS = ":00";

	public static Date[] getPainDates(String date) throws ParseException
	{
		SimpleDateFormat hmdfMMdd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String d2=date+" 00:00:00.000";
		String d3=date+" 23:59:59.999";
		Date beDate= hmdfMMdd.parse(d2);
		Date enDate= hmdfMMdd.parse(d3);
		Date[] dates=new Date[2];
		dates[0]=beDate;
		dates[1]=enDate;
		return dates;
	}
	
	public static Date[] getPainMDates(String date) throws ParseException
	{
		SimpleDateFormat hmdfMMdd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String day=date.substring(0, 7);
		System.out.println("---day from getPainMDates="+day);
		//Date d=dfYMD.parse(date);
		//String d1=addNDaysYMD(d,-1);
		String d2=day+"-01 00:00:00.000";
		String d3=day+"-31 23:59:59.999";
		Date beDate= hmdfMMdd.parse(d2);
		Date enDate= hmdfMMdd.parse(d3);
		Date[] dates=new Date[2];
		dates[0]=beDate;
		dates[1]=enDate;
		return dates;
	}
	public static String addNDaysYMD(Date date,int days )
	{
		SimpleDateFormat dfYMD = new SimpleDateFormat("yyyy-MM-dd");
		Calendar now =Calendar.getInstance();  
		now.setTime(date);  
		now.set(Calendar.DATE,now.get(Calendar.DATE)+days);  
		Date expireDate=now.getTime();
		return dfYMD.format(expireDate);
	}
	
	public static String getYMD( )
	{
	
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	public static Date[] getUTFDate(String date) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d=new SimpleDateFormat("yyyy-MM-dd").parse(date);
		String d1=addNDaysYMD(d,-1);
		String d2=d1+" 16:00:00";
		
		
		String d3=date+" 15:59:59";
		
		Date beDate=df.parse(d2);
		Date enDate=df.parse(d3);
		
		Date[] dates=new Date[2];
		dates[0]=beDate;
		dates[1]=enDate;
		
		return dates;
	}
	
	public static TimeZone getTimeZone(Integer t){
		if(null==t){
			t=8;
		}
		StringBuilder sb = null;
		if(t.intValue()>0){
			sb = new StringBuilder(preP);
		}else{
			sb = new StringBuilder(preS);
		}
		sb.append(t).append(subS);
		return TimeZone.getTimeZone(sb.toString());
	}
    
    
    
	/**
	 * @param args
	 */
    private static final Map<String,String> map=new HashMap<String,String>();
    public static void addMap()
    {
    	map.put("A", "B");
    }
    public static void addMap2()
    {
    	map.put("B", "C");
    }
	
	 
	  public static Object fromJsonToObject(String json,Class cls)
	 {
		try
		{
			mapper.setSerializationInclusion(Include.NON_NULL);
		  return mapper.readValue(json, cls); 
		 
		}catch(JsonMappingException e)
		{
			e.printStackTrace();	
			return null;
		}catch(JsonParseException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		} 
	 }
     public static String fromObjectToJson(Object obj)
     { 
    	 String s="";
    	 try
    	 {
    		 mapper.setSerializationInclusion(Include.NON_NULL);  
    	  s= mapper.writeValueAsString(obj);  
    	 }catch(Exception e)
    	 {
    		 e.printStackTrace();
    	 }
    	 return s;
     } 
     
    
     public static int[] bubbleSequence(int[] a,int sort)
     {
     	   if(a==null||a.length==0) return  a;
     	   for (int i = 0; i < a.length -1; i++)
     	   {    
     	               for(int j = 0 ;j < a.length - i - 1; j++)
     	               {  
     	            	   if(sort==ARRAY_DESC)
     	            	   {
     	                    if(a[j] < a[j + 1])
     	                    {    //把小的值交换到后面
     	                        int temp = a[j];
     	                        a[j] = a[j + 1];
     	                        a[j + 1] = temp;
     	                    }
     	            	   }else
     	            	   {
     	            		   if(a[j]>a[j + 1])
     		                    {
     		                        int temp = a[j];
     		                        a[j] = a[j + 1];
     		                        a[j + 1] = temp;
     		                    }
     	            	   }
     	               }            
     	       }
     	   return a;
     }  
     
     
     
     /*
      * 1:sunday 7:saturday
      * 
      * 0 25 7 ? MON,TUE: mon and tue,7:25 trigger
      */
     public final static String CRON_PREFIX="0 ";//second
     public static String generateCronExpression(int[] weeks,String time)
     {   
    	  weeks=bubbleSequence(weeks,ARRAY_ASC);
    	  StringBuffer sb=new StringBuffer();
    	  String[] ts=time.split("\\:");
    	  String hour=ts[0];
    	  if(hour.length()==2 && hour.startsWith("0"))
    	  {
    		  hour=hour.substring(1);
    	  }
    	  String minute=ts[1];
    	  if(minute.length()==2 && minute.startsWith("0"))
    	  {
    		  minute=minute.substring(1);
    	  }
    	  sb.append(CRON_PREFIX).append(minute+" "+hour+" ? * ");
    	  StringBuffer WEEKS=new StringBuffer();
    	  for(int week:weeks)
    	  {
    		 String WEEK=getWeek(weeks,week);
    		 WEEKS.append(WEEK+",");
    	  }
    	  String w=WEEKS.toString().substring(0,WEEKS.toString().length()-1);
    	  System.out.println("--w="+w);
    	  if(w.startsWith("SUN,")) w=w.substring(4)+",SUN";
    	  sb.append(w);
    	  
    	 return sb.toString();
     }
     
     public static String getWeek(int[] weeks,int value)
     {
    	  String w="";
    	 for(int week:weeks)
    	 {
    		 if(week==value)
    		 {
    			 switch(week)
    			 {
    			  case 7:
    				  w="SUN";	
    			      break;
    			  case 1:
    				  w="MON";	
        			  break;
    			  case 2:
    				  w="TUE";	
        			  break;
    			  case 3:
    				  w="WED";	
        			  break;
    			  case 4:
    				  w="THU";	
        			  break;
    			  case 5:
    				  w="FRI";	
        			  break;
    			  case 6:
    				  w="SAT";	
        			  break;
        			default:
        			//
    			 } 
    		 }
    	 }
    	 return  w;
     }
     
   
    public static Set<Date> getTiggerTimes(int[] weeks,Integer zone,String time )
    {
    	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 TimeZone timeZone=getTimeZone(zone);
    	 Set<Date> dates=new HashSet<Date>();
    	 String[] ts=time.split("\\:");
    	 Integer hour=Integer.parseInt(ts[0]);
    	 Integer minute=Integer.parseInt(ts[1]);
    	 
    	 Calendar calendar = Calendar.getInstance(timeZone);
    	 calendar.setTime(new Date());
    	 log.info("---calendar.setTime="+df.format( calendar.getTime()));
    	 Integer year;
    	 Integer month;
    	 Integer day;
    	 //Calendar.DAY_OF_MONTH
    	 for(int week:weeks)
    	 {
    		 switch(week)
    		 {
    		 case 1:
    			 
    			 calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    			 year=calendar.get(Calendar.YEAR);
    	 		 month=calendar.get(Calendar.MONTH);
    	 		 day= calendar.get(Calendar.DAY_OF_MONTH);
    	 		 calendar.set(year, month, day, hour, minute, 0);
    	 		 Date d1=calendar.getTime();
    			 dates.add(d1);
    			 log.info("---1="+df.format( d1));
   			  break;
   			  case 2:
   				calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
   				year=calendar.get(Calendar.YEAR);
   	 		    month=calendar.get(Calendar.MONTH);
   	 		    day= calendar.get(Calendar.DAY_OF_MONTH);
   	 		    calendar.set(year, month, day, hour, minute, 0);
   	 		     Date d2=calendar.getTime();
			     dates.add(d2);
			     log.info("---2="+df.format( d2));
       		  break;
   			  case 3:
   				calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
   				year=calendar.get(Calendar.YEAR);
   	 		    month=calendar.get(Calendar.MONTH);
   	 		    day= calendar.get(Calendar.DAY_OF_MONTH);
   	 		    calendar.set(year, month, day, hour, minute, 0);
   	 		    Date d3=calendar.getTime();
		        dates.add(d3);
		        log.info("---3="+df.format( d3));
       			break;
   			  case 4:
   				calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
   				year=calendar.get(Calendar.YEAR);
   	 		    month=calendar.get(Calendar.MONTH);
   	 		    day= calendar.get(Calendar.DAY_OF_MONTH);
   	 		    calendar.set(year, month, day, hour, minute, 0);
   	 		    Date d4=calendar.getTime();
	            dates.add(d4);
	            log.info("---4="+df.format( d4));
       			break;
   			  case 5:
   				calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
   				year=calendar.get(Calendar.YEAR);
   	 		    month=calendar.get(Calendar.MONTH);
   	 		    day= calendar.get(Calendar.DAY_OF_MONTH);
   	 		    calendar.set(year, month, day, hour, minute, 0);
   	 		    Date d5=calendar.getTime();
	            dates.add(d5);
	            log.info("---5="+df.format( d5));
       			break;
   			  case 6:
   				calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
   				year=calendar.get(Calendar.YEAR);
   	 		    month=calendar.get(Calendar.MONTH);
   	 		    day= calendar.get(Calendar.DAY_OF_MONTH);
   	 		    calendar.set(year, month, day, hour, minute, 0);
   	 		    Date d6=calendar.getTime();
                dates.add(d6);
                log.info("---6="+df.format( d6));
       			break;
   			  case 7:
   				calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
   				year=calendar.get(Calendar.YEAR);
   	 		    month=calendar.get(Calendar.MONTH);
   	 		    day= calendar.get(Calendar.DAY_OF_MONTH);
   	 		    calendar.set(year, month, day, hour, minute, 0);
	 		    Date d7=calendar.getTime();
                dates.add(d7);
               log.info("---7="+df.format( d7));
       			break;
       			default:
       			//
    		   
    		 }
 		    
    	 }
    	 
    	 return dates;
 		 
    }
    
    /*
     * @zone: time zone
     * @base on date, add or sub days
     * [-12,12]
     */
    public static Date addNDays(Date date,int days,Integer zone )
	{
    	TimeZone timeZone=getTimeZone(zone);
		Calendar calendar =Calendar.getInstance(timeZone);  
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+days);  
		return calendar.getTime();
	}
    public static <T extends BasePojo> List<T> fromJsonArrayToCollection(String json,Class<T> cls)
	 {
		try
		{
		  JavaType javaType = getCollectionType(List.class, cls); 
		  List<T> lst =  (List<T>)mapper.readValue(json, javaType); 
		  return lst; 
		}catch(JsonMappingException e)
		{
			e.printStackTrace();	
			return null;
		}catch(JsonParseException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		} 
	 }
    public static <T extends BasePojo> List<T> fromJsonToList(String json,Class<T> clazz) throws Exception
    {  
    	         JavaType javaType = getCollectionType(ArrayList.class,clazz ); 
    	          List<T> lst =  (List<T>)mapper.readValue(json, javaType); 
    	          return lst;
    } 
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
            return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
    } 
    
    
    
    
    public static void checkChineseName(String msg) {
    	
    	
    	String regex="[\\s]?(王|李|张|刘|陈|杨|黄|赵|吴|周|徐|孙|马|朱|胡|郭|何|高|林|罗|郑|梁|谢|宋|唐|许|韩|冯|邓|曹|彭|曾|肖|田|董|袁|潘|于|蒋|蔡|余|杜|叶|程|苏|魏|吕|丁|任|沈|姚|卢|姜|崔|钟|谭|陆|汪|范|金|石|廖|贾|夏|韦|傅|方|白|邹|孟|熊|秦|邱|江|尹|薛|闫|段|雷|侯|龙|史|黎|贺|顾|毛|郝|龚|邵|万|钱|覃|武|戴|孔|汤|庞|樊|兰|殷|施|陶|洪|翟|安|颜|倪|严|牛|温|芦|季|俞|章|鲁|葛|伍|申|尤|毕|聂|柴|焦|向|柳|邢|岳|齐|沿|梅|莫|庄|辛|管|祝|左|涂|谷|祁|时|舒|耿|牟|卜|路|詹|关|苗|凌|费|纪|靳|盛|童|欧|甄|项|曲|成|游|阳|裴|席|卫|查|屈|鲍|位|覃|霍|翁|隋|植|甘|景|薄|单|包|司|柏|宁|柯|阮|桂|闵|欧阳|解|强|丛|华|车|冉|房|边|辜|吉|饶|刁|瞿|戚|丘|古|米|池|滕|晋|苑|邬|臧|畅|宫|来|嵺|苟|全|褚|廉|简|娄|盖|符|奚|木|穆|党|燕|郎|邸|冀|谈|姬|屠|连|郜|晏|栾|郁|商|蒙|计|喻|揭|窦|迟|宇|敖|糜|鄢|冷|卓|花|艾|蓝|都|巩|稽|井|练|仲|乐|虞|卞|封|竺|冼|原|官|衣|楚|佟|栗|匡|宗|应|台|巫|鞠|僧|桑|荆|谌|银|扬|明|沙|薄|伏|岑|习|胥|保|和|蔺|水|云|昌|凤|酆|常|皮|康|元|平|萧|湛|禹|无|贝|茅|麻|危|骆|支|咎|经|裘|缪|干|宣|贲|杭|诸|钮|嵇|滑|荣|荀|羊|於|惠|家|芮|羿|储|汲|邴|松|富|乌|巴|弓|牧|隗|山|宓|蓬|郗|班|仰|秋|伊|仇|暴|钭|厉|戎|祖|束|幸|韶|蓟|印|宿|怀|蒲|鄂|索|咸|籍|赖|乔|阴|能|苍|双|闻|莘|贡|逢|扶|堵|宰|郦|雍|却|璩|濮|寿|通|扈|郏|浦|尚|农|别|阎|充|慕|茹|宦|鱼|容|易|慎|戈|庚|终|暨|居|衡|步|满|弘|国|文|寇|广|禄|阙|东|殴|殳|沃|利|蔚|越|夔|隆|师|厍|晃|勾|融|訾|阚|那|空|毋|乜|养|须|丰|巢|蒯|相|后|红|权逯|盖益|桓|公|万俟|司马|上官|夏侯|诸葛|闻人|东方|赫连|皇甫|尉迟|公羊|澹台|公冶|宗政|濮阳|淳于|单于|太叔|申屠|公孙|仲孙|轩辕|令狐|钟离|宇文|长孙|慕容|鲜于|闾丘|司徒|司空|亓官|司寇|仉|督|子车|颛孙|端木|巫马|公西|漆雕|乐正|壤驷|公良|拓跋|夹谷|宰父|谷粱|法|汝|钦|段干|百里|东郭|南门|呼延|归海|羊舌|微生|帅|缑|亢|况|郈|琴|梁丘|左丘|东门|西门|佘|佴|伯|赏|南宫|墨|哈|谯|笪|年|爱|仝|代)\\s*[\u4e00-\u9fa5]{1,3}";
    	Pattern p=Pattern.compile(regex);
        Matcher matcher=p.matcher(msg);
        
        while(matcher.find()) {
        	System.out.println(">>>>"+matcher.group());
        }
    }
    
    public static void main(String[] args)
    {
    	Long time= 1537929675541L;
    	Date date=new Date(time);
    	SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	System.out.println(f.format(date));
    }
    
}