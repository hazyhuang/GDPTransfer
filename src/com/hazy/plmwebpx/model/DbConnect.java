package com.hazy.plmwebpx.model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hazy.common.HazyUtil;

public class DbConnect {
	Connection conn;
	public boolean DbStatus = false;
	public String errorInfo;
	static Statement sql;
	
/*	
    private static ResultSet rs = null;*/

	public void getDbConnect(String db_path,String db_user,String db_pwd){
		try {
			conn = HazyUtil.getDBConnectionHelper().getDBConnection(db_path,db_user,db_pwd);		
			sql = conn.createStatement();
			DbStatus = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorInfo = "连接数据库失败！";
		}
	}
	
	public ResultSet getItemReord(String rowId) throws SQLException{
		ResultSet resultSet;
		String sqlQuery = "select item_number,description,Rev_Number from rev_query where id = '"+rowId+"'";		
		System.out.println(sqlQuery);
		resultSet = sql.executeQuery(sqlQuery);
		return resultSet;
	}
	
	public ResultSet getmanagerApprover(String ChangeId,String userId) throws SQLException{
		ResultSet resultSet;
		String sqlQuery = "select mangerApprove from RM_MangerApprove where change_id = '"+ChangeId+"' and user_id = '"+userId+"'";
		System.out.println(sqlQuery);
		resultSet = sql.executeQuery(sqlQuery);
		return resultSet;
	}

	public void writeReviewRecord(String tRowid,String tUserid,String tSpecReview,String tReason,String tDoc_ID,String tEcn_ID) throws SQLException{
		String sqlQuery = "select fn_merge_SpecReview('"+tRowid+"','"+tUserid+"','"+tSpecReview+"','"+tReason+"','"+tDoc_ID+"','"+tEcn_ID+"') from dual";
		sql.executeQuery(sqlQuery);
	}
	
	
	
	
	public static void main(String[] args) throws SQLException{
		String db_path = "jdbc:oracle:thin:@agileplm:1521:agile9";
		String db_user = "agile";
		String db_pwd = "agile933";
		
		DbConnect db = new DbConnect();
		db.getDbConnect(db_path, db_user, db_pwd);
		ResultSet resultSet = sql.executeQuery("select mangerApprove from RM_MangerApprove where change_id = '6758065' and user_id = '6022202'");
		System.out.println(resultSet.next());
		
	}

	public ResultSet getItemReviewInof(String rowId, String userIdList) throws SQLException {
		// TODO Auto-generated method stub
		ResultSet resultSet;
		//String sqlQuery = "select id,row_id,user_id,SpecReview,Reason,Doc_ID,ECN_ID from RM_SpecReview where Row_ID = '"+rowId+"' and user_id = '"+userId+"'";
		//String sqlQuery = "select a.*,(b.last_name||b.first_name||'('||b.loginid || ')') username from RM_SpecReview a left join agileuser b on a.user_id = b.id where a.Row_ID = '"+rowId+"' and a.user_id in ("+userIdList+")";
		String sqlQuery = "select * from view_RM_SpecReview where Row_ID = '"+rowId+"' and user_id in ("+userIdList+")";
		System.out.println(sqlQuery);
		resultSet = sql.executeQuery(sqlQuery);
		return resultSet;
	}
	
	
}
