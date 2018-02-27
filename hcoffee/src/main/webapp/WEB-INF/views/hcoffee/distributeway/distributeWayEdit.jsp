<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>下发方式修改</title>
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
		<li><a href="${ctx}/distributeway/distributeWay/list">下发方式列表</a></li>
		<li class="active"><a href="${ctx}/distributeway/distributeWay/form?distributeWayNum=${distributeWay.distributeWayNum}">下发方式修改</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="distributeWay" action="${ctx}/distributeway/distributeWay/save" method="post" class="form-horizontal">
		<form:hidden path="distributeWayNum"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">下发方式:</label>
			<div class="controls">
				<label class="lbl">${distributeWay.distributeWay}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">定义:</label>
			<div class="controls">
				<form:input path="distributeWayDesc" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态:</label>
			<div class="controls">
               <form:radiobutton path="dataStatus" value="0"/>启用
               <form:radiobutton path="dataStatus" value="1"/>禁用
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="distributeway:distributeway:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>