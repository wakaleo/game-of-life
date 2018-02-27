<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/index.css" rel="stylesheet" />
	<meta name="decorator" content="default"/>

	<script type="text/javascript">
	
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
			var path = file.value.toString();
			var perfix = path.substring(path.indexOf('.') + 1);
			var regExp = /(mp4)/gi;
			if (!perfix.match(regExp)) {
				document.getElementById("progressPercent").style.display="none"; 
				alert('文件格式不对，请重新选择');
				return false;

			}
			if (file.files[0].size > 1024 * 1024 * 50) {
				document.getElementById("progressPercent").style.display="none"; 
				alert("文件太大，请重新选择!");
				return false;
			}
			
		} else {
			videoDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
		}
		 
		xhr.send(form);
		
		
		
	/* 	<!--上传视频到指定地址 -->
		var videoDiv = document.getElementById('video');
		reader.onload = function(evt) {
			videoDiv.innerHTML = '<video width="80" height="80" controls="controls" src="' + evt.target.result + ' " />';
		}
		 */
		
		 document.getElementById(id).innerHTML = '<video width="80" height="80" controls="controls" src="' + evt.target.result + ' " />'; 
		 reader.readAsDataURL(file.files[0]);
	
	};
	
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
			    
		} 
	}; 
		
	</script>
	<style >
	
	</style>
</head>
<body>

	
    <!-- 图片选择框 -->
    <form>
    <div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<label class="control-label">售货机编码：</label>
					<div class="controls">
						<select id="vendCodeId" name="vendCode" style="width: 220px"
							class="form-control " placeholder="选择编码">
							<option value="">请选择编码</option>
							<c:forEach items="${machineCodeList}" var="channelobj"
								varStatus="indexStatus">
								<option value="${channelobj.vendCode}" id="" name="">${channelobj.vendCode}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</div>

		<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<label class="control-label">渠道名称：</label>
					<div class="controls">
						<input id="channelName" name="channelName" class="form-control"
							type="text" />
					</div>
				</div>
			</div>
		</div>
		
		<div id="progressBar" class="prog_border" align="left"
			style="background-color: green; width: 100px; display: none; margin-left: 200px">

			<img id="imgProgress"
				src="/hcoffeeadmin/static/jingle/image/ui-bg_gloss-wave_35_f6a828_500x100.png"
				style="width: 1px; height: 13px;">
		</div>

		<span id="progressPercent"
			style="width: 40px; display: none; margin-left: 200px">0%</span>
		

		<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<label class="control-label">社区名称：</label>
					<div class="controls">
						<input id="communityName" name="communityName"
							class="form-control" type="text" />
					</div>
				</div>
			</div>
		</div>
		
		<div class="pure-g" id="videoButton" >
			<div class="pure-u-2-3">
				<div class="control-group" style="margin-left: 200px">
					<a href="javascript:;" class="file">选择图片<input type="file"
						name="video" id="videoId" onchange="PreviewImage(this)">
					</a>
				</div>
			</div>
		</div>	
		
    

    <!-- 图片展示容器 -->
   <div class="img-cont"></div> 

			<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<label class="control-label">排序：</label>
					<div class="controls">
						<input id="channelName" name="channelName" class="form-control"
							type="text" />
					</div>
				</div>
			</div>
		</div>
			<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<label class="control-label">播放时长：</label>
					<div class="controls">
						<input id="channelName" name="channelName" class="form-control"
							type="text" />
					</div>
				</div>
			</div>
		</div>
		
		<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<label class="control-label">链接地址：</label>
					<div class="controls">
						<input id="channelName" name="channelName" class="form-control"
							type="text" />
					</div>
				</div>
			</div>
		</div>
		
		
		
	<div class="pure-g" id="videoButton" >
			<div class="pure-u-2-3">
				<div class="control-group" style="margin-left: 200px">
					<!--  <input type="file" name="video" onchange="videoPreview(this)" /> -->
					<a href="javascript:;" class="file">选择视频 <input type="file"
						name="video" id="videoId" onchange="videoPreview(this)">
					</a>
				</div>
			</div>
		</div>
		
    <div class="img-cont"></div> 
      		<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<label class="control-label">排序：</label>
					<div class="controls">
						<input id="channelName" name="channelName" class="form-control"
							type="text" />
					</div>
				</div>
			</div>
		</div>
			<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<label class="control-label">播放时长：</label>
					<div class="controls">
						<input id="channelName" name="channelName" class="form-control"
							type="text" />
					</div>
				</div>
			</div>
		</div>
		
		<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<label class="control-label">链接地址：</label>
					<div class="controls">
						<input id="channelName" name="channelName" class="form-control"
							type="text" />
					</div>
				</div>
			</div>
		</div>
		
  <!-- js -->
  <script src="/hkfsysadmin/static/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
  <script src="/hkfsysadmin/static/jquery/index.js" type="text/javascript"></script>
	
	
    </form>
</body>
</html>
