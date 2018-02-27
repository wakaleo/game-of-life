
<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		debugger;
		$("#btnsave")
				.bind(
						"click",
						function() {
							
							  var data = {}; 
							  /*var form={
								file1:$('#img')[0].src,
								playTime:$('#playTime').val(),
								imgname:$('#name').val(),
								linkUrl:$('#linkUrl').val(),
							}
							
							console.log(form)
							var id = $("#id").val();
							 var vendCode = $("#vendCodeId").val();
							if (vendCode == "" || vendCode == null) {
								alert("请选择售货机编号");
								return;
							} */
						//	data.imgPath=$('#file1').val();
							/* data.playTime=$('#playTime').val();
							data.imgName=$('#name').val();
							data.linkUrl=$('#linkUrl').val()
							//alert($('#img')[0].src)
							alert($('#playTime').val())
							alert($('#name').val())
							alert($('#linkUrl').val()) */
							var id = $("#id").val();
							var adId = $("#adId").val();
							var startTime = $("#startTime").val();
							var endTime = $("#endTime").val();
							var playTime = $("#playTime").val();
							if (adId == "" || adId == null) {
								alert("请选择活动编号");
								return;
							}
							if (startTime == "" || startTime == null) {
								alert("请选择有效时间");
								return;
							}
							if (endTime == "" || endTime == null) {
								alert("请选择有效时间");
								return;
							}
							if (playTime == "" || playTime == null) {
								alert("请选择播放时长");
								return;
							}
							loading('正在提交，请稍等...');
							var form = new FormData(document.getElementById("vendingConterAdForm"));

							//		             var req = new XMLHttpRequest();
							//		             req.open("post", "${pageContext.request.contextPath}/public/testupload", false);
							//		             req.send(form);
					      	
							var url = '${ctx}/hcf/vendingConterAdMentget/save';
							if (id != '') {
								url = '${ctx}/hcf/vendingConterAdMentget/update';
							}
							$
									.ajax({
										url : url,
										type : "post",
										data : form,
										processData : false,
										contentType : false,
										success : function(data) {
											alert(data.msg);
											 closeLoading()	
											if (data.code == "0") {
												window.location.href = "${ctx}/hcf/vendingConterAdMentget/list";
											}
										}
									});
							//		$("#vendingAdForm").submit();
						});
							
							$("#vendCodeId").bind("change",function(){
					            var vendCode = $(this).val();
					           $.ajax({
									type:"post",
									url:'${ctx}/hcf/vendingAdMentget/editUpdate',
									data: vendCode,
									dataType:"json",
									contentType:"application/json",
									success:function(data){
										if(data.id!=''){
											$("#id").val(data.id);
											 $("#channelName").val(data.channelName); 
											 $("#communityName").val(data.communityName); 
											 $("#channelName").attr("disabled",true);
											 $("#communityName").attr("disabled",true);
										}		       	 	
									}
									
								})
							})
							
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
	function radioClick(_this) {
		var adType = $(_this).val();
		if (adType == 1) {
			$("#imgButton").show();
			$("#imgDiv").show();
			$("#qrCode_div").show();//如果上传的是视频则需要显示二维码参数
			$("#videoButton").hide();
			$("#videoDiv").hide();
			$("#textTipDiv").html("图片大小不能超过1080x1920像素，大小不能超过1M.支持jpg、jpeg、png等格式");
		} else {
			$("#imgButton").hide();
			$("#imgDiv").hide();
			$("#qrCode_div").hide();//如果上传的是视频则需要隐藏二维码参数
			$("#videoButton").show();
			$("#videoDiv").show();
			$("#textTipDiv").html("文件大小不能超过50M，支持mp4");
		}
	}
	
	function previewFrist(file) {
		progressStart();
		var file1=file
		//	    var base = $("#base").val().trim()
		// 上传文件按钮点击的时候
	    document.getElementById("progressPercent").style.display="";     //显示百分比  
		// 进度条显示
		// 上传文件
		UpladFile1(file1);
		// 文件修改时

	}
	function UpladFile1(file) {

	   var reader = new FileReader();
		var fileObj = $("#file1").get(0).files[0]; // js 获取文件对象
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
			$("#file1").val("上传");
			//$('#myModal').modal('hide');
		};
		xhr.upload.addEventListener("progress", progressFunction, false);
		 if (!checkFile(file))
			   return;
		 if (file.files && file.files[0]) {
				reader.onload = function(evt) {
					prevDiv.innerHTML = '<img src="' + evt.target.result + '" />';
					var width = $(prevDiv).children("img").width();
					var height = $(prevDiv).children("img").height();
					if (width * height > 1080 * 1920) {
				document.getElementById("progressPercent").style.display="none"; 
						alert('图片太大，请重新选择');
						
						prevDiv.innerHTML = "";
						reader.readAsDataURL();
						return false;
					}
					
					if (file.files[0].size > 1024 * 1024 * 1) {
						document.getElementById("progressPercent").style.display="none"; 
						alert("	图片文件太大，请重新选择!");
						return false;
					}
				}
			} else {
				prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
			}
		xhr.send(form);
		
		<!--上传图片到指定地址 -->
		
		var prevDiv = document.getElementById('preview1');
		reader.readAsDataURL(file.files[0]);
		 
	};
	
	
	
	function previewScred(file) {
		var file2=file
		progressStart();
		//	    var base = $("#base").val().trim()
		// 上传文件按钮点击的时候
	    document.getElementById("progressPercent").style.display="";     //显示百分比  
		// 进度条显示
		// 上传文件
		UpladFile2(file2);
		// 文件修改时

	}
	function UpladFile2(file) {
      var reader = new FileReader();
		var fileObj = $("#file2").get(0).files[0]; // js 获取文件对象
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
			$("#file2").val("上传");
			//$('#myModal').modal('hide');
		};
		xhr.upload.addEventListener("progress", progressFunction, false);
		if (!checkFile(file))
			return;
    if (file.files && file.files[0]) {
			
			reader.onload = function(evt) {
				prevDiv.innerHTML = '<img id="img" src="' + evt.target.result + '" />';
				var width = $(prevDiv).children("img").width();
				var height = $(prevDiv).children("img").height();
				if (width * height > 1080 * 1920) {
					alert('图片太大，请重新选择');
					prevDiv.innerHTML = "";
					reader.readAsDataURL();
					return false;
				}
				
				if (file.files[0].size > 1024 * 1024 * 1) {
					document.getElementById("progressPercent").style.display="none"; 
					alert("	图片文件太大，请重新选择!");
					return false;
				}
			}
			
		} else {
			prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
		}
		xhr.send(form);
		
		<!--上传图片到指定地址 -->
		
		var prevDiv = document.getElementById('preview2');
		reader.readAsDataURL(file.files[0]);
		
	};
	
	 
	
	function previewThree(file) {
		var file3=file;
		
		progressStart();
		//	    var base = $("#base").val().trim()
		// 上传文件按钮点击的时候
	    document.getElementById("progressPercent").style.display="";     //显示百分比  
		
		// 进度条显示
		// 上传文件
		UpladFile3(file3);
		// 文件修改时
	}
	function UpladFile3(file) {
       var reader = new FileReader();
		var fileObj = $("#file3").get(0).files[0]; // js 获取文件对象
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
			progressEnd()
			//alert("上传完成");
			$("#file3").val("上传");
			//$('#myModal').modal('hide');
		};
		xhr.upload.addEventListener("progress", progressFunction, false);
		if (!checkFile(file))
			return;
      if (file.files && file.files[0]) {
			
			reader.onload = function(evt) {
				prevDiv.innerHTML = '<img id="img" src="' + evt.target.result + '" />';
				var width = $(prevDiv).children("img").width();
				var height = $(prevDiv).children("img").height();
				
				
				
				if (width * height > 1080 * 1920) {
					alert('图片太大，请重新选择');
					prevDiv.innerHTML = "";
					reader.readAsDataURL();
					return false;
				}
				if (file.files[0].size > 1024 * 1024 * 1) {
					document.getElementById("progressPercent").style.display="none"; 
					alert("	图片文件太大，请重新选择!");
					return false;
				}
			}
			
		} else {
			prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
		}
		xhr.send(form);
		
		<!--上传图片到指定地址 -->
		
		var prevDiv = document.getElementById('preview3');
		reader.readAsDataURL(file.files[0]);
		
	};
	
	
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
		
		<!--上传视频到指定地址 -->
		var videoDiv = document.getElementById('video');
		reader.onload = function(evt) {
			videoDiv.innerHTML = '<video width="80" height="80" controls="controls" src="' + evt.target.result + ' " />';
		}
		reader.readAsDataURL(file.files[0]);
		
		
	};
	
	
	
	function checkFile(obj) {
		var path = obj.value.toString();
		var perfix = path.substring(path.indexOf('.') + 1);
		var regExp = /(jpg|jpeg|png)/gi;
		if (!perfix.match(regExp)) {
			document.getElementById("progressPercent").style.display="none"; 
			alert('图片格式不对，请重新选择');
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
			    
		} 
	}; 

	/* 分页 */
