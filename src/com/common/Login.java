package com.common;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import com.agile.api.APIException;
import com.agile.api.AgileSessionFactory;
import com.agile.api.IAgileSession;

public class Login {
	  public static IAgileSession session = null;

	  public static IAgileSession connect(String AgileURL, String USERNAME, String PASSWORD)
	  	{
	      HashMap<Object,Object> params = new HashMap<Object,Object>();
	      try {
	        AgileSessionFactory instance = AgileSessionFactory.getInstance(AgileURL);
	        params.put(AgileSessionFactory.USERNAME, USERNAME);
	        params.put(AgileSessionFactory.PASSWORD, PASSWORD);
	        session = instance.createSession(params);
	        System.out.println("Connect to Agile server Successfully!!!");
	      } catch (APIException e) {
	        e.printStackTrace();
	        System.out.println("Connect Agile failed");
	      }
	      return session;
	    }
	  
	  public static IAgileSession DefaultConnect()
	  {
		  ResourceBundle resourceBundle =ResourceBundle.getBundle("com.common.Configure",Locale.getDefault());
		  
		  
		  HashMap<Object,Object> params = new HashMap<Object,Object>();
	      try {
	    	  String AgileURL = resourceBundle.getString("AgileServerURL");
			  String USERNAME = resourceBundle.getString("UserId");
			  String PASSWORD = resourceBundle.getString("UserPWD");
	    	  
			  AgileSessionFactory instance = AgileSessionFactory.getInstance(AgileURL);
			  params.put(AgileSessionFactory.USERNAME, USERNAME);
			  params.put(AgileSessionFactory.PASSWORD, PASSWORD);
			  session = instance.createSession(params);
			  System.out.println("Connect to Agile server Successfully!!!");
	      } catch (APIException e) {
	        e.printStackTrace();
	        System.out.println("Connect Agile failed");
	      }
		  

		  
		  return session;
	  }
	  
}
