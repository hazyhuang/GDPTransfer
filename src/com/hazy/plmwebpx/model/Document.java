package com.hazy.plmwebpx.model;

import net.sf.json.JSONObject;

public class Document {
	private Object ObjectID;
	private String Number;
	public Object getObjectID() {
		return ObjectID;
	}
	public void setObjectID(Object objectID) {
		ObjectID = objectID;
	}
	public String getNumber() {
		return Number;
	}
	public void setNumber(String number) {
		Number = number;
	}
	public JSONObject toJSON() {
		JSONObject jObj=new JSONObject();
		jObj.put("index", ObjectID);
		jObj.put("value", Number);
		return jObj;
		}

}
