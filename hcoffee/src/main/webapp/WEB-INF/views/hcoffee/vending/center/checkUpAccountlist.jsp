<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分帐设置</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(function(){
		$('#add_divi').on('hidden.bs.modal', function () {
			$("#add_divi").css("z-index",'-1');     
		})
		
		$('#add_divi').on('shown.bs.modal', function () {
			$("#add_divi").css("z-index",'1050');
		})
		$('#show_divi').on('hidden.bs.modal', function () {
			$("#show_divi").css("z-index",'-1');     
		})
		
		$('#show_divi').on('shown.bs.modal', function () {
			$("#show_divi").css("z-index",'1050');
		})
		
		//查询
		$("#searchBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/financialCenter/checkUpAccountlist");
			$("#searchForm").submit();
		});
		
		//导出
		$("#exceportBut").click(function(){
			top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/hcf/financialCenter/exportC");
					$("#searchForm").submit();
				    
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
		
	});
	
	//查已结算记录
	function queryArCloseAccount(loginName){
		window.location.href = "${ctx}/hcf/financialCenter/alreadyCloseAccountlist?loginName="+loginName+"";
	}
	
	//查看支出记录
    function queryOutAccount(loginName){
    	window.location.href = "${ctx}/hcf/financialCenter/outAccountlist?loginName="+loginName+"";
	}
	
	//查待结算记录
	function waitingAccount(loginName){
		window.location.href = "${ctx}/hcf/financialCenter/waitingCloseAccountlist?loginName="+loginName+"";
	}
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/financialCenter/checkUpAccountlist");
		$("#searchForm").submit();
    	return false;
    }
	
	</script>
	<style>
	
	</style>
</head>
<body>
		<form:form id="searchForm" modelAttribute="checkUpAccountVo" action="${ctx}/hcf/financialCenter/checkUpAccountlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td width="5%" style="text-align: left">
					<label class="control-label">代理商：</label>
					<form:input path="dealerName" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="代理商"/>
				</td>
				<td width="8%" style="text-align: left">
				<label class="control-label">账单日期：</label> 
					<input name="startTime" type="text" maxlength="20" class="input-medium Wdate " value="${startTime }"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
						readonly="readonly" /> -- 
					<input name="endTime" type="text"
						maxlength="20" class="input-medium Wdate " value="${endTime }"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
						readonly="readonly" />
				</td>
				<td  width="15%" style="text-align:left" >
	   				<input id="searchBut" class="btn btn-primary" type="button" value="查询" />
	   				<input id="exceportBut" class="btn btn-primary" type="button" value="导出excel"/>
				</td>
			</tr>
		</table>
	</form:form>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>日期</th>
				<th>代理商</th>
				<th>登录名</th>
				<th>收入（元）</th>
				<th>支出（元） </th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.timeDuan}</td>
					<td>${model.dealerName}</td>
					<td>${model.loginName}</td>
					<td><font color='green'>+${model.incomeMoney}</font></td>
					<td><font color='red'>-${model.expenditureMoney}</td>
					<td>
						<a href="javascript:void(0)" onclick="queryArCloseAccount('${model.loginName}')">查已结算记录</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="queryOutAccount('${model.loginName}')">查看支出记录</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="waitingAccount('${model.loginName}')">查待结算记录</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td>总合计：</td>
				<td></td>
				<td></td>
				<td></td>
				<td><font color='green'>+${sumInComeMoney }</font></td>
				<td><font color='red'>-${sumOutMoney }</font></td>
				<td></td>
			</tr>
		</tfoot>
	</table>
	<div class="pagination">${page}</div>
	<!-- 添加代商分帐模版 -->
	<div class="modal fade" id="add_divi" tabindex="-1" role="dialog" aria-labelledby="diviLabel" aria-hidden="true"  style="width: 888px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content"">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="divi_title">
						新增模版
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include  file="createDivideAccountSettin.jsp"%> 
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!-- 查看代商分帐模版 -->
	<div class="modal fade" id="show_divi" tabindex="-1" role="dialog" aria-labelledby="diviLabel" aria-hidden="true"  style="width: 840px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content"">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title">
						查看模版
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include  file="showDivideAccountSettin.jsp"%> 
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
</body>
</html>
