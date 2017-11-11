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
 * table element JQuery Version
 * @author Hua.Huang
 */
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

function createHideInputTD(count, value, width) {
	var newTD = "<td 'class'='GMColorNoFocus GMRow GMText GMCell'"
			+ " 'className'='GMColorNoFocus GMRow GMText GMCell'>"
			+ "<input id='flag" + count
			+ "' value='N' type='hidden'/> <input type='hidden' id='ID" + count
			+ "' value=" + value + "></td>";
	return newTD;
}

function createHideTD(tdID, count, value, width) {
	var newTD = "<td>" + "<input id='" + tdID + count + "' value='" + value
			+ "' type='hidden'/> </td>";
	return newTD;
}

function createDisabledTextAreaTD(tdID, count, value, width) {
	var newTD = "<td 'class'='GMColorNoFocus GMRow GMText GMCell'"
			+ " 'className'='GMColorNoFocus GMRow GMText GMCell'>"
			+ "<textarea id='" + tdID + count + "'  rows='3' style='width:"
			+ width + "px;' onchange =alertflag('flag" + count
			+ "')  disabled='disabled'>" + value + "</textarea> " + "</td>";
	return newTD;
}

function createEnabledTextAreaTD(tdID, count, value, width) {
	var newTD = "<td 'class'='GMColorNoFocus GMRow GMText GMCell'"
			+ " 'className'='GMColorNoFocus GMRow GMText GMCell'>"
			+ "<textarea id='" + tdID + count + "'  rows='3' style='width:"
			+ width + "px;' onchange =alertflag('flag" + count + "') >" + value
			+ "</textarea> " + "</td>";
	return newTD;
}

function createSelectTD(tdID, count, width) {
	var newTD = "<TD><select id='" + tdID + count + "' onchange =alertflag('flag" 
	+ count + "') style='width:" + width + "px;'></select></TD>";
	return newTD;
}
