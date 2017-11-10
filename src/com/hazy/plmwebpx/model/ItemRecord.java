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
public class ItemRecord {
	private Integer rowid=0;
	private String itemNumber;
	private Integer itemid=0;
	private String description;
	private String rev;
	private String managerReviewRecord;
	private Collection<ItemReviewRecord> itemReviewRecords;
	

	public ItemRecord(String itemNumber ,String description ,String rev, String managerReviewRecord) {
		this.itemNumber=itemNumber;
		this.description =description;
		this.rev =rev;
		this.managerReviewRecord = managerReviewRecord;
	}
	
	public ItemRecord(String itemNumber ,String description ,String rev) {
		this.itemNumber=itemNumber;
		this.description =description;
		this.rev =rev;
		
	}
	
	public ItemRecord() {
	}
	
	public Integer getItemid() {
		return itemid;
	}

	public void setItemid(Integer itemid) {
		this.itemid = itemid;
	}

	
	public Integer getRowid() {
		return rowid;
	}

	public void setRowid(Integer rowid) {
		this.rowid = rowid;
	}

	public Collection<ItemReviewRecord> getItemReviewRecords() {
		return itemReviewRecords;
	}

	public void setReviewRecords(Collection<ItemReviewRecord> itemReviewRecords) {
		this.itemReviewRecords = itemReviewRecords;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public String getManagerReviewRecord() {
		return managerReviewRecord;
	}

	public void setManagerReviewRecord(String managerReviewRecord) {
		this.managerReviewRecord = managerReviewRecord;
	}



	public void setItemReviewRecords(Collection<ItemReviewRecord> itemReviewRecords) {
		this.itemReviewRecords = itemReviewRecords;
	}
	

	public JSONObject toJSON() {
		JSONObject jObj=new JSONObject();
		jObj.put("rowid", this.rowid);
		jObj.put("itemid", this.itemid);
		jObj.put("itemNumber", this.itemNumber);
		jObj.put("description", this.description);
		jObj.put("rev", this.rev);
		jObj.put("managerReviewRecord", this.managerReviewRecord);	
		JSONArray array=new JSONArray();
		//array.fromObject(userlist);
		/* itemRecords.stream().forEach(itemReviewRecord ->{ 
			 array.add(itemReviewRecord.toJSON());
		 });*/ 
		for(ItemReviewRecord itemReviewRecord:itemReviewRecords) {
			array.add(itemReviewRecord);
		}
		jObj.put("itemReviewRecords",array);
		return jObj;
	}

	public static ItemRecord createItemRecord(JSONObject jObj) {
		ItemRecord record=new ItemRecord();
		
		Object rowidObj=jObj.get("rowid");
		if(rowidObj!=null&&!"".equals(rowidObj)) {
		record.setRowid(Integer.valueOf((String)rowidObj));
		}
		
		Object itemidObj=jObj.get("itemid");
		if(itemidObj!=null&&!"".equals(itemidObj)) {
		record.setItemid(Integer.valueOf((String)itemidObj));
		}
		record.setItemNumber((String)jObj.get("itemNumber"));
		record.setDescription((String)jObj.get("description"));
		record.setRev((String)jObj.get("rev"));
		record.setManagerReviewRecord((String)jObj.get("managerReviewRecord"));
		JSONArray array=jObj.getJSONArray("itemReviewRecords");
		Collection<ItemReviewRecord> list=new ArrayList<ItemReviewRecord>();
		Iterator iter=array.iterator();
		while(iter.hasNext()) {
			JSONObject child=(JSONObject)iter.next();
			ItemReviewRecord itemReviewRecord=ItemReviewRecord.createItemReviewRecord(child);
			list.add(itemReviewRecord);
		}
		record.setItemReviewRecords(list);
		return record;
	}

	

}
