<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>盒子支付订单记录</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function(){
			top.$.jBox.tip.mess = null;
		});
		function page(n,s){
        	location = '${ctx}/act/model/?pageNo='+n+'&pageSize='+s;
        }
		function updateCategory(id, category){
			$.jBox($("#categoryBox").html(), {title:"设置分类", buttons:{"关闭":true}, submit: function(){}});
			$("#categoryBoxId").val(id);
			$("#categoryBoxCategory").val(category);
		}
	</script>
	<script type="text/javascript">
		$(document).ready(function() {

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
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/cashbox/model/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnSubmit").click(function(){
				$("#searchForm").attr("action","${ctx}/cashbox/model/list");
				$("#searchForm").submit();
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
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
	<form:form id="searchForm" modelAttribute="order" action="${ctx}/cashbox/model/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>用户名称：</label>
				<form:input path="userName" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li>
				<label>手机号码：</label>
				<form:input path="phone" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li>
				<label>产品名称：</label>
				<form:input path="productName" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li>
				<label>现场销售：</label>
				<form:input path="sellManager" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>平台类型：</label>
				<form:select path="platformType" class="input-xlarge required">
					<form:option value="" label="全部平台"/>
					<form:option value="1" label="汇理财"/>
					<form:option value="2" label="汇有房"/>
					<form:option value="3" label="汇生活"/>
				</form:select>
			</li>
			<%-- <li>
				<label>支付方式：</label>
				<form:select path="orderForm" class="input-xlarge required">
					<form:option value="" label="全部平台"/>
					<form:option value="1" label="汇理财"/>
					<form:option value="2" label="汇有房"/>
					<form:option value="3" label="汇生活"/>
				</form:select>
			</li> --%>
			<li>
				<label>支付状态：</label>
				<form:select path="orderStatus" class="input-xlarge required">
					<form:option value="" label="全部"/>
					<form:option value="0" label="未支付"/>
					<form:option value="1" label="充值成功"/>
					<form:option value="2" label="充值失败"/>
					<form:option value="3" label="购买成功"/>
					<form:option value="4" label="充值成功 购买失败"/>
					<form:option value="5" label="预订购成功"/>
					<form:option value="6" label="订单已退订,退款成功"/>
					<form:option value="7" label="退款失败"/>
					<form:option value="8" label="线上退款中"/>
					<form:option value="9" label="pos机退款中 "/>
					<form:option value="99" label="状态异常"/>
				</form:select>
			</li>
			<li>
				<label>开始日期：</label>
				<input name="startDate" type="text" readonly="readonly" id="startdate" value="${order.startDate}"/>
			</li>
			<li>
				<label>截止日期：</label>
				<input name="endDate" type="text" readonly="readonly" id="enddate" value="${order.endDate}"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>
			<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="clearfix"></li>
		</ul>
		
		
		
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>交易流水号</th>
				<th>用户名称</th>
				<th>手机号码</th>
				<th>平台</th>
				<th>产品名称</th>
				<th>投资金额（元）</th>
				<th>支付方式</th>
				<th>支付状态</th>
				<th>创建日期</th>
				<th>现场销售</th>
				<th>经纪人</th>
				<th>设备编码</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${(bi.index+1)+((pageUtil.pageId-1) * pageUtil.pageSize)}</td>
					<td>${model.transactionID}</td>
					<td>${model.userName}</td>
					<td>${model.phone}</td>
					<td>
					<c:choose>
						<c:when test="${model.platformType == 1}">
							汇理财
						</c:when>
						<c:when test="${model.platformType == 2}">
							汇有房
						</c:when>
						<c:when test="${model.platformType == 3}">
							汇生活
						</c:when>
						<c:otherwise>
						-
						</c:otherwise>
					</c:choose>
					</td>
					<td>${model.productName}</td>
					<td><fmt:formatNumber value="${model.orderMoney}" pattern="#,##0.0#"/></td>
					<td>
						<c:choose>
							<c:when test="${model.payType == 1}">
								刷卡支付
							</c:when>
							<c:when test="${model.payType == 2}">
								微信被扫支付
							</c:when>
							<c:when test="${model.payType == 3}">
								微信扫码支付
							</c:when>
							<c:when test="${model.payType == 4}">
								支付宝被扫支付
							</c:when>
							<c:when test="${model.payType == 5}">
								支付宝扫码支付
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${model.orderStatus == 0}">
								未支付
							</c:when>
							<c:when test="${model.orderStatus == 1}">
								交易成功
							</c:when>
							<c:when test="${model.orderStatus == 2}">
								交易失败
							</c:when>
							<c:when test="${model.orderStatus == 3}">
								已购买产品
							</c:when>
							<c:when test="${model.orderStatus == 4}">
								充值成功 购买失败
							</c:when>
							<c:when test="${model.orderStatus == 5}">
								预订购成功
							</c:when>
							<c:when test="${model.orderStatus == 6}">
								订单已退订,退款成功
							</c:when>
							<c:when test="${model.orderStatus == 7}">
								退款失败
							</c:when>
							<c:when test="${model.orderStatus == 8}">
								线上退款中
							</c:when>
							<c:when test="${model.orderStatus == 9}">
								pos机退款中 
							</c:when>
							<c:when test="${model.orderStatus == 99}">
								状态异常
							</c:when>
							<c:otherwise>
							-
							</c:otherwise>
						</c:choose>
					</td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd"/></td>
					<td>
					<c:if test="${empty model.seller }">
					-
					</c:if>
					<c:if test="${not empty model.seller }">
					${model.seller.name}
					</c:if>
					</td>
					<td>
					<c:if test="${empty model.brokerPhone }">
					-
					</c:if>
					<c:if test="${not empty model.brokerPhone }">
					${model.brokerPhone}
					</c:if>
					</td>
					<td>${model.equipmentCode}</td>
					<td><a href="${ctx}/cashbox/model/detail?id=${model.id}">查看详情</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
