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
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.hazy.common.HazyException;
import com.hazy.gdptransfer.util.Helper;

/**
 * 
 * @author Hua.Huang
 */
public class GDPSummaryServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(GDPSummaryServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("agile.userName");
		String changeNumber = request.getParameter("agile.1047");
		request.getSession().setAttribute("agile.userName", userid);
		request.getSession().setAttribute("agile.1047", changeNumber);
		String agileurl = "";
		try {
			Properties config = Helper.loadConfig();
			agileurl = config.getProperty("agileurl");

		} catch (HazyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("agileurl", agileurl);
		String path = "default/GDPSummary.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + path);
		logger.debug("forward:" + path);
		requestDispatcher.forward(request, response);

	}

}
