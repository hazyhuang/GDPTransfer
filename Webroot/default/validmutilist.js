function validMutilist(strs, datalist) {
	var selectedList = strs.split(';');
	var failNumbers = new Array();

	for (var i = 0; i < selectedList.length; i++) {

		var chk = validNumber(selectedList[i], datalist);
		if (!chk) {
			failNumbers.push(selectedList[i]);
		}
	}
	return failNumbers;
}
function validNumber(Number, datalist) {
	for (var i = 0; i < datalist.length; i++) {
		if (datalist[i].value == Number) {
			return true;
		}
	}
	return false;
}

function showMutiList(strs,subclassid){
	  var selectedList = strs.split(';');
	  var links = new Array();
	 
	for (var i = 0; i < selectedList.length; i++) {
		var numberObj=selectedList[i].split(':');
		var link="<A href='"+agileurl+"/PLMServlet?action=OpenEmailObject&classid="
				+subclassid+"&objid="+numberObj[1]+"'>"+ numberObj[0] + "</A> ";
		links.push(link);
	}
	return links;
}