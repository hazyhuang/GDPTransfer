<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript" src="../jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="singlelist.js"></script>
<script type="text/javascript"> 
var doclist;
$.ajaxSettings.async = false; 
$.ajax({
	url : 'reviewlist.json',
	dataType : 'json',
	async : false,
	success : function(result) {
		doclist = result;
	}
});
	window.onload = function() {
	
	};
	</script>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  
<select id="doc1"  style="width:100px;">
</select><button id="btn"  onClick='javascript:approve();'>XX</button>
			<script type="text/javascript"> 
		
			var sList = document.getElementById('doc1');
			initList(doclist, sList);
			setValue(doclist,'NO_INVOLVE',sList);//NO_INVOLVE
			function approve(){
				  var selectValue = $("#doc1").val();
				  var selectIndex = $("#doc1").find("option:selected").text();
				  console.log(selectValue);
				  console.log(selectIndex);
			}
			</script>
			<br>
    This is my JSP page.
  </body>
</html>
