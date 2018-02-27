<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>售货机升级记录</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
 			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/vendingVersion/upgradeRecord");
				$("#searchForm").submit();
			});
			
			$("#btnClean").click(function(){
				$("#tab").find("input[type=text]").val("");
				$("#channel").get(0).selectedIndex = 0;
	 
				$("#pageNo").val(1);
			});
			
		
		});
		
		function page(n,s){
		//	alert($("#vendCode").val());
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/vendingVersion/upgradeRecord");
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
	<form:form id="searchForm" modelAttribute="appUpgradeRecordVo" action="${ctx}/hcf/vendingVersion/upgradeRecord" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo"  type="hidden" value="${upgradeRecordPage.pageNo}"/>
		<input id="pageSize" name="pageSize"  type="hidden" value="${upgradeRecordPage.pageSize}"/>
		<input id="vendCode" name="vendCode"  type="hidden" value="${vendCode}"/>
		<table>
			<tr>
				<td  width="5%" style="text-align:left" >
					<input type="button" class="btn btn-primary" name="returnSubmit" value="返回售货机管理" onclick=" window.history.back();"/>
				</td>
				
			</tr>
		</table>
	</form:form>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%">序号</th>
				<th width="8%">渠道</th>
				<th width="5%">售货机编码</th>
				<th width="5%">售货机位置</th>
				<th width="5%">当前版本</th>
				<th width="5%">本次升级版本</th>
				<th width="6%">创建时间</th>
				<th width="6%">升级时间</th>
				<th width="8%">升级内容</th>
				<th width="5%">升级状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${upgradeRecordPage.list}" var="model" varStatus="index">
				<tr>
					<td >${index.index + 1}</td>
					<td>${model.channelName}</td>
					<td>${model.vendCode}</td>
					<td>${model.location}</td>
					<td>${model.versionCurrent}</td>
					<td>${model.version}</td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${model.confirmTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${model.remark}</td>
					<td>
						<c:choose>
							<c:when test="${model.status == 0}">
								待升级
							</c:when>
							<c:when test="${model.status == 1}">
								升级中
							</c:when>
							<c:when test="${model.status == 2}">
								已升级
							</c:when>
							<c:when test="${model.status == 3}">
								升级失败
							</c:when>
							<c:when test="${model.status == 4}">
								作废
							</c:when>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${upgradeRecordPage}</div>
</body>
</html>
