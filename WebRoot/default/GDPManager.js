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
/**
 * 
 * @author Hua.Huang
 */
var action = "approve";
var changeNumber;
var userid;
var changerowid;
function reject() {
	action = "reject";
	approve();
}

function approve() {
	url = "<%=request.getContextPath()%>/default/GDPTransfer.jsp";
	var reload = true;
	var table = document.getElementById("recordlist");
	var rowscount = table.rows.length;
	var count = 0;
	var changeJSON = {
		"rowid": changerowid,
		"changeNumber" : changeNumber,
		"managerID" : userid,
		"managerApprove" : action,
		"itemRecords" : []
		//"managerReviewRecord" : $("#comment").val()
	};
	for (i = 1; i < rowscount; i++) {
		var tempid = table.rows[i].id;
		var num = tempid.substr(2);
		var ID = 0;
		var flag = "N";

		var specReview = "";
		var reason = "";
		var documentNumber = "";
		var row = i - 1;

		flag = $('#flag' + num).val();
		var itemNumber = $('#itemNumber' + num).val();
		if (flag == "Y") {
			ID = $('#ID' + num).val();

			if ($('#managerReviewRecord' + num).val() != '')
				managerReviewRecord = $('#managerReviewRecord' + num).val();
			else {
				alert("第" + (row + 1) + "行,managerReviewRecord不能为空!");
				reload = false;
				return;
			}

			var itemjson = {
				"rowid":ID,
				"itemNumber" : itemNumber,
				"description" : "",
				"rev" : "",
				"managerReviewRecord" : managerReviewRecord,
				"itemReviewRecords" : []
			};
			changeJSON.itemRecords.push(itemjson);
			reload = true;
		}
	}

	$.ajax({
		type : "POST",
		url : "ManagerServlet",
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(changeJSON),
		success : function(xhr, exception) {
			// alert("success错误提示： " + xhr.status + " "
			// + xhr.statusText+" "+exception);
			alert("已提交！请关闭窗口");
		},
		error : function(xhr, textStatus, errorThrown) {
			$("#msg").html("提交数据失败！");
		}
	});
	if (reload) {
		//alert("更新成功!");
		// window.location.replace(thisurl);
	}
}

function loadData() {
	
	var itemRecordList;
	$.getJSON("GDPTransferServlet?action=loadManager", function(result) {
		
		if (result.success) {
			itemRecordList = result.msg.itemRecords;
			changeNumber = result.msg.changeNumber;
			changerowid = result.msg.rowid;
			userid=result.msg.managerID;
			loadTitle(itemRecordList);
			loadList(itemRecordList);
			$("#msg").html("");
		} else {
			
			$("#msg").html("错误信息:" + result.msg + " <br>请关闭窗口！");
		}
	});

}


function loadTitle(recordList) {
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
			+ createTD("部门经理评审意见 ") + "</tr>";
	$("#recordlistTitle").append(tbWidth);
	$("#recordlistTitle").append(tbTitle);
	$("#recordlist").append(tbWidth);

}

function loadList(recordList) {
	for (var j = 0; j < recordList.length; j++) {

		var table = document.getElementById("recordlist");
		var rowscount = table.rows.length;
		var newTr = table.insertRow(rowscount);

		var count = Math.ceil(document.getElementById('recordcount').value);
		var newTrID = "tr" + count;
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
		initDisabledTextAreaTD(newTd2, "itemNumber", count,
				recordList[j].itemNumber, 150);

		var newTd3 = newTr.insertCell(3);
		initDisabledTextAreaTD(newTd3, "description", count,
				recordList[j].description, 300);

		var newTd4 = newTr.insertCell(4);
		initDisabledTextAreaTD(newTd4, "rev", count, recordList[j].rev, 80);

		var itemReviewRecords = recordList[j].itemReviewRecords;
		var itemReview;
		var row = 5;
		for (var k = 0; k < itemReviewRecords.length; k++) {
			itemReview = itemReviewRecords[k];
			var newTd5 = newTr.insertCell(row);
			initDisabledTextAreaTD(newTd5, "specReview", count,
					itemReview.specReviewValue, 100);
			row = row + 1;
			var newTd6 = newTr.insertCell(row);
			initDisabledTextAreaTD(newTd6, "reason", count, itemReview.reason,
					200);
			row = row + 1;
			var newTd7 = newTr.insertCell(row);
			initEnabledLinkTD(agileurl,newTd7, "documentNumber",
					itemReview.docNumber,"9000");
			row = row + 1;
			var newTd9 = newTr.insertCell(row);
			//agileURL,newTD,tdID,value,subclassid
			initEnabledLinkTD(agileurl,newTd9, "ECNNumber", itemReview.ecnNumber,"6000");
			row = row + 1;
		}
		var newTd8 = newTr.insertCell(row);
		initEnabledTextAreaTD(newTd8, "managerReviewRecord", count,
				recordList[j].managerReviewRecord, 300);
		document.getElementById('recordcount').value = count + 1;
	}
}