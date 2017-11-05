package com.hazy.gdptransfer.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;


import com.hazy.common.HazyUtil;
import com.hazy.gdptransfer.util.DBHelper;
import com.hazy.plmwebpx.model.Document;
import com.hazy.plmwebpx.model.ECN;

public class AgileDataBaseDAO {
	
	private static Logger logger = Logger.getLogger(AgileDataBaseDAO.class);

	public Collection<ECN> loadECN() throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn= null;
		Collection<ECN> RecordSet = new ArrayList<ECN>();
		try {
			conn=DBHelper.getConnection();
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
			HazyUtil.getDBConnectionHelper().close(conn, stmt,rs);
		}

		return RecordSet;
	}
	
	public Collection<Document> loadDocument() throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn= null;
		Collection<Document> RecordSet = new ArrayList<Document>();
		try {
			conn=DBHelper.getConnection();
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
			HazyUtil.getDBConnectionHelper().close(conn, stmt,rs);
		}

		return RecordSet;
	}
	
	

}
