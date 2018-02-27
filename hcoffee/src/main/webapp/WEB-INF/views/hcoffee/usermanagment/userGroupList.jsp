<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户组管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
    	return false;
    }
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/usermanagement/userGroup/list">用户组管理列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="userManagement" action="${ctx}/usermanagement/userGroup/list" method="post" class="breadcrumb form-search ">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>用户组编码</th>
				<th>用户组名称</th>
			<!-- 	<th>平台归属</th> -->
				<shiro:hasPermission name="userManagement:userGroup:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody><%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
		<c:forEach items="${page.list}" var="date" varStatus="status">
			<tr>
				<td>${(status.index+1)+((page.pageNo-1) * page.pageSize)}</td>
				<td>${date.userGroupNum}</td>
				<td>${date.userGroupName}</td>
				<%-- <td>${date.datasource}</td> --%>
				<%-- <td>
					<c:choose>
						<c:when test="${date.dataStatus == '0'}">启用</c:when>
						<c:otherwise>禁用</c:otherwise>
					</c:choose>
				</td> --%>
				<shiro:hasPermission name="userManagement:userGroup:edit"><td>
					<a href="${ctx}/usermanagement/userGroup/save?userGroupNum=${date.userGroupNum}&dataStatus=1" onclick="return confirmx('确认要删除用户组吗？', this.href)">删除</a>
    				<a href="${ctx}/usermanagement/userGroup/form?userGroupNum=${date.userGroupNum}">编辑</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form:form>
	<div class="pagination">${page}</div>
</body>
</html>