<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
	<title>抽奖活动管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function init(){
			$('#add_prize').on('hidden.bs.modal', function () {
				$("#add_prize").css("z-index",'-1');     
			})
			
			$('#add_prize').on('shown.bs.modal', function () {
				$("#add_prize").css("z-index",'1050');
			})
			
			$('#show_dealer').on('hidden.bs.modal', function () {
				$("#show_dealer").css("z-index",'-1');     
			})
			
			$('#show_dealer').on('shown.bs.modal', function () {
				$("#show_dealer").css("z-index",'1050');
			})
		}
		
		
		$(function(){
			init();
			//新增
			$("#addBut").click(function(){
				$("#idWindow").val("");
				$("#activityNoWindow").select2("data",{"id":'',"text":'请选择'});
				$("#vendCodeWindow").select2("data",{"id":'',"text":'请选择'});
				$("#vendCodeWindow").removeAttr('disabled');
				$("#activityNoWindow").removeAttr('disabled');
				
				$("#prizeNameWindow").val("");
				$("#prizeTypeWindow").select2("data",{"id":'',"text":'请选择'});
				$("#shelfWindow").val("");	
				$("#goodsIDWindow").val("");	
				$("#prizeUrlWindow").val("");	
				$("#prizeNumWindow").val("");	
				$("#probabilityWindow").val("");	
				$("#sortWindow").val("");	
				$("#vendCodeTemp").val("");	
				$("#activityNoTemp").val("");	
				$("#isEdit").val("");	
				$("#rowNum").val("");
				
				
				$("#prize_body tr").remove();
				$("#orderLabel").html("新增");
				$("#add_prize").modal("show");
			});
			
			$("#searchBut").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/lottoVend/list");
				$("#searchForm").submit();
			});
		});
		
		//详情
		function showMore(activityNo,vendCode){
			//console.log(id)
			var prizeData={};
			prizeData.activityNo=activityNo;
			prizeData.vendCode=vendCode;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/lottoVend/editPrize',
				data: JSON.stringify(prizeData),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					var prizeList = data.prizeList;
					if(prizeList != ''){
						
						var vendText = prizeList[0].vendCode+"："+prizeList[0].location;
						
						var tr_html="";
						var prizeTypeStr="";
						tr_html +="<div class='form-horizontal' >";
						tr_html +="<div class='pure-g showMorediv'><div class='pure-u-2-3'><div class='control-group'><label class='control-label'>活动名称：</label>";
						tr_html +="<div class='controls'>"+prizeList[0].activityName+"</div></div></div></div>";
						tr_html +="<div class='pure-g showMorediv'><div class='pure-u-2-3'><div class='control-group'><label class='control-label'>活动机器：</label>";
						tr_html +="<div class='controls'>"+vendText+"</div></div></div></div>";
						tr_html +="</div>";
						tr_html +="<h3>奖品列表</h3>";
						tr_html +="<table  class='table table-striped table-bordered'><thead>";
						tr_html +="<tr><td>序号</td><td>奖品名称</td><td>奖品类型</td><td>货道</td><td>商城商品ID</td>";
						tr_html +="<td>奖品图片</td><td>奖品数量</td><td>概率</td><td>排序(升序)</td></tr>";
						for (var i = 0; i < prizeList.length; i++) {
							if (prizeList[i].prizeType==1) {
								prizeTypeStr="货道商品";
							}else if (prizeList[i].prizeType==2) {
								prizeTypeStr="商城商品";
							}
							
							tr_html += "<tr><td  width='5%'>"+(i+1)+ "</td>";
						    tr_html +="<td width='15%'>"+prizeList[i].prizeName+"</td>";
						    tr_html +="<td width='10%'>"+prizeTypeStr+"</td>";
						    tr_html +="<td width='5%'>"+prizeList[i].shelf+"</td>";
						    tr_html +="<td width='10%'>"+prizeList[i].goodsID+"</td>";
						    tr_html +="<td width='15%'><img height='120px' src='${ctxFile}/"+prizeList[i].prizeUrl+"'></td>";
						    tr_html +="<td width='10%'>"+prizeList[i].prizeNum+"</td>";
						    tr_html +="<td width='10%'>"+prizeList[i].probability+"</td>";
						    tr_html +="<td width='10%'>"+prizeList[i].sort+"</td>";
						    tr_html += "</tr></thead>";
						}
						console.log(tr_html);
						$("#baseinfoTable").html(tr_html);
						$("#show_dealer").modal("show");
					 }
				}
			});
		}
		
		//编辑
		function editPrize(activityNo,vendCode){
			//console.log(id)
			var prizeData={};
			prizeData.activityNo=activityNo;
			prizeData.vendCode=vendCode;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/lottoVend/editPrize',
				data: JSON.stringify(prizeData),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					var prizeList = data.prizeList;
					if(prizeList != ''){
						var tr_html = "";
						var hidden_id = "";
						var hidden_prizeName = "";
						var hidden_prizeType = "";
						var hidden_shelf = "";
						var hidden_goodsID = "";
						var hidden_prizeUrl = "";
						var hidden_prizeNum = "";
						var hidden_probability = "";
						var hidden_sort = "";
						var prizeTypeStr="";
						
						/* var imgPreview1 = document.getElementById('preview1'); 
						   imgPreview1.innerHTML = '<img src=${ctxFile}/'+ lottoVendVo.prizeUrl +' />';  */
						/* $("#idWindow").val(lottoVendVo.id); */
						$("#activityNoWindow").select2("data",{"id":prizeList[0].activityNo,"text":prizeList[0].activityName});
						var vendText = prizeList[0].vendCode+"："+prizeList[0].location;
						$("#vendCodeWindow").select2("data",{"id":prizeList[0].vendCode,"text":vendText});
						console.log("aaaaa"+prizeList[0].vendCode);
						$("#vendCodeWindow").attr("disabled","disabled");
						$("#vendCodeTemp").val(prizeList[0].vendCode);
						$("#activityNoTemp").val("");
						$("#activityNoWindow").removeAttr('disabled');
						$("#isEdit").val(1);//编辑窗口，空表示新建窗口
						$("#rowNum").val("");
						
						
						for (var i = 0; i < prizeList.length; i++) {
							if (prizeList[i].prizeType==1) {
								prizeTypeStr="货道商品";
							}else if (prizeList[i].prizeType==2) {
								prizeTypeStr="商城商品";
							}
							hidden_id = i + "id";
							hidden_prizeName= i + "prizeName";
							hidden_prizeType= i + "prizeType";
							hidden_shelf= i + "shelf";
							hidden_goodsID= i + "goodsID";
							hidden_prizeUrl= i + "prizeUrl";
							hidden_prizeNum= i + "prizeNum";
							hidden_probability= i + "probability";
							hidden_sort= i + "sort";
							
							tr_html += "<tr><td  width='5%'>"+(i+1)+ "<input id='"+hidden_id+"' type='hidden' name='id' value='"+prizeList[i].id+"'/></td>";
						    tr_html +="<td width='15%'>"+prizeList[i].prizeName+"<input id='"+hidden_prizeName+"' type='hidden' name='prizeName' value='"+prizeList[i].prizeName+"'/></td>";
						    tr_html +="<td width='10%'>"+prizeTypeStr+"<input id='"+hidden_prizeType+"' type='hidden' name='prizeType' value='"+prizeList[i].prizeType+"'/></td>";
						    tr_html +="<td width='5%'>"+prizeList[i].shelf+"<input id='"+hidden_shelf+"' type='hidden' name='shelf' value='"+prizeList[i].shelf+"'/></td>";
						    tr_html +="<td width='10%'>"+prizeList[i].goodsID+"<input id='"+hidden_goodsID+"' type='hidden' name='goodsID' value='"+prizeList[i].goodsID+"'/></td>";
						    tr_html +="<td width='15%'><img height='120px' src='${ctxFile}/"+prizeList[i].prizeUrl+"'><input id='"+hidden_prizeUrl+"' type='hidden' name='prizeUrl' value='"+prizeList[i].prizeUrl+"'/></td>";
						    tr_html +="<td width='10%'>"+prizeList[i].prizeNum+"<input id='"+hidden_prizeNum+"' type='hidden' name='prizeNum' value='"+prizeList[i].prizeNum+"'/></td>";
						    tr_html +="<td width='10%'>"+prizeList[i].probability+"<input id='"+hidden_probability+"' type='hidden' name='probability' value='"+prizeList[i].probability+"'/></td>";
						    tr_html +="<td width='10%'>"+prizeList[i].sort+"<input id='"+hidden_sort+"' type='hidden' name='sort' value='"+prizeList[i].sort+"'/></td>";
						    tr_html += "<td style='text-align:right'>";
						    tr_html += "<input onclick='editPrizeInfo(this,"+i+")' class='btn btn-primary' type='button' value='修改'/>";
						    tr_html += "<input onclick='deletePrize(this,"+i+")' class='btn btn-primary' type='button' value='删除'/>";
						    tr_html += "</td></tr>";
						}
						$("#prize_body").html(tr_html);
						//totalProbability=0;
					    //for (var i = 0; i < $("#prize_body tr").length; i++) {
					    //	totalProbability=floatAdd(totalProbability,$("#"+i+"probability").val());
						//}
					    
					    //$("#leftProbability").html("剩余概率值："+floatSub(1.0,totalProbability)+"只有当剩余为0才能使用");
					    newForm();
						$("#orderLabel").text("奖品详情");
						$("#imgButton").show();
						$("#imgDiv").show();
						$("#add_prize").modal("show");
					 }
				}
			});
		}
		
		//删除该活动的该机器
		function deleteVendCode(activityNo,vendCode){
			if(confirm("将会删除机器下的所有奖品设置，确认删除吗？")){
				var prizeData={};
				prizeData.activityNo=activityNo;
				prizeData.vendCode=vendCode;
				$.ajax({
					type:"post",
					url:'${ctx}/hcf/lottoVend/deleteVendCode',
					data: JSON.stringify(prizeData),
					dataType:"json",
					contentType:"application/json",
					success:function(data){
						if(data.code=="0"){
				       		$.jBox.tip(data.msg);
				         	closeLoading()
			       			window.location.href="${ctx}/hcf/lottoVend/list";
			       		}else{
			       			$.jBox.tip(data.msg);
			       		}
						//window.location.href="${ctx}/hcf/lottoVend/list";
					}
				});
			}
		}
		
		/* $(function(){
			document.onkeydown = function(e){
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/dealerManagerment/list");
					$("#searchForm").submit();
			     }
			}
		}); */
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/lottoVend/list");
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
	<form:form id="searchForm" modelAttribute="lottoVendVo" action="${ctx}/hcf/lottoVend/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="id" name="id" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td width="20%" style="text-align:left">
					<label class="control-label">活动名称：</label>
					<form:select path="activityNo" class="input-xlarge required" style="width:120px;"  id="activityNo">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${activityList}" var="model" varStatus="indexStatus">
									<form:option value="${model.activityNo }" label="${model.activityName }"/>
							</c:forEach>
					</form:select>
				</td>
		 		
		 		<td width="20%" style="text-align:left">
					<label class="control-label">机器编码：</label>
					<form:select path="vendCode" class="input-xlarge required" style="width:120px;"  id="vendCode">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${vendCodeList}" var="model" varStatus="indexStatus">
									<form:option value="${model.vendCode }" label="${model.vendCode }"/>
							</c:forEach>
					</form:select>
					
				</td>
			
			 
				<%-- <td width="20%" style="text-align:left">
					<label class="control-label">触发条件：</label>
					<form:select path="triggerCondition" class="input-xlarge required" style="width:120px;"  id="triggerCondition">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${lottoTriggerConditionList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
				</td> --%>
 
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
				<th width="10%">活动编号</th>
				<th width="20%">活动名称</th>
				<th width="4%">机器编号</th>
				<!-- <th width="4%">奖品名称</th>
				<th width="4%">奖品类型</th>
				<th width="4%">货道</th>
				<th width="4%">商城商品ID</th>
				<th width="4%">奖品图片</th>
				<th width="6%">奖品数量</th>
				<th width="8%">概率</th>
				<th width="6%">排序</th> -->
				<th width="20%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.activityNo}</td>
					<td>${model.activityName}</td>
					<td>${model.vendCode}</td>
					<%-- <td>${model.prizeName}</td>
					<td>
					<c:choose>
							<c:when test="${model.prizeType == '1'}">
								货道商品
							</c:when>
							<c:when test="${model.prizeType == '2'}">
								商城商品
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>${model.shelf}</td>
					<td>${model.goodsID}</td>
					<td><img src="${ctxFile}/${model.prizeUrl}"></td>
					<td>${model.prizeNum}</td>
					<td>${model.probability}</td>
					<td>${model.sort}</td> --%>
					
					<td>
					  <a  href="javascript:void(0)" onclick="showMore('${model.activityNo}','${model.vendCode}')">详情</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					  <a  href="javascript:void(0)" onclick="editPrize('${model.activityNo}','${model.vendCode}')">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					  <a  href="javascript:void(0)" onclick="deleteVendCode('${model.activityNo}','${model.vendCode}')">删除</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!--  选择创建售货机模式窗口（Modal） -->
	<div class="modal fade" id="add_prize" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 1200px;margin-left: -600px;z-index: -1;">
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
 				<%@ include  file="createLottoVend.jsp"%> 
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!--  选择创建售货机模式窗口（Modal） -->
	<div class="modal fade" id="show_dealer" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 1200px;margin-left: -600px;z-index: -1;">
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