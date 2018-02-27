<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript">

	//解出关联
	function editOutRelevance(obj,id){
		$(obj).parent().parent().remove();
		var  url ='${ctx}/hcf/dealerManagerment/deleteVendCode';
		$.ajax({
			 type:'post',
	       	 url:url,
	       	 data:id,
	       	 dataType:"json",
	       	 contentType:"application/json",
	       	 success:function(data){
				
	       	 }
		});
	}
	//设置投放位置下拉框
	function locationChange(obj,index){
		var clas = index+"location";
		$("."+clas).empty();
		var option2 = $("<option>").val(obj.value).text(obj.value);
		$("."+clas).append(option2);
	}
	
	//验证登录名是否可用
	function checkName(obj){
		var loginName = $.trim(obj.value);
		var  url ='${ctx}/hcf/dealerManagerment/checkLoginName';
		$.ajax({
			 type:'post',
	       	 url:url,
	       	 data:loginName,
	       	 dataType:"json",
	       	 contentType:"application/json",
	       	 success:function(data){
				var code = data.errorCode;
				var msg = data.msg;
				var font = ""
				if(code == '0'){
					font = "<font color='red'>登录名不可用</font>";
				}else{
					font = "<font color='green'>登录名可用</font>";
				}
				$("#isOk").html(font);
	       	 }
		});
		
	}
	
	$(function() {
		//新增设备
		$("#addEquipment").click(function(){
			var length = $("#equipment_table tbody tr").length;
			var tr_html = "<tr><td>"+(length + 1)+"</td><td><select id='"+length+"vendCode' name='vendCode' class='vendCode' onchange='locationChange(this,"+length+")'></select></td><td><select id='"+length+"location' name='location' class='"+length+"location' readyOnly='true'></select></td><td><input onclick='outRelevance(this)' class='btn btn-primary' type='button' value='解除关联'/></td></tr>";
			var  url ='${ctx}/hcf/dealerManagerment/findEvdCode';
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(new Date()),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
					var vendCodes = data.vendCodes;
					var vendCodeFirstOption = "<option value='请选择投放位置'>--请选择机器编码--</option>";
					$(".vendCode").append(vendCodeFirstOption);
					for(var i=0;i<vendCodes.length;i++){
						console.log(vendCodes[i].vendCode + "----" + vendCodes[i].location);
						var option = $("<option>").val(vendCodes[i].location).text(vendCodes[i].vendCode);
						$(".vendCode").append(option);
					}
		       	 }
			});  
			$("#equipment_body").append(tr_html);
		});
		
		//保存
		$("#btnsave").click(function(){
			var data = {};
			/***代理商基本信息***/
			//登录名
			var loginName = $.trim($(":input[name='loginName']").val());
			//邮箱
			var mailbox = $.trim($(":input[name='mailbox']").val());
			//姓名
			var dealerName = $.trim($(":input[name='dealerName']").val());
			//生日
			var birthday = $.trim($(":input[name='birthday']").val());
			//合作状态
			var conStatus = $("#conStatus").val();
			//代理级别
			var dealerGrade = $("#dealerGrade").val();
			//身份证号
			var idCard = $.trim($(":input[name='idCard']").val());
			//银行卡号
			var bankCarNumber = $.trim($(":input[name='bankCarNumber']").val());
			//密码
			var password = $.trim($(":input[name='password']").val());
			//电话
			var cellPhone = $.trim($(":input[name='cellPhone']").val());
			//性别
			var gender = $.trim($(":input[name='gender']").val());
			//地区
			var area = $.trim($(":input[name='area']").val());
			//代理类型
			var dealerType = $("#dealerType").val();
			//详细地址
			var detailAddress = $.trim($(":input[name='detailAddress']").val());
			//银行卡信息
			var bankCarInfo = $.trim($(":input[name='bankCarInfo']").val());
			
			/****关联的设备信息*****/
			var leng = $("#equipment_table tbody tr").length;
			var arr_no = new Array(leng);
			var arr_lo = new Array(leng);
			var index = 0;
			$("#equipment_table tbody tr").each(function(){
				var vendCode = $("#"+index+"vendCode option:selected").text();
				var location = $("#"+index+"location option:selected").val();
				if(vendCode != '--请选择机器编码--' && vendCode != 'undefined'){
					arr_no[index] = vendCode;
				}
				if(location != '请选择投放位置' && location != 'undefined'){
					arr_lo[index] = location;
				}
				index++;
			});
			data.loginName = loginName;
			data.mailbox = mailbox;
			data.dealerName = dealerName;
			data.birthday = birthday;
			data.conStatus = conStatus;
			data.dealerGrade = dealerGrade;
			data.idCard = idCard;
			data.bankCarNumber = bankCarNumber;
			data.password = password;
			data.cellPhone = cellPhone;
			data.gender = gender;
			data.area = area;
			data.dealerType = dealerType;
			data.detailAddress = detailAddress;
			data.bankCarInfo = bankCarInfo;
			data.id = id;
			
			data.arr_no = arr_no;
			data.arr_lo = arr_lo;
			var  url ='${ctx}/hcf/dealerManagerment/save';
		    if(data.id != '' && data.id != undefined){
		    	   url ='${ctx}/hcf/dealerManagerment/update';
		    }
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(data),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		alert(data.msg);
		         	closeLoading()
		       		if(data.code=="0"){
		       			window.location.href="${ctx}/hcf/dealerManagerment/list";
		       		}
		       	 }
			});  
		});
	});
