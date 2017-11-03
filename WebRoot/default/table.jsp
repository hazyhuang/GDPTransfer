<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="../jquery-easyui-1.5.2/jquery.min.js"></script>

</head>
<body>
<h4>表头：</h4>
<table border="1" id="table1">
<tr>
									<TH style="WIDTH: 0px"></TH>
									<TH style="WIDTH: 30px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 150px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 300px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 80px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 100px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 200px; HEIGHT: 0px"></TH>
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
												部门经理评审意见 </b></SPAN></TD>
								</tr>
<tr>
  <th>姓名</th>
  <th>电话</th>

</tr>
<tr>
  <td>Bill Gates</td>
  <td></td>
  <td>555 77 855</td>
</tr>
<tr>
  <td>James</td>
  <td>555 77 854</td>
</tr>
</table>
<script type="text/javascript">
var table = document.getElementById("table1");
var newTH1 = document.createElement("TH");
newTH1.setAttribute("style", "WIDTH: 30px; HEIGHT: 0px");
var str="<th>1</th>";
table.appendChild(newTH1);
$("table").append(str);
var tt=$("table");
console.log("tt:"+tt);
console.log("tt:"+table);
</script>
</body>

</html>

