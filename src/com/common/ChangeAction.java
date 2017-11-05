package com.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

import com.agile.api.APIException;
import com.agile.api.ChangeConstants;
import com.agile.api.IAgileList;
import com.agile.api.IAgileSession;
import com.agile.api.ICell;
import com.agile.api.IChange;
import com.agile.api.IDataObject;
import com.agile.api.IItem;
import com.agile.api.IRow;
import com.agile.api.ISignoffReviewer;
import com.agile.api.IStatus;
import com.agile.api.ITable;
import com.agile.api.IWorkflow;
import com.agile.api.WorkflowConstants;

public class ChangeAction {
	static IChange change;
	static IAgileSession session;
	String wfApiName = "";
	String errorInfo = "";
	String statesApiName = "";
	//读取配置文件
	//public String directoryPath = new File("").getCanonicalPath();
		
	public ChangeAction(IChange changeNum) throws APIException, IOException {
		change = changeNum;
		session = change.getSession();		
		
		IWorkflow workFlow = change.getWorkflow();
		wfApiName = workFlow.getAPIName();
	}
	
	public boolean needJumpStandard() throws APIException{
		boolean flag = false;
		ICell cell = change.getCell(ChangeConstants.ATT_PAGE_THREE_LIST04);
		if (cell != null){
			IAgileList list = (IAgileList) cell.getValue();
			IAgileList[] select = list.getSelection();		
			if (select.length == 1){
				String apiName = select[0].getAPIName().toUpperCase();
				if (apiName.equals("STANDARD") || apiName.equals("DCC") ){	
					//System.out.println("Change");
					return this.changeStatus(apiName);									
				}
			}
		}
			
		
		return flag;
	}

	public void setItemInfo(Logger logger) throws IOException, APIException{
		logger.info("***设置受影响物件版本及生命周期状态***");
		String filename = CommFun.getConfigFile("Configure.properties");
		
		//String filename = System.getProperty("user.dir")+"/Configure.properties";
		InputStream in = new BufferedInputStream(new FileInputStream(filename));			
		ResourceBundle resourceBundle = new PropertyResourceBundle(in);
		
		//成品分类列表
		String asmValue = resourceBundle.getString("Class_Product");
		Set<String> asmList = new HashSet<String>(Arrays.asList(asmValue.split(",")));
		//半成品分类列表
		String halfAsmValue = resourceBundle.getString("Class_SFProduct");
		Set<String> halfAsmList = new HashSet<String>(Arrays.asList(halfAsmValue.split(",")));
		//各工作流配置项
		String superClassApiName = change.getAgileClass().getSuperClass().getAPIName();
		String configValue =  resourceBundle.getString(wfApiName);
		String[] paraValue = configValue.split(",");

		//获取C版本的生命周期特殊处理
		if (Boolean.valueOf(paraValue[0])){
			paraValue[0] = this.getDocCLifecycle();
		}else {
			paraValue[0] = "";
		}
		//成品的生命周期 特殊处理
		if (paraValue[1].toUpperCase().equals("FALSE")){
			paraValue[1] = this.getProductLifecycle();
		}
		
		//半成品特殊处理
		if (paraValue[2].toUpperCase().equals("FALSE")){
			paraValue[2] = this.getSFProductLifecycle();
		}
		
		//物料特殊处理
		if (paraValue[3].toUpperCase().equals("FALSE")){
			paraValue[3] = this.getPartLifecycle();
		}
		//文档特殊处理
		if (paraValue[4].toUpperCase().equals("FALSE")){
			paraValue[4] = this.getDocLifecycle();
		}
		
		ITable table = change.getTable(ChangeConstants.TABLE_AFFECTEDITEMS);		
		Iterator<?> i_row = table.iterator();
		logger.info("开始处理受影响物件:"+table.size());
		while(i_row.hasNext()){
			IRow irow = (IRow)i_row.next();
			IItem item = (IItem) irow.getReferent();
			logger.info("***********");
			logger.info("物件编号"+item.getName());
			
			HashMap<Object,Object> params = new HashMap<Object,Object>();
			//设置新版本号
			String newVersion = "";
			if (superClassApiName.equals("ChangeOrdersClass")){					
				String oldVersion = irow.getValue(ChangeConstants.ATT_AFFECTED_ITEMS_OLD_REV).toString();
				//System.out.println("oldVersion:"+oldVersion);
				newVersion = CommFun.getNextVer(oldVersion);
				params.put(ChangeConstants.ATT_AFFECTED_ITEMS_NEW_REV, newVersion);
				logger.info("新版本号:"+newVersion);
			}
			//设置生命周期阶段
			String itemClassApiName = item.getAgileClass().getAPIName();
			String itemSuperClassApiName = item.getAgileClass().getSuperClass().getAPIName();
			String oldLifecycle = irow.getValue(ChangeConstants.ATT_AFFECTED_ITEMS_OLD_LIFECYCLE_PHASE).toString();
			String newLifecycle = "";

			if (itemClassApiName.equals("RM_DocumentC")){
				//C版本
				newLifecycle = paraValue[0];
				if (wfApiName.equals("RMR") && !oldLifecycle.isEmpty()){
					newLifecycle = "";
				}
			}else if(asmList.contains(itemClassApiName)) {
				//成品
				newLifecycle = paraValue[1];
				if (wfApiName.startsWith("NPR") && !oldLifecycle.isEmpty()){
					newLifecycle = "";
				}				
			}else if(halfAsmList.contains(itemClassApiName)) {
				//半成品
				newLifecycle = paraValue[2];
				if (wfApiName.startsWith("NPR") && !oldLifecycle.isEmpty()){
					newLifecycle = "";
				}
			}else if (itemSuperClassApiName.equals("PartsClass")){
				//物料
				newLifecycle = paraValue[3];
				if (wfApiName.startsWith("NPR") && !oldLifecycle.isEmpty()){
					newLifecycle = "";
				}
				
			}else if (itemSuperClassApiName.equals("DocumentsClass")){
				//文档				
				newLifecycle = paraValue[4];
			}			
	
			
			
			logger.info("新生命周期阶段:"+newLifecycle);
			if (!newLifecycle.isEmpty()){
				params.put(ChangeConstants.ATT_AFFECTED_ITEMS_LIFECYCLE_PHASE, newLifecycle);
			}	
			if (!newVersion.isEmpty() || !newLifecycle.isEmpty()){
				try{
					irow.setValues(params);
				}catch (APIException e) {	
					logger.error(e.toString());
				}
			}		
		}
		
	}
	
