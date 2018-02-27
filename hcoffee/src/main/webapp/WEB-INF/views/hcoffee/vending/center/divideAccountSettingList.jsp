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
		$("#addBut").click(function(){
			clearContent();
			$("#divi_title").html("新增模版");
			$("#add_divi").modal("show");
		});
		//查询
		$("#searchBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/financialCenter/list");
			$("#searchForm").submit();
		});
	});
	
	function setCheckState(val){
		if(val.indexOf(",") != -1){
			var types = val.split(",");
			for(var i=0;i<types.length;i++){
				$(":input[name='probjectType']:checkbox").each(function(){
					if($(this).val() == types[i]){
						$(this).attr("checked",true);
					}
				});
			}
		}else{
			$(":input[name='probjectType']:checkbox").each(function(){
				if($(this).val() == val){
					$(this).attr("checked",true);
				}
			});
		}
		
	}
	
	//查看
	function show(id){
		$.ajax({
			type:"post",
			url:'${ctx}/hcf/financialCenter/show',
			data: JSON.stringify(id),
			dataType:"json",
			contentType:"application/json",
			success:function(data){
				var diVo = data.diVo;
				console.log("--diVo--"+$.parseJSON(diVo));
				var probjectType = diVo.probjectType;
				$(":input[name='id']").val(diVo.id);
				//模版名称
				$(":input[name='templateName']").val(diVo.templateName);
				//结算方式
				$(":input[name='settlementWay'][value='"+diVo.settlementWay+"']").attr("checked",true);
				//项目
				setCheckState(probjectType);
				//参数设置
				
				$(":input[name='fristMonthOne_sale']").val(diVo.fristMonthOne_sale);
				$(":input[name='fristMonthTwo_sale']").val(diVo.fristMonthTwo_sale);
				$(":input[name='secondMonthOne_sale']").val(diVo.secondMonthOne_sale);
				$(":input[name='secondMonthTwo_sale']").val(diVo.secondMonthTwo_sale);
				$(":input[name='threeMonthOne_sale']").val(diVo.threeMonthOne_sale);
				$(":input[name='threeMonthTwo_sale']").val(diVo.threeMonthTwo_sale);
				$(":input[name='fourthMonthOne_sale']").val(diVo.fourthMonthOne_sale);
				$(":input[name='fourthMonthTwo_sale']").val(diVo.fourthMonthTwo_sale);
				
				$(":input[name='saleOne']").val(diVo.saleOne);
				
				$(":input[name='fristSaleValue_1']").val(diVo.fristSaleValue_1);
				$(":input[name='secondSaleValue_1']").val(diVo.secondSaleValue_1);
				$(":input[name='threeSaleValue_1']").val(diVo.threeSaleValue_1);
				$(":input[name='fourthSaleValue_1']").val(diVo.fourthSaleValue_1);
				
				$(":input[name='saleSecond_1']").val(diVo.saleSecond_1);
				$(":input[name='saleSecond_2']").val(diVo.saleSecond_2);
				
				$(":input[name='fristSaleValue_2']").val(diVo.fristSaleValue_2);
				$(":input[name='secondSaleValue_2']").val(diVo.secondSaleValue_2);
				$(":input[name='threeSaleValue_2']").val(diVo.threeSaleValue_2);
				$(":input[name='fourthSaleValue_2']").val(diVo.fourthSaleValue_2);
				
				$(":input[name='saleThree']").val(diVo.saleThree);
				$(":input[name='fristSaleValue_3']").val(diVo.fristSaleValue_3);
				$(":input[name='secondSaleValue_3']").val(diVo.secondSaleValue_3);
				$(":input[name='threeSaleValue_3']").val(diVo.threeSaleValue_3);
				$(":input[name='fourthSaleValue_3']").val(diVo.fourthSaleValue_3);
				/*售货机销售分成 -- 参数设置*/
				/*售货机销售分成 -- 机器补贴--start--*/
				$(":input[name='fristMonthOne_vend']").val(diVo.fristMonthOne_vend);
				$(":input[name='fristMonthTwo_vend']").val(diVo.fristMonthTwo_vend);
				$(":input[name='secondMonthOne_vend']").val(diVo.secondMonthOne_vend);
				$(":input[name='secondMonthTwo_vend']").val(diVo.secondMonthTwo_vend);
				$(":input[name='threeMonthOne_vend']").val(diVo.threeMonthOne_vend);
				$(":input[name='threeMonthTwo_vend']").val(diVo.threeMonthTwo_vend);
				$(":input[name='fourthMonthOne_vend']").val(diVo.fourthMonthOne_vend);
				$(":input[name='fourthMonthTwo_vend']").val(diVo.fourthMonthTwo_vend);
				
				$(":input[name='fristSaleValue_vend']").val(diVo.fristSaleValue_vend);
				$(":input[name='secondSaleValue_vend']").val(diVo.secondSaleValue_vend);
				$(":input[name='threeSaleValue_vend']").val(diVo.threeSaleValue_vend);
				$(":input[name='fourthSaleValue_vend']").val(diVo.fourthSaleValue_vend);
				
				/*售货机销售分成 -- 机器补贴--end--*/
				
				/*售货机销售分成 -- 广告分成--start--*/
				$(":input[name='advertMoney']").val(diVo.advertMoney);
				/*售货机销售分成 -- 广告分成--end--*/
				
				//加载关联该模版的信息
				var list = data.list;
				var tr_html = "";
				var typeStr = "";
				for(var i=0;i<list.length;i++){
					tr_html += "<tr><td>"+(i+1)+"</td><td>"+list[i].loginName+"</td><td>"+list[i].dealerName+"</td>";
					if(list[i].dealerType == 1)
						typeStr = "个人代理";
					if(list[i].dealerType == 2)
						typeStr = "公司代理";
					tr_html += "<td>"+typeStr+"</td><td>"+list[i].useTimeStr+"</td>";
				}
				$("#gl_list").html(tr_html);
				$("#show_divi").modal("show");
			}
		});
	}
	
	//编辑
	function edit(id){
		$.ajax({
			type:"post",
			url:'${ctx}/hcf/financialCenter/edit',
			data: JSON.stringify(id),
			dataType:"json",
			contentType:"application/json",
			success:function(data){
				var diVo = data.diVo;
				console.log("--diVo--"+$.parseJSON(diVo));
				var probjectType = diVo.probjectType;
				$(":input[name='id']").val(diVo.id);
				//模版名称
				$(":input[name='templateName']").val(diVo.templateName);
				//结算方式
				$(":input[name='settlementWay'][value='"+diVo.settlementWay+"']").attr("checked",true);
				//项目
				setCheckState(probjectType);
				//参数设置
				
				$(":input[name='fristMonthOne_sale']").val(diVo.fristMonthOne_sale);
				$(":input[name='fristMonthTwo_sale']").val(diVo.fristMonthTwo_sale);
				$(":input[name='secondMonthOne_sale']").val(diVo.secondMonthOne_sale);
				$(":input[name='secondMonthTwo_sale']").val(diVo.secondMonthTwo_sale);
				$(":input[name='threeMonthOne_sale']").val(diVo.threeMonthOne_sale);
				$(":input[name='threeMonthTwo_sale']").val(diVo.threeMonthTwo_sale);
				$(":input[name='fourthMonthOne_sale']").val(diVo.fourthMonthOne_sale);
				$(":input[name='fourthMonthTwo_sale']").val(diVo.fourthMonthTwo_sale);
				
				$(":input[name='saleOne']").val(diVo.saleOne);
				
				$(":input[name='fristSaleValue_1']").val(diVo.fristSaleValue_1);
				$(":input[name='secondSaleValue_1']").val(diVo.secondSaleValue_1);
				$(":input[name='threeSaleValue_1']").val(diVo.threeSaleValue_1);
				$(":input[name='fourthSaleValue_1']").val(diVo.fourthSaleValue_1);
				
				$(":input[name='saleSecond_1']").val(diVo.saleSecond_1);
				$(":input[name='saleSecond_2']").val(diVo.saleSecond_2);
				
				$(":input[name='fristSaleValue_2']").val(diVo.fristSaleValue_2);
				$(":input[name='secondSaleValue_2']").val(diVo.secondSaleValue_2);
				$(":input[name='threeSaleValue_2']").val(diVo.threeSaleValue_2);
				$(":input[name='fourthSaleValue_2']").val(diVo.fourthSaleValue_2);
				
				$(":input[name='saleThree']").val(diVo.saleThree);
				$(":input[name='fristSaleValue_3']").val(diVo.fristSaleValue_3);
				$(":input[name='secondSaleValue_3']").val(diVo.secondSaleValue_3);
				$(":input[name='threeSaleValue_3']").val(diVo.threeSaleValue_3);
				$(":input[name='fourthSaleValue_3']").val(diVo.fourthSaleValue_3);
				/*售货机销售分成 -- 参数设置*/
				/*售货机销售分成 -- 机器补贴--start--*/
				$(":input[name='fristMonthOne_vend']").val(diVo.fristMonthOne_vend);
				$(":input[name='fristMonthTwo_vend']").val(diVo.fristMonthTwo_vend);
				$(":input[name='secondMonthOne_vend']").val(diVo.secondMonthOne_vend);
				$(":input[name='secondMonthTwo_vend']").val(diVo.secondMonthTwo_vend);
				$(":input[name='threeMonthOne_vend']").val(diVo.threeMonthOne_vend);
				$(":input[name='threeMonthTwo_vend']").val(diVo.threeMonthTwo_vend);
				$(":input[name='fourthMonthOne_vend']").val(diVo.fourthMonthOne_vend);
				$(":input[name='fourthMonthTwo_vend']").val(diVo.fourthMonthTwo_vend);
				
				$(":input[name='fristSaleValue_vend']").val(diVo.fristSaleValue_vend);
				$(":input[name='secondSaleValue_vend']").val(diVo.secondSaleValue_vend);
				$(":input[name='threeSaleValue_vend']").val(diVo.threeSaleValue_vend);
				$(":input[name='fourthSaleValue_vend']").val(diVo.fourthSaleValue_vend);
				
				/*售货机销售分成 -- 机器补贴--end--*/
				
				/*售货机销售分成 -- 广告分成--start--*/
				$(":input[name='advertMoney']").val(diVo.advertMoney);
				/*售货机销售分成 -- 广告分成--end--*/
				
				$("#divi_title").html("编辑模版");
				
				if(diVo.settlementWay == 1){
					$("#parm_div").hide();
					$("#data_div").hide();
				}
				
				$("#add_divi").modal("show");
			}
		});
	}
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/financialCenter/list");
		$("#searchForm").submit();
    	return false;
    }
	
	</script>
	<style>
	
	</style>
</head>
<body>
		<form:form id="searchForm" modelAttribute="divideAccountVo" action="${ctx}/hcf/financialCenter/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td width="5%" style="text-align: left">
					<label class="control-label">模板名称：</label>
					<form:input path="templateName" htmlEscape="false"  style="width:120px;" class="input-medium" placeholder="模板名称"/>
				</td>
				<td width="8%" style="text-align: left">
				<label class="control-label">创建时间：</label> 
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
	   				<input id="addBut" class="btn btn-primary" type="button" value="新增模版"/>
				</td>
			</tr>
		</table>
	</form:form>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>模板名称</th>
				<th>结算方式</th>
				<th>创建日期</th>
				<th>更新日期 </th>
				<th>最后操作人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.templateName}</td>
					<td>
						<c:choose>
							<c:when test="${model.settlementWay == '1'}">
								人工结算
							</c:when>
							<c:when test="${model.settlementWay == '2'}">
								自动结算
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${model.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${model.operator}</td>
					<td>
					  <a  href="javascript:void(0)" onclick="show(${model.id})">查看</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					  <a  href="javascript:void(0)" onclick="edit(${model.id})">分账系数设置</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					</td>
				</tr>
			</c:forEach>
		</tbody>
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
