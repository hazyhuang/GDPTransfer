package com.hazy.gdptransfer.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.hazy.common.HazyException;
import com.hazy.common.HazyUtil;
import com.hazy.pool.DataBasePoolFactory;

public class Helper {


	public static Connection getConnection() throws SQLException {
		String fullpath= Helper.class.getResource("/").getPath()+"dbcp-config.properties";
		Connection con2=DataBasePoolFactory.getInstance(fullpath).getConnection();
		return con2;

	}
	
	public static Properties loadConfig() throws HazyException {
		String fullPath= Helper.class.getResource("/").getPath()+"conf.properties";
		
		return HazyUtil.getInstance().loadPropertiesUTF8(fullPath);
	}
}
