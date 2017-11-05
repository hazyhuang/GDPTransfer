package com.hazy.gdptransfer.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import com.hazy.gdptransfer.dao.AgileAPIDAO;
import com.hazy.gdptransfer.dao.AgileDataBaseDAO;
import com.hazy.plmwebpx.model.Document;
import com.hazy.plmwebpx.model.ECN;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GDPTransferService {
	
	AgileAPIDAO apiDAO=new AgileAPIDAO();
	AgileDataBaseDAO dbDAO = new AgileDataBaseDAO();
	
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
	
	public JSONArray getSpecReivewList() {
		return null;
	}
	
	public JSONObject getGDPTransfer(String changeNumber,String userid) {
		return null;
	}
	
	public JSONObject updateGDPTransfer(JSONObject str) {
		return null;
	}
	
	public JSONObject getGDPManager(String changeNumber,String userid) {
		return null;
		
	}
	public JSONObject updateGDPManger(JSONObject str) {
		return null;
	}

}
