<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
   
	<title>售货机管理</title>
	<meta name="decorator" content="default"/>

	<script type="text/javascript">
		$(document).ready(function() {
 			$("#btnSubmit").click(function(){
 				//var vendCodeAdid="${vendCodeList}";
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/vendingConterAdMentget/shebeiList");
				$("#searchForm").submit();
			});
 			
			$("#btnClean").click(function(){
				$("#tab").find("input[type=text]").val("");
				$("#channel").get(0).selectedIndex = 0;
	 
				$("#pageNo").val(1);
			});
			
			$('#select_vending').on('hidden.bs.modal', function () {
				$("#select_vending").css("z-index",'-1');     
			})
			
			$('#select_vending').on('shown.bs.modal', function () {
				$("#select_vending").css("z-index",'1050');
			})
			
			$('#select_task').on('hidden.bs.modal', function () {
				$("#select_task").css("z-index",'-1');
			})
			
			$('#select_task').on('shown.bs.modal', function () {  
				$("#select_task").css("z-index",'1050');
			})
			
			$('#select_batchUpgradeTask').on('hidden.bs.modal', function () {
				$("#select_batchUpgradeTask").css("z-index",'-1');
			})
			
			$('#select_batchUpgradeTask').on('shown.bs.modal', function () {
				$("#select_batchUpgradeTask").css("z-index",'1050');
			})
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='vendCodeBox']:checked").each(function(){
			               	checkedList.push("'"+$(this).val()+"'");
			           	});
			        	$("#vendCodeList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/vendingMachine/export");
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
						$("#searchForm").attr("action","${ctx}/hcf/vendingMachine/qbexport");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			//发布到所有机器
			$("#pulishAllMainch").click(function(){
				top.$.jBox.confirm("确定要发布到所有机器上吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var aDList =$.trim($("#vendCodeList").val());
			            var  url ='${ctx}/hcf/vendingConterAdMentget/pulishAllMainch?aDList='+aDList;
			            loading('正在发布，请稍等...');
			            $.ajax({
					       	 type:'post',
					       	 url:url,
					       	 data : {"aDList":aDList},
					       	 dataType:"json",
					       	 contentType:"application/json",
					       	 success:function(data){
					       		 if(data.code=="0"){
						       		$.jBox.tip(data.msg);
						       		closeLoading();
						         	window.location.href="${ctx}/hcf/vendingAd/list";
					       		}else{
					       			$.jBox.tip(data.msg);
					       		}
					       	 }
						});  
			            
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
					$("#searchForm").attr("action","${ctx}/hcf/vendingConterAdMentget/shebeiList");
					$("#searchForm").submit();
			     }
			}
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/vendingConterAdMentget/shebeiList");
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
				url:'${ctx}/hcf/vendingMachine/enable',
				data:JSON.stringify(data),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					alert(data.msg);
		       		if(data.code=="0"){
		       			window.location.href="${ctx}/hcf/vendingConterAdMentget/shebeiList";
		       		}
		       	 	
				}
				
			})
		}
		function edit(vendCode){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/vendingMachine/edit',
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
		
	
		
		
	 	
		function selectAll(){   
			//alert($("#all").attr("checked"));
			if($("#all").attr("checked")=="checked"){
				$("[name='vendCodeBox']").attr("checked",'true');//全选
			}else{
				 $("[name='vendCodeBox']").removeAttr("checked");//取消全选
			}
		};
		
		
		function batchUpgrade(aDList){
			//var templateId="${templateId}";
		  var aDList =$("#vendCodeList").val();
			alert("确定关联机器吗")
            //判断至少写了一项
            var data = {};
            var checkedNum = $("input[name='vendCodeBox']:checked").length;
            if(checkedNum<1){
                alert("请至少选择1个进行发布!");
                return false;
            }
            if(checkedNum>24){
                alert("最多选择24个进行发布!");
                return false;
            }
           
           	var checkedList = new Array();
           	$("input[name='vendCodeBox']:checked").each(function(){
               	checkedList.push($(this).val());
           	});
           	var vendCodeList=checkedList.toString();
           	var aDList=aDList;
             $.ajax({
	                type:"POST",
	                url:"${ctx}/hcf/vendingConterAdMentget/vendList",
	                data : "vendCodeList="+vendCodeList+"&aDList="+aDList,
					dataType : "json",
					/* contentType:"application/json; charset=UTF-8", */
	                success:function(data){
						alert(data.msg);
			       		if(data.code=="0"){
			       			window.location.href="${ctx}/hcf/vendingAd/list";
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
	<form:form id="searchForm" modelAttribute="vendingMachineVo" action="${ctx}/hcf/vendingConterAdMentget/shebeiList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="vendCodeList" name="vendCodeList" type="hidden" value="${vendCodeList}"/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			
			<label >
					<input type="button" class="btn btn-primary" name="returnSubmit" value="返回发布记录页面" onclick=" window.history.back();"/>
			</label>
			
			<label >
					<label class="control-label">渠道归属：</label>
				<form:select path="channel" class="input-xlarge required" style="width:120px;"  id="channel">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${channelList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.channelId}" label="${channel.channelName}"/>
							</c:forEach>
				</form:select>
				</label>
				
			<%-- 	<label >
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
				</label> --%>
				
				
				<label>
					<label class="control-label">归属商圈：</label>
				<form:select path="communityId" class="input-xlarge required" style="width:130px;"  id="communityId">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${communityList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.communityId}" label="${channel.communityName}"/>
							</c:forEach>
				</form:select>
				</label>
				<%-- 	<label >
					<label class="control-label" >点位属性：</label>
				<form:select path="deliveryId" class="input-xlarge required" style="width:120px;"  id="deliveryId">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${deliveryTypeList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.deliveryId}" label="${channel.deliveryType}"/>
							</c:forEach>
				</form:select>
				</label> --%>
					<label style="padding-right:60px;">
					  <label class="control-label">当前版本：</label>
			     	<form:select path="appVersionId" class="input-xlarge required" style="width:120px;"  id="appVersionId">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${appVersionList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.id}" label="${channel.version}"/>
							</c:forEach>
				</form:select> 
				</label>
				 <br>
				 <br>
			 	<label >
					<label class="control-label">设备编码：</label>
					<%-- <form:input path="vendCode" htmlEscape="false"  style="width:120px;" class="input-medium"/> --%>
					<form:textarea path="vendCode" htmlEscape="false"  style="width:300px;height:100px;resize:none;" class="input-medium" cols="10" rows="5"/>
					</label>
			  	<label>
				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
				<input id="pulishAllMainch" class="btn btn-primary" type="button" value="发布到所有机器" />
				</label>
		        <label style="padding-right:50px;">
		    
                       </label>
		        <label style="padding-right:1000px;">
      				     
                       </label>
	   					
	   				 <label style="padding-right:10px;">
	   				</label>
	   				<%-- <label style="padding-right:100px;">
						<a  href="${ctx}/hcf/vendingMachine/selectBiaoList" style=" font-size:18px;text-decoration:underline; " >收搜售货机地理位置</a>
						</label>  --%>
		
		</table>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%"> <input type="checkbox" id="all" name="all" onclick="selectAll()"></th>
				<th width="1%">序号</th>
				<th width="6%">归属渠道</th>
				<th width="6%">售货机编码</th>
				
				<th width="6%">区域</th>
			
 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>
						<input type="checkbox" name="vendCodeBox" value="${model.vendCode}">
					</td>
					<td>${bi.count}</td>
					<td>${model.channelName}</td>
					<td>${model.vendCode}</td>
					
					<td>${model.cityName}${model.areaName}</td>
				<%-- 	<td>${model.communityName}</td>
					
					
					<td>
					<c:choose>
						<c:when test="${model.location == ''}">
							${model.communityName}${model.location}
						</c:when>
						<c:otherwise>
							${model.communityName}-${model.location}
						</c:otherwise>
					</c:choose></td> --%>
				<%-- 	
					<td>${model.appVersion}</td>
					<td>
					<c:choose>
							<c:when test="${model.status == 0}">
								使用中
							</c:when>
							<c:when test="${model.status == 1}">
								停止使用
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td> --%>
					 
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div align="center"> <input class="btn btn-primary" type="button" value="保存" onclick="batchUpgrade('${vendCodeList}')"/></div>
	<div class="pagination">${page}</div>
	


</body>
</html>
