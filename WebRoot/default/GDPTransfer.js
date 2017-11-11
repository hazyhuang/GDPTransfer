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
function savedata() {
	url = "<%=request.getContextPath()%>/default/GDPTransfer.jsp";
	var reload = true;
	var table = document.getElementById("recordlist");
	var rowscount = table.rows.length;
	var count = 0;
	var changeJSON = getInitChangeJson();
	for (i = 1; i < rowscount; i++) {
		var tempid = table.rows[i].id;
		var num = tempid.substr(2);
		var ID = 0;
		var flag = "N";
		var specReview = "";
		var specReviewValue = "";
		var reason = "";
		var documentNumber = "";
		var docid = "";
		var ECNNumber = "";
		var ecnid = "";
		var row = i - 1;
		flag = $('#flag' + num).val();
		specReview = $('#specReview' + num).val();
		specReviewValue = $('#specReview' + num).find("option:selected").text();
		if (specReview == '') {
			alert("第" + (row + 1) + "行,specReview不能为空!");
			reload = false;
			return;
		}
		if (flag == "Y") {
			ID = $('#ID' + num).val();
			
			if ($('#reason' + num).val() != '')
				reason = $('#reason' + num).val();
			if (specReview == "0") {
				if (reason == '') {
					alert("第" + (row + 1) + "行,不涉及则  原因不能为空!");
					reload = false;
					return;
				}
			} else if (specReview != "0") {
				documentNumber = $('#documentNumber' + num).val();
				if(specReview =="2" ||specReview =="3"){
					if (documentNumber == "") {
						alert("第" + (row + 1) + "行,DocumentNumber 不能为空!");
						reload = false;
						return;
					}
				}
				if(documentNumber!=""){
					var failNums=validMutilist(documentNumber,initList.doclist);
				      if(failNums.length>0){
				    	  alert("第" + (row + 1) + "行,DocumentNumber"+failNums.toString()+" 在系统中找不到!");
							reload = false;
							return;
					      
				      }
				
				}
				
				ECNNumber = $('#ECNNumber' + num).val();
				if(ECNNumber!=""){
					
					var failNums=validMutilist(ECNNumber,initList.ecnlist);
				      if(failNums.length>0){
				    	  alert("第" + (row + 1) + "行,ECNNumber"+failNums.toString()+" 在系统中找不到!");
							reload = false;
							return;
					      
				      }
				}
			}
			var itemjson = {
				"itemNumber" : $('#itemNumber' + num).val(),
				"description" : "",
				"rev" : "",
				"managerReviewRecord" : "",
				"itemReviewRecords" : [ {
					"rowid" : ID,
					"userid" : userid,
					"username" : username,
					"specReview" : specReview,
					"specReviewValue" : specReviewValue,
					"reason" : reason,
					"docNumber" : documentNumber,
					"ecnNumber" : ECNNumber
			
				} ]
			};
			changeJSON.itemRecords.push(itemjson);
			reload = true;
		}
	}
	$.ajax({
		type : "POST",
		url : "TransferServlet",
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(changeJSON),
		success : function(xhr, exception) {
			/*
			 * alert("success错误提示： " + xhr.status + " " + xhr.statusText + " " +
			 * exception);
			 */
			alert("已提交，请关闭窗口");

		},
		error : function(xhr, textStatus, errorThrown) {
			/*
			 * alert("错误提示： " + xhr.status + " " + xhr.statusText + " " +
			 * textStatus + " " + errorThrown);
			 */
			$("#msg").html("提交数据失败！");
		}
	});
	if (reload) {
		// alert("更新成功!");
		// window.location.replace(thisurl);
	}
}

function getInitChangeJson() {
	var initChangeJson = {
		"changeNumber" : chgnum,
		"managerID" : "",
		"managerApprove" : "",
		"itemRecords" : []
	};
	return initChangeJson;
}

var initList;
/**
 * 加载各列表项
 * @returns
 */
