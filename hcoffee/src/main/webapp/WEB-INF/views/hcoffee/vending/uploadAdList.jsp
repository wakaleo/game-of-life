<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>\
 
<html>
<head>
<head>
<link href="/hkfsysadmin/static/data/styles.css" type="text/css" rel="stylesheet"/>
	<title>上传广告</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/uploadAd/list");
				$("#searchForm").submit();
			});
			
			
			$('#select_createVendingAd').on('hidden.bs.modal', function () {
				$("#select_createVendingAd").css("z-index",'-1');
			})
			
			$('#select_createVendingAd').on('shown.bs.modal', function () {
				$("#select_createVendingAd").css("z-index",'1050');
			})
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						
						var checkedList = new Array();
			           	$("input[name='adBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			           	
			           	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/uploadAd/export");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#qbExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						
						var checkedList = new Array();
			           	$("input[name='adBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			           	
			           	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/uploadAd/qbexport");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
		});
		
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/uploadAd/list");
			$("#searchForm").submit();
        	return false;
        }
				
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/uploadAd/list");
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
		
		function addVendingAd(){   
		     
			var videoPreview = document.getElementById('video');  
	    	videoPreview.innerHTML = '';
			$("#orderLabel").text("新增广告!");
			 $("#select_createVendingAd").modal("show");
		}
		
		function load(channelId,schemeNo){
			var data = {}; 
			data.channelId=channelId;
			data.schemeNo=schemeNo;
			
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/uploadAd/load',
				data:JSON.stringify(data),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					if(data.id!=''){
						$("#id").val(data.id);
				    	$("#video").val(data.adPath);
				    	$("#channelId").select2("data",{"id":data.channelId,"text":data.channelName});
				    	$("#schemeNo").select2("data",{"id":data.schemeNo,"text":data.schemeName});
				    	var videoPreview = document.getElementById('video');  
				    	videoPreview.innerHTML = '<video width="60" height="80" controls="controls" src=${ctxFile}/'+ data.adPath + '  />';
				    //	videoPreview.innerHTML = '<video width="60" height="80" controls="controls" src="http://192.168.30.201//'+ data.adPath + ' " />';
				    	$("#orderLabel").text("编辑广告!");
					    $("#select_createVendingAd").modal("show");
					}		       	 	
				}
				
			})
		}
		
		
		function selectAll(){   
			//alert($("#all").attr("checked"));
			if($("#all").attr("checked")=="checked"){
				$("[name='adBox']").attr("checked",'true');//全选
			}else{
				 $("[name='adBox']").removeAttr("checked");//取消全选
			}
		};
		
		function occlude(channelId,schemeNo,adStatus,msg) {
			var data = {}; 
			data.channelId=channelId;
			data.schemeNo=schemeNo;
			data.adStatus=adStatus;
			$.ajax({
			type:"post",
			url:'${ctx}/hcf/uploadAd/close',
			data:JSON.stringify(data),
			dataType:"json",
			contentType:"application/json",
            success:function(data){
             	
             	if(data.code=="0"){
             		alert(msg);
		       		window.location.href="${ctx}/hcf/uploadAd/list";
		       	}else{
		       		alert(data.msg);
		       	}
             }
         });    
		}
		
	</script>
	<style>
	
	</style>
</head>
</head>
<body>
	<form:form id="searchForm" modelAttribute="uploadAdVo" action="${ctx}/hcf/uploadAd/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="idList" name="idList" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td width="5%" style="text-align:left">
					<label class="control-label">渠道：</label>
				<form:select path="channelId" class="input-xlarge required" style="width:120px;"  id="channel">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${channelList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.channelId}" label="${channel.channelName}"/>
							</c:forEach>
				</form:select>
				</td>
				
		 		<td width="5%" style="text-align:left">
					<label class="control-label">活动类型：</label>
				<form:select path="schemeNo" class="input-xlarge required" style="width:120px;"  id="scheme">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${activitySchemeList}" var="scheme" varStatus="indexStatus">
									<form:option value="${scheme.schemeNo}" label="${scheme.schemeName}"/>
							</c:forEach>
				</form:select>				
				</td>
				
				
				<td width="10%" style="text-align:left">
					<label class="control-label">创建时间：</label>
					<input name="startTime" type="text"  maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${uploadAdVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
					--
					<input name="endTime" type="text"  maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${uploadAdVo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
		 
				</td>
 
 
				<td  width="8%" style="text-align:left" >
	   				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
	   				<input class="btn btn-primary" type="button" value="新增广告" onclick="addVendingAd()"/>
	   				<input id="btnExport" class="btn btn-primary" type="button" value="导出当前页"/>
	   				<input id="qbExport" class="btn btn-primary" type="button" value="导出所有"/>
	   				<!-- <input class="btn btn-primary" type="button" value="批量更新" onclick="batchUpgrade()"/> -->
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
				<th width="8%">渠道</th>
				<th width="8%">活动类型</th>
				<th width="8%">创建时间</th>
				<th width="8%">广告状态</th>
				<th width="8%">操作</th>
 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>
						<input type="checkbox" name="adBox" value="${model.id}">
					</td>
					<td>${bi.count}</td>
					<td>${model.channelName}</td>
					<td>${model.schemeName}</td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
					<c:choose>
							<c:when test="${model.adStatus == 1}">
								进行中
							</c:when>
							<c:when test="${model.adStatus == 2}">
								已关闭
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					 
					<td>
					  <a href="javascript:void(0)"  onclick="load('${model.channelId}','${model.schemeNo}')">编辑 </a>&nbsp;
					  <c:choose>
							<c:when test="${model.adStatus == 1}">
								<a href="javascript:void(0)"  onclick="occlude('${model.channelId}','${model.schemeNo}','${model.adStatus}','关闭成功')">关闭 </a>&nbsp;
							</c:when>
							<c:when test="${model.adStatus == 2}">
								<a href="javascript:void(0)"  onclick="occlude('${model.channelId}','${model.schemeNo}','${model.adStatus}','打开成功')">打开 </a>&nbsp;
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
	
	
	<!--  选择创建新增机器广告模式窗口（Modal）-->
	<div class="modal fade" id="select_createVendingAd" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 700px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true" >
						&times;
					</button>
					<h4 class="modal-title" id="orderLabel">
						广告管理编辑
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include  file="uploadAd.jsp"%>
 
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
			</div>
		</div>
	</div> 
	
</body>
</html>