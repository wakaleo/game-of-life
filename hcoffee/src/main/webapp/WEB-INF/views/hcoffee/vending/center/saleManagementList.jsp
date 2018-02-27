<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>销售管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript" charset="UTF-8">
	$(function() {
		//查询
		$("#queryBut").click(function() {
			$("#pageNo").val(1);
			$("#searchForm").attr("action", "${ctx}/hcf/saleManagement/list");
			$("#searchForm").submit();
		});

		//导出Excel
		$("#exceportBut").click(
				function() {
					top.$.jBox.confirm("确认要导出数据吗？", "系统提示", function(v, h, f) {
						if (v == "ok") {
							$("#searchForm").attr("action","${ctx}/hcf/saleManagement/export");
							$("#searchForm").submit();
						}
					}, {
						buttonsFocus : 1
					});
					top.$('.jbox-body .jbox-icon').css('top', '55px');
				});
	});

	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action", "${ctx}/hcf/saleManagement/list");
		$("#searchForm").submit();
		return false;
	}
	
	function saleDetail(loginName,dealerName){
		debugger;
		var startTime = $("input[name='startTime']").val();
		var endTime = $("input[name='endTime']").val();
		if(startTime != '' && endTime != ''){
			window.location.href = "${ctx}/hcf/saleManagement/saleRecordList?loginName="+loginName+"&dealerName="+encodeURI (encodeURI (dealerName))+"&startTimeStr="+startTime+"&endTimeStr="+endTime+"";
		}else{
			window.location.href = "${ctx}/hcf/saleManagement/saleRecordList?loginName="+loginName+"&dealerName="+encodeURI (encodeURI (dealerName))+"";
		}
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
	<form:form id="searchForm" modelAttribute="saleManagementVo"
		action="${ctx}/hcf/saleManagement/list" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<table id="tab"
			class="table table-striped table-bordered table-condensed"
			style="width: 100%">
			<tr>
				<td width="5%" style="text-align: left">
					<form:input path="searchContent" htmlEscape="false" style="width:250px;" class="input-medium" placeholder="支持代理商登录名，姓名，手机号查询"/>
					<label class="control-label">交易时间：</label> 
					<input name="startTime"
						type="text" maxlength="20" class="input-medium Wdate " value="${startTime }"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
						readonly="readonly" /> -- <input name="endTime" type="text"
						maxlength="20" class="input-medium Wdate " value="${endTime }"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
						readonly="readonly" /> <input id="queryBut"
						class="btn btn-primary" type="button" value="查询" /> <input
						id="exceportBut" class="btn btn-primary" type="button"
						value="导出excel" />
				</td>
			</tr>
		</table>
	</form:form>
	<sys:message content="" />
	<table id="list_table" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%">序号</th>
				<th width="5%">登录名</th>
				<th width="4%">ID</th>
				<th width="6%">姓名</th>
				<th width="4%">电话</th>
				<th width="8%">销售金额(元)</th>
				<th width="8%">销售数量(件)</th>
				<th width="8%">机器数</th>
				<th width="8%">查看更多</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.loginName}</td>
					<td>${model.ID}</td>
					<td>${model.dealerName}</td>
					<td>${model.cellPhone}</td>
					<td>${model.saleMoney}</td>
					<td>${model.saleCount}</td>
					<td>${model.machineNum}</td>
					<td>
						<%-- <a href="${ctx}/hcf/saleManagement/saleRecordList?loginName=${model.loginName}&dealerName=${model.dealerName}">销售明细</a> --%>
						<a href="javascript:saleDetail('+${model.loginName}+','+${model.dealerName}+')">销售明细</a>
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
				<td></td>
				<td></td>
			</tr>
		</tfoot>
	</table>
</body>
</html>
