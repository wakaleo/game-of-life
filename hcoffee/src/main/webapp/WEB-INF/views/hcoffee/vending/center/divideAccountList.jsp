<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
	<title>经销商管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(function(){
		$(":input[name='templateId']:checkbox").click(function(){
			var sum = 0;
			$(":input[name='templateId']:checkbox:checked").each(function(){
				sum++;
			});
			if(sum >1){
				$.jBox.tip('只能选择一个模版!');
				$(this).attr("checked",false);
			}
		});
		//保存
		$("#saveDivi").click(function(){
			//模版id
			var templateId = $(":input[name='templateId']:checkbox:checked").val();
			if(templateId == '' || templateId == undefined){
				$.jBox.tip('请选择一个模版!');
				return;
			}
			//登录名
			var loginName = $("#loginName").val();
			//代理类型
			var dealerType = $("#dealerType").val();
			//姓名
			var dealerName = $("#dealerName").val();
			$.ajax({
		       	 type:'post',
		       	 url:'${ctx}/hcf/dealerManagerment/saveTemplate',
		       	 data:{"templateId":templateId,"loginName":loginName,"dealerType":dealerType,"dealerName":dealerName},
		       	 dataType:"json",
		       	 success:function(data){
		       		 if(data.code == 0){
		       			$.jBox.tip(data.msg);
		       			window.close();
		       		 }else{
		       			$.jBox.tip(data.msg);
		       		 }
		       	 }
		    });
		});
	});
	
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/dealerManagerment/diviSet");
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
	#btnMenu{display:none;}
	</style>
</head>
</head>
<body>
	<form:form id="searchForm" modelAttribute="divideAccountVo" action="${ctx}/hcf/dealerManagerment/diviSet" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="loginName" type="hidden" value="${loginName}"/>
		<input id="dealerName" type="hidden" value="${dealerName}"/>
		<input id="dealerType" type="hidden" value="${dealerType}"/>
		<input id="id" name="id" type="hidden" value=""/>
	</form:form>
	<sys:message content=""/>
				<label>请选择绑定模版</label><br/>
	<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
	<thead>
		<tr>
			<th>选择</th><th>模板名称</th><th>结算方式</th><th>创建日期</th><th>更新日期</th><th>最后操作人</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.list}" var="model" varStatus="bi">
			<tr>
				<td><input type="checkbox" name="templateId" value="${model.id}"></td>
				<td>${model.templateName}</td>
				<td>
					<c:choose>
						<c:when test="${model.settlementWay == '1'}">
							手动结算
						</c:when>
						<c:when test="${model.settlementWay == '2'}">
							自动结算
						</c:when>
					</c:choose>
				</td>
				<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${model.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${model.operator}</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="6" style="text-align: center;">
				<input id="saveDivi" class="btn btn-primary" type="button" value="保存" />
			</td>
		</tr>
	</tfoot>
	</table>
	<div class="pagination">${page}</div>
	
	
	
</body>
</html>