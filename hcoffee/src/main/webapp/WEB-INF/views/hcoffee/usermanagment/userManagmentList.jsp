<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#btnExport").click(function(){
			top.$.jBox.confirm("确认要导出活动用户数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					/* var str = document.getElementById("uploadFile").value;
				    if(str.length==0)
				    {
				        alert("请先选择上传文件！");
				        return false;
				    } */
					$("#searchForm").attr("action","${ctx}/usermanagement/userManagement/export");
					$("#searchForm").submit();
				    
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
		$("#btnImport").click(function(){
			$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
		});
	});
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		
		$("#searchForm").attr("action","${ctx}/usermanagement/userManagement/list");
		$("#searchForm").submit();
		
    	return false;
    }
</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/usermanagement/userManagement/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value=" 导    入   "/>
			<a href="${ctx}/usermanagement/userManagement/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/usermanagement/userManagement/list">用户管理列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="userManagement" action="${ctx}/usermanagement/userManagement/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>用户属性：</label>
			<select name="userGroupNum" id="userGroupNum" style="width: 130px">
					<option <c:if test="${searchStr==''}">selected</c:if> value="">全体用户</option>
					<c:forEach var="obj" items="${typeList}" varStatus="i">
						<option <c:if test="${searchStr==obj.userGroupNum}">selected</c:if> value="${obj.userGroupNum}">${obj.userGroupName}</option>
					</c:forEach>
			</select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>用户编码</th>
				<th>用户手机</th>
				<th>用户名称</th>
					<%-- <shiro:hasPermission name="userManagement:userManagement:edit">
						<th>操作</th>
					</shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody><%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
		<c:forEach items="${page.list}" var="date" varStatus="bi">
			<tr>
				<td>${(bi.index+1)+((page.pageNo-1) * page.pageSize)}</td>
				<td>${date.userNum}</td>
				<td>${date.userPhoneNumber}</td>
				<td>${date.notes}</td>
				<%-- <td>
					<c:choose>
						<c:when test="${date.dataStatus == '0'}">启用</c:when>
						<c:otherwise>禁用</c:otherwise>
					</c:choose>
				</td> --%>
				<%-- <shiro:hasPermission name="userManagement:userManagement:edit"><td>
					<c:choose>
						<c:when test="${date.dataStatus == '1'}">
							<a href="${ctx}/usermanagement/userManagement/save?userNum=${date.userNum}&dataStatus=0" onclick="return confirmx('确认要启用该字典吗？', this.href)">启用</a>
						</c:when>
						<c:otherwise>
							<a href="${ctx}/usermanagement/userManagement/save?userNum=${date.userNum}&dataStatus=1" onclick="return confirmx('确认要禁用该字典吗？', this.href)">禁用</a>
						</c:otherwise>
					</c:choose>
    				<a href="${ctx}/usermanagement/userManagement/form?userNum=${date.userNum}">编辑</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>