  <%@ page contentType="text/html;charset=UTF-8" %>
	<script  type="text/javascript">
			
	</script>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%">序号</th>
				<th width="8%">机器所在社区</th>
				<th width="8%">机器编码</th>
				<th width="5%">礼品编码</th>
				<th width="6%">礼品名称</th>
				<th width="6%">出货时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="activityDetailsList" var="details" varStatus="index">
				<tr>
					<td>${index.count}</td>
					<td>${details.communityName}</td>
					<td>${details.vendcode}</td>
					<td>${details.goodsId}</td>
					<td>${details.goodsName}</td>
					<td><fmt:formatDate value="${details.acquireDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
					<c:choose>
							<c:when test="${model.status == 0}">
								待领取
							</c:when>
							<c:when test="${goodsId.status == 1}">
								领取成功
							</c:when>
							<c:when test="${goodsId.status == 2}">
								领取失败
							</c:when>
							<c:when test="${goodsId.status == 3}">
								取货码失效
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
 