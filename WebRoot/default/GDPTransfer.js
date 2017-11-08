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
					var failNums=validMutilist(documentNumber,doclist);
				      if(failNums.length>0){
				    	  alert("第" + (row + 1) + "行,DocumentNumber"+failNums.toString()+" 在系统中找不到!");
							reload = false;
							return;
					      
				      }
				
				}
				
				ECNNumber = $('#ECNNumber' + num).val();
				if(ECNNumber!=""){
					
					var failNums=validMutilist(ECNNumber,ecnlist);
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

var doclist;
var ecnlist;
var reviewlist;
function init() {
	$.ajax({
		url : 'ListServlet?action=doc',
		dataType : 'json',
		async : false,
		success : function(result) {
			doclist = result;
		}
	});
	$.ajax({
		url : 'ListServlet?action=ecn',
		dataType : 'json',
		async : false,
		success : function(result) {
			ecnlist = result;
		}
	});
	$.ajax({
		url : 'ListServlet?action=specreview',
		dataType : 'json',
		async : false,
		success : function(result) {
			reviewlist = result;
		}
	});
}
function loadData() {

	init();
	var datalist;
	$.ajax({
		url : 'GDPTransferServlet?action=loadReview',
		dataType : 'json',
		async : false,
		success : function(result) {
			if (result.success) {
				datalist = result.msg.itemRecords;
				// changeNumber = result.msg.changeNumber;
				loadTitle(datalist);
				loadList(datalist);
				$("#msg").html("");
			} else {
				$("#msg").html("错误信息:" + result.msg + " <br>请关闭窗口！");
			}
		}
	});
	$("#msg").html("");
}

function loadTitle(recordList) {
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

function loadList(datalist) {
	for (var j = 0; j < datalist.length; j++) {
		var count = Math.ceil(document.getElementById('recordcount').value);
		// 'class'='GMDataRow' 'className'='GMDataRow'
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
		var newTd1 = getHideInputTD(count, itemReview.rowid, 150);// (newTD,tdID,count,value,width)
		$("#tr" + count).append(newTd1);
		var newTd2 = getDisabledTextAreaTD("itemNumber", count,
				datalist[j].itemNumber, 150);// (newTD,tdID,count,value,width)
		$("#tr" + count).append(newTd2);
		var newTd3 = getDisabledTextAreaTD("description", count,
				datalist[j].description, 300);// (newTD,tdID,count,value,width)
		$("#tr" + count).append(newTd3);
		var newTd4 = getDisabledTextAreaTD("rev", count, datalist[j].rev, 80);// (newTD,tdID,count,value,width)
		$("#tr" + count).append(newTd4);

		var newTd5 = getSelectTD("specReview", count,150);
		$("#tr" + count).append(newTd5);
		console.log(itemReview.specReview);
		initSeclectTD("specReview", count, itemReview.specReview, reviewlist);
		var newTd6 = getEnabledTextAreaTD("reason", count, itemReview.reason,
				200);// (newTD,tdID,count,value,width)
		$("#tr" + count).append(newTd6);
		var newTd7 = getEnabledTextAreaTD("documentNumber", count,
				itemReview.docNumber, 150);// (newTD,tdID,count,value,width)
		$("#tr" + count).append(newTd7);
		// registerChange("documentNumber", count,
		// itemReview.docId,itemReview.docNumber,doclist);
		var newTd8 = getEnabledTextAreaTD("ECNNumber", count,
				itemReview.ecnNumber, 150);// (newTD,tdID,count,value,width)
		$("#tr" + count).append(newTd8);
		// registerChange("ECNNumber", count,
		// itemReview.ecnId,itemReview.ecnNumber,ecnlist);

		var newTd9 = getDisabledTextAreaTD("managerReviewRecord", count,
				datalist[j].managerReviewRecord, 300);// (newTD,tdID,count,value,width)
		$("#tr" + count).append(newTd9);

		var newTd10 = createHideTD("userid", count, itemReview.userid, 0);// (newTD,tdID,count,value,width)
		$("#tr" + count).append(newTd10);

		document.getElementById('recordcount').value = count + 1;
	}
}

function getHideInputTD(count, value, width) {
	var newTD = "<td 'class'='GMColorNoFocus GMRow GMText GMCell'"
			+ " 'className'='GMColorNoFocus GMRow GMText GMCell'>"
			+ "<input id='flag" + count
			+ "' value='N' type='hidden'/> <input type='hidden' id='ID" + count
			+ "' value=" + value + "></td>";
	return newTD;
}

function createHideTD(tdID, count, value, width) {
	var newTD = "<td>" + "<input id='" + tdID + count + "' value='" + value
			+ "' type='hidden'/> </td>";
	return newTD;
}

function getDisabledTextAreaTD(tdID, count, value, width) {
	var newTD = "<td 'class'='GMColorNoFocus GMRow GMText GMCell'"
			+ " 'className'='GMColorNoFocus GMRow GMText GMCell'>"
			+ "<textarea id='" + tdID + count + "'  rows='3' style='width:"
			+ width + "px;' onchange =alertflag('flag" + count
			+ "')  disabled='disabled'>" + value + "</textarea> " + "</td>";
	return newTD;
}

function getEnabledTextAreaTD(tdID, count, value, width) {
	var newTD = "<td 'class'='GMColorNoFocus GMRow GMText GMCell'"
			+ " 'className'='GMColorNoFocus GMRow GMText GMCell'>"
			+ "<textarea id='" + tdID + count + "'  rows='3' style='width:"
			+ width + "px;' onchange =alertflag('flag" + count + "') >" + value
			+ "</textarea> " + "</td>";
	return newTD;
}

function getSelectTD(tdID, count, width) {
	var newTD = "<TD><select id='" + tdID + count + "' onchange =alertflag('flag" 
	+ count + "') style='width:" + width + "px;'></select></TD>";
	return newTD;
}

function initSeclectTD(tdID, count, value, datalist){
	var sList = document.getElementById(tdID+count);
	initList(datalist, sList);
	setValue(datalist,value,sList);
	//$("#"+tdID+count).change(function(){ 
		//alertflag('flag'+count);
		//}); 
}

function getEnabledSelectJQueryTD(tdID, count, value, width) {
	var newTD = "<td 'class'='GMColorNoFocus GMRow GMText GMCell'"
			+ " 'className'='GMColorNoFocus GMRow GMText GMCell'> <input id='"
			+ tdID + count + "'  class='easyui-combobox' "// value='" + value
															// + "'"
			+ " data-options=\"width:" + width
			+ ",valueField:'index',textField:'value',value:'" + value
			+ "' \"></td>";
	return newTD;
}

function registerChange(inputName, count, value, text, datalist) {

	$("#" + inputName + count).combobox({
		data : datalist
	});

	// $("#" + inputName + count).combobox('select', value);
	// $("#" + inputName + count).combobox('setValue', value);
	// $("#" + inputName + count).combobox('setText', text);
	$("#" + inputName + count).combobox({
		onChange : function(n, o) {
			alertflag("flag" + count);
		}
	});
	// $("#" + inputName + count).combobox({height:26});

}

function alertflag(flagname) {
	document.getElementById(flagname).value = 'Y';
}

// 替换特殊字符
function replaceReg(value) {
	return value.replace(/\&/g, '%26').replace(/\+/g, '%2b').replace(/\;/g,
			'%3b');
}