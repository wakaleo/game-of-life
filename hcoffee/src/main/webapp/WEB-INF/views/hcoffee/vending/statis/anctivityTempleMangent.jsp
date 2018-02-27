<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
   
	<title>活动模板管理</title>
	<meta name="decorator" content="default"/>

	<script type="text/javascript">
		$(document).ready(function() {
 			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/activityTemplatet/list");
				$("#searchForm").submit();
			});
			
			$("#btnClean").click(function(){
				$("#tab").find("input[type=text]").val("");
				$("#channel").get(0).selectedIndex = 0;
	 
				$("#pageNo").val(1);
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
						var schemeNoList = new Array();
						var channelList = new Array();
			           	$("input[name='vendCodeBox']:checked").each(function(){
			           		var str = $(this).val().split(";");
			           		schemeNoList.push(str[0]);
			           		channelList.push(str[1]);
			           	});
			           	
			           	$("#schemeNoList").val(schemeNoList.toString());
			           	$("#channelList").val(channelList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/activityTemplatet/export");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#qbExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var schemeNoList = new Array();
						var channelList = new Array();
			           	$("input[name='vendCodeBox']:checked").each(function(){
			           		var str = $(this).val().split(";");
			           		schemeNoList.push(str[0]);
			           		channelList.push(str[1]);
			           	});
			           	
			           	$("#schemeNoList").val(schemeNoList.toString());
			           	$("#channelList").val(channelList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/activityTemplatet/qbexport");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
		
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/activityTemplatet/list");
			$("#searchForm").submit();
        	return false;
        }
				
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/activityTemplatet/list");
					$("#searchForm").submit();
			     }
			}
		});
	
		function selectAll(){   
			//alert($("#all").attr("checked"));
			if($("#all").attr("checked")=="checked"){
				$("[name='vendCodeBox']").attr("checked",'true');//全选
			}else{
				 $("[name='vendCodeBox']").removeAttr("checked");//取消全选
			}
		};
		
		function occlude(templateId,msg) {
			var data = {}; 
			data.templateId=templateId;
			if(confirm("确定要删除数据吗")){
				$.ajax({
					type:"post",
					url:'${ctx}/hcf/activityTemplatet/delete',
					data:JSON.stringify(data),
					dataType:"json",
					contentType:"application/json",
		            success:function(data){
		             	
		             	if(data.code=="0"){
		             		alert(data.msg);
				       		window.location.href="${ctx}/hcf/activityTemplatet/list";
				       	}else{
				       		alert(data.msg);
				       	}
		             }
		         });    
				}
				
			}
	
		function load(id,templateId){
			var data = {}; 
			data.id=id
			data.templateId=templateId;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/activityTemplatet/load',
				data:JSON.stringify(data),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					for(var i=0; i<data.length;i++){
						
						if(data[i].id!=''){
							$("#id").val(data[i].id);
							
						/* 	$("#scheme").val(data.schemeNo);
							   $("#schemeId").select2("data",{"id":data.activityId,"text":data.activityType});
							$("#schemeName").val(data.schemeName);
							$("#url").val(data.url); */
							$("#template").val(data[i].templateName);
							$("#templateIds").val(data[i].templateId);
							$("#oldTemplateId").val(data[i].oldTemplateId);
							  $("#schemeNo").select2("data",{"id":data[i].schemeNo,"text":data[i].schemeName});
						
							  //$("#daiId").select2("data",{"id":data[i].schemeNo,"text":data[i].schemeName});
							 // $("#minId").select2("data",{"id":data[i].schemeNo,"text":data[i].schemeName});
							  //$("#actId").select2("data",{"id":data[i].schemeNo,"text":data[i].schemeName});
							
						//	alert(data[i].oldTemplateId)
							
							$("#remark").val(data[i].remark);
							
							
							$("#tableId").append("<tr><td type='aaa' value='" +data[i].schemeNo +"'>" + data[i].schemeName +"</td><td><input type='button' value='置顶' onclick='botten(this)'><td><input type='button' value='X' onclick='deleteRow(this)'></td></tr>")	
					}
						
					    $("#select_vending").modal("show");	
						
					}
				}
				
			})
		}
		
		
		function batchUpgrade(){
            //判断至少写了一项
            var checkedNum = $("input[name='vendCodeBox']:checked").length;
            if(checkedNum<2){
                alert("请至少选择2个进行批量升级!");
                return false;
            }
            if(confirm("确定升级所选售货机?")){
            	var checkedList = new Array();
            	$("input[name='vendCodeBox']:checked").each(function(){
                	checkedList.push($(this).val());
            	});
            	
            	debugger;
            	$("#batchVendCode").val(checkedList.toString());
            	$("#select_batchUpgradeTask").modal("show");
            	
            }
		}
		

		function create(){
			 
					    $("#select_vending").modal("show");
  
		}
		

	
	</script>
	<style >
	tr {
      text-align:center; /*设置水平居中*/
      vertical-align:middle;/*设置垂直居中*/
  }
	
	</style>
