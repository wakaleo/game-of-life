<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>卡券类型列表</title>
	<meta name="decorator" content="default"/>
	<style>
	.couponList{
		list-style: none;
	}
	.couponList li{
		float: left;padding: 0 5px 0 5px;
	}
	</style>
	<script type="text/javascript">
		$(function(){
			$("#addBtn").click(function(){
				window.location.href = "${ctx}/marketing/coupon/couponInfo";			
			});
		});
		function page(n,s){
        	location = '${ctx}/marketing/coupon/couponList?pageNo='+n+'&pageSize='+s+'&businessNum=${businessNum}&couponTypeNum=${couponTypeNum}';
        }
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	<ul class="nav nav-tabs">
		<c:if test="${not empty businessList }">
		<c:forEach items="${businessList }" var="bean">
		<li <c:if test="${businessNum ==  bean.businessNum}">class="active"</c:if>><a href="${ctx}/marketing/coupon/couponList?businessNum=${bean.businessNum }">${bean.businessName }</a></li>
		</c:forEach>
		</c:if>
	</ul>
	<br/>
	<ul class="couponList">
		<c:forEach items="${couponTypeList }" var="bean">
		<li><a href="${ctx}/marketing/coupon/couponList?businessNum=${businessNum}&couponTypeNum=${bean.typeNum}">${bean.couponName }</a></li>
		</c:forEach>
	</ul>
	<br/>
	<div style="clear:both;padding: 0 0 5px 0;">
	<input id="addBtn" class="btn btn-primary" type="button" value="新增"/>
	</div>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>卡券编号</th>
				<th>卡券类型</th>
				<th>卡券属性</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty page.list }">
			<tr>
				<td style="text-align: center;" colspan="5">暂无数据</td>
			</tr>
			</c:if>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${(bi.index+1)+((page.pageNo-1) * page.pageSize)}</td>
					<td>${model.couponNum }</td>
					<td>${model.couponName }</td>
					<td>${model.couponContent }</td>
					<td>${couponStatusMap[model.couponStatus] }</td>
					<td>
					<a href="${ctx}/marketing/coupon/couponInfo?couponNum=${model.couponNum}">查看详情</a>
					<c:if test="${model.couponStatus == 1 }">
					<a href="${ctx}/marketing/coupon/updateCouponStatus?couponNum=${model.couponNum}&status=2">禁用</a>
					</c:if>
					<c:if test="${model.couponStatus == 2 }">
					<a href="${ctx}/marketing/coupon/updateCouponStatus?couponNum=${model.couponNum}&status=1">启用</a>
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
