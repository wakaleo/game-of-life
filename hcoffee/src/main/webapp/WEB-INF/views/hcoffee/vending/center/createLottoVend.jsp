<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript" src="${ctxStatic }/jquery/ajaxfileupload.js"></script>
<script type="text/javascript">
//当前总概率
var totalProbability=0;


	//解出关联
	/* function outRelevance(obj){
		$(obj).parent().parent().remove();
	} */
	
	//删除奖品
	function deletePrize(obj,i){
		if(confirm("确认删除奖品吗？删除后请确认中奖概率总和为1！")){
			//totalProbability=floatSub(totalProbability, $("#"+i+"probability").val());
			$(obj).parent().parent().remove();
			var tLength=$("#prize_body tr").length;
			console.log("de:"+tLength)
			for (var j = i; j < tLength; j++) {
				$("#prize_body").find("tr").eq(j).find("td").eq(0).text(j+1);
				$("#prize_body").find("tr").eq(j).find("[name=id]").attr('id',j+"id");
				$("#prize_body").find("tr").eq(j).find("[name=prizeName]").attr('id',j+"prizeName");
				$("#prize_body").find("tr").eq(j).find("[name=prizeType]").attr('id',j+"prizeType");
				$("#prize_body").find("tr").eq(j).find("[name=shelf]").attr('id',j+"shelf");
				$("#prize_body").find("tr").eq(j).find("[name=goodsID]").attr('id',j+"goodsID");
				$("#prize_body").find("tr").eq(j).find("[name=prizeUrl]").attr('id',j+"prizeUrl");
				$("#prize_body").find("tr").eq(j).find("[name=prizeNum]").attr('id',j+"prizeNum");
				$("#prize_body").find("tr").eq(j).find("[name=probability]").attr('id',j+"probability");
				$("#prize_body").find("tr").eq(j).find("[name=sort]").attr('id',j+"sort");
			}
			newForm();
			
			/* var  url ='${ctx}/hcf/lottoVend/deletePrize';
			var data = {};
			data.id = id;
			$.ajax({
				 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(data),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		$.jBox.tip(data.msg);
		       		if (data.code==0) {
		       			$("#leftProbability").html("");
					}
		       	 }
			}); */
		}
	}
	
