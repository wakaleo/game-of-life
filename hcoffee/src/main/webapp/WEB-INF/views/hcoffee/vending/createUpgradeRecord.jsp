  <%@ page contentType="text/html;charset=UTF-8" %>
<script  type="text/javascript">


	
</script>
<form:form id="upgradeRecordForm" modelAttribute="appUpgradeRecordVo" action="${ctx}/hcf/vendingMachine/upgradeRecord" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo"  value="${upgradeRecordPage.pageNo}"/>
		<input id="pageSize" name="pageSize"  value="${upgradeRecordPage.pageSize}"/>
		
	</form:form>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="8%">渠道</th>
				<th width="8%">售货机编码</th>
				<th width="5%">售货机位置</th>
				<th width="5%">升级版本</th>
				<th width="6%">升级时间</th>
				<th width="8%">升级内容</th>
			</tr>
		</thead>
		<tbody id="tbodyModel">
			<!--<c:forEach items="${upgradeRecordPage.list}" var="model" >
				<tr>
					<td>${model.channelName}</td>
					<td>${model.vendCode}</td>
					<td>${model.vendCode}</td>
					<td>${model.version}</td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${model.remark}</td>
				</tr>
			</c:forEach>-->
		</tbody>
	</table>
	<div class="pagination" id="pagination"></div>
 