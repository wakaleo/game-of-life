<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
<title>销售明细</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function() {
		//查询
		$("#queryBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/saleManagement/detailList");
			$("#searchForm").submit();
		});
		
		//导出Excel
		$("#exceportBut").click(function(){
			var vendCodes = $("#vendCodes").val();
			top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/hcf/saleManagement/exportOrderDetail?vendCodes="+vendCodes);
					$("#searchForm").submit();
				    
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/saleManagement/detailList");
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
</head>
<body>
	<label> 
		当前位置＞＞ 运营中心＞＞<a href="javascript:void(0);" onclick=" window.history.back();">销售管理</a>＞＞销售明细
	</label>
	<br />
	<br />

	<form:form id="searchForm" modelAttribute="saleDetailVo"
		action="${ctx}/hcf/saleManagement/detailList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<input name="loginName" type="hidden" value="${loginName}" />
		<input id="vendCodes" name="vendCodes" type="hidden" value="${vendCodes}" />

		<table id="tab"
			class="table table-striped table-bordered table-condensed"
			style="width: 100%">
			<tr>
				<td width="2%">
					<label class="control-label">代理商：</label>
					<input id="dealerName" name="dealerName" placeholder="姓名" readonly="readonly" style="width: 250px;" class="input-medium" type="text" value="${dealerName}" /> 
				</td>
				<td width="2%" style="text-align: left">
					<label class="control-label">售货机编码：</label> 
					<select name="vendCode" id="vendCode"
						class="input-xlarge required" style="width: 120px;">
							<option value="" id="" name="">全部</option>
							<c:forEach items="${vendCodeList}" var="model"
								varStatus="indexStatus">
								<option value="${model.vendCode}" name="">${model.vendCode}</option>
							</c:forEach>
					</select>
				</td>
				<td width="1%">
					<label class="control-label">机器部署地：</label> 
					<input id="location" name="location" placeholder="机器部署地" style="width: 250px;" class="input-medium" type="text" value="${location }" />
				</td>
			</tr>
			<tr>
				<td style="text-align: left" colspan="3"><label
					class="control-label">交易时间：</label> <input name="startTime"
					type="text" maxlength="20" class="input-medium Wdate " value="${startTime }"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
					readonly="readonly" /> -- <input name="endTime" type="text"
					maxlength="20" class="input-medium Wdate " value="${endTime }"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
					readonly="readonly" /> <input id="queryBut"
					class="btn btn-primary" type="button" value="查询" /> <input
					id="exceportBut" class="btn btn-primary" type="button"
					value="导出交易明细" /></td>
			</tr>
		</table>
	</form:form>

	<table id="tab"
		class="table table-striped table-bordered table-condensed"
		style="width: 100%">
		<thead>
			<tr>
				<th>序号</th>
				<th>机器编号</th>
				<th>部署地</th>
				<th>销售金额（元）</th>
				<th>销售件数</th>
				<th>交易日期</th>
				<th>查看更多</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.vendCode}</td>
					<td>${model.location}</td>
					<td>${model.saleMoney}</td>
					<td>${model.saleCount}</td>
					<td><fmt:formatDate value="${model.shipTime}" pattern="yyyy-MM-dd"/></td>
					<td>
						<a href="${ctx}/hcf/saleManagement/orderRecordList?vendCode=${model.vendCode}&shipTimeStr=${model.shipTime}">交易明细</a> 
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2">总合计</td>
				<td colspan="2">销售额：${saleMoney }</td>
				<td colspan="2">销售件数：${saleCount }</td>
				<td></td>
			</tr>
		</tfoot>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>