/*
 * Copyright 2012 - 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hazy.gdptransfer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

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
import com.hazy.gdptransfer.util.Helper;
import com.hazy.plmwebpx.model.UserDTO;
import com.hazy.plmwebpx.model.ChangeDTO;

/**
 * 
 * @author Hua.Huang
 */
public class GDPServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(GDPServlet.class);
	private static final long serialVersionUID = 1L;
	private GDPTransferService service = null;


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("agile.userName");// userid
		String changeNumber = request.getParameter("agile.1047");// userid
		request.getSession().setAttribute("agile.userName", userid);
		request.getSession().setAttribute("agile.1047", changeNumber);
		IAgileSession session = null;
		ChangeDTO chgInfor = null;
		boolean hasPrivilege = false;
		String review="";
		String approve="";
		String finalApprove="";
		String agileurl="";
		try {
			Properties config=Helper.loadConfig();
			review=config.getProperty("reviewNodeAPI");
			approve=config.getProperty("approveNodeAPI");
			finalApprove=config.getProperty("finalApproveNodeAPI");//finalApproveNodeAPI
			agileurl=config.getProperty("agileurl");
			session = AgileSessionHelper.getCurrentSession(request);
			this.service = new GDPTransferService(session);
			chgInfor = this.service.loadChangeInfor(changeNumber);
			UserDTO user=this.service.loadUserInfor(userid);
			request.setAttribute("fullName", user.getUsername());
			hasPrivilege = this.service.containsUser(chgInfor, userid);
			if (!hasPrivilege) {
				String path = "default/error.jsp";
				request.setAttribute("error", "无权限");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + path);
				logger.debug("forward:" + path);
				requestDispatcher.forward(request, response);
				return;
			}
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HazyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (review.equals(chgInfor.getStatus())) {
			String path = "default/GDPTransfer.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + path);
			logger.debug("forward:" + path);
			requestDispatcher.forward(request, response);
		} else if (approve.equals(chgInfor.getStatus())) {
			request.setAttribute("agileurl", agileurl);
			String path = "default/GDPManager.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + path);
			logger.debug("forward:" + path);
			requestDispatcher.forward(request, response);
		}  else if (finalApprove.equals(chgInfor.getStatus())){
			request.setAttribute("agileurl", agileurl);
			String path = "default/GDPSummary.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + path);
			logger.debug("forward:" + path);
			requestDispatcher.forward(request, response);
		}else {
			String path = "default/error.jsp";
			request.setAttribute("error", "不在有效的流程状态中");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + path);
			logger.debug("forward Error:" + path);
			requestDispatcher.forward(request, response);

		}
	}

}
