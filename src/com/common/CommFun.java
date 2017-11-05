package com.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.agile.api.APIException;
import com.agile.api.ChangeConstants;
import com.agile.api.DataTypeConstants;
import com.agile.api.IAdmin;
import com.agile.api.IAgileClass;
import com.agile.api.IAgileList;
import com.agile.api.IAgileSession;
import com.agile.api.IAutoNumber;
import com.agile.api.ICell;
import com.agile.api.IChange;
import com.agile.api.IDataObject;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.IQualityChangeRequest;
import com.agile.api.IRow;
import com.agile.api.IServiceRequest;
import com.agile.api.IStateful;
import com.agile.api.IStatus;
import com.agile.api.ITable;
import com.agile.api.IUser;
import com.agile.api.ItemConstants;
import com.agile.api.NodeConstants;
import com.agile.api.QualityChangeRequestConstants;
import com.agile.api.ServiceRequestConstants;
import com.agile.api.UserConstants;
import com.agile.px.ActionResult;





public class CommFun {

	public static String getNextVer(String oldRevision){
		String newRevision = "";
		String[] revNumbers = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z" };
		int size = revNumbers.length;
		
		//oldRevision = oldRevision.toUpperCase();
		//去除小数点
		oldRevision = oldRevision.replace(".","").toUpperCase();
		//去除数字部分
		Pattern pattern = Pattern.compile("\\D+");  
	    Matcher matcher = pattern.matcher(oldRevision);  
	    if  (matcher.find()) {  
	    	oldRevision = matcher.group(0); 
	    }else{
	    	oldRevision = "";
	    } 

		
		if (oldRevision.equals("")) {
			newRevision = "A";			
		}else{
			
			
			if (oldRevision.length() == 1) {
				for (int i = 0; i < size; i++) {
					if (oldRevision.equals(revNumbers[i])) {
						if ((i + 1) == size) {
							newRevision = "AA";
						} else {
							newRevision = revNumbers[i + 1];
						}
					}
				}
			}
			
			if (oldRevision.length() == 2) {
				for (int i = 0; i < size; i++) {
					if (oldRevision.endsWith(revNumbers[i])) {
						if ((i + 1) == size) {
							for (int num = 0; num < size; num++) {
								if (oldRevision.startsWith(revNumbers[num])) {
									if (num < size - 1) {
										newRevision = revNumbers[num + 1] + "A";
									}
									if (num == (size - 1)) {
										newRevision = oldRevision;
									}
								}
							}
						} else {
							newRevision = String.valueOf(oldRevision.charAt(0)) + revNumbers[i + 1];
						}
					}
				}
			}			
		}
		return newRevision;
	}
	
	//返回单列表选择值
	//type:1,返回值名称，2，返回值API名称
	
	public static String getSingleListInfo(ICell cell,int type) throws APIException{
		String value = "";
		if (cell != null){
			IAgileList list = (IAgileList) cell.getValue();
			IAgileList[] select = list.getSelection();		
			if (select.length == 1){
				switch(type) 
				{ 
					case 1: 
						value = select[0].getName();
					break; 

					case 2: 
						value = select[0].getAPIName();
					break;  

					default: 
					break; 
				} 				
			}
		}
		
		return value;
	}
	
	public static IDataObject getSingleListObject(ICell cell) throws APIException{
		IDataObject returnObj = null;
		if (cell != null){
			IAgileList list = (IAgileList) cell.getValue();
			IAgileList[] select = list.getSelection();		
			if (select.length == 1){
				returnObj = (IDataObject) select[0].getValue();			
			}
		}		
		return returnObj;
	}
	
	//判断字符串是否为全数字
	public static boolean isNumeric(String str){ 
		Pattern pattern = Pattern.compile("[0-9]*"); 
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false; 
		} 
		return true; 
	}

	public static String getConfigFile(String infilename){
		String os = System.getProperty("os.name").toUpperCase();
		String filename =  "/"+infilename;
		String classPath = System.getProperty("user.dir");
		if (os.startsWith("WIN")){
			filename  = classPath + filename;
		}else if (os.startsWith("LINUX")){
			filename = classPath + "/../integration/sdk/extensions"+filename;
		}
		
		return filename;
		
	}
	
	public static String getToday(String format){
		Calendar today = Calendar.getInstance(); 
		today.setTime(new Date());
		String os = System.getProperty("os.name").toUpperCase();
		if (os.startsWith("LINUX")){
			today.add(Calendar.HOUR, 8);
		}
		String str = "";
		if (format.toUpperCase().equals("DAY")){
			str = (new SimpleDateFormat("MM/dd/yyyy")).format(today.getTime());  
		}else if (format.toUpperCase().equals("TIME")){
			str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(today.getTime());
		}
 
		return str;
	}
	
}
