package com.hazy.plmwebpx.model;

import java.util.Collection;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ChangeRecord {
	String changeNumber;
	String managerID;
	String managerApprove;
	Collection<ItemRecord> itemRecords;

	public ChangeRecord(String changeNumber,String  managerID ,String managerApprove) {
		this.changeNumber = changeNumber;
		this.managerID = managerID;
		this.managerApprove= managerApprove;
	}
	
	public Collection<ItemRecord> getItemRecords() {
		return itemRecords;
	}

	public void setItemRecords(Collection<ItemRecord> itemRecords) {
		this.itemRecords = itemRecords;
	}

	public JSONObject toJSON() {
			JSONObject jObj=new JSONObject();
			jObj.put("changeNumber", this.changeNumber);
			jObj.put("managerID", this.managerID);
			jObj.put("managerApprove", this.managerApprove);
			JSONArray array=new JSONArray();

			Iterator<ItemRecord> iter= itemRecords.iterator();
			while(iter.hasNext()) {
				ItemRecord line=iter.next();
				array.add(line.toJSON());
			}
			jObj.put("itemRecords", array);
			return jObj;
	
		
	}

}
