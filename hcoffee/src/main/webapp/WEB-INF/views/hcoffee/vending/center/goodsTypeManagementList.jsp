<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	function init(){
		$('#add_goodsType').on('hidden.bs.modal', function () {
			$("#add_goodsType").css("z-index",'-1');     
		})
		
		$('#add_goodsType').on('shown.bs.modal', function () {
			$("#add_goodsType").css("z-index",'1050');
		})
		
	}
	$(function(){
		init();
		//新增
		$("#addBut").click(function(){
			 $("#id").val("");
			 $("#typeName").val("");
			 $("#orderFlag").val("");
		     $("#goodsTypeLabel").text("新增");
		     $("#add_goodsType").modal("show");
		});
		$("#searchBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/goodsTypeManagement/list");
			$("#searchForm").submit();
		});
		
	});
	$(function(){
		document.onkeydown = function(e){ 
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		    	$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/goodsTypeManagement/list");
				$("#searchForm").submit();
		     }
		}
	});
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/goodsTypeManagement/list");
		$("#searchForm").submit();
    	return false;
    }
	
	
	function editGoodsType(id){
		$.ajax({
			type:"post",
			url:'${ctx}/hcf/goodsTypeManagement/edit',
			data: id,
			dataType:"json",
			contentType:"application/json",
			success:function(data){
				if(data.goodsTypeVo != ''){
					$("#id").val(data.goodsTypeVo.id);
					$("#orderFlag").val(data.goodsTypeVo.orderFlag);
					$("#typeName").val(data.goodsTypeVo.typeName);
					$("#goodsTypeLabel").text("编辑");
					$("#add_goodsType").modal("show");
				}		       	 	
			}
			
		})
	}
	function deleteGoodsType(id){
		$.jBox.confirm("确定要删除吗?","系统提示",function(v,h,f){
			if(v=="ok"){
				$.ajax({
					type:"post",
					url:'${ctx}/hcf/goodsTypeManagement/isCanDelete',
					data: id,
					dataType:"json",
					contentType:"application/json",
					success:function(data){
						if(data.code == '1'){
							$.jBox.tip(data.msg);
							return;
						}
						if(data.code == '0'){
							$.ajax({
								type:"post",
								url:'${ctx}/hcf/goodsTypeManagement/delete',
								data: id,
								dataType:"json",
								contentType:"application/json",
								success:function(data){
									if(data.code == '0'){
										$.jBox.tip(data.msg);
										window.location.href="${ctx}/hcf/goodsTypeManagement/list";
									}
									else{
										$.jBox.tip(data.msg);
									}
								}
							});
						}
					}
				})
			    
			}
		},{buttonsFocus:1});
		top.$('.jbox-body .jbox-icon').css('top','55px');
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
	<form:form id="searchForm" modelAttribute="goodsTypeVo" action="${ctx}/hcf/goodsTypeManagement/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="id" name="id" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
 				<td width="150px;">
 					<form:input id="" path="typeName" htmlEscape="false" style="width:150px;" class="input-medium" placeholder="请输入类别名称查询"/>
 					<input id="searchBut" class="btn btn-primary" type="button" value="查询" />&nbsp;&nbsp;
 					<input id="addBut" class="btn btn-primary" type="button" value="新增分类"/>
 				</td>
			</tr>
		</table>
	</form:form>
	<sys:message content=""/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%">排序</th>
				<th width="4%">类别</th>
				<th width="10%">操作</th>
 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${model.orderFlag}</td>
					<td>${model.typeName}</td>
					<td>
					  <a  href="javascript:void(0)" onclick="editGoodsType('${model.id}')">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					  <a  href="javascript:void(0)" onclick="deleteGoodsType('${model.id}')">删除</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!--  选择创建新增机器广告模式窗口（Modal）-->
	<div class="modal fade" id="add_goodsType" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 700px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true" >
						&times;
					</button>
					<h4 class="modal-title" id="goodsTypeLabel">
						新增商品类别
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include  file="createGoodsType.jsp"%>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
			</div>
		</div>
	</div> 
</body>
</html>