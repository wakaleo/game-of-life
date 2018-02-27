function setMsg(input, msg) {
	var lb = input + "_msg";
	document.getElementById(lb).innerText = msg;
	document.getElementById(input).focus();
}

function checkPhoneNum(arg0) {
	var flag = true;
	var input = document.getElementById(arg0);
	var value = input.value.replace(/^\s+|\s+$/g, "");
	input.value = value;
	if (value != "") {
		flag = isPhoneNum(value);
		if (!flag) {
			setMsg(arg0, "请输入正确的手机号码");
		}
	}
	return flag;
}

function checkBankCard(arg0) {
	var flag = true;
	var input = document.getElementById(arg0);
	var value = input.value.replace(/^\s+|\s+$/g, "");
	input.value = value;
	if (value != "") {
		flag = isBankCardNo(value);
		if (!flag) {
			setMsg(arg0, "请输入正确的银行卡号");
		}
	}
	return flag;
}

function checkIdCardNo(arg0) {
	var flag = true;
	var input = document.getElementById(arg0);
	var value = input.value.replace(/^\s+|\s+$/g, "");
	input.value = value;
	if (value != "") {
		flag = isIdCardNo(value);
		if (!flag) {
			setMsg(arg0, "请输入正确的身份证号");
		}
	}
	return flag;
}

function checkMoney(arg0) {
	var flag = true;
	var input = document.getElementById(arg0);
	var value = input.value.replace(/^\s+|\s+$/g, "");
	input.value = value;
	if (value != "") {
		flag = isMoney(value);
		if (!flag) {
			setMsg(arg0, "请输入正确的金额");
		}
	}
	return flag;
}

function checkNum(arg0) {
	var flag = true;
	var input = document.getElementById(arg0);
	var value = input.value.replace(/^\s+|\s+$/g, "");
	input.value = value;
	if (value != "") {
		flag = isNum(value);
		if (!flag) {
			setMsg(arg0, "请输入正确的数字");
		}
	}
	return flag;
}

function checkNumber(arg0) {
	var flag = true;
	var input = document.getElementById(arg0);
	var value = input.value.replace(/^\s+|\s+$/g, "");
	input.value = value;
	if (value != "") {
		flag = isNumber(value);
		if (!flag) {
			setMsg(arg0, "请输入正整数");
		}
	}
	return flag;
}

function checkNull(arg0) {
	var flag = true;
	var input = document.getElementById(arg0);
	var value = input.value.replace(/^\s+|\s+$/g, "");
	input.value = value;
	if (value.length == 0) {
		flag = false;
		setMsg(arg0, "此项不能为空");

	}
	return flag;
}

function checkMax(arg0, max) {
	var flag = true;
	var input = document.getElementById(arg0);
	var value = input.value.replace(/^\s+|\s+$/g, "");
	input.value = value;
	if (value != "") {
		if (value > max) {
			flag = false;
			setMsg(arg0, "请输入小于" + max + "的数字");
		}
	}
	return flag;
}

function checkLength(arg0, arg1, arg2) {
	var value = "";
	var input = document.getElementById(arg0);
	value = input.value.replace(/^\s+|\s+$/g, "");
	if (value.length >= arg1 && value.length <= arg2) {
		return true;
	}
	return false;
}

function isPhoneNum(tel) {
	return /^1([34578][0-9])\d{8}$/.test(tel);
}

function isMoney(money) {
	return /^([1-9][\d]{0,14}|0)(\.[\d]{1,2})?$/.test(money);
}

function isNum(num) {
	return /^([1-9][\d]{0,14}|0)(\.[\d]{1,8})?$/.test(num);
}

function isNumber(num) {
	return /^[1-9][\d]*$/.test(num);
}

function isBankCardNo(cardNo) {
	return /^(\d{16}|\d{19})$/.test(cardNo);
}

function isIdCardNo(idCardNo){
	return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(idCardNo);
}