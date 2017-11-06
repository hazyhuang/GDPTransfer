package com.hazy.gdptransfer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.hazy.common.HazyUtil;
import com.hazy.gdptransfer.util.Helper;
import com.hazy.plmwebpx.model.AgileUser;
import com.hazy.plmwebpx.model.ChangeRecord;
import com.hazy.plmwebpx.model.Document;
import com.hazy.plmwebpx.model.ECN;
import com.hazy.plmwebpx.model.ItemRecord;
import com.hazy.plmwebpx.model.ItemReviewRecord;

public class AgileDataBaseDAO {

	private static Logger logger = Logger.getLogger(AgileDataBaseDAO.class);

	public AgileUser getUserInfor(String userid) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		AgileUser obj =null;
		try {
			conn = Helper.getConnection();
			stmt = conn.createStatement();
			String sql = "select b.last_name||b.first_name||'('||b.loginid || ')' username from agileuser b where b.loginid='"+userid+"'";
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				obj = new AgileUser(userid);

				
				obj.setUsername(rs.getString("username"));

				
				logger.debug(obj);
			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

		return obj;
	}

	
	public Collection<ECN> loadECN() throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Collection<ECN> RecordSet = new ArrayList<ECN>();
		try {
			conn = Helper.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from change where subclass =2473057";
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ECN obj = new ECN();

				obj.setObjectID(rs.getInt("ID"));
				obj.setNumber(rs.getString("change_Number"));

				RecordSet.add(obj);
			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

		return RecordSet;
	}

	public Collection<Document> loadDocument() throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Collection<Document> RecordSet = new ArrayList<Document>();
		try {
			conn = Helper.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from item where class =9000";
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Document obj = new Document();

				obj.setObjectID(rs.getInt("ID"));
				obj.setNumber(rs.getString("Item_Number"));

				RecordSet.add(obj);
			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

		return RecordSet;
	}

