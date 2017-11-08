package com.hazy.plmwebpx.model;

import net.sf.json.JSONObject;

public class AgileUser {
	private String loginid;
	private String username;
	public AgileUser(String userid){
		this.loginid=userid;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public JSONObject toJSON() {
		JSONObject jObj = new JSONObject();
		jObj.put("loginid", this.loginid);
		jObj.put("username", this.username);
	
		return jObj;
	}
     
}
