package com.agilean.lessons.tools.dom4j;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class DomParser 
{ 
	/*
	 * ELEMENT_NODE= 1;
     * The node is an <code>Attr</code>.
     * ATTRIBUTE_NODE= 2;
     * The node is a <code>Text</code> node.
     * TEXT_NODE= 3;
	 */
	
	public static Object[] extractSQL(String filePath,String jobValue,String typeValue)
	{
	    Object[] o=new Object[2];
	    String sql="";
	    String  repname="";
		try 
		{     
			DocumentBuilderFactory domfac=DocumentBuilderFactory.newInstance();
			DocumentBuilder dombuilder=domfac.newDocumentBuilder();
			InputStream is=new FileInputStream(filePath);
			Document doc=dombuilder.parse(is);
		    Element root=doc.getDocumentElement();
		    NodeList sqls=root.getChildNodes();
		    System.out.println("---sqls.getLength()="+sqls.getLength());
			if(sqls!=null)
			{
				for(int i=0;i<sqls.getLength();i++)
				{
					  System.out.println("i="+i);
				     Node sqlNode=sqls.item(i);
				     System.out.println("---sqlNode.getNodeType()="+sqlNode.getNodeType());
				     if(sqlNode.getNodeType()==1)
			         {
						String job=sqlNode.getAttributes().getNamedItem("job").getNodeValue();
			            String type=sqlNode.getAttributes().getNamedItem("type").getNodeValue();
			            repname=sqlNode.getAttributes().getNamedItem("repname").getNodeValue();
			            System.out.println("---type="+type+",job="+job+",repname="+repname);
			            if(job.equals(jobValue)&&type.equals(typeValue))
			            {
			               for(Node node=sqlNode.getFirstChild();node!=null;node=node.getNextSibling())
			               {
			            	   System.out.println("---sqlNode.getNodeType()="+node.getNodeType()+",node.getNodeName()="+node.getNodeName());
			    	        if(node.getNodeType()==1)
			                {
			    		      if(node.getNodeName().equals("clause"))
			    		      {
			    			     //String clause=node.getNodeValue();
			    			    // String clause=node.getFirstChild().getNodeValue();
			    		    	String clause=node.getTextContent();
			    			    System.out.println("----clause="+clause);
			    			    sql=clause;
			    			    break;
                               }
			    		     
			    		       }
			    	         }//end of for
			              }//end of if if(job.equals(jobValue)&&type.equals(typeValue))
			            
			              break;
			            
			           }//end of if(sqlNode.getNodeType()==Node.ELEMENT_NODE)
				      
			      }//end for 
		    }
			
          } catch (ParserConfigurationException e) 
		  {
			 e.printStackTrace();
			} catch (FileNotFoundException e) 
			{
			
			e.printStackTrace();
			
		   } catch (SAXException e) 
		   {
			
			e.printStackTrace();
			
		   } catch (IOException e) 
		   {
			e.printStackTrace();
		   }	
		   o[0]=sql;
		   o[1]=repname;
		   return o;
	}
	public static void main(String[] args) throws Exception
	{
		String filePath="D:/Report/MicrofinanceReport/WebContent/SQLXML/SQL.xml";
		String jobValue="JR-M130503-001";
		String typeValue="01";
		Object[] o=extractSQL(filePath,jobValue,typeValue);
		System.out.println("sql="+(String)o[0]);
		 
		System.out.println("repname="+(String)o[1]);
	}
}