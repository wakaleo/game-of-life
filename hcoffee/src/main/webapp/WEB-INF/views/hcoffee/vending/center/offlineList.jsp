<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>线下交易查询</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function(){
		
		//查询
		$("#queryBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/trade/offLineList");
			$("#searchForm").submit();
		});
		
		//导出
		$("#exceportBut").click(function(){
			top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/hcf/trade/exportInfo");
					$("#searchForm").submit();
				    
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
		
	});
	
	$(document).keypress(function(e) {  
		if(e.which == 13) {  
			$("#queryBut").click();
		}  
	 }); 
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/trade/offLineList");
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
	<form:form id="searchForm" modelAttribute="tradeInfoVo"
		action="${ctx}/hcf/trade/offLineList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<tr>
				<td style="text-align: left">
					<label class="control-label">机器编号：</label>
					<form:input path="vendCode" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="机器编号"/>
				</td>
				<td style="text-align: left">
					<label class="control-label">订单号：</label>
					<form:input path="orderNo" htmlEscape="false"  style="width:250px;" class="input-medium" placeholder="订单号"/>
				</td>
				<td style="text-align: left">
					<label class="control-label">用户名：</label>
					<form:input path="userName" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="用户名"/>
				</td>
				<td style="text-align: left">
					<label class="control-label">手机号：</label>
					<form:input path="phoneNo" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="手机号"/>
				</td>
				<td width="30%">
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
				<th>订单号</th>
				<th>手机号码</th>
				<th>用户名</th>
				<th>用户地址 </th>
				<th>商品名</th>
				<th>商品价格</th>
				<th>机器编码</th>
				<th>商品所在货道</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.orderNo}</td>
					<td>${model.phoneNo}</td>
					<td>${model.userName}</td>
					<td>${model.location}</td>
					<td>${model.goodsName}</td>
					<td>${model.amount}</td>
					<td>${model.vendCode}</td>
					<td>${model.shelf}</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td>总合计：</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>销售额：${sumPrice }</td>
				<td></td>
				<td></td>
			</tr>
		</tfoot>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
