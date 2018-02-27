  <%@ page contentType="text/html;charset=UTF-8" %>
  
<script  type="text/javascript">
			$(function(){
				$("#btnsave").bind("click",function(){
					  var data = {}; 
					 if($("#addFlag").val() == 1){
						 if($("#channelId").val()==''){
							 $.jBox.tip("请选择渠道");
							 return false; 
						  }
						  var commmty=  $("#commId").select2("data").text;
						  if(commmty=="请选择归属商圈"){
							  $.jBox.tip("请选择归属商圈");
							   return false;
						    } 
						   if($("#vendCodeId").val().trim()==''){
							   $.jBox.tip("机器编码为空");
							   return false; 
						    }
						   
						 /*   var ssas=$("#verId").select2("data").text;
						   alert(ssas)
						   if(ssas=="请选择APP版本"){
							   return  alert("请选择APP版本");
						    } */
						    if($("#verId").val()==''){
						    	$.jBox.tip("请选择APP版本");
								return false;  
								   
						    }  
						   var enf=$("#equipId").select2("data").text;
						     if(enf=="请选择设备供应方"){
						    	$.jBox.tip("请选择设备供应方");
						    	return false;
						    	
						    }
						     
						    var type=$("#delivery").select2("data").text;
							if( type=="请选择投放类型"){
								$.jBox.tip("请选择投放类型");
						    	return false;
						    	
						    } 
						    var sdds=$("#prId").select2("data").text
						    if( sdds=="省份"){
						    	$.jBox.tip("请选择地理位置");
						    	return  false;
						    	
						    } 
						    data.channel = $("#channelId").val();
						    data.channelName = $("#channelId").select2("data").text;
						    data.appVersionId = $("#verId").val();
						    data.appVersion = $("#verId").select2("data").text;
						    data.communityId = $("#commId").val();
						    data.communityName =$("#commId").select2("data").text;
						    data.huiLink = $("#huiLink").val() 
							data.equipSupply = $("#equipId").select2("data").text;
							data.deliveryId = $("#delivery").val();
							data.deliveryType = $("#delivery").select2("data").text;
						    data.cityId = $("#cId").val();
							data.cityName = $("#cId").select2("data").text;
							data.areaId = $("#counId").val();
							data.areaName = $("#counId").select2("data").text;
							data.provinceId = $("#prId").val();
							data.provinceName = $("#prId").select2("data").text;
					 }
					 if($("#addFlag").val() == 2){
						 if($.trim($("#vendCodeId").val()) == ''){
							 $.jBox.tip("机器编码为空");
							 return false;
						 }
					 }
					   	data.id = $("#id").val();
					   	data.location = $("#location").val() 
					   	data.vendName = $.trim($("#vendName").val());
					   	data.vendCode = $("#vendCodeId").val();
						data.addFlag = $("#addFlag").val();
						loading('正在提交，请稍等...');
					    var  url ='${ctx}/hcf/vendingMachine/save';
					    if(data.id!=''){
					    	   url ='${ctx}/hcf/vendingMachine/update';
					    }
						$.ajax({
					       	 type:'post',
					       	 url:url,
					       	 data:JSON.stringify(data),
					       	 dataType:"json",
					       	 contentType:"application/json",
					       	 success:function(data){
					       		$.jBox.tip(data.msg);
					         	closeLoading()
					       		if(data.code=="0"){
					       			window.location.href="${ctx}/hcf/vendingMachine/list";
					       		}
					       	 	
	 
					       	 }
						});  
				});
			})
			
			
			
			function province(){
				var provinceId= $("#prId").val()
				$.ajax({
			       	 type:'post',
			       	 url:'${ctx}/hcf/vendingMachine/selectProvince',
			       	data: provinceId,
			       	 dataType:"json",
			       	 contentType:"application/json",
			       	 success:function(data){
			       	//  $("#cityId").select2("data",{"id":data.cityId,"text":data.cityName});
			       	
			       		 $("#cId").empty();
			             var options = "<option value='0'>--请选择--</option>";
			             for(i = 0;i < data.length; i++){
			                     var id = data[i].cityId;
			                     var name = data[i].cityName;
			                 	 options += "<option value=" + id + ">" + name + "</option>";
			                 	 
			                 	
			              }
			              $("#cId").append(options);
			              $("#s2id_cId span:first").text("--请选择--");
			       	 }
				});  
				
			}
			
			
			function city(){
				var cityId= $("#cId").val()
				
				var  url ='${ctx}/hcf/vendingMachine/selectCity';
				$.ajax({
			       	 type:'post',
			       	 url:url,
			     	data: cityId,
			       	 dataType:"json",
			       	 contentType:"application/json",
			       	 success:function(data){
			       		 $("#counId").empty();
			             var options = "<option value='0'>--请选择--</option>";
			             for(i = 0;i < data.length; i++){
			                     var id = data[i].countyId;
			                     var name = data[i].countyName;
			                 	 options += "<option value=" + id + ">" + name + "</option>";
			              }
			              $("#counId").append(options);
			              $("#s2id_counId span:first").text("--请选择--");
			       		 
			       		 
			       	 }
				});  
				
			}
			
		/* 	function myMethod(){
				
				
				 var data = {}; 
		   	data.id = $("#id").val();
			    var  url ='${ctx}/hcf/vendingMachine/SaveTubiao';
			    if(data.id!=''){
			    	   url ='${ctx}/hcf/vendingMachine/updateTubiao';
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
			       			window.location.href="${ctx}/hcf/vendingMachine/list";
			       		}
			       	 	

			       	 }
				});  
			}
			 */
			
	</script>
	<form:form id="vendingMachineForm" modelAttribute="vendingMachineVo"
		action="#" method="post"
		class="breadcrumb form-search">
		 <input id="id" name="id" type="hidden" value=""/>
		<input id="addFlag" name="addFlag" type="hidden" value=""/>
		<div class="form-horizontal">
			<div class="pure-g" id="machine_text_1">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">渠道：</label>
						<div class="controls">
						    <select id="channelId" name="channelId" style="width: 220px" class="form-control "
								placeholder="选择渠道">
								<option value="">请选择渠道</option>
								<c:forEach items="${channelList}" var="channelobj" varStatus="indexStatus">
									<option value="${channelobj.channelId}">${channelobj.channelName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g" id="machine_text_2">
				<div class="pure-u-2-3" style="width:560px;">
					<div class="control-group">
						<label class="control-label">地理位置：</label>
						<div class=left>
						    <select id="prId" name="prId" style="width: 100px" class="form-control " onchange="province()"
								placeholder="省份">
								<option value="">省份</option>
								<c:forEach items="${provinceNameList}" var="channelobj" varStatus="indexStatus">
									<option value="${channelobj.provinceId}">${channelobj.provinceName}</option>
								</c:forEach>
							</select>
							<select id="cId" name="cId" style="width: 120px" class="form-control " onchange="city()">
								
							</select>
							<select id="counId" name="counId" style="width: 120px" class="form-control " >
								
							</select>

						</div>
						
					</div>
				</div>
			</div> 
			
			
			<div class="pure-g" id="machine_text_3">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">投放类型：</label>
						<div class="controls">
							<select id="delivery" name="delivery" style="width: 220px" class="form-control select2"
								placeholder="请选投放类型"><option value="">请选择投放类型</option>
								<c:forEach items="${deliveryTypeList}" var="community" varStatus="indexStatus">
									<option value="${community.deliveryId}">${community.deliveryType}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			
				<div class="pure-g" id="machine_text_4">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">设备供应方：</label>
						<div class="controls">
							<select id="equipId" name="equipId" style="width: 220px" class="form-control select2"
								placeholder="请选择设备供应方"><option value="">请选择设备供应方</option>
								<c:forEach items="${equipSupplyList}" var="community" varStatus="indexStatus">
									<option value="${community.id}">${community.equipSupply}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">售货机编码：</label>
						<div class="controls">
							<input id="vendCodeId" name="vendCodeId" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			<div class="pure-g" id="machine_text_5">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">售货机名称：</label>
						<div class="controls">
							<input id="vendName" name="vendName" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			<div class="pure-g" id="machine_text_6">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">app版本：</label>
						<div class="controls">
							<select id="verId" name="verId" style="width: 220px" class="form-control select2"
								placeholder="选择APP版本"><option value="">请选择APP版本</option>
								<c:forEach items="${appVersionList}" var="app" varStatus="indexStatus">
									<option value="${app.id}">${app.version}</option>
								</c:forEach>
							</select>
							
							 
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g" id="machine_text_7">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">归属商圈：</label>
						<div class="controls">
						    <select id="commId" name="commId" style="width: 220px" class="form-control "
								placeholder="选择归属商圈">
								<option value="">请选择归属商圈</option>
								<c:forEach items="${communityList}" var="community" varStatus="indexStatus">
									<option value="${community.communityId}">${community.communityName}</option>
								</c:forEach>
							</select>

						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">详细地址：</label>
						<div class="controls">
							 <textarea rows="3"  name="location"  id="location"> </textarea>

						</div>
					</div>
				</div>
			</div>
			
			 <div class="pure-g" id="machine_text_8">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">汇有房贷款连接：</label>
						<div class="controls">
							 <textarea rows="3"  name="huiLink"  id="huiLink"> </textarea>

						</div>
					</div>
				</div>
			</div> 
   
    <br>
    <br>
			<div class="pure-g" style="margin-left: 30px">
				<div class="pure-u-2-3">
					<div class="control-group">
						<div class="controls">
							<input id="btnsave" name="btnsave" class="btn" type="button" value="保存"   />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
							<button type="button" class="btn btn-default" data-dismiss="modal">取消
					</button>
					</div>
				</div>
			</div>
		</div>
		
		

	</form:form>
 