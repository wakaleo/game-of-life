<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
<title>订单明细</title>
<meta name="decorator" content="default" />
<script type="text/javascript">

	//下拉框显示
	function showSelect(){
		var payType = $("#payTypes").val();
		var payTypeText = "";
		if(payType != ''){
			if(payType == 0){
				payTypeText = "未支付";
			}
			if(payType == 1){
				payTypeText = "微信支付";
			}
			if(payType == 2){
				payTypeText = "支付宝支付 ";
			}
			if(payType == 3){
				payTypeText = "现金";
			}
			if(payType == 4){
				payTypeText = "雅支付";
			}
			if(payType == 5){
				payTypeText = "取货码";
			}
			if(payType == 6){
				payTypeText = "抽奖";
			}
			if(payType == 7){
				payTypeText = "泛贩支付";
			}
			if(payType == 8){
				payTypeText = "刷脸支付";
			}
			if(payType == 9){
				payTypeText = "美好支付";
			}
			$("#payType").select2("data",{"id":payType,"text":payTypeText});
		}else{
			$("#payType").select2("data",{"id":'',"text":'全部'});
		}
		
		var payStatus = $("#payStatuss").val();
		var payStatusText = "";
		if(payStatus != ''){
			if(payStatus == 1){
				payStatusText = "未支付";
			}
			if(payStatus == 2){
				payStatusText = "已支付";
			}
			if(payStatus == 3){
				payStatusText = "部分支付";
			}
			if(payStatus == 4){
				payStatusText = "支付完成";
			}
			if(payStatus == 5){
				payStatusText = "已退回";
			}
			if(payStatus == 6){
				payStatusText = "已中请退款";
			}
			if(payStatus == 7){
				payStatusText = "退款成功";
			}
			if(payStatus == 8){
				payStatusText = "申请退款失败";
			}
			$("#payStatus").select2("data",{"id":payStatus,"text":payStatusText});
		}else{
			$("#payStatus").select2("data",{"id":'',"text":'全部'});
		}
		
		var orderStatus = $("#orderStatuss").val();
		var orderStatusText = "";
		if(orderStatus != ''){
			if(orderStatus == 1){
				orderStatusText = "效易成功";
			}
			if(orderStatus == 2){
				orderStatusText = "效易失败,待退款";
			}
			if(orderStatus == 3){
				orderStatusText = "效易失败,已退款";
			}
			$("#orderStatus").select2("data",{"id":orderStatus,"text":orderStatusText});
		}else{
			$("#orderStatus").select2("data",{"id":'',"text":'全部'});
		}
		
		var shipStatus = $("#shipStatuss").val();
		var shipStatusText = "";
		if(shipStatus != ''){
			if(shipStatus == 1){
				shipStatusText = "已出货";
			}
			if(shipStatus == 2){
				shipStatusText = "未出货";
			}
			if(shipStatus == 3){
				shipStatusText = "未通知出货";
			}
			if(shipStatus == 4){
				shipStatusText = "已通知出货";
			}
			if(shipStatus == 5){
				shipStatusText = "通知出货失败";
			}
			if(shipStatus == 6){
				shipStatusText = "出货成功";
			}
			if(shipStatus == 7){
				shipStatusText = "出货失败";
			}
			$("#shipStatus").select2("data",{"id":shipStatus,"text":shipStatusText});
		}else{
			$("#shipStatus").select2("data",{"id":'',"text":'全部'});
		}
	}

	$(function() {
		
		//下拉框回显
		showSelect();
		
		//查询
		$("#queryBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/saleManagement/orderDetaildList");
			$("#searchForm").submit();
		});
		
		//导出Excel
		$("#exceportBut").click(function(){
			top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/hcf/saleManagement/exportOrderDetail");
					$("#searchForm").submit();
				    
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
	});
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
</head>
<body>
	<label> 
		当前位置＞＞ 运营中心＞＞销售管理＞＞<a href="javascript:void(0);" onclick=" window.history.back();">销售 明细</a>＞＞订单明细
	</label>
	<br />
	<br />

	<form:form id="searchForm" modelAttribute="orderDetailVo"
		action="${ctx}/hcf/saleManagement/orderDetaildList" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<input name="vendCode" type="hidden" value="${vendCode}" />
		<input name="vendCodes" type="hidden" value="${vendCodes}" />
		<input id="payTypes" name="payTypes" type="hidden" value="${payType}" />
		<input id="payStatuss" name="payStatuss" type="hidden" value="${payStatus}" />
		<input id="orderStatuss" name="orderStatuss" type="hidden" value="${orderStatus}" />
		<input id="shipStatuss" name="shipStatuss" type="hidden" value="${shipStatus}" />
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td>
					<label class="control-label">订单号：</label> 
					<input id="orderNo" name="orderNo" placeholder="订单号" style="width: 250px;" class="input-medium" type="text" value="${orderNo }" /> 
				</td>
				<td>
					<label class="control-label">支付类型：</label> 
					<select id="payType" name="payType" class="input-xlarge required" style="width: 120px;">
							<option value="">全部</option>
							<option value="1">微信支付</option>
							<option value="2">支付宝支付</option>
							<option value="3">现金</option>
							<option value="4">雅支付</option>
							<option value="5">取货码</option>
							<option value="6">抽奖</option>
							<option value="7">泛贩支付</option>
							<option value="8">刷脸支付</option>
							<option value="9">美好支付</option>
					</select>
				</td>
				<td>	
					<label class="control-label">支付状态：</label> 
					<select id="payStatus" name="payStatus" class="input-xlarge required" style="width: 120px;">
							<option value="">全部</option>
							<option value="1">未支付</option>
							<option value="2">已支付</option>
							<option value="3">部分支付</option>
							<option value="4">支付完成</option>
							<option value="5">已退回</option>
							<option value="6">已中请退款</option>
							<option value="7">退款成功</option>
							<option value="8">申请退款失败</option>
					</select>
				</td>
				<td>
					<label class="control-label">订状状态：</label> 
					<select id="orderStatus" name="orderStatus" class="input-xlarge required" style="width: 120px;">
							<option value="">全部</option>
							<option value="1">效易成功</option>
							<option value="2">效易失败,待退款</option>
							<option value="3">效易失败,已退款</option>
					</select>
				</td>
				<td>	
					<label class="control-label">出货状态：</label> 
					<select id="shipStatus" name="shipStatus" class="input-xlarge required" style="width: 120px;">
							<option value="">全部</option>
							<option value="1">已出货</option>
							<option value="2">未出货</option>
							<option value="3">未通知出货</option>
							<option value="4">已通知出货</option>
							<option value="5">通知出货失败</option>
							<option value="6">出货成功</option>
							<option value="7">出货失败</option>
					</select>
					<input id="queryBut" class="btn btn-primary" type="button" value="查询" />
					<input id="exceportBut" class="btn btn-primary" type="button" value="导出excel" />
				</td>
				<!-- <td width="5%" style="text-align: left"><label
					class="control-label">交易时间：</label> <input name="startTime"
					type="text" maxlength="20" class="input-medium Wdate " value=""
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"
					readonly="readonly" /> -- <input name="endTime" type="text"
					maxlength="20" class="input-medium Wdate " value=""
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"
					readonly="readonly" /> <input id="queryBut"
					class="btn btn-primary" type="button" value="查询" /> <input
					id="exceportBut" class="btn btn-primary" type="button"
					value="导出excel" /></td> -->
			</tr>
		</table>
	</form:form>

	<table id="tab"
		class="table table-striped table-bordered table-condensed"
		style="width: 100%">
		<thead>
			<tr>
				<th>序号</th>
				<th>交易编码</th>
				<th>订单号</th>
				<th>支付类型</th>
				<th>销售金额（元）</th>
				<th>销售件数</th>
				<th>交易时间</th>
				<th>上传时间</th>
				<th>出货结果</th>
				<th>机器编码</th>
				<th>部署地</th>
				<th>货道编码</th>
				<th>商品名称</th>
				<th>代理商</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="model" varStatus="bi">
				<tr>
					<td>${bi.count }</td>
					<td>${model.runningAccount }</td>
					<td>${model.orderNo }</td>
					<td>
						<c:choose>
							<c:when test="${model.payType == 1}">
								微信
							</c:when>
							<c:when test="${model.payType == 2}">
								支付宝
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
							<c:when test="${model.payType == 9}">
								美好支付
							</c:when>
							<c:otherwise>
								--
							</c:otherwise>
						</c:choose>
					</td>
					<td>${model.saleMoney }</td>
					<td>${model.saleCount }</td>
					<td><fmt:formatDate value="${model.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${model.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
					<td>${model.vendCode }</td>
					<td>${model.location }</td>
					<td>${model.shelf }</td>
					<td>${model.goodsName }</td>
					<td>${model.dealerName }</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tfoot>
			<tr>
				<td colspan="2">总合计</td>
				<td colspan="2">销售额：${saleMoney }</td>
				<td colspan="2">销售件数：${saleCount }</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tfoot>
		</tfoot>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>