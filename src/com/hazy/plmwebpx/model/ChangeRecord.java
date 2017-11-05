package com.hazy.plmwebpx.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.agile.api.APIException;
import com.agile.api.ChangeConstants;
import com.agile.api.IChange;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.IUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ChangeRecord {
	String changeNumber;
	String managerID;
	String managerApprove;
	Collection<ItemRecord> itemRecords;
	
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
	
	public void setItemRecords(Collection<ItemRecord> itemRecords){
		this.itemRecords=itemRecords;
	}
	
/*	public Collection<ItemRecord> getItemRecords() {
		return itemRecords;
	}

	public void setItemRecords(Collection<ItemRecord> itemRecords) {
		this.itemRecords = itemRecords;
	}*/



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
	public JSONObject toJSON() {
			JSONObject jObj=new JSONObject();
			jObj.put("changeNumber", this.changeNumber);
			jObj.put("managerID", this.managerID);
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
	
/*	public void setItemRecordInfo(DbConnect db, IChange change, ArrayList<IUser> reviewUserList,IUser managerUser){
		JSONArray itemRecordsArry = new JSONArray();
		try {
			ResultSet rsOut = db.getmanagerApprover(change.getObjectId().toString(), managerUser.getObjectId().toString());
			String manReviewInfo = "";
			if (rsOut.next()){
				manReviewInfo = rsOut.getString(1);
				System.out.println(rsOut.getString(1));
			}	
			this.setChangeRecord(change.getName(),managerUser.getObjectId().toString(),manReviewInfo);
			rsOut.close();
			
			ITable table = change.getTable(ChangeConstants.TABLE_AFFECTEDITEMS);		
			Iterator it = table.iterator();		
			while (it.hasNext()){
				IRow row = (IRow)it.next();
				ResultSet trsOut = db.getItemReord(row.getObjectId().toString());
				//if (trsOut != null){
					while (trsOut.next()) {
						ItemRecord itemRecord = new ItemRecord(trsOut.getString(1),trsOut.getString(2),trsOut.getString(3),"");
						itemRecord.setItemReviewInof(db,row,reviewUserList);
						itemRecordsArry.add(itemRecord.toJSON());
					}					 
				//}
				trsOut.close();
			}
			this.setItemRecords(itemRecordsArry);
			
			
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
}