	private String getDocLifecycle() {
		// TODO Auto-generated method stub
		String newLifecycle = "";
		return newLifecycle;
	}

	private String getPartLifecycle() {
		// TODO Auto-generated method stub
		String newLifecycle = "";
		return newLifecycle;
	}

	private String getSFProductLifecycle() {
		// TODO Auto-generated method stub
		String newLifecycle = "";
		return newLifecycle;
	}

	private String getProductLifecycle() throws APIException {
		// TODO Auto-generated method stub
		String newLifecycle = "";
		if (wfApiName.equals("PHR")){
				//获取属性值
				ICell cell = change.getCell(ChangeConstants.ATT_PAGE_THREE_LIST25);
				String cellApiName = CommFun.getSingleListInfo(cell,2);
				if (!CommFun.isNumeric(cellApiName)){
					newLifecycle = cellApiName;
				}
		}else if (wfApiName.equals("TR")){
			ICell cell = change.getCell(ChangeConstants.ATT_PAGE_THREE_LIST25);
			String cellApiName = CommFun.getSingleListInfo(cell,2);
			if (cellApiName.equals("TR5")){
				newLifecycle = "RM_XiaoPL";
			}else if (cellApiName.equals("TR6")){
				newLifecycle = "RM_Production";
			}
		}
		return newLifecycle;
	}

	//获取C版本生命周期 状态
	private String getDocCLifecycle() throws APIException {
		// TODO Auto-generated method stub
		String newLifecycle = "";
		if (wfApiName.equals("CDR")){
			newLifecycle = "Charter";
		}else if (wfApiName.equals("DCN_IC")){
			newLifecycle = "IC_Tapeout";
		}else if (wfApiName.equals("DCP") || wfApiName.equals("TR")){
			ICell cell = change.getCell(ChangeConstants.ATT_PAGE_THREE_LIST25);
			newLifecycle = CommFun.getSingleListInfo(cell, 2);
		}else if (wfApiName.equals("RMR")){					
				newLifecycle = "RM_Plan";
		}
		return newLifecycle;
	}

	
	
	public boolean changeStatus(String statusName) throws APIException{
		boolean flag = false;
		
		IStatus nextStatuses[] = change.getNextStatuses();
		IStatus nextStatus = null;
		for (int i=0;i<nextStatuses.length;i++){
			if (nextStatuses[i].getAPIName().toUpperCase().equals(statusName.toUpperCase())){
				nextStatus = nextStatuses[i];
				break;
			}
		}
		
		if (nextStatus != null){
			// 通知列表			
			IDataObject[] notifys = change.getDefaultNotifyListEx(nextStatus);
			List<IDataObject> tempNotifyList = Arrays.asList(notifys);			
			List<IDataObject> notifyList = new ArrayList<IDataObject>(tempNotifyList);
			// Get default approvers for the next status
			ISignoffReviewer[] defaultApprovers = change.getReviewers(nextStatus, WorkflowConstants.USER_APPROVER);
			List<ISignoffReviewer> approverList = Arrays.asList(defaultApprovers);
			// Get default observers for the next status
			ISignoffReviewer[] defaultObservers = change.getReviewers(nextStatus, WorkflowConstants.USER_OBSERVER);
			List<ISignoffReviewer> observerList = Arrays.asList(defaultObservers);
			// Change to the next status
			session.disableAllWarnings();				
			change.changeStatus(nextStatus, false, "Auto promote by PX !", false, false, notifyList, approverList,observerList, null, false);
			session.enableAllWarnings();
			flag = true;
		}
		
		
		return flag;
	}

	

}
