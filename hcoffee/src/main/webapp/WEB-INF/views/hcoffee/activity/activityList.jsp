<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动列表</title>
	<meta name="decorator" content="default"/>
	<style>
	.status{
		list-style: none;
	}
	.status li{
		padding:0 5px 0 5px;
		float: left;
	}
	.status li:LAST-CHILD {
		clear: right;
	}
	</style>
	<script type="text/javascript">
		$(function(){
			$("#addBtn").click(function(){
				window.location.href = "${ctx}/marketing/coupon/couponInfo";			
			});

			var startdate = new Pikaday(
			    {
			        field: document.getElementById('startdate'),
			        firstDay: 1,
			        minDate: new Date('2015-01-01'),
			        maxDate: new Date('2018-12-31'),
			        yearRange: [2015,2018],
			        format: 'YYYY-MM-DD'
			    });

			var enddate = new Pikaday(
			    {
			        field: document.getElementById('enddate'),
			        firstDay: 1,
			        minDate: new Date('2015-01-01'),
			        maxDate: new Date('2018-12-31'),
			        yearRange: [2015,2018],
			        format: 'YYYY-MM-DD'
			    });
			$("#btnSubmit").click(function(){
				$("#searchForm").submit();
			});
		});
		function page(n,s){
        	location = '${ctx}/marketing/activity/activityList?pageNo='+n+'&pageSize='+s+'&activityStatus=${activityStatus}&name=${name}';
        }
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	<ul class="status">
		<li><a href="${ctx}/marketing/activity/activityList">全部</a></li>
		<c:if test="${not empty activityStatusMap }">
		<c:forEach items="${activityStatusMap }" var="bean">
		<li><a href="${ctx}/marketing/activity/activityList?activityStatus=${bean.key }">${bean.value }</a></li>
		</c:forEach>
		</c:if>
	</ul>
	<br/>
	<div style="clear:both;padding: 0 0 5px 0;">
	<form:form id="searchForm" action="${ctx}/marketing/activity/activityList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>活动名称：</label>
				<input name="name" type="text" id="name" value=""/>
			</li>
			<!-- <li>
				<label>开始日期：</label>
				<input name="startDate" type="text" readonly="readonly" id="startdate" value=""/>
			</li>
			<li>
				<label>截止日期：</label>
				<input name="endDate" type="text" readonly="readonly" id="enddate" value=""/>
			</li> -->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>
			</li>
			<li class="clearfix"></li>
		</ul>
		
	</form:form>
	</div>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>活动编号</th>
				<th>活动名称</th>
				<th>活动平台</th>
				<th>下发对象</th>
				<th>下发方式</th>
				<th>活动有效期</th>
				<th>活动状态</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty page.list }">
			<tr>
				<td style="text-align: center;" colspan="12">暂无数据</td>
			</tr>
			</c:if>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${(bi.index+1)+((page.pageNo-1) * page.pageSize)}</td>
					<td>${model.activityNum }</td>
					<td>${model.activityName }</td>
					<td>${platformNameMap[model.activityPlatform] }</td>
					<td>
					<c:if test="${not empty model.userGroupList }">
					<c:forEach items="${model.userGroupList }" var="group">
					<p>${group.userGroupName }</p> 
					</c:forEach> 
					</c:if>
					</td>
					<td>${model.distributeWay.distributeWayDesc }</td>
					<td><fmt:formatDate value="${model.activityStartTime }" pattern="yyyy.MM.dd"/> - <fmt:formatDate value="${model.activityEndTime }" pattern="yyyy.MM.dd"/></td>
					<td>${activityStatusMap[model.activityStatus] }</td>
					<td>${model.createUser.name }</td>
					<td><fmt:formatDate value="${model.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						<a href="${ctx}/marketing/activity/activityEdit?activityNum=${model.activityNum }&viewType=1">查看</a>
						<c:if test="${model.activityStatus == 0 || model.activityStatus == 4 }">
						<a href="${ctx}/marketing/activity/activityEdit?activityNum=${model.activityNum }">编辑</a>
						</c:if>
						<c:if test="${model.activityStatus == 1 }">
						<a href="javascript:if(confirm('确定要结束活动吗?'))location='${ctx}/marketing/activity/activityOver?activityNum=${model.activityNum }'">手动下架</a>
						<%-- <a href="${ctx}/marketing/activity/activityEdit?activityNum=${model.activityNum }">延期</a> --%>
						</c:if>
						<a href="${ctx}//marketing/activity/copyActivityView?activityNum=${model.activityNum }">复制</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
