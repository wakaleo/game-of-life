<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>第三方交易查询</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
 			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/transactionQuery/list");
				$("#searchForm").submit();
			});
 			
 			
			$('#select_vending').on('hidden.bs.modal', function () {
				$("#select_vending").css("z-index",'-1');     
			})
			
			$('#select_vending').on('shown.bs.modal', function () {
				$("#select_vending").css("z-index",'1050');
			})
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出第三方交易查询数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='communityBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			           	
			           	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/transactionQuery/export");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			$("#qbExport").click(function(){
				top.$.jBox.confirm("确认要导第三方交易查询数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='communityBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			           	
			           	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/transactionQuery/qbexport");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
		});
		
		
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/transactionQuery/list");
					$("#searchForm").submit();
			     }
			}
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/transactionQuery/list");
			$("#searchForm").submit();
        	return false;
        }
		
		
	 	function updateOrderStauts(orderNo,amount){
			 var data = {}; 
			 data.orderNo=orderNo;
			 data.amount=amount;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/transactionQuery/updateOrderStauts',
				data:JSON.stringify(data),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					alert(data.msg);
		       		if(data.code=="0"){
		       			window.location.href="${ctx}/hcf/transactionQuery/list";
		       		}
		       	 	
				}
				
			})
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
	<form:form id="searchForm" modelAttribute="tranQueryVo" action="${ctx}/hcf/transactionQuery/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table id="tab" class="table table-striped table-bordered table-condensed" >
			<tr>
			<td  style="text-align:left">
					<label class="control-label">售货机编码：</label>
					<form:input path="vendCode" htmlEscape="false" maxlength="16" class="input-medium"/>
				</td> 
			<td  style="text-align:left">
					<label class="control-label">订单编号：</label>
					<form:input path="orderNo" htmlEscape="false" maxlength="30" class="input-medium"/>
				</td> 
		<td style="text-align:left">
					<label class="control-label">订单状态：</label>
				<form:select path="orderStatus" class="input-xlarge required" style="width:120px;"  id="orderStatus">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${orserList}" var="orser" varStatus="indexStatus">
									<form:option value="${orser.id}" label="${orser.value}"/>
							</c:forEach>
				</form:select>
		</td>
		<td style="text-align:left">
					<label class="control-label">支付状态：</label>
				<form:select path="paymentStatus" class="input-xlarge required" style="width:120px;"  id="orderStatus">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${payStusList}" var="orser" varStatus="indexStatus">
									<form:option value="${orser.id}" label="${orser.value}"/>
							</c:forEach>
				</form:select>
		</td>	
			<td style="text-align:left">
					<label class="control-label">出货状态：</label>
				<form:select path="shipmentStatus" class="input-xlarge required" style="width:120px;"  id="orderStatus">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${shipStusList}" var="orser" varStatus="indexStatus">
									<form:option value="${orser.id}" label="${orser.value}"/>
							</c:forEach>
				</form:select>
		</td>	       
		 <td style="text-align:left">
					<label class="control-label">订单渠道：</label>
				<form:select path="channel" class="input-xlarge required" style="width:120px;"  id="channel">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${channelList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.channelId}" label="${channel.channelName}"/>
							</c:forEach>
				</form:select>
				</td>
				
			
				
				 <td  style="text-align:left">
					<label class="control-label">创建时间：</label>
					<input name="startTime" type="text"  class="input-medium Wdate " value="<fmt:formatDate value="${tranQueryVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
					--
					<input name="endTime" type="text"   class="input-medium Wdate " value="<fmt:formatDate value="${tranQueryVo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
		 
				</td> 
				<td  style="text-align:left" >
	   				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
	   				<input id="btnExport" class="btn btn-primary" type="button" value="导出当前页"/>
	   				<input id="qbExport" class="btn btn-primary" type="button" value="导出所有"/>
				</td>
			</tr>
		</table> 
	</form:form>
	
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%"> <input type="checkbox" id="all" name="all" onclick="selectAll()"></th>
				<th width="1%">序号</th>
				<th width="4%">售货机编码</th>
				<th width="4%">订单编号</th>
				<th width="4%">商品编号</th>
				<th width="4%">商品名称</th>
				<th width="4%">商品价格(分)</th>
				<th width="4%">订单渠道</th>
				<th width="4%">订单创建时间</th>
				<th width="4%">支付时间</th>
				<th width="4%">出货时间</th>
				<th width="4%">订单状态</th>
				<th width="4%">支付状态</th>
				<th width="4%">出货状态</th>
				<th width="4%">更新</th>
				<th width="6%">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>
						<input type="checkbox" name="communityBox" value="${model.id}">
					</td>
					<td>${bi.count}</td>
					<td>${model.vendCode}</td>
					<td>${model.orderNo}</td>
					<td>${model.goodsId}</td>
					<td>${model.goodsName}</td>
					<td>${model.amount}</td>
					<td>${model.channelName}</td>
					<td><fmt:formatDate value="${model.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${model.paymentDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${model.shipmentDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
					<c:choose>
							<c:when test="${model.orderStatus == '1'}">
							 下单未支付
							</c:when>
							<c:when test="${model.orderStatus == '2'}">
							 下单已支付
							</c:when>
							<c:when test="${model.orderStatus == '3'}">
							 交易完成
							</c:when>
							<c:when test="${model.orderStatus == '4'}">
							 交易失败
							</c:when>
							<c:when test="${model.orderStatus == '5'}">
							 交易异常
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>
					<c:choose>
							<c:when test="${model.paymentStatus == '1'}">
							未支付
							</c:when>
							<c:when test="${model.paymentStatus == '2'}">
							 部分支付
							</c:when>
							<c:when test="${model.paymentStatus == '3'}">
							 支付完成
							</c:when>
							<c:when test="${model.paymentStatus == '4'}">
							 已退回
							</c:when>
							<c:when test="${model.paymentStatus == '5'}">
							已申请退款
							</c:when>
							<c:when test="${model.paymentStatus == '6'}">
							 退款成功
							</c:when>
							<c:when test="${model.paymentStatus == '7'}">
							 申请退款失败
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>
					<c:choose>
							<c:when test="${model.shipmentStatus == '1'}">
							 未通知出货
							</c:when>
							<c:when test="${model.shipmentStatus == '2'}">
							 已通知出货
							</c:when>
							<c:when test="${model.shipmentStatus == '3'}">
							通知出货失败
							</c:when>
							<c:when test="${model.shipmentStatus == '4'}">
							出货成功
							</c:when>
							<c:when test="${model.shipmentStatus == '5'}">
							出货失败
							</c:when>
							
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${model.orderStatus == '2'}">
								 	<a  href="javascript:void(0)"  onclick="updateOrderStauts('${model.orderNo}','${model.amount}')">更新订单</a>
							</c:when>
							<c:otherwise>
							 最终状态
						    </c:otherwise>
						</c:choose>
					</td>
					<td>${model.remark1}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	
</body>
</html>
