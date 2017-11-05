package com.hazy.gdptransfer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.agile.api.APIException;
import com.agile.api.IAdminList;
import com.agile.api.IAgileList;
import com.agile.api.IAgileSession;
import com.hazy.gdptransfer.service.GDPTransferService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AgileListServlet extends HttpServlet{
	 private static Logger logger = Logger.getLogger(AgileListServlet.class);
		

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	GDPTransferService service=new GDPTransferService();
	static String Agile_List_APIName=""; 
	static String Agile_List_WebKey="Agile_List_WebKey";
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONArray jsonArray2 =null;
		String action="";
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		try {
		if("ecn".equals(action)) {
			jsonArray2 =	service.getECNJSON();
		}else if("doc".equals(action)) {
			jsonArray2 =	service.getDocumentJSON();
		}else if("specreview".equals(action)) {
			jsonArray2 = (JSONArray)getServletContext().getAttribute("Agile_List");
		     
		    if(jsonArray2==null){
			
			//	IAgileSession m_session= AgileSession.getCurrentSession(request);
			//	JSONObject json=getCascadeListByAPIName(m_session,Agile_List_APIName);
				jsonArray2=new JSONArray();
			//	jsonArray2.add(json);
				getServletContext().setAttribute(Agile_List_WebKey, jsonArray2);
			
			
		    }
		}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	    logger.info("JSON:"+jsonArray2);
		response.setContentType("text/json;charset=UTF-8"); 

		response.getWriter().write(jsonArray2.toString());
		
		
	}




}
