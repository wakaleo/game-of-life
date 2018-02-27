<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
	<title>抽奖活动管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function init(){
			$('#add_activity').on('hidden.bs.modal', function () {
				$("#add_activity").css("z-index",'-1');     
			})
			
			$('#add_activity').on('shown.bs.modal', function () {
				$("#add_activity").css("z-index",'1050');
			})
			
			$('#show_dealer').on('hidden.bs.modal', function () {
				$("#show_dealer").css("z-index",'-1');     
			})
			
			$('#show_dealer').on('shown.bs.modal', function () {
				$("#show_dealer").css("z-index",'1050');
			})
		}
		
		
		/* function changeLocation(obj,j){
			var locationId = j+"location";
			$("#"+locationId).empty();
			var optionLocation = "<option value='"+obj.value+"'>"+obj.value+"</option>";
			$("#"+locationId).append(optionLocation);
		} */
		
		$(function(){
			init();
			//新增
			$("#addBut").click(function(){
				$("#idWindow").val("");
				$("#activityNoWindow").val("");
				$("#activityNoWindow").removeAttr("disabled");
				$("#activityNameWindow").val("");	
				$("#lottoWayWindow").select2("data",{"id":'',"text":'请选择'});
				$("#lottoWayWindow").select2("data",{"id":'',"text":'请选择'});
				$("#lottoTriggerConditionWindow").select2("data",{"id":'',"text":'请选择'});
				$("#startTimeWindow").val("");
				$("#endTimeWindow").val("");
				$("#activityDescWindow").val("");
				$("#activityNameTemp").val("");
				$("#activityNoTemp").val("");
				
				$("#activityNameMsg").remove();
				$("#activityNoMsg").remove();
				
				$("#orderLabel").html("新增");
				$("#add_activity").modal("show");
			});
			$("#searchBut").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/lottoActivity/list");
				$("#searchForm").submit();
			});
		});
		
		//编辑
		function edit(activityNo){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/lottoActivity/edit',
				data: activityNo,
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					var lottoActivityVo = data.lottoActivityVo;
					if(lottoActivityVo.id!=''){
						$("#idWindow").val(lottoActivityVo.id);
						$("#activityNoWindow").val(lottoActivityVo.activityNo);
						$("#activityNoWindow").attr("disabled","disabled");
						$("#activityNameWindow").val(lottoActivityVo.activityName);
						
					    var lottoWay = lottoActivityVo.lottoWay;
					    var lottoWayText = "";
					    if('1' == lottoWay)
					    	lottoWayText = "转盘";
					    if('2' == lottoWay)
					    	lottoWayText = "投票";
					    $("#lottoWayWindow").select2("data",{"id":lottoWay,"text":lottoWayText});
						
					    var triggerCondition = lottoActivityVo.triggerCondition;
					    var triggerConditionText = "";
					    if('1' == triggerCondition)
					    	triggerConditionText = "第三方指令";
					    $("#lottoTriggerConditionWindow").select2("data",{"id":triggerCondition,"text":triggerConditionText});
						
						
					    $("#startTimeWindow").val(data.startTime);
					    $("#endTimeWindow").val(data.endTime);
					    $("#activityDescWindow").val(lottoActivityVo.activityDesc);
					    $("#activityNameTemp").val(lottoActivityVo.activityName);
					    $("#activityNoTemp").val(lottoActivityVo.activityNo);
					}
					$("#orderLabel").html("修改");
					$("#add_activity").modal("show");
				}
			})
		}
		
		//查看更多
		function showMore(activityNo){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/lottoActivity/showMore',
				data: activityNo,
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					var lottoActivityVo = data.lottoActivityVo;
					var vendCodes = data.vends;
					var startTime = data.startTime;
					var endTime = data.endTime;
					var createTime = data.createTime;
					
				    var activityStatus = lottoActivityVo.activityStatus;
				    var activityStatusText = "";
				    if('1' == activityStatus)
				    	activityStatusText = "未进行";
				    if('2' == activityStatus)
				    	activityStatusText = "进行中";
				    if('3' == activityStatus)
				    	activityStatusText = "已结束";
				    var lottoWay = lottoActivityVo.lottoWay;
				    var lottoWayText = "";
				    if('1' == lottoWay)
				    	lottoWayText = "转盘";
				    if('2' == lottoWay)
				    	lottoWayText = "投票";
				    
				    var triggerCondition = lottoActivityVo.triggerCondition;
				    var triggerConditionText = "";
				    if('1' == triggerCondition)
				    	triggerConditionText = "第三方指令";
				    
				    
				    //var activityName = lottoActivityVo.activityName;
				    //var activityNo = lottoActivityVo.activityNo;
				    //var createTime = lottoActivityVo.createTime;
				    //var drawNum = lottoActivityVo.drawNum;
				    //var winnerNum = lottoActivityVo.winnerNum;
				    //var startTime = lottoActivityVo.startTime;
				    //var endTime = lottoActivityVo.endTime;
				    //var activityDesc = lottoActivityVo.activityDesc;
				    ///////////
					var html = "";
					var tableHtml = "<table class='table table-striped table-bordered'>";
					tableHtml += "<tr><td>活动名称</td><td colspan='3'>"+lottoActivityVo.activityName+"</td></tr>";
					tableHtml += "<tr><td>ID</td><td>"+lottoActivityVo.activityNo+"</td><td>创建时间</td><td>"+createTime+"</td></tr>";
					tableHtml += "<tr><td>开始时间</td><td>"+startTime+"</td><td>结束时间</td><td>"+endTime+"</td></tr>";
					tableHtml += "<tr><td>抽奖次数</td><td>"+lottoActivityVo.drawNum+"</td><td>中奖次数</td><td>"+lottoActivityVo.winnerNum+"</td></tr>";
					tableHtml += "<tr><td>抽奖方式</td><td>"+lottoWayText+"</td><td>触发条件</td><td>"+triggerConditionText+"</td></tr>";
					tableHtml += "<tr><td>活动状态</td><td>"+activityStatusText+"</td><td></td><td></td></tr>";
					tableHtml += "<tr><td>活动说明</td><td colspan='3'>"+lottoActivityVo.activityDesc+"</td></tr>";
					tableHtml += "</table>";
					$("#baseinfoTable").html(tableHtml);
					$("#show_dealer").modal("show");
				}
				
			})
		}
		
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/dealerManagerment/list");
					$("#searchForm").submit();
			     }
			}
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/dealerManagerment/list");
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
</head>
<body>
	<form:form id="searchForm" modelAttribute="lottoActivityVo" action="${ctx}/hcf/lottoActivity/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="id" name="id" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td width="20%" style="text-align:left">
					<label class="control-label">活动状态：</label>
					<form:select path="activityStatus" class="input-xlarge required" style="width:120px;"  id="activityStatus">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${lottoActivityStatusList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
				</td>
		 		
		 		<td width="20%" style="text-align:left">
					<label class="control-label">抽奖方式：</label>
					<form:select path="lottoWay" class="input-xlarge required" style="width:120px;"  id="lottoWay">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${lottoWayList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
					
				</td>
			
			 
				<td width="20%" style="text-align:left">
					<label class="control-label">触发条件：</label>
					<form:select path="triggerCondition" class="input-xlarge required" style="width:120px;"  id="triggerCondition">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${lottoTriggerConditionList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
				</td>
 
 				<td>
 					<form:input path="activityName" htmlEscape="false"  style="width:300px;" class="input-medium" placeholder="支持活动名搜索"/>
 				</td>
 
				<td  width="15%" style="text-align:left" >
	   				<input id="searchBut" class="btn btn-primary" type="button" value="查询" />
	   				<input id="addBut" class="btn btn-primary" type="button" value="新增"/>
				</td>
			</tr>
		</table>
	</form:form>
	<sys:message content=""/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%">序号</th>
				<th width="25%">活动名称</th>
				<th width="4%">活动编号</th>
				<th width="6%">活动状态</th>
				<th width="8%">创建时间</th>
				<th width="6%">抽奖次数</th>
				<th width="6%">中奖人数</th>
				<th width="20%">操作</th>
 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.activityName}</td>
					<td>${model.activityNo}</td>
					<td>
					<c:choose>
							<c:when test="${model.activityStatus == '1'}">
								未进行
							</c:when>
							<c:when test="${model.activityStatus == '2'}">
								进行中
							</c:when>
							<c:when test="${model.activityStatus == '3'}">
								已结束
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${model.drawNum}</td>
					<td>${model.winnerNum}</td>
					<td>
					  <a  href="javascript:void(0)" onclick="showMore('${model.activityNo}')">查看更多</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					  <a  href="javascript:void(0)" onclick="edit('${model.activityNo}')">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!--  选择创建售货机模式窗口（Modal） -->
	<div class="modal fade" id="add_activity" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 800px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="orderLabel">
						新增
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include  file="createLottoActivity.jsp"%> 
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!--  选择创建售货机模式窗口（Modal） -->
	<div class="modal fade" id="show_dealer" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 800px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="orderLabel">
						查看
					</h4>
				</div>
				<div class="modal-body" id="baseinfoTable">	
 				<%-- <%@ include  file="createDealer.jsp"%>  --%>
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
</body>
</html>