<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="../jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="singlelist.js"></script>
<script type="text/javascript" src="table.js"></script>
<script type="text/javascript" src="GDPTransfer.js"></script>
<script type="text/javascript" src="validmutilist.js"></script>
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
    var userid='<%=request.getParameter("agile.userName")%>'//userid
	var chgnum='<%=request.getParameter("agile.1047")%>'
		var username='<%=request.getAttribute("fullName")%>'
    //http://agileplm:7001/Agile/PLMServlet?action=OpenEmailObject&classid=9000&objid=6758066
    var thisurl=  "<%=request.getContextPath()%>/default/GDPTransfer.jsp";      
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
			<div id="msg" style="color:#F00">请不要关闭窗口,正在加载数据...</div>
			<div style="overflow-x:scroll;width:1300px;white-space:nowrap;">
				<table>
					<tr>
						<td>
							<table id="recordlistTitle" class=GMSection>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table id="recordlist">
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
	loadData();
</script>
</html>
