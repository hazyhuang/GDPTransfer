<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head>
<%
String userid=request.getParameter("userid");
if(userid!=null){
request.getSession().setAttribute("USERID",userid);
}

%>
<title>SWRManage</title>
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
	src="../jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript">

	   var xmlHttp = null;  
	   var thisurl=  "<%=request.getContextPath()%>/default/SWRManage.jsp";      
	   var canEdit='Y';
	   function checkPrivilege(){
	   
	   if(canEdit!='Y'){
	   document.getElementById('addbtn').disabled=true;
	   document.getElementById('deletebtn').disabled=true;
	   document.getElementById('savebtn').disabled=true;
	   }
	   }
	   
	    function createXmlHttp() {                                                                                                                  
	      if (window.XMLHttpRequest) {                                                                                 
	         //FireFox、Opera                                                                
	         xmlHttp = new XMLHttpRequest();                                                                           
	      } else {                                                                                                     
	         //IE                                                                              
	         xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");                                                         
	      }     
	      return xmlHttp;                                                                                                       
	    } 
	    
	    function deletedata()
		{
			var table = document.getElementById("recordlist");
			var rowscount =table.rows.length;
			console.log(rowscount);
			var candelete = false;		
			 for(var i=1; i < rowscount ;i++){		
			    var tempid = table.rows[i].id;					
				var num = tempid.substr(2);
				console.log(tempid+" "+num);
				if(document.getElementById("checkbox"+num).checked == true)
				{
						if(confirm("你确定要删除选择的行?"))
						{
							candelete = true;
							break;
						}else return;
				}
			 }
			
			if(candelete)
			{
			    for(var i=rowscount-2; i >=0 ;i--)
			    {		
			    	console.log("[i]"+i);
			      	var tempid = table.rows[i+1].id;					
					var num = tempid.substr(2);
					console.log(tempid+" "+num);
					if(document.getElementById("checkbox"+num).checked == true)
					{
						console.log(tempid+" [del] "+num);
					  var ID = Math.ceil(document.getElementById('ID' + num).value);
					  if(ID == 0)
					  {
					  	  table.deleteRow(i+1);
					  }
					  else
					  {
					  	  url="<%=request.getContextPath()%>/default/SWR";      
			 			  url=url+"?Action=save&type=delete";
			 			  url=url+"&ID="+ID;
						  xmlHttp = createXmlHttp();                                                                      
	         			  xmlHttp.open("POST", url, true);
	         			  xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");       
	         			  xmlHttp.send(null); 
						  table.deleteRow(i+1);
					  }
		 			  
					}
				}
			} 
			else 
			{
				alert("请选择需要删除的记录行!");
			}
			 
		}
		
	    function savedata()
		{
		 url = "<%=request.getContextPath()%>/default/SWR";	 	 
	  	 url=url+"?Action=save&type=save";
		var reload = true;
		var data = "";
		var table = document.getElementById("recordlist");
		var rowscount = table.rows.length;
		var count = 0;
		for (i = 1; i <rowscount; i++) {
			console.log("[i]"+i);
			var tempid = table.rows[i].id;
			var num = tempid.substr(2);
			var ID = 0;
			var flag = "N";

			var Description = " ";

			var row = i - 1;

			if (document.getElementById('flag' + num).value != '')
				flag = document.getElementById('flag' + num).value;

			if (flag == "Y") {
				if (document.getElementById('ID' + num) != null) {
					if (document.getElementById('ID' + num).value != '')
						ID = document.getElementById('ID' + num).value;
				}
		

				if (document.getElementById('Description' + num).value != '')
					Description = document.getElementById('Description' + num).value;
				else {
					alert("行" + row + ",描述 不能为空!");
					reload = false;
					return;
				}
				var UseTime = 0;
				var dataline = "dataline=" + ID + ";"
						+ replaceReg(Description)+";"+i;

				xmlHttp = createXmlHttp();

				xmlHttp.open("POST", url, false);
				xmlHttp.setRequestHeader("Content-Type",
						"application/x-www-form-urlencoded");
				xmlHttp.send(encodeURI(encodeURI(dataline)));
				reload = true;
			}
		}

		if (reload) {
			alert("更新成功!");
			window.location.replace(thisurl);
		}
	}

	function add() {

		var addcountlen = Math
				.ceil(document.getElementById('addrecordcount').value);
		for (var j = 0; j < addcountlen; j++) {

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
			var newTd2 = newTr.insertCell(1);
			newTd2.setAttribute("class", "GMColorNoFocus GMRow GMText GMCell"); //class
			newTd2.setAttribute("className",
					"GMColorNoFocus GMRow GMText GMCell"); //class
			newTd2.innerHTML = "<input id='flag"+count+"' value='N' type='hidden'/> <input type='checkbox' id='checkbox"+count+"' id='checkbox"+count+"'  /><input type='hidden' name='ID"+count+"' value='0'>";

	

			var newTd4 = newTr.insertCell(2);
			var tdid4 = "Description";
			newTd4.id = tdid4;
			newTd4.setAttribute("class", "GMColorNoFocus GMRow GMText GMCell"); //class
			newTd4.setAttribute("className",
					"GMColorNoFocus GMRow GMText GMCell"); //class
			newTd4.innerHTML = " <textarea id='Description"
					+ count
					+ "'  rows='3' style='width:340px;' onchange =alertflag('flag"
					+ count + "')></textarea> ";
			document.getElementById('recordcount').value = count + 1;

		}

	}

	
	function loadSWRconnetServlet() {
		var swrlist;
		$.getJSON("swrjson", function(result) {
			  console.log(result);
			swrlist = result;
			loadSWR2(swrlist);
		});
		
	}
	
	function loadSWR() {
		var swrlist;
		$.getJSON("SWRJson.json", function(result) {
			  console.log(result);
			swrlist = result;
			loadSWR2(swrlist);
		});
		
	}
	
	function loadSWR2(swrlist){
		console.log(swrlist);
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
			var newTd2 = newTr.insertCell(1);
			newTd2.setAttribute("class", "GMColorNoFocus GMRow GMText GMCell"); //class
			newTd2.setAttribute("className",
					"GMColorNoFocus GMRow GMText GMCell"); //class
					 if(canEdit!='Y'){			
			newTd2.innerHTML = "<input id='flag"+count+"' value='N' type='hidden'/> <input type='checkbox'  id='checkbox"+count+"'  disabled='disabled'/><input type='hidden' id='ID"+count+"' value='"+swrlist[j].LineID+"'>";
					 }else{
			newTd2.innerHTML = "<input id='flag"+count+"' value='N' type='hidden'/> <input type='checkbox' id='checkbox"+count+"'  /><input type='hidden' id='ID"+count+"' value='"+swrlist[j].LineID+"'>";
									 
					 }
		
			var newTd4 = newTr.insertCell(2);
			var tdid4 = "Description";
			newTd4.id = tdid4;
			newTd4.setAttribute("class", "GMColorNoFocus GMRow GMText GMCell"); //class
			newTd4.setAttribute("className",
					"GMColorNoFocus GMRow GMText GMCell"); //class
		
			 if(canEdit!='Y'){
					newTd4.innerHTML = " <textarea id='Description"
						+ count
						+ "'  rows='3' style='width:340px;' onchange =alertflag('flag"
						+ count + "'  disabled='disabled')>"+swrlist[j].comment+"</textarea> ";
			  }else{
					newTd4.innerHTML = " <textarea id='Description"
						+ count
						+ "'  rows='3' style='width:340px;' onchange =alertflag('flag"
						+ count + "')>"+swrlist[j].comment+"</textarea> "; 
			  }
			document.getElementById('recordcount').value = count + 1;
		}
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
	<table width="100%" aglign="left">
		<tr>
			<td align="left" valign="middle"
				style="BACKGROUND-COLOR: rgb(239, 239, 239)">
				<div id="savecancel">
					<select valign="middle" name="addrecordcount" id="addrecordcount">
						<option value=1 selected="selected">1</option>
						<option value=2>2</option>
						<option value=3>3</option>
						<option value=4>4</option>
						<option value=5>5</option>
						<option value=6>6</option>
						<option value=7>7</option>
						<option value=8>8</option>
						<option value=9>9</option>
						<option value=10>10</option>
						<option value=11>11</option>
						<option value=12>12</option>
						<option value=13>13</option>
						<option value=14>14</option>
						<option value=15>15</option>
						<option value=16>16</option>
						<option value=17>17</option>
						<option value=18>18</option>
						<option value=19>19</option>
						<option value=20>20</option>
					</select> &nbsp;
					<button id=addbtn class=button onClick='javascript:add();'>
						<SPAN id=generatespan>增加&nbsp;</SPAN>
					</button>
					&nbsp;&nbsp;
					<button id=deletebtn class=button
						onClick='javascript:deletedata();'>
						<SPAN id=generatespan>删除&nbsp;</SPAN>
					</button>
					&nbsp;&nbsp;
					<button id=savebtn class=button onClick='javascript:savedata();'>
						<SPAN id=generatespan>保存&nbsp;</SPAN>
					</button>
				</div>
			</td>

			<td style='BACKGROUND-COLOR: rgb(239, 239, 239)'></td>

		</tr>
		<tr>
			<td height="35px" colspan=2></td>
		</tr>
		<tr>
			<td colspan=2>
				<table width="100%" align="left">
					<tr>
						<td>
							<table id="recordlistTitle" class=GMSection cellSpacing=0
								cellPadding=0 width=100%>
								<tr>
									<TH style="WIDTH: 0px"></TH>
									<TH style="WIDTH: 30px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 342px; HEIGHT: 0px"></TH>
								</tr>
								<tr class=GMHeaderRow style="OVERFLOW: auto;">
									<TD style="WIDTH: 0px;"></TD>
									<TD class=" GMRow GMHeaderText GMCellHeader"
										style="BACKGROUND-COLOR: rgb(239, 239, 239)" colSpan=1><SPAN><b></b></SPAN></TD>
								
									<TD class=' GMRow GMHeaderText GMCellHeader'
										style='BACKGROUND-COLOR: rgb(239, 239, 239)' colSpan=1><SPAN><b>Condition/Special
												Instructions<br>特殊作业条件/指示
										</b></SPAN></TD>
								</tr>
							</table>
							
						</td>
					</tr>
					
					<tr>
						<td>
							<table id="recordlist" class=GMSection cellSpacing=0
								cellPadding=0 width=100%>
								<tr>
									<TH style="WIDTH: 0px"></TH>
									<TH style="WIDTH: 30px; HEIGHT: 0px"></TH>
									<TH style="WIDTH: 342px; HEIGHT: 0px"></TH>
								</tr>
							</table>
							
						</td>
					</tr>
				</table>
			</td>
		</tr>

	</table>

</body>
<script type="text/javascript">

	loadSWR();
</script>

</html>
