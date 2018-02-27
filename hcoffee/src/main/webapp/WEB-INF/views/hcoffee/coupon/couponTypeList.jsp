<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务类别</title>
	<meta name="decorator" content="default"/>
	<style>
	</style>
</head>
<body>
	<sys:message content="${message}"/>
	<ul class="nav nav-tabs">
		<c:if test="${not empty businessList }">
		<c:forEach items="${businessList }" var="bean">
		<li <c:if test="${businessNum ==  bean.businessNum}">class="active"</c:if>><a href="${ctx}/marketing/coupon/couponTypeList?businessNum=${bean.businessNum }">${bean.businessName }</a></li>
		</c:forEach>
		</c:if>
	</ul>
	<br/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>卡券类别</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty couponTypeList }">
			<tr>
				<td style="text-align: center;" colspan="5">暂无数据</td>
			</tr>
			</c:if>
			<c:forEach items="${couponTypeList}" var="model" varStatus="bi">
				<tr>
					<td>${(bi.index+1)+((pageUtil.pageId-1) * pageUtil.pageSize)}</td>
					<td>${model.couponName }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
