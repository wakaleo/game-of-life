<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>银行Logo显示列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function(){
		
		$('#add_logo').on('hidden.bs.modal', function () {
			$("#add_logo").css("z-index",'-1');     
		})
		
		$('#add_logo').on('shown.bs.modal', function () {
			$("#add_logo").css("z-index",'1050');
		})
		
		//查询
		$("#searchBut").click(function(){
			$("#pageNo").val(1);
			$("#searchForm").attr("action","${ctx}/hcf/bankLogo/list");
			$("#searchForm").submit();
		});
		
		//新增
		$("#addBut").click(function(){
			$("#imgButton").show();
			$("#imgDiv").show();
			$("#logoLabel").text("新增");
		    $("#add_logo").modal("show");
		});
	});
	
	//编辑
	function editLogo(id){
		$.ajax({
			type:"post",
			url:'${ctx}/hcf/bankLogo/edit',
			data: id,
			dataType:"json",
			contentType:"application/json",
			success:function(data){
				 if(data.bankLogoVo != ''){
					 var imgPreview1 = document.getElementById('preview1'); 
				     imgPreview1.innerHTML = '<img src=${ctxFile}/'+ data.bankLogoVo.logoUrl +' />'; 
				     $("#id").val(data.bankLogoVo.id);
				     $("#bankCode").select2("data",{"id":data.bankLogoVo.bankCode,"text":data.bankLogoVo.bankName});
				     $("#logoLabel").text("编辑");
				     $("#imgButton").show();
					 $("#imgDiv").show();
					 $("#add_logo").modal("show");
				 }
				        	 	
			}
			
		});
	}
	
	//删除
	function deleteLogo(id){
		$.jBox.confirm("确定要删除吗?","系统提示",function(v,h,f){
			if(v=="ok"){
				$.ajax({
					type:"post",
					url:'${ctx}/hcf/bankLogo/delete',
					data: id,
					dataType:"json",
					contentType:"application/json",
					success:function(data){
						if(data.code == '0'){
							$.jBox.tip(data.msg);
							window.location.href="${ctx}/hcf/bankLogo/list";
						}
						else{
							$.jBox.tip(data.msg);
						}
					}
				});
			    
			}
		},{buttonsFocus:1});
		top.$('.jbox-body .jbox-icon').css('top','55px');
	}
	
	//分页
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/hcf/vendStock/list");
		$("#searchForm").submit();
    	return false;
    }
</script>
<style>
.hide {
	display: block;
}

.show {
	display: none;
}

.grayBar {
	background-color: #efefee
}
</style>
</head>
<body>
	<form:form id="searchForm" modelAttribute="bankLogoVo"
		action="${ctx}/hcf/vendStock/list" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table class="table table-striped table-bordered table-condensed"
			style="width: 100%">
			<tr>
				<td>
					<form:input path="bankName" htmlEscape="false"  style="width:300px;" class="input-medium" placeholder="银行名称"/>
					<input type="button" id="searchBut" class="btn btn-primary"  width="200px" value="查询">
					<input type="button" id="addBut" class="btn btn-primary"  width="200px" value="新增">
				</td>
				
			</tr>
		</table>
	</form:form>
	
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>银行名称</th>
				<th>logo图片</th>
				<th>创建时间 </th>
				<th>创建人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.bankName}</td>
					<td><img alt="" src="${ctxFile}/${model.logoUrl}" style="width: 200px;height: 50px;"/></td>
					<td>${model.createTime}</td>
					<td>${model.createBy}</td>
					<td>
					  <a  href="javascript:void(0)" onclick="editLogo('${model.id}')">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					  <a  href="javascript:void(0)" onclick="deleteLogo('${model.id}')">删除</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!--  新建商品模式窗口（Modal）-->
	<div class="modal fade" id="add_logo" tabindex="-1" role="dialog" aria-labelledby="logoLabel" aria-hidden="true"  style="width: 800px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="logoLabel">
						新增
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include file="createBankLogo.jsp"%>
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
</body>
</html>
