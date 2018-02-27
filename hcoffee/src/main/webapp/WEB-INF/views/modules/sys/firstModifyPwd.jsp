<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html >
<head>
	<title>C端贷款审核</title>
	<meta name="decorator" content="default"/>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	
<script type="text/javascript">
$(function() {
			$("input").blur(function(){
    	
				$('#loginName_msg').text('');
				$('#oldPassword_msg').text('');
				$('#newPassword_msg').text('');
				$('#surePassword_msg').text('');
		    	var loginName = $('#loginName').val();
				var oldPassword = $('#oldPassword').val();
				var newPassword = $('#newPassword').val();
				var surePassword = $('#surePassword').val();
				if (loginName == null || loginName == '') {
					//alert("登录账号不能为空!")
					$('#loginName_msg').text('登录账号不能为空');
				}
				if (oldPassword == null || oldPassword == '') {
					//alert("旧密码不能为空!")
					$('#oldPassword_msg').text('旧密码不能为空');
				}
				if (newPassword == null || newPassword == '') {
					$('#newPassword_msg').text('新密码不能为空');
				}
				if (surePassword == null || surePassword == '') {
					//alert("确定密码不能为空!")
					$('#surePassword_msg').text('确定密码不能为空');
				}else{
					if(newPassword!=surePassword){
						$('#surePassword_msg').text('确定密码不一致');
					}
				}

		  });

	});
	function updatePwd() {
		var loginName = $('#loginName').val();
		var oldPassword = $('#oldPassword').val();
		var newPassword = $('#newPassword').val();
		var surePassword = $('#surePassword').val();
		if (loginName == null || loginName == '') {
			//alert("登录账号不能为空!")
			$('#loginName_msg').text('登录账号不能为空');
			return;
		}
		if (oldPassword == null || oldPassword == '') {
			//alert("旧密码不能为空!")
			$('#oldPassword_msg').text('旧密码不能为空');
			return;
		}
		if (newPassword == null || newPassword == '') {
			$('#newPassword_msg').text('新密码不能为空');
			return;
		}
		if (surePassword == null || surePassword == '') {
			//alert("确定密码不能为空!")
			$('#surePassword_msg').text('确定密码不能为空');
			return;
		}

		var data = {
			'loginName' : loginName,
			'oldPassword' : oldPassword,
			'newPassword' : newPassword
		};
		$.ajax({
			type : 'post',
			url : '${ctx}/userTimer/getUserByPwd',
			data : data,
			async : false,
			success : function(date) {

				if (date.isEmpty == '0') {
					alert(date.msg);
				} else {
					
					$.ajax({
						type : 'post',
						url : '${ctx}/userTimer/toUpdateUser',
						data : data,
						async : false,
						success : function(date) {
							alert('设置成功');
							window.location.href ='${ctx}/login';
						}
					});
					
						
				}
			}
		});

	}
	
	
</script>	
</head>
<body>

	<div class="header">
		<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">${message}</label>
		</div>
	</div>
	<h1 class="form-signin-heading">修改密码</h1>
	<form id="customerFinancialInfoForm" action="" method="post" class="breadcrumb form-search">
	   <table class="table table-striped table-bordered table-condensed" >
	
		<tr>
			<td width="15%" style="text-align:right">
				<label >用户账号：</label>
			</td>
			<td width="35%">
				<input type="text" id="loginName" name="loginName" maxlength="15" class="input-block-level required" value="${user.loginName }" >
				<label id="loginName_msg" class="msg"></label>
			</td>
		</tr>
		<tr>
			<td width="15%" style="text-align:right">
				<label >旧密码：</label>
			</td>
			<td width="35%">
				<input type="password" id="oldPassword" name="oldPassword" class="input-block-level required">
				<label id="oldPassword_msg" class="msg"></label>
			</td>
		</tr>
		<tr>
			<td width="15%" style="text-align:right">
				<label >新密码：</label>
			</td>
			<td width="35%">
				<input type="password" id="newPassword" name="newPassword" class="input-block-level required">
				<label id="newPassword_msg" class="msg"></label>
			</td>
		</tr>
		
		<tr>
			<td width="15%" style="text-align:right">
				<label >确认密码：</label>
			</td>
			<td width="35%">
				<input type="password" id="surePassword" name="surePassword" class="input-block-level required">
				<label id="surePassword_msg" class="msg"></label>
			</td>
		</tr>
		<tr>	
<!-- 			<td colspan="4" style="text-align:center">
    				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="updatePwd()"  value="保 存"/>
			</td>
 -->			
			<td colspan="2"  style="text-align:center">
						<input id="btnSubmit" class="btn btn-primary" type="button" onclick="updatePwd()"  value="保 存"/>
			</td>
		</tr>
	   </table>
	</form>
</body>
</html>