/*
 * Copyright 2012 - 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 * @author Hua.Huang
 */
function setSingleListValue(list,value,selectTag) {
	var sIndex = getSingleListIndex(list,value);
	if(sIndex!=0){
	selectTag.selectedIndex = sIndex;
	}
}

/**
 * 
 * @param list
 * @param value
 * @returns
 */
function getSingleListIndex(list,value) {
	console.log(value);
	for (var i = 0; i < list.length; i++) {
		if (list[i].index == value) {
			return i + 1;
		}
	}
	return 0;
}
/**
 * list为Array
 * 初始化 列表项
 * @param list
 * @param selectObj
 * @returns
 */
function initSingleList(list, selectObj) {
	// 为1级菜单select添加option
	var op = new Option("", "");
	selectObj.options.add(op);
	for (var i = 0; i < list.length; i++) {
		var op = new Option(list[i].value, list[i].index);
		selectObj.options.add(op);
	}
}