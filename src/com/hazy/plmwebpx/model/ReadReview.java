package com.hazy.plmwebpx.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.agile.api.APIException;

import com.agile.api.ChangeConstants;
import com.agile.api.IAgileList;
import com.agile.api.IAgileSession;
import com.agile.api.ICell;
import com.agile.api.IChange;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.IUser;
import com.agile.api.IUserGroup;
import com.agile.api.UserConstants;
import com.agile.api.UserGroupConstants;
import com.common.Login;
import com.hazy.common.HazyUtil;



public class ReadReview{

	DbConnect db = new DbConnect();		
	/*
	public JSONObject loadReview(IChange change,IUser user){
		ChangeRecord changeRecode = new ChangeRecord();
		JSONObject jObj = new JSONObject();
		try{
			AgileConnect newConnect  = new AgileConnect("conf.properties");
			if (!newConnect.connectStatus){
				System.out.println("1");
				jObj.put("success", AgileConnect.connectStatus);
				jObj.put("msg", AgileConnect.errorhistory.toString());
				return jObj;
			}
			//检查当前节点状态
			String nodeAPI = change.getStatus().getAPIName();
			if (!nodeAPI.matches(AgileConnect.reviewNodeAPI)){
				System.out.println("2");
				jObj.put("success", "false");
				jObj.put("msg", "只允许在[文件转换]节点运行此功能！");
				return jObj;
			}
			
			//检查当前用户是否在审核人列表中			
			if (!this.checkRole(change, user)){
				jObj.put("success", "false");
				jObj.put("msg", "您不是审核用户，无权限执行该功能！");
				return jObj;
			}
			
			//检查受影响物件是否为空
			ITable table = change.getTable(ChangeConstants.TABLE_AFFECTEDITEMS);
			if (table.size() == 0){
				System.out.println("3");
				jObj.put("success", "false");
				jObj.put("msg","受影响物件中没有相关文档记录!");
				return jObj;
			}
				
			//连接数据库		
			db.getDbConnect(AgileConnect.db_path,AgileConnect.db_user,AgileConnect.db_pwd);
			if (!db.DbStatus){
				System.out.println("4");
				jObj.put("success", "false");
				jObj.put("msg",db.errorInfo);
				return jObj;
			}
			
			//获取当前用户的审核经理ID
			ArrayList<IUser> managerlist = this.getManagerList(change,user);			
			IUser managerUser = managerlist.get(0);
			
			//开始处理
			ArrayList<IUser> reviewUserList = new ArrayList<IUser>();
			reviewUserList.add(user);
			
			//测试
			//IUser otheruser = (IUser)newConnect.session.getObject(UserConstants.CLASS_USER,"yemw");
			//reviewUserList.add(otheruser);
			changeRecode.setItemRecordInfo(db,change,reviewUserList,managerUser);
		}catch(APIException e){
			jObj.put("success", "false");
			jObj.put("msg",e.toString());
			return jObj;
		}
		return changeRecode.toJSON();
	}
	*/
	public void writeReview(JSONObject jObj) throws SQLException{
		try{
			System.out.println(jObj.toString());
			JSONArray itemRecordArray = jObj.getJSONArray("itemRecords");
			for (int i=0;i<itemRecordArray.size();i++){
				JSONObject reviewRecords = (JSONObject) itemRecordArray.get(i);
				JSONArray reviewRecordAarray = reviewRecords.getJSONArray("itemReviewRecords"); 
				for (int j=0;j<reviewRecordAarray.size();j++){
					JSONObject reviewObj = (JSONObject) reviewRecordAarray.get(j);
					db.writeReviewRecord(reviewObj.getString("rowid"), reviewObj.getString("userid"), reviewObj.getString("specReview"),
							reviewObj.getString("reason"),reviewObj.getString("docId"), reviewObj.getString("ecnId")); 
				}				
			}
		}finally{			
			try {
				if (db.conn != null){
					db.conn.close();	
				}
				if (db.sql != null){
					db.sql.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	boolean checkRole(IChange change,IUser user) throws APIException{
		IUser[] userList = change.getApprovers(change.getStatus());
		for (int i=0;i<userList.length;i++){
			if (userList[i].equals(user)){
				return true;
			}
		}		
		return false;
	}
	
	ArrayList<IUser> getManagerList(IChange change,IUser user) throws APIException{
		ArrayList<IUser> userList = new ArrayList<IUser>();
		ICell cell = change.getCell(ChangeConstants.ATT_COVER_PAGE_FUNCTIONALTEAM);
		IAgileList list = (IAgileList) cell.getValue();
		IAgileList[] select = list.getSelection();
		for (int i=0;i<select.length;i++){
			IAgileList[] managerList = null;
			IAgileList[] reviewList = null;			
			IUserGroup a = (IUserGroup) select[i].getValue();
			ITable table = a.getTable(UserGroupConstants.TABLE_JOBFUNCTION);
			Iterator itr = table.iterator();
			while (itr.hasNext()){
				IRow tempRow = (IRow)itr.next();
				ICell rowCell = tempRow.getCell(new Integer(2000017205));	
				IAgileList rowvalue = (IAgileList)rowCell.getValue();
				IAgileList[] rowselected = rowvalue.getSelection();
				if (rowselected[0].getAPIName().toUpperCase().equals("USER")){
					rowCell = tempRow.getCell(new Integer(2000017207));
					rowvalue = (IAgileList)rowCell.getValue();					
					reviewList = rowvalue.getSelection();
				}
				if (rowselected[0].getAPIName().toUpperCase().equals("MANGER")){
					rowCell = tempRow.getCell(new Integer(2000017207));
					rowvalue = (IAgileList)rowCell.getValue();	
					managerList = rowvalue.getSelection();
				}				
			}
			for (int j=0;j<reviewList.length;j++){
				IUser tuser = (IUser) reviewList[j].getValue();
				if (tuser.equals(user)){
					for (int k=0;k<managerList.length;k++){
						IUser muser = (IUser)managerList[k].getValue();
						if (!userList.contains(muser)){
							userList.add(muser);
						}						
					}
					break;
				}
			}						
		}
		
		return userList;
	}
	
	
	public static void main(String[] args) throws APIException, IOException, SQLException {
		String URL = "http://agileplm:7001/Agile";
		String USERNAME = "admin";
		String PASSWORD = "1";
		
		IAgileSession tSession = Login.connect(URL, USERNAME, PASSWORD);
		IChange change = (IChange)tSession.getObject(ChangeConstants.CLASS_CHANGE_ORDERS_CLASS,"DIC0000002");
		IUser user = (IUser)tSession.getObject(UserConstants.CLASS_USER,"huanglonga");
		System.out.println(user.getValue(UserConstants.ATT_GENERAL_INFO_USER_ID));
		
		
		
		
		ReadReview a = new ReadReview();
		
		//System.out.println(a.loadReview(change,user));
		
		String input = "{\"changeNumber\":\"DIR009\",\"managerID\":\"\",\"managerApprove\":\"\",\"itemRecords\":[{\"itemNumber\":\"D001\"," +
				"\"description\":\"\",\"rev\":\"\",\"managerReviewRecord\":\"\",\"itemReviewRecords\":[{\"rowid\":\"999\",\"userid\":\"999\"," +
				"\"specReview\":\"GenerateNewSpec\",\"reason\":\"\",\"docId\":\"7777\",\"ecnId\":\"6666\"}]},{\"itemNumber\":\"D002\"," +
				"\"description\":\"\",\"rev\":\"\",\"managerReviewRecord\":\"\",\"itemReviewRecords\":[{\"rowid\":\"888\",\"userid\":\"888\"," +
				"\"specReview\":\"NoInvole\",\"reason\":\"XX文档不需要转换\",\"docId\":\"555\",\"ecnId\":\"444\"}]}]}";

		JSONObject jObj = JSONObject.fromObject(input);
		

		
		
		a.writeReview(jObj);
		
		
		

		
		
		//System.out.println(a.toJSON().toString());
		
	}	
}
