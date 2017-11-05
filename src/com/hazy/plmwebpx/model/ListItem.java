package com.hazy.plmwebpx.model;

import net.sf.json.JSONObject;

public class ListItem {
	String APIName;
	String text;
	public String getAPIName() {
		return APIName;
	}
	public void setAPIName(String id) {
		this.APIName = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public JSONObject toJSON() {
		JSONObject jObj=new JSONObject();
		jObj.put("value", text);
		jObj.put("index", APIName);
		return jObj;
		}

}
