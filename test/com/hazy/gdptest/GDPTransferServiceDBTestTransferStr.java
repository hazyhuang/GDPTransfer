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
import com.hazy.plmwebpx.model.UserDTO;
import com.hazy.plmwebpx.model.ChangeDTO;
import com.hazy.plmwebpx.model.ChangeRecord;
import com.hazy.pool.DataBasePoolFactory;

import net.sf.json.JSONObject;

public class GDPTransferServiceDBTestTransferStr {
	static Logger logger = Logger.getLogger(GDPTransferServiceDBTestTransferStr.class);
	GDPTransferService service = null;

	@Before
	public void setUp() {
		HazyUtil.getLogHelper().initLogger();
		service = new GDPTransferService();
	}


//@Test
	public void testGetUserInfor() {
		try {
			String number = this.service.getDbDAO().transferDocumentNumber("O00000007;O00000008");
			System.out.println(number);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
