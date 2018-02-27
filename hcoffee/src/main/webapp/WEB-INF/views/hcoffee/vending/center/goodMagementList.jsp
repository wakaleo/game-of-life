<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
 			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/goodMagement/list");
				$("#searchForm").submit();
			});
			
			$("#btnClean").click(function(){
				$("#tab").find("input[type=text]").val("");
				$("#channel").get(0).selectedIndex = 0;
	 
				$("#pageNo").val(1);
			});
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var schemeNoList = new Array();
						var channelList = new Array();
			           	$("input[name='vendCodeBox']:checked").each(function(){
			           		var str = $(this).val().split(";");
			           		schemeNoList.push(str[0]);
			           		channelList.push(str[1]);
			           	});
			           	
			           	$("#schemeNoList").val(schemeNoList.toString());
			           	$("#channelList").val(channelList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/goodMagement/export");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#qbExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var schemeNoList = new Array();
						var channelList = new Array();
			           	$("input[name='vendCodeBox']:checked").each(function(){
			           		var str = $(this).val().split(";");
			           		schemeNoList.push(str[0]);
			           		channelList.push(str[1]);
			           	});
			           	
			           	$("#schemeNoList").val(schemeNoList.toString());
			           	$("#channelList").val(channelList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/goodMagement/qbexport");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
		
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/goodMagement/list");
			$("#searchForm").submit();
        	return false;
        }
				
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/goodMagement/list");
					$("#searchForm").submit();
			     }
			}
		});
	
		function selectAll(){   
			//alert($("#all").attr("checked"));
			if($("#all").attr("checked")=="checked"){
				$("[name='vendCodeBox']").attr("checked",'true');//全选
			}else{
				 $("[name='vendCodeBox']").removeAttr("checked");//取消全选
			}
		};
		
		
		function batchUpgrade(){
            //判断至少写了一项
            var checkedNum = $("input[name='vendCodeBox']:checked").length;
            if(checkedNum<2){
                alert("请至少选择2个进行批量升级!");
                return false;
            }
            if(confirm("确定升级所选售货机?")){
            	var checkedList = new Array();
            	$("input[name='vendCodeBox']:checked").each(function(){
                	checkedList.push($(this).val());
            	});
            	
            	debugger;
            	$("#batchVendCode").val(checkedList.toString());
            	$("#select_batchUpgradeTask").modal("show");
            	
            	/* $.ajax({
	                type:"POST",
	                url:"${ctx}/hcf/vendingMachine/batchUpgrade",
	                data:{"delitems":checkedList.toString()},
	                datatype:"html",
	                success:function(data){
						alert(data.msg);
			       		if(data.code=="0"){
			       			window.location.href="${ctx}/hcf/vendingMachine/list";
			       		}
					}
            	}); */
            }
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
	<form:form id="searchForm" modelAttribute="goodMagementVo" action="${ctx}/hcf/goodMagement/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="schemeNoList" name="schemeNoList" type="hidden" value=""/>
		<input id="channelList" name="channelList" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td width="5%" style="text-align:left">
					<label class="control-label">商品品牌：</label>
				<form:select path="usrChannel" class="input-xlarge required" style="width:120px;"  id="channel">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${CommodityBrand}" var="channel" varStatus="indexStatus">
									<form:option value="${channel.CommodityBrand}" label="${channel.CommodityBrand}"/>
							</c:forEach>
				</form:select>
				</td>
		 		
		 		<td width="5%" style="text-align:left">
					<label class="control-label">活动类型：</label>
				<form:select path="schemeNo" class="input-xlarge required" style="width:120px;"  id="channel">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${activitySchemeList}" var="scheme" varStatus="indexStatus">
									<form:option value="${scheme.schemeNo}" label="${scheme.schemeName}"/>
							</c:forEach>
				</form:select>
				</td>
			
			 
				<td width="8%" style="text-align:left">
					<label class="control-label">创建时间：</label>
					<input name="startTime" type="text"  maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${goodMagementVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
					--
					<input name="endTime" type="text"  maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${goodMagementVo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
		 
				</td>
 
 
				<td  width="8%" style="text-align:left" >
	   				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
	   				<input id="btnExport" class="btn btn-primary" type="button" value="导出当前页"/>
	   				<input id="qbExport" class="btn btn-primary" type="button" value="导出所有"/>
				</td>
			</tr>
		</table>
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%"> <input type="checkbox" id="all" name="all" onclick="selectAll()"></th>
				<th width="1%">序号</th>
				<th width="8%">活动类型</th>
				<th width="8%">渠道</th>
				<th width="5%">礼品数量</th>
				<th width="6%">创建时间</th>
				<th width="6%">活动状态</th>
				<th width="8%">操作</th>
 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>
						<input type="checkbox" name="vendCodeBox" value="${model.schemeNo};${model.usrChannel}">
					</td>
					<td>${bi.count}</td>
					<td>${model.schemeName}</td>
					<td>${model.channelName}</td>
					<td>${model.rewardCnt}</td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
					<c:choose>
							<c:when test="${model.status == '0'}">
								进行中
							</c:when>
							<c:when test="${model.status == '1'}">
								已停止
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					 
					<td>
					  <a  href="${ctx}/hcf/goodMagement/rewardRecordList?schemeNo=${model.schemeNo}&usrChannel=${model.usrChannel}">详情</a>&nbsp; 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	
	
</body>
</html>