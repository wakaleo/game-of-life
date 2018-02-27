<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>渠道管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
 			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/channelManagement/list");
				$("#searchForm").submit();
			});
 			
			$('#select_vending').on('hidden.bs.modal', function () {
				$("#select_vending").css("z-index",'-1');     
			})
			
			$('#select_vending').on('shown.bs.modal', function () {
				$("#select_vending").css("z-index",'1050');
			})
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出渠道数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='communityBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			           	
			           	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/channelManagement/export");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			$("#qbExport").click(function(){
				top.$.jBox.confirm("确认要导出渠道数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='communityBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			           	
			           	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/channelManagement/qbexport");
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
					$("#searchForm").attr("action","${ctx}/hcf/channelManagement/list");
					$("#searchForm").submit();
			     }
			}
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/channelManagement/list");
			$("#searchForm").submit();
        	return false;
        }
		
		function load(id){
			var data = {}; 
			data.id=id;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/channelManagement/load',
				data:JSON.stringify(data),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					if(data.id!=''){
						$("#id").val(data.id);
						$("#channelName").val(data.channelName);
					    $("#channelId").val(data.channelId);
					    $("#mark").val(data.mark);
					    $("#remark").val(data.remark);
					  /*  // $("#dataStatus").val(data.dataStatus); */
					    $("#select_vending").modal("show");
						var eles=$("input[name='dataStatus']");						
						var channelArr = data.statusList.split(",");
						   for(var j=0;j<eles.length;j++){
				               	for(var i=0;i<channelArr.length;i++){
				               		if(channelArr[i]==eles[j].value){
				               			eles[j].checked=true;
				               			break;
				               		}else{
				               			eles[j].checked=false;
				               		}
				               	}
						    	   
						       }
					    
					}		       	 	
				}
				
			})
		}
		
		function create(){
			    $("#channelId").val('');
			    $("#channelName").val('');
			    $("#mark").val('');
			    $("#remark").val('');
				$("#id").val('');
			    $("#select_vending").modal("show");
  
		}
		
		function selectAll(){   
			if($("#all").attr("checked")=="checked"){
				$("[name='communityBox']").attr("checked",'true');//全选
			}else{
				 $("[name='communityBox']").removeAttr("checked");//取消全选
			}
		};
		
		function occlude(id,msg) {
			var data = {}; 
			data.id=id;
			if(confirm("确定要删除数据吗")){
				$.ajax({
					type:"post",
					url:'${ctx}/hcf/channelManagement/updateChannelStatus',
					data:JSON.stringify(data),
					dataType:"json",
					contentType:"application/json",
		            success:function(data){
		             	
		             	if(data.code=="0"){
		             		alert(msg);
				       		window.location.href="${ctx}/hcf/channelManagement/list";
				       	}else{
				       		alert(data.msg);
				       	}
		             }
		         });    
				}
				
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
	<form:form id="searchForm" modelAttribute="ChannelVo" action="${ctx}/hcf/channelManagement/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td width="5%" style="text-align:left">
					<label class="control-label">渠道名称：</label>
					<input name="channelName" type="text" maxlength="20">
				
				</td>
			    <td width="8%" style="text-align:left">
					<label class="control-label">创建时间：</label>
					<input name="startTime" type="text"  maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${channelVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
					--
					<input name="endTime" type="text"  maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${channelVo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
		 
				</td> 
			 
				<td  width="8%" style="text-align:left" >
	   				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
	   				<input class="btn btn-primary" type="button" value="新增渠道" onclick="create()"/>	
				   <input id="btnExport" class="btn btn-primary" type="button" value="导出当前页"/>
				   <input id="qbExport" class="btn btn-primary" type="button" value="导出所有"/>
				</td>
			</tr>
		</table>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%"> <input type="checkbox" id="all" name="all" onclick="selectAll()"></th>
				<th width="1%">序号</th>
				<th width="5%">渠道名称</th>
				<th width="5%">渠道编码</th>
				<th width="4%">渠道标识</th>
				<th width="4%">渠道备注</th>
				<th width="6%">创建时间</th>
				<th width="4%">渠道状态</th>
				<th width="8%">操作</th>
 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>
						<input type="checkbox" name="communityBox" value="${model.id}">
					</td>
					<td>${bi.count}</td>
					<td>${model.channelName}</td>
					<td>${model.channelId}</td>
					<td>${model.mark}</td>
					<td>${model.remark}</td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
					<c:choose>
							<c:when test="${model.dataStatus == 0}">
								开启
							</c:when>
							<c:when test="${model.dataStatus == 1}">
								关闭
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					 
					<td>
					 <a href="javascript:void(0)"  onclick="load('${model.id}')">编辑 </a>&nbsp;
					 
					 <a href="javascript:void(0)"  onclick="occlude('${model.id}','删除成功')">删除</a>&nbsp;
					 
					<%--  <c:choose>
							<c:when test="${model.dataStatus == 0}">
								<a href="javascript:void(0)"  onclick="occlude('${model.id}','${model.dataStatus}','删除成功')">关闭</a>&nbsp;
							</c:when>
							<c:when test="${model.dataStatus == 1}">
								<a href="javascript:void(0)"  onclick="occlude('${model.id}','${model.dataStatus}','恢复成功')">开启 </a>&nbsp;
							</c:when>
					  </c:choose> --%>
					 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!--  选择创建渠道模式窗口（Modal） -->
	<div class="modal fade" id="select_vending" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 700px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="orderLabel">
						渠道管理
					</h4>
				</div>
				<div class="modal-body">	
 	            <%@ include  file="createChannel.jsp"%>
 
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
