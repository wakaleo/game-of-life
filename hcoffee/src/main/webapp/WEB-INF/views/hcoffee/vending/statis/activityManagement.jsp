<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
   
	<title>活动统计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
 			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForms").attr("action","${ctx}/hcf/activityManagement/list");
				$("#searchForms").submit();
			});
			
			$("#btnClean").click(function(){
				$("#tab").find("input[type=text]").val("");
				$("#channel").get(0).selectedIndex = 0;
	 
				$("#pageNo").val(1);
			});
			
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
						$("#searchForms").attr("action","${ctx}/hcf/activityManagement/export");
						$("#searchForms").submit();
					    
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
						$("#searchForms").attr("action","${ctx}/hcf/activityManagement/qbexport");
						$("#searchForms").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
		
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForms").attr("action","${ctx}/hcf/activityManagement/list");
			$("#searchForms").submit();
        	return false;
        }
				
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForms").attr("action","${ctx}/hcf/activityManagement/list");
					$("#searchForms").submit();
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
		
		function occlude(schemeNo,id,msg) {
			var data = {}; 
			data.schemeNo=schemeNo;
			data.id=id;
			if(confirm("确定要删除数据吗")){
				$.ajax({
					type:"post",
					url:'${ctx}/hcf/activityManagement/delete',
					data:JSON.stringify(data),
					dataType:"json",
					contentType:"application/json",
		            success:function(data){
		             	
		             	if(data.code=="0"){
		             		alert(msg);
				       		window.location.href="${ctx}/hcf/activityManagement/list";
				       	}else{
				       		alert(data.msg);
				       	}
		             }
		         });    
				}
				
			}
		
		function load(id){
			var data = {}; 
			data.id=id;
		
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/activityManagement/load',
				data:JSON.stringify(data),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					if(data.id!=''){
						$("#id").val(data.id);
						$("#scheme").val(data.schemeNo);
						$("#schemeId").select2("data",{"id":data.activityType,"text":data.activityType});
						$("#schemeName").val(data.schemeName);
						$("#minPrice").val(data.minPrice);
						$("#maxPrice").val(data.maxPrice);
						$("#url").val(data.url);
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
		     $("#imgDiv").show();
			$("#schemeNo").val('');
		    $("#schemeName").val('');
		    $("#channelName").val('');
			$("#id").val('');
			   $("#maxImg").html("");
		    	 $("#minImg").html("");
		    $("#select_vending").modal("show");

	}
	 	
	
	</script>

</head>
<body>
	<form:form id="searchForms" modelAttribute="activityManageVo" action="${ctx}/hcf/activityManagement/list" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="vendCodeList" name="vendCodeList" type="hidden" value=""/>
	 <table id="tab" class="table table-striped table-bordered table-condensed" >
			<div>
			<label>活动类型：</label>
				<form:select path="activityType" class="input-xlarge required" style="width:120px;"  id="activityId">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${activityTypeList}" var="tar" varStatus="indexStatus">
									<form:option value="${tar.activityType}" label="${tar.activityType}"/>
							</c:forEach>
				</form:select>
				
			
				<label>活动名称：</label>
				<form:select path="schemeNo" class="input-xlarge required" style="width:120px;"  id="schemeNo">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${activitySchemeList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.schemeNo}" label="${channel.schemeName}"/>
							</c:forEach>
				</form:select>
				&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp 
				
				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" /> 
			
				</div>
	  
	   <br>
	   <br>
			 
		        <label style="padding-right:50px;">
		           活动素材(共计  ${pages} 条)   
                       </label>
		        <label style="padding-right:1000px;">
      				 
                       </label>
	   					
	   				 <label style="padding-right:10px;">
	   				<input class="btn btn-primary"  type="button" value="新增活动" onclick="create()">
	   				</label>
		  
		 </table> 
	</form:form>
	<sys:message content="${message}"/> 
	<table class="table table-striped table-bordered table-condensed">
		<thead>
				<tr>
				<!-- <th width="1%" hidden=""> <input type="checkbox" id="all" hidden="checkbox"  name="all" onclick="selectAll()"></th> --> 
				<th width="8%" style="font-size:16px">活动名称</th>
				<th width="8%" style="font-size:16px">活动编码</th>
				<th width="8%" style="font-size:16px">活动类型</th>
				<th width="6%">最低礼品价格</th>
				<th width="6%">最高礼品价格</th>
				<th width="10%" style="font-size:16px">创建时间</th>
				<th width="10%" style="font-size:16px">操作</th>
 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<%--  <td hidden="">
						<input type="checkbox" name="vendCodeBox" hidden="checkbox " value="${model.schemeNo};${model.usrChannel}">
					</td> --%> 
					<td>${model.schemeName}</td>
					<td>${model.schemeNo}</td>
					<td>
					<c:choose>
							<c:when test="${model.activityType == '1'}">
								注册类大图标
							</c:when>
							<c:when test="${model.activityType == '2'}">
								注册类小图标
							</c:when>
							
							<c:when test="${model.activityType == '3'}">
								广告类
							</c:when>
							<c:when test="${model.activityType == '4'}">
								贷款类
							</c:when>
							
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					
					<td>${model.minPrice}</td>
					<td>${model.maxPrice}</td>
					
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
					 	 <a href="javascript:void(0)"  onclick="load('${model.id}')">编辑 </a>&nbsp;
					
					 
					 <a href="javascript:void(0)"  onclick="occlude('${model.schemeNo}','${model.id}','删除成功')">删除</a>&nbsp;
				
					</td>
				</tr>
				
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!--  选择创建售货机模式窗口（Modal） -->
	<div class="modal fade" id="select_vending" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 700px;margin-left: -400px;z-index: 1050;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="orderLabel">
						售货机管理
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include  file="../batchActivityManagement.jsp"%> 
 
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	

</body>
</html>
