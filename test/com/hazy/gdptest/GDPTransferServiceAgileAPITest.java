package com.hazy.gdptest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
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
import com.hazy.plmwebpx.model.AgileUser;
import com.hazy.plmwebpx.model.ChangeInfor;
import com.hazy.plmwebpx.model.ChangeRecord;
import com.hazy.pool.DataBasePoolFactory;

import net.sf.json.JSONObject;

public class GDPTransferServiceAgileAPITest {
	static Logger logger = Logger.getLogger(GDPTransferServiceAgileAPITest.class);
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
	//@Test
	public void testSpecReivewList() throws SQLException, APIException{
		System.out.println(service.getSpecReivewList());
	}
	//@Test
	public void testGetChangeInfor() throws SQLException, APIException{
		ChangeInfor infor=service.getChangeInfor("DIC0000002");
		System.out.println(infor.getStatus());
		Collection<AgileUser> users=infor.getReviewers();
		for(AgileUser user:users) {
			System.out.println(user.getLoginid());
		}
	}
	public void testUpdateTransfer() {
		String jsonStr="{\"changeNumber\":\"DIC0000002\",\"managerID\":\"\",\"managerApprove\":\"\",\"itemRecords\":[{\"itemNumber\":\"03023XFG_PSG1ZH\",\"description\":\"\",\"rev\":\"\",\"managerReviewRecord\":\"\",\"itemReviewRecords\":[{\"rowid\":\"0\",\"userid\":\"admin\",\"specReview\":\"GENERATE_NEW_SPEC\",\"reason\":\"\",\"docNumber\":\"O00000008\",\"docId\":\"6018702\",\"ecnNumber\":\"ECN_00000084\",\"ecnId\":\"6162370\"}]}]}";
	   ChangeRecord record=ChangeRecord.createChangeRecord(JSONObject.fromObject(jsonStr));
		try {
			this.service.updateGDPTransfer(record);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//@Test
	public void testGetManager() throws APIException, SQLException {
		JSONObject jobj=this.service.getGDPManager("DIC0000002", "yemw");
		System.out.println(jobj);
	}
	//@Test
		public void testGetChangeInforReview() throws SQLException, APIException{
			ChangeInfor infor=service.getChangeInfor("DIC0000002","Review2");
			System.out.println(infor.getStatus());
			Collection<AgileUser> users=infor.getReviewers();
			for(AgileUser user:users) {
				System.out.println(user.getLoginid());
			}
		}
}
