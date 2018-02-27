<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>下发方式详情</title>
<meta name="decorator" content="default" />
</head>
<body>
	<div style="border: 1px solid #08c; margin: 10px;">
		<sys:message content="${message}" />
		<ul class="nav nav-tabs">
			<li><label class="control-label">下发方式详情</label></li>
		</ul>
		<sys:message content="${message}" />
		<table id="contentTable" class="table table-striped table-bordered table-condensed" border="1">
			<tr>
				<td><label class="control-label">下发方式编码：</label></td>
				<td><input type="text" id="distributeWayNum" name="distributeWayNum" readonly="readonly" value="${distributeWay.distributeWayNum}"></td>

				<td><label class="control-label">下发方式：</label></td>
				<td><input type="text" id="distributeWay" name="distributeWay" readonly="readonly" value="${distributeWay.distributeWay }"></td>

				<td><label class="control-label">定义：</label></td>
				<td><input name="distributeWayDesc" type="text" id="distributeWayDesc" readonly="readonly"  value="${distributeWay.distributeWayDesc }" /></td>

				<td><label class="control-label">状态：</label></td>
				<td>
				<c:choose>
					<c:when test="${distributeWay.dataStatus == '0'}">
						<input type="text" id="dataStatus" name="dataStatus" readonly="readonly" value="启用">
					</c:when>
					<c:otherwise><input type="text" id="dataStatus" name="dataStatus" readonly="readonly" value="禁用"></c:otherwise>
				</c:choose>
				</td>
				<td><label class="control-label">平台归属：</label></td>
				<td >
				<c:choose>
					<c:when test="${distributeWay.datasource == '1'}">
						<input type="text" id="datasource" name="datasource" readonly="readonly" value="汇有房">
					</c:when>
					<c:when test="${distributeWay.datasource == '2'}">
						<input type="text" id="datasource" name="datasource" readonly="readonly" value="汇理财">
					</c:when>
					<c:when test="${distributeWay.datasource == '3'}">
						<input type="text" id="datasource" name="datasource" readonly="readonly" value="汇生活">
					</c:when>
				</c:choose>
				</td>
			</tr>
             <tr>
				<td><label class="control-label">创建时间：</label></td>
				<td ><input type="text" id="createTime " readonly="readonly" name="createTime " value="<fmt:formatDate value="${distributeWay.createTime }" pattern="yyyy-MM-dd"/>"></td>
				<td><label class="control-label">修改时间：</label></td>
				<td ><input type="text" id="updateTime " readonly="readonly" name="updateTime " value="<fmt:formatDate value="${distributeWay.updateTime }" pattern="yyyy-MM-dd"/>"></td>
				<td><label class="control-label">创建人：</label></td>
				<td ><input type="text" id="createBy" readonly="readonly" name="createBy" value="${distributeWay.createByStr }"></td>
				<td><label class="control-label">更新人：</label></td>
				<td ><input type="text" id="updateBy" readonly="readonly" name="updateBy " value="${distributeWay.updateByStr }"></td>
			</tr>
			
		</table>
	</div>
	<div class="form-actions" align="center">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</body>

</html>