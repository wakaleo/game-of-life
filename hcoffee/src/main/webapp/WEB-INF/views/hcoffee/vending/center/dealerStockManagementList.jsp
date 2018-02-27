<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代理商库存管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
 			//查询
			$("#searchBut").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/stockManagement/list");
				$("#searchForm").submit();
 			});
			
		});
			
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/stockManagement/list");
			$("#searchForm").submit();
        	return false;
        }
		
	</script>
	<style>
	.hide {
		display:block;
	}
	.show {
		display:none;
	}
	.grayBar{
		background-color:#efefee
	}
	</style>
</head>
<body>
	<form:form id="searchForm" modelAttribute="dealerStockVo" action="${ctx}/hcf/stockManagement/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="id" name="id" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td width="20%" style="text-align:left">
					<label class="control-label">代理类型：</label>
					<form:select path="dealerType" class="input-xlarge required" style="width:120px;"  id="dealerType">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${dealerTypeList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
				</td>
		 		
		 		<td width="20%" style="text-align:left">
					<label class="control-label">代理级别：</label>
					<form:select path="dealerGrade" class="input-xlarge required" style="width:120px;"  id="dealerGrade">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${dealerGradeList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
				</td>
			 
				<td width="20%" style="text-align:left">
					<label class="control-label">合作状态：</label>
					<form:select path="conStatus" class="input-xlarge required" style="width:120px;"  id="conStatus">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${conStatusList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
				</td>
 
 				<td>
 					<form:input path="searchText" htmlEscape="false"  style="width:300px;" class="input-medium" placeholder="支持登录名，姓名，手机号搜索"/>
 				</td>
 
				<td  width="15%" style="text-align:left" >
	   				<input id="searchBut" class="btn btn-primary" type="button" value="查询" />
				</td>
			</tr>
		</table>
	</form:form>
	<sys:message content="${message}"/>
	
	<label>
		<h5>库存列表</h5>
	</label>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="2%">序号</th>
				<th width="4%">登录名</th>
				<th width="4%">姓名</th>
				<th width="4%">代理类型</th>
				<th width="4%">代理级别</th>
				<th width="4%">合作状态</th>
				<th width="6%">电话</th>
				<th width="8%">地址</th>
				<th width="4%">库存数</th>
 				<th width="8%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.loginName}</td>
					<td>${model.dealerName}</td>
					<td>
						<c:choose>
							<c:when test="${model.dealerType == 1}">
								省级代理
							</c:when>
							<c:when test="${model.dealerType == 2}">
								市级代理
							</c:when>
							<c:otherwise>
								--
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${model.dealerGrade == 1}">
							一级代理
							</c:when>
							<c:when test="${model.dealerGrade == 2}">
								二级代理
							</c:when>
							<c:when test="${model.dealerGrade == 3}">
								三级代理
							</c:when>
							<c:otherwise>
								--
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${model.conStatus == 1}">
								合作中
							</c:when>
							<c:when test="${model.conStatus == 2}">
								谈判中
							</c:when>
							<c:when test="${model.conStatus == 3}">
								停止合作
							</c:when>
							<c:otherwise>
								--
							</c:otherwise>
						</c:choose>
					</td>
					<td>${model.cellPhone}</td>
					<td>${model.detailAddress}</td>
					<td>${model.stockAmount}</td>
					<td>
						<a href="${ctx}/hcf/stockManagement/stockDetailList?loginName=${model.loginName}">库存明细</a>&nbsp;
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
