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
package com.hazy.gdptransfer.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.agile.api.APIException;
import com.agile.api.ChangeConstants;
import com.agile.api.IAdminList;
import com.agile.api.IAgileList;
import com.agile.api.IAgileSession;
import com.agile.api.ICell;
import com.agile.api.IChange;
import com.agile.api.IRow;
import com.agile.api.ISignoffReviewer;
import com.agile.api.IStatus;
import com.agile.api.ITable;
import com.agile.api.IUser;
import com.agile.api.IUserGroup;
import com.agile.api.UserConstants;
import com.agile.api.UserGroupConstants;
import com.agile.api.WorkflowConstants;
import com.hazy.common.HazyUtil;
import com.hazy.plmwebpx.model.UserDTO;
import com.hazy.plmwebpx.model.ChangeDTO;
import com.hazy.plmwebpx.model.ItemRecord;
import com.hazy.plmwebpx.model.ListItemDTO;
/**
 * 
 * @author Hua.Huang
 */
public class AgileAPIDAO {
	private static Logger logger = Logger.getLogger(AgileAPIDAO.class);

	IAgileSession session = null;
	public AgileAPIDAO(IAgileSession session) {
		this.session=session;
	}
	public Collection<ListItemDTO> loadSingleList(String listAPIName) throws APIException {
		IAdminList adminList = session.getAdminInstance().getListLibrary().getAdminList(listAPIName);
		logger.debug(adminList.getAPIName()+":"+adminList.getName());
	    Collection<ListItemDTO> list=new ArrayList<ListItemDTO>();
		IAgileList agileList = adminList.getValues();
		@SuppressWarnings("unchecked")
		Collection<IAgileList> children = agileList.getChildNodes();
		logger.debug("Children:"+children);
		for (IAgileList child : children) {
			ListItemDTO item=new ListItemDTO();
			item.setAPIName(child.getAPIName());
			logger.debug(child.getValue()+":"+child.getAPIName());
			item.setText((String)child.getValue());
			list.add(item);
		}
	
		return list;
	}
	
	public ChangeDTO loadChangeDTO(String changeNumber) throws APIException{
		ChangeDTO chgInfor=new ChangeDTO(changeNumber);
		IChange change=HazyUtil.getAgileAPIHelper().loadChange(session, changeNumber);
		IStatus istatus=change.getStatus();
		chgInfor.setStatus(istatus.getAPIName());
		ISignoffReviewer[] acknowledgers=change.getAllReviewers(istatus, WorkflowConstants.USER_ACKNOWLEDGER );
		ISignoffReviewer[] approvers=change.getAllReviewers(istatus, WorkflowConstants.USER_APPROVER );
		
		Collection<UserDTO> users=new ArrayList<UserDTO>();
		for(ISignoffReviewer approver:approvers) {
			String userid=approver.getReviewer().getName();
			UserDTO user=new UserDTO(userid);
			users.add(user);
		}
		for(ISignoffReviewer acknowledger:acknowledgers) {
			String userid=acknowledger.getReviewer().getName();
			UserDTO user=new UserDTO(userid);
			users.add(user);
		}
		chgInfor.setReviewers(users);
		return chgInfor;
	}
	
	public ChangeDTO loadChangeDTO(String changeNumber,String statusAPIName) throws APIException{
		ChangeDTO chgInfor=new ChangeDTO(changeNumber);
		IChange change=HazyUtil.getAgileAPIHelper().loadChange(session, changeNumber);
		IStatus[] statuses=change.getWorkflow().getStates();
		IStatus approveStatus=null;;
		for(IStatus status:statuses) {
			if(status.getAPIName().equals(statusAPIName)) {
				approveStatus=status;
			}
		}
		chgInfor.setStatus(statusAPIName);
		ISignoffReviewer[] acknowledgers=change.getAllReviewers(approveStatus, WorkflowConstants.USER_ACKNOWLEDGER );
		ISignoffReviewer[] approvers=change.getAllReviewers(approveStatus, WorkflowConstants.USER_APPROVER );
		
		Collection<UserDTO> users=new ArrayList<UserDTO>();
		for(ISignoffReviewer approver:approvers) {
			String userid=approver.getReviewer().getName();
			UserDTO user=new UserDTO(userid);
			users.add(user);
		}
		for(ISignoffReviewer acknowledger:acknowledgers) {
			String userid=acknowledger.getReviewer().getName();
			UserDTO user=new UserDTO(userid);
			users.add(user);
		}
		chgInfor.setReviewers(users);
		return chgInfor;
	}
	
