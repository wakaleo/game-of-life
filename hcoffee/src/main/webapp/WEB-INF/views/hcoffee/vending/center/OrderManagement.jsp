<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商机管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
	
	
		$(document).ready(function() {
			//提交事件
 			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/OrderManagement/list");
				$("#searchForm").submit();
			});
			
 			
			$('#select_vending').on('hidden.bs.modal', function () {
				$("#select_vending").css("z-index",'-1');     
			})
			
			$('#select_vending').on('shown.bs.modal', function () {
				$("#select_vending").css("z-index",'1050');
			})
		});
			
		
		function selectAll(){   
			if($("#all").attr("checked")=="checked"){
				$("[name='communityBox']").attr("checked",'true');//全选
			}else{
				 $("[name='communityBox']").removeAttr("checked");//取消全选
			}
		};
		
		
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/OrderManagement/list");
			$("#searchForm").submit();
        	return false;
        }
		
		
		// 编辑的方法
		function load(id){
		//	alert(id);
			var data = {}; 
			data.id=id;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/LeadManagement/load',
				data:JSON.stringify(data),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					if(data.id!=''){
						$("#id").val(data.id);
						$("#lmName").val(data.lmName);
						 $("#select_vending").modal("show");
					  /*   $("#communityId").val(data.communityId);
					    $("#select_vending").modal("show"); */
					}		       	 	
				}
				
			})
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
<body>
	<form:form id="searchForm" modelAttribute="OrderManagementVo" action="${ctx}/hcf/OrderManagement/list" method="post" class="breadcrumb form-search">
		<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> --%>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="idList" name="idList" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
		
			
			<th width="30%" >删除订单：
				<select name="removeOrder" class="input-xlarge required" style="width:120px;">
						<option value="1">订单正常</option>
						<option value="2">已经删除</option>
						<option value="3">订单关闭</option>
				</select>
			</th>
			
			<th width="30%" >订单状态：
				<select name="orderState" class="input-xlarge required" style="width:120px;">
						
						<option value="1">待发货</option>
						<option value="2">已发货</option>
						<option value="3">未收货</option>
						<option value="4">已收货</option>
						<option value="5">订单完成 </option>
				</select>
			</th>
		
		
			<th width="30%">
				<input type="text" width="300px" name="orderId">
				<input type="button" id="btnSubmit" class="btn btn-primary  width="200px" value="查询">
			</th>
			
			
		</table>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%"> <input type="checkbox" id="all" name="all" onclick="selectAll()"></th>
				<th width="2%">序号</th>
				<th width="8%">订单编号</th>
				<th width="10%">订单时间</th>
				<th width="8%">金额</th>
				<th width="8%">订单状态</th>
				<th width="8%">订单操作</th>
				
				<!-- <th width="8%">创建时间</th>
 				<th width="4%">跟进状态</th> -->
 				
 				<th width="8%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>
						<input type="checkbox" name="communityBox" value="${model.id}">
					</td>
					<td>${bi.count}</td>
					<td>${model.orderId}</td>
					<td><fmt:formatDate value="${model.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					<td>${model.money}</td>
					<%-- <td>${model.orderState}</td>
					<td>${model.removeOrder}</td> --%>
					
					<td >
					<c:choose>
							<c:when test="${model.orderState == 1}">
								待发货
							</c:when>
							<c:when test="${model.orderState == 2}">
								已发货
							</c:when>
							<c:when test="${model.orderState == 3}">
								未收货
							</c:when>
							<c:when test="${model.orderState == 4}">
								已收获
							</c:when>
							<c:when test="${model.orderState == 5}">
								订单完成
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					
					<td>
					<c:choose>
							<c:when test="${model.removeOrder == 1}">
								订单正常
							</c:when>
							<c:when test="${model.removeOrder == 2}">
								删除订单
							</c:when>
							<c:when test="${model.removeOrder == 3}">
								订单关闭
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					
					<td>
						 <a href="javascript:void(0)"  onclick="load('${model.id}')">编辑订单状态 </a>&nbsp;
						
						 <c:choose>
								<c:when test="${model.removeOrder == 1}">
									<a href="javascript:void(0)"  onclick="occlude('${model.id}','${model.removeOrder}','删除成功')"></a>&nbsp;
								</c:when>
								<c:when test="${model.removeOrder == 2}">
									<a href="javascript:void(0)"  onclick="occlude('${model.id}','${model.removeOrder}','恢复成功')"></a>&nbsp;
								</c:when>
						  </c:choose>
					  
					</td> 
					
					
					<%-- 
					<td><fmt:formatDate value="${model.applicationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					<td><fmt:formatDate value="${model.creationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					<td>${model.state}</td>
					<td>
					<c:choose>
							<c:when test="${model.state == 1}">
								已跟进
							</c:when>
							<c:when test="${model.state == 2}">
								待跟进
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					 --%>
					<%-- 
					
					<td>
						 <a href="javascript:void(0)"  onclick="load('${model.id}')">编辑跟进状态 </a>&nbsp;
						 
						 <c:choose>
								<c:when test="${model.state == 1}">
									<a href="javascript:void(0)"  onclick="occlude('${model.id}','${model.state}','删除成功')">已跟进 </a>&nbsp;
								</c:when>
								<c:when test="${model.state == 2}">
									<a href="javascript:void(0)"  onclick="occlude('${model.id}','${model.state}','恢复成功')">待跟进 </a>&nbsp;
								</c:when>
						  </c:choose>
					 
					</td> 
					 --%>
				
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!--  选择商机管理模式窗口（Modal） -->
	<div class="modal fade" id="select_vending" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 700px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="orderLabel">
						订单管理
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include  file="createOrderManagement.jsp"%> 
 
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
</body>
</html>
