  <%@ page contentType="text/html;charset=UTF-8" %>
<script  type="text/javascript">
			$(function(){
				
				$("#uploadAdForms").validate({
					submitHandler: function(form){
						
						//form.submit();
							var id = $("#id").val();
							 if($("#channelId").val()==''){			 
								 return  alert("请选择渠道");
								 
							 }
						     if($("#schemeNo").val()==''){
							   return  alert("请选择活动类型");
						    }
						     loading('正在提交，请稍等...');
							 var form = new FormData(document.getElementById("uploadAdForms"));
//				             var req = new XMLHttpRequest();
//				             req.open("post", "${pageContext.request.contextPath}/public/testupload", false);
//				             req.send(form);
							 var  url ='${ctx}/hcf/uploadAd/save';
						     if(id !='' ){
						    	   url ='${ctx}/hcf/uploadAd/edit';
						     }
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
							       			window.location.href="${ctx}/hcf/uploadAd/list";
					            		}
				                }
				            });    
					//		$("#vendingAdForm").submit();
					},
				});
				
			
				document.onkeydown = function() {
				    var evt = window.event || arguments[0];
				    if (evt && evt.keyCode == 13) {
			        if (typeof evt.cancelBubble != "undefined")
			            evt.cancelBubble = true;
			        if (typeof evt.stopPropagation == "function")
			            evt.stopPropagation();
			          }
			    
			        }		
			})
			
	 function videoPreview(file) {
		   progressStart();
           var fileVideo=file;
		//	    var base = $("#base").val().trim()
		// 上传文件按钮点击的时候
	    document.getElementById("progressPercent").style.display="";     //显示百分比  
		
		// 进度条显示
		// 上传文件
		UpladFileVideo(fileVideo);
		// 文件修改时
	}
	function UpladFileVideo(file) {
     	var reader = new FileReader();
		var fileObj = $("#videoId").get(0).files[0]; // js 获取文件对象
		console.info("上传的文件：" + fileObj);
		/*    var reader = new FileReader(); 
		var videoDiv = document.getElementById('video'); */

		var FileController = "${ctx}/hcf/vendingAdMentget/uploadFile" // 接收上传文件的后台地址 
		// FormData 对象
		var form = new FormData();
		// form.append("author", "hooyes"); // 可以增加表单数据
		form.append("file", fileObj); // 文件对象
		// XMLHttpRequest 对象
		var xhr = new XMLHttpRequest();
		xhr.open("post", FileController, true);
		xhr.onload = function() {
			// ShowSuccess("上传完成");
			progressEnd();
			//alert("上传完成");
			$("#videoId").val("上传");
			//$('#myModal').modal('hide');
		};
		xhr.upload.addEventListener("progress", progressFunction, false);
		 if (file.files && file.files[0]) {
			 if(!checkFile(file))return; 
			if (file.files[0].size > 1024 * 1024 * 50) {
				
				document.getElementById("progressPercent").style.display="none"; 
				alert("文件太大，请重新选择!");
				return false;
			}
			
		} else {
			videoDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
		}
		 
		xhr.send(form);
		
		<!--上传视频到指定地址 -->
		var videoDiv = document.getElementById('video');
		reader.onload = function(evt) {
			videoDiv.innerHTML = '<video width="80" height="80" controls="controls" src="' + evt.target.result + ' " />';
		}
		reader.readAsDataURL(file.files[0]);
		
	};
			
			function checkFile(obj){
				var path=obj.value.toString();
				var perfix=path.substring(path.indexOf('.')+1);
				var regExp=/(mp4|flv|avi|rm|rmvb|wmv)/gi;
				if(!perfix.match(regExp)){
					alert('文件格式不对，请重新选择'); 
					return false;	

				}
				return true;
			} 
		
			
			//上传完成后进度条完毕
			function progressStart(){
				 document.getElementById("imgProgress").style.width= 1; 
			}

			//上传完成后进度条完毕
			function progressEnd(){
				 document.getElementById("imgProgress").style.width= 250; 
			    document.getElementById("progressBar").style.display="none"; 
			    document.getElementById("progressPercent").style.display="none"; 
			}
			

			function progressFunction(evt) {
				if (evt.lengthComputable) {
				
					var completePercent = Math.round(evt.loaded / evt.total * 100);
						progressPercent.innerHTML=completePercent+"%";
					    document.getElementById("progressBar").style.display="block";   //显示进度条
		                document.getElementById("imgProgress").style.width=completePercent*(235/100); 
					    
		                //console.log(new Date() + completePercent*(235/100) );

					    
				} 
			}; 

	</script>
	<form:form id="uploadAdForms" modelAttribute="uploadAdVo" action="${ctx}/hcf/uploadAd/save" method="post" enctype="multipart/form-data" class="breadcrumb form-search">
		<input id="id" name="id" type="hidden" value=""/>
		<div class="form-horizontal">
			<ul class="nav nav-tabs">
				<li>广告上传</li>
			</ul>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">渠道：</label>
						<div class="controls">
							<select id="channelId" name="channelId" style="width: 220px" class="form-control "
								placeholder="选择渠道">
								<option value="">请选择渠道</option>
								<c:forEach items="${channelList}" var="channelobj" varStatus="indexStatus">
									<option value="${channelobj.channelId}">${channelobj.channelName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">活动类型：</label>
						<div class="controls">
							<select id="schemeNo" name="schemeNo" style="width: 220px" class="form-control select2"
								placeholder="请选择活动类型"><option value="">请选择活动类型</option>
								<c:forEach items="${activitySchemeList}" var="activityScheme" varStatus="indexStatus">
									<option value="${activityScheme.schemeNo}">${activityScheme.schemeName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			
		<div id="progressBar" class="prog_border" align="left"
			style="background-color: green; width: 100px; display: none; margin-left: 180px">

			<img id="imgProgress"
				src="/hcoffeeadmin/static/jingle/image/ui-bg_gloss-wave_35_f6a828_500x100.png"
				style="width: 1px; height: 13px;">
		</div>

		<span id="progressPercent"
			style="width: 40px; display: none; margin-left: 200px">0%</span>
			
			
			<div class="pure-g" id="videoButton" >
				<div class="pure-u-2-3">
					<div class="control-group"  style="margin-left: 200px">
						<!--  <input type="file" name="video" onchange="videoPreview(this)" /> -->
						 <a href="javascript:;" class="file">选择视频
						    <input id="videoId" type="file" name="video" onchange="videoPreview(this)">
						 </a>
					</div>
				</div>
			</div>
			
			<div class="pure-g" id="videoDiv" >
				<div class="pure-u-2-3">
					<div class="control-group" style="margin-left: 200px">
						<div class="box-left" id="video"></div>
					</div>
				</div>
			</div>
			<div class="pure-g"  >
				<div class="pure-u-2-3">
					<div class="control-group" style="margin-left: 100px">
						<div style="color: red">文件大小不能超过50M，支持mp4、flv、avi、rm、rmvb、wmv等格式</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3" style="margin-left: 40px">
					<div class="control-group">
						<div class="controls">
							<input id="btnsave" name="btnsave" class="btn" type="submit" value="保存"   />
						</div>
					</div>
				</div>
			</div>

		</div>
	</form:form>
 