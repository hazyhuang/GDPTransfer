package com.hazy.plmwebpx.model;


import org.apache.log4j.Logger;
import net.sf.json.JSONObject;

public class ItemReviewRecord {
	private static Logger logger = Logger.getLogger(ItemReviewRecord.class);
	private Integer rowid=0;
	private String userid;
	private String specReview;
	private String specReviewValue;
	private String reason;
	private Integer docId=0;
	private Integer ecnId=0;
	private String username;
	private String docNumber;
	private String ecnNumber;
	public ItemReviewRecord() {
		
	}

	public static ItemReviewRecord createItemReviewRecord(JSONObject jObj) {
		logger.debug("ItemReviewRecord:"+jObj);
		ItemReviewRecord record=new ItemReviewRecord();

		Object rowidObj=jObj.get("rowid");
		if(rowidObj!=null&&!"".equals(rowidObj)) {
		record.setRowid(Integer.valueOf((String)rowidObj));
		}
		record.setUserid((String)jObj.get("userid"));
		record.setSpecReview((String)jObj.get("specReview"));
		record.setReason((String)jObj.get("reason"));
		

		Object docidObj=jObj.get("docId");
		if(docidObj!=null&&!"".equals(docidObj)) {
		record.setDocId(Integer.valueOf((String)docidObj));
		}
		Object ecnIdObj=jObj.get("ecnId");
		if(ecnIdObj!=null&&!"".equals(ecnIdObj)) {
		record.setEcnId(Integer.valueOf((String)ecnIdObj));
		}
		
		record.setDocNumber((String)jObj.get("docNumber"));
		record.setEcnNumber((String)jObj.get("ecnNumber"));
		record.setUsername((String)jObj.get("username"));
		record.setSpecReviewValue((String)jObj.get("specReviewValue"));
		return record;
	}
	
	public String getSpecReviewValue() {
		return specReviewValue;
	}

	public void setSpecReviewValue(String specReviewValue) {
		this.specReviewValue = specReviewValue;
	}

	public JSONObject toJSON() {
		JSONObject jObj = new JSONObject();
		jObj.put("rowid", this.rowid);
		jObj.put("userid", this.userid);
		jObj.put("specReview", this.specReview);
		jObj.put("specReviewValue", this.specReviewValue);
		jObj.put("reason", this.reason);
		jObj.put("docId", this.docId);
		jObj.put("ecnId", this.ecnId);
		jObj.put("username", this.username);
		jObj.put("docNumber", this.docNumber);
		jObj.put("ecnNumber", this.ecnNumber);
		return jObj;
	}
	public Integer getRowid() {
		return rowid;
	}
	public void setRowid(Integer rowid) {
		this.rowid = rowid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSpecReview() {
		return specReview;
	}
	public void setSpecReview(String specReview) {
		this.specReview = specReview;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getDocId() {
		return docId;
	}
	public void setDocId(Integer docId) {
		this.docId = docId;
	}
	public Integer getEcnId() {
		return ecnId;
	}
	public void setEcnId(Integer ecnId) {
		this.ecnId = ecnId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public String getEcnNumber() {
		return ecnNumber;
	}
	public void setEcnNumber(String ecnNumber) {
		this.ecnNumber = ecnNumber;
	}

}
