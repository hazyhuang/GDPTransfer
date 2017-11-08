package com.hazy.gdptransfer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.hazy.common.HazyException;
import com.hazy.gdptransfer.service.GDPTransferService;
import com.hazy.gdptransfer.util.AgileSessionHelper;
import com.hazy.gdptransfer.util.Helper;

import net.sf.json.JSONObject;
public class GDPTransferServlet extends HttpServlet{
	 private static Logger logger = Logger.getLogger(GDPTransferServlet.class);
	 private GDPTransferService service=null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IAgileSession session=null;
		try {
			session = AgileSessionHelper.getCurrentSession(request);
			logger.debug("agile session status:"+session.isOpen());
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HazyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.service=new GDPTransferService(session);
		JSONObject retJSON =new JSONObject();
		String approveAPIName="";
		String action="";
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		try {
			String changeNumber=(String)request.getSession().getAttribute("agile.1047");
			String userid=(String)request.getSession().getAttribute("agile.userName");
			Properties config=Helper.loadConfig();
			approveAPIName=config.getProperty("approveNodeAPI");
		if("loadReview".equals(action)) {
			retJSON.put("success", true);
			retJSON.put("msg", this.service.getGDPTransfer(changeNumber, userid));
		}else if("loadManager".equals(action)){
			retJSON.put("success", true);
			retJSON.put("msg", this.service.getGDPManager(changeNumber, userid));
		}else if("loadUsers".equals(action)) {
			retJSON.put("success", true);
			retJSON.put("msg", this.service.getChangeInfor(changeNumber, approveAPIName).toJSONReviewers());
		}else if("loadManagerByUserid".equals(action)){
			retJSON.put("success", true);
			String manager=(String)request.getParameter("Manager");
			logger.debug("Manager:"+manager);
			retJSON.put("msg", this.service.getGDPManager(changeNumber, manager));
		}
		}catch(SQLException ex) {
			ex.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HazyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    logger.info("JSON:"+retJSON);
		response.setContentType("text/json;charset=UTF-8"); 
		response.getWriter().write(retJSON.toString());
	}




}
