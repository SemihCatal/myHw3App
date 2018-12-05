package com.mycompany.app;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;


public class App {
	public static void main(String[] args) {
        port(getHerokuAssignedPort());

        get("/", (req, res) -> "Semih CATAL 161101011 Bil481 Hw3");

        post("/compute", (req, res) -> {
            // System.out.println(req.queryParams("input1"));
            // System.out.println(req.queryParams("input2"));
        	App app = new App();
        	 App.UserHandler userhandler = app.new UserHandler();
        	try {
                File inputFile = new File("EEAS.xml");
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                saxParser.parse(inputFile, userhandler); 
            } catch (Exception e) {
                e.printStackTrace();
             }
            
			String fname = req.queryParams("input1").replaceAll("\\s", "");
            String lname = req.queryParams("input2").replaceAll("\\s", "");
            
            String result= userhandler.searchList(fname,lname);
           

            Map map = new HashMap();
            map.put("result", result);
            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());

        get("/compute", (rq, rs) -> {
            Map map = new HashMap();
            map.put("result", "not computed yet!");
            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());
    }
	
	class UserHandler extends DefaultHandler {
		private Entity entity;
		private ArrayList<Entity> entList = new ArrayList<>();
		String temp;

		 boolean bFirstName = false;
		  boolean bLastName = false;
		  

		   @Override
		   public void startElement(String uri, 
		   String localName, String qName, Attributes attributes) throws SAXException {

		      if (qName.equalsIgnoreCase("name")) {
		    	  entity = new Entity();
		         String entity_ID = attributes.getValue("Entity_id");
		         entity.setEntity_id(entity_ID);
		         //System.out.println("EntityID : " + entity_ID);
		      } else if (qName.equalsIgnoreCase("firstname")) {
		         bFirstName = true;
		      } else if (qName.equalsIgnoreCase("lastname")) {
		         bLastName = true;
		      }
		      
		   }

		   @Override
		   public void endElement(String uri, 
		   String localName, String qName) throws SAXException {
		      if (qName.equalsIgnoreCase("name")) {
		         //System.out.println("End Element :" + qName);
		         entList.add(entity);
		      }
		   }

		   @Override
		   public void characters(char ch[], int start, int length) throws SAXException {
		      if (bFirstName) {
		    	  temp = new String(ch, start, length);
		    	  entity.setFname(temp);
		          bFirstName = false;
		      } else if (bLastName) {
		    	  temp=new String(ch, start, length);
		          entity.setLname(temp);
		          bLastName = false;
		      } 
		      
		      
		   }
		   
		   public String searchList(String fname,String lname) {
			StringBuilder sb = new StringBuilder();
				Iterator<Entity> it = entList.iterator();
				while(it.hasNext()) {
					Entity entity = it.next();
					if(entity.getFname().equals(fname)&&(lname.length()==0)&&(fname.length()!=0)) {
						sb.append("Entity_ID: "+entity.getEntity_id());
						sb.append("\nFirst Name: "+entity.getFname());
						sb.append("\nLast Name: "+ entity.getLname()+"\n\n");
					  }
					  else if (entity.getLname().equals(lname)&&(lname.length()!=0)&&fname.length()==0){
						 sb.append("Entity_ID: "+entity.getEntity_id());
						 sb.append("\nFirst Name: "+entity.getFname());
						 sb.append("\nLast Name: "+ entity.getLname()+"\n\n");
					  }
					  else if(entity.getFname().equals(fname)&&entity.getLname().equals(lname)&&
									  (fname.length()!=0)&&(lname.length()!=0)){
						 sb.append("Entity_ID: "+entity.getEntity_id());
						 sb.append("\nFirst Name: "+entity.getFname());
						 sb.append("\nLast Name: "+ entity.getLname()+"\n\n");
					  }
				}
				return sb.toString();
		}
	
	
    
}

	
	
	static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; // return default port if heroku-port isn't set (i.e. on localhost)
    }
	
}

