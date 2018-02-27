<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
   
	<title>二维码管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function init(){
			$('#add_wechat').on('hidden.bs.modal', function () {
				$("#add_wechat").css("z-index",'-1');     
			})
			
			$('#add_wechat').on('shown.bs.modal', function () {
				$("#add_wechat").css("z-index",'1050');
			})
			
		}
		
		
		$(function(){
			init();
			//新增
			$("#addBut").click(function(){
				console.log("11111111");
				$("#idTemp").val("");
				$("#wechatName").val("");
				$("#wechatNo").val("");
				$("#startTime").val("");
				$("#endTime").val("");
				$("#topUrlTemp").val("");
				$("#bottomUrlTemp").val("");
				$("#logoUrlTemp").val("");
				$("#nameIsOk").val("");
				$("#noIsOk").val("");
				
				
				$("#preview1").html("");
				$("#preview2").html("");
				$("#preview3").html("");
				
				$("#orderLabel").html("新增");
				$("#add_wechat").modal("show");
			});
			
			$("#searchBut").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/wechatMaterial/list");
				$("#searchForm").submit();
			});
		});
		
		
		//编辑
		function editvo(id){
			//console.log(id)
			var datavo={};
			datavo.id=id;
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/wechatMaterial/edit',
				data: JSON.stringify(datavo),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					console.log(data)
					var pojo = data.editPOJO;
					$("#idTemp").val(pojo.id);
					$("#wechatName").val(pojo.wechatName);
					$("#wechatNo").val(pojo.wechatNo);
					$("#startTime").val(data.startTime);
					$("#endTime").val(data.endTime);
					$("#topUrlTemp").val(pojo.topUrl);
					$("#bottomUrlTemp").val(pojo.bottomUrl);
					$("#logoUrlTemp").val(pojo.logoUrl);
					$("#nameIsOk").val("0");
					$("#noIsOk").val("0");
					
					var html1="<img src='${ctxFile}/"+pojo.topUrl+"'>"
					var html2="<img src='${ctxFile}/"+pojo.bottomUrl+"'>"
					var html3="<img src='${ctxFile}/"+pojo.logoUrl+"'>"
					
					$("#preview1").html(html1);
					$("#preview2").html(html2);
					$("#preview3").html(html3);
					$("#orderLabel").html("修改");
					$("#add_wechat").modal("show");
				}
			});
		}
		
		//删除
		function deletevo(id,wechatNo){
			if(confirm("删除成功后，无法找回。确认是否删除？")){
				var datevo={};
				datevo.id=id;
				datevo.wechatNo=wechatNo;
				$.ajax({
					type:"post",
					url:'${ctx}/hcf/wechatMaterial/deleteVo',
					data: JSON.stringify(datevo),
					dataType:"json",
					contentType:"application/json",
					success:function(data){
						if(data.code=="0"){
				       		$.jBox.tip(data.msg);
				         	closeLoading()
			       			window.location.href="${ctx}/hcf/wechatMaterial/list";
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
			$("#searchForm").attr("action","${ctx}/hcf/wechatMaterial/list");
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
<body>
	<form:form id="searchForm" modelAttribute="wechatMaterialVo" action="${ctx}/hcf/wechatMaterial/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	 <table id="tab" class="table table-striped table-bordered table-condensed" >
	 <tr>
				<td width="20%" style="text-align:left">
					<label class="control-label">二维码名称：</label>
					<form:select path="wechatNo" class="input-xlarge required" style="width:120px;"  id="wechatNoId">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${materialList}" var="model" varStatus="indexStatus">
									<form:option value="${model.wechatNo}" label="${model.wechatName}"/>
							</c:forEach>
				</form:select>
				</td>
 
 				<td>
 					<form:input path="searchText" htmlEscape="false"  style="width:300px;" class="input-medium" placeholder="支持素材名称、编号搜索"/>
 				</td>
				<td  width="15%" style="text-align:left" >
	   				<input id="searchBut" class="btn btn-primary" type="button" value="查询" />
	   				<input id="addBut" class="btn btn-primary" type="button" value="新增素材"/>
				</td>
			</tr>
	  
		 </table> 
	</form:form>
	<sys:message content="${message}"/> 
	<table class="table table-striped table-bordered table-condensed">
		<thead>
				<tr>
				<!-- <th width="1%" hidden=""> <input type="checkbox" id="all" hidden="checkbox"  name="all" onclick="selectAll()"></th> --> 
				<th width="8%" style="font-size:16px">二维码名称</th>
				<th width="8%" style="font-size:16px">二维码编码</th>
				<th width="10%" style="font-size:16px">创建时间</th>
				<th width="10%" style="font-size:16px">开始时间</th>
				<th width="10%" style="font-size:16px">结束时间</th>
				<th width="8%">顶部图片</th>
				<th width="8%">底部图片</th>
				<th width="8%">二维码LOGO</th>
				<th width="10%" style="font-size:16px">操作</th>
 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<%--  <td hidden="">
						<input type="checkbox" name="vendCodeBox" hidden="checkbox " value="${model.schemeNo};${model.usrChannel}">
					</td> --%> 
					<td>${model.wechatName}</td>
					<td>${model.wechatNo}</td>
					
					
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${model.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${model.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					
					<td><img src="${ctxFile}/${model.topUrl}"></td>
					<td><img src="${ctxFile}/${model.bottomUrl}"></td>
					<td><img src="${ctxFile}/${model.logoUrl}"></td>
					
					<td>
					 	 <a href="javascript:void(0)"  onclick="editvo('${model.id}')">编辑 </a>&nbsp;
					
					 
					 <a href="javascript:void(0)"  onclick="deletevo('${model.id}','${model.wechatNo}')">删除</a>&nbsp;
				
					</td>
				</tr>
				
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!--  选择创建售货机模式窗口（Modal） -->
	<div class="modal fade" id="add_wechat" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 800px;margin-left: -400px;z-index: -1;">
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
 				<%@ include  file="createWechatMaterial.jsp"%> 
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>

</body>
</html>
