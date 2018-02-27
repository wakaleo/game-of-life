<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>下发统计列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		
		$("#searchForm").attr("action","${ctx}/distributecount/distributeCount/alist");
		$("#searchForm").submit();
    	return false;
    }
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">下发统计列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="distributeCount" action="${ctx}/distributecount/distributeCount/alist" method="post" class="breadcrumb form-search ">
		<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" /> --%>
		<ul class="ul-form">
			<li><label>活动平台：</label> <select name="activityPlatform"
				id="activityPlatform" style="width: 100px">
					<option selected value="">全部平台</option>
					<option value="1">汇有房</option>
					<option value="2">汇理财</option>
					<option value="3">汇生活</option>
					<option value="4">汇联易家</option>
			</select></li>

			<li><label>卡券类型：</label> <select name="couponType"
				id="couponType" style="width: 120px">
					<option selected value="">全部类型</option>
					<c:forEach var="obj" items="${couponTypeList}" varStatus="i">
						<option value="${obj.type_num}">${obj.coupon_name}</option>
					</c:forEach>
			</select></li>
			<li><label>下发方式：</label> <select name="distributeWayId"
				id="distributeWayId" style="width: 100px">
					<option selected value="">全部方式</option>
					<c:forEach var="obj" items="${distList}" varStatus="i">
						<option value="${obj.distributeWayNum}">${obj.distributeWayDesc}</option>
					</c:forEach>
			</select></li>
			<li>&nbsp;&nbsp; 
				<input name="searchText" id="searchText" type="text"
					value="输入活动名称关键字" onfocus="if(value =='输入活动名称关键字'){value =''}"
					onblur="if (value ==''){value='输入活动名称关键字'}" style="width: 130px" />
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="5%">序列号</th>
				<th width="10%">活动编号</th>
				<th width="15%">活动名称</th>
				<th width="10%">项目状态</th>
				<th width="10%">下发方式</th>
				<th width="10%">卡券名称</th>
				<th width="10%">卡券发放数量</th>
				<th width="10%">卡券领取数量</th>
				<th width="10%">卡券核销数量</th>
				<th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="activitiDto" varStatus="status">
				<tr>
					<td><input type="hidden" value="${activitiDto.activityNum}" />
					<td>${(status.index+1)+((page.pageNo-1) * page.pageSize)}</td>
					<td><c:out value="${activitiDto.activityName}"></c:out></td>
					<td><c:choose>
							<c:when test="${activitiDto.acvivityStatus == '0'}">活动进行中</c:when>
							<c:otherwise>已下架</c:otherwise>
						</c:choose></td>
					<td><c:out value="${activitiDto.distributeWayId}"></c:out></td>
					<td><c:out value="${activitiDto.activityName}"></c:out></td>
					<td><c:out value="${activitiDto.couponProvideCount}"></c:out></td>
					<td><c:out value="${activitiDto.couponReceivedCount}"></c:out></td>
					<td><c:out value="${activitiDto.couponUsedCount}"></c:out></td>
					<td><a
						href="${ctx}/distributecount/distributeCount/detail?activity_num=${activitiDto.activityNum}&activity_name=${activitiDto.activityName}&activity_status=${activitiDto.acvivityStatus}">查看详情</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>