<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>机器库存显示列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function(){
		//导出
		$("#exceportBut").click(function(){
			top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/hcf/vendStock/export");
					$("#searchForm").submit();
				    
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
		//查询
		$("#queryBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/vendStock/list");
			$("#searchForm").submit();
		});
	});
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/vendStock/list");
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
body{
	font-size: 15px;
}
</style>
</head>
<body>
	<form:form id="searchForm" modelAttribute="stockInfoVo"
		action="${ctx}/hcf/vendStock/list" method="post"
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
					<label class="control-label">货道编号：</label>
					<form:input path="ailseId" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="货道编号"/>
				</td>
			</tr>
			<tr>
				<td width="5%" style="text-align: left">
					<label class="control-label">设置类型：</label>
					<form:select path="setType" class="input-xlarge required" style="width:120px;">
						 	<form:option value="" label="请选择"/>
						 	<form:option value="1" label="货道存量设置"/>
						 	<form:option value="2" label="货道容量设置"/>
					</form:select>
				</td>
				<td width="8%" style="text-align: left">
				<label class="control-label">设置时间：</label> 
					<input name="startTime" type="text" maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${stockInfoVo.startTime}" pattern="yyyy-MM-dd"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
						readonly="readonly" /> -- 
					<input name="endTime" type="text"
						maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${stockInfoVo.endTime}" pattern="yyyy-MM-dd"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
						readonly="readonly" />
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
				<th>售货机编码</th>
				<th>设备名称</th>
				<th>货道编号</th>
				<th>商品编号 </th>
				<th>商品名称</th>
				<th>商品价格</th>
				<th>库存数</th>
				<th>库存变化</th>
				<th>货道容量</th>
				<th>设置类型</th>
				<th>设置时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.vendCode}</td>
					<td>${model.vendName}</td>
					<td>${model.ailseId}</td>
					<td>${model.goodsId}</td>
					<td>${model.goodsName}</td>
					<td>${model.goodsPrice}</td>
					<td>${model.stockAmount}</td>
					<td>
						<c:choose>
							<c:when test="${model.changeAmount > 0}">
								<font color="green">+${model.changeAmount}</font>
							</c:when>
							<c:when test="${model.changeAmount < 0}">
								<font color="red">${model.changeAmount}</font>
							</c:when>
							<c:otherwise>
								${model.changeAmount}
							</c:otherwise>
						</c:choose>
					</td>
					<td>${model.ailseCapacity}</td>
					<td>
						<c:choose>
							<c:when test="${model.setType == '1'}">
								货道存量设置
							</c:when>
							<c:when test="${model.setType == '2'}">
								货道容量设置
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>${model.setTimeStr}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
