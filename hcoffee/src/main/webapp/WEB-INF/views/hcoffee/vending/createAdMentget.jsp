  <%@ page contentType="text/html;charset=UTF-8" %>
<script  type="text/javascript">
	 	$(function(){
				
				$("#batchBtn").bind("click",function(){
					 if(confirm("确定更新所选售货机?"+$("#batchVendCode").val())){
						 var form = new FormData(document.getElementById("batchVendingAdForm"));
//			             var req = new XMLHttpRequest();
//			             req.open("post", "${pageContext.request.contextPath}/public/testupload", false);
//			             req.send(form);
                       loading('正在提交，请稍等...');
					     url ='${ctx}/hcf/vendingAdMentget/batchEdit';
					     
			             $.ajax({
			                url:url,
			                type:"post",
			                data:form, 
			                processData:false,
			                contentType:false,
			                success:function(data){
			                	alert(data.msg);
					       		closeLoading()
			                	if(data.code=="0"){
					       			window.location.href="${ctx}/hcf/vendingAdMentget/list";
					       		}
			                }
			            });    
					 }
			//		$("#vendingAdForm").submit();
				});
				
				
			/*  $(":radio").click(function(){
				 var adType=$('input:radio[name="batchAdType"]:checked').val();
			     if(adType==1){
			    	 $("#batchImgButton").show();
				     $("#batchImgDiv").show();
				     $("#batchVideoButton").hide();
				     $("#batchVideoDiv").hide();
			     }else{
			    	 $("#batchImgButton").hide();
				     $("#batchImgDiv").hide();
				     $("#batchVideoButton").show();
				     $("#batchVideoDiv").show();
			     }
			 }); */
			 
			})
	/* 分页 */
	 		function batchRadio(_this){
	 			 var adType = $(_this).val();
	 			 $("#adType").val(adType);
				 if(adType==1){
			    	 $("#batchImgButton").show();
				     $("#batchImgDiv").show();
				     $("#batchVideoButton").hide();
				     $("#batchVideoDiv").hide();
			     }else{
			    	 $("#batchImgButton").hide();
				     $("#batchImgDiv").hide();
				     $("#batchVideoButton").show();
				     $("#batchVideoDiv").show();
			     }
	 		}
			function batchVideoPreview(file){
				var videoDiv = document.getElementById('batchVideo');  
				 if (file.files && file.files[0])  {  
				 	var reader = new FileReader();  
				 	reader.onload = function(evt){  
				 	videoDiv.innerHTML = '<video width="80" height="80" controls="controls" src="' + evt.target.result + ' " />';  
			
				
				 	
				 	}    
				 	reader.readAsDataURL(file.files[0]);  
				}else  {  
					videoDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';  
			
				 	alert(file.value)
				
				
				
				}  
			}
			
			
			
			 function preview1(file) { 
				 var prevDiv = document.getElementById('batchPreview1');  
				 if (file.files && file.files[0])  {  
				 	var reader = new FileReader();  
				 	reader.onload = function(evt){  
				 	prevDiv.innerHTML = '<img src="' + evt.target.result + '" />';  
				 	
				}    
				 	reader.readAsDataURL(file.files[0]);  
				}else  {  
				 	prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';  
			
				
				}  
				 
			 }
			function preview2(file) {  
				 var prevDiv = document.getElementById('batchPreview2');  
				 if (file.files && file.files[0])  {  
				 	var reader = new FileReader();  
				 	reader.onload = function(evt){  
				 	prevDiv.innerHTML = '<img src="' + evt.target.result + '" />';  
				}    
				 	reader.readAsDataURL(file.files[0]);  
				}else  {  
				 	prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';  
				}  
			 } 
			function preview3(file) {  
				 var prevDiv = document.getElementById('batchPreview3');  
				 if (file.files && file.files[0])  {  
				 	var reader = new FileReader();  
				 	reader.onload = function(evt){  
				 	prevDiv.innerHTML = '<img src="' + evt.target.result + '" />';  
				}    
				 	reader.readAsDataURL(file.files[0]);  
				}else  {  
				 	prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';  
				}  
			 }  
		

	</script>
		<input id="adType" name="adType" type="hidden" value="1"/>
 	<form:form id="batchVendingAdForm" modelAttribute="vendingAdVo" action="${ctx}/hcf/vendingAdMentget/save" method="post" enctype="multipart/form-data" class="breadcrumb form-search">
		<div class="form-horizontal">
			<ul class="nav nav-tabs">
				<li>售货机广告管理</li>
			</ul>
		
		
		<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">售货机编码：</label>
						<div class="controls">
							<input id="batchVendCode" name="batchVendCode" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">广告类型：</label>
						<div class="controls">
							 <input name="batchAdType" type="radio" value="1" checked onclick="batchRadio(this);"/>图片广告 
							 <input name="batchAdType" type="radio" value="2" onclick="batchRadio(this);"/>视频广告
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g" id="batchImgButton" style="display: none">
				<div class="pure-u-2-3">
				<div class="control-group" style="margin-left: 70px">
								 <a href="javascript:;" class="file"  style="margin-left: 20px;border-left-style: solid;">选择图片1
							    <input type="file" name="file1"  id="file1" onchange="preview1(this)">
							 </a>
							 <a href="javascript:;" class="file"  style="margin-left: 20px;border-left-style: solid;"> 选择图片2
							    <input type="file" name="file2"  id="file2" onchange="preview2(this)">
							 </a>
							 <a href="javascript:;" class="file"  style="margin-left: 20px;border-left-style: solid;">选择图片3
							    <input type="file" name="file3" id="file3" onchange="preview3(this)">
							 </a>
							<!--  <input type="file" name="file1" onchange="preview1(this)" />  
						 	 <input type="file" name = "file2" onchange="preview2(this)" /> 
							 <input type="file" name = "file3" onchange="preview3(this)" /> -->
					</div>
				</div>
			</div>
			
			<div class="pure-g" id="batchImgDiv" style="display: none">
				<div class="pure-u-2-3">
					<div class="control-group"  style="margin-left: 60px">
						<div class="boxs">
							 <div class="box-left" id="batchPreview1" style="margin-left: 30px;border-left-style: solid;"></div>
							 <div class="box-left" id="batchPreview2" style="margin-left: 30px;border-left-style: solid;"></div>
							 <div class="box-left" id="batchPreview3" style="margin-left: 30px;border-left-style: solid;"></div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g" id="batchVideoButton" style="display: none">
				<div class="pure-u-2-3">
					<div class="control-group" style="margin-left: 200px">
						 	<a href="javascript:;" class="file">选择视频
						 	 	 <input type="file" name="video" onchange="batchVideoPreview(this)">
						 	</a>
					</div>
				</div>
			</div>
		
			<div class="pure-g" id="batchVideoDiv" style="display: none">
				<div class="pure-u-2-3">
					<div class="control-group"  style="margin-left: 180px">
						<div class="box-left" id="batchVideo"></div>
					</div>
				</div>
			</div>
			
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<div class="controls">
							<input id="batchBtn" name="batchBtn" class="btn" type="button" value="保存"   />
						</div>
					</div>
				</div>
			</div>

		</div>
	</form:form> 
 