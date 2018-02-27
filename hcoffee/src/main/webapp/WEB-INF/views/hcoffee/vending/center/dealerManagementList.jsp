<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
	<title>经销商管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function init(){
			$('#add_dealer').on('hidden.bs.modal', function () {
				$("#add_dealer").css("z-index",'-1');     
			})
			
			$('#add_dealer').on('shown.bs.modal', function () {
				$("#add_dealer").css("z-index",'1050');
			})
			
			$('#show_dealer').on('hidden.bs.modal', function () {
				$("#show_dealer").css("z-index",'-1');     
			})
			
			$('#show_dealer').on('shown.bs.modal', function () {
				$("#show_dealer").css("z-index",'1050');
			})
			$('#add_DiviSet').on('hidden.bs.modal', function () {
				$("#add_DiviSet").css("z-index",'-1');     
			})
			
			$('#add_DiviSet').on('shown.bs.modal', function () {
				$("#add_DiviSet").css("z-index",'1050');
			})
		}
		
		//初化设备下拉框
		function initSelect(vendCodeId,locationId,vendCodeV,locationV){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/dealerManagerment/initSelect',
				data:JSON.stringify(new Date()),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					var vendCodes = data.vends;
					for(var i=0;i<vendCodes.length;i++){
						var vendCode = vendCodes[i].vendCode;
						var location = vendCodes[i].location;
						var flag = 	vendCodeV == vendCode ? "true" : "false";
						var optionVendCode = "<option value='"+location+"'>"+vendCode+"</option>";
						$("#"+vendCodeId).append(optionVendCode);
						
						var optionLocation = "<option value='"+location+"'>"+location+"</option>";
						$("#"+locationId).append(optionLocation);
					}
				}
			});
		}
		
		function changeLocation(obj,j){
			var locationId = j+"location";
			$("#"+locationId).empty();
			var optionLocation = "<option value='"+obj.value+"'>"+obj.value+"</option>";
			$("#"+locationId).append(optionLocation);
		}
		
		$(function(){
			init();
			//新增
			$("#addBut").click(function(){
				$("#id").val("");
				$("#loginName").val("");	
				$("#password").val("");	
				$("#password").show();
				$("#password_show").hide();
				$("#mailbox").val("");	
				$("#dealerName").val("");	
				$("#birthday").val("");	
				$("#idCard").val("");	
				$("#bankCarNumber").val("");	
				$("#cellPhone").val("");	
				$("#dealerArea").val("");	
				$("#detailAddress").val("");	
				$("#bankCarInfo").val("");	
				$("#bankCode").select2("data",{"id":'',"text":'请选择'});
				$("#conStatusS").select2("data",{"id":'',"text":'请选择'});
				$("#dealerGradeS").select2("data",{"id":'',"text":'请选择'});
				$("#dealerTypeS").select2("data",{"id":'',"text":'请选择'});
				$("#sex").select2("data",{"id":'',"text":'请选择'});
				$("#parentId").select2("data",{"id":'',"text":'请选择'});
				$("#equipment_body").html("");
				
				$("#companyName").val("");
				$("#zxjy_value").select2("data",{"id":'',"text":'请选择'});
				$("#xyCode").val("");
				$("#div_zxjg").html("");
				$("#div_zjzp").html("");
				
				$("#orderLabel").html("新增");
				$("#add_dealer").modal("show");
			});
			$("#searchBut").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/dealerManagerment/list");
				$("#searchForm").submit();
			});
		});
		
		//编辑
		function edit(loginName){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/dealerManagerment/edit',
				data: loginName,
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					var dealerVo = data.dealerVo;
					var vendCodes = data.vends;
					if(dealerVo.id!=''){
						$("#id").val(dealerVo.id);
					    $("#loginName").val(dealerVo.loginName);
					    $("#password_show").val(dealerVo.password);
					    $("#password").hide();
					    $("#password").val(dealerVo.password);
					    $("#password_show").show();
					    $("#mailbox").val(dealerVo.mailbox);
					    $("#dealerName").val(dealerVo.dealerName);
					    $("#birthday").val(data.birthday);
					    
					    $("#companyName").val(dealerVo.companyName);
					    var zxjy = dealerVo.zxjy;
					    var zxjyText = "";
					    if('1' == zxjy)
					    	zxjyText = "信用良好,建议合作";
					    if('2' == zxjy)
					    	zxjyText = "有信用风险，建议终止合作";
					    $("#zxjy_value").select2("data",{"id":zxjy,"text":zxjyText});
					    $("#bankCode").select2("data",{"id":data.dealerVo.bankCode,"text":data.dealerVo.bankCarInfo});
					    $("#xyCode").val(dealerVo.xyCode);
					    
					    $("#div_zxjg").html("");//清空
					    $("#div_zjzp").html("");//清空
					    //征信查询结果
					    var zxcxjgUrl = dealerVo.zxcxjgUrl;
					    var zjzpUrl = dealerVo.zjzpUrl;
					    
					    var zxjgFileName = dealerVo.zxjgFileName;
					    var zjzpFileName = dealerVo.zjzpFileName;
					    var div_html = "<ul>";
					    if(zxcxjgUrl != undefined){
					    	if(zxcxjgUrl.indexOf(",") != -1){
						    	var zxcxjgUrls = zxcxjgUrl.split(",");
						    	var zxjgFileNames = zxjgFileName.split(",");
						    	for(var i=0;i<zxcxjgUrls.length;i++){
						    		var file_name = zxcxjgUrls[i].substring(zxcxjgUrls[i].lastIndexOf("/")+1,zxcxjgUrls[i].length);
						    		console.log("file_name:==="+file_name);
						    		div_html += "<li>"+zxjgFileNames[i]+"</br><a href='${ctx}/hcf/dealerManagerment/download?filePath="+zxcxjgUrls[i]+"&fileName="+zxjgFileNames[i]+"')>下载</a>&nbsp;&nbsp;<a href=javascript:deleteFile('"+zxcxjgUrls[i]+"')>删除</a></li>";
						    	}
						    }else{
						    	var file_name = zxcxjgUrl.substring(zxcxjgUrl.lastIndexOf("/")+1,zxcxjgUrl.length);
						    	div_html += "<li>"+zxjgFileName+"</br><a href='${ctx}/hcf/dealerManagerment/download?filePath="+zxcxjgUrl+"&fileName='"+zxjgFileName+"'')>下载</a>&nbsp;&nbsp;<a href=javascript:deleteFile('"+zxcxjgUrl+"')>删除</a></li>";
						    }
						    div_html += "</ul>"
						    $("#div_zxjg").html(div_html);
					    }
					    
					    //证件照片
					    if(zjzpUrl != undefined){
					    	var div_html = "<ul>";
						    if(zjzpUrl.indexOf(",") != -1){
						    	var zjzpUrls = zjzpUrl.split(",");
						    	var zjzpFileNames = zjzpFileName.split(",");
						    	for(var i=0;i<zjzpUrls.length;i++){
						    		var file_name = zjzpUrls[i].substring(zjzpUrls[i].lastIndexOf("/")+1,zjzpUrls[i].length);
						    		div_html += "<li>"+zjzpFileNames[i]+"</br><a href='${ctx}/hcf/dealerManagerment/download?filePath="+zjzpUrls[i]+"&fileName="+zjzpFileNames[i]+"'>下载</a>&nbsp;&nbsp;<a href=javascript:deleteFile('"+zjzpUrls[i]+"')>删除</a></li>";
						    	}
						    }else{
						    	var file_name = zjzpUrl.substring(zjzpUrl.lastIndexOf("/")+1,zjzpUrl.length);
						    	div_html += "<li>"+zjzpFileName+"</br><a href='${ctx}/hcf/dealerManagerment/download?filePath="+zjzpUrl+"&fileName="+zjzpFileName+"'>下载</a>&nbsp;&nbsp;<a href=javascript:deleteFile('"+zjzpUrl+"')>删除</a></li>";
						    }
						    div_html += "</ul>"
						    $("#div_zjzp").html(div_html);
					    }
					    
					    var conStatus = dealerVo.conStatus;
					    var conStatusText = "";
					    if('' == conStatus)
					    	conStatusText = "全部";
					    if('1' == conStatus)
					    	conStatusText = "合作中";
					    if('2' == conStatus)
					    	conStatusText = "谈判中";
					    if('3' == conStatus)
					    	conStatusText = "停止合作";
					    $("#conStatusS").select2("data",{"id":conStatus,"text":conStatusText});
					    var dealerGrade = dealerVo.dealerGrade;
					    var dealerGradeText = "";
					    if('' == dealerGrade)
					    	dealerGradeText = "全部";
					    if('1' == dealerGrade)
					    	dealerGradeText = "一级代理";
					    if('2' == dealerGrade)
					    	dealerGradeText = "二级代理";
					    if('3' == dealerGrade)
					    	dealerGradeText = "三级代理";
					    $("#dealerGradeS").select2("data",{"id":dealerGrade,"text":dealerGradeText});
					    if(dealerVo.parentName != ''){
					    	$("#parentId").select2("data",{"id":parentId,"text":dealerVo.parentName});
					    }else{
					    	$("#parentId").select2("data",{"id":'',"text":"请选择"});
					    }
					    $("#idCard").val(dealerVo.idCard);
					    $("#bankCarNumber").val(dealerVo.bankCarNumber);
					    $("#cellPhone").val(dealerVo.cellPhone);
					    var gender = dealerVo.gender;
					    var genderText = gender == '1' ? "男" : "女";
					    $("#sex").select2("data",{"id":gender,"text":genderText});
					    $("#dealerArea").val(dealerVo.dealerArea);
					    var dealerType = dealerVo.dealerType;
					    
					    $(":input[name='dealerType'][value='"+dealerType+"']").attr("checked",true);
					    backData(dealerType);
					    $("#detailAddress").val(dealerVo.detailAddress);
					    $("#bankCarInfo").val(dealerVo.bankCarInfo);
					}
					
					var tr_html = "";
					var vendCodeId_1 = "";
					var locationId_1 = "";
					var hidden_id = "";
					for(var i=0;i<vendCodes.length;i++){
						hidden_id = i + "id"
						vendCodeId_1 = i + "vendCode"; 
						locationId_1 = i + "location";
						tr_html += "<tr><td>"+(i+1)+"</td><td width='30%'><input id='"+hidden_id+"' type='hidden' name='id' value='"+vendCodes[i].id+"'/>";
					    tr_html += "<div class='controls'><select id='"+vendCodeId_1+"' name='vendCode' onchange='locationChange(this,"+i+")' style='width: 220px' class='form-control select2'><option value='请选择'>请选择</option><c:forEach items="${vendCodeList }" var='vend' varStatus='bi'><option value='${vend.location}'>${vend.vendCode }</option></c:forEach></select></div>";
					    tr_html += "</td><td width='30%'>";
					    tr_html += "<div class='controls'><input id='"+locationId_1+"' name='location' readonly='readonly' class='form-control' style='width: 220px' value=''></input></div>";
					    tr_html += "</td>";
					    tr_html += "<td style='text-align:right'><input onclick='editOutRelevance(this,"+vendCodes[i].id+")' class='btn btn-primary' type='button' value='解除关联'/></td></tr>";
					}
					$("#equipment_body").html(tr_html);
					$("#orderLabel").html("修改");
					$("#add_dealer").modal("show");
					for(var i=0;i<vendCodes.length;i++){
						vendCodeId_1 = "#" + i + "vendCode"; 
						locationId_1 = "#" + i + "location";
						$(vendCodeId_1).select2();
						$(vendCodeId_1).select2("data",{"id":vendCodeId_1,"text":vendCodes[i].vendCode});
						$(locationId_1).val(vendCodes[i].location);
					}
					
					/*分帐设置列表*/
					var dvList = data.dvList;
					var html_t = "";
					var mbStr = "";
					for(var i=0;i<dvList.length;i++){
						if(i==0)
							mbStr = "<font color='red'>当前模版</font>";
						else
							mbStr = "历史模版";
						html_t += "<tr><td><a href='javascript:showTemplate("+dvList[i].templateId+")'>"+dvList[i].templateName+"</a></td><td>"+dvList[i].useTimeStr+"</td><td>"+dvList[i].operator+"</td><td>"+mbStr+"</td></tr>";
					}
					$("#fenZhangSz_body").html(html_t);
				}
			})
		}
		
		function backData(dealerType){
			if(dealerType == 1){
				$("#type_name").html("<font size='4' color='red'>*</font>姓名");
				$("#type_brith").html("生日");
				$("#company_name").html("");
				$("#companyName").hide();
				$("#type_email").html("<font size='4' color='red'>*</font>邮箱");
				$("#type_call").html("<font size='4' color='red'>*</font>电话");
				$("#type_card").html("<font size='4' color='red'>*</font>身份证号");
				$("#type_xyCard_tex").hide();
				$("#type_idCard_tex").show();
				$("#zxjy").html("");
				$("#zxjy_tex").hide();
				$("#company_tr").hide();
			}else if(dealerType == 2){
				$("#type_name").html("<font size='4' color='red'>*</font>联系人姓名");
				$("#type_brith").html("开业日期");
				$("#company_name").html("<font size='4' color='red'>*</font>企业名");
				$("#companyName").show();
				$("#type_email").html("<font size='4' color='red'>*</font>联系人邮箱");
				$("#type_call").html("<font size='4' color='red'>*</font>联系人电话");
				$("#type_card").html("<font size='4' color='red'>*</font>统一社会信用代码");
				$("#type_idCard_tex").hide();
				$("#type_xyCard_tex").show();
				$("#zxjy").html("征信建议");
				$("#zxjy_tex").show();
				$("#company_tr").show();
			}
		}
		
		//查看更多
		function showMore(loginName){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/dealerManagerment/showMore',
				data: loginName,
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					var dealerVo = data.dealerVo;
					var vendCodes = data.vends;
				    var conStatus = dealerVo.conStatus;
				    var conStatusText = "";
				    if('0' == conStatus)
				    	conStatusText = "全部";
				    if('1' == conStatus)
				    	conStatusText = "合作中";
				    if('2' == conStatus)
				    	conStatusText = "谈判中";
				    if('3' == conStatus)
				    	conStatusText = "停止合作";
				    var dealerGrade = dealerVo.dealerGrade;
				    var dealerGradeText = "";
				    if('' == dealerGrade)
				    	dealerGradeText = "全部";
				    if('1' == dealerGrade)
				    	dealerGradeText = "一级代理";
				    if('2' == dealerGrade)
				    	dealerGradeText = "二级代理";
				    if('3' == dealerGrade)
				    	dealerGradeText = "三级代理";
				    var gender = dealerVo.gender;
				    var genderText = gender == '1' ? "男" : "女";
				    $("#gender").html(genderText);
				    $("#dealerArea").html(dealerVo.dealerArea);
				    var dealerType = dealerVo.dealerType;
				    var dealerText = "";
				    if('' == dealerType)
				    	dealerText = "全部";
				    if('1' == dealerType)
				    	dealerText = "省级代理";
				    if('2' == dealerType)
				    	dealerText = "市级代理";
					var length = vendCodes.length;
					var html = "";
					var tableHtml = "<table class='table table-striped table-bordered'>";
					tableHtml += "<tr><td>登录名</td><td>"+dealerVo.loginName+"</td><td>密码</td><td>******</td></tr>";
					tableHtml += "<tr><td>邮箱</td><td>"+dealerVo.mailbox+"</td><td>电话</td><td>"+dealerVo.cellPhone+"</td></tr>";
					tableHtml += "<tr><td>姓名</td><td>"+dealerVo.dealerName+"</td><td>性别</td><td>"+genderText+"</td></tr>";
					tableHtml += "<tr><td>生日</td><td>"+data.birthday+"</td><td>地区</td><td>"+dealerVo.dealerArea+"</td></tr>";
					tableHtml += "<tr><td>合作状态</td><td>"+conStatusText+"</td><td>代理类型</td><td>"+dealerText+"</td></tr>";
					tableHtml += "<tr><td>代理级别</td><td>"+dealerGradeText+"</td><td>代理商上级</td><td>"+dealerVo.parentName+"</td></tr>";
					tableHtml += "<tr><td>身份证号</td><td>"+dealerVo.idCard+"</td><td>开户行信息</td><td>"+dealerVo.bankCarInfo+"</td></tr>";
					tableHtml += "<tr><td>银行卡号</td><td>"+dealerVo.bankCarNumber+"</td><td>注册时间</td><td>"+data.createTime+"</td></tr>";
					tableHtml += "<tr><td>最后登录时间</td><td>"+data.lastLoginTime+"</td><td>所有设备数</td><td>"+length+"</td></tr>";
					tableHtml += "<tr><td>详细地址</td><td>"+dealerVo.detailAddress+"</td><td></td><td></td></tr>";
					tableHtml += "<tr><td>设备详情</td><td></td><td></td><td></td></tr>";
					tableHtml += "</table>";
					tableHtml += "<table id='equipment_table' class='table table-striped table-bordered'>";
					tableHtml += "<thead><td>序号</td><td>机器编码</td><td>投放位置</td></thead>";
					for(var i=0;i<vendCodes.length;i++){
						// 回示关联设备信息
					   tableHtml += "<tr><td>"+(i+1)+"</td><td>"+vendCodes[i].vendCode+"</td><td>"+vendCodes[i].location+"</td></tr>";
					}
					tableHtml += "</table>";
					$("#baseinfoTable").html(tableHtml);
					$("#show_dealer").modal("show");
				}
				
			})
		}
		
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/dealerManagerment/list");
					$("#searchForm").submit();
			     }
			}
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/dealerManagerment/list");
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
	<form:form id="searchForm" modelAttribute="dealerManagermentVo" action="${ctx}/hcf/dealerManagerment/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="id" name="id" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
				<td width="20%" style="text-align:left">
					<label class="control-label">代理类型：</label>
					<form:select path="dealerType" class="input-xlarge required" style="width:120px;"  id="dealerType">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${dealerTypeList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
				</td>
		 		
		 		<td width="20%" style="text-align:left">
					<label class="control-label">代理级别：</label>
					<form:select path="dealerGrade" class="input-xlarge required" style="width:120px;"  id="dealerGrade">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${dealerGradeList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
					
				</td>
			
			 
				<td width="20%" style="text-align:left">
					<label class="control-label">合作状态：</label>
					<form:select path="conStatus" class="input-xlarge required" style="width:120px;"  id="conStatus">
						 	<form:option value="" label="全部"/>
						 	<c:forEach items="${conStatusList}" var="model" varStatus="indexStatus">
									<form:option value="${model.value }" label="${model.label }"/>
							</c:forEach>
					</form:select>
				</td>
 
 				<td>
 					<form:input path="searchText" htmlEscape="false"  style="width:300px;" class="input-medium" placeholder="支持登录名，姓名，手机号搜索"/>
 				</td>
 
				<td  width="15%" style="text-align:left" >
	   				<input id="searchBut" class="btn btn-primary" type="button" value="查询" />
	   				<input id="addBut" class="btn btn-primary" type="button" value="新增"/>
				</td>
			</tr>
		</table>
	</form:form>
	
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%">序号</th>
				<th width="4%">登录名</th>
				<th width="4%">姓名</th>
				<th width="6%">代理类型</th>
				<th width="6%">代理级别</th>
				<th width="6%">合作状态</th>
				<th width="6%">电话</th>
				<th width="20%">地址</th>
				<th width="8%">最后登录时间</th>
				<th width="8%">创建时间</th>
				<th width="10%">操作</th>
 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>${bi.count}</td>
					<td>${model.loginName}</td>
					<td>${model.dealerName}</td>
					<td>
					<c:choose>
							<c:when test="${model.dealerType == '1'}">
								个人代理
							</c:when>
							<c:when test="${model.dealerType == '2'}">
								公司代理
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>
					<c:choose>
							<c:when test="${model.dealerGrade == '1'}">
								一级代理
							</c:when>
							<c:when test="${model.dealerGrade == '2'}">
								二级代理
							</c:when>
							<c:when test="${model.dealerGrade == '3'}">
								三级代理
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>
					<c:choose>
							<c:when test="${model.conStatus == '1'}">
								合作中
							</c:when>
							<c:when test="${model.conStatus == '2'}">
								谈判中
							</c:when>
							<c:when test="${model.conStatus == '3'}">
								停止合作
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
						</c:choose>
					</td>
					<td>${model.cellPhone}</td>
					<td>${model.detailAddress}</td>
					<td><fmt:formatDate value="${model.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
					  <a  href="javascript:void(0)" onclick="showMore('${model.loginName}')">查看更多</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					  <a  href="javascript:void(0)" onclick="edit('${model.loginName}')">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp; 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<!--  选择创建售货机模式窗口（Modal） -->
	<div class="modal fade" id="add_dealer" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 800px;margin-left: -400px;z-index: -1;">
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
 				<%@ include  file="createDealer.jsp"%> 
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!--  选择创建售货机模式窗口（Modal） -->
	<div class="modal fade" id="show_dealer" tabindex="-1" role="dialog" aria-labelledby="orderLabel" aria-hidden="true"  style="width: 800px;margin-left: -400px;z-index: -1;">
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