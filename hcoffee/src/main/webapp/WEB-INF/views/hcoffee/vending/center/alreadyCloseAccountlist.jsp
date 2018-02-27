\<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>已结算记录</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(function(){
		$('#add_divi').on('hidden.bs.modal', function () {
			$("#add_divi").css("z-index",'-1');     
		})
		
		$('#add_divi').on('shown.bs.modal', function () {
			$("#add_divi").css("z-index",'1050');
		})
		$('#show_vendSale').on('hidden.bs.modal', function () {
			$("#show_vendSale").css("z-index",'-1');     
		})
		
		$('#show_vendSale').on('shown.bs.modal', function () {
			$("#show_vendSale").css("z-index",'1050');
		})
		
		//查询
		$("#searchBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/financialCenter/alreadyCloseAccountlist");
			$("#searchForm").submit();
		});
		
		//导出
		$("#exceportBut").click(function(){
			top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/hcf/financialCenter/exportA");
					$("#searchForm").submit();
				    
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
	});
	
	//查看详情
	function showInfo(amountType,dealerName,checkUpTimes,incomeMoney,loginName){
		var timeStr = checkUpTimes.split("-");
		var yearMonth = timeStr[0] + "年" + timeStr[1] + "月";
		
		// 本月售货机销售总额
		var monthSaleMoney= "";
		
		// 本月分成比例
		var percentage = "";
		var data = {};
		data.amountStatus = 2;
		if(amountType == 1){//售货机销售分成
			var table_html = "<table id='xq_ta'>";
			table_html += "<tr><td>结算月份:&nbsp;"+yearMonth+"</td><td>币种:&nbsp;人民币</td></tr>";
			table_html += "<tr><td>代理商:&nbsp;"+dealerName+"</td><td> 结算金额（元）："+incomeMoney+"</td></tr>";
			table_html += "<tr><td clospan='2'></td></tr>";
			table_html += "<tr><td clospan='2' class='s_xq'><h1>售货机销售分成详情</h1></td></tr>";
			
			data.loginName = loginName;
			data.dealerName = dealerName;
			data.checkUpTimes = checkUpTimes;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/financialCenter/detail',
				data:JSON.stringify(data),
				dataType:"json",
				contentType:"application/json",
				success:function(re){
					monthSaleMoney = re.saleMoney;
					percentage = re.percentage;
					console.log("--monthSaleMoney--"+monthSaleMoney);
					console.log("--percentage--"+percentage);
					table_html += "<tr><td>本月售货机销售总额（元）："+monthSaleMoney+"</td><td></td></tr>";
					table_html += "<tr><td >本月分成比例："+percentage+" </td><td>本期分成收入（元）："+incomeMoney+"</td></tr>";
					table_html += "</table>";
					$("#showVendSale").html(table_html);
					$("#show_vendSale").modal("show");
					
				}
			});
		}
		if(amountType == 2){
			var table_html = "<table id='xq_ta'>";
			table_html += "<tr><td>结算月份:&nbsp;"+yearMonth+"</td><td>币种:&nbsp;人民币</td></tr>";
			table_html += "<tr><td>代理商:&nbsp;"+dealerName+"</td><td> 结算金额（元）："+incomeMoney+"</td></tr>";
			table_html += "<tr><td clospan='2'></td></tr>";
			table_html += "<tr><td clospan='2' class='s_xq'><h1>机器补贴详情</h1></td></tr>";
			var data = {};
			data.loginName = loginName;
			data.dealerName = dealerName;
			data.checkUpTimes = checkUpTimes;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/financialCenter/detailVending',
				data:JSON.stringify(data),
				dataType:"json",
				contentType:"application/json",
				success:function(re){
					table_html += "<tr><td>总补贴（元）："+re.totalSubsidy+"</td><td>发放补贴期数："+re.periods+"</td></tr>";
					table_html += "<tr><td >已发放补贴（元）："+re.acaMoney+"</td><td>待发放补贴（元）："+re.wcaMoney+"</td></tr>";
					table_html += "</table>";
					$("#showVendSale").html(table_html);
					$("#show_vendSale").modal("show");
					
				}
			});
		}
		
		if(amountType == 3){
			var table_html = "<table id='xq_ta'>";
			table_html += "<tr><td>结算月份:&nbsp;"+yearMonth+"</td><td>币种:&nbsp;人民币</td></tr>";
			table_html += "<tr><td>代理商:&nbsp;"+dealerName+"</td><td> 结算金额（元）："+incomeMoney+"</td></tr>";
			table_html += "<tr><td clospan='2'></td></tr>";
			table_html += "<tr><td clospan='2' class='s_xq'><h1>广告分成详情</h1></td></tr>";
			var data = {};
			data.loginName = loginName;
			data.dealerName = dealerName;
			data.checkUpTimes = checkUpTimes;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/financialCenter/detailAdvertisement',
				data:JSON.stringify(data),
				dataType:"json",
				contentType:"application/json",
				success:function(re){
					table_html += "<tr><td>本月广告分成（元）："+incomeMoney+"</td><td></td></tr>";
					table_html += "<tr><td >总广告支数："+re.advertisNum+"</td><td>已推送机器数（台）："+re.pushNum+"</td></tr>";
					table_html += "</table>";
					$("#showVendSale").html(table_html);
					$("#show_vendSale").modal("show");
					
				}
			});
		}
		
	}
	
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/financialCenter/alreadyCloseAccountlist");
		$("#searchForm").submit();
    	return false;
    }
	
	</script>
	<style>
		#xq_ta{line-height: 30px;}
		#showVendSale .s_xq{text-align:center;}
	</style>
</head>
<body>
		<form:form id="searchForm" modelAttribute="alreadyAccountVo" action="${ctx}/hcf/financialCenter/alreadyCloseAccountlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="loginName" name="loginName" type="hidden" value="${loginName}"/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td width="5%" style="text-align: left">
					<label class="control-label">代理商：</label>
					<form:input path="dealerName" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="代理商"/>
				</td>
				<td width="8%" style="text-align: left">
				<label class="control-label">结算日期：</label> 
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
				<th>收入（元）</th>
				<th>项目</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.checkUpTimes}</td>
					<td>${model.dealerName}</td>
					<td><font color='green'>+${model.incomeMoney}</font></td>
					<td>
						<c:choose>
							<c:when test="${model.amountType == '1'}">
								售货机销售分成
							</c:when>
							<c:when test="${model.amountType == '2'}">
								机器补贴
							</c:when>
							<c:when test="${model.amountType == '3'}">
								广告分成
							</c:when>
						</c:choose>
					</td>
					<td>
						<a href="javascript:void(0)" onclick="showInfo(${model.amountType},'${model.dealerName}','${model.checkUpTimes}',${model.incomeMoney},'${model.loginName}')">查看详情</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td>本页合计：</td>
				<td></td>
				<td></td>
				<td><font color='green'>+${sumInComeMoney }</font></td>
				<td></td>
				<td></td>
			</tr>
		</tfoot>
	</table>
	<div class="pagination">${page}</div>
	<!-- 查看详情 -->
	<div class="modal fade" id="show_vendSale" tabindex="-1" role="dialog" aria-labelledby="diviLabel" aria-hidden="true"  style="width: 500px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content"">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title">
						详情
					</h4>
				</div>
				<div class="modal-body" id="showVendSale">	
 				
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
</body>
</html>
