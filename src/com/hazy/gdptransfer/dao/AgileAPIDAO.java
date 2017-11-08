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
import com.agile.api.UserGroupConstants;
import com.agile.api.WorkflowConstants;
import com.hazy.common.HazyUtil;
import com.hazy.plmwebpx.model.AgileUser;
import com.hazy.plmwebpx.model.ChangeInfor;
import com.hazy.plmwebpx.model.ItemRecord;
import com.hazy.plmwebpx.model.ListItem;

public class AgileAPIDAO {
	private static Logger logger = Logger.getLogger(AgileAPIDAO.class);

	IAgileSession session = null;
	public AgileAPIDAO(IAgileSession session) {
		this.session=session;
	}
	public Collection<ListItem> getAgileList(String listAPIName) throws APIException {
		IAdminList adminList = session.getAdminInstance().getListLibrary().getAdminList(listAPIName);
		logger.debug(adminList.getAPIName()+":"+adminList.getName());
	    Collection<ListItem> list=new ArrayList<ListItem>();
		IAgileList agileList = adminList.getValues();
		@SuppressWarnings("unchecked")
		Collection<IAgileList> children = agileList.getChildNodes();
		logger.debug("Children:"+children);
		for (IAgileList child : children) {
			ListItem item=new ListItem();
			item.setAPIName(child.getAPIName());
			logger.debug(child.getValue()+":"+child.getAPIName());
			item.setText((String)child.getValue());
			list.add(item);
		}
	
		return list;
	}
	
	public ChangeInfor getChangeInfor(String changeNumber) throws APIException{
		ChangeInfor chgInfor=new ChangeInfor(changeNumber);
		IChange change=HazyUtil.getAgileAPIHelper().loadChange(session, changeNumber);
		IStatus istatus=change.getStatus();
		chgInfor.setStatus(istatus.getAPIName());
		ISignoffReviewer[] acknowledgers=change.getAllReviewers(istatus, WorkflowConstants.USER_ACKNOWLEDGER );
		ISignoffReviewer[] approvers=change.getAllReviewers(istatus, WorkflowConstants.USER_APPROVER );
		
		Collection<AgileUser> users=new ArrayList<AgileUser>();
		for(ISignoffReviewer approver:approvers) {
			String userid=approver.getReviewer().getName();
			AgileUser user=new AgileUser(userid);
			users.add(user);
		}
		for(ISignoffReviewer acknowledger:acknowledgers) {
			String userid=acknowledger.getReviewer().getName();
			AgileUser user=new AgileUser(userid);
			users.add(user);
		}
		chgInfor.setReviewers(users);
		return chgInfor;
	}
	
	public ChangeInfor getChangeInfor(String changeNumber,String statusAPIName) throws APIException{
		ChangeInfor chgInfor=new ChangeInfor(changeNumber);
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
		
		Collection<AgileUser> users=new ArrayList<AgileUser>();
		for(ISignoffReviewer approver:approvers) {
			String userid=approver.getReviewer().getName();
			AgileUser user=new AgileUser(userid);
			users.add(user);
		}
		for(ISignoffReviewer acknowledger:acknowledgers) {
			String userid=acknowledger.getReviewer().getName();
			AgileUser user=new AgileUser(userid);
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

	public Collection<AgileUser> getFunctionUsers(String changeNumber,String userid) throws APIException {
		IUser manager=HazyUtil.getAgileAPIHelper().loadUser(session, userid);
		IChange change=HazyUtil.getAgileAPIHelper().loadChange(session, changeNumber);
		Collection<AgileUser> users=new ArrayList<AgileUser>();
		ArrayList<IUser> iusers=this.getReviewUserList(change, manager);
		for(IUser user:iusers) {
			
			AgileUser aUser=new AgileUser(user.getName());
			users.add(aUser);
		}
		return users;
	}
	private ArrayList<IUser> getReviewUserList(IChange change, IUser user) throws APIException {
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
				if (rowselected[0].getAPIName().toUpperCase().equals("USER")){
					rowCell = tempRow.getCell(new Integer(2000017207));
					rowvalue = (IAgileList)rowCell.getValue();					
					reviewList = rowvalue.getSelection();
				}
				if (rowselected[0].getAPIName().toUpperCase().equals("MANGER")){
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
