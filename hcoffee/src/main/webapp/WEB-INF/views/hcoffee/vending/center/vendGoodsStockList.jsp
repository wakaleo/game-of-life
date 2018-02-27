<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>机器商品库存显示列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function(){
		//导出
		$("#exceportBut").click(function(){
			top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/hcf/vendStock/exportByVg");
					$("#searchForm").submit();
				    
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
		//查询
		$("#queryBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/vendStock/vendGoodsList");
			$("#searchForm").submit();
		});
	});
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/vendStock/vendGoodsList");
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
	<form:form id="searchForm" modelAttribute="vendGoodsStockVo"
		action="${ctx}/hcf/vendStock/vendGoodsList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table class="table table-striped table-bordered table-condensed"
			style="width: 100%">
			<tr>
				<td>
					<label class="control-label">代理商：</label>
					<form:input path="searchContent" htmlEscape="false"  style="width:300px;" class="input-medium" placeholder="支持登录名，姓名，手机号搜索"/>
				</td>
				<td>	
					<label class="control-label">售货机编码：</label>
					<form:input path="vendCode" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="售货机编码"/>
					<input type="button" id="queryBut" class="btn btn-primary"  width="200px" value="查询">&nbsp;&nbsp;
					<input type="button" id="exceportBut" class="btn btn-primary"  width="200px" value="导出excel">	
				</td>
			</tr>
		</table>
	</form:form>
	<sys:message content="${message}" />
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>商品编号 </th>
				<th>商品名称</th>
				<th>库存数</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.goodsId}</td>
					<td>${model.goodsName}</td>
					<td>${model.stockAmount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
