<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript">

	
	
	//表单验证
	function validationF(){
		//活动名称
		var activityName = $.trim($("#activityNameWindow").val());
		var activityNo = $.trim($("#activityNoWindow").val());
		var activityNameIsOk=$.trim($("#activityNameIsOk").val());
		var activityNameTemp=$.trim($("#activityNameTemp").val());
		var activityNoIsOk=$.trim($("#activityNoIsOk").val());
		var activityNoTemp=$.trim($("#activityNoTemp").val());
		if(activityName == ''){
			$.jBox.tip('活动名称不能为空!');
			return false;
		}if(activityNameIsOk!="1"&&activityName!=activityNameTemp){
			$.jBox.tip('活动名称不可用!');
			return false;
		}
		if(activityNo == ''){
			$.jBox.tip('活动编号不能为空!');
			return false;
		}if(activityNoIsOk!="1"&&activityNo!=activityNoTemp){
			$.jBox.tip('活动编号不可用!');
			return false;
		}
		//抽奖方式
		var lottoWay = $("#lottoWayWindow").val();
		if(lottoWay == ''){
			$.jBox.tip('请选择抽奖方式!');
			return false;
		}
		//触发条件
		var triggerCondition = $("#lottoTriggerConditionWindow").val();
		if(triggerCondition == ''){
			$.jBox.tip('请选择触发条件!');
			return false;
		}
		//开始时间
		var startTime = $.trim($("#startTimeWindow").val());
		if(startTime == ''){
			$.jBox.tip('开始时间不能为空!');
			return false;
		}
		//结束时间
		var endTime = $.trim($("#endTimeWindow").val());
		if(endTime == ''){
			$.jBox.tip('结束时间不能为空!');
			return false;
		}
		
		if(startTime>endTime){
			$.jBox.tip('开始时间不能晚于结束时间!');
			return false;
		}
		
		
		
		return true;
	}
	
	
	
	$(function(){
		
		
		//验证活动名称是否可用
		$("#activityNameWindow").blur(function(){
			
			var activityName = $.trim($(this).val());
			var $this = $(this);
			$("#activityNameMsg").remove();
			if(activityName != "" && activityName != 'undefined'){
				var activityNameTemp=$.trim($("#activityNameTemp").val());
				if (activityNameTemp!=""&&activityNameTemp!=undefined) {
					if (activityName==activityNameTemp) {
						return ;
					}
				}
				var  url ='${ctx}/hcf/lottoActivity/checkActivityName';
				$.ajax({
					 type:'post',
			       	 url:url,
			       	 data:activityName,
			       	 dataType:"json",
			       	 contentType:"application/json",
			       	 success:function(data){
						var code = data.errorCode;
						var msg = data.msg;
						var font = ""
						if(code == '0'){
							$this.after("<font id='activityNameMsg' color='red'>"+msg+"<font>");
							$("#activityNameIsOk").val("0");
						}else if(code == '1'){
							$this.after("<font id='activityNameMsg' color='green'>"+msg+"<font>");
							$("#activityNameIsOk").val("1");
						}else{
							$.jBox.tip("服务器错误!");
						}
			       	 }
				});
			}else{
				$this.after("<font id='activityNameMsg' color='red'>活动名称不能为空!<font>");
				$("#activityNameIsOk").val("0");
			}
		});
		
		//验证活动编号是否可用
		$("#activityNoWindow").blur(function(){
			
			var activityNo = $.trim($(this).val());
			var $this = $(this);
			$("#activityNoMsg").remove();
			if(activityNo != "" && activityNo != 'undefined'){
				var activityNoTemp=$.trim($("#activityNoTemp").val());
				if (activityNoTemp!=""&&activityNoTemp!=undefined) {
					if (activityNo==activityNoTemp) {
						return ;
					}
				}
				var  url ='${ctx}/hcf/lottoActivity/checkActivityNo';
				$.ajax({
					 type:'post',
			       	 url:url,
			       	 data:activityNo,
			       	 dataType:"json",
			       	 contentType:"application/json",
			       	 success:function(data){
						var code = data.errorCode;
						var msg = data.msg;
						var font = ""
						if(code == '0'){
							$this.after("<font id='activityNoMsg' color='red'>"+msg+"<font>");
							$("#activityNoIsOk").val("0");
						}else if(code == '1'){
							$this.after("<font id='activityNoMsg' color='green'>"+msg+"<font>");
							$("#activityNoIsOk").val("1");
						}else{
							$.jBox.tip("服务器错误!");
						}
			       	 }
				});
			}else{
				$this.after("<font id='activityNoMsg' color='red'>活动名称不能为空!<font>");
				$("#activityNoIsOk").val("0");
			}
		});
		
		//保存
		$("#btnsave").click(function(){
			
			//表单验证
			if(!validationF()){
				return false;
			}
			var lottoActivityVo = {};
			/***抽奖活动基本信息***/
			var id = $("#idWindow").val();
			//活动名称
			var activityName = $.trim($("#activityNameWindow").val());
			//活动编号
			var activityNo = $.trim($("#activityNoWindow").val());
			//抽奖方式
			var lottoWay = $("#lottoWayWindow").val();
			//触发条件
			var triggerCondition = $("#lottoTriggerConditionWindow").val();
			//开始时间
			var startTime = $("#startTimeWindow").val();
			//结束时间
			var endTime = $("#endTimeWindow").val();
			//活动说明
			var activityDesc = $.trim($("#activityDescWindow").val());
			
			
			lottoActivityVo.activityName = activityName;
			lottoActivityVo.activityNo = activityNo;
			lottoActivityVo.lottoWay =  Number(lottoWay);
			lottoActivityVo.triggerCondition = Number(triggerCondition);
			lottoActivityVo.startTime = startTime;
			lottoActivityVo.endTime = endTime;
			lottoActivityVo.activityDesc = activityDesc;
			lottoActivityVo.id = id;
			
			console.log(JSON.stringify(lottoActivityVo))
			
			var  url ='${ctx}/hcf/lottoActivity/save';
		    if(lottoActivityVo.id != '' && lottoActivityVo.id != undefined){
		    	   url ='${ctx}/hcf/lottoActivity/update';
		    }
			console.log(url)
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(lottoActivityVo),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		if(data.code=="0"){
			       		$.jBox.tip(data.msg);
			         	closeLoading()
		       			window.location.href="${ctx}/hcf/lottoActivity/list";
		       		}else{
		       			$.jBox.tip(data.msg);
		       		}
		       	 }
			});  
		});
	});
