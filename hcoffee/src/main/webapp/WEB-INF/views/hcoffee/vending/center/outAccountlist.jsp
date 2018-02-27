<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>支出记录</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(function(){
		$('#add_divi').on('hidden.bs.modal', function () {
			$("#add_divi").css("z-index",'-1');     
		})
		
		$('#add_divi').on('shown.bs.modal', function () {
			$("#add_divi").css("z-index",'1050');
		})
		$('#show_divi').on('hidden.bs.modal', function () {
			$("#show_divi").css("z-index",'-1');     
		})
		
		$('#show_divi').on('shown.bs.modal', function () {
			$("#show_divi").css("z-index",'1050');
		})
		
		//查询
		$("#searchBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/financialCenter/outAccountlist");
			$("#searchForm").submit();
		});
		
		//导出
		$("#exportOut").click(function(){
			top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/hcf/financialCenter/exportOut");
					$("#searchForm").submit();
				    
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
	});
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/financialCenter/outAccountlist");
		$("#searchForm").submit();
    	return false;
    }
	
	</script>
	<style>
	
	</style>
</head>
<body>
		<form:form id="searchForm" modelAttribute="outAccountVo" action="${ctx}/hcf/financialCenter/outAccountlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="loginName" name="loginName" type="hidden" value="${loginName}"/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td width="5%" style="text-align: left">
					<label class="control-label">代理商：</label>
					<form:input path="dealerName" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="代理商"/>
				</td>
				<td width="8%" style="text-align: left">
				<label class="control-label">账单日期：</label> 
					<input name="startTime" type="text" maxlength="20" class="input-medium Wdate " value="${startTime }"
						onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"
						readonly="readonly" /> -- 
					<input name="endTime" type="text"
						maxlength="20" class="input-medium Wdate " value="${endTime }"
						onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"
						readonly="readonly" />
				</td>
				<td  width="15%" style="text-align:left" >
	   				<input id="searchBut" class="btn btn-primary" type="button" value="查询" />
	   				<input id="exportOut" class="btn btn-primary" type="button" value="导出excel"/>
				</td>
			</tr>
		</table>
	</form:form>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>日期</th>
				<th>代理商</th>
				<th>支出（元） </th>
				<th>项目</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.checkUpTimes}</td>
					<td>${model.dealerName}</td>
					<td><font color='red'>${model.expenditureMoney}</font></td>
					<td>
						<c:choose>
							<c:when test="${model.amountType == '4'}">
								订货
							</c:when>
							<c:when test="${model.amountType == '5'}">
								提现
							</c:when>
						</c:choose>						
					</td>
					<td>
						<c:choose>
							<c:when test="${model.amountStatus == '3'}">
								失败
							</c:when>
							<c:when test="${model.amountStatus == '4'}">
								成功
							</c:when>
							<c:when test="${model.amountStatus == '5'}">
								失败
							</c:when>
							<c:when test="${model.amountStatus == '6'}">
								成功
							</c:when>
						</c:choose>						
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td>本页合计：</td>
				<td></td>
				<td></td>
				<td><font color='red'>-${sumOutMoney }</font></td>
				<td></td>
				<td></td>
			</tr>
		</tfoot>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
