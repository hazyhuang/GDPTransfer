<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>GDP转化责任人确认</title>
<style type="text/css">
input {
	border: 1px solid #000;
}
#Loading {
	position: absolute;
	z-index: 10;
	left: 10px;
	top: 10px;
	border: 1px #666666 solid;
	background: #F7F7F7;
	width: 10px;
	height: 10px
}
.LoadContent {
	width: 100%;
	height: 100%;
	overflow: auto
}
</style>
<%@include file="StylesInclude.jsp"%>
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.4/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.4/themes/color.css">
<link rel="stylesheet" type="text/css" href="../css/style.css">

<script type="text/javascript" src="../jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.4.4/datagrid-groupview.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.4.4/datagrid-cellediting.js"></script>


<script type="text/javascript">
    var doclist;
    var ecnlist;
    //http://172.16.90.32:7001/Agile/PLMServlet?action=OpenEmailObject&classid=10000
    var thisurl=  "<%=request.getContextPath()%>/default/GDPTransfer.jsp";      
	
	function getInitChangeJson()
        {
	    var initChangeJson = {
	        "changeNumber": "DIR009",
	        "managerID": "",
	        "managerApprove": "",
	        "itemRecords": []
	        };
	    return initChangeJson;
	    }
	
	function savedata()
		{
		url = "<%=request.getContextPath()%>/default/GDPTransfer.jsp";
		var reload = true;
		var table = document.getElementById("recordlist");
		var rowscount = table.rows.length;
		var count = 0;
		var changeJSON = 
		    {
		    "changeNumber": "DIR009",
		    "managerID": "",
		    "managerApprove": "",
		    "itemRecords": []
		    };
		for (i = 1; i < rowscount; i++) {
			var tempid = table.rows[i].id;
			var num = tempid.substr(2);
			var ID = 0;
			var flag = "N";
			var specReview = "";
            var reason ="";
            var documentNumber = "";
			var row = i - 1;

			if (document.getElementById('flag' + num).value != '')
				flag = document.getElementById('flag' + num).value;

			if (flag == "Y") {
				if (document.getElementById('ID' + num) != null) {
					if (document.getElementById('ID' + num).value != '')
						ID = document.getElementById('ID' + num).value;
				}

				if (document.getElementById('specReview' + num).value != '')
					specReview = document.getElementById('specReview' + num).value;
				else {
					alert("行" + row + ",specReview不能为空!");
					reload = false;
					return;
				}
				
				if (document.getElementById('reason' + num).value != '')
					reason = document.getElementById('reason' + num).value;
				else {
					alert("行" + row + ",reason 不能为空!");
					reload = false;
					return;
				}
				
				if (document.getElementById('documentNumber' + num).value != '')
					documentNumber = document.getElementById('documentNumber' + num).value;
				else {
					alert("行" + row + ",DocumentNumber 不能为空!");
					reload = false;
					return;
				}
				 var itemjson = 
				     {
				     "itemNumber": "D001",
				     "description": "",
				     "rev": "",
				     "managerReviewRecord":"",
				     "itemReviewRecords": [{"userid":"terry",
				            	            "specReview":specReview,
				            	            "reason":reason,
				            	            "documentNumber":documentNumber}]
				     };
				changeJSON.itemRecords.push(itemjson);
				reload = true;
			}
		}

	        $.ajax({
	            type: "POST",
	            url: "JSONServlet",
	            contentType: "application/json; charset=utf-8",
	            data: JSON.stringify(changeJSON),
	            complete: function (xhr,exception) {
	            	alert("success错误提示： " + xhr.status + " " + xhr.statusText+" "+exception);
	            	alert("请求已提交！我们会尽快与您取得联系");
	                
	            },
	            error: function (xhr,textStatus, errorThrown ) {
	            	alert("错误提示： " + xhr.status + " " + xhr.statusText+" "+textStatus+" "+errorThrown);
	                $("#msg").html("提交数据失败！");
	            }
	        });
		if (reload) {
			alert("更新成功!");
			//window.location.replace(thisurl);
		}
	}

	
	function loadSWR() {
		var swrlist;
		$.getJSON("initGDPReview.json", function(result) {
			console.log(result);
			
			
			console.log(result);
			console.log(result.success);
			if(result.success=='true'){
				swrlist = result.msg.itemRecords;
				console.log(swrlist)
				loadSWR2(swrlist);
			}else{
				console.log("错误信息");
				$("#msg").html("错误信息:"+result.msg+" <br>请关闭窗口！");
			}
		});

	}

	function loadSWR2(swrlist) {
		for (var j = 0; j < swrlist.length; j++) {

			var table = document.getElementById("recordlist");
			var rowscount = table.rows.length;
			var newTr = table.insertRow(rowscount);

			var count = Math.ceil(document.getElementById('recordcount').value);
			var newTrID = "tr" + count;
			newTr.id = newTrID;
			newTr.setAttribute("class", "GMDataRow"); //class
			newTr.setAttribute("className", "GMDataRow"); //class      

			var newTd0 = newTr.insertCell(0);
			newTd0.innerHTML = "";
			newTd0.setAttribute("class", "GMColorNoFocus GMRow GMText GMCell"); //class
			newTd0.setAttribute("className",
					"GMColorNoFocus GMRow GMText GMCell"); //class
					
			var newTd1 = newTr.insertCell(1);
			newTd1.setAttribute("class", "GMColorNoFocus GMRow GMText GMCell");
			newTd1.setAttribute("className",
					"GMColorNoFocus GMRow GMText GMCell");
			
				newTd1.innerHTML = "<input id='flag"+count
			                   +"' value='N' type='hidden'/> <input type='checkbox' id='checkbox"
			                   +count+"'  /><input type='hidden' id='ID"
			                   +count+"' value='"+swrlist[j].rowid+"'>";
			

			var newTd2 = newTr.insertCell(2);
			getDisabledTextAreaTD(newTd2,"itemNumber",count,swrlist[j].itemNumber,150);//(newTD,tdID,count,value,width)
           
			var newTd3 = newTr.insertCell(3);
            getDisabledTextAreaTD(newTd3,"description",count,swrlist[j].description,300);//(newTD,tdID,count,value,width)
            
			var newTd4 = newTr.insertCell(4);
			getDisabledTextAreaTD(newTd4,"rev",count,swrlist[j].rev,80);//(newTD,tdID,count,value,width)
            		
			var itemReviewRecords=swrlist[j].itemReviewRecords;
			var itemReview;
			for (var k = 0; k < itemReviewRecords.length; k++) {
				itemReview=itemReviewRecords[k]
			}
			
			var newTd5 = newTr.insertCell(5);
			getEnabledTextAreaTD(newTd5,"specReview",count,itemReview.specReview,100);//(newTD,tdID,count,value,width)
        	
			
			var newTd6 = newTr.insertCell(6);
			getEnabledTextAreaTD(newTd6,"reason",count,itemReview.reason,200);//(newTD,tdID,count,value,width)
     					
			var newTd7 = newTr.insertCell(7);
			getEnabledSelectTD(newTd7,"documentNumber",count,itemReview.documentNumber,150);//(newTD,tdID,count,value,width)
 
			var newTd8 = newTr.insertCell(8);
			getEnabledTextAreaTD(newTd8,"ECNNumber",count,itemReview.ECNNumber,150);//(newTD,tdID,count,value,width)
 
			var newTd9 = newTr.insertCell(9);
			getDisabledTextAreaTD(newTd9,"managerReviewRecord",count,swrlist[j].managerReviewRecord,300);//(newTD,tdID,count,value,width)
        	
			document.getElementById('recordcount').value = count + 1;
		}
	}
	
	function getDisabledTextAreaTD(newTD,tdID,count,value,width){
	    newTD.id = tdID;
	    
	    newTD.setAttribute("class", "GMColorNoFocus GMRow GMText GMCell");
	    newTD.setAttribute("className","GMColorNoFocus GMRow GMText GMCell");
        newTD.innerHTML = " <textarea id='"+tdID
				+ count+ "'  rows='3' style='width:"+width+"px;' onchange =alertflag('flag"
				+ count + "')  disabled='disabled'>"+ value + "</textarea> ";
	}
	function getEnabledTextAreaTD(newTD,tdID,count,value,width){
	    newTD.id = tdID;
	    newTD.setAttribute("class", "GMColorNoFocus GMRow GMText GMCell");
	    newTD.setAttribute("className","GMColorNoFocus GMRow GMText GMCell");
        newTD.innerHTML = " <textarea id='"+tdID
				+ count+ "'  rows='3' style='width:"+width+"px;' onchange =alertflag('flag"
				+ count + "') >"+ value + "</textarea> ";
	}
	
	function getEnabledSelectTD(newTD,tdID,count,value,width){
	    newTD.id = tdID;
	    //newTD.setAttribute("class", "GMColorNoFocus GMRow GMText GMCell");
	    //newTD.setAttribute("className","GMColorNoFocus GMRow GMText GMCell");
      
		newTD.innerHTML = " <input id='"+tdID
		    + count+ "' class='easyui-combobox' value='"+value+"'"
		    +" data-options=\"width:"+width
		    +",valueField:'index',textField:'value',url:'doclist.json'\">";
	    console.log(newTD.innerHTML);
	 
	}
	
	function alertflag(flagname) {
		document.getElementById(flagname).value = 'Y';
	}

	//替换特殊字符
	function replaceReg(value) {
		return value.replace(/\&/g, '%26').replace(/\+/g, '%2b').replace(/\;/g,
				'%3b');
	}
