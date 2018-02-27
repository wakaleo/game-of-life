<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>售货机管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
 			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/vendStore/list");
				$("#searchForm").submit();
			});
			
			$("#btnClean").click(function(){
				$("#tab").find("input[type=text]").val("");
				$("#channel").get(0).selectedIndex = 0;
	 
				$("#pageNo").val(1);
			});
			
			$('#create_appVersion').on('hidden.bs.modal', function () {
				$("#create_appVersion").css("z-index",'-1');     
			})
			
			$('#create_appVersion').on('shown.bs.modal', function () {
				$("#create_appVersion").css("z-index",'1050');
			})
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='versionBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			        	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/vendStore/export");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#qbExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='versionBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			        	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/vendStore/qbexport");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
		
			
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/vendStore/list");
					$("#searchForm").submit();
			     }
			}
		});
		
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/vendStore/list");
			$("#searchForm").submit();
        	return false;
        }
				
		function edit(id){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/vendStore/edit',
				data: id,
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					if(data.id!=''){
						$("#id").val(data.id);
					    //$("#vendCode").val(data.vendCode);
					    $("#vendCodes").val(data.vendCode);
					    $("#shelf").val(data.shelf);
					    $("#goodsNames").val(data.goodsName);
					    $("#storeGoodsId").val(data.storeGoodsId);
					    $("#storeGoodsLink").val(data.storeGoodsLink);
					    $("#amount").val(data.amount);
					    $("#goodsID").val(data.goodsID);
					    /* if(data.status == 0){
					    	$("#status").select2("data",{"id":data.status,"text":"使用中"});
					    }else{
					    	$("#status").select2("data",{"id":data.status,"text":"停止使用"});
					    } */
					    
					    $("#description").val(data.description);
					    $("#create_appVersion").modal("show");
					}		       	 	
				}
				
			})
		}
		function deleted(id){
			if(confirm("确定删除?")){
				$.ajax({
					type:"post",
					url:'${ctx}/hcf/vendStore/deleted',
					data: id,
					dataType:"json",
					contentType:"application/json",
					success:function(data){
			       		alert(data.msg);
			       		if(data.code=="0"){
			       			window.location.href="${ctx}/hcf/vendStore/list";
			       		}
			       	 }
				})
			}
			
		}
		function create(){
						$("#id").val('');
						$('#communityForm')[0].reset(); 
					    $("#create_appVersion").modal("show");
  
		}
		
		function selectAll(){   
			//alert($("#all").attr("checked"));
			if($("#all").attr("checked")=="checked"){
				$("[name='versionBox']").attr("checked",'true');//全选
			}else{
				 $("[name='versionBox']").removeAttr("checked");//取消全选
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
	
	div .a {float:left;}
	</style>
</head>
<body>
	<form:form id="searchForm" modelAttribute="vendStoreVo" action="${ctx}/hcf/vendStore/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="idList" name="idList" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			  <tr>
			  	<td width="20%" colspan="2">
			  		<label class="control-label">商品名称：</label>
					<form:input path="goodsName" htmlEscape="false" maxlength="16" class="input-medium"/>
					<label class="control-label">机器编码：</label>
					<form:input path="vendCode" htmlEscape="false" maxlength="16" class="input-medium"/>
			  	</td>
			  	<td width="20%">
			  		<label class="control-label">创建时间：</label>
					<input name="startTime" type="text"  maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${vendStoreVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
					--
					<input name="endTime" type="text"  maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${vendStoreVo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
                  <label style="padding-right:80px;">
                  </label>
			  	</td>
			  	
			  </tr>
			  <tr>
			  	<td width="40%" colspan="4">
			  		<label style="padding-right:400px;">
	   				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
	   				<input id="btnExport" class="btn btn-primary" type="button" value="导出当前页"/>
	   				<input id="qbExport" class="btn btn-primary" type="button" value="导出所有"/>
	   				<input class="btn btn-primary" type="button" value="新增" onclick="create()"/>
	   				</label>
			  	</td>
			  </tr>      
               
		</table>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%"> <input type="checkbox" hidden="checkbox" id="all" name="all" onclick="selectAll()"></th>
				<th width="1%">序号</th>
				<th width="8%">机器编码</th>
				<th width="8%">货道</th>
				<th width="8%">贩卖机商品ID</th> 
				<th width="8%">商品名称</th>
				<th width="8%">商品价格</th>
				<th width="8%">商城商品ID</th>
				<th width="8%">商品链接</th>
				<th width="8%">创建时间</th>
 				<th width="12%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>
						<input type="checkbox" name="versionBox" hidden="versionBox" value="${model.id}">
					</td>
					<td>${bi.count}</td>
					<td>${model.vendCode}</td>
					<td> ${model.shelf}</td>
					<td> ${model.goodsID}</td> 
					<td> ${model.goodsName}</td> 	
					<td> ${model.amount}</td>
					<td> ${model.storeGoodsId}</td>
					<td> ${model.storeGoodsLink}</td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<%-- <td>
					<c:choose>
							<c:when test="${model.status == 0}">
								使用中
							</c:when>
							<c:when test="${model.status == 1}">
								停止使用
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td> --%>
					<td>
					 <a href="javascript:void(0)"  onclick="edit('${model.id}')">编辑 </a>&nbsp;
					 <a href="javascript:void(0)"  onclick="deleted('${model.id}')">删除 </a>&nbsp;
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>       
	<div class="pagination">${page}</div>
	
	<!--  选择创建售货机模式窗口（Modal） -->
	<div class="modal fade" id="create_appVersion" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 700px;margin-left: -400px;z-index: -1;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="orderLabel">
						售货机商城管理
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include  file="vendStore.jsp"%> 
 
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
