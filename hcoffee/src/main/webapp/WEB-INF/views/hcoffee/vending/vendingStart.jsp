<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>统计管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
 			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/vendingState/list");
				$("#searchForm").submit();
			});
 			
			$('#select_vending').on('hidden.bs.modal', function () {
				$("#select_vending").css("z-index",'-1');     
			})
			
			$('#select_vending').on('shown.bs.modal', function () {
				$("#select_vending").css("z-index",'1050');
			})
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出售货机运营数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='communityBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			           	
			           	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/vendingState/export");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			$("#qbExport").click(function(){
				top.$.jBox.confirm("确认要导售货机运营数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='communityBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			           	
			           	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/vendingState/qbexport");
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
					$("#searchForm").attr("action","${ctx}/hcf/vendingState/list");
					$("#searchForm").submit();
			     }
			}
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/vendingState/list");
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
	<form:form id="searchForm" modelAttribute="vendingStartVo" action="${ctx}/hcf/vendingState/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
			<td width="3%" style="text-align:left">
					<label class="control-label">售货机编码：</label>
					<form:input path="vendCode" htmlEscape="false" maxlength="16" class="input-medium"/>
				</td> 
		 <td width="3%" style="text-align:left">
					<label class="control-label">机器状态 :</label>
				<form:select path="status" class="input-xlarge required" style="width:120px;"  id="status">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${statusList}" var="tus" varStatus="indexStatus">
									<form:option value="${tus.id}" label="${tus.value}"/>
							</c:forEach>
				</form:select>
			</td>
					       
 
				<td  width="8%" style="text-align:left" >
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
				<th width="1%"> <input type="checkbox" id="all" name="all" onclick="selectAll()"></th>
				<th width="1%">序号</th>
				<th width="6%">售货机编码</th>
				<th width="6%">最后一次心跳时间</th>
				<th width="6%">机器状态</th>
				<th width="6%">备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>
						<input type="checkbox" name="communityBox" value="${model.id}">
					</td>
					<td>${bi.count}</td>
					<td>${model.vendCode}</td>
					<td><fmt:formatDate value="${model.heartTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
					<c:choose>
							<c:when test="${model.status == '0'}">
							 正常
							</c:when>
							<c:when test="${model.status == '1'}">
							 <span style="color:red">不正常</span>
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>${model.remark}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	
</body>
</html>
