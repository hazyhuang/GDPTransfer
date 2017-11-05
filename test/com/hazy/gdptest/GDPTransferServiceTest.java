package com.hazy.gdptest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.hazy.common.HazyException;
import com.hazy.common.HazyUtil;
import com.hazy.gdptransfer.service.GDPTransferService;
import com.hazy.gdptransfer.util.AgileSessionHelper;
import com.hazy.gdptransfer.util.Helper;
import com.hazy.pool.DataBasePoolFactory;

public class GDPTransferServiceTest {
	static Logger logger = Logger.getLogger(GDPTransferServiceTest.class);
	GDPTransferService service=new GDPTransferService();
	@Before
	public void setUp() {
		HazyUtil.getLogHelper().initLogger();
		IAgileSession session=null;
		try {
			Properties config=Helper.loadConfig();
			session = AgileSessionHelper.getSession(config);
		} catch (HazyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		service=new GDPTransferService(session);
	}
	//@Test
	public void testLoad() throws SQLException{
		HazyUtil.getLogHelper().initLogger();
		String fullpath=HazyUtil.getLinuxUtil().getLocalFullPath("dbcp-config.properties");
	for(int i=0;i<5;i++){
	Connection con2=DataBasePoolFactory.getInstance(fullpath).getConnection();
	System.out.println(i+1);
	}
	}
	//@Test
	public void testDocument() throws SQLException{
		System.out.println(service.getDocumentJSON());
	}
	//@Test
	public void testECN() throws SQLException{
		System.out.println(service.getECNJSON());
	}
	@Test
	public void testSpecReivewList() throws SQLException, APIException{
		System.out.println(service.getSpecReivewList());
	}
}