	public Collection<ItemRecord> loadAffectItem(String changeNumber) throws APIException{
		IChange change=HazyUtil.getAgileAPIHelper().loadChange(session, changeNumber);
		ITable table=change.getTable(ChangeConstants.TABLE_AFFECTEDITEMS);
		Iterator iter= table.iterator();
		Collection<ItemRecord> itemlist=new ArrayList<ItemRecord>();
		while(iter.hasNext()) {
		    IRow row=	(IRow)iter.next();
		    
		    String itemNum=(String)row.getValue(ChangeConstants.ATT_AFFECTED_ITEMS_ITEM_NUMBER);
		  String desc=(String)  row.getValue(ChangeConstants.ATT_AFFECTED_ITEMS_ITEM_DESCRIPTION);
		  String rev=(String) row.getValue(ChangeConstants.ATT_AFFECTED_ITEMS_NEW_REV);
		  ItemRecord itemRecord=new ItemRecord(itemNum,desc,rev);
		  itemlist.add(itemRecord);
		}
		return itemlist;
		
	}

	public Collection<UserDTO> loadFunctionUsers(String changeNumber,String userid) throws APIException {
		IUser manager=HazyUtil.getAgileAPIHelper().loadUser(session, userid);
		IChange change=HazyUtil.getAgileAPIHelper().loadChange(session, changeNumber);
		Collection<UserDTO> users=new ArrayList<UserDTO>();
		ArrayList<IUser> iusers=this.loadReviewUserList(change, manager);
		for(IUser user:iusers) {
			
			UserDTO aUser=new UserDTO((String)user.getValue(UserConstants.ATT_GENERAL_INFO_USER_ID));
			logger.debug("transferman:"+aUser.getLoginid());
			users.add(aUser);
		}
		return users;
	}
	
	private ArrayList<IUser> loadReviewUserList(IChange change, IUser user) throws APIException {
		ArrayList<IUser> userList = new ArrayList<IUser>();
		ICell cell = change.getCell(ChangeConstants.ATT_COVER_PAGE_FUNCTIONALTEAM);
		IAgileList list = (IAgileList) cell.getValue();
		IAgileList[] select = list.getSelection();
		for (int i=0;i<select.length;i++){
			IAgileList[] managerList = null;
			IAgileList[] reviewList = null;			
			IUserGroup a = (IUserGroup) select[i].getValue();
			ITable table = a.getTable(UserGroupConstants.TABLE_JOBFUNCTION);
			Iterator itr = table.iterator();
			while (itr.hasNext()){
				IRow tempRow = (IRow)itr.next();
				ICell rowCell = tempRow.getCell(new Integer(2000017205));	
				IAgileList rowvalue = (IAgileList)rowCell.getValue();
				IAgileList[] rowselected = rowvalue.getSelection();
				if (rowselected[0].getAPIName().toUpperCase().equals("01")){
					rowCell = tempRow.getCell(new Integer(2000017207));
					rowvalue = (IAgileList)rowCell.getValue();					
					reviewList = rowvalue.getSelection();
				}
				if (rowselected[0].getAPIName().toUpperCase().equals("02")){
					rowCell = tempRow.getCell(new Integer(2000017207));
					rowvalue = (IAgileList)rowCell.getValue();	
					managerList = rowvalue.getSelection();
				}				
			}
			for (int j=0;j<managerList.length;j++){
				IUser tuser = (IUser) managerList[j].getValue();
				if (tuser.equals(user)){
					for (int k=0;k<reviewList.length;k++){
						IUser muser = (IUser)reviewList[k].getValue();
						if (!userList.contains(muser)){
							userList.add(muser);
						}						
					}
					break;
				}
			}						
		}
		
		return userList;
	}
	
}
