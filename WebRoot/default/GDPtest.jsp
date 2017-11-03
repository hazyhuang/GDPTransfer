<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.4/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.4.4/themes/color.css">
<link rel="stylesheet" type="text/css" href="../css/style.css">

<script type="text/javascript" src="../jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.4.4/datagrid-groupview.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.4.4/datagrid-cellediting.js"></script>
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

<title>GDP转化责任人确认</title>


<script type="text/javascript">
   //http://172.16.90.32:7001/Agile/PLMServlet?action=OpenEmailObject&classid=10000
    var thisurl=  "<%=request.getContextPath()%>/default/GDPTransfer.jsp";      
	


	
	function loadSWR() {
		var swrlist;
		$.ajax({
			   url: 'initGDPReview.json',
			   dataType: 'json',
			   async: false,
			   success: function(result) {
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
			  
			   }
			  });
	}
	function getInnerTD(){
	 	   
		var inner = "XX: <input id='xx' class='easyui-combobox' "
		    +" data-options=\"width:130"
		    +",valueField:'index',textField:'value',url:'doclist.json'\">";
	    return inner;
	 
	}
	function loadSWR2(swrlist) {
		 $("#xxx").append("<td>"+getInnerTD()+"</td>");
		    
		
	}
</script>
</head>
<body>
	<input name="recordcount" value="0" type="hidden" class="formElm"
		size="8" id="recordcount" />
	<table>
		<tr id="xxx"></tr>
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
							<table id="recordlist">
								<tr>
									<TH style="WIDTH: 0px"></TH>
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
<script type="text/javascript">

	loadSWR();
</script>
</body>


</html>
