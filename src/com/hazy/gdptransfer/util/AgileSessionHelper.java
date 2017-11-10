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
package com.hazy.gdptransfer.util;

import com.agile.api.APIException;
import com.agile.api.AgileSessionFactory;
import com.agile.api.IAgileSession;
import com.hazy.common.HazyException;

import java.util.HashMap;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
/**
 * 
 * @author Hua.Huang
 */
public class AgileSessionHelper {
	private static Logger logger = Logger.getLogger(AgileSessionHelper.class);
	private static IAgileSession session = null;
	private static AgileSessionFactory factory;
	private static String USERNAME = "";
	private static String PASSWORD = "";
	private static String Session_WebKey="Agile_Session_Instance";

	public static IAgileSession getSession(Properties config) throws APIException {

		HashMap<Object, Object> params = new HashMap<Object, Object>();

		factory = AgileSessionFactory.getInstance(config.getProperty("agileurl"));
		USERNAME = config.getProperty("agileuser");
		PASSWORD = config.getProperty("agilepwd");
		params.put(AgileSessionFactory.USERNAME, USERNAME);
		params.put(AgileSessionFactory.PASSWORD, PASSWORD);

		session = factory.createSession(params);
		return session;

	}

	public static IAgileSession getCurrentSession( HttpServletRequest request) throws APIException, HazyException {
		Properties config = Helper.loadConfig();
		IAgileSession agilesession = null;
		try {
			if (request.getSession().getAttribute(Session_WebKey) == null) {
				agilesession = AgileSessionHelper.getSession(config);
				request.getSession().setAttribute(Session_WebKey, agilesession);
			} else {
				/* User change */
				agilesession = (IAgileSession) request.getSession().getAttribute(Session_WebKey);
				if (agilesession != null & agilesession.isOpen()) {
					return agilesession;
				} else {
					agilesession = AgileSessionHelper.getSession(config);
					request.getSession().setAttribute(Session_WebKey, agilesession);
				}
			}
		} catch (APIException e) {
			e.printStackTrace();
			logger.error(e);
			agilesession = AgileSessionHelper.getSession(config);
			request.getSession().setAttribute(Session_WebKey, agilesession);

		}

		return agilesession;
	}

}