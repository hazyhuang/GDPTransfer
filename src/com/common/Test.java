package com.common;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.agile.api.APIException;
import com.agile.api.ChangeConstants;
import com.agile.api.CommonConstants;
import com.agile.api.DataTypeConstants;
import com.agile.api.FileFolderConstants;
import com.agile.api.IAgileClass;
import com.agile.api.IAgileList;
import com.agile.api.IAgileSession;
import com.agile.api.IAttachmentFile;
import com.agile.api.IAttribute;
import com.agile.api.ICell;
import com.agile.api.IChange;
import com.agile.api.IFileFolder;
import com.agile.api.IFolder;
import com.agile.api.IItem;
import com.agile.api.IProductReport;
import com.agile.api.IQualityChangeRequest;
import com.agile.api.IQuery;
import com.agile.api.IRow;
import com.agile.api.IServiceRequest;
import com.agile.api.IStatus;
import com.agile.api.ISupplier;
import com.agile.api.ITable;
import com.agile.api.ITwoWayIterator;
import com.agile.api.IUser;
import com.agile.api.IUserGroup;
import com.agile.api.ItemConstants;
import com.agile.api.UserGroupConstants;
import com.sun.org.apache.xerces.internal.util.Status;

public class Test {
	
	public static void main(String[] args) throws APIException, ParseException, ClassNotFoundException, IOException {
		String URL = "http://agileplm:7001/Agile";
		String USERNAME = "solon";
		String PASSWORD = "1";
		
		IAgileSession session = Login.connect(URL, USERNAME, PASSWORD);
		//IUser user = session.getCurrentUser();
		
		
		IChange change = (IChange) session.getObject(IChange.OBJECT_TYPE, "ECN_00000002");		
		System.out.println(change.getObjectId());
		
		IItem item = (IItem) session.getObject(IItem.OBJECT_TYPE, "CPFMEA0000001");		
		System.out.println(item.getObjectId());
		
/*		ITable table = change.getTable(ChangeConstants.TABLE_AFFECTEDITEMS);
		Iterator itr = table.iterator();
		
		if (table.size() > 0){
			while(itr.hasNext()){
				IRow row = (IRow)itr.next();
				System.out.println("ROW:"+row.getObjectId());
			}
			
		}*/
		
		//IItem doc = (IItem) session.getObject(IItem.OBJECT_TYPE, "CPFMEA0000001");
		
/*		//删除华为变更单
		IAgileClass changeClass = session.getAdminInstance().getAgileClass("DIC_HW");
		IQuery query = (IQuery)session.createObject(IQuery.OBJECT_TYPE,changeClass);
		query.setCaseSensitive(false);
		ITable table = query.execute();
		System.out.println("Change Num:"+table.size());
		Iterator itr = table.iterator();
		
		if (table.size() > 0){	
			session.disableAllWarnings();
			while(itr.hasNext()){
				IRow row = (IRow)itr.next();
				IChange change = (IChange) row.getReferent();
				if (change.getStatus().getAPIName().equals("Released")){					
					IStatus[] statusList = change.getNextStatuses();
					IStatus nestStatus = null;
					for (int i=0;i<statusList.length;i++){
						if (statusList[i].getAPIName().equals("Cancel")){
							nestStatus = statusList[i];
							break;
						}
					}
					if (nestStatus != null){
						change.changeStatus(nestStatus, false, "Auto promote by PX !", false, false, null, null,null, null, false);
						System.out.println("Changed");
						change.delete();
					}
					
				}else if  (change.getStatus().getAPIName().equals("Pending")){
					change.delete();
				}				
			}
			session.enableAllWarnings();
		}
		
		//删除华为GDP文档
		IAgileClass docClass = session.getAdminInstance().getAgileClass("GDP");
		IQuery docQuery = (IQuery)session.createObject(IQuery.OBJECT_TYPE,docClass);
		query.setCaseSensitive(false);
		ITable docTable = docQuery.execute();
		System.out.println("Change Num:"+docTable.size());
		Iterator docItr = docTable.iterator();
		
		if (docTable.size() > 0){	
			session.disableAllWarnings();
			while(docItr.hasNext()){
				IRow row = (IRow)docItr.next();
				IItem doc = (IItem) row.getReferent();
				
				ITable reftable = doc.getRelationship();
				if (reftable.size() > 0){
					reftable.clear();
				}
				doc.delete();	
				System.out.println("deleted");
			}
			session.enableAllWarnings();
		}
		
		*/
		
		//String jarFilePath = Test.class.getProtectionDomain().getCodeSource().getLocation().getFile();  
		//System.out.println(jarFilePath);
		// URL Decoding  
		//jarFilePath = java.net.URLDecoder.decode(jarFilePath, "UTF-8");
		//System.out.println(System.getProperty("user.dir"));
		
		//String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		//System.out.println(System.getProperty("java.class.path"));
		

		//String path = 
		//System.out.println(new Test().getClass().getProtectionDomain().getCodeSource().getLocation());
		//String oldver = "A4";

		//System.out.println(CommFun.getNextVer(oldver));

	
		//calendar.setFirstDayOfWeek(2);
		//System.out.println(calendar.getFirstDayOfWeek());
		//String str = (new SimpleDateFormat("yyyy-MM-dd")).format(a.getTime());  
		//System.out.println(str); 
		//鑾峰彇浠婂ぉ鏃ユ湡
		//Calendar today = Calendar.getInstance(); 
		//today.set(2017,4,22); 
		
		/*
		int week = today.get(Calendar.DAY_OF_WEEK)-1;
		System.out.println(week);
		switch(week){
			case 3:
				today.add(Calendar.HOUR,120);
			break;
			case 4:
				today.add(Calendar.HOUR,120);
			break;
			case 5:
				today.add(Calendar.HOUR,120);
			break;
			case 6:
				today.add(Calendar.HOUR,96);
			break;			
			default:
				today.add(Calendar.HOUR,72);
			break;			
		}
		String str = (new SimpleDateFormat("yyyy-MM-dd")).format(today.getTime()); 
		//Date one = today.getTime();
		System.out.println(str);
		
		//鐩爣鏃ユ湡
		Calendar tag = Calendar.getInstance(); 
		tag.set(2017,4,29);
		str = (new SimpleDateFormat("yyyy-MM-dd")).format(tag.getTime());		
		//Date two = tag.getTime();
		//System.out.println(two);
		
		//System.out.println(one.getTime() > two.getTime());
		
		long  day=(today.getTime().getTime() - tag.getTime().getTime())/(24*60*60*1000);
		System.out.println(day);
		if (day == 0){
			//鍒囨崲鐘舵�
		}else if (day < 0){
			//鍙戦�閫氱煡
		}else{}
		
		/*
		Calendar Tocalendar = Calendar.getInstance();
		Tocalendar.add(Calendar.HOUR,72);

		
		
		 
		System.out.println(Tocalendar.DAY_OF_WEEK);
		*/
		//System.out.println(Tocalendar.compareTo(calendar));
		
		
	
		    // 鎴栬�鐢�Date 鏉ュ垵濮嬪寲 Calendar 瀵硅薄  
		     
		  
		    // setTime 绫讳技涓婇潰涓�  
		    // Date date = new Date();  
		    // calendar.setTime(date);  
		  
		  
		/*
		
		
		System.out.println(session.getCurrentUser());
		IFileFolder folder = (IFileFolder) session.getObject(IFileFolder.OBJECT_TYPE, "FOLDER13384");
		//InputStream stream = downloadFile(session,folder);
		String filename = "c:\\1.xml";
		try {
			saveFile(folder,filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		*/
		/*
		IItem item1 = (IItem) session.getObject(IItem.OBJECT_TYPE, "D00139");

		IItem item2 = (IItem) session.getObject(IItem.OBJECT_TYPE, "0WL.BD01.000001");

		IAttribute attrib1 = item1.getAgileClass().getAttribute(ItemConstants.ATT_PAGE_THREE_LIST01);
		IAttribute attrib2 = item2.getAgileClass().getAttribute(ItemConstants.ATT_PAGE_THREE_LIST01);
		
		
		System.out.println("1:"+attrib1.isCascadedList());
		System.out.println("2:"+attrib2.isCascadedList());
		String out = item1.getLargeTextValue(ItemConstants.ATT_PAGE_THREE_LARGETEXT01).toString();
		System.out.println(out);
		out = "AAA";
		 item1.setLargeTextValue(ItemConstants.ATT_PAGE_THREE_LARGETEXT01,out);
		//System.out.println("TYPE_SINGLELIST:"+DataTypeConstants.TYPE_SINGLELIST);
		//System.out.println("TYPE_MULTILIST:"+DataTypeConstants.TYPE_MULTILIST);
		/*
		IChange change = (IChange)session.getObject(IChange.OBJECT_TYPE, "DCN0000008");
		ITable affectTable = change.getTable(ChangeConstants.TABLE_AFFECTEDITEMS);
		Iterator itr = affectTable.iterator();
		while(itr.hasNext()){
			IRow row = (IRow) itr.next();
			IItem item  = (IItem) row.getReferent();
			ITable P2Table = item.getTable(ItemConstants.TABLE_REDLINEPAGETWO);
			Iterator it = P2Table.getTableIterator();
			IRow redPage2Row = (IRow)it.next();
			ICell cell = redPage2Row.getCell(CommonConstants.ATT_PAGE_TWO_TEXT15);
			System.out.println("old value, before update: " + cell.getOldValue());
			System.out.println("old value, before after: " + cell.getValue());
		}
		
		/*
		for (int i=1;i<65000;i++){
			String str = String.valueOf(i);
			IProductReport item = (IProductReport)session.getObject(IProblemReports  ISupplier.OBJECT_TYPE, str);
			if (item != null){
				//item.delete();
				item.undelete();
				System.out.println("del:"+item.getName());
			}
		}
		
		//AgileClass funClass = session.getAdminInstance().getAgileClass("FunctionalTeamSubClass");
		//锟斤拷询锟斤拷锟叫诧拷品锟竭癸拷锟斤拷小锟斤拷锟斤拷锟�
		
		
		

		for (int i=1;i<=80;i++){
			String str = String.format("%7d", i).replace(" ", "0");
			str = "QFB"+str;
			IQualityChangeRequest item = (IQualityChangeRequest)session.getObject(IQualityChangeRequest.OBJECT_TYPE, str);
		
			if (item != null){
				//item.delete();
				//System.out.println("del:"+item.getName());
			}
		}
		 
		IAgileClass itemClass = session.getAdminInstance().getAgileClass("Model");
		IQuery query = (IQuery)session.createObject(IQuery.OBJECT_TYPE,itemClass);
		query.setCaseSensitive(false);
		String queryStr = "";
		query.setCriteria(queryStr);
		ITable table = query.execute();
		System.out.println("table:"+table.size());
		Iterator itr = table.iterator();
		while(itr.hasNext()){
			IRow row = (IRow)itr.next();
			IItem newItem = (IItem)row.getReferent();
			String old = newItem.getValue(ItemConstants.ATT_TITLE_BLOCK_NUMBER).toString();
			old = old +"_锟斤拷锟斤拷BOM";
			System.out.println(old);
			newItem.setValue(ItemConstants.ATT_TITLE_BLOCK_NUMBER, old);
		}
		session.close();
		*/

		

		
	
	}
	
