package com.hazy.plmwebpx.model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.agile.api.APIException;
import com.agile.api.AgileSessionFactory;
import com.agile.api.IAgileSession;
import com.common.CommFun;

public class AgileConnect {
	IAgileSession session = null;
	public static boolean connectStatus = false;	
	public static StringBuffer errorhistory = new StringBuffer();
	public static String reviewNodeAPI = "";
	public static String db_path = "";
	public static String db_user ="";
	public static String db_pwd ="";
			
	public void connect(String filename){		
		//String conFilename = CommFun.getConfigFile(filename);
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filename));
			ResourceBundle resourceBundle = new PropertyResourceBundle(in);
			String AgileURL = resourceBundle.getString("agileurl");
			String USERNAME = resourceBundle.getString("agileuser"); 
			String PASSWORD = resourceBundle.getString("agilepwd");
			reviewNodeAPI = resourceBundle.getString("reviewNodeAPI");
			db_path=resourceBundle.getString("db_path");
			db_user=resourceBundle.getString("db_user");
			db_pwd=resourceBundle.getString("db_pwd");
			
			HashMap<Object,Object> params = new HashMap<Object,Object>();

		    AgileSessionFactory instance = AgileSessionFactory.getInstance(AgileURL);
		    params.put(AgileSessionFactory.USERNAME, USERNAME);
		    params.put(AgileSessionFactory.PASSWORD, PASSWORD);
		    session = instance.createSession(params);
		    connectStatus = true;
			
		    //System.out.println("Connect to Agile server Successfully!!!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(e.toString().contains("MissingResourceException")){				
				errorhistory.append("配置文件相关参数错误，请联系管理员！");
			}else if (e.toString().contains("FileNotFoundException")){
				errorhistory.append("未找到配置文件，请检查配置文件！");
			}else{
				System.out.println(e.toString());
				//history.append(e.getMessage());
				e.printStackTrace();
				errorhistory.append(e.toString());
			}			
		}
	}
	
	AgileConnect(String filename){
		this.connect(filename);
	}
	
	
}
