package com.hazy.gdptransfer.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.hazy.gdptransfer.dao.AgileAPIDAO;
import com.hazy.gdptransfer.dao.AgileDataBaseDAO;
import com.hazy.plmwebpx.model.ChangeInfor;
import com.hazy.plmwebpx.model.ChangeRecord;
import com.hazy.plmwebpx.model.Document;
import com.hazy.plmwebpx.model.ECN;
import com.hazy.plmwebpx.model.ItemRecord;
import com.hazy.plmwebpx.model.ItemReviewRecord;
import com.hazy.plmwebpx.model.ListItem;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GDPTransferService {
	
	private AgileAPIDAO apiDAO=null;
	private AgileDataBaseDAO dbDAO = new AgileDataBaseDAO();
	public GDPTransferService(IAgileSession session) {
		this.apiDAO=new AgileAPIDAO(session);
	}
	public GDPTransferService() {
		
	}
	public ChangeInfor getChangeInfor(String changeNumber) throws APIException {
	  return 	this.apiDAO.getChangeInfor(changeNumber);
	}
	public JSONArray getECNJSON() throws SQLException{
		JSONArray array=new JSONArray();
		Collection<ECN> list=dbDAO.loadECN();
		Iterator<ECN> iter=list.iterator();
		while(iter.hasNext()) {
			ECN ecn=iter.next();
			array.add(ecn.toJSON());
			
		}
		return array;
	}
	
	public JSONArray getDocumentJSON() throws SQLException {
		JSONArray array=new JSONArray();
		Collection<Document> list=dbDAO.loadDocument();
		Iterator<Document> iter=list.iterator();
		while(iter.hasNext()) {
			Document ecn=iter.next();
			array.add(ecn.toJSON());
			
		}
		return array;
	}
	String listAPIName="SpecReview";
	public JSONArray getSpecReivewList() throws APIException {
		JSONArray array=new JSONArray();
		Collection<ListItem> list=apiDAO.getAgileList(listAPIName);
		Iterator<ListItem> iter=list.iterator();
		while(iter.hasNext()) {
			ListItem item=iter.next();
			array.add(item.toJSON());
			
		}
		return array;
	}
	
	public JSONObject getGDPTransfer(String changeNumber,String userid) throws APIException, SQLException {
		Collection<ItemRecord> itemRecords=this.apiDAO.loadAffectItem(changeNumber);
		for(ItemRecord itemRecord:itemRecords) {
			String itemNumber=itemRecord.getItemNumber();
			ItemReviewRecord itemReviewRecord=this.dbDAO.getGDPTransfer(changeNumber, itemNumber, userid);
		    ArrayList<ItemReviewRecord> list=new ArrayList<ItemReviewRecord>();
		    list.add(itemReviewRecord);
		    itemRecord.setItemReviewRecords(list);
		    
		}
		ChangeRecord changeRecord=new ChangeRecord(changeNumber);
		changeRecord.setItemRecords(itemRecords);
		return changeRecord.toJSON();
	}
	
	public JSONObject updateGDPTransfer(ChangeRecord changeRecord) {
	  
	   String chgNumber=changeRecord.getChangeNumber();
	   Collection<ItemRecord> list=changeRecord.getItemRecords();
	   for(ItemRecord itemRecord:list) {
		   Integer rowid=itemRecord.getRowid();
		   if(rowid==0) {
			   dbDAO.createItemRecord(chgNumber,itemRecord);
		   }else {
			   dbDAO.updateItemRecord(itemRecord);
		   }
	   }
		return changeRecord.toJSON();
	}
	
	public JSONObject getGDPManager(String changeNumber,String userid) {
		return null;
		
	}
	public JSONObject updateGDPManger(JSONObject str) {
		return null;
	}

}
