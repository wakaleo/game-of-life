<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript" src="${ctxStatic }/jquery/ajaxfileupload.js"></script>
<script type="text/javascript">


	
////////上传图片/////////////////////
	
//type:1-顶部图片 ； 2-底部图片；3-logo
	function previewFrist1(file) {
		progressStart1();
		var file1=file
		//document.getElementById("preview"+type).html("");
		// 上传文件按钮点击的时候
	    document.getElementById("progressPercent1").style.display="";     //显示百分比  
		// 进度条显示
		
		
	    
		// 上传文件
		UploadFile1(file1);
		// 文件修改时

	}
	function UploadFile1(file) {

	   var reader = new FileReader();
		var fileObj = $("#file1").get(0).files[0]; // js 获取文件对象
		console.info("上传的文件：" + fileObj);
		var FileController = "${ctx}/hcf/wechatMaterial/uploadPic" // 接收上传文件的后台地址 
		// FormData 对象
		var form = new FormData();
		// form.append("author", "hooyes"); // 可以增加表单数据
		form.append("file", fileObj); // 文件对象
		// XMLHttpRequest 对象
		var xhr = new XMLHttpRequest();
		xhr.open("post", FileController, true);
		
	    // 设置回调函数  
	    xhr.onreadystatechange = function() {  
	        // 判断请求状态  
	        if (xhr.readyState == 4 && xhr.status == 200) {  
	            // 获取返回的responseText 值  
	            var $responseJson=$.parseJSON(xhr.responseText);
	            console.log("上传回调函数返回值:"+$responseJson.prizeUrl);
		        $("#topUrlTemp").val(""+$responseJson.prizeUrl);
	            
	        }  
	    }  
	    
		xhr.onload = function() {
			// ShowSuccess("上传完成");
			progressEnd1();
			//alert("上传完成");
			$("#file1").val("");
			//$('#myModal').modal('hide');
		};
		xhr.upload.addEventListener("progress", progressFunction1, false);
		 if (!checkFile1(file))
			   return;
		 if (file.files && file.files[0]) {
				reader.onload = function(evt) {
					prevDiv.innerHTML = '<img width="100" src="' + evt.target.result + '" />';
					var width = $(prevDiv).children("img").width();
					var height = $(prevDiv).children("img").height();
					if (width * height > 1080 * 1920) {
				document.getElementById("progressPercent1").style.display="none"; 
						$.jBox.tip('图片太大，请重新选择');
						
						prevDiv.innerHTML = "";
						reader.readAsDataURL();
						return false;
					}
					
					if (file.files[0].size > 1024 * 1024 * 1) {
						document.getElementById("progressPercent1").style.display="none"; 
						$.jBox.tip("	图片文件太大，请重新选择!");
						return false;
					}
				}
			} else {
				prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
				//console.log("2"+file.value);
			}
		xhr.send(form);
		
		<!--上传图片到指定地址 -->
		
		
		var prevDiv = document.getElementById('preview1');
		reader.readAsDataURL(file.files[0]);
		 
	};
	
	function checkFile1(obj) {
		var path = obj.value.toString();
		var perfix = path.substring(path.indexOf('.') + 1);
		var regExp = /(jpg|jpeg|png)/gi;
		if (!perfix.match(regExp)) {
			document.getElementById("progressPercent1").style.display="none"; 
			$.jBox.tip('图片格式不对，请重新选择');
			return false;

		}
		return true;
	}
	
	//上传完成后进度条完毕
	function progressStart1(){
		 document.getElementById("imgProgress1").style.width= 1; 
	}

	//上传完成后进度条完毕
	function progressEnd1(){
		document.getElementById("imgProgress1").style.width= 250; 
	    document.getElementById("progressBar1").style.display="none"; 
	    document.getElementById("progressPercent1").style.display="none"; 
	}
	

	function progressFunction1(evt) {
		if (evt.lengthComputable) {
		
			var completePercent = Math.round(evt.loaded / evt.total * 100);
		
				var progressPercent= document.getElementById("progressPercent1");
				progressPercent.innerHTML=completePercent+"%";
			    document.getElementById("progressBar1").style.display="block";   //显示进度条
                document.getElementById("imgProgress1").style.width=completePercent*(235/100); 
			    
		} 
	}; 
	

/////////////////////////////////
		
////////上传图片/////////////////////
	
