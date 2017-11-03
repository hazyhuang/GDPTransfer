package com.hazy.gdptest;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.hazy.common.HazyUtil;
import com.hazy.pool.DataBasePoolFactory;

public class DBCPTest {
	static Logger logger = Logger.getLogger(DBCPTest.class);
	@Before
	public void setUp() {}
	//@Test
	public void testLoad() throws SQLException{
		HazyUtil.getLogHelper().initLogger();
		String fullpath=HazyUtil.getLinuxUtil().getLocalFullPath("dbcp-config.properties");
	for(int i=0;i<5;i++){
	Connection con2=DataBasePoolFactory.getInstance(fullpath).getConnection();
	System.out.println(i+1);
	}
	}
	@Test
	public void testVV(){
		String xx="0|11";
		String[] ss=xx.split("\\|");
		for(int i=0;i<ss.length;i++){
			System.out.println(ss[i]);
		}
		
	}
}
