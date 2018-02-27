<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
 
<html>
<head>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<title>活动统计</title>
    
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
 			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#searchForm").attr("action","${ctx}/hcf/vendingAdStatistic/list");
				$("#searchForm").submit();
			});
			
			$("#btnClean").click(function(){
				$("#tab").find("input[type=text]").val("");
				$("#channel").get(0).selectedIndex = 0;
	 
				$("#pageNo").val(1);
			});
			
			
			$('#select_createVendingAd').on('hidden.bs.modal', function () {
				$("#select_createVendingAd").css("z-index",'-1');
			})
			
			$('#select_createVendingAd').on('shown.bs.modal', function () {
				$("#select_createVendingAd").css("z-index",'1050');
			})
			
			$('#select_batchAddVendingAd').on('hidden.bs.modal', function () {
				$("#select_batchAddVendingAd").css("z-index",'-1');
			})
			
			$('#select_batchAddVendingAd').on('shown.bs.modal', function () {
				$("#select_batchAddVendingAd").css("z-index",'1050');
			})
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='vendCodeBox']:checked").each(function(){
			               	checkedList.push("'"+$(this).val()+"'");
			           	});
			           	$("#vendCodeList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/vendingAdStatistic/exports");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#qbExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						var checkedList = new Array();
			           	$("input[name='vendCodeBox']:checked").each(function(){
			               	checkedList.push("'"+$(this).val()+"'");
			           	});
			           	$("#vendCodeList").val(checkedList.toString());
						$("#searchForm").attr("action","${ctx}/hcf/vendingAdStatistic/qbexports");
						$("#searchForm").submit();
					    
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
		});
		
		/* 分页 */
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/hcf/vendingAdStatistic/list");
			$("#searchForm").submit();
        	return false;
        }
				
		$(function(){
			document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
			    	$("#pageNo").val(1);
					$("#searchForm").attr("action","${ctx}/hcf/vendingAdStatistic/list");
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
		
		function addVendingAd(){   
			 
			 var adType=$('input:radio[name="adType"]:checked').val();
		     if(adType==1){
		    	 $("#imgButton").show();
			     $("#imgDiv").show();
			     $("#videoButton").hide();
			     $("#videoDiv").hide();
		     }else{
		    	 $("#imgButton").hide();
			     $("#imgDiv").hide();
			     $("#videoButton").show();
			     $("#videoDiv").show();
		     }
		    
		     $("#id").val("");
		     $("#vendCodeId").val("");
		     $("#file1").val("");
		     $("#preview1").html("");
	    	 $("#video").val("");
		     $("#orderLabel").text("新增机器广告!");
		     $("#vendCodeId").attr("disabled",false); 
			 $("#select_createVendingAd").modal("show");
		}
		
		function edit(adId){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/vendingConterAdMentget/edit',
				data: adId,
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					if(data.vendCode!=''){
						$("#id").val(data.id);
						/* $("#channelName").val(data.channelName);
						$("#communityName").val(data.communityName);
						$("#imgLink1").val(data.imgLink1);
						$("#imgLink2").val(data.imgLink2);
						$("#imgLink3").val(data.imgLink3);
						$("#channelId").attr("disabled",true);
						$("#communityId").attr("disabled",true);
						$("#channelId").attr("disabled",true);
						$("#channelName").attr("disabled",true);
						$("#communityName").attr("disabled",true); */
 					    if(data.adType==1){
					    	 $("#img").val(data.imgName);
					    	 $("#adId").val(data.adId);
					    	 $("#linkUrl").val(data.linkUrl);
					    	 $("#startTime").val(data.startTimes);
					    	 $("#endTime").val(data.endStimes);
					    	 $("#playTime").select2("data",{"id":data.playTime,"text":data.playTime});
					    	 /* $("#communityId").select2("data",{"id":data.communityId,"text":data.communityName});
						    $("#vendCodeId").select2("data",{"id":data.vendCode,"text":data.vendCode});
					    	 $(":radio[name=adType][value='1']").attr("checked","true"); */
					    	 var imgPreview1 = document.getElementById('preview1'); 
						     imgPreview1.innerHTML = '<img src=${ctxFile}/'+ data.imgPath +' />';  
						    
						   /*   var imgPreview2 = document.getElementById('preview2'); 
						     imgPreview2.innerHTML = '<img src=${ctxFile}/'+ data.imgPath2 +' />'; 
						    
						     var imgPreview3 = document.getElementById('preview3'); 
						     imgPreview3.innerHTML = '<img src=${ctxFile}/'+ data.imgPath3 +' />'; 
						     //   imgPre view3.innerHTML = '<img src="http://192.168.30.201/'+ data.imgPath3 +'" />'; */
						     
						     $("#imgButton").show();
						     $("#imgDiv").show();
						     $("#videoButton").hide();
						     $("#videoDiv").hide();
					    }else{
					    	 $("#img").val(data.imgName);
					    	 $("#adId").val(data.adId);
					    	 $("#linkUrl").val(data.linkUrl);
					    	 $("#startTime").val(data.startTimes);
					    	 $("#endTime").val(data.endStimes);
					    	 $("#playTime").select2("data",{"id":data.playTime,"text":data.playTime});
					    	var videoPreview = document.getElementById('video');  
					    	videoPreview.innerHTML = '<video width="80" height="100" controls="controls" src=${ctxFile}/'+ data.vedioPath + '  />';
					    	 $(":radio[name=adType][value='2']").attr("checked","true");
					    	 
					    	 $("#imgButton").hide();
						     $("#imgDiv").hide();
						     $("#videoButton").show();
						     $("#videoDiv").show();
					    }
					    $("#orderLabel").text("编辑机器广告!");
					    $("#select_createVendingAd").modal("show");
					}		       	 	
				}
				
			})
		}
		
		
		function deletes(id) {
			  var data = {};
			  data.id = id;
			if(confirm("确定要删除数据吗")){
				$.ajax({
					type:"post",
					url:'${ctx}/hcf/vendingConterAdMentget/delete',
					data:JSON.stringify(data),
					dataType:"json",
					contentType:"application/json",
		            success:function(data){
		             	if(data.code=="0"){
		             		alert(data.msg);
				       		window.location.href="${ctx}/hcf/vendingConterAdMentget/list";
				       	}else{
				       		alert(data.msg);
				       	}
		             }
		         });    
				}
			}
		
		function selectAll(){   
			//alert($("#all").attr("checked"));
			if($("#all").attr("checked")=="checked"){
				$("[name='vendCodeBox']").attr("checked",'true');//全选
			}else{
				 $("[name='vendCodeBox']").removeAttr("checked");//取消全选
			}
		};
		

		function occlude(vendCode,adStatus,msg) {
			var data = {}; 
			data.adStatus=adStatus;
			data.vendCode=vendCode;
			$.ajax({
			type:"post",
			url:'${ctx}/hcf/vendingConterAdMentget/close',
			data:JSON.stringify(data),
			dataType:"json",
			contentType:"application/json",
            success:function(data){
             	
             	if(data.code=="0"){
             		alert(msg);
		       		window.location.href="${ctx}/hcf/vendingConterAdMentget/list";
		       	}else{
		       		alert(data.msg);
		       	}
             }
         });    
		}
		function batchUpgrade(){
			//var templateId="${templateId}";
            //判断至少写了一项
            var data = {};
            var checkedNum = $("input[name='vendCodeBox']:checked").length;
            if(checkedNum<1){
                alert("请至少选择1个进行发布!");
                return false;
            }
            
            if(checkedNum>24){
            	
            	alert("请至多选择24个进行发布!");
                return false;
            }
           
           	var checkedList = new Array();
           	$("input[name='vendCodeBox']:checked").each(function(){
               	checkedList.push($(this).val());
           	});
           	var vendCodeList=checkedList.toString();
           	window.location.href="${ctx}/hcf/vendingConterAdMentget/shebeiList?vendCodeList="+vendCodeList;
        }

		
	</script>
 <style>    
 </style> 
