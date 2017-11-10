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
import com.hazy.plmwebpx.model.UserDTO;
import com.hazy.plmwebpx.model.ChangeDTO;
import com.hazy.plmwebpx.model.ChangeRecord;
import com.hazy.plmwebpx.model.DocumentDTO;
import com.hazy.plmwebpx.model.ECNDTO;
import com.hazy.plmwebpx.model.ItemRecord;
import com.hazy.plmwebpx.model.ItemReviewRecord;
import com.hazy.plmwebpx.model.ListItemDTO;

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

	public UserDTO loadUserInfor(String userid) throws SQLException {
		return this.dbDAO.getUserInfor(userid);
	}

	public ChangeDTO loadChangeInfor(String changeNumber) throws APIException {
		return this.apiDAO.loadChangeDTO(changeNumber);
	}

	public ChangeDTO loadChangeInfor(String changeNumber, String statusAPIName) throws APIException {
		return this.apiDAO.loadChangeDTO(changeNumber, statusAPIName);
	}

	public boolean containsUser(ChangeDTO chg, String userid) {
		Collection<UserDTO> list = chg.getReviewers();
		for (UserDTO user : list) {
			if (userid.equals(user.getLoginid())) {
				return true;
			}
		}
		return false;
	}

	public JSONArray loadAllECNJSON() throws SQLException {
		JSONArray array = new JSONArray();
		Collection<ECNDTO> list = dbDAO.loadAllECN();
		Iterator<ECNDTO> iter = list.iterator();
		while (iter.hasNext()) {
			ECNDTO ecn = iter.next();
			array.add(ecn.toJSON());

		}
		return array;
	}

	public JSONArray loadAllDocumentJSON() throws SQLException {
		JSONArray array = new JSONArray();
		Collection<DocumentDTO> list = dbDAO.loadAllDocument();
		Iterator<DocumentDTO> iter = list.iterator();
		while (iter.hasNext()) {
			DocumentDTO ecn = iter.next();
			array.add(ecn.toJSON());

		}
		return array;
	}

	public JSONArray loadSpecReivewList() throws APIException {
		JSONArray array = new JSONArray();
		Collection<ListItemDTO> list = apiDAO.loadSingleList(listAPIName);
		Iterator<ListItemDTO> iter = list.iterator();
		while (iter.hasNext()) {
			ListItemDTO item = iter.next();
			array.add(item.toJSON());

		}
		return array;
	}

	public JSONObject loadGDPTransfer(String changeNumber, String userid) throws APIException, SQLException {
		Collection<ItemRecord> itemRecords = this.apiDAO.loadAffectItem(changeNumber);
		for (ItemRecord itemRecord : itemRecords) {
			String itemNumber = itemRecord.getItemNumber();
			ItemReviewRecord itemReviewRecord = this.dbDAO.loadItemReviewRecord(changeNumber, itemNumber, userid);
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
			Integer rowid = getItemReviewRecordID(itemRecord);
			if (rowid == 0) {
				dbDAO.createItemRecord(chgNumber, itemRecord);
			} else {
				dbDAO.updateItemRecord(itemRecord);
			}
		}
		return changeRecord.toJSON();
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

	public JSONObject loadGDPManager(String changeNumber, String userid) throws APIException, SQLException {
		Collection<ItemRecord> itemRecords = this.apiDAO.loadAffectItem(changeNumber);
		Collection<UserDTO> users = this.apiDAO.loadFunctionUsers(changeNumber, userid);
		for (ItemRecord itemRecord : itemRecords) {
			String itemNumber = itemRecord.getItemNumber();
			Collection<ItemReviewRecord> list = this.dbDAO.loadItemReviewRecords(changeNumber, itemNumber, users);
			this.dbDAO.loadItemRecord(itemRecord, changeNumber, userid);
			itemRecord.setItemReviewRecords(list);
		}
		ChangeRecord changeRecord = new ChangeRecord(changeNumber);
		this.dbDAO.loadChangeRecord(changeRecord, changeNumber, userid);

		changeRecord.setManagerID(userid);
		UserDTO agileUser = this.dbDAO.getUserInfor(userid);
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
