<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商机管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
	
	
		$(document).ready(function() {
			//提交事件
 			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/LeadManagement/list");
				$("#searchForm").submit();
			});
			
 			
			$('#select_vending').on('hidden.bs.modal', function () {
				$("#select_vending").css("z-index",'-1');     
			})
			
			$('#select_vending').on('shown.bs.modal', function () {
				$("#select_vending").css("z-index",'1050');
			})
		});
			
		// 数据导出
		$("#btnExport").click(function(){
			top.$.jBox.confirm("确认要导出社区数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					var checkedList = new Array();
		           	$("input[name='communityBox']:checked").each(function(){
		               	checkedList.push($(this).val());
		           	});
		           	
		           	$("#idList").val(checkedList.toString());
					$("#searchForm").attr("action","${ctx}/hcf/LeadManagement/export");
					$("#searchForm").submit();
				    
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
		
		
		
		
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/LeadManagement/list");
			$("#searchForm").submit();
        	return false;
        }
		
		
		// 编辑的方法
		function load(id){
			var data = {}; 
			data.id=id;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/LeadManagement/load',
				data:JSON.stringify(data),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					if(data.id!=''){
						$("#id").val(data.id);
						$("#lmName").val(data.lmName);
						var state = data.state;
						var stateStr = "";
						if(state == 1){
							stateStr = "已跟进";
						}
						if(state == 2){
							stateStr = "待跟进";
						}
						$("#stateS").select2("data",{"id":state,"text":stateStr});
						$("#remark").val(data.remark);
						console.log("data:"+data);
						var historyList = data.list;
						console.log("historyList:"+historyList);
						var table_html = "<thead><tr><td>操作时间</td><td>操作人</td><td>操作动作</td></tr></thead>";
						if(historyList != undefined){
							for(var i=0;i<historyList.length;i++){
								table_html += "<tr><td>"+historyList[i].operationTimeStr+"</td><td>"+historyList[i].operationPerson+"</td><td>"+historyList[i].operationAction+"</td></tr>";
							}
						}
						$("#historyList").html(table_html);
						$("#orderLabel").html("编辑");
						$("#select_vending").modal("show");
					}		       	 	
				}
				
			})
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
<body>
	<form:form id="searchForm" modelAttribute="leadManagementVo" action="${ctx}/hcf/LeadManagement/list" method="post" class="breadcrumb form-search">
		<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> --%>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="idList" name="idList" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<td width="20%">
				<label class="control-label">跟进状态：</label>
					<form:select path="state" class="input-xlarge required" style="width:120px;"  id="state">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${stateList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
					<form:input path="searchText" htmlEscape="false"  style="width:300px;" class="input-medium" placeholder="支持姓名，手机号搜索"/>
					<input type="button" id="btnSubmit" class="btn btn-primary  width="200px" value="查询">
			</td>
		</table>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%"> <input type="checkbox" id="all" name="all" onclick="selectAll()"></th>
				<th width="1%">序号</th>
				<th width="5%">姓名</th>
				<th width="6%">电话</th>
				<th width="8%">地址</th>
				<th width="8%">申请时间</th>
				<th width="8%">创建时间</th>
				<th width="8%">更新时间</th>
 				<th width="4%">跟进状态</th>
 				<th width="4%">备注</th>
 				<th width="4%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>
						<input type="checkbox" name="communityBox" value="${model.id}">
					</td>
					<td>${bi.count}</td>
					<td>${model.lMName}</td>
					<td>${model.lMPhone}</td>
					<td>${model.lMAdd}</td>
					<td><fmt:formatDate value="${model.applicationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					<td><fmt:formatDate value="${model.creationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					<td><fmt:formatDate value="${model.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					<%-- <td>${model.state}</td> --%>
					<td>
					<c:choose>
							<c:when test="${model.state == 1}">
								待跟进
							</c:when>
							<c:when test="${model.state == 2}">
								已跟进
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					
					
					<td>${model.remark}</td>
					<td>
						 <a href="javascript:void(0)"  onclick="load('${model.id}')">编辑</a>&nbsp;
					</td> 
					
				
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!--  选择商机管理模式窗口（Modal） -->
	<div class="modal fade" id="select_vending" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 700px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="orderLabel">
						商机管理
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include  file="createLeadManagement.jsp"%> 
 
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
</body>
</html>
