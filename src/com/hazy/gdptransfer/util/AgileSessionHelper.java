package com.hazy.gdptransfer.util;

import com.agile.api.APIException;
import com.agile.api.AgileSessionFactory;
import com.agile.api.IAgileSession;
import com.hazy.common.HazyException;

import java.util.HashMap;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

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