<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>下发统计详情</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">下发统计详情</a></li>
	</ul>
	<sys:message content="${message}" />
	<form:form id="searchForm" modelAttribute="distributeCount" action="${ctx}/distributecount/distributeCount/alist" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>活动编码：</label> ${userCouponModel.activity_num}</li>
			<li><label>活动名称：</label> ${userCouponModel.activity_name}</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="5%">序号</th>
				<th width="10%">活动状态</th>
				<th width="10%">下发方式</th>
				<th width="10%">卡券名称</th>
				<th width="10%">用户</th>
				<th width="10%">核销平台</th>
				<th width="10%">订单编号</th>
				<th width="10%">订单金额</th>
				<th width="10%">卡券实际成本</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="activitiDto" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td><c:choose>
							<c:when test="${userCouponModel.activity_status == '0'}">活动进行中</c:when>
							<c:otherwise>已下架</c:otherwise>
						</c:choose></td>
					<td><c:out value="${activitiDto.distribute_way}"></c:out></td>
					<td><c:out value="${activitiDto.coupon_num}"></c:out></td>
					<td><c:out value="${activitiDto.user_id}"></c:out></td>
					<td><c:out value="${activitiDto.platform_num}"></c:out></td>
					<td><c:out value="${activitiDto.order_num}"></c:out></td>
					<td><c:out value="${activitiDto.order_amount}"></c:out></td>
					<td>待明确</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>