//type:1-顶部图片 ； 2-底部图片；3-logo
	function previewFrist2(file) {
		progressStart2();
		var file2=file
		//document.getElementById("preview"+type).html("");
		// 上传文件按钮点击的时候
	    document.getElementById("progressPercent2").style.display="";     //显示百分比  
		// 进度条显示
		
		
	    
		// 上传文件
		UploadFile2(file2);
		// 文件修改时

	}
	function UploadFile2(file) {

	   var reader = new FileReader();
		var fileObj = $("#file2").get(0).files[0]; // js 获取文件对象
		console.info("上传的文件：" + fileObj);
		var FileController = "${ctx}/hcf/wechatMaterial/uploadPic" // 接收上传文件的后台地址 
		// FormData 对象
		var form = new FormData();
		// form.append("author", "hooyes"); // 可以增加表单数据
		form.append("file", fileObj); // 文件对象
		// XMLHttpRequest 对象
		var xhr = new XMLHttpRequest();
		xhr.open("post", FileController, true);
		
	    // 设置回调函数  
	    xhr.onreadystatechange = function() {  
	        // 判断请求状态  
	        if (xhr.readyState == 4 && xhr.status == 200) {  
	            // 获取返回的responseText 值  
	            var $responseJson=$.parseJSON(xhr.responseText);
	            console.log("上传回调函数返回值:"+$responseJson.prizeUrl);
		        $("#bottomUrlTemp").val(""+$responseJson.prizeUrl);
	            
	        }  
	    }  
	    
		xhr.onload = function() {
			// ShowSuccess("上传完成");
			progressEnd2();
			//alert("上传完成");
			$("#file2").val("");
			//$('#myModal').modal('hide');
		};
		xhr.upload.addEventListener("progress", progressFunction2, false);
		 if (!checkFile2(file))
			   return;
		 if (file.files && file.files[0]) {
				reader.onload = function(evt) {
					prevDiv.innerHTML = '<img width="100" src="' + evt.target.result + '" />';
					var width = $(prevDiv).children("img").width();
					var height = $(prevDiv).children("img").height();
					if (width * height > 1080 * 1920) {
				document.getElementById("progressPercent2").style.display="none"; 
						$.jBox.tip('图片太大，请重新选择');
						
						prevDiv.innerHTML = "";
						reader.readAsDataURL();
						return false;
					}
					
					if (file.files[0].size > 1024 * 1024 * 1) {
						document.getElementById("progressPercent2").style.display="none"; 
						$.jBox.tip("	图片文件太大，请重新选择!");
						return false;
					}
				}
			} else {
				prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
				//console.log("2"+file.value);
			}
		xhr.send(form);
		
		<!--上传图片到指定地址 -->
		
		
		var prevDiv = document.getElementById('preview2');
		reader.readAsDataURL(file.files[0]);
		 
	};
	
	function checkFile2(obj) {
		var path = obj.value.toString();
		var perfix = path.substring(path.indexOf('.') + 1);
		var regExp = /(jpg|jpeg|png)/gi;
		if (!perfix.match(regExp)) {
			document.getElementById("progressPercent2").style.display="none"; 
			$.jBox.tip('图片格式不对，请重新选择');
			return false;

		}
		return true;
	}
	
	//上传完成后进度条完毕
	function progressStart2(){
		 document.getElementById("imgProgress2").style.width= 1; 
	}

	//上传完成后进度条完毕
	function progressEnd2(){
		document.getElementById("imgProgress2").style.width= 250; 
	    document.getElementById("progressBar2").style.display="none"; 
	    document.getElementById("progressPercent2").style.display="none"; 
	}
	

	function progressFunction2(evt) {
		if (evt.lengthComputable) {
		
			var completePercent = Math.round(evt.loaded / evt.total * 100);
		
				var progressPercent= document.getElementById("progressPercent2");
				progressPercent.innerHTML=completePercent+"%";
			    document.getElementById("progressBar2").style.display="block";   //显示进度条
                document.getElementById("imgProgress2").style.width=completePercent*(235/100); 
			    
		} 
	}; 
	

/////////////////////////////////
		
////////上传图片/////////////////////
	
