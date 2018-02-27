<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台下发方式</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/distributeway/distributeWay/list">下发方式列表</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>下发方式</th>
				<!-- <th>定义</th> -->
				<th>状态</th>
					<shiro:hasPermission name="distributeway:distributeway:edit">
						<th>操作</th>
					</shiro:hasPermission>
			</tr>
		</thead>
		<tbody><%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
		<c:forEach items="${page.list}" var="distributeWay" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td>${distributeWay.distributeWay}</td>
				<%-- <td>${distributeWay.distributeWayDesc}</td> --%>
				<td>
					<c:choose>
						<c:when test="${distributeWay.dataStatus == '0'}">启用</c:when>
						<c:otherwise>禁用</c:otherwise>
					</c:choose>
				</td>
				<shiro:hasPermission name="distributeway:distributeway:edit"><td>
						<c:choose>
							<c:when test="${distributeWay.dataStatus == '1'}">
								<a href="${ctx}/distributeway/distributeWay/save?distributeWayNum=${distributeWay.distributeWayNum}&dataStatus=0" onclick="return confirmx('确认要启用该字典吗？', this.href)">启用</a>
							</c:when>
							<c:otherwise>
								<a href="${ctx}/distributeway/distributeWay/save?distributeWayNum=${distributeWay.distributeWayNum}&dataStatus=1" onclick="return confirmx('确认要禁用该字典吗？', this.href)">禁用</a>
							</c:otherwise>
						</c:choose>
					
	    				<%-- <a href="${ctx}/distributeway/distributeWay/form?distributeWayNum=${distributeWay.distributeWayNum}">编辑</a>
	    				<a href="${ctx}/distributeway/distributeWay/details?distributeWayNum=${distributeWay.distributeWayNum}">查看</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>