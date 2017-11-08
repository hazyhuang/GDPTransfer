var managers;	
function loadSummary() {

	$.ajax({
		url : 'GDPTransferServlet?action=loadUsers',
		dataType : 'json',
		async : false,
		success : function(result) {
			if (result.success) {
				managers = result.msg;
			} else {
				$("#msg").html("错误信息:" + result.msg + " <br>请关闭窗口！");
			}
		}
	});
	
	for(var i=0;i<managers.length;i++){
		var managerTable=
			"<table><tr><td><table id='recordlistTitle"+i+"' class=GMSection></table></td></tr>"
		+"<tr><td><table id='recordlist"+i+"' class=GMSection></table></td></tr></table>";
		
		$("#alltable").append(managerTable);
		console.log(managerTable);
		
	}
	for(var i=0;i<managers.length;i++){
		loadManager(managers[i].loginid,i);
	}
	$("#msg").html("");

}
function loadManager(manager,mgrcount){
	var itemRecordList;
	$.ajaxSettings.async = false; 
	$.getJSON("GDPTransferServlet?action=loadManagerByUserid&Manager="+manager, function(result) {
		
		if (result.success) {
			itemRecordList = result.msg.itemRecords;
			//var managerID=result.msg.managerID;
			var managerUserName=result.msg.username;
			
			loadTitle(managerUserName,itemRecordList,mgrcount);
			loadList(itemRecordList,mgrcount);
			
		} else {
			
			$("#msg").html("错误信息:" + result.msg + " <br>请关闭窗口！");
		}
	});
}

function loadTitle(managerID,recordList,mgrcount) {
	var dynmicTH;
	var dynmicTitle;
	var itemReviewRecords = recordList[0].itemReviewRecords;
	for (var k = 0; k < itemReviewRecords.length; k++) {
		var itemReview = itemReviewRecords[k];
		var username = itemReview.username;
		dynmicTH = dynmicTH + createTH(100) + createTH(200) + createTH(150)
				+ createTH(150);
		dynmicTitle = dynmicTitle
				+ createOrangeTD(username + "<br>Spec Review ")
				+ createOrangeTD(username + " <br>不涉及原因 ")
				+ createOrangeTD(username + " <br>转化后文档编号 ")
				+ createOrangeTD(username + " <br>ECN编号 ");
	}

	var tbWidth = "<tr>" + createTH(0) + createTH(30) + createTH(150)
			+ createTH(300) + createTH(80) + dynmicTH + createTH(300) + "</tr>";
	var tbTitle = "<tr class=GMHeaderRow style='OVERFLOW: auto;'>"
			+ createTD("") + createTD("") + createTD("附件编号 ")
			+ createTD("附件名称 ") + createTD("附件版本 ") + dynmicTitle
			+ createTD(managerID+"<br>部门经理评审意见 ") + "</tr>";
	$("#recordlistTitle"+mgrcount).append(tbWidth);
	$("#recordlistTitle"+mgrcount).append(tbTitle);
	$("#recordlist"+mgrcount).append(tbWidth);

}

function loadList(recordList,mgrcount) {
	var count=0;
	for (var j = 0; j < recordList.length; j++) {

		var table = document.getElementById("recordlist"+mgrcount);
		console.log("recordlist"+mgrcount+":"+table);
		var rowscount = table.rows.length;
		var newTr = table.insertRow(rowscount);
        
		var newTrID = "tr"+mgrcount + count;
		newTr.id = newTrID;
		newTr.setAttribute("class", "GMDataRow"); // class
		newTr.setAttribute("className", "GMDataRow"); // class

		var newTd0 = newTr.insertCell(0);
		newTd0.innerHTML = "";
		newTd0.setAttribute("class", "GMColorNoFocus GMRow GMText GMCell"); // class
		newTd0.setAttribute("className", "GMColorNoFocus GMRow GMText GMCell"); // class

		var newTd1 = newTr.insertCell(1);
		newTd1.setAttribute("class", "GMColorNoFocus GMRow GMText GMCell");
		newTd1.setAttribute("className", "GMColorNoFocus GMRow GMText GMCell");
		newTd1.innerHTML = "<input id='flag"
				+ count
				+ "' value='N' type='hidden'/> <input type='hidden'  id='checkbox"
				+ count + "'  disabled='disabled'/><input type='hidden' id='ID"
				+ count + "' value='" + recordList[j].rowid + "'>";

		var newTd2 = newTr.insertCell(2);
		getDisabledTextAreaTD(newTd2, "itemNumber", count,
				recordList[j].itemNumber, 150);

		var newTd3 = newTr.insertCell(3);
		getDisabledTextAreaTD(newTd3, "description", count,
				recordList[j].description, 300);

		var newTd4 = newTr.insertCell(4);
		getDisabledTextAreaTD(newTd4, "rev", count, recordList[j].rev, 80);

		var itemReviewRecords = recordList[j].itemReviewRecords;
		var itemReview;
		var row = 5;
		for (var k = 0; k < itemReviewRecords.length; k++) {
			itemReview = itemReviewRecords[k];
			var newTd5 = newTr.insertCell(row);
			getDisabledTextAreaTD(newTd5, "specReview", count,
					itemReview.specReviewValue, 100);
			row = row + 1;
			var newTd6 = newTr.insertCell(row);
			getDisabledTextAreaTD(newTd6, "reason", count, itemReview.reason,
					200);
			row = row + 1;
			var newTd7 = newTr.insertCell(row);
			getEnabledLinkTD(newTd7, "documentNumber", count,
					itemReview.docNumber,"9000",itemReview.docId, 150);
			row = row + 1;
			var newTd9 = newTr.insertCell(row);
			getEnabledLinkTD(newTd9, "ECNNumber", count, itemReview.ecnNumber,"6000",itemReview.ecnId,
					150);
			row = row + 1;
		}
		var newTd8 = newTr.insertCell(row);
		getEnabledTextAreaTD(newTd8, "managerReviewRecord", count,
				recordList[j].managerReviewRecord, 300);
		document.getElementById('recordcount').value = count + 1;
	}
}