//type:1-顶部图片 ； 2-底部图片；3-logo
	function previewFrist3(file) {
		progressStart3();
		var file3=file
		//document.getElementById("preview"+type).html("");
		// 上传文件按钮点击的时候
	    document.getElementById("progressPercent3").style.display="";     //显示百分比  
		// 进度条显示
		
		
	    
		// 上传文件
		UploadFile3(file3);
		// 文件修改时

	}
	function UploadFile3(file) {

	   var reader = new FileReader();
		var fileObj = $("#file3").get(0).files[0]; // js 获取文件对象
		console.info("上传的文件：" + fileObj);
		var FileController = "${ctx}/hcf/wechatMaterial/uploadPic" // 接收上传文件的后台地址 
		// FormData 对象
		var form = new FormData();
		// form.append("author", "hooyes"); // 可以增加表单数据
		form.append("file", fileObj); // 文件对象
		// XMLHttpRequest 对象
		var xhr = new XMLHttpRequest();
		xhr.open("post", FileController, true);
		
	    // 设置回调函数  
	    xhr.onreadystatechange = function() {  
	        // 判断请求状态  
	        if (xhr.readyState == 4 && xhr.status == 200) {  
	            // 获取返回的responseText 值  
	            var $responseJson=$.parseJSON(xhr.responseText);
	            console.log("上传回调函数返回值:"+$responseJson.prizeUrl);
		        $("#logoUrlTemp").val(""+$responseJson.prizeUrl);
	            
	        }  
	    }  
	    
		xhr.onload = function() {
			// ShowSuccess("上传完成");
			progressEnd3();
			//alert("上传完成");
			$("#file3").val("");
			//$('#myModal').modal('hide');
		};
		xhr.upload.addEventListener("progress", progressFunction3, false);
		 if (!checkFile3(file))
			   return;
		 if (file.files && file.files[0]) {
				reader.onload = function(evt) {
					prevDiv.innerHTML = '<img width="100" src="' + evt.target.result + '" />';
					var width = $(prevDiv).children("img").width();
					var height = $(prevDiv).children("img").height();
					if (width * height > 1080 * 1920) {
				document.getElementById("progressPercent3").style.display="none"; 
						$.jBox.tip('图片太大，请重新选择');
						
						prevDiv.innerHTML = "";
						reader.readAsDataURL();
						return false;
					}
					
					if (file.files[0].size > 1024 * 1024 * 1) {
						document.getElementById("progressPercent3").style.display="none"; 
						$.jBox.tip("	图片文件太大，请重新选择!");
						return false;
					}
				}
			} else {
				prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
				//console.log("2"+file.value);
			}
		xhr.send(form);
		
		<!--上传图片到指定地址 -->
		
		
		var prevDiv = document.getElementById('preview3');
		reader.readAsDataURL(file.files[0]);
		 
	};
	
	function checkFile3(obj) {
		var path = obj.value.toString();
		var perfix = path.substring(path.indexOf('.') + 1);
		var regExp = /(jpg|jpeg|png)/gi;
		if (!perfix.match(regExp)) {
			document.getElementById("progressPercent3").style.display="none"; 
			$.jBox.tip('图片格式不对，请重新选择');
			return false;

		}
		return true;
	}
	
	//上传完成后进度条完毕
	function progressStart3(){
		 document.getElementById("imgProgress3").style.width= 1; 
	}

	//上传完成后进度条完毕
	function progressEnd3(){
		document.getElementById("imgProgress3").style.width= 250; 
	    document.getElementById("progressBar3").style.display="none"; 
	    document.getElementById("progressPercent3").style.display="none"; 
	}
	

	function progressFunction3(evt) {
		if (evt.lengthComputable) {
		
			var completePercent = Math.round(evt.loaded / evt.total * 100);
		
				var progressPercent= document.getElementById("progressPercent3");
				progressPercent.innerHTML=completePercent+"%";
			    document.getElementById("progressBar3").style.display="block";   //显示进度条
                document.getElementById("imgProgress3").style.width=completePercent*(235/100); 
			    
		} 
	}; 
	