</script>
<style>
<!--
fieldset{border: none;margin: 0;padding: 0;}
-->
</style>
<form:form id="vendingConterAdForm" modelAttribute="vendingConterAdVo"
	action="${ctx}/hcf/vendingConterAdVo/save" method="post"
	enctype="multipart/form-data" class="breadcrumb form-search">
	<input id="id" name="id" type="hidden" value="" />
	<div class="form-horizontal">

		<%-- <div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">渠道：</label>
						<div class="controls">
						<select id="channelId" name="channel" style="width: 220px" class="form-control "
								placeholder="渠道">
								<option value="">请选择渠道</option>
								<c:forEach items="${channelList}" var="channelobj" varStatus="indexStatus">
									<option value="${channelobj.channelId}" id="" name="">${channelobj.channelName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>	
		<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">机器所在的社区：</label>
						<div class="controls">
						<select id="communityId" name="communityId" style="width: 220px" class="form-control "
								placeholder="选择社区">
								<option value="">请选择社区</option>
								<c:forEach items="${communityList}" var="channelobj" varStatus="indexStatus">
									<option  value="${channelobj.communityId}"  id="" name="">${channelobj.communityName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div> --%>

		


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
					<label class="control-label">广告类型：</label>
					<div class="controls">
						<input name="adType" type="radio" value="1" checked
							onclick="radioClick(this);" />图片广告 <input name="adType"
							type="radio" value="2" onclick="radioClick(this);" />视频广告
					</div>
				</div>
			</div>
		</div>
                

		<div class="pure-g" id="imgButton" style="display: none">
			<div class="pure-u-2-3">
				<div class="control-group" style="margin-left: 200px">
					<a href="javascript:;" class="file"
						style="margin-left: 20px; border-left-style: solid;">选择图片 <input
						id="file1" type="file" name="file1" onchange="previewFrist(this)">
					</a>
					<!--  <input type="file" name="file1" onchange="preview1(this)" />  
						 	 <input type="file" name = "file2" onchange="preview2(this)" /> 
							 <input type="file" name = "file3" onchange="preview3(this)" /> -->
				</div>
			</div>
		</div>

		<div class="pure-g" id="imgDiv" style="display: none">
			<div class="pure-u-2-3">
				<div class="control-group">
					<div class="boxs" style="margin-left: 200px">
						<div class="box-left" id="preview1"
						></div>
					</div>
				</div>
			</div>
		</div>


		<div class="pure-g" id="videoButton" style="display: none">
			<div class="pure-u-2-3">
				<div class="control-group" style="margin-left: 200px">
					<!--  <input type="file" name="video" onchange="videoPreview(this)" /> -->
					<a href="javascript:;" class="file">选择视频 <input type="file"
						name="video" id="videoId" onchange="videoPreview(this)">
					</a>
				</div>
			</div>
		</div>
		
		

		<div class="pure-g" id="videoDiv" style="display: none">
			<div class="pure-u-2-3">
				<div class="control-group" style="margin-left: 200px">
					<div class="box-left" id="video"></div>
				</div>
			</div>
		</div>

		<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group" style="margin-left: 50px">
					<div style="color: red; width: 500px" id="textTipDiv" >图片大小不能超过1080x1920像素，大小不能超过1M,支持jpg、jpeg、png等格式</div>
				</div>
			</div>
		</div>
       
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">播放时长(s)：</label>
						<div class="controls">
							<input id="playTime" type="text" name="playTime" maxlength="10" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">图片/视频名称：</label>
						<div class="controls">
							<input id="img" name="imgName" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">广告/视频编码：</label>
						<div class="controls">
							<input id="adId" name="adId" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
		
		
		<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">链接地址：</label>
						<div class="controls">
							<input id="linkUrl" name="linkUrl" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
		<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">有效期：</label>
						<div class="controls">
					<input id="startTime" name="startTime" type="text"  maxlength="20" class="input-medium Wdate " value=""
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
					至
					<br>
					<input id="endTime"  name="endTime" type="text"  maxlength="20" class="input-medium Wdate " value=""
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
		 
						</div>
					</div>
				</div>
			</div>
		
		<div style="width: 80%;margin-left: 2px;" id="qrCode_div">
			<fieldset>
				<legend>二维码参数</legend>
				<table id="qrCode_table" class="table table-striped table-bordered">
					<tr>
						<td>X&nbsp;坐&nbsp;标：</td><td><input id="coordinate_x" name="coordinateX" type="text"/></td>
						<td>Y&nbsp;坐&nbsp;标：</td><td><input id="coordinate_y" name="coordinateY" type="text"/></td>
					</tr>
					<tr>
						<td>宽&nbsp;度：</td><td><input id="qrCode_w" name="qrCodeW" type="text"/></td>
						<td>高&nbsp;度：</td><td><input id="qrCode_h" name="qrCodeH" type="text"/></td>
					</tr>
					<tr>
						<td>摆放方式：</td>
						<td>
							<select id="putType" name="putType"  style="width: 220px" class="form-control"
									placeholder="选择摆放方式">
									<option value="">请选择</option>
									<option value="1">横屏</option>
									<option value="2">坚屏</option>
							</select>
						</td>
						<td>二维码值：</td>
						<td><input id="qrCodeValue" name="qrCodeValue" type="text"/></td>
					</tr>
				</table>
			</fieldset>
		</div>
		
		

		<br>
		<div class="pure-g" style="margin-left: 40px">
			<div class="pure-u-2-3">
				<div class="control-group">
					<div class="controls">
						<input id="btnsave" name="btnsave" class="btn" type="button"
							value="保存" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn btn-default" data-dismiss="modal">取消
						</button>	
					</div>
				</div>
			</div>
		</div>
		
		

	</div>
</form:form>
