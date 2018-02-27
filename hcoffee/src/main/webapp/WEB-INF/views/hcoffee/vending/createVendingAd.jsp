  <%@ page contentType="text/html;charset=UTF-8" %>
<script  type="text/javascript">
			$(function(){
				
				$("#btnsave").bind("click",function(){
					
					var id = $("#id").val();
					 var adId= $("#adId").val();
					   if(adId=="" || adId ==null){
						     alert("请输入广告编号");
						     return;
					    }
					 var form = new FormData(document.getElementById("vendingAdForm"));
//		             var req = new XMLHttpRequest();
//		             req.open("post", "${pageContext.request.contextPath}/public/testupload", false);
//		             req.send(form);
					 var  url ='${ctx}/hcf/vendingAd/save';
				     if(id !='' ){
				    	   url ='${ctx}/hcf/vendingAd/update';
				     }
		             $.ajax({
		                url:url,
		                type:"post",
		                data:form,
		                processData:false,
		                contentType:false,
		                success:function(data){
		                	alert(data.msg);
		                	if(data.code=="0"){		                		
		                		window.location.href="${ctx}/hcf/vendingAd/list";
				       		}
		                }
		            });    
			//		$("#vendingAdForm").submit();
				});
				
				
			
			 
			})
			
			
	function batchUpgrade(){
			 //var data = {}; 	
		    var aDList= $("#aDList").val();
		    var vendCode= $("#vendCodeId").val();
           	window.location.href="${ctx}/hcf/vendingAd/vendAdList?aDList="+aDList+ "&vendCode="+vendCode;
            /* 	data.aDList=aDList;
		     $.ajax({
					type:"post",
					url:'${ctx}/hcf/vendingAd/vendAdList',
					data:JSON.stringify(data),
					dataType:"json",
					contentType:"application/json",
		            success:function(data){
		            	for(var i = 0;i<data.length;i++){
		            	 //$("#tableId").append("<tr><th width=’5%‘>序号</th><th width=’5%‘>广告名称</th><th width=’5%‘>广告类型</th><th width=’5%‘>添加时间</th><th width=’5%‘>排序号</th><tr>")
		            	$("#tableId").append("<tr><td type='text' value='" +data[i].schemeNo +"'>" + data[i].schemeName +"</td><td><input type='button' value='置顶' onclick='botten(this)'><td><input type='button' value='X' onclick='deleteRow(this)'></td></tr>")
		            	
		            	}
		             }
		         });   */  
	     }
		

	</script>
	<form:form id="vendingAdForm" modelAttribute="vendingReleVo" action="${ctx}/hcf/vendingAd/save" method="post" enctype="multipart/form-data" class="breadcrumb form-search">
		<input id="id" name="id" type="hidden" value=""/>
		<input id="aDList" name="aDList" type="hidden" value=""/>
		<input id="vendCodeId" name="vendCode" type="hidden" value=""/>
		<div class="form-horizontal">
			<ul class="nav nav-tabs">
				<li>屏保广告管理</li>
			</ul>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">售货机编码：</label>
						<div class="controls">
							<input id="vendCodes" name="vendCode" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">机器所在社区：</label>
						<div class="controls">
							<input id="communityName" name="communityName" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">渠道：</label>
						<div class="controls">
							<input id="channelName" name="channelName" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>			
		
	<!-- 	<div align="center"> <input class="btn btn-primary" type="button" value="屏保广告详情" onclick="batchUpgrade()"/></div>
		
		<table id="form">
		
		</table> -->
		<br>
		<br>
		
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<div class="controls">
							<input class="btn btn-primary" type="button" value="屏保广告详情" onclick="batchUpgrade()"/>
						</div>
					</div>
				</div>
			</div>

		</div>
	</form:form>
 