</script>
</head>
<body>
	<input name="recordcount" value="0" type="hidden" class="formElm"
		size="8" id="recordcount" />
	<table>
		
		<tr>
			<td height="35px" colspan=2></td>
		</tr>
		<tr>
			<td colspan=2>
			<div id="msg" style="color:#F00"></div>
			<div style="overflow-x:scroll;width:1300px;white-space:nowrap;">
				<table>
					<tr>
						<td>
							<table id="recordlistTitle" class=GMSection>
								<tr>
									<TH style="WIDTH: 0px"></TH>
									<TH style="WIDTH: 30px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 150px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 300px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 80px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 100px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 200px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 150px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 150px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 300px; HEIGHT: 0px"></TH>
								</tr>
								<tr class=GMHeaderRow style="OVERFLOW: auto;">
									<TD style="WIDTH: 0px;"></TD>
									<TD class=" GMRow GMHeaderText GMCellHeader"
										style="BACKGROUND-COLOR: rgb(239, 239, 239)" colSpan=1><SPAN><b>
										</b></SPAN></TD>
									<TD class=' GMRow GMHeaderText GMCellHeader'
										style='BACKGROUND-COLOR: rgb(239, 239, 239)' colSpan=1><SPAN><b>
												附件编号 </b></SPAN></TD>
									<TD class=' GMRow GMHeaderText GMCellHeader'
										style='BACKGROUND-COLOR: rgb(239, 239, 239)' colSpan=1><SPAN><b>
												附件名称 </b></SPAN></TD>
									<TD class=' GMRow GMHeaderText GMCellHeader'
										style='BACKGROUND-COLOR: rgb(239, 239, 239)' colSpan=1><SPAN><b>
												附件版本 </b></SPAN></TD>
									<TD class=' GMRow GMHeaderText GMCellHeader'
										style='BACKGROUND-COLOR: rgb(239, 239, 239)' colSpan=1><SPAN><b>
												Spec Review </b></SPAN></TD>
									<TD class=' GMRow GMHeaderText GMCellHeader'
										style='BACKGROUND-COLOR: rgb(239, 239, 239)' colSpan=1><SPAN><b>
												不涉及原因 </b></SPAN></TD>
									<TD class=' GMRow GMHeaderText GMCellHeader'
										style='BACKGROUND-COLOR: rgb(239, 239, 239)' colSpan=1><SPAN><b>
												转化后文档编号 </b></SPAN></TD>
									<TD class=' GMRow GMHeaderText GMCellHeader'
										style='BACKGROUND-COLOR: rgb(239, 239, 239)' colSpan=1><SPAN><b>
												转化后ECN编号 </b></SPAN></TD>
									<TD class=' GMRow GMHeaderText GMCellHeader'
										style='BACKGROUND-COLOR: rgb(239, 239, 239)' colSpan=1><SPAN><b>
												部门经理评审意见 </b></SPAN></TD>
								</tr>
							</table>

						</td>
					</tr>

					<tr>
						<td>
							<table id="recordlist" class=GMSection>
								<tr>
									<TH style="WIDTH: 0px"></TH>
									<TH style="WIDTH: 30px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 150px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 300px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 80px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 100px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 200px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 150px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 150px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 300px; HEIGHT: 0px"></TH>
								</tr>
							</table>

						</td>
					</tr>
				</table>
				</div>
			</td>
		</tr>
        <tr>
			<td align="left" valign="middle"
				style="BACKGROUND-COLOR: rgb(239, 239, 239)">
				<div id="savecancel">
					
					&nbsp;&nbsp;
					<button id=savebtn class=button onClick='javascript:savedata();'>
						<SPAN id=generatespan>提交&nbsp;</SPAN>
					</button>
				</div>
			</td>

			<td style='BACKGROUND-COLOR: rgb(239, 239, 239)'></td>

		</tr>
	</table>

</body>
<script type="text/javascript">

	loadSWR();
</script>

</html>
