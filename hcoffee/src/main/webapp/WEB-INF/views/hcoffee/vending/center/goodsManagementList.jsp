<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>活动统计</title>

<meta name="decorator" content="default" />
<script type="text/javascript">
		$(function() {
			$('#add_goods').on('hidden.bs.modal', function () {
				$("#add_goods").css("z-index",'-1');     
			})
			
			$('#add_goods').on('shown.bs.modal', function () {
				$("#add_goods").css("z-index",'1050');
			})
			$('#show_goods').on('hidden.bs.modal', function () {
				$("#show_goods").css("z-index",'-1');     
			})
			
			$('#show_goods').on('shown.bs.modal', function () {
				$("#show_goods").css("z-index",'1050');
			})
			
			//新增
			$("#addGoodsBut").click(function(){
				$("#goodsName").val("");
			    $("#goodsId").val("");;
			    $("#typeId").select2("data",{"id":'',"text":'请选择'});
			    $("#goodsBrand").val("");
			    $("#priceInto").val("");
			    $("#priceOut").val("");
			    $("#specification").val("");
			    $("#packagesNumber").val("");
			    $("#imgButton").show();
			    $("#imgDiv").show();
			    $("#goodsLabel").text("新建商品");
			    $("#add_goods").modal("show");
			});
			
			//刷新
			$("#refreshBut").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/goodsManagement/list");
				$("#searchForm").submit();
			});
			
			//查询
			$("#searchBut").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/goodsManagement/list");
				$("#searchForm").submit();
			});
			
		});
		
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/goodsManagement/list");
					$("#searchForm").submit();
			     }
			}
		});
	
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/goodsManagement/list");
			$("#searchForm").submit();
        	return false;
        }
		
		//编辑
		function editGoods(id){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/goodsManagement/edit',
				data: JSON.stringify(id),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					 if(data.goodsVo != ''){
						 var imgPreview1 = document.getElementById('preview1'); 
					     imgPreview1.innerHTML = '<img src=${ctxFile}/'+ data.goodsVo.pictureUrl +' />'; 
					     $("#id").val(data.goodsVo.id);
					     $("#goodsName").val(data.goodsVo.goodsName);
					     $("#goodsId").val(data.goodsVo.goodsId);
					     $("#typeIds").select2("data",{"id":data.goodsVo.typeId,"text":data.goodsVo.typeName});
					     $("#goodsBrand").val(data.goodsVo.goodsBrand);
					     $("#priceInto").val(data.goodsVo.priceInto);
					     $("#priceOut").val(data.goodsVo.priceOut);
					     $("#specification").val(data.goodsVo.specification);
					     $("#packagesNumber").val(data.goodsVo.packagesNumber);
					     $("#goodsLabel").text("修改商品");
					     $("#imgButton").show();
						 $("#imgDiv").show();
						 $("#add_goods").modal("show");
					 }
					        	 	
				}
				
			})
		}
		
		//下架
		function goodsNoSale(id,val){
			var data = {};
			data.id = id;
			data.isSale = val;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/goodsManagement/isSale',
				data: JSON.stringify(data),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					$.jBox.tip(data.msg);  
					window.location.href = "${ctx}/hcf/goodsManagement/list";  	 	
				}
				
			})
		}
		
		function goodsTypeManagement(){
			window.location.href = "${ctx}/hcf/goodsTypeManagement/list"; 
		}
		
		
	</script>
<style>
</style>
</head>
</head>
<body>
	<form:form id="searchForm" modelAttribute="goodsVo" action="${ctx}/hcf/goodsManagement/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<table id="tab"
			class="table table-striped table-bordered table-condensed"
			style="width: 100%">
			<tr>
				<td width="20%" style="text-align: left">
					<label class="control-label">商品分类：</label>
					<form:select path="typeId" class="input-xlarge required" style="width:120px;"  id="typeId">
						<form:option value="" label="全部"/>
						<c:forEach items="${goodsTypeList}" var="model" varStatus="indexStatus">
						<form:option value="${model.id }" label="${model.typeName }"/>
						</c:forEach>
					</form:select>
				</td>
				<td>
					<input type="text" width="300px" name="goodsName" placeholder="商品名称">
					<input id="searchBut" class="btn btn-primary" type="button" value="查询" />
					<input id="addGoodsBut" class="btn btn-primary" type="button" value="新建商品" />
					<input id="refreshBut" class="btn btn-primary" type="button" value="刷新" />
					<input class="btn btn-primary" type="button" value="商品分类管理" onclick="goodsTypeManagement()"/>
				</td>
				<td width="20%" style="text-align: left">
					
				</td>
			</tr>
		</table>
	</form:form>

	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%">商品ID</th>
				<th width="4%">商品品牌</th>
				<th width="4%">商品名称</th>
				<th width="6%">成本价</th>
				<th width="6%">零售价</th>
				<th width="6%">商品图片</th>
				<th width="6%">创建时间</th>
				<th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${model.goodsId}</td>
					<td>${model.goodsBrand}</td>
					<td>${model.goodsName}</td>
					<td>${model.priceInto}</td>
					<td>${model.priceOut}</td>
					<td><img alt="" src="${ctxFile}/${model.pictureUrl}" style="width: 40px;height: 40px;"/></td>
					<td>${model.createTime}</td>
					<td>
						<a href="javascript:void(0)" onclick="editGoods(${model.id})">编辑</a>&nbsp; 
						<c:choose>
							<c:when test="${model.isSale == '0'}">
								<a href="javascript:void(0)" onclick="goodsNoSale(${model.id},'1')">上架 </a>&nbsp;
							</c:when>
							<c:when test="${model.isSale == '1'}">
								<a href="javascript:void(0)" onclick="goodsNoSale(${model.id},'0')">下架 </a>&nbsp;
							</c:when>
						</c:choose>
						
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>


	<!--  新建商品模式窗口（Modal）-->
	<div class="modal fade" id="add_goods" tabindex="-1" role="dialog" aria-labelledby="goodsLabel" aria-hidden="true"  style="width: 800px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="goodsLabel">
						新建商品
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include file="createGoods.jsp"%>
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>

	<div class="modal fade" id="show_goods" tabindex="-1" role="dialog"
		aria-labelledby="orderLabel" aria-hidden="true"
		style="width: 800px; margin-left: -400px; z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="orderLabel">查看</h4>
				</div>
				<div class="modal-body" id="goodsinfoTable"></div>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>