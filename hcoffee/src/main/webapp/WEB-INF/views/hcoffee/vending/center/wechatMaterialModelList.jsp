<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
	<title>经销商管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function init(){
			$('#add_model').on('hidden.bs.modal', function () {
				$("#add_model").css("z-index",'-1');     
			})
			
			$('#add_model').on('shown.bs.modal', function () {
				$("#add_model").css("z-index",'1050');
			})
			
			$('#show_model').on('hidden.bs.modal', function () {
				$("#show_model").css("z-index",'-1');     
			})
			
			$('#show_model').on('shown.bs.modal', function () {
				$("#show_model").css("z-index",'1050');
			})
		}
		
		//初化设备下拉框
		function initSelect(vendCodeId,locationId,vendCodeV,locationV){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/wechatMaterialModel/initSelect',
				data:JSON.stringify(new Date()),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					var vendCodes = data.vends;
					for(var i=0;i<vendCodes.length;i++){
						var vendCode = vendCodes[i].vendCode;
						var location = vendCodes[i].location;
						var flag = 	vendCodeV == vendCode ? "true" : "false";
						var optionVendCode = "<option value='"+location+"'>"+vendCode+"</option>";
						$("#"+vendCodeId).append(optionVendCode);
						
						var optionLocation = "<option value='"+location+"'>"+location+"</option>";
						$("#"+locationId).append(optionLocation);
					}
				}
			});
		}
		
		function changeLocation(obj,j){
			var locationId = j+"location";
			$("#"+locationId).empty();
			var optionLocation = "<option value='"+obj.value+"'>"+obj.value+"</option>";
			$("#"+locationId).append(optionLocation);
		}
		
		$(function(){
			init();
			//新增
			$("#addBut").click(function(){
				$("#id").val("");
				$("#modelName").val("");	
				$("#modelNo").val("");	
				$("#material_body").html("");
				$("#remark").val("");
				$("#nameIsOk").val("1");
				$("#noIsOk").val("1");
				
				
				$("#orderLabel").html("新增");
				$("#add_model").modal("show");
			});
			$("#searchBut").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/wechatMaterialModel/list");
				$("#searchForm").submit();
			});
		});
		
		//编辑
		function edit(modelNo){
			
			var datavo={};
			datavo.modelNo=modelNo;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/wechatMaterialModel/edit',
				data: JSON.stringify(datavo),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					var editPOJOList=data.editPOJOList;
					var editSize=data.editSize;
					if(editSize>0){
						$("#modelName").val(editPOJOList[0].modelName);
					    $("#modelNo").val(editPOJOList[0].modelNo);
					    $("#remark").val(editPOJOList[0].remark);
						var tr_html = "";
						var wechatNoId_1 = "";
						var wechatNameId_1 = "";
						var sortId_1 = "";
						var hidden_id = "";
						var hidden_createTime = "";
						for(var i=0;i<editSize;i++){
							var sortNum="";
							hidden_id = i + "id"
							hidden_createTime = i + "createTime"
							hidden_st = i + "sort"
							wechatNoId_1 = i + "wechatNo"; 
							wechatNameId_1 = i + "wechatName";
							sortId_1 = i + "sort";
							if (editPOJOList[i].sort) {
								sortNum=editPOJOList[i].sort;
							}
							tr_html += "<tr><td>"+(i+1)+"</td><td width='30%'><input id='"+hidden_id+"' type='hidden' name='hidden_id' value='"+editPOJOList[i].id+"'/>";
							tr_html += "<input id='"+hidden_createTime+"' type='hidden' name='hidden_ct' value='"+editPOJOList[i].createTime+"'/>";
						    tr_html += "<div class='controls'><select id='"+wechatNoId_1+"' name='wechatNo' onchange='materialChange(this,"+i+")' style='width: 220px' class='form-control select2'><option value='请选择'>请选择</option><c:forEach items="${wechatMaterialList }" var='wechat' varStatus='bi'><option value='${wechat.wechatName}'>${wechat.wechatNo }</option></c:forEach></select></div>";
						    tr_html += "</td><td width='30%'>";
						    tr_html += "<div class='controls'><input id='"+wechatNameId_1+"' name='wechatName' readonly='readonly' class='form-control' style='width: 220px' value=''></input></div>";
						    tr_html += "</td>";
						    tr_html += "<td ><input id='"+sortId_1+"' style='width:40px;' type='text' name='sort' value='"+sortNum+"'/>";
						    tr_html += "</td>";
						    tr_html += "<td id='optionId' style='text-align:right'>";
						    if (editSize>1) {
						    	tr_html += "<input onclick='editOutRelevance(this,"+editPOJOList[i].id+","+i+")' class='btn btn-primary' type='button' value='删除'/>";
							}else {
						    	tr_html += "<input onclick='editOutRelevance(this,"+editPOJOList[i].id+","+i+")' class='btn btn-primary' style='display:none' type='button' value='删除'/>";
							}
						    tr_html += "</td></tr>";
						}
						$("#material_body").html(tr_html);
						$("#orderLabel").html("修改");
						$("#add_model").modal("show");
						$("#nameIsOk").val("0");
						$("#noIsOk").val("0");
					}
					
					for(var i=0;i<editPOJOList.length;i++){
						wechatNoId_1 = "#" + i + "wechatNo"; 
						wechatNameId_1 = "#" + i + "wechatName";
						$(wechatNoId_1).select2();
						$(wechatNoId_1).select2("data",{"id":wechatNoId_1,"text":editPOJOList[i].wechatNo});
						$(wechatNoId_1 +" option[value='"+editPOJOList[i].wechatName+"']").attr("selected",true);
						$(wechatNameId_1).val(editPOJOList[i].wechatName);
					}
					
				}
			})
		}
		
		//删除
		function deletevo(modelNo){
			if(confirm("删除成功后，无法找回。确认是否删除？")){
				var datevo={};
				datevo.modelNo=modelNo;
				$.ajax({
					type:"post",
					url:'${ctx}/hcf/wechatMaterialModel/deleteVo',
					data: JSON.stringify(datevo),
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
						//window.location.href="${ctx}/hcf/lottoVend/list";
					}
				});
			}
		}
		
		
		//查看更多
		function showMore(modelNo){

			var datavo={};
			datavo.modelNo=modelNo;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/wechatMaterialModel/showMore',
				data: JSON.stringify(datavo),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					var morePOJOList = data.morePOJOList;
					var moreSize = data.moreSize;
					if(moreSize>0){
						
					    var modelName = morePOJOList[0].modelName;
					    var modelNo = morePOJOList[0].modelNo;
					    var remark = morePOJOList[0].remark;
						var tableHtml = "<table class='table table-striped table-bordered'>";
						tableHtml += "<tr><td>模板名称</td><td>"+modelName+"</td><td>模板编号</td><td>"+modelNo+"</td></tr>";
						tableHtml += "<tr><td>备注</td><td colspan='3'>"+remark+"</td></tr>";
						tableHtml += "</table>";
						tableHtml += "<table id='material_table' class='table table-striped table-bordered'>";
						tableHtml += "<thead><td>序号</td><td>素材编码</td><td>素材名称</td><td>排序</td></thead>";
					    for(var i=0;i<moreSize;i++){
					    	// 回示素材信息
							var sort="";
					    	if (morePOJOList[i].sort) {
					    		sort=morePOJOList[i].sort;
							}
						    tableHtml += "<tr><td>"+(i+1)+"</td><td>"+morePOJOList[i].wechatNo+"</td><td>"+morePOJOList[i].wechatName+"</td><td>"+sort+"</td></tr>";
						}
					    tableHtml += "</table>";
					}
					$("#baseinfoTable").html(tableHtml);
					$("#show_model").modal("show");
				}
				
			})
		}
		
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/wechatMaterialModel/list");
					$("#searchForm").submit();
			     }
			}
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/wechatMaterialModel/list");
			$("#searchForm").submit();
        	return false;
        }
	</script>
	<style>
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
</head>
<body>
	<form:form id="searchForm" modelAttribute="wechatMaterialModelVo" action="${ctx}/hcf/wechatMaterialModel/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td width="20%" style="text-align:left">
					<label class="control-label">模板名称：</label>
					<form:select path="modelNo" class="input-xlarge required" style="width:120px;"  id="modelNoId">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${modelList}" var="model" varStatus="indexStatus">
									<form:option value="${model.modelNo }" label="${model.modelName }"/>
							</c:forEach>
					</form:select>
				</td>
		 		
		 		<td width="20%" style="text-align:left">
					<%-- <label class="control-label">代理级别：</label>
					<form:select path="dealerGrade" class="input-xlarge required" style="width:120px;"  id="dealerGrade">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${dealerGradeList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select> --%>
					
				</td>
			
			 
				<td width="20%" style="text-align:left">
					<%-- <label class="control-label">合作状态：</label>
					<form:select path="conStatus" class="input-xlarge required" style="width:120px;"  id="conStatus">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${conStatusList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select> --%>
				</td>
 
 				<td>
 					<form:input path="searchText" htmlEscape="false"  style="width:300px;" class="input-medium" placeholder="支持模板名称、编号搜索"/>
 				</td>
 
				<td  width="15%" style="text-align:left" >
	   				<input id="searchBut" class="btn btn-primary" type="button" value="查询" />
	   				<input id="addBut" class="btn btn-primary" type="button" value="新增"/>
				</td>
			</tr>
		</table>
	</form:form>
	<sys:message content=""/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%">序号</th>
				<th width="8%">模板名称</th>
				<th width="6%">模板编号</th>
				<th width="6%">创建时间</th>
				<th width="8%">备注</th>
				<th width="6%">操作</th>
				<th width="6%">查看</th>
 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.modelName}</td>
					<td>${model.modelNo}</td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${model.remark}</td>
					
					<td>
					  <a  href="javascript:void(0)" onclick="showMore('${model.modelNo}')">查看更多</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					  <a  href="javascript:void(0)" onclick="edit('${model.modelNo}')">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					  <a  href="javascript:void(0)" onclick="deletevo('${model.modelNo}')">删除</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					  <a  href="${ctx}/hcf/wechatMaterialModel/fabuList?wechatModelNo=${model.modelNo}" >发布</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					</td>
					<td>
					  <a  href="${ctx}/hcf/wechatMaterialModel/recortList?wechatModelNo=${model.modelNo}"    style=" color:#00F; text-decoration:underline; " >发布记录</a>
					  <a  href="${ctx}/hcf/wechatMaterialModel/revokeList?oldWechatModelNo=${model.modelNo}"    style=" color:#00F; text-decoration:underline; " >撤销记录</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!--  选择创建售货机模式窗口（Modal） -->
	<div class="modal fade" id="add_model" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 800px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="orderLabel">
						新增
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include  file="createWechatMaterialModel.jsp"%> 
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!--  选择创建售货机模式窗口（Modal） -->
	<div class="modal fade" id="show_model" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 800px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="orderLabel">
						查看
					</h4>
				</div>
				<div class="modal-body" id="baseinfoTable">	
 				<%-- <%@ include  file="createWechatMaterialModel.jsp"%>  --%>
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
</body>
</html>