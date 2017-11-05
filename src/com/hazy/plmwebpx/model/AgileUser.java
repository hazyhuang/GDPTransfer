package com.hazy.plmwebpx.model;

public class AgileUser {
	String loginid;
	String username;
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
     
}
