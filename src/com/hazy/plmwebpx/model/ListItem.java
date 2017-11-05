package com.hazy.plmwebpx.model;

import net.sf.json.JSONObject;

public class ListItem {
	String id;
	String text;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public JSONObject toJSON() {
		JSONObject jObj=new JSONObject();
		jObj.put("index", text);
		jObj.put("value", id);
		return jObj;
		}

}
