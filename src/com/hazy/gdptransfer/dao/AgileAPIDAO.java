package com.hazy.gdptransfer.dao;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.agile.api.APIException;
import com.agile.api.IAdminList;
import com.agile.api.IAgileList;
import com.agile.api.IAgileSession;
import com.hazy.plmwebpx.model.ListItem;

public class AgileAPIDAO {
	private static Logger logger = Logger.getLogger(AgileAPIDAO.class);

	IAgileSession session = null;
	Collection<ListItem> getAgileList(String listAPIName) throws APIException {
		IAdminList adminList = session.getAdminInstance().getListLibrary().getAdminList(listAPIName);
		logger.debug(adminList.getAPIName()+":"+adminList.getName());
	    Collection<ListItem> list=new ArrayList<ListItem>();
		IAgileList agileList = adminList.getValues();
		@SuppressWarnings("unchecked")
		Collection<IAgileList> children = agileList.getChildNodes();
		for (IAgileList child : children) {
			ListItem item=new ListItem();
			item.setId(child.getAPIName());
			item.setText((String)child.getValue());
			list.add(item);
		}
	
		return list;
	}
	
}
