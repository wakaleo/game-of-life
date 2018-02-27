<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户修改</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/usermanagement/userManagement/list">用户管理列表</a></li>
		<li class="active"><a href="${ctx}/usermanagement/userManagement/form?userNum=${userManagement.userNum}">用户管理修改</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userManagement" action="${ctx}/usermanagement/userManagement/save" method="post" class="form-horizontal">
		<form:hidden path="userNum"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">下发方式:</label>
			<div class="controls">
				<label class="lbl">${userManagement.userNum}</label>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">用户动作:</label>
			<div class="controls">
				<label class="lbl">
					<c:choose>
						<c:when test="${userManagement.userAction == '1'}">
							新注册
						</c:when>
						<c:when test="${userManagement.userAction == '2'}">
							首次授信
						</c:when>
						<c:when test="${userManagement.userAction == '3'}">
							完成首次还款
						</c:when>
						<c:when test="${userManagement.userAction == '4'}">
							完成首次投资购订单
						</c:when>
					</c:choose>
				</label>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">平台归属:</label>
			<div class="controls">
				<label class="lbl">
					<c:choose>
						<c:when test="${userManagement.datasource == '1'}">
							汇有房
						</c:when>
						<c:when test="${userManagement.datasource == '2'}">
							汇理财
						</c:when>
						<c:when test="${userManagement.datasource == '3'}">
							汇生活
						</c:when>
					</c:choose>
				</label>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">动作名称:</label>
			<div class="controls">
				<form:input path="userActionName" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">用户状态:</label>
			<div class="controls">
               <form:radiobutton path="dataStatus" value="0"/>启用
               <form:radiobutton path="dataStatus" value="1"/>禁用
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更多备注:</label>
			<div class="controls">
				<form:textarea path="notes" htmlEscape="false" rows="3" maxlength="100" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="usermanagement:userManagement:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>