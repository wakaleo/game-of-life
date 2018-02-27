
<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		$("#btnsave")
				.bind(
						"click",
						function() {
							
							var data = {};
							if ($("#channelId").val() == '') {
								return alert("请输入渠道名称");
							}
							 if($("#mxscQrCode").val()==''){
								   return  alert("请选择免息商城二维码");
							    }
							var str=document.getElementsByName("payName");
							var objarray=str.length;
							var chestr="";
							for (i=0;i<objarray;i++)
							{
							 if(str[i].checked == true)
							 {
							  chestr+=str[i].value+",";
							 }
							}
							if(chestr == "")
							{
							 return alert("请先选择支付方式～！");
							}

							var checkedList = new Array();
							$("input[name='payName']:checked").each(function() {
								checkedList.push($(this).val());
							});
							var checkedLists = new Array();
							$("input[name='mxscQrCode']:checked").each(function() {
								checkedLists.push($(this).val());
							});
							loading('正在提交，请稍等...');
							data.id = $("#id").val();
							data.payList = checkedList.toString();
							data.mxscQrCodeList = checkedLists.toString();
							data.channelId = $("#channelId").val();
							var url = '${ctx}/hcf/payMentger/save';
							if (data.id != '') {
								url = '${ctx}/hcf/payMentger/update';
							}
							$
									.ajax({
										type : 'post',
										url : url,
										data : JSON.stringify(data),
										dataType : "json",
										contentType : "application/json",
										success : function(data) {
											alert(data.msg);
											closeLoading()
											if (data.code == "0") {
												window.location.href = "${ctx}/hcf/payMentger/list";
											}
										}
									});
						});
		document.onkeydown = function() {
			var evt = window.event || arguments[0];
			if (evt && evt.keyCode == 13) {
				if (typeof evt.cancelBubble != "undefined")
					evt.cancelBubble = true;
				if (typeof evt.stopPropagation == "function")
					evt.stopPropagation();
			}

		}
	})
</script>
<form:form id="channelForms" modelAttribute="PayVo" action="#"
	method="post" class="breadcrumb form-search">
	<input id="id" name="id" type="hidden" value="" />
	<input id="payList" name="payList" type="hidden" value="" />

	<div class="form-horizontal">
		<ul class="nav nav-tabs">
			<li>渠道管理</li>
		</ul>
		<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<label class="control-label">渠道：</label>
					<div class="controls">
						<select id="channelId" name="channelId" style="width: 220px"
							class="form-control " placeholder="选择渠道">
							<option value="">请选择渠道</option>
							<c:forEach items="${channelList}" var="channelobj"
								varStatus="indexStatus">
								<option value="${channelobj.channelId}">${channelobj.channelName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</div>

		<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<label class="control-label">支付方式：</label>
					<div class="controls" id="payId">
						<input type="checkbox" id="payId" name="payName" value="微信" />微信 
						<input type="checkbox" id="payId" name="payName" value="第三方" />第三方 
						<input type="checkbox" id="payId" name="payName" value="支付宝" />支付宝
					</div>
				</div>
			</div>
		</div>
		<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<label class="control-label">免息商城二维码：</label>
					<div class="controls" id="payId">
							<input type="checkbox" id="mxscQrCode" name="mxscQrCode" value="不显示" />不显示<input
							type="checkbox" id="mxscQrCode" name="mxscQrCode" value="显示" />显示
					</div>
				</div>
			</div>
		</div>

		<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<div class="controls">
						<input id="btnsave" name="btnsave" class="btn" type="button"
							value="保存" />
					</div>
				</div>
			</div>
		</div>

	</div>
</form:form>
