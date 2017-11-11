function alertflag(flagname) {
	document.getElementById(flagname).value = 'Y';
}

//替换特殊字符
function replaceReg(value) {
	return value.replace(/\&/g, '%26').replace(/\+/g, '%2b').replace(/\;/g,
			'%3b');
}