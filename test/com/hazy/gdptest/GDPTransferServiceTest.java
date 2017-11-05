package com.hazy.gdptest;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.hazy.common.HazyUtil;
import com.hazy.gdptransfer.service.GDPTransferService;
import com.hazy.pool.DataBasePoolFactory;

public class GDPTransferServiceTest {
	static Logger logger = Logger.getLogger(GDPTransferServiceTest.class);
	GDPTransferService service=new GDPTransferService();
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
	public void testDocument() throws SQLException{
		System.out.println(service.getDocumentJSON());
	}
	@Test
	public void testECN() throws SQLException{
		System.out.println(service.getECNJSON());
	}
}
