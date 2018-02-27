
<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">

	$(function(){
		$("#btnsave").click(function(){
			var data = {};
			//排序
			var orderFlag = $.trim($("#orderFlag").val());
			//类别
			var typeName = $.trim($("#typeName").val());
			if(orderFlag == ''){
				$.jBox.tip("排序不能空!");
				return;
			}
			if(typeName == ''){
				$.jBox.tip("类别不能空!");
				return;
			}
			
			var id = $("#id").val();
			var url = '${ctx}/hcf/goodsTypeManagement/save';
			if (id != '') {
				url = '${ctx}/hcf/goodsTypeManagement/update';
			}
			data.orderFlag =orderFlag;
			data.typeName = typeName;
			data.id = id;
			loading('正在提交，请稍等...');
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(data),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		         	closeLoading()
		       		$.jBox.tip(data.msg);
		       		if(data.code=="0"){
		       			window.location.href="${ctx}/hcf/goodsTypeManagement/list";
		       		}
		       	 }
			});  
		});
	});


	/* 分页 */
</script>
<form:form id="goodsTypeAdForm" modelAttribute="goodsTypeVo"
	action="#" method="post" class="breadcrumb form-search">
	<input id="id" name="id" type="hidden" value="" />
	<div class="form-horizontal">

			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>排序：</label>
						<div class="controls">
							<input id="orderFlag" name=orderFlag class="form-control" type="text" value=""/>
							<span><font>说明：序号越小，前端排序越前，为空则默认排在最后</font></span>
						</div>
					</div>
				</div>
			</div>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>类别：</label>
						<div class="controls">
							<input id="typeName" name=typeName class="form-control" type="text" value=""/>
						</div>
					</div>
				</div>
			</div>
		
		<br>
		<div class="pure-g" style="margin-left: 40px">
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
