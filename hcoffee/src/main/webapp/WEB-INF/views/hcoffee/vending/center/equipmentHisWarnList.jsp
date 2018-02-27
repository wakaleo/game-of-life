<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>设备故障历史信息查询</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/equipment/equipmentHisWarnList");
		$("#searchForm").submit();
    	return false;
    }
</script>
<style>
.hide {
	display: block;
}
.show {
	display: none;
}
.grayBar {
	background-color: #efefee
}
#btnMenu{display:none;}
</style>
</head>
<body>
	<form:form id="searchForm" modelAttribute="equipmentWarnVo" action="${ctx}/hcf/equipment/equipmentHisWarnList" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="" />
	<label>当前机器编码：${vendCode }</label>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>报警描述 </th>
				<th>报警时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.warnDes}</td>
					<td>${model.warnTimeStr}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
