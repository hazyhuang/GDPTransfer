package com.hazy.gdptransfer;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;


public class JSONServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JSONServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String json = readJSONString(request);
		System.out.println("JSON:"+json);
		JSONObject jobj=JSONObject.fromObject(json);
		System.out.println("JSONObject:"+jobj);
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