package com.hazy.plmwebpx.model;

import net.sf.json.JSONObject;

public class ECN {
	Object ObjectID;
	String Number;
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
	jObj.put("index", Number);
	jObj.put("value", Number);
	return jObj;
	}
	

}