	public static void saveFile(IFileFolder fileFolder, String fileNameValue) throws APIException, IOException {
		// TODO Auto-generated method stub
			ITable fileTable = fileFolder.getTable(FileFolderConstants.TABLE_FILES);

			if (fileTable.size() > 0){
				ITwoWayIterator it = fileTable.getTableIterator();
				if (it.hasNext()) {
					IRow row = (IRow)it.next();
					InputStream is = null;
					is = ((IAttachmentFile)row).getFile();
				
					
					File file = new File(fileNameValue);
				    OutputStream os = new FileOutputStream(file);
				    		    
				    byte[] buf = new byte[4096];
					int amt;
					while ((amt = is.read(buf)) > 0) {
						os.write(buf, 0, amt);
					}
					is.close();
					os.close();	
				}
			}

	}
	
	
	
	public static InputStream downloadFile(IAgileSession session, IFileFolder folder) {
		InputStream stream = null;
		try {
			ITable fileTable = folder.getTable(FileFolderConstants.TABLE_FILES);

			ITwoWayIterator it = fileTable.getTableIterator();
			// Get the first attachment in the table
			if (it.hasNext()) {
				IRow row = (IRow) it.next();
				
				// Read the contents of the stream
				stream = ((IAttachmentFile) row).getFile();
				System.out.println("stream"+stream.available());
			} else {
				
			}
		} catch (APIException ex) {
			ex.printStackTrace();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stream;
	}
	
	public static void copyStreamToFile(InputStream in, File file) throws IOException {
		FileOutputStream out = new FileOutputStream(file);

		try {
			copyStreamToStream(in, out);
		} finally {
			out.close();
		}
	}
	
	public static void copyStreamToStream(InputStream in, OutputStream out) throws IOException {
		byte[] buf = new byte[4096];
		int amt;

		while ((amt = in.read(buf)) > 0) {
			out.write(buf, 0, amt);
		}
	}
	
}
