<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>设备交易查询</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function(){
		$("#queryBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/equipment/equipmentTradeList");
			$("#searchForm").submit();
		});
		
		$("#exceportBut").click(function(){
			top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/hcf/equipment/exportTrade");
					$("#searchForm").submit();
				    
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
	});
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/equipment/equipmentTradeList");
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
	<form:form id="searchForm" modelAttribute="equipmentTradeVo"
		action="${ctx}/hcf/equipment/equipmentTradeList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table id="orderShowList" class="table table-striped table-bordered table-condensed"
			style="width: 100%">
			<tr>
				<td style="text-align: left">
					<label class="control-label">机器编号：</label>
					<form:input path="vendCode" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="机器编号"/>
					<label class="control-label">订单号：</label>
					<form:input path="orderNo" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="订单号"/>
				</td>
				<td>
					<label class="control-label">支付类型： </label>
					<form:select path="payType" class="input-xlarge required" style="width:120px;">
						<form:option value="" label="全部"/>
						<form:option value="1" label="取货码"/>
						<form:option value="2" label="微信"/>
						<form:option value="3" label="支付宝"/>
						<form:option value="4" label="其他"/>
					</form:select>
					<label class="control-label">交易时间：</label> 
					<input name="startTime"
						type="text" maxlength="20" class="input-medium Wdate " value="${startTime }"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
						readonly="readonly" /> -- <input name="endTime" type="text"
						maxlength="20" class="input-medium Wdate " value="${endTime }"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
						readonly="readonly" />
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
				<th>机器编号</th>
				<th>订单号</th>
				<th>产品编号</th>
				<th>支付类型</th>
				<th>价格（元）</th>
				<th>交易时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.vendCode}</td>
					<td>${model.orderNo}</td>
					<td>${model.productCode}</td>
					<td>
					<c:choose>
							<c:when test="${model.payType == '1'}">
								取货码
							</c:when>
							<c:when test="${model.payType == '2'}">
								微信支付
							</c:when>
							<c:when test="${model.payType == '3'}">
								支付宝支付
							</c:when>
							<c:when test="${model.payType == '4'}">
								其他
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>${model.price}</td>
					<td>${model.tradeTimeStr}</td>
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
				<td>销售额：${sumPrice }</td>
				<td></td>
			</tr>
		</tfoot>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
