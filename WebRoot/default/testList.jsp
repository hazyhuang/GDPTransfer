<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
<script type="text/javascript">
    var doclist;
    var str="<th>1</th>";
    $("#table1").append(str);
    $.getJSON("doclist.json", function(result) 
    	{
       // doclist=result;
        console.log(doclist);
        console.log(JSON.stringify(doclist));
        });
    
    $.ajax({
		url : 'doclist.json',
		dataType : 'json',
		async : false,
		success : function(result) {
			
				doclist = result;
			
		}
	});
 
</script>
</head>
<body>
<label>责任人：</label>
<select class="easyui-combobox" id="approver" name="approver" required="true"  style="width:200px;" data-options="
                    valueField: 'value',
                    textField: 'index'"></select>
<br>
cc3:<select id="cc3" class="easyui-combobox" name="dept" style="width:200px;">
    <option value="aa">aitem1</option>
    <option>bitem2</option>
    <option>bitem3</option>
    <option>ditem4</option>
    <option>eitem5</option>
</select>

<br>
cc2:<input id="cc2" class="easyui-combobox" name="dept" >
<br>
cc1:<input id="cc1" class="easyui-combobox" name="dept" value="D003" width=30
   data-options="width:30,valueField:'index',textField:'value',url:'doclist.json'">
<br>
cc5:<input id="cc5" class="easyui-combobox" name="dept"
   data-options="width:150,valueField:'index',textField:'value'">

<br>
cc4:<input id="cc4" class="easyui-combobox" name="dept"
   data-options="valueField:'index',textField:'value',data:[{'index':'D012','value':'D012'},{'index':'D011','value':'D011'}]">
<h4>表头：</h4>

<table border="1" id="table1">
<tr id='tr1'></tr>
</table>
<button id=savebtn class=button onClick='javascript:savedata();'>
						<SPAN id=generatespan>提交&nbsp;</SPAN>
					</button>
<script type="text/javascript">
   
    $("#tr1").append("<td>"+getInnerTD()+"</td>");
    function getInnerTD(){
 	   
		var inner = "XX: <input id='xx' class='easyui-combobox' "
		    +" data-options=\"width:130"
		    +",valueField:'index',textField:'value'\">";
	    return inner;
	 
	}
    function savedata(){
    	var value=$('#xx').combobox("getValue");
    	var text=$('#xx').combobox("getText");
    	console.log(text+":"+value);
    }
 
    $('#cc5').combobox(
        {
        valueField:'index',
        textField:'value',
        data:doclist
        });
    
    $('#cc1').combobox({ 
    	
    	width:100}); 
$('#xx').combobox({ 
	  data:doclist,
	  value:'D002'}); 
//$('#xx').combobox("select","0002");   
//$('#cc4').combobox("select","D012"); 
//$('#xx').combobox("setText","0001");        
//$('#xx').combobox("select","D002");   	
</script>
</body>
</html>

