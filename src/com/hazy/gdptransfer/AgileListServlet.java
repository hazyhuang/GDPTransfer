package com.hazy.gdptransfer;

import java.io.IOException;
import java.sql.SQLException;
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

import net.sf.json.JSONArray;
public class AgileListServlet extends HttpServlet{
	 private static Logger logger = Logger.getLogger(AgileListServlet.class);
		
	 private GDPTransferService service=null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	
	
	static String Agile_List_WebKey="Agile_List_WebKey";
	public void doPost(HttpServletRequest request, HttpServletResponse response)
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
			jsonArray2 = (JSONArray)getServletContext().getAttribute(Agile_List_WebKey);
			if(jsonArray2==null) {
				jsonArray2=service.getSpecReivewList();
			}
		    getServletContext().setAttribute(Agile_List_WebKey, jsonArray2);
			
		}
		}catch(SQLException ex) {
			ex.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    logger.info("JSON:"+jsonArray2);
		response.setContentType("text/json;charset=UTF-8"); 

		response.getWriter().write(jsonArray2.toString());
		
		
	}




}