</script>

<style>

.td_style_1 {
	width: 15%;
	text-align: center;
}

.td_style_2 {
	width: 35%;
	text-align: center;
}


</style>

<form:form id="dealerForm" modelAttribute="lottoActivityVo"
	action="#" method="post" class="breadcrumb form-search">
	<input id="idWindow" name="id" type="hidden" value="" />
	<input id="activityNameIsOk"  type="hidden" value="" />
	<input id="activityNoIsOk"  type="hidden" value="" />
	<input id="activityNameTemp"  type="hidden" value="" />
	<input id="activityNoTemp"  type="hidden" value="" />
	<table class="table table-striped table-bordered">
		<tr>
		<tr>
			<td class="td_style_1">活动名称</td>
			<td class="td_style_2" ><input id="activityNameWindow" type="text"  name="activityName" /></td>
			<td class="td_style_1">活动编号</td>
			<td class="td_style_2" ><input id="activityNoWindow" type="text"  name="activityNo" /></td>
		</tr>
		<!-- <tr>
			<td>ID</td>
			<td><span id="activityNoWindow"></span></td>
			<td>活动状态</td>
			<td><span id="activityStatusWindow"></span></td>
		</tr>
		<tr>
			<td>抽奖次数</td>
			<td><span id="drawNumWindow"></span></td>
			<td>中奖次数</td>
			<td><span id="winnerNumWindow"></span></td>
		</tr> -->
		<tr>
			<td>抽奖方式</td>
			<td>
				<div class="controls">
					<select id="lottoWayWindow" name="lottoWayS"  style="width: 220px" class="form-control"
						placeholder="选择抽奖方式"><option value="">请选择</option>
						<c:forEach items="${lottoWayList}" var="model" varStatus="indexStatus">
							<option value="${model.value}">${model.label}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td>触发条件</td>
			<td>
				<div class="controls">
					<select id="lottoTriggerConditionWindow" name="lottoTriggerConditionS"  style="width: 220px" class="form-control"
						placeholder="选择触发条件"><option value="">请选择</option>
						<c:forEach items="${lottoTriggerConditionList}" var="model" varStatus="indexStatus">
							<option value="${model.value}">${model.label}</option>
						</c:forEach>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td>开始时间</td>
			<td>
				<input id="startTimeWindow" name="startTime" type="text"  maxlength="20" class="input-medium Wdate " value=""
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
			</td>
			<td>结束时间</td>
			<td>
				<input id="endTimeWindow" name="endTime" type="text"  maxlength="20" class="input-medium Wdate " value=""
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td>活动说明</td>
			<td colspan="3">
				<textarea rows="3"  name="activityDesc"  id="activityDescWindow"></textarea>
			</td>
		</tr>
		
		<!-- <tr>
			<td colspan="4"><input id="addEquipment" class="btn btn-primary"
				type="button" value="新增设备" /></td>
			
		</tr> -->
	</table>
	<hr/>
	
	<!-- <h3>绑定机器</h3>
	<table id="equipment_table" class="table table-striped table-bordered">
		<thead>
			<tr>
				<td>序号</td>
				<td>机器编码</td>
				<td>投放位置</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody id="equipment_body">
		</tbody>
	</table> -->

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
</form:form>
