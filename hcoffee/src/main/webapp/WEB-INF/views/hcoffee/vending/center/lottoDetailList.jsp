<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>抽奖活动统计查询</title>

<meta name="decorator" content="default" />
<script type="text/javascript">
		$(function() {
			//查询
			$("#searchBut").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/lottoDetail/list");
				$("#searchForm").submit();
			});
			
			//导出Excel
			$("#exceportBut").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？", "系统提示", function(v, h, f) {
					if (v == "ok") {
						$("#searchForm").attr("action","${ctx}/hcf/lottoDetail/export");
						$("#searchForm").submit();
					}
				}, {
					buttonsFocus : 1
				});
				top.$('.jbox-body .jbox-icon').css('top', '55px');
			});
			
			//导出当前页
			$("#exceportCurrentBut").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？", "系统提示", function(v, h, f) {
					if (v == "ok") {
						$("#searchForm").attr("action","${ctx}/hcf/lottoDetail/exportCurrent");
						$("#searchForm").submit();
					}
				}, {
					buttonsFocus : 1
				});
				top.$('.jbox-body .jbox-icon').css('top', '55px');
			});
			
		});
		
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/lottoDetail/list");
					$("#searchForm").submit();
			     }
			}
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/lottoDetail/list");
			$("#searchForm").submit();
        	return false;
        }

</script>
<style>
</style>
</head>
</head>
<body>
	<form:form id="searchForm" modelAttribute="lottoDetailVo" action="${ctx}/hcf/lottoDetail/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<%-- <td width="15%">
					<label class="control-label">活动编码：</label>
					<form:input path="activityNo" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="活动编码"/>
				</td> --%>
				<td width="15%">
					<label class="control-label">奖品名称：</label>
					<form:input path="prizeName" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="奖品名称"/>
				</td>
				<td width="15%">
					<label class="control-label">机器编码：</label>
					<form:input path="vendCode" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="机器编码"/>
				</td>
				<td  width="30%">
					<label class="control-label">订单创建时间：</label> 
					<input name="startTime" type="text" maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${lottoDetailVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"
						readonly="readonly" /> -- 
					<input name="endTime" type="text"
						maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${lottoDetailVo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"
						readonly="readonly" />
				</td>
			</tr>
			<tr>	
				<td width="30%" colspan="2">
					<label class="control-label">活动名称： </label>
					<form:select path="activityNo" class="input-xlarge required" style="width:120px;"  id="activityNo">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${activityList}" var="model" varStatus="indexStatus">
									<form:option value="${model.activityNo }" label="${model.activityName }"/>
							</c:forEach>
					</form:select>
					
					<label class="control-label">奖品类型： </label>
					<form:select path="prizeType" class="input-xlarge required" style="width:120px;"  id="prizeType">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${prizeTypeList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
					
					<label class="control-label">抽奖来源： </label>
					<form:select path="lottoSource" class="input-xlarge required" style="width:120px;"  id="lottoSource">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${lottoSourceList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
					
					
					
				</td>
				<td width="8%" style="text-align: left">
					
					<input id="searchBut" class="btn btn-primary" type="button" value="查询" />
					<input id="exceportCurrentBut" class="btn btn-primary" type="button" value="导出当前页" />	
					<input id="exceportBut" class="btn btn-primary" type="button" value="导出所有" />	
				</td>
			</tr>
		</table>
	</form:form>

	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>活动名称</th>
				<th>活动编号</th>
				<th>中奖时间</th>
				<th>机器编码</th>
				<th>奖品名称</th>
				<th>奖品类型</th>
				<th>抽奖来源</th>
				<th>用户</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.activityName}</td>
					<td>${model.activityNo}</td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${model.vendCode}</td>
					<td>${model.prizeName}</td>
					<td>
						<c:choose>
							<c:when test="${model.prizeType == 1}">
								货道商品
							</c:when>
							<c:when test="${model.prizeType == 2}">
								商城商品
							</c:when>
							<c:otherwise>
								--
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${model.lottoSource == 1}">
								商城注册
							</c:when>
							<c:when test="${model.lottoSource == 2}">
								商城充值
							</c:when>
							<c:when test="${model.lottoSource == 10}">
								缴纳物业
							</c:when>
							<c:when test="${model.lottoSource == 11}">
								预存物业
							</c:when>
							<c:otherwise>
								--
							</c:otherwise>
						</c:choose>
					</td>
					<td>${model.userNo}</td>
					<td>
						<c:choose>
							<c:when test="${model.status == 1}">
								领取成功
							</c:when>
							<c:when test="${model.status == 2}">
								奖品推送免息商城失败
							</c:when>
							<c:when test="${model.status == 3}">
								货道不存在
							</c:when>
							<c:when test="${model.status == 4}">
								空货道
							</c:when>
							<c:when test="${model.status == 5}">
								出货失败
							</c:when>
							<c:otherwise>
								--
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>


	<%-- <!--  新建商品模式窗口（Modal）-->
	<div class="modal fade" id="add_goods" tabindex="-1" role="dialog"
		aria-labelledby="orderLabel" aria-hidden="true"
		style="width: 700px; margin-left: -400px; z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="goodsLabel">新建商品</h4>
				</div>
				<div class="modal-body">
					<%@ include file="createGoods.jsp"%>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="show_goods" tabindex="-1" role="dialog"
		aria-labelledby="orderLabel" aria-hidden="true"
		style="width: 800px; margin-left: -400px; z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="orderLabel">查看</h4>
				</div>
				<div class="modal-body" id="goodsinfoTable"></div>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div> --%>
</body>
</html>