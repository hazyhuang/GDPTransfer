/*
 * Copyright 2012 - 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hazy.plmwebpx.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * @author Hua.Huang
 */
public class ChangeRecord {
	private Integer rowid=0;
	private String changeNumber;
	private String managerID;
	private String username;
	private String managerApprove;
	private String comment;
	private Collection<ItemRecord> itemRecords;
	
	public ChangeRecord() {
		
	}
public ChangeRecord(String chgNum) {
		this.changeNumber=chgNum;
	}
	public void setChangeRecord(String changeNumber,String  managerID ,String managerApprove) {
		this.changeNumber = changeNumber;
		this.managerID = managerID;
		this.managerApprove= managerApprove;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setItemRecords(Collection<ItemRecord> itemRecords){
		this.itemRecords=itemRecords;
	}
	
	public Integer getRowid() {
		return rowid;
	}
	public void setRowid(Integer rowid) {
		this.rowid = rowid;
	}
	public String getChangeNumber() {
		return changeNumber;
	}
	public void setChangeNumber(String changeNumber) {
		this.changeNumber = changeNumber;
	}
	public String getManagerID() {
		return managerID;
	}
	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}
	public String getManagerApprove() {
		return managerApprove;
	}
	public void setManagerApprove(String managerApprove) {
		this.managerApprove = managerApprove;
	}
	public Collection<ItemRecord> getItemRecords() {
		return itemRecords;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public JSONObject toJSON() {
			JSONObject jObj=new JSONObject();
			jObj.put("rowid", this.rowid);
			jObj.put("changeNumber", this.changeNumber);
			jObj.put("managerID", this.managerID);
			jObj.put("username", this.username);
			jObj.put("managerApprove", this.managerApprove);
			JSONArray array=new JSONArray();
			
			//array.fromObject(userlist);
			/* itemRecords.stream().forEach(itemReviewRecord ->{ 
				 array.add(itemReviewRecord.toJSON());
			 });*/ 
			for(ItemRecord itemRecord:itemRecords) {
				array.add(itemRecord);
			}
			jObj.put("itemRecords", array);
			return jObj;
	}
	
	public static ChangeRecord createChangeRecord(JSONObject jObj) {
		ChangeRecord chgRecord=new ChangeRecord();
		
		
		chgRecord.setRowid((Integer)jObj.get("rowid"));
		
		chgRecord.setChangeNumber((String)jObj.get("changeNumber"));
		chgRecord.setManagerID((String)jObj.get("managerID"));
		chgRecord.setManagerApprove((String)jObj.get("managerApprove"));
		
		JSONArray array=jObj.getJSONArray("itemRecords");
		Collection<ItemRecord> list=new ArrayList<ItemRecord>();
		Iterator iter=array.iterator();
		while(iter.hasNext()) {
			JSONObject child=(JSONObject)iter.next();
			ItemRecord itemRecord=ItemRecord.createItemRecord(child);
			list.add(itemRecord);
		}
		chgRecord.setItemRecords(list);
		return chgRecord;
	}
	

	
}
