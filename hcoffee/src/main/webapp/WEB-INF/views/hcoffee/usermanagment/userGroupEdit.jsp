<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户组修改</title>
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
		<li><a href="${ctx}/usermanagement/userGroup/list">用户组列表</a></li>
		<li class="active"><a href="${ctx}/usermanagement/userGroup/form?userGroupNum=${userGroup.userGroupNum}">用户组修改</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userGroup" action="${ctx}/usermanagement/userGroup/save" method="post" class="form-horizontal">
		<form:hidden path="userGroupNum"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">用户组编码:</label>
			<div class="controls">
				<label class="lbl">${userGroup.userGroupNum}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户组名称:</label>
			<div class="controls">
				<form:input path="userGroupName" htmlEscape="false" maxlength="20" class="required"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">平台归属:</label>
			<div class="controls">
				<label class="lbl">
					<c:choose>
						<c:when test="${userGroup.datasource == '1'}">
							汇有房
						</c:when>
						<c:when test="${userGroup.datasource == '2'}">
							汇理财
						</c:when>
						<c:when test="${userGroup.datasource == '3'}">
							汇生活
						</c:when>
					</c:choose>
				</label>
			</div>
		</div> --%>
		
		<%-- <div class="control-group">
			<label class="control-label">用户状态:</label>
			<div class="controls">
               <form:radiobutton path="dataStatus" value="0"/>启用
               <form:radiobutton path="dataStatus" value="1"/>禁用
			</div>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="usermanagement:userGroup:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>