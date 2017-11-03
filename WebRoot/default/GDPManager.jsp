<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>GDP转化人部门经理审核</title>
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
<script type="text/javascript"
	src="../jquery-easyui-1.4.4/jquery.min.js"></script>

<script type="text/javascript" src="table.js"></script>
<script type="text/javascript" src="GDPManager.js"></script>
<script type="text/javascript">
    var thisurl=  "<%=request.getContextPath()%>/default/GDPManager.jsp";      

  
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
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table id="recordlist" class=GMSection>
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
					 &nbsp;&nbsp;审批意见:
					<textarea id="comment" rows='3' style='width:300px;'></textarea>
					&nbsp;&nbsp;
					<button id=rejectbtn class=button
						onClick='javascript:reject();'>
						<SPAN id=generatespan>拒绝&nbsp;</SPAN>
					</button>
					&nbsp;&nbsp;
					<button id=approvebtn class=button onClick='javascript:approve();'>
						<SPAN id=generatespan>批准&nbsp;</SPAN>
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
