function createTH(width){
	return "<TH style='WIDTH: "+width+"px; HEIGHT: 0px'></TH>";
}
	
function createTD(value){
	var td="<TD class=' GMRow GMHeaderText GMCellHeader' "
		+ "style='BACKGROUND-COLOR: rgb(239, 239, 239)' colSpan=1><SPAN><b> "
		+value+ "</b></SPAN></TD>";
		return td;
}
	
function createOrangeTD(value){
	var td="<TD class=' GMRow GMHeaderText GMCellHeader' "
		+ "style='BACKGROUND-COLOR: rgb(200, 150, 80)' colSpan=1><SPAN><b> "
		+value+ "</b></SPAN></TD>";
		return td;
}


function getDisabledTextAreaTD(newTD,tdID,count,value,width){
    newTD.id = tdID;
    newTD.setAttribute("class", "GMColorNoFocus GMRow GMText GMCell");
    newTD.setAttribute("className","GMColorNoFocus GMRow GMText GMCell");
    newTD.innerHTML = " <textarea id='"+tdID+"'"
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

function getEnabledLinkTD(newTD,tdID,count,value,width){
    newTD.id = tdID;
    newTD.setAttribute("class", "GMColorNoFocus GMRow GMText GMCell");
    newTD.setAttribute("className","GMColorNoFocus GMRow GMText GMCell");
    newTD.innerHTML = " <A href='http://agileplm:7001/Agile/PLMServlet?action=OpenEmailObject&classid=9000&objid=6758066'>"+ value + "</A> ";
}

function alertflag(flagname) {
	document.getElementById(flagname).value = 'Y';
}

//替换特殊字符
function replaceReg(value) {
	return value.replace(/\&/g, '%26').replace(/\+/g, '%2b').replace(/\;/g,
			'%3b');
}