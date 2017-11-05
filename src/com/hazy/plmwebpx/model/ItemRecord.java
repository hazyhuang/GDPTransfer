package com.hazy.plmwebpx.model;

import java.util.Collection;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ItemRecord {
	String itemNumber;
	String rowid;
	String description;
	String rev;
	String managerReviewRecord;
	Collection<ItemReviewRecord> itemReviewRecords;
	public ItemRecord(String itemNumber ,String rowid,String description ,String rev, String managerReviewRecord) {
		this.itemNumber=itemNumber;
		this.rowid=rowid;
		this.description =description;
		this.rev =rev;
		this.managerReviewRecord =managerReviewRecord;
	}
	
	public Collection<ItemReviewRecord> getItemReviewRecords() {
		return itemReviewRecords;
	}

	public void setReviewRecords(Collection<ItemReviewRecord> itemReviewRecords) {
		this.itemReviewRecords = itemReviewRecords;
	}

	public JSONObject toJSON() {
		JSONObject jObj=new JSONObject();
		jObj.put("itemNumber", this.itemNumber);
		jObj.put("rowid", this.rowid);
		jObj.put("description", this.description);
		jObj.put("rev", this.rev);
		jObj.put("managerReviewRecord", this.managerReviewRecord);
		JSONArray array=new JSONArray();
	
		 Iterator<ItemReviewRecord> iter= itemReviewRecords.iterator();
			while(iter.hasNext()) {
				ItemReviewRecord line=iter.next();
				array.add(line.toJSON());
			}
		jObj.put("itemReviewRecords",array);
		return jObj;

	
}
	

}
