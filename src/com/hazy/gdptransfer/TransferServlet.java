package com.hazy.gdptransfer;

import java.io.*;
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
import com.hazy.plmwebpx.model.ItemReviewRecord;

import net.sf.json.JSONObject;


public class TransferServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	private static Logger logger = Logger.getLogger(TransferServlet.class);
	public TransferServlet() {
		super();
	}
	GDPTransferService service=null;
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
        this.service.updateGDPTransfer(changeRecord);
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