/////////////////////////////////
		
	
	//表单验证
	function validationF(){
			
		
		//二维码名称
		var wechatName = $.trim($("#wechatName").val());
		if(wechatName == ''){
			$.jBox.tip('二维码名称不能为空!');
			return false;
		}
		var nameIsOk = $.trim($("#nameIsOk").val());
		if ('0'!=nameIsOk) {
			$.jBox.tip('二维码名称不可用!');
			return false;
		}
		//二维码编码
		var wechatNo = $.trim($("#wechatNo").val());
		if(wechatNo == ''){
			$.jBox.tip('二维码编码不能为空!');
			return false;
		}
		var noIsOk = $.trim($("#noIsOk").val());
		if ('0'!=noIsOk) {
			$.jBox.tip('二维码编码不可用!');
			return false;
		}
		//开始时间
		var startTime = $.trim($("#startTime").val());
		if(startTime == ''){
			$.jBox.tip('开始时间不能为空!');
			return false;
		}
		//结束时间
		var endTime = $.trim($("#endTime").val());
		if(endTime == ''){
			$.jBox.tip('结束时间不能为空!');
			return false;
		}
		//顶部图片
		var topUrl = $.trim($("#topUrlTemp").val());
		if(topUrl == ''){
			$.jBox.tip('顶部图片不能为空!');
			return false;
		}
		//底部图片
		var bottomUrl = $.trim($("#bottomUrlTemp").val());
		if(bottomUrl == ''){
			$.jBox.tip('底部图片不能为空!');
			return false;
		}
		//二维码logo图片
		var logoUrl = $.trim($("#logoUrlTemp").val());
		if(logoUrl == ''){
			$.jBox.tip('二维码logo图片不能为空!');
			return false;
		}
		
		
		
		return true;
	}
	
	
	
	
	
	
	
	
	
	/* function ajaxFileUpload() {
		$.ajaxFileUpload
         	(
             {
                 url: '${ctx}/hcf/lottoVend/uploadPic',
                 secureuri: false, //一般设置为false
                 fileElementId: 'file1',
                 dataType: 'json', //返回值类型 一般设置为json
                 success: function (ret, status)  //服务器成功响应处理函数
                 {  
                 	//$("#file1").val("");//清空指定ID
                 	//debugger;
                 	var imgPreview1 = document.getElementById('preview1'); 
				     imgPreview1.innerHTML = '<img  src=${ctxFile}/'+ ret.prizeUrl +' />'; 
                 	$("#picUrlTemp").val(""+ret.prizeUrl);
             	    // if(ret.code==1){
             		 //   createShowingTable(ret);
             		 //   showMsg(ret.msg);
             	     //  } else {
             	    //	  showMsg(ret.msg);
             	      // } 
                 },
                 error: function (ret, status)//服务器响应失败处理函数
                 {
                	 $.jBox.tip("上传失败!");
                 }
             }
         )
     } */
	
	$(function(){
		
		
		//二维码名称
		$("#wechatName").change(function(){
			$("#nameIsOkSpan").html("");
			var wechatMaterialVo={};
			//二维码编码
			var wechatName = $.trim($("#wechatName").val());
			wechatMaterialVo.wechatName=wechatName;
			var  url ='${ctx}/hcf/wechatMaterial/checkNameOfNo';
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(wechatMaterialVo),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		if(data.code=="0"){
		       			$("#nameIsOkSpan").css("color","green");
		       		}else{
		       			$("#nameIsOkSpan").css("color","red");
		       		}
		       		$("#nameIsOkSpan").html(data.msg);
		       		//可用为0，不可用为1
		       		$("#nameIsOk").val(data.code);
		       	 }
			});  
		});
		//二维码编码
		$("#wechatNo").blur(function(){
		    $("#noIsOkSpan").html("");
			var wechatMaterialVo={};
			//二维码编码
			var wechatNo = $.trim($("#wechatNo").val());
			wechatMaterialVo.wechatNo=wechatNo;
			var  url ='${ctx}/hcf/wechatMaterial/checkNameOfNo';
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(wechatMaterialVo),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		if(data.code=="0"){
		       			$("#noIsOkSpan").css("color","green");
		       		}else{
		       			$("#noIsOkSpan").css("color","red");
		       		}
		       		$("#noIsOkSpan").html(data.msg);
		       		//可用为0，不可用为1
		       		$("#noIsOk").val(data.code);
		       	 }
			});  
		});
		
		
	
		
		//保存
		$("#btnsave").click(function(){
			
			
			if(!validationF()){
				return false;
			}
			var wechatMaterialVo={};
			//id
			var id = $.trim($("#idTemp").val());
			//二维码名称
			var wechatName = $.trim($("#wechatName").val());
			//二维码编码
			var wechatNo = $.trim($("#wechatNo").val());
			//开始时间
			var startTime = $.trim($("#startTime").val());
			//结束时间
			var endTime = $.trim($("#endTime").val());
			//顶部图片
			var topUrl = $.trim($("#topUrlTemp").val());
			//底部图片
			var bottomUrl = $.trim($("#bottomUrlTemp").val());
			//二维码logo图片
			var logoUrl = $.trim($("#logoUrlTemp").val());
			
			wechatMaterialVo.id=id;
			wechatMaterialVo.wechatName=wechatName;
			wechatMaterialVo.wechatNo=wechatNo;
			wechatMaterialVo.startTime=startTime;
			wechatMaterialVo.endTime=endTime;
			wechatMaterialVo.topUrl=topUrl;
			wechatMaterialVo.bottomUrl=bottomUrl;
			wechatMaterialVo.logoUrl=logoUrl;
			
			//data.lottoVendVoList=lottoVendVoList;
			var id = $("#idTemp").val()
			if (id=undefined||id=='') {
				var  url ='${ctx}/hcf/wechatMaterial/save';
			}else{
				var  url ='${ctx}/hcf/wechatMaterial/update';
			}
			console.log(JSON.stringify(wechatMaterialVo));
			
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(wechatMaterialVo),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		if(data.code=="0"){
			       		$.jBox.tip(data.msg);
			         	closeLoading()
		       			window.location.href="${ctx}/hcf/wechatMaterial/list";
		       		}else{
		       			$.jBox.tip(data.msg);
		       		}
		       	 }
			});  
		});
		
		//取消
		$("#btncancel").click(function(){
			closeLoading()
   			window.location.href="${ctx}/hcf/wechatMaterial/list";
		});
		
	});
