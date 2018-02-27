<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
	<title>活动统计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
 			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/ActivityStatis/rewardRecordList");
				$("#searchForm").submit();
			});
			
			$("#btnClean").click(function(){
				$("#tab").find("input[type=text]").val("");
				$("#channel").get(0).selectedIndex = 0;
	 
				$("#pageNo").val(1);
			});
			
			$("#btnExport").click(function(){
				
				var usrChannel="${usrChannel}";
				var schemeNo="${schemeNo}";
				
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='rewardBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			           	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/ActivityStatis/exportRewardRecord");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			$("#qbExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='rewardBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			           	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/ActivityStatis/qbexportRewardRecord");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/ActivityStatis/rewardRecordList");
			$("#searchForm").submit();
        	return false;
        }
				
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/ActivityStatis/rewardRecordList");
					$("#searchForm").submit();
			     }
			}
		});
	
		function selectAll(){   
			if($("#all").attr("checked")=="checked"){
				$("[name='rewardBox']").attr("checked",'true');//全选
			}else{
				 $("[name='rewardBox']").removeAttr("checked");//取消全选
			}
		};
		
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
</head>
<body>
	<form:form id="searchForm" modelAttribute="rewardRecordVo" action="${ctx}/hcf/ActivityStatis/rewardRecordList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="idList" name="idList" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td  width="5%" style="text-align:left" >
					<input type="button" class="btn btn-primary" name="returnSubmit" value="返回活动统计" onclick=" window.history.back();"/>
				</td>
				
				<td width="6%" style="text-align:left">
					<label class="control-label">活动类型：</label>
				<form:select path="schemeNo" class="input-xlarge required" style="width:120px;"  id="channel">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${activitySchemeList}" var="scheme" varStatus="indexStatus">
									<form:option value="${scheme.schemeNo}" label="${scheme.schemeName}"/>
							</c:forEach>
				</form:select>
				</td>
				
				<td width="6%" style="text-align:left">
					<label class="control-label">渠道：</label>
				<form:select path="usrChannel" class="input-xlarge required" style="width:120px;"  id="channel">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${channelList}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.channelId}" label="${channel.channelName}"/>
							</c:forEach>
				</form:select>
				</td>
		 		
		 		<td width="6%" style="text-align:left">
					<label class="control-label">取货码：</label>
					<form:input path="drawCode" htmlEscape="false" maxlength="16" class="input-medium"/>
				</td>
		 		
				<td width="6%" style="text-align:left">
					<label class="control-label">售货机编码：</label>
					<form:input path="vendcode" htmlEscape="false" maxlength="16" class="input-medium"/>
				</td>
				
				
				<td  width="4%" style="text-align:left" >
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
				<th width="8%">机器所在社区</th>
				<th width="8%">活动机器编码</th>
				<th width="8%">手机号码</th>
				<th width="5%">取货码</th>
				<th width="6%">创建时间</th>
				<th width="5%">礼品编码</th>
				<th width="6%">礼品名称</th>
				<th width="6%">礼品价格</th>
				<th width="6%">出货时间</th>
				<th width="6%">取货码状态</th>
				<th width="7%">取货机器编码</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="details" varStatus="index">
				<tr>
					<td>
						<input type="checkbox" name="rewardBox" value="${details.id}">
					</td>
					<td>${index.count}</td>
					<td>${details.communityName}</td>
					<td>${details.vendcode}</td>
					<td>${details.phoneNo}</td>
					<td>${details.drawCode}</td>
					<td><fmt:formatDate value="${details.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${details.goodsId}</td>
					<td>${details.goodsName}</td>
					<td>${details.price}</td>
					<td><fmt:formatDate value="${details.acquireDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
					<c:choose>
							<c:when test="${details.status == 0}">
								待领取
							</c:when>
							<c:when test="${details.status == 1}">
								领取成功
							</c:when>
							<c:when test="${details.status == 2}">
								领取失败
							</c:when>
							<c:when test="${details.status == 3}">
								取货码失效
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>${details.getVenderId}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

</body>
</html>