	public ItemReviewRecord getGDPTransfer(String changeid, String itemNum,String userid) throws SQLException {

		String sql = "select * from GDP_ItemReviewRecord where changenumber = '"
		+changeid+"' and userid = '"+userid+"'"+" and itemNumber='"+itemNum+"'";

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
	
		ItemReviewRecord itemReviewRecord=new ItemReviewRecord();
		try {
			conn = Helper.getConnection();
			stmt = conn.createStatement();
            
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				itemReviewRecord.setRowid(rs.getInt("TID"));
				itemReviewRecord.setSpecReview(rs.getString("specReview"));
				itemReviewRecord.setReason(rs.getString("reason"));
				itemReviewRecord.setDocId(rs.getInt("docID"));
				itemReviewRecord.setDocNumber(rs.getString("docNumber"));
				itemReviewRecord.setEcnId(rs.getInt("ecnID"));
				itemReviewRecord.setEcnNumber(rs.getString("ecnNumber"));
				itemReviewRecord.setUserid(rs.getString("userid"));
				itemReviewRecord.setUsername(rs.getString("username"));
				itemReviewRecord.setSpecReviewValue(rs.getString("specReviewValue"));
			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

		return itemReviewRecord;
	}



	public void createItemRecord(String chgnum,ItemRecord itemRecord) throws SQLException {
		
		Collection<ItemReviewRecord> ItemReviewRecords=itemRecord.getItemReviewRecords();
		ItemReviewRecord itemReviewRecord=null;
		for(ItemReviewRecord record:ItemReviewRecords) {
			itemReviewRecord=record;
		}
		if(itemReviewRecord!=null) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn= null;
		try {
			conn=Helper.getConnection();

			String sql = "insert into GDP_ItemReviewRecord"
		       +" (tid,changeNumber,itemid,itemNumber,userid,specReview,Reason,"
					+"docid,docNumber,ecnid,ecnNumber,specReviewValue,username)"
					+ " values(GDP_ItemReviewRecord_seq_id.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, chgnum);
			stmt.setInt(2, itemRecord.getItemid());
			stmt.setString(3, itemRecord.getItemNumber());
			
			stmt.setString(4, itemReviewRecord.getUserid());
			stmt.setString(5, itemReviewRecord.getSpecReview());
			stmt.setString(6, itemReviewRecord.getReason());
			stmt.setInt(7, itemReviewRecord.getDocId());
			stmt.setString(8, itemReviewRecord.getDocNumber());
			stmt.setInt(9, itemReviewRecord.getEcnId());
			stmt.setString(10, itemReviewRecord.getEcnNumber());
			stmt.setString(11, itemReviewRecord.getSpecReviewValue());
			stmt.setString(12, itemReviewRecord.getUsername());
				stmt.execute();
				
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt,rs);
		}
		}
	}

	public void updateItemRecord(ItemRecord itemRecord) throws SQLException {
		Collection<ItemReviewRecord> ItemReviewRecords=itemRecord.getItemReviewRecords();
		ItemReviewRecord itemReviewRecord=null;
		for(ItemReviewRecord record:ItemReviewRecords) {
			itemReviewRecord=record;
		}
		if(itemReviewRecord!=null) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn= null;
		try {
			conn=Helper.getConnection();
/*
 create table GDP_ItemReviewRecord( ---ItemReviewRecord
       id number(10) not null,
       changenumber varchar2(50) not null,----Change_ID
       itemid number(10) , ---Item_ID
       userid varchar2(50) not null, ---UserID
       username varchar2(100) , ---UserID
       ItemNumber varchar2(50)  not null, ---Item_Number
       SpecReview varchar2(50), ---SpecReview
       SpecReviewValue varchar2(100),
       Reason varchar2(1000), ----Reason
       docid number(10),     ----Document
       DocNumber varchar2(30),
       ecnid number(10),      ----ECN
       ECNNumber varchar2(30),
       primary key(changenumber,ItemNumber,userid)
);*/
			String sql = "update GDP_ItemReviewRecord "
		       +" set specReview=?,Reason=?,docid=?, docNumber=?, "
		       + "ecnid=?,ecnNumber=?,specReviewValue=?"
					+ " where tid=?";
			stmt = conn.prepareStatement(sql);
		
			stmt.setString(1, itemReviewRecord.getSpecReview());
			stmt.setString(2, itemReviewRecord.getReason());
			stmt.setInt(3, itemReviewRecord.getDocId());
			stmt.setString(4, itemReviewRecord.getDocNumber());
			stmt.setInt(5, itemReviewRecord.getEcnId());
			stmt.setString(6, itemReviewRecord.getEcnNumber());
			stmt.setString(7, itemReviewRecord.getSpecReviewValue());
			stmt.setInt(8, itemReviewRecord.getRowid());
			stmt.executeUpdate();
		}  finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt,rs);
		}
		}
		
	}

	public Collection<ItemReviewRecord> getGDPManager(String changeNumber, String itemNumber, Collection<AgileUser> users) throws SQLException {
		Collection<ItemReviewRecord> itemReviewRecords=new ArrayList<ItemReviewRecord>();
		for(AgileUser user:users) {
			ItemReviewRecord itemReviewRecord=this.getGDPTransfer(changeNumber, itemNumber, user.getLoginid());
	        if(itemReviewRecord.getRowid()==0) {
	        	AgileUser aUser=this.getUserInfor(user.getLoginid());
	        	itemReviewRecord.setUsername(aUser.getUsername());
	        }
			itemReviewRecords.add(itemReviewRecord);	
		}
		return itemReviewRecords;
	}

	public void loadItemRecord(ItemRecord itemRecord, String changeNumber, String userid) throws SQLException {
		String itemNumber = itemRecord.getItemNumber();
		String sql = "select * from GDP_ManagerReview where changenumber = '"
	+ changeNumber + "' and userid = '"+ userid + "'"
	 + " and  itemnumber = '"+ itemNumber + "'";

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = Helper.getConnection();
			stmt = conn.createStatement();

			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				itemRecord.setRowid(rs.getInt("TID"));
				itemRecord.setManagerReviewRecord(rs.getString("managerReview"));

			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

		
	}

	public void createMangerReview(String chgNumber, String userid,ItemRecord itemRecord) throws SQLException {
	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn= null;
		try {
			conn=Helper.getConnection();
			/*id number(10) not null,
		      changenumber varchar2(50) not null,
		       userid varchar2(50) not null, ---Managerid
		       itemid number(10) not null,
		       ItemNumber varchar2(50), ---Item_Number
		       mangerReview varchar2(1000),  */
			String sql = "insert into GDP_ManagerReview"
		       +" (tid,changeNumber,userid,itemNumber,managerReview)"
					+" values(GDP_ManagerReview_seq_id.nextval,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, chgNumber);
			stmt.setString(2, userid);
			stmt.setString(3, itemRecord.getItemNumber());
			stmt.setString(4, itemRecord.getManagerReviewRecord());
				stmt.execute();
				
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt,rs);
		}
		
	}

	public void updateMangerReview(String chgNumber, String userid,ItemRecord itemRecord)throws SQLException  {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn= null;
		try {
			conn=Helper.getConnection();
            //changeNumber,userid,itemNumber,mangerReview
			String sql = "update GDP_ManagerReview"
		       +" set managerReview=?  where tid=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, itemRecord.getManagerReviewRecord());
			stmt.setInt(2, itemRecord.getRowid());
				stmt.executeUpdate();
		}  finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt,rs);
		}
		
	}

	public void loadChangeRecord(ChangeRecord changeRecord, String changeNumber, String userid) throws SQLException {
		
		String sql = "select * from GDP_ManagerApprove where changenumber = '"
	+ changeNumber + "' and userid = '"+ userid + "'";;

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = Helper.getConnection();
			stmt = conn.createStatement();

			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				changeRecord.setRowid(rs.getInt("TID"));
				changeRecord.setManagerApprove(rs.getString("managerApprove"));
			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

		
		
	}

	public void createMangerApprove(String chgNumber, String userid, ChangeRecord changeRecord)throws SQLException  {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn= null;
		try {
			conn=Helper.getConnection();
			/*   id number(10) not null,
      changenumber varchar2(50) not null,
      userid varchar2(50) not null,  ----Managerid
       mangerApprove varchar2(1000),       
       primary key(changenumber,userid) */
			String sql = "insert into GDP_ManagerApprove"
		       +" (tid,changeNumber,userid,managerApprove)"
					+" values(GDP_ManagerApprove_seq_id.nextval,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, chgNumber);
			stmt.setString(2, userid);
			stmt.setString(3, changeRecord.getManagerApprove());
				stmt.execute();
				
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt,rs);
		}
		
	}

	public void updateMangerApprove(String chgNumber, String userid, ChangeRecord changeRecord) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn= null;
		try {
			conn=Helper.getConnection();
            //changeNumber,userid,itemNumber,mangerReview
			String sql = "update GDP_ManagerApprove"
		       +" set managerApprove=?  where tid=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, changeRecord.getManagerApprove());
			stmt.setInt(2, changeRecord.getRowid());
			logger.debug("rowid:"+changeRecord.getRowid());
				stmt.executeUpdate();
			
		}  finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt,rs);
		}
		
	}

}