</script>

<style>

.td_style_1 {
	width: 15%;
	text-align: center;
}

.td_style_2 {
	width: 35%;
	text-align: center;
}


</style>

<form:form id="wechatMaterialForm" modelAttribute="wechatMaterialVo"
	action="${ctx}/hcf/wechatMaterial/upload" method="post"
	enctype="multipart/form-data" class="breadcrumb form-search">
	<input id="idTemp" name="id" type="hidden" value="" />
	<input id="topUrlTemp" name="topUrl" type="hidden" value="" />
	<input id="bottomUrlTemp" name="bottomUrl" type="hidden" value="" />
	<input id="logoUrlTemp" name="logoUrl" type="hidden" value="" />
	<input id="typeTemp" type="hidden" value="" />
	<!-- 可用为0，不可用为1 -->
	<input id="nameIsOk" type="hidden" value="" />
	<input id="noIsOk" type="hidden" value="" />
	
	<table id="wechat_table" class="table table-striped table-bordered">
		<tr class="person_type">
			<td class="td_style_1"><font size="4" color="red">*</font>二维码名称</td>
			<td class="td_style_2"><input id="wechatName" type="text" name="wechatName" /><span id="nameIsOkSpan"></span></td>
			<td class="td_style_1"><font size="4" color="red">*</font>二维码编码</td>
			<td class="td_style_2"><input id="wechatNo" type="text" name="wechatNo" /><span id="noIsOkSpan"></span></td>
			<!-- <td class="td_style_2">
				<input id="wechatNo" type="text" name="wechatNo" maxlength="12" onkeyup="value=value.replace(/[\W]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
			</td> -->
		</tr>
		<tr class="person_type">
			<td class="td_style_1"><font size="4" color="red">*</font>开始时间</td>
			<td class="td_style_2">
						<input id="startTime" name="startTime" type="text"  maxlength="20" class="input-medium Wdate " value=""
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
			</td>
			<td class="td_style_1"><font size="4" color="red">*</font>结束时间</td>
			<td class="td_style_2">
						<input id="endTime" name="endTime" type="text"  maxlength="20" class="input-medium Wdate " value=""
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" readonly="readonly"/>
			</td>
		</tr>
		
		
		
		<tr class="person_type">
			<td class="td_style_1"><font size="4" color="red">*</font>顶部图片</td>
			<td colspan="3">
			
				<div id="progressBar1" class="prog_border" align="left"
					style="background-color: green; width: 100px; display: none; margin-left: 200px">
			
					<img id="imgProgress1"
						src="/hcoffee/static/jingle/image/ui-bg_gloss-wave_35_f6a828_500x100.png"
						style="width: 1px; height: 13px;">
				</div>
			
				<span id="progressPercent1"
					style="width: 40px; display: none; margin-left: 200px">0%</span>
			
			
				<div class="pure-g" id="imgButton1" style="display: block">
					<div class="pure-u-2-3">
						<div class="control-group" style="margin-left: 200px">
							<font color="red">*&nbsp &nbsp &nbsp </font>
							<a href="javascript:;" class="file"
								style="margin-left: 20px; border-left-style: solid;">选择图片 <input
								id="file1" type="file"  onchange="previewFrist1(this)">
							</a>
						</div>
					</div>
				</div>
			
				<div class="pure-g" id="imgDiv" style="display: block">
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
							<div style="color: red; width: 500px" id="textTipDiv1" >图片大小不能超过1080x1920像素，大小不能超过1M,支持jpg、jpeg、png等格式</div>
						</div>
					</div>
				</div>
			</td>
		</tr>
		
		
		
		<tr class="person_type">
			<td class="td_style_1"><font size="4" color="red">*</font>底部图片</td>
			<td colspan="3">
			
				<div id="progressBar2" class="prog_border" align="left"
					style="background-color: green; width: 100px; display: none; margin-left: 200px">
			
					<img id="imgProgress2"
						src="/hcoffee/static/jingle/image/ui-bg_gloss-wave_35_f6a828_500x100.png"
						style="width: 1px; height: 13px;">
				</div>
			
				<span id="progressPercent2"
					style="width: 40px; display: none; margin-left: 200px">0%</span>
			
			
				<div class="pure-g" id="imgButton2" style="display: block">
					<div class="pure-u-2-3">
						<div class="control-group" style="margin-left: 200px">
							<font color="red">*&nbsp &nbsp &nbsp </font>
							<a href="javascript:;" class="file"
								style="margin-left: 20px; border-left-style: solid;">选择图片 <input
								id="file2" type="file"  onchange="previewFrist2(this)">
							</a>
						</div>
					</div>
				</div>
			
				<div class="pure-g" id="imgDiv" style="display: block">
					<div class="pure-u-2-3">
						<div class="control-group">
							<div class="boxs" style="margin-left: 200px">
								<div class="box-left" id="preview2"
								></div>
							</div>
						</div>
					</div>
				</div>
				
				
				<div class="pure-g">
					<div class="pure-u-2-3">
						<div class="control-group" style="margin-left: 50px">
							<div style="color: red; width: 500px" id="textTipDiv2" >图片大小不能超过1080x1920像素，大小不能超过1M,支持jpg、jpeg、png等格式</div>
						</div>
					</div>
				</div>
				
				
			</td>
		</tr>
		
		
		
		<tr class="person_type">
			<td class="td_style_1"><font size="4" color="red">*</font>LOGO图片</td>
			<td colspan="3">
			
				<div id="progressBar3" class="prog_border" align="left"
					style="background-color: green; width: 100px; display: none; margin-left: 200px">
			
					<img id="imgProgress3"
						src="/hcoffee/static/jingle/image/ui-bg_gloss-wave_35_f6a828_500x100.png"
						style="width: 1px; height: 13px;">
				</div>
			
				<span id="progressPercent3"
					style="width: 40px; display: none; margin-left: 200px">0%</span>
			
			
				<div class="pure-g" id="imgButton3" style="display: block">
					<div class="pure-u-2-3">
						<div class="control-group" style="margin-left: 200px">
							<font color="red">*&nbsp &nbsp &nbsp </font>
							<a href="javascript:;" class="file"
								style="margin-left: 20px; border-left-style: solid;">选择图片 <input
								id="file3" type="file"  onchange="previewFrist3(this)">
							</a>
						</div>
					</div>
				</div>
			
				<div class="pure-g" id="imgDiv" style="display: block">
					<div class="pure-u-2-3">
						<div class="control-group">
							<div class="boxs" style="margin-left: 200px">
								<div class="box-left" id="preview3"
								></div>
							</div>
						</div>
					</div>
				</div>
			
				<div class="pure-g">
					<div class="pure-u-2-3">
						<div class="control-group" style="margin-left: 50px">
							<div style="color: red; width: 500px" id="textTipDiv3" >图片大小不能超过1080x1920像素，大小不能超过1M,支持jpg、jpeg、png等格式</div>
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>
	
	
	
	
	<div class="form-horizontal">
		<br> <br>
		<div class="pure-g" style="margin-left: 50px">
			<div class="pure-u-2-3">
				<div class="control-group">
					<div class="controls">
						<input id="btnsave" name="btnsave" class="btn" type="button" 
							value="保存" />&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp
						<button id="btncancel" type="button" class="btn btn-default" data-dismiss="modal">取消
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>


