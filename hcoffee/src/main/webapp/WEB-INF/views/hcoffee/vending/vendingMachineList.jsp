<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
   
	<title>售货机管理</title>
	<meta name="decorator" content="default"/>

	<script type="text/javascript">
	
		function init(){
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
		}
	
	
		$(function(){
 			
			init()
			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/vendingMachine/list");
				$("#searchForm").submit();
			});
 			
			$("#btnClean").click(function(){
				$("#tab").find("input[type=text]").val("");
				$("#channel").get(0).selectedIndex = 0;
	 
				$("#pageNo").val(1);
			});
			
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
		});
		
		
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/vendingMachine/list");
					$("#searchForm").submit();
			     }
			}
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/vendingMachine/list");
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
		       			window.location.href="${ctx}/hcf/vendingMachine/list";
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
					    
					    $("#vendName").val(data.vendName);
					    $("#addFlag").val(1);
					    $("#select_vending").modal("show");
					}		       	 	
				}
				
			})
		}
		
		function record(vendCode){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/vendingMachine/upgradeRecord',
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
		
		
		function create(){
			 
						$("#id").val('');
					    $("#channelId").select2("data",{"id":'',"text":'请选择渠道'});
					    $("#vendCodeId").val('');
					    $("#appVersionId").select2("data",{"id":'',"text":'请选择APP版本'});
					    $("#location").val('') ;
					    $("#addFlag").val(1);
					    $("#select_vending").modal("show");
  
		}
		
		function selectAll(){   
			//alert($("#all").attr("checked"));
			if($("#all").attr("checked")=="checked"){
				$("[name='vendCodeBox']").attr("checked",'true');//全选
			}else{
				 $("[name='vendCodeBox']").removeAttr("checked");//取消全选
			}
		};
		
		
	

		
	
		
		
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
		             var options = "<option value=''>--请选择--</option>";
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
		
		//快速添加
		function fastCreate(){
			$("#machine_text_1").hide();
			$("#machine_text_2").hide();
			$("#machine_text_3").hide();
			$("#machine_text_4").hide();
			$("#machine_text_5").hide();
			$("#machine_text_6").hide();
			$("#machine_text_7").hide();
			$("#machine_text_8").hide();
		    $("#machineLabel").html("快速添加售货机");
		    $("#addFlag").val(2);
		    $("#select_vending").modal("show");
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
	<form:form id="searchForm" modelAttribute="vendingMachineVo" action="${ctx}/hcf/vendingMachine/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="vendCodeList" name="vendCodeList" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<label >
					<label class="control-label">渠道归属：</label>
				<form:select path="channel" class="input-xlarge required" style="width:120px;"  id="channel">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${channelList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.channelId}" label="${channel.channelName}"/>
							</c:forEach>
				</form:select>
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
					<label class="control-label" >点位属性：</label>
				<form:select path="deliveryId" class="input-xlarge required" style="width:120px;"  id="deliveryId">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${deliveryTypeList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.deliveryId}" label="${channel.deliveryType}"/>
							</c:forEach>
				</form:select>
				</label>
					<label style="padding-right:60px;">
					  <label class="control-label">当前版本：</label>
			     	<form:select path="appVersionId" class="input-xlarge required" style="width:120px;"  id="appVersionId">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${appVersionList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.id}" label="${channel.version}"/>
							</c:forEach>
				</form:select> 
				</label>
				<br/>
				<br/>
		        <label style="padding-right:50px;">
		           	     当前总计： ${pages}  &nbsp &nbsp &nbsp 
      				  <a href="javascript:void(0)"  id="btnExport">导出当前页 </a>&nbsp &nbsp &nbsp;
      				  <a href="javascript:void(0)"  id="qbExport">导出所有 </a>&nbsp &nbsp;
      				  <a  href="${ctx}/hcf/vendingMachine/tuBiaoList" >售货机分布图标</a>
                 </label>
	   			<label style="float: right;padding-right: 250px;">
					<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />&nbsp;&nbsp;&nbsp;
					<input class="btn btn-primary"  type="button" value="新增设备" onclick="create()">&nbsp;&nbsp;&nbsp;
					<input class="btn btn-primary"  type="button" value="快速添加" onclick="fastCreate()">
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
				<th width="2%">机器id</th>
				<th width="3%">归属渠道</th>
				<th width="4%">售货机编码</th>
				<th width="4%">机器名称</th>
				<th width="4%">区域</th>
				<th width="4%">归属商圈</th>
				<th width="4%">点位属性</th>
				
				
				<th width="4%">详细地址</th>
				<th width="4%">当前版本</th>
				<th width="4%">启用状态</th>
				<th width="4%">坐标位置</th>
				<th width="8%">操作</th>
 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>
						<input type="checkbox" name="vendCodeBox" value="${model.vendCode}">
					</td>
					<td>${bi.count}</td>
					<td>${model.id}</td>
					<td>${model.channelName}</td>
					<td>${model.vendCode}</td>
					<td>${model.vendName}</td>
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
					</td>
					
				  <td>${model.langLaction}</td>
					 
					<td>
					 <a href="javascript:void(0)"  onclick="edit('${model.vendCode}')">编辑 </a>&nbsp;
					  <a href="${ctx}/hcf/vendingMachine/updateTubiao?vendCode=${model.vendCode}"  >标注图标</a> 
					 <%-- <a  href="javascript:void(0)"  onclick="createTask('${model.vendCode}')">在线升级</a>&nbsp;  
					 <a  href="${ctx}/hcf/vendingMachine/upgradeRecord?vendCode=${model.vendCode}">升级记录</a>&nbsp; --%>
					<c:choose>
							<c:when test="${model.status == 0}">
							<a  href="javascript:void(0)"  onclick="enable('${model.vendCode}',1)">停用</a>
							</c:when>
							<c:when test="${model.status == 1}">
							<a  href="javascript:void(0)"  onclick="enable('${model.vendCode}',0)">启用</a>
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!--  选择创建售货机模式窗口（Modal） -->
	<div class="modal fade" id="select_vending" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 800px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="machineLabel">
						新增售货机
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include  file="createMachine.jsp"%> 
				</div>
				
			</div>
		</div>
	</div>
</body>
</html>
