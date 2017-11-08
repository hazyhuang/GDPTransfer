function setValue(list,value,selectTag) {
	var sIndex = getSingleListIndex(list,value);
	if(sIndex!=0){
	selectTag.selectedIndex = sIndex;
	}
}


function getSingleListIndex(list,value) {
	console.log(value);
	for (var i = 0; i < list.length; i++) {
		if (list[i].index == value) {
			return i + 1;
		}
	}
	return 0;
}

function initList(list, selectObj) {
	// 为1级菜单select添加option
	var op = new Option("", "");
	selectObj.options.add(op);
	for (var i = 0; i < list.length; i++) {
		var op = new Option(list[i].value, list[i].index);
		selectObj.options.add(op);
	}
}