////////上传图片/////////////////////
	

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
		var FileController = "${ctx}/hcf/lottoVend/uploadPic" // 接收上传文件的后台地址 
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
	            $("#picUrlTemp").val(""+$responseJson.prizeUrl);
	            
	        }  
	    }  
	    
		xhr.onload = function() {
			// ShowSuccess("上传完成");
			progressEnd();
			//alert("上传完成");
			$("#file1").val("");
			//$('#myModal').modal('hide');
		};
		xhr.upload.addEventListener("progress", progressFunction, false);
		 if (!checkFile(file))
			   return;
		 if (file.files && file.files[0]) {
				reader.onload = function(evt) {
					prevDiv.innerHTML = '<img width="100" src="' + evt.target.result + '" />';
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
				//console.log("2"+file.value);
			}
		xhr.send(form);
		
		<!--上传图片到指定地址 -->
		
		
		var prevDiv = document.getElementById('preview1');
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
	

/////////////////////////////////
		
	
	//表单验证
	function validationF(){
			
		//活动ID
		var activityNo = $("#activityNoTemp").val()==''||$("#activityNoTemp").val()==undefined?$("#activityNoWindow").val():$("#activityNoTemp").val();
		if(activityNo == ''){
			$.jBox.tip('请选择活动名称!');
			return false;
		}
		
		//机器
		var vendCode =  $("#vendCodeTemp").val()==''||$("#vendCodeTemp").val()==undefined?$("#vendCodeWindow").val():$("#vendCodeTemp").val();
		//console.log("!!!!"+$("#vendCodeTemp").val());
		//console.log("??????????"+vendCode);
		if(vendCode == ''){
			$.jBox.tip('请选择机器!');
			return false;	
		}
		
		//奖品名称
		var prizeName = $.trim($("#prizeNameWindow").val());
		if(prizeName == ''){
			$.jBox.tip('奖品名称不能为空!');
			return false;
		}
		
		//奖品类型
		var prizeType = $("#prizeTypeWindow").val();
		if(prizeType == ''){
			$.jBox.tip('请选择奖品类型!');
			return false;
		}
		//奖品货道和商城商品ID
		var shelf = $.trim($("#shelfWindow").val());
		var goodsID = $.trim($("#goodsIDWindow").val());
		if(prizeType=='1'){
			if(shelf==''){
				$.jBox.tip('货道商品的货道不能为空!');
				return false;
			}
		}else if(prizeType=='2'){
			if(goodsID==''){
				$.jBox.tip('商城商品的商品ID不能为空!');
				return false;
			}
		}
		
		//奖品数量
		var prizeNum = $.trim($("#prizeNumWindow").val());
		if(prizeNum == ''){
			$.jBox.tip('奖品数量不能为空!');
			return false;
		}else if(!prizeNum.match(/^[0-9]*$/)){
			$.jBox.tip('奖品数量只能是数字!');
			return false;
		}else if(prizeNum<0){
			$.jBox.tip('奖品数量不能小于0!');
			return false;
		}
		//概率
		var probability = $.trim($("#probabilityWindow").val());
		if(probability == ''){
			$.jBox.tip('概率不能为空!');
			return false;
		}else if(!probability.match(/^\d+(?:\.\d{1,6})?$/)){
			$.jBox.tip('概率只能是数字和小数点，且小数后只能有6位!');
			return false;
		}else if(probability<0||probability>1){
			$.jBox.tip('概率不能小于0也不能大于1!');
			return false;
		}else if(floatAdd(probability,totalProbability)>1){
			$.jBox.tip('概率总和不能大于1!');
			return false;
		}
		
		/* if($("#prizeNameIsOk").val()!='1'){
			$.jBox.tip('奖品名不能重复');
			return false;
		} */
		
		var picUrlTemp=$.trim($("#picUrlTemp").val());
		console.log()
		if(picUrlTemp == ''){
			$.jBox.tip('图片地址不能为空!');
			return false;
		}
		//排序
		var prizeName = $.trim($("#sortWindow").val());
		if(prizeName == ''){
			$.jBox.tip('排序不能为空!');
			return false;
		}
		
		return true;
	}
	
	//表格表单验证
	function validationT(i){
			
		//活动ID
		var activityNo = $("#activityNoTemp").val()==''||$("#activityNoTemp").val()==undefined?$("#activityNoWindow").val():$("#activityNoTemp").val();
		if(activityNo == ''){
			$.jBox.tip('请选择活动名称!');
			return false;
		}
		
		//机器
		var vendCode =  $("#vendCodeTemp").val()==''||$("#vendCodeTemp").val()==undefined?$("#vendCodeWindow").val():$("#vendCodeTemp").val();
		if(vendCode == ''){
			$.jBox.tip('请选择机器!');
			return false;
		}
		
		//奖品名称
		/* var prizeName = $.trim($("#"+i+"prizeName").val()); 
		if(prizeName == ''){
			$.jBox.tip('第'+i+'行奖品名称不能为空!');
			return false;
		}
		
		//奖品类型
		 var prizeType = $("#"+i+"prizeType").val();
		if(prizeType == ''){
			$.jBox.tip('第'+i+'行请选择奖品类型!');
			return false;
		} 
		//奖品货道和商城商品ID
		 var shelf = $.trim($("#"+i+"shelf").val());
		var goodsID = $.trim($("#"+i+"goodsID").val());
		if(prizeType=='1'){
			if(shelf==''){
				$.jBox.tip('第'+i+'行货道商品的货道不能为空!');
				return false;
			}
		}else if(prizeType=='2'){
			if(goodsID==''){
				$.jBox.tip('第'+i+'行商城商品的商品ID不能为空!');
				return false;
			 }
		} */
		
		//奖品数量
		/* var prizeNum = $.trim($("#"+i+"prizeNum").val());
		if(prizeNum == ''){
			$.jBox.tip('第'+i+'行奖品数量不能为空!');
			return false;
		}else if(!prizeNum.match(/^[0-9]*$/)){
			$.jBox.tip('第'+i+'行奖品数量只能是数字!');
			return false;
		}else if(prizeNum<0){
			$.jBox.tip('第'+i+'行奖品数量不能小于0!');
			return false;
		} */
		//概率
		/* var probability = $.trim($("#"+i+"probability").val());
		
		if(probability == ''){
			$.jBox.tip('第'+i+'行概率不能为空!');
			return false;
		}else if(!probability.match(/^\d+(?:\.\d{1,6})?$/)){
			$.jBox.tip('第'+i+'行概率只能是数字和小数点，且小数后只能有6位!');
			return false;
		}else if(probability<0||probability>1){
			$.jBox.tip('第'+i+'行概率不能小于0也不能大于1!');
			return false;
		}else  */ 
		console.log("finaly:"+totalProbability);
		if(floatSub(totalProbability, 1.0)>0.0000001){
			$.jBox.tip('概率总和必须为1!');
			return false;
		}
		
		return true;
	}
	
	 //加法  
	function floatAdd(arg1,arg2){
	     var r1,r2,m;  
	     try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}  
	     try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}  
	     m=Math.pow(10,Math.max(r1,r2));  
	     return (arg1*m+arg2*m)/m;  
	}  
	function floatSub(arg1,arg2){
	      var r1,r2,m,n;  
	      try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}  
	      try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}  
	      m=Math.pow(10,Math.max(r1,r2));  
	      //动态控制精度长度  
	      n=(r1>=r2)?r1:r2;  
	      n=n>6?6:n;
	      return ((arg1*m-arg2*m)/m).toFixed(n);  
	  }  
	
	
	
	//奖品列表
	function prizeList(){
		$("#leftProbability").html("");
		var prizeData = {};
		var activityNo = $("#activityNoTemp").val()==''||$("#activityNoTemp").val()==undefined?$("#activityNoWindow").val():$("#activityNoTemp").val();
		var vendCode = $("#vendCodeTemp").val()==''||$("#vendCodeTemp").val()==undefined?$("#vendCodeWindow").val():$("#vendCodeTemp").val();
		
		if (activityNo!=''&&vendCode!='') {
			prizeData.activityNo = activityNo;
			prizeData.vendCode = vendCode;
			var  url ='${ctx}/hcf/lottoVend/getPrizeList';
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(prizeData),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		//当前该活动该机器的总概率
		       		totalProbability=data.totalProbability;
		       		
		       		var prizeList=data.prizeList;
		       		
		       		var tr_html = "";
					var hidden_id = "";
					var hidden_prizeName = "";
					var hidden_prizeType = "";
					var hidden_shelf = "";
					var hidden_goodsID = "";
					var hidden_prizeUrl = "";
					var hidden_prizeNum = "";
					var hidden_probability = "";
					var hidden_sort = "";
					var prizeTypeStr="";
					for(var i=0;i<prizeList.length;i++){
						
						
						if (prizeList[i].prizeType==1) {
							prizeTypeStr="货道商品";
						}else if (prizeList[i].prizeType==2) {
							prizeTypeStr="商城商品";
						}
						hidden_id = i + "id"
						hidden_prizeName= i + "prizeName"
						hidden_prizeType= i + "prizeType"
						hidden_shelf= i + "shelf"
						hidden_goodsID= i + "goodsID"
						hidden_prizeUrl= i + "prizeUrl"
						hidden_prizeNum= i + "prizeNum"
						hidden_probability= i + "probability"
						hidden_sort= i + "sort"
						
						tr_html += "<tr><td  width='5%'>"+(i+1)+ "<input id='"+hidden_id+"' type='hidden' name='id' value='"+prizeList[i].id+"'/></td>";
					    tr_html +="<td width='15%'>"+prizeList[i].prizeName+"<input id='"+hidden_prizeName+"' type='hidden' name='prizeName' value='"+prizeList[i].prizeName+"'/></td>";
					    tr_html +="<td width='10%'>"+prizeTypeStr+"<input id='"+hidden_prizeType+"' type='hidden' name='prizeType' value='"+prizeList[i].prizeType+"'/></td>";
					    tr_html +="<td width='5%'>"+prizeList[i].shelf+"<input id='"+hidden_shelf+"' type='hidden' name='shelf' value='"+prizeList[i].shelf+"'/></td>";
					    tr_html +="<td width='10%'>"+prizeList[i].goodsID+"<input id='"+hidden_goodsID+"' type='hidden' name='goodsID' value='"+prizeList[i].goodsID+"'/></td>";
					    tr_html +="<td width='15%'><img height='120px' src='${ctxFile}/"+prizeList[i].prizeUrl+"'><input id='"+hidden_prizeUrl+"' type='hidden' name='prizeUrl' value='"+prizeList[i].prizeUrl+"'/></td>";
					    tr_html +="<td width='10%'>"+prizeList[i].prizeNum+"<input id='"+hidden_prizeNum+"' type='hidden' name='prizeNum' value='"+prizeList[i].prizeNum+"'/></td>";
					    tr_html +="<td width='10%'>"+prizeList[i].probability+"<input id='"+hidden_probability+"' type='hidden' name='probability' value='"+prizeList[i].probability+"'/></td>";
					    tr_html +="<td width='10%'>"+prizeList[i].sort+"<input id='"+hidden_sort+"' type='hidden' name='sort' value='"+prizeList[i].sort+"'/></td>";
					    tr_html += "<td style='text-align:right'>";
					    tr_html += "<input onclick='editPrizeInfo(this,"+i+")' class='btn btn-primary' type='button' value='修改'/>";
					    tr_html += "<input onclick='deletePrize(this,"+i+")' class='btn btn-primary' type='button' value='删除'/>";
					    tr_html += "</td></tr>";
					}
					$("#prize_body").html(tr_html);
					$("#vendCodeTemp").val($("#vendCodeWindow").val());
					$("#activityNoTemp").val($("#activityNoWindow").val());
					$("#activityNoWindow").attr("disabled","disabled");
					$("#vendCodeWindow").attr("disabled","disabled");
					newForm();
					//$("#leftProbability").html("剩余概率值："+floatSub(1.0,totalProbability)+"只有当剩余为0才能使用");
		       	 }
			});  
		}
		
	}
	
	function newForm(){
		$("#idWindow").val("");
		//$("#activityNoWindow").select2("data",{"id":'',"text":'请选择'});
		//$("#vendCodeWindow").select2("data",{"id":'',"text":'请选择'});
		
		$("#prizeNameWindow").val("");
		//$("#prizeTypeWindow").select2("data",{"id":'',"text":'请选择'});
		$("#shelfWindow").val("");	
		$("#goodsIDWindow").val("");	
		$("#prizeUrlWindow").val("");	
		$("#prizeNumWindow").val("");	
		$("#probabilityWindow").val("");	
		$("#sortWindow").val("");	
		
		
		setPrizeType();
		
		//清算
		totalProbability=0;
	    for (var i = 0; i < $("#prize_body tr").length; i++) {
	    	var rowNum=$("#rowNum").val();
	    	if(rowNum!='' && rowNum!= undefined && rowNum==i){
	    		continue;
	    	}
	    	totalProbability=floatAdd(totalProbability,$("#"+i+"probability").val());
		}
	    $("#prizeNameMsg").remove();
	    $("#leftProbability").html("剩余概率值："+floatSub(1.0,totalProbability)+"只有当剩余为0才能使用");
	    
		//清除临时值
		$("#preview1 img").remove();	
		//$("#prizeNameIsOk").val("");	
		$("#rowNum").val("");	
		$("#picUrlTemp").val("");	
		
		var file = $("#file1") ;
		file.after(file.clone().val(""));      
		file.remove();  
	}
	
	//根据设置必填奖品类型设置必填
	function setPrizeType(){
		var prizeTyp = $("#prizeTypeWindow").val();
		if (prizeTyp!='' && prizeTyp!= undefined) {
			if (prizeTyp=='1') {
				$("#labelShelf").html("<font id='redStarShelf' color='red'>*</font>&nbsp &nbsp &nbsp 货道：");
				$("#redStarGoodsId").remove();
				$("#shelfWindow").removeAttr("disabled");
				$("#goodsIDWindow").attr("disabled","disabled");
			}else if (prizeTyp=='2') {
				$("#labelGoodsID").html("<font id='redStarGoodsId' color='red'>*</font>&nbsp &nbsp &nbsp 商城商品ID：");
				$("#redStarShelf").remove();
				$("#goodsIDWindow").removeAttr("disabled");
				$("#shelfWindow").attr("disabled","disabled");
			}
		}else {
				$("#redStartShelf").remove();
				$("#redStartGoodsId").remove();
				$("#shelfWindow").removeAttr("disabled");
				$("#goodsIDWindow").removeAttr("disabled");
			
		}
	}
	
	//修改奖品
	function editPrizeInfo(obj,i){
		//console.log(i+":::::::::::"+$("#rowNum").val());
		
		if (parseInt($("#rowNum").val())===parseInt(i)) {
			$.jBox.tip('正在修改中!');
			return false;
		}
		
	    
		//奖品名称
		var prizeName = $.trim($("#"+i+"prizeName").val());
		//奖品类型
		var prizeType = $.trim($("#"+i+"prizeType").val());
		//货道
		var shelf = $.trim($("#"+i+"shelf").val());
		//商城商品ID
		var goodsID = $.trim($("#"+i+"goodsID").val());
		//奖品图片
		var prizeUrl = $.trim($("#"+i+"prizeUrl").val());
		//奖品数量
		var prizeNum = $.trim($("#"+i+"prizeNum").val());
		//概率
		var probability = $.trim($("#"+i+"probability").val());
		//排序
		var sort = $.trim($("#"+i+"sort").val());
		
		
		$("#prizeNameWindow").val(prizeName);
		var prizeTypeStr="";
		if (prizeType==1) {
			prizeTypeStr="货道商品";
		}else if (prizeType==2) {
			prizeTypeStr="商城商品";
		}
		$("#prizeTypeWindow").select2("data",{"id":prizeType,"text":prizeTypeStr});
		setPrizeType();
		$("#shelfWindow").val(shelf);	
		$("#goodsIDWindow").val(goodsID);	
		$("#picUrlTemp").val(prizeUrl);	
		$("#prizeNumWindow").val(prizeNum);	
		$("#probabilityWindow").val(probability);	
		$("#sortWindow").val(sort);	
		
		//$("#prizeNameIsOk").val("1");
		$("#rowNum").val(i);
		//清算
		totalProbability=0;
	    for (var i = 0; i < $("#prize_body tr").length; i++) {
	    	/* var rowNum=$("#rowNum").val();
	    	
	    	console.log("00000000000000000"+rowNum);
	    	if(rowNum!='' && rowNum!= undefined && rowNum==i){
	    		continue;
	    	} */
	    	totalProbability=floatAdd(totalProbability,$("#"+i+"probability").val());
		}
		totalProbability=floatSub(totalProbability, probability);
	    	console.log("1111111111111111"+totalProbability);
		$("#leftProbability").html("剩余概率值："+floatSub(1.0,totalProbability)+"只有当剩余为0才能使用");
		//$("#add_prize").scrollTop(0);
		//$('#add_prize').animate({scrollTop:0},'slow');
		//$('#prizeNameWindow').foucs();
		//跳到顶部
		window.location.href="#file1";
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
		
		//验证奖品名称是否可用
		/* $("#prizeNameWindow").change(function(){
			$("#prizeNameMsg").remove();
			
			var prizeName = $.trim($(this).val());
			console.log(prizeName+"~~~")
			var $this=$(this);
			var rowNum=$("#rowNum").val();
			var rowLength=$("#prize_body tr").length;
			if (prizeName == "" || prizeName == 'undefined') {
				$this.after("<font id='prizeNameMsg' color='red'>奖品名称不能为空!<font>");
				$("#prizeNameIsOk").val("0");
				return ;
			}else if (rowLength==''||rowLength==0||rowLength==undefined) {
				$this.after("<font id='prizeNameMsg' color='green'>奖品名可用<font>");
				$("#prizeNameIsOk").val("1");
				return ;
			}else if (rowNum==''||rowNum==undefined) {
				for (var i = 0; i < rowLength; i++) {
					if(prizeName==$("#"+i+"prizeName").val()){
						$this.after("<font id='prizeNameMsg' color='red'>奖品名不可用<font>");
						$("#prizeNameIsOk").val("0");
						return ;
					}
				}
			}else{
				for(var i = 0; i < rowLength; i++){
					if (i==rowNum) {
						continue;
					}
					if (prizeName==$("#"+i+"prizeName").val()) {
						$this.after("<font id='prizeNameMsg' color='red'>奖品名不可用<font>");
						$("#prizeNameIsOk").val("0");
						return ;
					}
				}
			}
			$this.after("<font id='prizeNameMsg' color='green'>奖品名可用<font>");
			$("#prizeNameIsOk").val("1");
		}); */
		
		//选择活动
		$("#activityNoWindow").change(function(){
			if ($("#isEdit").val()) {
				$("#activityNoTemp").val($("#activityNoWindow").val());
				$("#activityNoWindow").attr("disabled","disabled");
			}else{
				//活动ID
				var activityNo = $("#activityNoTemp").val()==''||$("#activityNoTemp").val()==undefined?$("#activityNoWindow").val():$("#activityNoTemp").val();
				//机器ID
				var vendCode = $("#vendCodeTemp").val()==''||$("#vendCodeTemp").val()==undefined?$("#vendCodeWindow").val():$("#vendCodeTemp").val();
				if(activityNo!=''&&activityNo!= undefined&&vendCode!=''&&vendCode!= undefined){
					$("#prize_body").html('');
					prizeList();
				}
			}
		});
		//选择机器
		$("#vendCodeWindow").change(function(){
			//活动ID
			var activityNo = $("#activityNoTemp").val()==''||$("#activityNoTemp").val()==undefined?$("#activityNoWindow").val():$("#activityNoTemp").val();
			//机器ID
			var vendCode = $("#vendCodeTemp").val()==''||$("#vendCodeTemp").val()==undefined?$("#vendCodeWindow").val():$("#vendCodeTemp").val();
			if(activityNo!=''&&activityNo!= undefined&&vendCode!=''&&vendCode!= undefined){
				$("#prize_body").html('');
				prizeList();
			}
		}); 
		//选择奖品类型
		$("#prizeTypeWindow").change(function(){
			//奖品类型
			setPrizeType();
		}); 
		
		
		//添加
		$("#btnadd").click(function(){
			//表单验证
			if(!validationF()){
				return false;
			}
			var i=0;
			var haveRow=false;
			var rowNum = $("#rowNum").val();
			if (rowNum!='' && rowNum!=undefined) {
				i=Number(rowNum);
				haveRow=true;
			}else{
				//console.log("1111")
				i=$("#prize_body tr").length;
				
			}
			/***抽奖活动基本信息***/
			var id = $("#idWindow").val();
			//活动ID
			var activityNo = $("#activityNoTemp").val()==''||$("#activityNoTemp").val()==undefined?$("#activityNoWindow").val():$("#activityNoTemp").val();
			//机器ID
			var vendCode = $("#vendCodeTemp").val()==''||$("#vendCodeTemp").val()==undefined?$("#vendCodeWindow").val():$("#vendCodeTemp").val();
			//奖品名称
			var prizeName = $.trim($(":input[name='prizeName']").val());
			//奖品类型
			var prizeType = $("#prizeTypeWindow").val();
			//奖品图片
			var prizeUrl =$("#picUrlTemp").val();
			//货道
			var shelf = $.trim($(":input[name='shelf']").val());
			//商城商品ID
			var goodsID = $.trim($(":input[name='goodsID']").val());
			//奖品数量
			var prizeNum = $.trim($(":input[name='prizeNum']").val());
			//概率
			var probability = $.trim($(":input[name='probability']").val());
			//排序
			var sort = $.trim($(":input[name='sort']").val());
			
			var prizeTypeStr="";
			if (prizeType==1) {
				prizeTypeStr="货道商品";
			}else if (prizeType==2) {
				prizeTypeStr="商城商品";
			}
			var tr_html = "";
			var hidden_id = i + "id";
			var hidden_prizeName= i + "prizeName";
			var hidden_prizeType= i + "prizeType";
			var hidden_shelf= i + "shelf";
			var hidden_goodsID= i + "goodsID";
			var hidden_prizeUrl= i + "prizeUrl";
			var hidden_prizeNum= i + "prizeNum";
			var hidden_probability= i + "probability";
			var hidden_sort= i + "sort";
			
			tr_html += "<td  width='5%'>"+(i+1)+ "<input id='"+hidden_id+"' type='hidden' name='id' value='"+id+"'/></td>";
		    tr_html +="<td width='15%'>"+prizeName+"<input id='"+hidden_prizeName+"' type='hidden' name='prizeName' value='"+prizeName+"'/></td>";
		    tr_html +="<td width='10%'>"+prizeTypeStr+"<input id='"+hidden_prizeType+"' type='hidden' name='prizeType' value='"+prizeType+"'/></td>";
		    tr_html +="<td width='5%'>"+shelf+"<input id='"+hidden_shelf+"' type='hidden' name='shelf' value='"+shelf+"'/></td>";
		    tr_html +="<td width='10%'>"+goodsID+"<input id='"+hidden_goodsID+"' type='hidden' name='goodsID' value='"+goodsID+"'/></td>";
		    if (!haveRow) {
		    	prizeUrl=$("#picUrlTemp").val();
		    	console.log("prizeUrl~~~:"+prizeUrl);
			}
		    tr_html +="<td width='15%'><img height='120px' src='${ctxFile}/"+prizeUrl+"'><input id='"+hidden_prizeUrl+"' type='hidden' name='prizeUrl' value='"+prizeUrl+"'/></td>";
		    tr_html +="<td width='10%'>"+prizeNum+"<input id='"+hidden_prizeNum+"' type='hidden' name='prizeNum' value='"+prizeNum+"'/></td>";
		    tr_html +="<td width='10%'>"+probability+"<input id='"+hidden_probability+"' type='hidden' name='probability' value='"+probability+"'/></td>";
		    tr_html +="<td width='10%'>"+sort+"<input id='"+hidden_sort+"' type='hidden' name='sort' value='"+sort+"'/></td>";
		    tr_html += "<td style='text-align:right'>";
		    tr_html += "<input onclick='editPrizeInfo(this,"+i+")' class='btn btn-primary' type='button' value='修改'/>";
		    tr_html += "<input onclick='deletePrize(this,"+i+")' class='btn btn-primary' type='button' value='删除'/>";
		    tr_html += "</td>";
		    if (haveRow) {
				$("#prize_body").find("tr").eq(i).html(tr_html);
			}else{
				tr_html="<tr>"+tr_html+"</tr>";
				$("#prize_body").append(tr_html);
			}
		    //$("#picUrlTemp").val("");
		    $("#rowNum").val("");
		    
		    newForm();
		    
		   
		});
		
		//保存
		$("#btnsave").click(function(){
			
			var rowLength=$("#prize_body tr").length;
			console.log("rowLength:"+rowLength)
			for (var i = 0; i < rowLength; i++) {
				//表单验证
				if(!validationT(i)){
					return false;
				}
			}
			var lottoVendVoList = new Array();  
			totalProbability=0;
			for (var i = 0; i < rowLength; i++) {
				var lottoVendVo={};
				//活动ID
				var activityNo = $("#activityNoTemp").val()==''||$("#activityNoTemp").val()==undefined?$("#activityNoWindow").val():$("#activityNoTemp").val();
				//机器ID
				var vendCode = $("#vendCodeTemp").val()==''||$("#vendCodeTemp").val()==undefined?$("#vendCodeWindow").val():$("#vendCodeTemp").val();
				//奖品名称
				var prizeName = $.trim($("#"+i+"prizeName").val());
				//奖品类型
				var prizeType = $.trim($("#"+i+"prizeType").val());
				//货道
				var shelf = $.trim($("#"+i+"shelf").val());
				//商城商品ID
				var goodsID = $.trim($("#"+i+"goodsID").val());
				//奖品图片
				var prizeUrl = $.trim($("#"+i+"prizeUrl").val());
				//奖品数量
				var prizeNum = $.trim($("#"+i+"prizeNum").val());
				//概率
				var probability = $.trim($("#"+i+"probability").val());
				//排序
				var sort = $.trim($("#"+i+"sort").val());
				
				totalProbability=floatAdd(totalProbability,$("#"+i+"probability").val());
				console.log("ppppp"+i+totalProbability);
				
				lottoVendVo.activityNo=activityNo;
				lottoVendVo.vendCode=vendCode;
				lottoVendVo.prizeName=prizeName;
				lottoVendVo.prizeType=prizeType;
				lottoVendVo.shelf=shelf;
				lottoVendVo.goodsID=goodsID;
				lottoVendVo.prizeUrl=prizeUrl;
				lottoVendVo.prizeNum=prizeNum;
				lottoVendVo.probability=probability;
				lottoVendVo.sort=sort;
				lottoVendVoList.push(lottoVendVo);
			}
			if(lottoVendVoList.length==0){
				$.jBox.tip("没有数据");
				return false;
			}
			if (floatSub(totalProbability, 1.0)>0.0000001) {
				$.jBox.tip("概率总和必须为1，当前是："+floatSub(totalProbability, 0.0));
				return false;
			}
			
			console.log(JSON.stringify(lottoVendVoList));
			//data.lottoVendVoList=lottoVendVoList;
			var id = $("#idWindow").val()
			var  url ='${ctx}/hcf/lottoVend/save';
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(lottoVendVoList),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		if(data.code=="0"){
			       		$.jBox.tip(data.msg);
			         	closeLoading()
		       			window.location.href="${ctx}/hcf/lottoVend/list";
		       		}else{
		       			$.jBox.tip(data.msg);
		       		}
		       	 }
			});  
		});
		
		//取消
		$("#btncancel").click(function(){
			closeLoading()
   			window.location.href="${ctx}/hcf/lottoVend/list";
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

<form:form id="prizeForm" modelAttribute="lottoVendVo"
	action="${ctx}/hcf/lottoVend/upload" method="post"
	enctype="multipart/form-data" class="breadcrumb form-search">
	<input id="idWindow" name="id" type="hidden" value="" />
	<!-- <input id="prizeNameIsOk" type="hidden" value="" /> -->
	<input id="rowNum" type="hidden" value="" />
	<input id="picUrlTemp" type="hidden" value="" />
	<input id="vendCodeTemp" type="hidden" value="" />
	<input id="activityNoTemp" type="hidden" value="" />
	<input id="isEdit" type="hidden" value="" />
	<div class="form-horizontal" id="divWindow">


		<div id="progressBar" class="prog_border" align="left"
			style="background-color: green; width: 100px; display: none; margin-left: 200px">

			<img id="imgProgress"
				src="/hcoffeeadmin/static/jingle/image/ui-bg_gloss-wave_35_f6a828_500x100.png"
				style="width: 1px; height: 13px;">
		</div>

		<span id="progressPercent"
			style="width: 40px; display: none; margin-left: 200px">0%</span>


		<div class="pure-g" id="imgButton" style="display: block">
			<div class="pure-u-2-3">
				<div class="control-group" style="margin-left: 200px">
					<font color="red">*&nbsp &nbsp &nbsp </font>
					<a href="javascript:;" class="file"
						style="margin-left: 20px; border-left-style: solid;">选择图片 <input
						id="file1" type="file" name="file" onchange="previewFrist(this)">
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
					<div style="color: red; width: 500px" id="textTipDiv" >图片大小不能超过1080x1920像素，大小不能超过1M,支持jpg、jpeg、png等格式</div>
				</div>
			</div>
		</div>
       
			
			<div class="pure-g showMorediv">
				<div class="pure-u-2-3">
					<div class="control-group">
						
						<label class="control-label"><font color="red">*</font>&nbsp &nbsp &nbsp 活动名称：</label>
						<div class="controls">
							<select id="activityNoWindow" name="activityNo"  style="width: 220px" class="form-control"
								placeholder="选择抽奖方式"><option value="">请选择</option>
								<c:forEach items="${activityList}" var="model" varStatus="indexStatus">
									<option value="${model.activityNo}">${model.activityName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g showMorediv" >
				<div class="pure-u-2-3">
					<div class="control-group">
					
						<label class="control-label"><font color="red">*</font>&nbsp &nbsp &nbsp 活动机器：</label>
						<div class="controls">
							<select id="vendCodeWindow" name="vendCode"  style="width: 220px" class="form-control"
								placeholder="选择机器"><option value="">请选择</option>
								<c:forEach items="${vendCodeList}" var="model" varStatus="indexStatus">
									<option value="${model.vendCode}">${model.vendCode}：${model.location}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>&nbsp &nbsp &nbsp 奖品类型：</label>
						<td>
							<select id="prizeTypeWindow" name="prizeType"  style="width: 220px" class="form-control"
								placeholder="选择奖品类型"><option value="">请选择</option>
								<c:forEach items="${prizeTypeList}" var="model" varStatus="indexStatus">
									<option value="${model.value}">${model.label}</option>
								</c:forEach>
							</select>
						</td>
					</div>
				</div>
			</div>
			
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>&nbsp &nbsp &nbsp 奖品名称：</label>
						<div class="controls">
							<input id="prizeNameWindow"  name=prizeName class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label id="labelShelf" class="control-label">货道：</label>
						<div class="controls">
							<input id="shelfWindow" name=shelf class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label id="labelGoodsID" class="control-label">商城商品ID：</label>
						<div class="controls">
							<input id="goodsIDWindow" name=goodsID class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>&nbsp &nbsp &nbsp 奖品数量：</label>
						<div class="controls">
							<input id="prizeNumWindow" name=prizeNum class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>&nbsp &nbsp &nbsp 概率</label>
						<div class="controls">
							<input id="probabilityWindow" name=probability class="form-control" type="text"  />
							<span id="leftProbability" ></span>
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label"><font color="red">*</font>&nbsp &nbsp &nbsp 排序(升序)</label>
						<div class="controls">
							<input id="sortWindow" name=sort class="form-control" type="text"  />
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
						<input id="btnadd" name="btnadd" class="btn" type="button" 
							value="添加" />&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp
					</div>
				</div>
			</div>
		</div>
	</div>

	</div>
	<h3>奖品列表</h3>
	<table id="prize_table" class="table table-striped table-bordered">
		<thead>
			<tr>
				<td>序号</td>
				<td>奖品名称</td>
				<td>奖品类型</td>
				<td>货道</td>
				<td>商城商品ID</td>
				<td>奖品图片</td>
				<td>奖品数量</td>
				<td>概率</td>
				<td>排序(升序)</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody id="prize_body">
		</tbody>
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


