function setValue(docType, issue, ci, seq) {
	var docIndex = getDocIndex(docType);
	if(docIndex!=0){
	ci.selectedIndex = docIndex;
	createIssue(seq);
	
	var di = document.getElementById("issue" + seq);

	di.selectedIndex = getIssueIndex(docTypes[docIndex - 1].children, issue);
	//console.log(docType + "   " + getDocIndex(docType) + " "+ di.selectedIndex);
	}
}

function getValue(docType, issue) {

	var docIndex = getDocIndex(docType);
	var value = getDocText(docType)+"|"+getIssueText(docTypes[docIndex - 1].children,issue);
    return value;
}

function getDocIndex(docType) {
	for (var i = 0; i < docTypes.length; i++) {
		if (docTypes[i].id == (docType)) {
			return i + 1;
		}
	}
	return 0;
}
function getDocText(docType) {
	for (var i = 0; i < docTypes.length; i++) {
		if (docTypes[i].id == (docType)) {
			return docTypes[i].text;
		}
	}
	return "";
}
function getIssueText(issuelist, issue) {
	for (var i = 0; i < issuelist.length; i++) {
		if (issuelist[i].id == (issue)) {
			return issuelist[i].text;
		}
	}
	return "";
}
function getIssueIndex(issuelist, issue) {
	for (var i = 0; i < issuelist.length; i++) {
		if (issuelist[i].id == (issue)) {
			return i + 1;
		}
	}
	return 0;
}
function initDocument(docTypes, ci) {
	// 获得1级菜单select

	// 为1级菜单select添加option
	var op = new Option("", "");
	ci.options.add(op);
	for (var i = 0; i < docTypes.length; i++) {
		var op = new Option(docTypes[i].text, docTypes[i].id);
		//console.log(docTypes[i].text);
		ci.options.add(op);
	}
}
function issueChange(seq) {
	var ci = document.getElementById("doc" + seq);
	var di = document.getElementById("issue" + seq);
	var cindex = ci.selectedIndex - 1;
	var dindex = di.selectedIndex - 1;
	//console.log(docTypes[cindex].text);
	var issues = docTypes[cindex].children;
	//console.log(docTypes[cindex].id + "[change-id]" + issues[dindex].id);
	//console.log(docTypes[cindex].text + "[change-text]" + issues[dindex].text);
	alertflag("flag"+seq);

}
function createIssue(seq) {

	var ci = document.getElementById("doc" + seq);
	// 获得2级菜单select
	var di = document.getElementById("issue" + seq);
	// 获取当前选中的一级菜单的选项的下标
	var selIndex = ci.selectedIndex;
	//console.log(selIndex);
	var index = ci.selectedIndex - 1;

	// 清空二级菜单选项
	di.options.length = 0;
	// 为2级菜单select添加option
	if (index != -1) {
		var issues = docTypes[index].children;
		if (issues != null) {
			var op = new Option("", "");
			di.options.add(op);
			for (var i = 0; i < issues.length; i++) {
				var op = new Option(issues[i].text, issues[i].id);
				//console.log(issues[i].text);
				di.options.add(op);
			}
		}
	}
}