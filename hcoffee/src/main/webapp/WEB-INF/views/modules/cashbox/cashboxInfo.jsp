<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>盒子支付订单记录</title>
<meta name="decorator" content="default" />
</head>
<body>
<br/><br/>
	<div class="form-horizontal">
	<div class="control-group">
		<label class="control-label">订单号: </label>
		<div class="controls">${obj.orderCode}</div>
	</div>
	<div class="control-group">
		<label class="control-label">支付流水号: </label>
		<div class="controls">${obj.payCode}</div>
	</div>
	<div class="control-group">
		<label class="control-label">交易流水号: </label>
		<div class="controls">${obj.transactionID}</div>
	</div>
	<div class="control-group">
		<label class="control-label">姓名: </label>
		<div class="controls">${obj.userName}</div>
	</div>
	<div class="control-group">
		<label class="control-label">手机号码: </label>
		<div class="controls">${obj.phone}</div>
	</div>
	<div class="control-group">
		<label class="control-label">身份证号码: </label>
		<div class="controls">${obj.identityCode}</div>
	</div>
	<div class="control-group">
		<label class="control-label">平台: </label>
		<div class="controls">
			<c:choose>
				<c:when test="${obj.platformType == 1}">
					汇理财
				</c:when>
				<c:when test="${obj.platformType == 2}">
					汇有房
				</c:when>
				<c:when test="${obj.platformType == 3}">
					汇生活
				</c:when>
				<c:otherwise>
				-
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">产品名称: </label>
		<div class="controls">${obj.productName}</div>
	</div>
	<div class="control-group">
		<label class="control-label">交易金额: </label>
		<div class="controls"><fmt:formatNumber value="${obj.orderMoney}" pattern="#,#00.00#"/></div>
	</div>
	<div class="control-group">
		<label class="control-label">交易日期: </label>
		<div class="controls"><fmt:formatDate value="${obj.orderDateTime}" pattern="yyyy-MM-dd"/></div>
	</div>
	<div class="control-group">
		<label class="control-label">支付方式: </label>
		<div class="controls">
			<c:choose>
					<c:when test="${obj.payType == 1}">
						刷卡支付
					</c:when>
					<c:when test="${obj.payType == 2}">
						微信被扫支付
					</c:when>
					<c:when test="${obj.payType == 3}">
						微信扫码支付
					</c:when>
					<c:when test="${obj.payType == 4}">
						支付宝被扫支付
					</c:when>
					<c:when test="${obj.payType == 5}">
						支付宝扫码支付
					</c:when>
					<c:otherwise>
						-
					</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">支付账号: </label>
		<div class="controls">${obj.orderAccounts}</div>
	</div>
	<div class="control-group">
		<label class="control-label">支付状态: </label>
		<div class="controls">
			<c:choose>
				<c:when test="${obj.orderStatus == 0}">
					未支付
				</c:when>
				<c:when test="${obj.orderStatus == 1}">
					交易成功
				</c:when>
				<c:when test="${obj.orderStatus == 2}">
					交易失败
				</c:when>
				<c:when test="${obj.orderStatus == 3}">
					已购买产品
				</c:when>
				<c:when test="${obj.orderStatus == 4}">
					充值成功 购买失败
				</c:when>
				<c:when test="${obj.orderStatus == 5}">
					预订购成功
				</c:when>
				<c:when test="${obj.orderStatus == 6}">
					订单已退订,退款成功
				</c:when>
				<c:when test="${obj.orderStatus == 7}">
					退款失败
				</c:when>
				<c:when test="${obj.orderStatus == 8}">
					线上退款中
				</c:when>
				<c:when test="${obj.orderStatus == 9}">
					pos机退款中 
				</c:when>
				<c:when test="${obj.orderStatus == 99}">
					状态异常
				</c:when>
				<c:otherwise>
				-
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">备注: </label>
		<div class="controls">${obj.remark}</div>
	</div>
	<div class="control-group">
		<label class="control-label">销售经理: </label>
		<div class="controls">
			<c:if test="${empty obj.seller }">
			-
			</c:if>
			<c:if test="${not empty obj.seller }">
			${obj.seller.name}
			</c:if>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">经纪人: </label>
		<div class="controls">
			<c:if test="${empty obj.brokerPhone }">
			-
			</c:if>
			<c:if test="${not empty obj.brokerPhone }">
			${obj.brokerPhone}
			</c:if>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">设备编号: </label>
		<div class="controls">${obj.equipmentCode}</div>
	</div>
	</div>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回"
			onclick="history.go(-1)" />
	</div>
</body>
</html>
