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
package com.hazy.gdptransfer.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.hazy.common.HazyException;
import com.hazy.gdptransfer.dao.AgileAPIDAO;
import com.hazy.gdptransfer.dao.AgileDataBaseDAO;
import com.hazy.gdptransfer.util.Helper;
import com.hazy.plmwebpx.model.AgileUser;
import com.hazy.plmwebpx.model.ChangeInfor;
import com.hazy.plmwebpx.model.ChangeRecord;
import com.hazy.plmwebpx.model.Document;
import com.hazy.plmwebpx.model.ECN;
import com.hazy.plmwebpx.model.ItemRecord;
import com.hazy.plmwebpx.model.ItemReviewRecord;
import com.hazy.plmwebpx.model.ListItem;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * @author Hua.Huang
 */
public class GDPTransferService {
	private static Logger logger = Logger.getLogger(GDPTransferService.class);
	private AgileAPIDAO apiDAO = null;
	private AgileDataBaseDAO dbDAO = new AgileDataBaseDAO();
	private String listAPIName = "RM_DIC_SpecReview";
    
	public GDPTransferService(IAgileSession session) {
		this();
		this.apiDAO = new AgileAPIDAO(session);
	}

	public GDPTransferService() {
		Properties config = null;
		try {
			config = Helper.loadConfig();
			logger.debug("init Config");
		} catch (HazyException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		this.listAPIName = config.getProperty("ListAPI");
	}
    
	public AgileDataBaseDAO getDbDAO() {
		return dbDAO;
	}

	public AgileUser getUserInfor(String userid) throws SQLException {
		return this.dbDAO.getUserInfor(userid);
	}

	public ChangeInfor getChangeInfor(String changeNumber) throws APIException {
		return this.apiDAO.getChangeInfor(changeNumber);
	}

	public ChangeInfor getChangeInfor(String changeNumber, String statusAPIName) throws APIException {
		return this.apiDAO.getChangeInfor(changeNumber, statusAPIName);
	}

	public boolean containsUser(ChangeInfor chg, String userid) {
		Collection<AgileUser> list = chg.getReviewers();
		for (AgileUser user : list) {
			if (userid.equals(user.getLoginid())) {
				return true;
			}
		}
		return false;
	}

	public JSONArray getECNJSON() throws SQLException {
		JSONArray array = new JSONArray();
		Collection<ECN> list = dbDAO.loadECN();
		Iterator<ECN> iter = list.iterator();
		while (iter.hasNext()) {
			ECN ecn = iter.next();
			array.add(ecn.toJSON());

		}
		return array;
	}

	public JSONArray getDocumentJSON() throws SQLException {
		JSONArray array = new JSONArray();
		Collection<Document> list = dbDAO.loadDocument();
		Iterator<Document> iter = list.iterator();
		while (iter.hasNext()) {
			Document ecn = iter.next();
			array.add(ecn.toJSON());

		}
		return array;
	}

	public JSONArray getSpecReivewList() throws APIException {
		JSONArray array = new JSONArray();
		Collection<ListItem> list = apiDAO.getAgileList(listAPIName);
		Iterator<ListItem> iter = list.iterator();
		while (iter.hasNext()) {
			ListItem item = iter.next();
			array.add(item.toJSON());

		}
		return array;
	}

	public JSONObject getGDPTransfer(String changeNumber, String userid) throws APIException, SQLException {
		Collection<ItemRecord> itemRecords = this.apiDAO.loadAffectItem(changeNumber);
		for (ItemRecord itemRecord : itemRecords) {
			String itemNumber = itemRecord.getItemNumber();
			ItemReviewRecord itemReviewRecord = this.dbDAO.getGDPTransfer(changeNumber, itemNumber, userid);
			ArrayList<ItemReviewRecord> list = new ArrayList<ItemReviewRecord>();
			list.add(itemReviewRecord);
			itemRecord.setItemReviewRecords(list);
			this.dbDAO.loadItemRecord(itemRecord, changeNumber, userid);

		}
		ChangeRecord changeRecord = new ChangeRecord(changeNumber);
		changeRecord.setItemRecords(itemRecords);
		return changeRecord.toJSON();
	}

	public JSONObject updateGDPTransfer(ChangeRecord changeRecord) throws SQLException {

		String chgNumber = changeRecord.getChangeNumber();
		Collection<ItemRecord> list = changeRecord.getItemRecords();

		for (ItemRecord itemRecord : list) {
			//fillAgileObjectID(itemRecord);
			Integer rowid = getItemReviewRecordID(itemRecord);
			if (rowid == 0) {
				dbDAO.createItemRecord(chgNumber, itemRecord);
			} else {
				dbDAO.updateItemRecord(itemRecord);
			}
		}
		return changeRecord.toJSON();
	}

	private void fillAgileObjectID(ItemRecord itemRecord) throws SQLException {
		Collection<ItemReviewRecord> ItemReviewRecords = itemRecord.getItemReviewRecords();

		ItemReviewRecord itemReviewRecord = null;
		for (ItemReviewRecord record : ItemReviewRecords) {
			itemReviewRecord = record;
		}
		if (itemReviewRecord != null) {
			String ecnNum = itemReviewRecord.getEcnNumber();
			if (ecnNum != null) {
				Integer ecnid = getECNID(ecnNum);
				itemReviewRecord.setEcnId(ecnid);
			}
			String docNum = itemReviewRecord.getDocNumber();
			if (docNum != null) {
				Integer docid = getDocID(docNum);
				itemReviewRecord.setDocId(docid);
			}
		}
	}

	private Integer getDocID(String docNum) throws SQLException {
		return this.dbDAO.getDocID(docNum);

	}

	private Integer getECNID(String ecnNum) throws SQLException {
		// TODO Auto-generated method stub
		return this.dbDAO.getECNID(ecnNum);
	}

	private Integer getItemReviewRecordID(ItemRecord itemRecord) {
		Collection<ItemReviewRecord> ItemReviewRecords = itemRecord.getItemReviewRecords();

		ItemReviewRecord itemReviewRecord = null;
		for (ItemReviewRecord record : ItemReviewRecords) {
			itemReviewRecord = record;
		}
		if (itemReviewRecord != null) {
			return itemReviewRecord.getRowid();
		}
		return 0;
	}

	public JSONObject getGDPManager(String changeNumber, String userid) throws APIException, SQLException {
		Collection<ItemRecord> itemRecords = this.apiDAO.loadAffectItem(changeNumber);
		Collection<AgileUser> users = this.apiDAO.getFunctionUsers(changeNumber, userid);
		for (ItemRecord itemRecord : itemRecords) {
			String itemNumber = itemRecord.getItemNumber();
			Collection<ItemReviewRecord> list = this.dbDAO.getGDPManager(changeNumber, itemNumber, users);
			this.dbDAO.loadItemRecord(itemRecord, changeNumber, userid);
			itemRecord.setItemReviewRecords(list);
		}
		ChangeRecord changeRecord = new ChangeRecord(changeNumber);
		this.dbDAO.loadChangeRecord(changeRecord, changeNumber, userid);
		
		changeRecord.setManagerID(userid);
        AgileUser agileUser=this.dbDAO.getUserInfor(userid);
        changeRecord.setUsername(agileUser.getUsername());
		changeRecord.setItemRecords(itemRecords);
		return changeRecord.toJSON();

	}

	public JSONObject updateGDPManager(ChangeRecord changeRecord) throws SQLException {
		String chgNumber = changeRecord.getChangeNumber();
		String userid = changeRecord.getManagerID();
		Collection<ItemRecord> list = changeRecord.getItemRecords();
		for (ItemRecord itemRecord : list) {
			Integer rowid = itemRecord.getRowid();
			if (rowid == 0) {
				dbDAO.createMangerReview(chgNumber, userid, itemRecord);
			} else {
				dbDAO.updateMangerReview(chgNumber, userid, itemRecord);
			}
		}
		Integer approveRowid = changeRecord.getRowid();
		if (approveRowid == 0) {
			dbDAO.createMangerApprove(chgNumber, userid, changeRecord);
		} else {
			dbDAO.updateMangerApprove(chgNumber, userid, changeRecord);
		}
		return changeRecord.toJSON();
	}

}
