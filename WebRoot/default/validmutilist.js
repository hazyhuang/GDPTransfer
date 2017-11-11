/**
 * 检查多列表strs(字符串形式，以;隔开)在datalist(Array)里是否存在
 * @param strs
 * @param datalist
 * @returns
 */
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
/**
 * 返回多个<A>数据
 * 展示多列表strs,具有直接打开功能
 * @param strs
 * @param subclassid
 * @returns
 */
function showMutiList(agileURL,strs,subclassid){
	  var selectedList = strs.split(';');
	  var links = new Array();
	 
	for (var i = 0; i < selectedList.length; i++) {
		var numberObj=selectedList[i].split(':');
		var link="<A href='"+agileURL+"/PLMServlet?action=OpenEmailObject&classid="
				+subclassid+"&objid="+numberObj[1]+"'>"+ numberObj[0] + "</A> ";
		links.push(link);
	}
	return links;
}