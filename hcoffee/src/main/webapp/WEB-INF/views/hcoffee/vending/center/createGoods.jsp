<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">

	$(function(){
		$("#btnsave").click(function(){
			var id = $("#id").val();
			var url = '${ctx}/hcf/goodsManagement/save';
			if (id != '' && id != undefined) {
				url = '${ctx}/hcf/goodsManagement/update';
			}
			var form = new FormData(document.getElementById("goodsAdForm"));
			form.id = id;
			loading('正在提交，请稍等...');
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:form,
		       	 processData : false,
				 contentType : false,
		       	 success:function(data){
		       		$.jBox.tip(data.msg);
		         	closeLoading();
		       		if(data.code=="0"){
		       			window.location.href = "${ctx}/hcf/goodsManagement/list";
		       		}
		       	 }
			});  
		});
	});

	function radioClick(_this) {
		var adType = $(_this).val();
		if (adType == 1) {
			$("#imgButton").show();
			$("#imgDiv").show();
			$("#videoButton").hide();
			$("#videoDiv").hide();
			$("#textTipDiv").html("图片大小不能超过1080x1920像素，大小不能超过1M.支持jpg、jpeg、png等格式");
		} else {
			$("#imgButton").hide();
			$("#imgDiv").hide();
			$("#videoButton").show();
			$("#videoDiv").show();
			$("#textTipDiv").html("文件大小不能超过50M，支持mp4");
		}
	}
	
	function previewFrist(file) {
		progressStart();
		var file1=file
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
		var FileController = "${ctx}/hcf/goodsManagement/uploadFile" // 接收上传文件的后台地址 
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
						$.jBox.tip('图片太大，请重新选择');
						
						prevDiv.innerHTML = "";
						reader.readAsDataURL();
						return false;
					}
					
					if (file.files[0].size > 1024 * 1024 * 1) {
						document.getElementById("progressPercent").style.display="none"; 
						$.jBox.tip("	图片文件太大，请重新选择!");
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

		var FileController = "${ctx}/hcf/goodsManagement/uploadFile" // 接收上传文件的后台地址 
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
					$.jBox.tip('图片太大，请重新选择');
					prevDiv.innerHTML = "";
					reader.readAsDataURL();
					return false;
				}
				
				if (file.files[0].size > 1024 * 1024 * 1) {
					document.getElementById("progressPercent").style.display="none"; 
					$.jBox.tip("	图片文件太大，请重新选择!");
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

		var FileController = "${ctx}/hcf/goodsManagement/uploadFile" // 接收上传文件的后台地址 
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
					$.jBox.tip('图片太大，请重新选择');
					prevDiv.innerHTML = "";
					reader.readAsDataURL();
					return false;
				}
				if (file.files[0].size > 1024 * 1024 * 1) {
					document.getElementById("progressPercent").style.display="none"; 
					$.jBox.tip("	图片文件太大，请重新选择!");
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

		var FileController = "${ctx}/hcf/goodsManagement/uploadFile" // 接收上传文件的后台地址 
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
				$.jBox.tip('文件格式不对，请重新选择');
				return false;

			}
			if (file.files[0].size > 1024 * 1024 * 50) {
				document.getElementById("progressPercent").style.display="none"; 
				$.jBox.tip("文件太大，请重新选择!");
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
			$.jBox.tip('图片格式不对，请重新选择');
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
<form:form id="goodsAdForm" modelAttribute="goodsVo"
	action="${ctx}/hcf/goodsManagement/save" method="post"
	enctype="multipart/form-data" class="breadcrumb form-search">
	<input id="id" name="id" type="hidden" value="" />
	<div class="form-horizontal">

		<div id="progressBar" class="prog_border" align="left"
			style="background-color: green; width: 100px; display: none; margin-left: 200px">

			<img id="imgProgress"
				src="/hcoffeeadmin/static/jingle/image/ui-bg_gloss-wave_35_f6a828_500x100.png"
				style="width: 1px; height: 13px;">
		</div>

		<span id="progressPercent"
			style="width: 40px; display: none; margin-left: 200px">0%</span>


		<div class="pure-g" id="imgButton" style="display: none">
			<div class="pure-u-2-3">
				<div class="control-group" style="margin-left: 200px">
					<a href="javascript:;" class="file"
						style="margin-left: 20px; border-left-style: solid;">选择图片 <input
						id="file1" type="file" name="file" onchange="previewFrist(this)">
					</a>
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
						<label class="control-label">商品名称：</label>
						<div class="controls">
							<input id="goodsName" name=goodsName class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">商品ID：</label>
						<div class="controls">
							<input id="goodsId" name=goodsId class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">商品类型：</label>
						<div class="controls">
							<select id="typeIds" name="typeId"  style="width: 220px" class="form-control"
								placeholder="选择合作状态"><option value="">请选择</option>
								<c:forEach items="${goodsTypeList}" var="model" varStatus="indexStatus">
									<option value="${model.id}">${model.typeName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">商品规格：</label>
						<div class="controls">
							<input id="specification" name=specification class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">商品品牌：</label>
						<div class="controls">
							<input id="goodsBrand" name=goodsBrand class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">成本价：(元)</label>
						<div class="controls">
							<input id="priceInto" name=priceInto class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">零售价：(元)</label>
						<div class="controls">
							<input id="priceOut" name=priceOut class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">包箱数：</label>
						<div class="controls">
							<input id="packagesNumber" name=packagesNumber class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
		
		<br>
		
		<div class="form-horizontal">
		<br> <br>
		<div class="pure-g" style="margin-left: 50px">
			<div class="pure-u-2-3">
				<div class="control-group">
					<div class="controls">
						<input id="btnsave" name="btnsave" class="btn" type="button" 
							value="保存" />&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp
						<button type="button" class="btn btn-default" data-dismiss="modal">取消
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	</div>
</form:form>
