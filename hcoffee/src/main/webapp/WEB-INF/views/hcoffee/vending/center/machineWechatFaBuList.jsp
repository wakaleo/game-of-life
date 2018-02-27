<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
   
	<title>二维码发布</title>
	<meta name="decorator" content="default"/>

	<script type="text/javascript">
		$(document).ready(function() {
 			$("#btnSubmit").click(function(){
 				
				$("#pageNo").val(1);
				var wechatModelNo="${wechatModelNo}";
				$("#searchForm").attr("action","${ctx}/hcf/wechatMaterialModel/fabuList?wechatModelNo="+wechatModelNo);
				$("#searchForm").submit();
			});
 			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='vendCodeBox']:checked").each(function(){
			               	checkedList.push("'"+$(this).val()+"'");
			           	});
			        	$("#vendCodeList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/wechatMaterialModel/export");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			$("#qbExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='vendCodeBox']:checked").each(function(){
			               	checkedList.push("'"+$(this).val()+"'");
			           	});
			        	$("#vendCodeList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/wechatMaterialModel/qbexport");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
		
		
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/wechatMaterialModel/fabuList");
					$("#searchForm").submit();
			     }
			}
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/wechatMaterialModel/fabuList");
			$("#searchForm").submit();
        	return false;
        }
				
	/* 	function  createTask(vendenCode){
			$("#taskVendCode").val(vendenCode);
			$("#select_task").modal("show");
		} */
		
		function enable(vendCode,status){
			 var data = {}; 
			 data.status=status;
			 data.vendCode=vendCode;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/wechatMaterialModel/enable',
				data:JSON.stringify(data),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					alert(data.msg);
		       		if(data.code=="0"){
		       			window.location.href="${ctx}/hcf/wechatMaterialModel/fabuList";
		       		}
		       	 	
				}
				
			})
		}
		function edit(vendCode){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/wechatMaterialModel/edit',
				data: vendCode,
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					if(data.id!=''){
						$("#id").val(data.id);
					    $("#channelId").select2("data",{"id":data.channel,"text":data.channelName});
					    $("#vendCodeId").val(data.vendCode);
					    $("#verId").select2("data",{"id":data.appVersionId,"text":data.appVersion});
					    $("#verId").attr("disabled","disabled");
					    $("#commId").select2("data",{"id":data.communityId,"text":data.communityName});
					    $("#equipId").select2("data",{"id":data.id,"text":data.equipSupply});
					    $("#delivery").select2("data",{"id":data.deliveryId,"text":data.deliveryType});
					    
					    $("#prId").select2("data",{"id":data.provinceId,"text":data.provinceName});
					    $("#cId").select2("data",{"id":data.cityId,"text":data.cityName});
					    $("#counId").select2("data",{"id":data.areaId,"text":data.areaName});
					   
					    
					    $("#location").val(data.location) 
					    $("#huiLink").val(data.huiLink) 
					    $("#select_vending").modal("show");
					}		       	 	
				}
				
			})
		}
		
		function record(vendCode){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/wechatMaterialModel/upgradeRecord',
				data: vendCode,
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					debugger;
					var upgradeRecordPage = data.upgradeRecordPage;
					if(upgradeRecordPage.list != null && upgradeRecordPage.list.length>0){
						var list = upgradeRecordPage.list;
						var tr="";
						
						for(var i = 0;i<upgradeRecordPage.list.length;i++){
							var obj = upgradeRecordPage.list[i];
							var td="<tr>";
							td="<td>"+obj.channelName+"</td>";
							td+="<td>"+obj.vendCode+"</td>";
							td+="<td>"+obj.vendCode+"</td>";
							td+="<td>"+obj.version+"</td>";
							td+="<td>"+obj.createTime+"</td>";
							td+="<td>"+obj.remark+"</td>";
							tr+=td+"</tr>";
						}
						$("#tbodyModel").append(tr);
					}
					$("#pagination").html(upgradeRecordPage.html);
					$("#pageNo").val(upgradeRecordPage.pageNo);
					$("#pageSize").val(upgradeRecordPage.pageSize);
					$("#select_upgradeRecord").modal("show");
				}
				
			})
		}
		
		
	
		function selectAll(){   
			//alert($("#all").attr("checked"));
			if($("#all").attr("checked")=="checked"){
				$("[name='vendCodeBox']").attr("checked",'true');//全选
			}else{
				 $("[name='vendCodeBox']").removeAttr("checked");//取消全选
			}
		};
		
		
		$(function(){
			//保存
			$("#btnsave").click(function(){
				var wechatModelNo= $("#wechatModelNo").val();
				var data = {};
	            //所有展示的
	           	var arr_vendCode = new Array();
	           	$("input[name='vendCodeBox']").each(function(){
	           		arr_vendCode.push($(this).val());
	           	});
	           //选中的
	           	var arr_checked_vendCode = new Array();
	           	$("input[name='vendCodeBox']:checked").each(function(){
	           		arr_checked_vendCode.push($(this).val());
	           	});
	           	data.arr_vendCode=arr_vendCode;
	           	data.arr_checked_vendCode=arr_checked_vendCode;
	           	data.wechatModelNo=wechatModelNo;
	           	confirm("确认发布吗?")
	           	//debugger;
	           	console.log("111"+JSON.stringify(data));
	           	var  url ='${ctx}/hcf/wechatMaterialModel/vendList';
	             $.ajax({
	            	 type:'GET',
			       	 url:url,
			       	 data:"arr_vendCode="+arr_vendCode+"&arr_checked_vendCode="+arr_checked_vendCode+"&wechatModelNo="+wechatModelNo,
			       	 dataType:"json",
			       	 //contentType:"application/json",
	                 success:function(data){
	                	 $.jBox.tip(data.msg);
			       		 if(data.code=="0"){
			       			 window.location.href="${ctx}/hcf/wechatMaterialModel/list";
			       		 }
					 }
	           	 }); 
			});
			
		});

		function batchUpgrade(wechatModelNo){
			//var templateId="${templateId}";
            //判断至少写了一项
            
            /* var checkedNum = $("input[name='vendCodeBox']:checked").length;
            if(checkedNum<1){
                alert("请至少选择1个进行发布!");
                return false;
            } */
            
            
            var data = {};
            //所有展示的
           	var arr_vendCode = new Array(15);
           	$("input[name='vendCodeBox']").each(function(){
           		arr_vendCode.push($(this).val());
           	});
           //选中的
           	var arr_checked_vendCode = new Array(15);
           	$("input[name='vendCodeBox']:checked").each(function(){
           		arr_checked_vendCode.push($(this).val());
           	});
           	data.arr_vendCode=arr_vendCode;
           	data.arr_checked_vendCode=arr_checked_vendCode;
           	data.wechatModelNo=wechatModelNo;
           	confirm("确认发布吗?")
           	
           	var  url ='${ctx}/hcf/wechatMaterialModel/vendList';
             $.ajax({
            	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(data),
		       	 dataType:"json",
		       	 contentType:"application/json",
                 success:function(data){
                	 $.jBox.tip(data.msg);
		       		 if(data.code=="0"){
		       			 window.location.href="${ctx}/hcf/wechatMaterialModel/list";
		       		 }
				 }
           	 }); 
        }
	
		
		
		function provinceName(){
			var provinceId= $("#provinceId").val()
			
			$.ajax({
		       	 type:'post',
		       	 url:'${ctx}/hcf/vendingMachine/selectProvince',
		       	data: provinceId,
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       	//  $("#cityId").select2("data",{"id":data.cityId,"text":data.cityName});
		       	
		       		 $("#cityId").empty();
		             var options = "<option value='0'>--请选择--</option>";
		             for(i = 0;i < data.length; i++){
		                     var id = data[i].cityId;
		                     var name = data[i].cityName;
		                 	 options += "<option value=" + id + ">" + name + "</option>";
		                 	 
		                 	
		              }
		              $("#cityId").append(options);
		              $("#s2id_cityId span:first").text("--请选择--");
		       	 }
			});  
			
		}
		
		
		function cityName(){
			var cityId= $("#cityId").val()
			
			var  url ='${ctx}/hcf/vendingMachine/selectCity';
			$.ajax({
		       	 type:'post',
		       	 url:url,
		     	data: cityId,
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		 
		       		 
		       		 $("#countyId").empty();
		             var options = "<option value='0'>--请选择--</option>";
		             for(i = 0;i < data.length; i++){
		                     var id = data[i].countyId;
		                     var name = data[i].countyName;
		                 	 options += "<option value=" + id + ">" + name + "</option>";
		              }
		              $("#countyId").append(options);
		              $("#s2id_countyId span:first").text("--请选择--");
		       		 
		       		 
		       	 }
			});  
			
		}
		
		
	</script>
	<style >
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
	<form:form id="searchForm" modelAttribute="vendingMachineVo" action="${ctx}/hcf/wechatMaterialModel/fabuList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="vendCodeList" name="vendCodeList" type="hidden" value=""/>
		<input id="wechatModelNo" name="wechatModelNo" type="hidden" value="${wechatModelNo}"/>
		
		
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
	     	<label >
					<input type="button" class="btn btn-primary" name="returnSubmit" value="返回发布页面" onclick=" window.history.back();"/>
			</label>
				<label >
					<label class="control-label">地域归属：</label>
				<form:select path="provinceId" class="input-xlarge required" style="width:100px;"  id="provinceId" onchange="provinceName()">
						 	<form:option value="" label="省份"/>
						 	<c:forEach items="${provinceNameList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.provinceId}" label="${channel.provinceName}"/>
							</c:forEach>
				</form:select>
			    	<select id="cityId" name="cityId" style="width:100px" class="form-control " onchange="cityName()">
								
					</select>
							<select id="countyId" name="countyId" style="width: 100px" class="form-control " >
								
							</select>
				</label>
				
				
				<label>
					<label class="control-label">归属商圈：</label>
				<form:select path="communityId" class="input-xlarge required" style="width:130px;"  id="communityId">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${communityList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.communityId}" label="${channel.communityName}"/>
							</c:forEach>
				</form:select>
				</label>
					
				<label >
					<label class="control-label">设备编码：</label>
					<form:input path="vendCode" htmlEscape="false"  style="width:120px;" class="input-medium"/>
				
					</label>
					
					<label >
					<label class="control-label" >渠道归属：</label>
				<form:select path="channel" class="input-xlarge required" style="width:120px;"  id="deliveryId">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${channelList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.channelId}" label="${channel.channelName}"/>
							</c:forEach>
				</form:select>
				</label>
					
					<label>
				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
				
				</label>
				
				 <br>
				 <br>
			 	
			
	   					
	   			
	   				<%-- <label style="padding-right:100px;">
						<a  href="${ctx}/hcf/wechatMaterialModel/selectBiaoList" style=" font-size:18px;text-decoration:underline; " >收搜售货机地理位置</a>
						</label>  --%>
		
		</table>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="4%"> <input type="checkbox" id="all" name="all" onclick="selectAll()"></th>
				<th width="4%">序号</th>
				<th width="8%">归属渠道</th>
				<th width="8%">售货机编码</th>
				
				<th width="8%">区域</th>
				<th width="8%">归属商圈</th>
				<th width="8%">点位属性</th>
				
				
				<th width="8%">详细地址</th>
				<th width="8%">当前版本</th>
 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>
						<input type="checkbox" name="vendCodeBox" <c:if test="${model.wechatModelNo == wechatModelNo && 1==model.wechatOptionType}">checked="checked"</c:if> value="${model.vendCode}">
					</td>
					<td>${bi.count}</td>
					<td>${model.channelName}</td>
					<td>${model.vendCode}</td>
					
					<td>${model.cityName}${model.areaName}</td>
					<td>${model.communityName}</td>
					<td>${model.deliveryType}</td>
					
					
					<td>
					<c:choose>
						<c:when test="${model.location == ''}">
							${model.communityName}${model.location}
						</c:when>
						<c:otherwise>
							${model.communityName}-${model.location}
						</c:otherwise>
					</c:choose></td>
					
					<td>${model.appVersion}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div align="center"> <input id="btnsave" class="btn btn-primary" type="button" value="保存" /></div>
	<div class="pagination">${page}</div>
	
</body>
</html>
