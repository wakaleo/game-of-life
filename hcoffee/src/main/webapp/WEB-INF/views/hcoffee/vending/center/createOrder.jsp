  <%@ page contentType="text/html;charset=UTF-8" %>
<script  type="text/javascript">
			$(function(){
				$("#btnsave").bind("click",function(){
					var data = {};
					var id = $("#id").val();
					var orderStatus = $("#orderStatus").val();
					var logisticsCompany = $("#logisticsCompany").val();
					var orderRemark = $("#orderRemark").val();
					var orderNo = $("#orderNo_r").html();
					data.id = id;
					data.orderStatus = orderStatus;
					data.logisticsCompany = logisticsCompany;
					data.orderRemark = orderRemark;
					var url ='${ctx}/hcf/orderCenter/update';
					loading('正在提交，请稍等...');
					$.ajax({
				       	 type:'post',
				       	 url:url,
				       	 data:JSON.stringify(data),
				       	 dataType:"json",
				       	 contentType:"application/json",
				       	 success:function(data){
				       		if(data.code == '0'){
				       			closeLoading();
				       			$.jBox.tip(data.msg);
				       			window.location.href = "${ctx}/hcf/orderCenter/orderRecordList?id="+id+"&orderNo="+orderNo+"";
				       		}
				       	 }
					});  
				});
			});
	</script>
	<form:form id="communityForm" modelAttribute="OrderBaseInfoVo"
		action="#" method="post"
		class="breadcrumb form-search">
		<input id="id" name="id" type="hidden" value=""/>
		<div class="form-horizontal">
			<ul class="nav nav-tabs">
				<li>订单状态</li>
			</ul>
			<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
				<tr>
					<td width="20%">订单状态</td>
					<td width="80%">
						<select id="orderStatus" name="orderStatus" class="input-xlarge required" style="width:120px;">
							<option value="1">待付款</option>
							<option value="2">已付款</option>
							<option value="3">待接单</option>
							<option value="4">已接单</option>
							<option value="5">待发货</option>
							<option value="6">已发货</option>
							<option value="7">待收货</option>
							<option value="8">已收货</option>
							<option value="9">取消订单</option>
							<option value="10">申请退款</option>
							<option value="11">退款已受理</option>
							<option value="12">退款已拒绝</option>
							<option value="13">退款中</option>
							<option value="14">已退款</option>
							<option value="15">已关闭</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>物流信息</td>
					<td>
						物流公司：
						<select id="logisticsCompany" name="logisticsCompany" class="input-xlarge required" style="width:120px;">
									<option value="0">请选择</option>
									<option value="1">顺丰</option>
									<option value="2">申通</option>
									<option value="3">中通</option>
									<option value="4">韵达</option>
									<option value="5">天天快递</option>
						</select>
						&nbsp;&nbsp;快递单号：<span id="orderNo_r"></span>
					</td>
				</tr>
				<tr>
					<td>备注</td>
					<td>
						<textarea rows="5" cols="10" id="orderRemark" name="orderRemark" style="resize: none;"></textarea>
					</td>
				</tr>
			</table>
		</div>

		<div class="form-horizontal">
			<br> <br>
			<div class="pure-g" style="margin-left: 50px">
				<div class="pure-u-2-3">
					<div class="control-group">
						<div class="controls">
							<input id="btnsave" name="btnsave" class="btn" type="button" 
								value="保存" />&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp
							<button type="button" class="btn btn-default" data-dismiss="modal">取消
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		</div>
	</form:form>
 