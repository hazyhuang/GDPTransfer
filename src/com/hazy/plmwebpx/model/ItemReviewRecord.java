package com.hazy.plmwebpx.model;

import net.sf.json.JSONObject;

public class ItemReviewRecord {
	String itemNumber;
	String userid;
	String username;
	String specReview;
	String reason;
	String documentNumber;
	String ECNNumber;

	public ItemReviewRecord(String itemNumber, String userid,String username, String specReview, String reason, String documentNumber,String ECNNumber) {
		this.itemNumber = itemNumber;
		this.userid = userid;
		this.username=username;
		this.specReview = specReview;
		this.reason = reason;
		this.documentNumber = documentNumber;
		this.ECNNumber = ECNNumber;
	}

	public JSONObject toJSON() {
		JSONObject jObj = new JSONObject();
		jObj.put("itemNumber", this.itemNumber);
		jObj.put("userid", this.userid);
		jObj.put("username", this.username);
		jObj.put("specReview", this.specReview);
		jObj.put("reason", this.reason);
		jObj.put("documentNumber", this.documentNumber);
		jObj.put("ECNNumber", this.ECNNumber);
		return jObj;
	}
}
