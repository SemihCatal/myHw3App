package com.mycompany.app;



import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserDemo {
	
	

   public static void main(String[] args) {

      try {
         File inputFile = new File("EEAS.xml");
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
         UserHandler userhandler = new UserHandler();
         saxParser.parse(inputFile, userhandler); 
      } catch (Exception e) {
         e.printStackTrace();
      }
   }   
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
	   
	   public void readList() {
          
           Iterator<Entity> it = entList.iterator();
           while (it.hasNext()) {
                  System.out.println(it.next().toString());
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