function loadAllList() {
	var initList=new Object();
	$.ajax({
		url : 'ListServlet?action=doc',
		dataType : 'json',
		async : false,
		success : function(result) {
			initList.doclist = result;
		}
	});
	$.ajax({
		url : 'ListServlet?action=ecn',
		dataType : 'json',
		async : false,
		success : function(result) {
			initList.ecnlist = result;
		}
	});
	$.ajax({
		url : 'ListServlet?action=specreview',
		dataType : 'json',
		async : false,
		success : function(result) {
			initList.reviewlist = result;
		}
	});
	return initList;
}
function loadData() {
    initList=
	loadAllList();
	var datalist;
	$.ajax({
		url : 'GDPTransferServlet?action=loadReview',
		dataType : 'json',
		async : false,
		success : function(result) {
			if (result.success) {
				datalist = result.msg.itemRecords;
				
				createTitleHTML(datalist);
				createTableHTML(datalist,initList);
				$("#msg").html("");
			} else {
				$("#msg").html("错误信息:" + result.msg + " <br>请关闭窗口！");
			}
		}
	});
	$("#msg").html("");
}

function createTitleHTML(recordList) {
	var dynmicTH;
	var dynmicTitle;
	var itemReviewRecords = recordList[0].itemReviewRecords;
	var itemReview;
	for (var k = 0; k < itemReviewRecords.length; k++) {
		itemReview = itemReviewRecords[k];
	}

	dynmicTH = dynmicTH + createTH(150) + createTH(200) + createTH(150)
			+ createTH(150);
	dynmicTitle = dynmicTitle + createOrangeTD("<br>Spec Review ")
			+ createOrangeTD(" <br>不涉及原因 ") + createOrangeTD(" <br>转化后文档编号 ")
			+ createOrangeTD(" <br>ECN编号 ");

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

function createTableHTML(datalist,initList) {
	for (var j = 0; j < datalist.length; j++) {
		var count = Math.ceil(document.getElementById('recordcount').value);
		var itemReviewRecords = datalist[j].itemReviewRecords;
		var itemReview;
		for (var k = 0; k < itemReviewRecords.length; k++) {
			itemReview = itemReviewRecords[k];
		}
		var newTr = "<tr id='tr" + count + "'></tr>"
		$("#recordlist").append(newTr);
		var newTd0 = "<td 'class'='GMColorNoFocus GMRow GMText GMCell'"
				+ " 'className'='GMColorNoFocus GMRow GMText GMCell'></td>"
		$("#tr" + count).append(newTd0);
		var newTd1 = createHideInputTD(count, itemReview.rowid, 150);
		$("#tr" + count).append(newTd1);
		var newTd2 = createDisabledTextAreaTD("itemNumber", count,
				datalist[j].itemNumber, 150);
		$("#tr" + count).append(newTd2);
		var newTd3 = createDisabledTextAreaTD("description", count,
				datalist[j].description, 300);
		$("#tr" + count).append(newTd3);
		var newTd4 = createDisabledTextAreaTD("rev", count, datalist[j].rev, 80);
		$("#tr" + count).append(newTd4);

		var newTd5 = createSelectTD("specReview", count,150);
		$("#tr" + count).append(newTd5);
		console.log(itemReview.specReview);
		initSeclectTD("specReview", count, itemReview.specReview, initList.reviewlist);
		var newTd6 = createEnabledTextAreaTD("reason", count, itemReview.reason,
				200);
		$("#tr" + count).append(newTd6);
		var newTd7 = createEnabledTextAreaTD("documentNumber", count,
				itemReview.docNumber, 150);
		$("#tr" + count).append(newTd7);

		var newTd8 = createEnabledTextAreaTD("ECNNumber", count,
				itemReview.ecnNumber, 150);
		$("#tr" + count).append(newTd8);
	
		var newTd9 = createDisabledTextAreaTD("managerReviewRecord", count,
				datalist[j].managerReviewRecord, 300);
		$("#tr" + count).append(newTd9);

		var newTd10 = createHideTD("userid", count, itemReview.userid, 0);
		$("#tr" + count).append(newTd10);

		document.getElementById('recordcount').value = count + 1;
	}
}



function initSeclectTD(tdID, count, value, datalist){
	var sList = document.getElementById(tdID+count);
	initSingleList(datalist, sList);
	setSingleListValue(datalist,value,sList);
}


