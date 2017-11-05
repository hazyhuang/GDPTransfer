package com.hazy.gdptransfer.util;

import java.sql.Connection;
import java.sql.SQLException;


import com.hazy.pool.DataBasePoolFactory;

public class DBHelper {


	public static Connection getConnection() throws SQLException {
		String fullpath= DBHelper.class.getResource("/").getPath()+"dbcp-config.properties";
		Connection con2=DataBasePoolFactory.getInstance(fullpath).getConnection();
		return con2;

	}
}
