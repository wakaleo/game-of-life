<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>库存明细</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function(){
		//查询
		$("#queryBut").click(function(){
			var content = $.trim($(":input[name='searchText']").val());
			if(content == ""){
				alert("请输入经销商登录名");
				return;
			}
			$("#loginName").val(content);
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/stockManagement/stockDetailList");
			$("#searchForm").submit();
		});
	});
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/stockManagement/stockDetailList");
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
</style>
</head>
<body>
	<form:form id="searchForm" modelAttribute="dealerStockVo"
		action="${ctx}/hcf/stockManagement/stockDetailList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="loginName" name="loginName" type="hidden" value="${loginName}"/>
		<label>
			 当前位置＞＞ 运营中心＞＞<a href="javascript:void(0);" onclick=" window.history.back();">经销商库存</a>＞＞库存明细
		</label><br/><br/>
		<table id="stockDetailList" class="table table-striped table-bordered table-condensed"
			style="width: 100%">
			<tr>
				<td width="30%">
					<label class="control-label">经销商：</label>
					<form:input path="searchText" htmlEscape="false"  style="width:300px;" class="input-medium" placeholder="请输入经销商登录名"/>
					&nbsp;&nbsp;
					<input type="button" id="queryBut" class="btn btn-primary"  width="200px" value="查询">
				</td>
			</tr>
		</table>
	</form:form>
	<sys:message content="${message}" />
	<label>
		<h5>库存列表</h5>
	</label>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>产品代码</th>
				<th>产品名称</th>
				<th>单位</th>
				<th>库存数 </th>
				<th>已销售量</th>
				<th>采购在订量</th>
				<th>建议采购量</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.goodsId}</td>
					<td>${model.goodsName}</td>
					<td>${model.specification}</td>
					<td>${model.count}</td>
					<td>${model.saledAmount}</td>
					<td>${model.procurementAmount}</td>
					<td>${model.suggestProcurementAmount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
