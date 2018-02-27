<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
	<title>订货单详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(function(){
			$('#edit_order').on('hidden.bs.modal', function () {
				$("#edit_order").css("z-index",'-1');     
			})
			
			$('#edit_order').on('shown.bs.modal', function () {
				$("#edit_order").css("z-index",'1050');
			})
		});
	
		
		//编辑
		function edit(id){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/orderCenter/edit',
				data: JSON.stringify(id),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					var obj = data.baseInfoVo;
					var orderState = obj.orderStatus;
					var logisticsCompany = obj.logisticsCompany;
					var orderStateText = "";
					var logisticsCompanyText = "";
					//订单状态
					if(orderState == '1'){
						orderStateText = "待付款";
					}
					if(orderState == '2'){
						orderStateText = "已付款";
					}
					if(orderState == '3'){
						orderStateText = "待接单";
					}
					if(orderState == '4'){
						orderStateText = "已接单";
					}
					if(orderState == '5'){
						orderStateText = "待发货";
					}
					if(orderState == '6'){
						orderStateText = "已发货";
					}
					if(orderState == '7'){
						orderStateText = "待收货";
					}
					if(orderState == '8'){
						orderStateText = "已收货";
					}
					if(orderState == '9'){
						orderStateText = "取消订单";
					}
					if(orderState == '10'){
						orderStateText = "申请退款";
					}
					if(orderState == '11'){
						orderStateText = "退款已受理";
					}
					if(orderState == '12'){
						orderStateText = "退款已拒绝";
					}
					if(orderState == '13'){
						orderStateText = "退款中";
					}
					if(orderState == '14'){
						orderStateText = "已退款";
					}
					if(orderState == '15'){
						orderStateText = "已关闭";
					}
					
					 $("#orderStatus").select2("data",{"id":orderState,"text":orderStateText});
					
					//物流公司
					if(logisticsCompany == '1'){
						logisticsCompanyText = "顺丰";
					}
					if(logisticsCompany == '2'){
						logisticsCompanyText = "申通";
					}
					if(logisticsCompany == '3'){
						logisticsCompanyText = "中通";
					}
					if(logisticsCompany == '4'){
						logisticsCompanyText = "韵达";
					}
					if(logisticsCompany == '5'){
						logisticsCompanyText = "天天快递";
					}
					
					$("#logisticsCompany").select2("data",{"id":logisticsCompany,"text":logisticsCompanyText});
					
					//快递单号
					$("#orderNo_r").html(obj.orderNo);
					//备注
					$("#orderRemark").val(obj.orderRemark);
					$("#id").val(obj.id);
					
					$("#edit_order").modal("show");
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
</head>
<body>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>
			 当前位置＞＞ 运营中心＞＞<a href="javascript:void(0);" onclick=" window.history.back();">订货中心</a>＞＞订货单详情
		</label><br/><br/>
		<label>
			<h5>订货单详情：</h5>
		</label><br/>
		
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<thead>
				<tr>
					<th>交易流水</th>
					<th>订货单号</th>
					<th>支付类型</th>
					<th>订单来源</th>
					<th>订单金额（元）</th>
					<th>订货件数</th>
					<th>交易时间</th>
					<th>订单状态</th>
					<th>代理商</th>
					<th>收货人</th>
					<th>电话</th>
					<th>收货地址</th>
					<th>买家留言</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${roderListPage.list}" var="model" varStatus="bi">
				<tr>
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
								抽奖
							</c:when>
							<c:when test="${model.payWay == 7}">
								泛贩支付
							</c:when>
							<c:when test="${model.payWay == 8}">
								刷脸支付
							</c:when>
							<c:when test="${model.payWay == 9}">
								美好支付
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${model.orderSource == '1'}">
								微信公众号
							</c:when>
							<c:when test="${model.orderSource == '2'}">
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
					<td>${model.buyerMessage}</td>
					<td>
						<a href="javascript:void(0);" onclick="edit(${model.id})">编辑</a>
					</td>
				</tr>
				</c:forEach>
		</tbody>
	</table>
	
	<br/><br/>
	<label>
		<h5>订单商品列表：</h5>
	</label>
	<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
		<thead>
			<tr>
				<th>序号</th>
				<th>商品图</th>
				<th>商品名称</th>
				<th>商品分类</th>
				<th>商品品牌</th>
				<th>单品规格</th>
				<th>箱包数</th>
				<th>补货单价(元)</th>
				<th>订货件数</th>
				<th>小计(元)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="orderGoodsVo" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>
						<img alt="" src="${ctxFile}/${orderGoodsVo.goods.pictureUrl}" style="width: 40px;height: 40px;"/>
					</td>
					<td>${orderGoodsVo.goods.goodsName}</td>
					<td>${orderGoodsVo.goodsType.typeName}</td>
					<td>${orderGoodsVo.goods.goodsBrand}</td>
					<td>${orderGoodsVo.goods.specification}</td>
					<td>${orderGoodsVo.goods.packagesNumber}</td>
					<td>${orderGoodsVo.goods.priceOut}</td>
					<td>${orderGoodsVo.orderBaseInfo.goodsCount}</td>
					<td>${orderGoodsVo.orderBaseInfo.money}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!--  编辑模式窗口（Modal） -->
	<div class="modal fade" id="edit_order" tabindex="-1" role="dialog"
		aria-labelledby="orderLabel" aria-hidden="true"
		style="width: 700px; margin-left: -400px; z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="orderLabel">编辑</h4>
				</div>
				<div class="modal-body">
					<%@ include file="createOrder.jsp"%>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>