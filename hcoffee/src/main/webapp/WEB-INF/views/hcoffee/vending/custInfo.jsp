<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户查询</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
 			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/vendCustQuery/list");
				$("#searchForm").submit();
			});
 			
 			
			$('#select_vending').on('hidden.bs.modal', function () {
				$("#select_vending").css("z-index",'-1');     
			})
			
			$('#select_vending').on('shown.bs.modal', function () {
				$("#select_vending").css("z-index",'1050');
			})
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户查询数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='communityBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			           	
			           	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/vendCustQuery/export");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			$("#qbExport").click(function(){
				top.$.jBox.confirm("确认要导用户查询数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='communityBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			           	
			           	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/vendCustQuery/qbexport");
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
					$("#searchForm").attr("action","${ctx}/hcf/vendCustQuery/list");
					$("#searchForm").submit();
			     }
			}
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/vendCustQuery/list");
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
<body>
	<form:form id="searchForm" modelAttribute="vendCustInfo" action="${ctx}/hcf/vendCustQuery/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table id="tab" class="table table-striped table-bordered table-condensed" >
			<tr>
			<td  style="text-align:left">
					<label class="control-label">售货机编码：</label>
					<form:input path="vendCode" htmlEscape="false" maxlength="16" class="input-medium"/>
				</td> 
			<td  style="text-align:left">
					<label class="control-label">手机号：</label>
					<form:input path="phoneNo" htmlEscape="false" maxlength="16" class="input-medium"/>
				</td> 

		 <td style="text-align:left">
					<label class="control-label">渠道：</label>
				<form:select path="channelId" class="input-xlarge required" style="width:120px;"  id="channelId">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${channelList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.channelId}" label="${channel.channelName}"/>
							</c:forEach>
				</form:select>
				</td>
				
			
				</td>
				 <td  style="text-align:left">
					<label class="control-label">创建时间：</label>
					<input name="startTime" type="text"  class="input-medium Wdate " value="<fmt:formatDate value="${vendCustInfo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
					--
					<input name="endTime" type="text"   class="input-medium Wdate " value="<fmt:formatDate value="${vendCustInfo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
		 
				</td> 
				<td  style="text-align:left" >
	   				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
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
			<th width="1%"> </th>
				<th width="1%">序号</th>
				<th width="2%">汇理财编码</th>
				<th width="6%">汇联易家编号</th>
				<th width="3%">手机号码</th>
				<th width="2%">售货机编码</th>
				<th width="3%">渠道</th>
				<th width="4%">社区</th>
				<th width="4%">注册时间</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>
						<input type="checkbox" name="communityBox" value="${model.id}">
					</td>
					<td>${bi.count}</td>
					<td>${model.custId}</td>
					<td>${model.hlejId}</td>
					<td>${model.phoneNo}</td>
					<td>${model.vendCode}</td>
					<td>${model.channelName}</td>
					<td>${model.communityName}</td>
					<td><fmt:formatDate value="${model.registDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	
</body>
</html>
