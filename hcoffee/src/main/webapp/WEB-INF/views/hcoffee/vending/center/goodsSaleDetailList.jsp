<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>商品销售明细查询</title>

<meta name="decorator" content="default" />
<script type="text/javascript">
		$(function() {
			//查询
			$("#searchBut").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/goodsSaleDetail/list");
				$("#searchForm").submit();
			});
			
			//导出Excel
			$("#exceportBut").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？", "系统提示", function(v, h, f) {
					if (v == "ok") {
						$("#searchForm").attr("action","${ctx}/hcf/goodsSaleDetail/export");
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
						$("#searchForm").attr("action","${ctx}/hcf/goodsSaleDetail/exportCurrent");
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
					$("#searchForm").attr("action","${ctx}/hcf/goodsSaleDetail/list");
					$("#searchForm").submit();
			     }
			}
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/goodsSaleDetail/list");
			$("#searchForm").submit();
        	return false;
        }

</script>
<style>
</style>
</head>
</head>
<body>
	<form:form id="searchForm" modelAttribute="goodsSaleDetailVo" action="${ctx}/hcf/goodsSaleDetail/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td width="15%">
					<label class="control-label">订单号：</label>
					<form:input path="orderNo" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="订单号"/>
				</td>
				<td width="15%">
					<label class="control-label">商品名称：</label>
					<form:input path="goodsName" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="商品名称"/>
				</td>
				<td width="30%">
					<label class="control-label">机器编码：</label>
					<form:input path="vendCode" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="机器编码"/>
				</td>
			</tr>
			<tr>	
				<td width="30%" colspan="2">
					<label class="control-label">订单状态： </label>
					<form:select path="orderStatus" class="input-xlarge required" style="width:120px;"  id="orderStatus">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${orderStatusList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
					
					<label class="control-label">支付状态： </label>
					<form:select path="payStatus" class="input-xlarge required" style="width:120px;"  id="orderStatus">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${payStatusList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
					
					<label class="control-label">支付方式： </label>
					<form:select path="payType" class="input-xlarge required" style="width:120px;"  id="orderStatus">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${payTypeList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
					
					<label class="control-label">出货状态： </label>
					<form:select path="shipStatus" class="input-xlarge required" style="width:120px;"  id="orderStatus">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${shipStatusList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
					
				</td>
				<td width="8%" style="text-align: left">
				<label class="control-label">订单创建时间：</label> 
					<input name="startTime" type="text" maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${goodsSaleDetailVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"
						readonly="readonly" /> -- 
					<input name="endTime" type="text"
						maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${goodsSaleDetailVo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"
						readonly="readonly" />
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
				<th>订单号</th>
				<th>订单状态</th>
				<th>订单创建时间</th>
				<th>机器编码</th>
				<th>商品编号</th>
				<th>商品名称</th>
				<th>货道</th>
				<th>价格（单位：分）</th>
				<th>支付状态</th>
				<th>支付时间</th>
				<th>支付方式</th>
				<th>出货状态</th>
				<th>出货时间</th>
				<th>记录更新时间</th>
				<th>来源</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.orderNo}</td>
					<td>
						<c:choose>
							<c:when test="${model.orderStatus == 1}">
								交易成功
							</c:when>
							<c:when test="${model.orderStatus == 2}">
								交易失败,待退款
							</c:when>
							<c:when test="${model.orderStatus == 3}">
								交易失败，已退款
							</c:when>
							<c:otherwise>
								--
							</c:otherwise>
						</c:choose>
					</td>
					<td><fmt:formatDate value="${model.orderCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${model.vendCode}</td>
					<td>${model.goodsId}</td>
					<td>${model.goodsName}</td>
					<td>${model.shelf}</td>
					<td>${model.amount}</td>
					<td>
						<c:choose>
							<c:when test="${model.payStatus == 1}">
								未支付
							</c:when>
							<c:when test="${model.payStatus == 2}">
								已支付
							</c:when>
							<c:otherwise>
								--
							</c:otherwise>
						</c:choose>
					</td>
					<td><fmt:formatDate value="${model.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						<c:choose>
							<c:when test="${model.payType == 1}">
								微信支付
							</c:when>
							<c:when test="${model.payType == 2}">
								支付宝支付
							</c:when>
							<c:when test="${model.payType == 3}">
								现金
							</c:when>
							<c:when test="${model.payType == 4}">
								雅支付
							</c:when>
							<c:when test="${model.payType == 5}">
								取货码
							</c:when>
							<c:when test="${model.payType == 6}">
								抽奖
							</c:when>
							<c:when test="${model.payType == 7}">
								泛贩支付
							</c:when>
							<c:when test="${model.payType == 8}">
								刷脸支付
							</c:when>
							<c:otherwise>
								--
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${model.shipStatus == 1}">
								已出货
							</c:when>
							<c:when test="${model.shipStatus == 2}">
								未出货
							</c:when>
							<c:otherwise>
								--
							</c:otherwise>
						</c:choose>
					</td>
					<td><fmt:formatDate value="${model.shipTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${model.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						<c:choose>
							<c:when test="${model.source == 1}">
								便捷神
							</c:when>
							<c:when test="${model.source == 2}">
								凯欣达
							</c:when>
							<c:when test="${model.source == 3}">
								乐科
							</c:when>
							<c:when test="${model.source == 4}">
								零壹比特
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


	<!--  新建商品模式窗口（Modal）-->
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
	</div>
</body>
</html>