</head>
<body>
	<form:form id="searchForm" modelAttribute="anctivityTempleVo" action="${ctx}/hcf/activityTemplatet/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="vendCodeList" name="vendCodeList" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
		    <label style="padding-right:50px;">
					<label class="control-label">活动模板名称：</label>
					<form:input path="templateName" htmlEscape="false"  style="width:120px;" class="input-medium"/>
					</label>
			<label>
				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
				</label>
				
				 <br> 
				 <br>
			 	
			  	
		    
		        <label style="padding-right:1500px;">
      				     
                       </label>
	   					
	   				 <label style="padding-right:10px;">
	   				 <%-- <%-- <a  href="${ctx}/hcf/activityTemplatet/addLists" style=" size:80; color:#00F; text-decoration:underline; " >新增活动模板</a> --%> 
	   				<input class="btn btn-primary"  type="button" value="新增活动模板" onclick="create()">
	   				
	   				</label>
	   				<%-- <label style="padding-right:100px;">
						<a  href="${ctx}/hcf/vendingMachine/selectBiaoList" style=" font-size:18px;text-decoration:underline; " >收搜售货机地理位置</a>
						</label>  --%>
		</table>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
	      
		<tbody>
			<c:forEach items="${result}" var="list" >
			
				<tr>
					<td >
						<table class="table table-striped table-bordered table-condensed">
							<c:forEach items="${list}" var="vo" varStatus="bi">
							<tr>
							
							
							<c:choose>
								<c:when test="${bi.count==1}">
									<td style="" rowspan="${sizes }">${vo.templateName}</td>
									<td rowspan="${sizes }"><fmt:formatDate value="${vo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									
								</c:when>
							</c:choose>
							
							
							<td>${vo.schemeName}</td>
							
						
							<c:choose>
								<c:when test="${bi.count==1}">
										<td rowspan="${sizes }"><a href="${ctx}/hcf/activityTemplatet/alwrsList?templateId=${vo.templateId}"    style=" color:#00F; text-decoration:underline; " >已发布的设备</a></td>
								</c:when>
							</c:choose>
							
							<c:choose>
								<c:when test="${bi.count==1}">
									<td rowspan="${sizes }" ><a href="${ctx}/hcf/activityTemplatet/recortList?templateId=${vo.templateId}"    style=" color:#00F; text-decoration:underline; " >发布记录</a></td>
								</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${bi.count==1}">
									<td rowspan="${sizes }"><a href="${ctx}/hcf/activityTemplatet/revokeList?templateId=${vo.templateId}"    style=" color:#00F; text-decoration:underline; " >撤销记录</a></td>
								</c:when>
							</c:choose>
						
							
							
							
							<c:choose>
								<c:when test="${bi.count==1}">  
										<td rowspan="${sizes }"> <a href="javascript:void(0)"  onclick="load('${vo.id}','${vo.templateId}')">编辑 </a>&nbsp &nbsp &nbsp;
										 <a href="javascript:void(0)"  onclick="occlude('${vo.templateId}','删除成功')">删除</a>&nbsp &nbsp &nbsp;
										 <a  href="${ctx}/hcf/activityTemplatet/fabuList?templateId=${vo.templateId}" >发布</a>
								</c:when>
							</c:choose>
							 
								</tr>
								
							</c:forEach>
								</table>
							</td>
						</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

	<!--  选择创建售货机模式窗口（Modal） -->
	<div class="modal fade" id="select_vending" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 700px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="orderLabel"  align="center">
						活动模板管理
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include  file="anctivityTemple.jsp"%> 
 
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
</body>
</html>
