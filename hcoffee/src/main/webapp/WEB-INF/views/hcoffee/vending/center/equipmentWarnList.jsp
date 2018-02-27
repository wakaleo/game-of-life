<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>设备报警查询</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function(){
		$("#queryBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/equipment/equipmentWarnList");
			$("#searchForm").submit();
		});
		
		$("#exceportBut").click(function(){
			top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/hcf/equipment/exportWarn");
					$("#searchForm").submit();
				    
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
	});
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/equipment/equipmentWarnList");
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
	<form:form id="searchForm" modelAttribute="equipmentWarnVo"
		action="${ctx}/hcf/equipment/equipmentWarnList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table id="orderShowList" class="table table-striped table-bordered table-condensed"
			style="width: 100%">
			<tr>
				<td style="text-align: left">
					<label class="control-label">机器编号：</label>
					<form:input path="vendCode" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="机器编号"/>
				</td>
				<td>
					<label class="control-label">故障状态： </label>
					<form:select path="faultState" class="input-xlarge required" style="width:120px;">
						<form:option value="" label="全部"/>
						<form:option value="1" label="正常"/>
						<form:option value="2" label="故障"/>
					</form:select>
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
				<th>位置简述</th>
				<th>详细地址</th>
				<th>故障状态</th>
				<th>报警编号 </th>
				<th>报警描述 </th>
				<th>报警时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.vendCode}</td>
					<td>${model.location}</td>
					<td>${model.address}</td>
					<td>
					<c:choose>
							<c:when test="${model.faultState == '1'}">
								<font color="green">正常</font>
							</c:when>
							<c:when test="${model.faultState == '2'}">
								<font color="red">故障</font>
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>${model.warnNo}</td>
					<td>${model.warnDes}</td>
					<td>${model.warnTimeStr}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
