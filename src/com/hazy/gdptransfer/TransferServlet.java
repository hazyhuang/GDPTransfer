package com.hazy.gdptransfer;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.hazy.common.HazyException;
import com.hazy.gdptransfer.service.GDPTransferService;
import com.hazy.gdptransfer.util.AgileSessionHelper;
import com.hazy.plmwebpx.model.ChangeRecord;

import net.sf.json.JSONObject;


public class TransferServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TransferServlet.class);
	private GDPTransferService service=null;
	public TransferServlet() {
		super();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IAgileSession session=null;
		try {
			session = AgileSessionHelper.getCurrentSession(request);
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HazyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.service=new GDPTransferService(session);
		
		String json = readJSONString(request);
		logger.debug("json String:"+json);
		JSONObject jObj=JSONObject.fromObject(json);
		logger.debug("jsonObject:"+jObj);
        ChangeRecord changeRecord=ChangeRecord.createChangeRecord(jObj);
        try {
			this.service.updateGDPTransfer(changeRecord);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

	private String readJSONString(HttpServletRequest request) {
		StringBuffer json = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return json.toString();
	}
}