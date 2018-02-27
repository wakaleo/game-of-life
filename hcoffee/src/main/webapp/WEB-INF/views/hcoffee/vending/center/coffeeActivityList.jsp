<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>咖啡活动查询</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function(){
		//查询
		$("#queryBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/equipment/coffeeActivityList");
			$("#searchForm").submit();
		});
		//导出
		$("#exceportBut").click(function(){
			top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/hcf/equipment/exportCoffeeActivityInfo");
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
		$("#searchForm").attr("action","${ctx}/hcf/equipment/coffeeActivityList");
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
	<form:form id="searchForm" modelAttribute="coffeeActivityInfoVo"
		action="${ctx}/hcf/equipment/coffeeActivityList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table id="orderShowList" class="table table-striped table-bordered table-condensed"
			style="width: 100%">
			<tr>
				<td style="text-align: left">
					<label class="control-label">活动编号：</label>
					<form:input path="schemeNo" htmlEscape="false" class="input-medium" placeholder="活动编号"/>
				</td>
				<td style="text-align: left">
					<label class="control-label">取货编号：</label>
					<form:input path="drawCode" htmlEscape="false" class="input-medium" placeholder="取货编号"/>
				</td>
				<td style="text-align: left">
					<label class="control-label">订单号：</label>
					<form:input path="orderNo" htmlEscape="false" class="input-medium" placeholder="订单号"/>
				</td>
				<td style="text-align: left">
					<label class="control-label">手机号：</label>
					<form:input path="phoneNo" htmlEscape="false" class="input-medium" placeholder="手机号"/>
				</td>
				<td>
					<label class="control-label">礼品状态： </label>
					<form:select path="status" class="input-xlarge required" style="width:120px;">
						<form:option value="" label="全部"/>
						<form:option value="0" label="待领取"/>
						<form:option value="1" label="领取成功"/>
						<form:option value="2" label="领取失败"/>
						<form:option value="3" label="失效"/>
					</form:select>
				</td>
			</tr>
			<tr>		
				<td>
					<label class="control-label">创建时间：</label> 
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
				<th>活动编号</th>
				<th>取货编码</th>
				<th>礼品状态</th>
				<th>领取时间 </th>
				<th>创建时间</th>
				<th>失效时间</th>
				<th>手机号码</th>
				<th>商品ID</th>
				<th>商品名称</th>
				<th>商品价格(元)</th>
				<th>提取机器ID</th>
				<th>订单号</th>
				<th>口味代码</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.schemeNo}</td>
					<td>${model.drawCode}</td>
					<td>${model.statusStr}</td>
					<td>${model.acquireDateStr}</td>
					<td>${model.createDateStr}</td>
					<td>${model.invaliDateStr}</td>
					<td>${model.phoneNo}</td>
					<td>${model.goodsId}</td>
					<td>${model.goodsName}</td>
					<td>${model.price}</td>
					<td>${model.getMachineCode}</td>
					<td>${model.orderNo}</td>
					<td>${model.fastCode}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
