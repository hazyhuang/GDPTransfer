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
import com.hazy.plmwebpx.model.Document;
import com.hazy.plmwebpx.model.ECN;
import com.hazy.plmwebpx.model.ItemRecord;
import com.hazy.plmwebpx.model.ItemReviewRecord;

import net.sf.json.JSONObject;

public class AgileDataBaseDAO {

	private static Logger logger = Logger.getLogger(AgileDataBaseDAO.class);

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
				logger.debug(obj);
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
				logger.debug(obj);
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
				itemReviewRecord.setRowid(rs.getInt("ID"));
				itemReviewRecord.setSpecReview(rs.getString("specReview"));
				itemReviewRecord.setReason(rs.getString("reason"));
				itemReviewRecord.setDocId(rs.getInt("docID"));
				itemReviewRecord.setDocNumber(rs.getString("docNumber"));
				itemReviewRecord.setEcnId(rs.getInt("ecnID"));
				itemReviewRecord.setEcnNumber(rs.getString("ecnNumber"));
			
			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

		return itemReviewRecord;
	}

	public JSONObject updateGDPTransfer(JSONObject str) throws SQLException {

		String sql = "select mangerApprove from GDP_ItemReviewRecord where change_id = '' and user_id = ''";

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Collection<Document> RecordSet = new ArrayList<Document>();
		try {
			conn = Helper.getConnection();
			stmt = conn.createStatement();

			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Document obj = new Document();

				obj.setObjectID(rs.getInt("ID"));
				obj.setNumber(rs.getString("Item_Number"));

				RecordSet.add(obj);
				logger.debug(obj);
			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

		return null;

	}

	public JSONObject getGDPManager(String changeid, String userid) throws SQLException {

		String sql = "select mangerApprove from RM_MangerApprove where change_id = '" + changeid + "' and user_id = '"
				+ userid + "'";

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Collection<Document> RecordSet = new ArrayList<Document>();
		try {
			conn = Helper.getConnection();
			stmt = conn.createStatement();

			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Document obj = new Document();

				obj.setObjectID(rs.getInt("ID"));
				obj.setNumber(rs.getString("Item_Number"));

				RecordSet.add(obj);
				logger.debug(obj);
			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

		return null;

	}

	public JSONObject updateGDPManger(JSONObject str) {
		return null;
	}

	public void createItemRecord(String chgnum,ItemRecord itemRecord) {
		
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
		       +" (id,changeNumber,itemid,itemNumber,userid,specReview,Reason,"
					+"docid,docNumber,ecnid,ecnNumber) values(GDP_ItemReviewRecord_seq_id.nextval,?,?,?,?,?,?,?,?,?,?)";
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
				stmt.execute();
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt,rs);
		}
		}
	}

	public void updateItemRecord(ItemRecord itemRecord) {
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

			String sql = "update GDP_ItemReviewRecord"
		       +" set specReview=? ,set Reason=?,"
					+"set docid=?, set docNumber=?, set ecnid=?,set ecnNumber=?"
					+ " where rowid=?";
			stmt = conn.prepareStatement(sql);
		
			stmt.setString(1, itemReviewRecord.getSpecReview());
			stmt.setString(2, itemReviewRecord.getReason());
			stmt.setInt(3, itemReviewRecord.getDocId());
			stmt.setString(4, itemReviewRecord.getDocNumber());
			stmt.setInt(5, itemReviewRecord.getEcnId());
			stmt.setString(6, itemReviewRecord.getEcnNumber());
			stmt.setInt(7, itemReviewRecord.getRowid());
				stmt.executeUpdate();
				
		

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt,rs);
		}
		}
		
	}

}
