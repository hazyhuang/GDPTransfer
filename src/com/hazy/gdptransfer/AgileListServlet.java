/*
 * Copyright 2012 - 2017 the original author or authors.
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

/**
 * 
 * @author Hua.Huang
 */
public class AgileListServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(AgileListServlet.class);

	private GDPTransferService service = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	static String Agile_List_WebKey = "Agile_List_WebKey";

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IAgileSession session = null;
		JSONArray jsonArray = null;
		String action = "";
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		try {
			session = AgileSessionHelper.getCurrentSession(request);
			this.service = new GDPTransferService(session);
			if ("ecn".equals(action)) {
				jsonArray = service.loadAllECNJSON();
			} else if ("doc".equals(action)) {
				jsonArray = service.loadAllDocumentJSON();
			} else if ("specreview".equals(action)) {
				jsonArray = (JSONArray) getServletContext().getAttribute(Agile_List_WebKey);
				if (jsonArray == null) {
					jsonArray = service.loadSpecReivewList();
					getServletContext().setAttribute(Agile_List_WebKey, jsonArray);
				}

			}
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HazyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		logger.info("JSON:" + jsonArray);
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().write(jsonArray.toString());

	}

}