</script>
<style>
<!--
.td_style_1 {
	width: 40%;
	text-align: center;
}

.td_style_2 {
	width: 30%;
	text-align: center;
}
-->
</style>
<form:form id="dealerForm" modelAttribute="dealerManagermentVo"
	action="#" method="post" class="breadcrumb form-search">
	<input id="id" name="id" type="hidden" value="" />

	<table class="table table-striped table-bordered">
		<tr>
		<tr>
			<td class="td_style_1">登录名</td>
			<td class="td_style_2"><input id="loginName" onchange="checkName(this)" type="text" name="loginName" /><span id="isOk"></span></td>
			<td class="td_style_1">密码</td>
			<td class="td_style_2"><input id="password" type="text" name="password" /></td>
		</tr>
		<tr>
			<td>邮箱</td>
			<td><input id="mailbox" type="text" name="mailbox" /></td>
			<td>电话</td>
			<td><input id="cellPhone" type="text" name="cellPhone" /></td>
		</tr>
		<tr>
			<td>姓名</td>
			<td><input id="dealerName" type="text" name="dealerName" /></td>
			<td>性别</td>
			<td><select id="gender" name="gender" class="input-xlarge required"
				style="width: 120px;">
					<option value="0">男</option>
					<option value="1">女</option>
			</select></td>
		</tr>
		<tr>
			<td>生日</td>
			<td><input id="birthday" name="birthday" type="text" maxlength="20"
				class="input-medium Wdate "
				value="<fmt:formatDate value="${uploadAdVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
				readonly="readonly" /></td>
			<td>地区</td>
			<td>
				<input id="area" type="text" name="area" />
			</td>
		</tr>
		<tr>
			<td>合作状态</td>
			<td><select id="conStatus" name="conStatus" class="input-xlarge required"
				style="width: 120px;">
					<option value="0">全部</option>
					<option value="1">合作中</option>
					<option value="2">判断中</option>
					<option value="3">停止合作</option>
			</select></td>
			<td>代理类型</td>
			<td><select id="dealerType" name="dealerType" class="input-xlarge required"
				style="width: 120px;">
					<option value="0">全部</option>
					<option value="1">省级代理</option>
					<option value="2">市级代理</option>
			</select></td>
		</tr>
		<tr>
			<td>代理级别</td>
			<td><select id="dealerGrade" name="dealerGrade" class="input-xlarge required"
				style="width: 120px;">
					<option value="0">全部</option>
					<option value="1">一级代理</option>
					<option value="2">二级代理</option>
					<option value="3">三级代理</option>
			</select></td>
			<td>详细地址</td>
			<td><input id="detailAddress" type="text" name="detailAddress" /></td>
		</tr>
		<tr>
			<td>身份证号</td>
			<td><input id="idCard" type="text" name="idCard" /></td>
			<td>银行卡信息</td>
			<td><input id="bankCarInfo" type="text" name="bankCarInfo" /></td>
		</tr>
		<tr>
			<td>银行卡号</td>
			<td><input id="bankCarNumber" type="text" name="bankCarNumber" /></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td><input id="addEquipment" class="btn btn-primary"
				type="button" value="新增设备" /></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	
	<table id="edit_table" class="table table-striped table-bordered">
		<thead>
			<td>序号</td>
			<td>机器编码</td>
			<td>投放位置</td>
			<td>操作</td>
		</thead>
		<tbody id="edit_body">
			
		</tbody>
	</table>

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
