/*
 * Copyright 2012 - 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import com.hazy.plmwebpx.model.UserDTO;
import com.hazy.plmwebpx.model.ChangeRecord;
import com.hazy.plmwebpx.model.DocumentDTO;
import com.hazy.plmwebpx.model.ECNDTO;
import com.hazy.plmwebpx.model.ItemRecord;
import com.hazy.plmwebpx.model.ItemReviewRecord;

/**
 * 
 * @author Hua.Huang
 */
public class AgileDataBaseDAO {

	private static Logger logger = Logger.getLogger(AgileDataBaseDAO.class);

	public UserDTO getUserInfor(String loginid) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		UserDTO obj = null;
		try {
			conn = Helper.getConnection();
			stmt = conn.createStatement();
			String sql = "select b.last_name||b.first_name||'('||b.loginid || ')' username from agileuser b where b.loginid='"
					+ loginid + "'";
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				obj = new UserDTO(loginid);
				obj.setUsername(rs.getString("username"));
				logger.debug(obj);
			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}
		return obj;
	}

	/**
	 * 从Agile数据库中获取子类ECN的ObjectID 不存在则返回0
	 * 
	 * @param number
	 * @return
	 * @throws SQLException
	 */
	public Integer getECNObjectID(String number) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = Helper.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from change where subclass =2473057 and change_Number='" + number + "'";
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				return rs.getInt("ID");

			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

		return 0;
	}

	public Integer getDocumentObjectID(String number) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = Helper.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from item where class =9000 and item_number='" + number + "'";
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				return rs.getInt("ID");

			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

		return 0;
	}

	/**
	 * 加载所有子类ECN
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Collection<ECNDTO> loadAllECN() throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Collection<ECNDTO> RecordSet = new ArrayList<ECNDTO>();
		try {
			conn = Helper.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from change where subclass =2473057";
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ECNDTO obj = new ECNDTO();

				obj.setObjectID(rs.getInt("ID"));
				obj.setNumber(rs.getString("change_Number"));

				RecordSet.add(obj);
			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

		return RecordSet;
	}

	public Collection<DocumentDTO> loadAllDocument() throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Collection<DocumentDTO> RecordSet = new ArrayList<DocumentDTO>();
		try {
			conn = Helper.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from item where class =9000";
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				DocumentDTO obj = new DocumentDTO();

				obj.setObjectID(rs.getInt("ID"));
				obj.setNumber(rs.getString("Item_Number"));

				RecordSet.add(obj);
			}
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

		return RecordSet;
	}

	public ItemReviewRecord loadItemReviewRecord(String changeNumber, String itemNum, String loginid)
			throws SQLException {

		String sql = "select * from GDP_ItemReviewRecord where changenumber = '" + changeNumber + "' and userid = '"
				+ loginid + "'" + " and itemNumber='" + itemNum + "'";

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		ItemReviewRecord itemReviewRecord = new ItemReviewRecord();
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

	public void createItemRecord(String chgnum, ItemRecord itemRecord) throws SQLException {

		Collection<ItemReviewRecord> ItemReviewRecords = itemRecord.getItemReviewRecords();
		ItemReviewRecord itemReviewRecord = null;
		for (ItemReviewRecord record : ItemReviewRecords) {
			itemReviewRecord = record;
		}
		if (itemReviewRecord != null) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			Connection conn = null;
			try {
				conn = Helper.getConnection();

				String sql = "insert into GDP_ItemReviewRecord"
						+ " (tid,changeNumber,itemid,itemNumber,userid,specReview,Reason,"
						+ "docid,docNumber,ecnid,ecnNumber,specReviewValue,username)"
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
				HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
			}
		}
	}

	public void updateItemRecord(ItemRecord itemRecord) throws SQLException {
		Collection<ItemReviewRecord> ItemReviewRecords = itemRecord.getItemReviewRecords();
		ItemReviewRecord itemReviewRecord = null;
		for (ItemReviewRecord record : ItemReviewRecords) {
			itemReviewRecord = record;
		}
		if (itemReviewRecord != null) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			Connection conn = null;
			try {
				conn = Helper.getConnection();

				String sql = "update GDP_ItemReviewRecord " + " set specReview=?,Reason=?,docid=?, docNumber=?, "
						+ "ecnid=?,ecnNumber=?,specReviewValue=?" + " where tid=?";
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
			} finally {
				HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
			}
		}

	}

	public Collection<ItemReviewRecord> loadItemReviewRecords(String changeNumber, String itemNumber,
			Collection<UserDTO> users) throws SQLException {
		Collection<ItemReviewRecord> itemReviewRecords = new ArrayList<ItemReviewRecord>();
		for (UserDTO user : users) {
			ItemReviewRecord itemReviewRecord = this.loadItemReviewRecord(changeNumber, itemNumber, user.getLoginid());
			fillObjectID(itemReviewRecord);
			if (itemReviewRecord.getRowid() == 0) {
				UserDTO aUser = this.getUserInfor(user.getLoginid());
				itemReviewRecord.setUsername(aUser.getUsername());
			}
			itemReviewRecords.add(itemReviewRecord);
		}
		return itemReviewRecords;
	}

	private void fillObjectID(ItemReviewRecord itemReviewRecord) throws SQLException {
		String docNum = itemReviewRecord.getDocNumber();
		itemReviewRecord.setDocNumber(this.transferDocumentNumber(docNum));
		String ecnNum = itemReviewRecord.getEcnNumber();
		itemReviewRecord.setEcnNumber(this.transferECNNumber(ecnNum));
	}

	public String transferECNNumber(String ecnNum) throws SQLException {
		StringBuffer strBuffer = new StringBuffer();
		if (ecnNum != null & ecnNum != "") {
			String numbers[] = ecnNum.split(";");
			for (String num : numbers) {
				Integer objectID = this.getECNObjectID(num);
				strBuffer.append(num + ":" + objectID + ";");
			}
			if (numbers.length > 0) {
				strBuffer.deleteCharAt(strBuffer.length() - 1);
			}
		}
		return strBuffer.toString();
	}

	public String transferDocumentNumber(String docNum) throws SQLException {
		StringBuffer strBuffer = new StringBuffer();
		if (docNum != null & docNum != "") {
			String numbers[] = docNum.split(";");
			for (String num : numbers) {
				Integer objectID = this.getDocumentObjectID(num);
				strBuffer.append(num + ":" + objectID + ";");
			}
			if (numbers.length > 0) {
				strBuffer.deleteCharAt(strBuffer.length() - 1);
			}
		}
		return strBuffer.toString();
	}

	public void loadItemRecord(ItemRecord itemRecord, String changeNumber, String loginid) throws SQLException {
		String itemNumber = itemRecord.getItemNumber();
		String sql = "select * from GDP_ManagerReview where changenumber = '" + changeNumber + "' and userid = '"
				+ loginid + "'" + " and  itemnumber = '" + itemNumber + "'";

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

	public void createMangerReview(String chgNumber, String userid, ItemRecord itemRecord) throws SQLException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = Helper.getConnection();
			String sql = "insert into GDP_ManagerReview" + " (tid,changeNumber,userid,itemNumber,managerReview)"
					+ " values(GDP_ManagerReview_seq_id.nextval,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, chgNumber);
			stmt.setString(2, userid);
			stmt.setString(3, itemRecord.getItemNumber());
			stmt.setString(4, itemRecord.getManagerReviewRecord());
			stmt.execute();

		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

	}

	public void updateMangerReview(String chgNumber, String loginid, ItemRecord itemRecord) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = Helper.getConnection();
			String sql = "update GDP_ManagerReview" + " set managerReview=?  where tid=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, itemRecord.getManagerReviewRecord());
			stmt.setInt(2, itemRecord.getRowid());
			stmt.executeUpdate();
		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

	}

	public void loadChangeRecord(ChangeRecord changeRecord, String changeNumber, String userid) throws SQLException {

		String sql = "select * from GDP_ManagerApprove where changenumber = '" + changeNumber + "' and userid = '"
				+ userid + "'";
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

	public void createMangerApprove(String chgNumber, String loginid, ChangeRecord changeRecord) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = Helper.getConnection();
			String sql = "insert into GDP_ManagerApprove" + " (tid,changeNumber,userid,managerApprove)"
					+ " values(GDP_ManagerApprove_seq_id.nextval,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, chgNumber);
			stmt.setString(2, loginid);
			stmt.setString(3, changeRecord.getManagerApprove());
			stmt.execute();

		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

	}

	public void updateMangerApprove(String chgNumber, String loginid, ChangeRecord changeRecord) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = Helper.getConnection();
			String sql = "update GDP_ManagerApprove" + " set managerApprove=?  where tid=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, changeRecord.getManagerApprove());
			stmt.setInt(2, changeRecord.getRowid());
			logger.debug("rowid:" + changeRecord.getRowid());
			stmt.executeUpdate();

		} finally {
			HazyUtil.getDBConnectionHelper().close(conn, stmt, rs);
		}

	}

}
