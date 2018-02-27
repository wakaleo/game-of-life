<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>订货中心</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function(){
		$("#queryBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/orderCenter/list");
			$("#searchForm").submit();
		});
		
		$("#exceportBut").click(function(){
			top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
		        	//$("#vendCodeList").val(checkedList.toString());
					$("#searchForm").attr("action","${ctx}/hcf/orderCenter/export");
					$("#searchForm").submit();
				    
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
	});
	
	//手动同步
	function manualSync(orderNo){
		var url ='${ctx}/hcf/orderCenter/manualSync';
		$.ajax({
			 type:'post',
	       	 url:url,
	       	 data:JSON.stringify(orderNo),
	       	 dataType:"json",
	       	 contentType:"application/json",
	       	 success:function(data){
	       		$.jBox.tip(data.msg);
	       		$("#searchForm").attr("action","${ctx}/hcf/orderCenter/list");
				$("#searchForm").submit();
	       	 }
		});
	}
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/orderCenter/list");
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
	<form:form id="searchForm" modelAttribute="orderBaseInfoVo"
		action="${ctx}/hcf/orderCenter/list" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table id="orderShowList" class="table table-striped table-bordered table-condensed"
			style="width: 100%">
			<tr>
				<td width="30%">
					<label class="control-label">订单状态： </label>
					<form:select path="orderStatus" class="input-xlarge required" style="width:120px;"  id="orderStatus">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${orderStatusList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
					<label class="control-label">是否同步： </label>
					<form:select path="syncStatus" class="input-xlarge required" style="width:120px;">
						<form:option value="" label="全部"/>
						<form:option value="1" label="同步成功"/>
						<form:option value="2" label="同步失败"/>
						<form:option value="3" label="未同步"/>
					</form:select>
					<!-- <select id="syncStatus" name="syncStatus" class="input-xlarge required" style="width:120px;">
						<option value="">全部</option>	
						<option value="1">已同步</option>	
						<option value="2">未同步</option>	
					</select> -->
				</td>
				<td width="30%">
					<label class="control-label">支付类型：</label>
					<form:select path="payWay" class="input-xlarge required" style="width:120px;"  id="payWay">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${orderPayWayList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
				</td>
				<td width="30%">
					<label class="control-label">订单来源：</label>
					<form:select path="orderSource" class="input-xlarge required" style="width:120px;"  id="orderSource">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${orderSourceList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
				</td>
			</tr>
			<tr>
				<td width="5%" style="text-align: left">
					<label class="control-label">订货单号：</label>
					<form:input path="orderNo" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="订单号"/>
					<label class="control-label">代理商：</label>
					<form:input path="dealerName" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="代理商"/>
				</td>
				<td width="8%" style="text-align: left">
				<label class="control-label">交易时间：</label> 
					<input name="startTime" type="text" maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${orderBaseInfoVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"
						readonly="readonly" /> -- 
					<input name="endTime" type="text"
						maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${orderBaseInfoVo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"
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
				<th width="2%">序号</th>
				<th width="4%">交易流水</th>
				<th width="8">订货单号</th>
				<th width="8%">支付类型</th>
				<th width="8%">订单来源 </th>
				<th width="8%">订单金额（元）</th>
				<th width="4%">订货件数</th>
				<th width="8%">交易时间</th>
				<th width="8%">订单状态</th>
				<th width="8%">代理商</th>
				<th width="8%">收货人</th>
				<th width="4%">电话</th>
				<th width="10%">收货地址</th>
				<th width="5%">是否同步</th>
				<th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.runningAccount}</td>
					<td>${model.orderNo}</td>
					<td>
						<c:choose>
								<c:when test="${model.payWay == 0}">
									未支付
								</c:when>
								<c:when test="${model.payWay == 1}">
									微信支付
								</c:when>
								<c:when test="${model.payWay == 2}">
									支付宝支付 
								</c:when>
								<c:when test="${model.payWay == 3}">
									现金
								</c:when>
								<c:when test="${model.payWay == 4}">
									雅支付
								</c:when>
								<c:when test="${model.payWay == 5}">
									取货码
								</c:when>
								<c:when test="${model.payWay == 6}">
									银联刷卡
								</c:when>
								<c:when test="${model.payWay == 7}">
									银联扫码
								</c:when>
								<c:when test="${model.payWay == 8}">
									通联支付
								</c:when>
								<c:otherwise>
									-
								</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
								<c:when test="${model.orderSource == 1}">
									微信公众号
								</c:when>
								<c:when test="${model.orderSource == 2}">
									PC
								</c:when>
								<c:otherwise>
									-
								</c:otherwise>
						</c:choose>
					</td>
					<td>${model.money}</td>
					<td>${model.goodsCount}</td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						<c:choose>
								<c:when test="${model.orderStatus == 1}">
									待付款
								</c:when>
								<c:when test="${model.orderStatus == 2}">
									已付款
								</c:when>
								<c:when test="${model.orderStatus == 3}">
									待接单
								</c:when>
								<c:when test="${model.orderStatus == 4}">
									已接单
								</c:when>
								<c:when test="${model.orderStatus == 5}">
									待发货
								</c:when>
								<c:when test="${model.orderStatus == 6}">
									已发货
								</c:when>
								<c:when test="${model.orderStatus == 7}">
									待收货
								</c:when>
								<c:when test="${model.orderStatus == 8}">
									已收货
								</c:when>
								<c:when test="${model.orderStatus == 9}">
									取消订单
								</c:when>
								<c:when test="${model.orderStatus == 10}">
									申请退款
								</c:when>
								<c:when test="${model.orderStatus == 11}">
									退款已受理
								</c:when>
								<c:when test="${model.orderStatus == 12}">
									退款已拒绝
								</c:when>
								<c:when test="${model.orderStatus == 13}">
									退款中
								</c:when>
								<c:when test="${model.orderStatus == 14}">
									已退款
								</c:when>
								<c:when test="${model.orderStatus == 15}">
									已关闭
								</c:when>
								<c:otherwise>
									-
								</c:otherwise>
						</c:choose>
					</td>
					<td>${model.dealerName}</td>
					<td>${model.consignee}</td>
					<td>${model.cellPhone}</td>
					<td>${model.address}</td>
					<td>
						<c:choose>
							<c:when test="${model.orderStatus == 2}">
									<c:choose>
										<c:when test="${model.syncStatus == 1}">
											同步成功
										</c:when>
										<c:when test="${model.syncStatus == 2}">
											同步失败&nbsp;&nbsp;<br/>
											<a href="javascript:void" onclick="manualSync('${model.orderNo}')">手动同步</a>
										</c:when>
										<c:otherwise>
											未同步&nbsp;&nbsp;<br/>
											<a href="javascript:void" onclick="manualSync('${model.orderNo}')">手动同步</a>
										</c:otherwise>
									</c:choose>
							</c:when>
						</c:choose>
					</td>
					<td>
					  <a  href="${ctx}/hcf/orderCenter/orderRecordList?id=${model.id}&orderNo=${model.orderNo}">订单详情</a>&nbsp; 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
