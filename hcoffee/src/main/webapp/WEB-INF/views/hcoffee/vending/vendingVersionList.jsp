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
				$("#searchForm").attr("action","${ctx}/hcf/vendingVersion/list");
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
				alert("asasa")
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='versionBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			        	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/vendingVersion/export");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#qbExport").click(function(){
				alert("全部")
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='versionBox']:checked").each(function(){
			               	checkedList.push($(this).val());
			           	});
			        	$("#idList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/vendingVersion/qbexport");
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
					$("#searchForm").attr("action","${ctx}/hcf/vendingVersion/list");
					$("#searchForm").submit();
			     }
			}
		});
		
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/vendingVersion/list");
			$("#searchForm").submit();
        	return false;
        }
				
		function edit(version){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/vendingVersion/edit',
				data: version,
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					if(data.id!=''){
						$("#id").val(data.id);
					    $("#versions").val(data.version);
					    $("#versions").attr("disabled","disabled");
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
		function deleted(version){
			if(confirm("确定删除?")){
				$.ajax({
					type:"post",
					url:'${ctx}/hcf/vendingVersion/deleted',
					data: version,
					dataType:"json",
					contentType:"application/json",
					success:function(data){
			       		alert(data.msg);
			       		if(data.code=="0"){
			       			window.location.href="${ctx}/hcf/vendingVersion/list";
			       		}
			       	 }
				})
			}
			
		}
		function create(){
						$("#id").val('');
						$('#appVersionForm')[0].reset(); 
						$("#versions").removeAttr("disabled");
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
	<form:form id="searchForm" modelAttribute="appVersionRecordsVo" action="${ctx}/hcf/vendingMachine/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="idList" name="idList" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 60%">
			<div>
			<label style="padding-right:70px;">
			<a href="${ctx}/hcf/vendingVersion/list" style=" color:#000; text-decoration:underline; " >软件版本</a>
			</label>
			<label style="padding-right:70px;">
			<a  href="${ctx}/hcf/vendingVersion/versionList" style=" color:#000; text-decoration:underline; " >设备版本管理</a>
			</label>
			</div>
			<br>
		
					<label class="control-label">版本名称：</label>
					<form:input path="version" htmlEscape="false" maxlength="16" class="input-medium"/>
					<label class="control-label">版本创建时间：</label>
					<input name="startTime" type="text"  maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${appVersionRecordsVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
					--
					<input name="endTime" type="text"  maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${appVersionRecordsVo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
                  <label style="padding-right:80px;">
                  </label>
               
                <label style="padding-right:400px;">
	   				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
	   				</label>
	   				<label style="padding-right:5px;">
	   				<input class="btn btn-primary" type="button" value="新增售货机版本" onclick="create()"/>
	   				</label>
		</table>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%"> <input type="checkbox" hidden="checkbox" id="all" name="all" onclick="selectAll()"></th>
				<th width="1%">序号</th>
				<th width="8%">版本名称</th>
				<th width="12%">版本内容</th>
				<th width="12%">创建时间</th>
<!-- 				<th width="5%">版本状态</th>
 -->				<th width="18%">下载地址</th>
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
					<td>${model.version}</td>
					<td> ${model.description}</td>
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
					<td> ${model.downloadLink}</td>
					<td>
					 <a href="javascript:void(0)"  onclick="edit('${model.version}')">编辑 </a>&nbsp;
					 <a href="javascript:void(0)"  onclick="deleted('${model.version}')">删除 </a>&nbsp;
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
						售货机版本编辑
					</h4>
				</div>
				<div class="modal-body">	
 				<%@ include  file="addAppVersion.jsp"%> 
 
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
