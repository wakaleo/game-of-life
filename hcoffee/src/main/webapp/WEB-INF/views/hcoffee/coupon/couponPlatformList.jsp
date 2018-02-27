<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分平台卡券列表</title>
	<meta name="decorator" content="default"/>
	<style>
	.couponList{
		list-style: none;
	}
	.couponList li{
		float: left;padding: 0 5px 0 5px;
	}
	</style>
	<script type="text/javascript">
		$(function(){
		});
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	<ul class="nav nav-tabs">
		<c:forEach items="${platformNameMap }" var="bean">
		<li <c:if test="${platformNum ==  bean.key}">class="active"</c:if>><a href="${ctx}/marketing/coupon/couponListByPlatform?platformNum=${bean.key}">${bean.value }</a></li>
		</c:forEach>
	</ul>
	<br/>
	<ul class="couponList">
		<c:if test="${not empty businessList }">
		<c:forEach items="${businessList }" var="bean">
		<li <c:if test="${businessNum ==  bean.businessNum}">class="active"</c:if>><a href="${ctx}/marketing/coupon/couponListByPlatform?platformNum=${platformNum }&businessNum=${bean.businessNum }">${bean.businessName }</a></li>
		</c:forEach>
		</c:if>
	</ul>
	<br/>
	<form:form id="searchForm" modelAttribute="order" action="${ctx}/marketing/coupon/couponListByPlatform" method="post" class="breadcrumb form-search">
	<input type="hidden" name="businessNum" value="${businessNum }"/>
	<input type="hidden" name="platformNum" value="${platformNum }"/>
	<select name="couponTypeNum">
		<option value="">全部</option>
		<c:if test="${not empty couponTypeList }">
		<c:forEach items="${couponTypeList}" var="bean">
		<option value="${bean.typeNum }">${bean.couponName }</option>
		</c:forEach>
		</c:if>
	</select>
	<input type="text" name="name" placeholder="输入关键字查询"/>
	<input id="searchBtn" class="btn btn-primary" type="submit" value="搜索"/>
	</form:form>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>卡券编号</th>
				<th>卡券类别</th>
				<th>卡券属性</th>
				<th>历史活动</th>
				<th>当前活动</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty page.list }">
			<tr>
				<td style="text-align: center;" colspan="6">暂无数据</td>
			</tr>
			</c:if>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${(bi.index+1)+((pageUtil.pageId-1) * pageUtil.pageSize)}</td>
					<td>${model.couponNum }</td>
					<td>${model.couponName }</td>
					<td>${model.couponContent }</td>
					<td>${model.activityCount }</td>
					<td>${model.currentActivityCount }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