</head>
</head>
<body>
	<form:form id="searchForm" modelAttribute="vendingConterAdVo" action="${ctx}/hcf/vendingAdStatistic/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="vendCodeList" name="vendCodeList" type="hidden" value=""/>
		<table id="tab" class="table table-striped table-bordered table-condensed" style="width: 100%">
			<tr>
		             <label>
					<label class="control-label">广告名称：</label>
					<form:input path="imgName" htmlEscape="false" maxlength="16" class="input-medium"/>
		          </label>
		          
		           <label>
					<label class="control-label">时间：</label>
					<input name="startTime" type="text"  maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${vendingConterAdVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
					--
					<input name="endTime" type="text"  maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${vendingConterAdVo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
		       </label>
				
				<label>
				  <input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />&nbsp;&nbsp;&nbsp;&nbsp;
				</label>
					<label >
				  <input id="btnExport" class="btn btn-primary" type="button" value="导出当前页"/>&nbsp;&nbsp;&nbsp;&nbsp;
	   				<input id="qbExport" class="btn btn-primary" type="button" value="导出所有"/>
				</label>
				<br>
				<br>
				<label>
				
		       最后一次更新时间:  <fmt:formatDate value="${updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/> 
				</label>
				
				
			</tr>
		</table> 
	</form:form>
	<sys:message content="${message}"/>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="1%"> <input type="checkbox" id="all" name="all" onclick="selectAll()"></th>
				<th width="1%">序号 </th>
				<th width="4%">广告名称 </th>
				<th width="6%">下发渠道总数</th>
				<th width="4%">下发终端总数</th>
				<th width="5%">下发终端在线总数</th>
				<th width="5%">总播放次数</th>
				<th width="5%">总播放时长</th> 
				<th width="6%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="model" varStatus="bi">
				<tr>
					<td>
						<input type="checkbox" name="vendCodeBox" value="${model.imgName}">
					</td>
					<td>${bi.count}</td>
					<td>${model.imgName	}</td>
					 <td>${model.channelTotel}</td>
					  <td>${model.vendTotel}</td>
					  <td>${model.vendingTotel}</td>
				    <td>${model.sumPlayTimes}</td>
					<td>${model.sumPlayLongs}</td>
				   <td> 
				    <a  href="${ctx}/hcf/vendingAdStatistic/rewardList?adId=${model.adId}&vendTotel=${model.vendTotel}&vendingTotel=${model.vendingTotel}">详情</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination" >${page}</div>
</body>
</html>