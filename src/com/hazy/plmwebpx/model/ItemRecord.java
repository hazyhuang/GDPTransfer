package com.hazy.plmwebpx.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ItemRecord {
	Integer rowid=0;
	String itemNumber;
	Integer itemid=0;
	String description;
	String rev;
	String managerReviewRecord;
	Collection<ItemReviewRecord> itemReviewRecords;
	
	
	public Integer getItemid() {
		return itemid;
	}

	public void setItemid(Integer itemid) {
		this.itemid = itemid;
	}

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
		record.setRowid(Integer.valueOf((String)jObj.get("rowid")));
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
	
	
/*	public void setItemReviewInof(DbConnect db, IRow row, ArrayList<IUser> reviewUserList) throws APIException, SQLException{
		
		String userIdList="";
		for (int i = 0;i<reviewUserList.size();i++){
			if (i==0){
				userIdList = "'"+reviewUserList.get(i).getObjectId()+"'";
			}else{
				userIdList = userIdList+",'"+reviewUserList.get(i).getObjectId()+"'";
			}
		}		
		ResultSet riRsOut = db.getItemReviewInof(row.getObjectId().toString(),userIdList);
		//if (riRsOut != null){
			while (riRsOut.next()) {
				ItemReviewRecord itemReviewRecord = new ItemReviewRecord(riRsOut.getString(1),riRsOut.getString(2),
						riRsOut.getString(3),riRsOut.getString(4),riRsOut.getString(5),riRsOut.getString(6),riRsOut.getString(7),
						riRsOut.getString(8),riRsOut.getString(9),riRsOut.getString(10));
				array.add(itemReviewRecord.toJSON());
			}
		//}
		riRsOut.close();
	}*/
	

}
