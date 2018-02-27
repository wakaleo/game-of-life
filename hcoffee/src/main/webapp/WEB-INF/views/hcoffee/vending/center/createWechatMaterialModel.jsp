<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">

	//解出关联
	function outRelevance(obj,i){
		$(obj).parent().parent().remove();
		var tLength=$("#material_body tr").length;
		console.log("de:"+tLength)
		for (var j = i; j < tLength; j++) {
			$("#material_body").find("tr").eq(j).find("td").eq(0).text(j+1);
			$("#material_body").find("tr").eq(j).find("[name=hidden_id]").attr('id',j+"id");
			$("#material_body").find("tr").eq(j).find("[name=hidden_ct]").attr('id',j+"createTime");
			$("#material_body").find("tr").eq(j).find("[name=wechatNo]").attr('id',j+"wechatNo");
			$("#material_body").find("tr").eq(j).find("[name=wechatName]").attr('id',j+"wechatName");
			$("#material_body").find("tr").eq(j).find("[name=sort]").attr('id',j+"sort");
		}
		checkCanDelete();
	}
	

	//解出关联
	function editOutRelevance(obj,id,i){
		$.jBox.confirm("确定要删除吗?删除成功后，将无法找回!","系统提示",function(v,h,f){
			 if (v === 'ok') {
				$(obj).parent().parent().remove();
				var  url ='${ctx}/hcf/wechatMaterialModel/deleteVo';
				var datevo={};
				datevo.id=id;
				$.ajax({
					 type:'post',
			       	 url:url,
			       	 data:JSON.stringify(datevo),
			       	 dataType:"json",
			       	 contentType:"application/json",
			       	 success:function(data){
			       		if(data.code=="0"){
							var tLength=$("#material_body tr").length;
							console.log("de:"+tLength)
							for (var j = i; j < tLength; j++) {
								$("#material_body").find("tr").eq(j).find("td").eq(0).text(j+1);
								$("#material_body").find("tr").eq(j).find("[name=hidden_id]").attr('id',j+"id");
								$("#material_body").find("tr").eq(j).find("[name=hidden_ct]").attr('id',j+"createTime");
								$("#material_body").find("tr").eq(j).find("[name=wechatNo]").attr('id',j+"wechatNo");
								$("#material_body").find("tr").eq(j).find("[name=wechatName]").attr('id',j+"wechatName");
								$("#material_body").find("tr").eq(j).find("[name=sort]").attr('id',j+"sort");
							}
			       		}
			       		$.jBox.tip(data.msg);
			       	 }
				});
			checkCanDelete();
          	}
		},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
	}
	
	//设置投放位置下拉框
	function materialChange(obj,index){
		var val = obj.value;
		var idF = index+"wechatName";
		$("#"+idF).val(val);
	}
	
	
	//表单验证
	function validationF(){
		//模板名称
		var modelName = $.trim($("#modelName").val());
		if(modelName == ''){
			$.jBox.tip('模板名称不能为空!');
			return false;
		}
		var nameIsOk = $.trim($("#nameIsOk").val());
		if ('0'!=nameIsOk) {
			$.jBox.tip('模板名称不可用!');
			return false;
		}
		//模板编码
		var modelNo = $.trim($("#modelNo").val());
		if(modelNo == ''){
			$.jBox.tip('模板编码不能为空!');
			return false;
		}
		var noIsOk = $.trim($("#noIsOk").val());
		if ('0'!=noIsOk) {
			$.jBox.tip('模板编码不可用!');
			return false;
		}
		
		
		
		return true;
	}
	
	function superiorAgent(obj){
		var  url ='${ctx}/hcf/wechatMaterialModel/superiorAgent';
		$.ajax({
			 type:'post',
	       	 url:url,
	       	 data:obj.value,
	       	 dataType:"json",
	       	 contentType:"application/json",
	       	 success:function(data){
	       		$("#parentId").empty();
	       		$("#parentId").select2("data",{"id":'',"text":'请选择'});
	       		var dealers = data.dealers;
				$("#select_id").prepend("<option value=''>请选择</option>")
				for(var i=0;i<dealers.length;i++){
					$("#parentId").prepend("<option value='"+dealers[i].id+"'>"+dealers[i].dealerName+"</option>")
				}
	       	 }
		});
	}
	
	

	function checkCanDelete(){
		var length = $("#material_body tr").length;
		if (length>1) {
			
			$("#optionId input").css("display","inline");  
		}else {
			$("#optionId input").css("display","none");  
		}
	}
	
	$(function(){
		
		//模板名称
		$("#modelName").change(function(){
			$("#nameIsOkSpan").html("");
			var wechatMaterialModelVo={};
			//模板名称
			var modelName = $.trim($("#modelName").val());
			wechatMaterialModelVo.modelName=modelName;
			var  url ='${ctx}/hcf/wechatMaterialModel/checkNameOfNo';
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(wechatMaterialModelVo),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		if(data.code=="0"){
		       			$("#nameIsOkSpan").css("color","green");
		       		}else{
		       			$("#nameIsOkSpan").css("color","red");
		       		}
		       		$("#nameIsOkSpan").html(data.msg);
		       		//可用为0，不可用为1
		       		$("#nameIsOk").val(data.code);
		       	 }
			});  
		});
		//模板编码
		$("#modelNo").change(function(){
		    $("#noIsOkSpan").html("");
			var wechatMaterialModelVo={};
			//模板编码
			var modelNo = $.trim($("#modelNo").val());
			wechatMaterialModelVo.modelNo=modelNo;
			var  url ='${ctx}/hcf/wechatMaterialModel/checkNameOfNo';
			$.ajax({
				 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(wechatMaterialModelVo),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		if(data.code=="0"){
		       			$("#noIsOkSpan").css("color","green");
		       		}else{
		       			$("#noIsOkSpan").css("color","red");
		       		}
		       		$("#noIsOkSpan").html(data.msg);
		       		//可用为0，不可用为1
		       		$("#noIsOk").val(data.code);
		       	 }
			});  
		});
		
		
		
		
		
		//新增素材
		$("#addMaterial").click(function(){
			var length = $("#material_body tr").length;
			var selectId = length + "wechatNo";
			var tr_html = "<tr><td>"+(length + 1)+"</td><td width='30%'>";
		    tr_html += "<div class='controls'><select id='"+length+"wechatNo' name='wechatNo' class='form-control' style='width: 220px;' onchange='materialChange(this,"+length+")'><option value='请选择'>请选择</option><c:forEach items="${wechatMaterialList }" var='wechat' varStatus='bi'><option value='${wechat.wechatName}'>${wechat.wechatNo }</option></c:forEach></select></div>";
		    tr_html += "</td><td width='30%'>";
		    tr_html += "<div class='controls'><input id='"+length+"wechatName' name='wechatName' readonly='readonly' class='form-control' style='width: 220px;' value=''></input></div>";
		    tr_html += "</td>";
		    tr_html += "<td ><input id='"+length+"sort' style='width:40px;' type='text' name='hidden_st' value=''/>";
		    tr_html += "</td>";
		    tr_html += "<td id='optionId' style='text-align:right'>";
		    tr_html += "<input onclick='outRelevance(this,"+length+")' class='btn btn-primary' style='display:none'  type='button' value='删除'/>";
		    tr_html += "</td></tr>";
		    $("#material_body").append(tr_html);
		    $("#"+selectId).select2();
		    checkCanDelete();
		});
		function isRepeat(arr){
			for(var i=0;i<arr.length;i++){
				if(arr[i] == arr[i+1]){
					return arr[i];
					break;
				}
			}
		}
		
		
		//保存
		$("#btnsave").click(function(){
			
			//表单验证
			if(!validationF()){
				return false;
			}
			var data = {};
			/***代理商基本信息***/
			var id = $("#id").val();
			//模板名称
			var modelName = $.trim($("#modelName").val());
			//模板编码
			var modelNo = $.trim($("#modelNo").val());
			//备注
			var remark = $.trim($("#remark").val());
			console.log("modelNo"+modelNo);
			console.log("remark"+remark);
			
			/****关联的设备信息*****/
			var leng = $("#material_table tbody tr").length;
			var arr_no = new Array(leng);
			var arr_id = new Array(leng);
			var arr_ct = new Array(leng);
			var arr_st = new Array(leng);
			var index = 0;
			$("#material_table tbody tr").each(function(){
				//二维码素材编码
				var wechatNo = "";
				var hidden_id = "";
				var hidden_createTime = "";
				var sort = "";
				wechatNo = $("#"+index+"wechatNo option:selected").text();
				 //location = $("#"+index+"location option:selected").val();
				 //location = $("#"+index+"wechatName").val();
				 hidden_id = $("#"+index+"id").val();
				 hidden_createTime = $("#"+index+"createTime").val();
				 sort = $("#"+index+"sort").val();
				 if(wechatNo != '请选择' && wechatNo != 'undefined'){
					 arr_no[index] = wechatNo;
				 }
				 if(hidden_id != '' && hidden_id != 'undefined'){
					 arr_id[index] = hidden_id;
				 }
				 if(hidden_createTime != '' && hidden_createTime != 'undefined'){
					 arr_ct[index] = hidden_createTime;
				 }
				 if(sort != '' && sort != 'undefined'){
					 arr_st[index] = sort;
				 }
				index++;
			});
			data.modelName = modelName;
			data.modelNo = modelNo;
			data.remark = remark;
			data.id = id;
			console.log("arr_no:"+arr_no);
			
			data.arr_no = arr_no;
			data.arr_id = arr_id;
			data.arr_ct = arr_ct;
			data.arr_st = arr_st;
			
			var repeatWechatNo = isRepeat(arr_no);
			if(repeatWechatNo != null && repeatWechatNo != ""){
				$.jBox.tip("素材:<font color='red'><strong>"+repeatWechatNo+"</strong></font>不能重复关联!");
				return false;
			}
			var  url ='${ctx}/hcf/wechatMaterialModel/save';
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(data),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		if(data.code=="0"){
			       		$.jBox.tip(data.msg);
			         	closeLoading()
		       			window.location.href="${ctx}/hcf/wechatMaterialModel/list";
		       		}else{
		       			$.jBox.tip(data.msg);
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
.span_type{
	margin: 0;
	padding: 0;
	line-height: 2px;
}
#div_zxjg{padding:0px;margin: 0px;}
#div_zxjg ul{padding:0px;margin: 0px;}
#div_zxjg ul li{list-style-type: none;}

#div_zjzp{padding:0px;margin: 0px;}
#div_zjzp ul{padding:0px;margin: 0px;}
#div_zjzp ul li{list-style-type: none;}

#fenZhangSz{margin-top: 5px;}
-->
</style>
<form:form id="dealerForm" modelAttribute="wechatMaterialModelVo"
	action="#" method="post" class="breadcrumb form-search">
	<input id="id" name="id" type="hidden" value="" />
	<input id="nameIsOk"  type="hidden" value="" />
	<input id="noIsOk"  type="hidden" value="" />
	<table class="table table-striped table-bordered">
		<tr class="person_type">
			<td class="td_style_1"><font size="4" color="red">*</font>模板名称</td>
			<td class="td_style_2"><input id="modelName" type="text" name="modelName" /><span id="nameIsOkSpan"></span></td>
			<td class="td_style_1"><font size="4" color="red">*</font>模板编号</td>
			<td class="td_style_2"><input id="modelNo" type="text" name="modelNo" /><span id="noIsOkSpan"></span></td>
		</tr>
		<tr class="person_type">
			<td  >备注</td>
			<td  colspan='3' ><textarea  id="remark" rows="3" cols="100"  name="remark" ></textarea></td>
		</tr>
		<!-- 企业显示字段   结束 -->
		<tr>
			<td><input id="addMaterial" class="btn btn-primary"
				type="button" value="新增素材" /></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	
	<table id="material_table" class="table table-striped table-bordered">
		<thead>
			<tr>
				<td>序号</td>
				<td>素材编码</td>
				<td>素材名称</td>
				<td>排序</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody id="material_body">
		</tbody>
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
</form:form>
<script type="text/javascript">  

</script> 

