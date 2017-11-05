package com.hazy.gdptransfer;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
import com.hazy.plmwebpx.model.ChangeInfor;


public class GDPServlet extends HttpServlet{
	 private static Logger logger = Logger.getLogger(GDPServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//http://www.local.com:8080/GDPTransfer/GDPServlet?agile.classID=2486607&agile.1047=DIC0000002&agile.userName=admin&ContextType=DIC_%E5%8D%8E%E4%B8%BA%E6%96%87%E6%A1%A3%E8%BD%AC%E5%8C%96%E6%B5%81%E7%A8%8B&ContextName=DIC0000002
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);

	}
	GDPTransferService service=null;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userid=request.getParameter("agile.userName");//userid
		String changeNumber=request.getParameter("agile.1047");//userid
		request.getSession().setAttribute("agile.userName", userid);
		request.getSession().setAttribute("agile.1047", changeNumber);
		IAgileSession session=null;
		ChangeInfor chgInfor=null;
		try {
			session = AgileSessionHelper.getCurrentSession(request);
			this.service=new GDPTransferService(session);
			chgInfor =   this.service.getChangeInfor(changeNumber);
			
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HazyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		  String path = "default/GDPTransfer.jsp";
	        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/"+path);
	        logger.debug("forward:"+path);
	        requestDispatcher.forward(request, response);
	}

}
