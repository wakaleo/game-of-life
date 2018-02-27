<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">

	//解出关联
	function outRelevance(obj){
		$(obj).parent().parent().remove();
	}
	
	function deleteFile(fileUrl){
		$.jBox.confirm("确定要删除吗?删除成功后，记得保存!","系统提示",function(v,h,f){
			if(v=="ok"){
				var loginName = $.trim($(":input[name='loginName']").val());
				var url ='${ctx}/hcf/dealerManagerment/deleteFile';
				$.ajax({
					 type:'post',
			       	 url:url,
			       	 data:{"fileUrl":fileUrl,"loginName":loginName},
			       	 dataType:"json",
			       	 success:function(data){
			       		if(data.code == '0'){
			       			$.jBox.tip(data.msg);
			       			var zxcxjgUrl = data.data.cxUrl;
			       			var zjzpUrl = data.data.zPUrl;
			       			var zxjgFileName = data.data.zxjgFileName;
						    var zjzpFileName = data.data.zjzpFileName;
						    
						    console.log("--证件照片文件名:--"+zjzpFileName);
						    
			       			var div_html = "<ul>";
						    if(zxcxjgUrl != '' && zxcxjgUrl != undefined){
						    	if(zxcxjgUrl.indexOf(",") != -1){
							    	var zxcxjgUrls = zxcxjgUrl.split(",");
							    	var zxjgFileNames = zxjgFileName.split(",");
							    	for(var i=0;i<zxcxjgUrls.length;i++){
							    		var file_name = zxcxjgUrls[i].substring(zxcxjgUrls[i].lastIndexOf("/")+1,zxcxjgUrls[i].length);
							    		div_html += "<li>"+zxjgFileNames[i]+"</br><a href='${ctx}/hcf/dealerManagerment/download?filePath="+zxcxjgUrls[i]+"&fileName='"+zxjgFileNames[i]+"'')>下载</a>&nbsp;&nbsp;<a href=javascript:deleteFile('"+zxcxjgUrls[i]+"')>删除</a></li>";
							    	}
							    }else{
							    	var file_name = zxcxjgUrl.substring(zxcxjgUrl.lastIndexOf("/")+1,zxcxjgUrl.length);
							    	div_html += "<li>"+zxjgFileName+"</br><a href='${ctx}/hcf/dealerManagerment/download?filePath="+zxcxjgUrl+"&fileName='"+zxjgFileName+"'')>下载</a>&nbsp;&nbsp;<a href=javascript:deleteFile('"+zxcxjgUrl+"')>删除</a></li>";
							    }
							    div_html += "</ul>"
							    $("#div_zxjg").html(div_html);
						    }else{
						    	$("#div_zxjg").html("");
						    }
						    
						    //证件照片
						    if(zjzpUrl != '' && zjzpUrl != undefined){
						    	var div_html = "<ul>";
							    if(zjzpUrl.indexOf(",") != -1){
							    	var zjzpUrls = zjzpUrl.split(",");
							    	var zjzpFileNames = zjzpFileName.split(",");
							    	for(var i=0;i<zjzpUrls.length;i++){
							    		var file_name = zjzpUrls[i].substring(zjzpUrls[i].lastIndexOf("/")+1,zjzpUrls[i].length);
							    		div_html += "<li>"+zjzpFileNames[i]+"</br><a href='${ctx}/hcf/dealerManagerment/download?filePath="+zjzpUrls[i]+"&fileName='"+zjzpFileNames[i]+"''>下载</a>&nbsp;&nbsp;<a href=javascript:deleteFile('"+zjzpUrls[i]+"')>删除</a></li>";
							    	}
							    }else{
							    	var file_name = zjzpUrl.substring(zjzpUrl.lastIndexOf("/")+1,zjzpUrl.length);
							    	div_html += "<li>"+zjzpFileName+"</br><a href='${ctx}/hcf/dealerManagerment/download?filePath="+zjzpUrl+"&fileName='"+zjzpFileName+"''>下载</a>&nbsp;&nbsp;<a href=javascript:deleteFile('"+zjzpUrl+"')>删除</a></li>";
							    }
							    div_html += "</ul>"
							    $("#div_zjzp").html(div_html);
						    }else{
						    	$("#div_zjzp").html("");
						    }
			       		}
			       	 }
				});
			}
		},{buttonsFocus:1});
		top.$('.jbox-body .jbox-icon').css('top','55px');
	}

	//编辑
	function editOutRelevance(obj,id){
		$(obj).parent().parent().remove();
		var  url ='${ctx}/hcf/dealerManagerment/deleteVendCode';
		$.ajax({
			 type:'post',
	       	 url:url,
	       	 data:JSON.stringify(id),
	       	 dataType:"json",
	       	 contentType:"application/json",
	       	 success:function(data){
	       		$.jBox.tip(data.msg);
	       	 }
		});
	}
	
	//设置投放位置下拉框
	function locationChange(obj,index){
		var data = {};
		var idV = index+"vendCode";
		var idF = index+"location";
		var vendCode = $("#"+idV).find("option:selected").text();
		var val = obj.value;
		data.vendCode = vendCode;
		var  url ='${ctx}/hcf/dealerManagerment/isRepeat';
		$.ajax({
			 type:'post',
	       	 url:url,
	       	 data:JSON.stringify(data),
	       	 dataType:"json",
	       	 contentType:"application/json",
	       	 success:function(data){
				if(data.errorCode == '0'){
					$.jBox.tip(data.msg);
					$("#"+idV).val("");
					$("#"+idF).val("");
				}
				if(data.errorCode == '1'){
					$.jBox.tip(data.msg);
					$("#"+idF).val(obj.value);
					//$("#"+idF).html(obj.value);
				}
	       	 }
		});
	}
	
	//验证银行卡号
	function luhmCheck(bankno) {
	    var lastNum = bankno.substr(bankno.length - 1, 1);//取出最后一位（与luhm进行比较）

	    var first15Num = bankno.substr(0, bankno.length - 1);//前15或18位
	    var newArr = new Array();
	    for (var i = first15Num.length - 1; i > -1; i--) {//前15或18位倒序存进数组
	        newArr.push(first15Num.substr(i, 1));
	    }
	    var arrJiShu = new Array();  //奇数位*2的积 <9
	    var arrJiShu2 = new Array(); //奇数位*2的积 >9

	    var arrOuShu = new Array();  //偶数位数组
	    for (var j = 0; j < newArr.length; j++) {
	        if ((j + 1) % 2 == 1) {//奇数位
	            if (parseInt(newArr[j]) * 2 < 9)
	                arrJiShu.push(parseInt(newArr[j]) * 2);
	            else
	                arrJiShu2.push(parseInt(newArr[j]) * 2);
	        }
	        else //偶数位
	            arrOuShu.push(newArr[j]);
	    }

	    var jishu_child1 = new Array();//奇数位*2 >9 的分割之后的数组个位数
	    var jishu_child2 = new Array();//奇数位*2 >9 的分割之后的数组十位数
	    for (var h = 0; h < arrJiShu2.length; h++) {
	        jishu_child1.push(parseInt(arrJiShu2[h]) % 10);
	        jishu_child2.push(parseInt(arrJiShu2[h]) / 10);
	    }

	    var sumJiShu = 0; //奇数位*2 < 9 的数组之和
	    var sumOuShu = 0; //偶数位数组之和
	    var sumJiShuChild1 = 0; //奇数位*2 >9 的分割之后的数组个位数之和
	    var sumJiShuChild2 = 0; //奇数位*2 >9 的分割之后的数组十位数之和
	    var sumTotal = 0;
	    for (var m = 0; m < arrJiShu.length; m++) {
	        sumJiShu = sumJiShu + parseInt(arrJiShu[m]);
	    }

	    for (var n = 0; n < arrOuShu.length; n++) {
	        sumOuShu = sumOuShu + parseInt(arrOuShu[n]);
	    }

	    for (var p = 0; p < jishu_child1.length; p++) {
	        sumJiShuChild1 = sumJiShuChild1 + parseInt(jishu_child1[p]);
	        sumJiShuChild2 = sumJiShuChild2 + parseInt(jishu_child2[p]);
	    }
	    //计算总和
	    sumTotal = parseInt(sumJiShu) + parseInt(sumOuShu) + parseInt(sumJiShuChild1) + parseInt(sumJiShuChild2);

	    //计算Luhm值
	    var k = parseInt(sumTotal) % 10 == 0 ? 10 : parseInt(sumTotal) % 10;
	    var luhm = 10 - k;
	    var my = false;
	    if (lastNum == luhm) {//Luhm验证通过
	        my = true;
	    }
	    else {//银行卡号必须符合Luhm校验
	        my = false;
	    }
	    return my;
	}
	
	//表单验证
	function validationF(){
		//登录名
		var loginName = $.trim($(":input[name='loginName']").val());
		if(loginName == ''){
			$.jBox.tip('登录名不能为空!');
			return false;
		}else{
			if($("#loginNameIsOk").val() == '0'){
				$.jBox.tip('登录名不可用!');
				return false;
			}
		}
		
		//密码
		var password = $.trim($(":input[name='password']").val());
		if(password == ''){
			$.jBox.tip('密码不能为空!');
			return false;
		}
		
		//邮箱
		var mailbox = $.trim($(":input[name='mailbox']").val());
		if(mailbox == ''){
			$.jBox.tip('邮箱不能为空!');
			return false;
		}else{
			if(!mailbox.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
				$.jBox.tip('邮箱格式不正确！请重新输入');
				return false;
			 }
		}
		
		//电话
		var cellPhone = $.trim($(":input[name='cellPhone']").val());
		if(cellPhone == ''){
			$.jBox.tip('电话不能为空!');
			return false;
		}else{
			if(!cellPhone.match(/^(((13[0-9]{1})|159|153)+\d{8})$/)){
				$.jBox.tip("请正确填写手机号码");
				return false;
			}
		}
		
		//姓名
		var dealerName = $.trim($(":input[name='dealerName']").val());
		if(dealerName == ''){
			$.jBox.tip('姓名不能为空!');
			return false;
		}
		
		//性别
		var gender = $("#sex").val();
		if(gender == ''){
			$.jBox.tip('请选择性别!');
			return false;
		}
		
		//生日
		var birthday = $.trim($(":input[name='birthday']").val());
		if(birthday == ''){
			$.jBox.tip('生日不能为空!');
			return false;
		}
		
		//地区
		var dealerArea = $.trim($(":input[name='dealerArea']").val());
		if(dealerArea == ''){
			$.jBox.tip('地区不能为空!');
			return false;
		}
		var conStatus = $("#conStatusS").val();
		if(conStatus == ''){
			$.jBox.tip('请选择合作状态!');
			return false;
		}
		
		//代理类型
		//var dealerType = $("#dealerTypeS").val();
		var dealerType = $('input[name="dealerType"]:checked').val();
		if(dealerType == ''){
			$.jBox.tip('请选择代理类型!');
			return false;
		}
		
		//代理级别
		var dealerGrade = $("#dealerGradeS").val();
		if(dealerGrade == ''){
			$.jBox.tip('请选择代理级别!');
			return false;
		}
		
		//详细地址
		var detailAddress = $.trim($(":input[name='detailAddress']").val());
		if(detailAddress == ''){
			$.jBox.tip('请输入详细地址!');
			return false;
		}
		
		//身份证号
		var idCard = $.trim($(":input[name='idCard']").val());
		if(dealerType == 1){
			if(idCard == ''){
				$.jBox.tip('身份证号不能空!');
				return false;
			}else{
				if(!idCard.match(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/)){
					$.jBox.tip('身份证号格式不正确! 请重新输入');
					return false;
				}
			}
		}
		//银行卡信息
		var bankCode = $.trim($(":input[name='bankCode']").val());
		if(bankCode == ''){
			$.jBox.tip('请输入开户行信息!');
			return false;
		}
		
		//银行卡号
		var bankCarNumber = $.trim($(":input[name='bankCarNumber']").val());
		if(bankCarNumber == ''){
			$.jBox.tip('银行卡号不能为空!');
			return false;
		}/*else{
			if(!luhmCheck(bankCarNumber)){
				$.jBox.tip('银行卡号格式不正确! 请重新输入');
				return false;
			}
		}*/
		
		//代理商上级
		/*var parentId = $("#parentId").val();
		if(parentId == ''){
			$.jBox.tip('代理商上级不能为空!');
			return false;
		}*/
		return true;
	}
	
	function superiorAgent(obj){
		var  url ='${ctx}/hcf/dealerManagerment/superiorAgent';
		$.ajax({
			 type:'post',
	       	 url:url,
	       	 data:obj.value,
	       	 dataType:"json",
	       	 contentType:"application/json",
	       	 success:function(data){
	       		$("#parentId").empty();
	       		$("#parentId").select2("data",{"id":'',"text":'请选择'});
	       		var dealers = data.dealers;
				$("#select_id").prepend("<option value=''>请选择</option>")
				for(var i=0;i<dealers.length;i++){
					$("#parentId").prepend("<option value='"+dealers[i].id+"'>"+dealers[i].dealerName+"</option>")
				}
	       	 }
		});
	}
	
	$(function(){
		
		//验证登录名是否可用
		$(":input[name='loginName']").change(function(){
			var loginName = $.trim($(this).val());
			var $this = $(this);
			$this.nextAll("font").remove();
			if(loginName != "" && loginName != 'undefined'){
				var  url ='${ctx}/hcf/dealerManagerment/checkLoginName';
				$.ajax({
					 type:'post',
			       	 url:url,
			       	 data:loginName,
			       	 dataType:"json",
			       	 contentType:"application/json",
			       	 success:function(data){
						var code = data.errorCode;
						var msg = data.msg;
						var font = ""
						if(code == '0'){
							$this.after("<font color='red'>"+msg+"<font>");
							$("#loginNameIsOk").val("0");
						}else if(code == '1'){
							$this.after("<font color='green'>"+msg+"<font>");
							$("#loginNameIsOk").val("1");
						}else{
							$.jBox.tip("服务器错误!");
						}
			       	 }
				});
			}else{
				$this.after("<font color='red'>登录名不能为空!<font>");
			}
		});
		
		//新增设备
		$("#addEquipment").click(function(){
			var length = $("#equipment_body tr").length;
			var selectId = length + "vendCode";
			var tr_html = "<tr><td>"+(length + 1)+"</td><td width='30%'>";
		    tr_html += "<div class='controls'><select id='"+length+"vendCode' name='vendCode' class='form-control' style='width: 220px;' onchange='locationChange(this,"+length+")'><option value='请选择'>请选择</option><c:forEach items="${vendCodeList }" var='vend' varStatus='bi'><option value='${vend.location}'>${vend.vendCode }</option></c:forEach></select></div>";
		    tr_html += "</td><td width='30%'>";
		    tr_html += "<div class='controls'><input id='"+length+"location' name='location' readonly='readonly' class='form-control' style='width: 220px;' value=''></input></div>";
		    tr_html += "</td>";
		    tr_html += "<td style='text-align:right'><input onclick='outRelevance(this)' class='btn btn-primary' type='button' value='解除关联'/></td></tr>";
		    $("#equipment_body").append(tr_html);
		    $("#"+selectId).select2();
		});
		
		$("#password_show").blur(function(){
			if ($(this).val() == "") { 
                $("#password").show(); 
                $("#password_show").hide(); 
            } 
		});
		
		$("#password_show").keyup(function(){
			if($(this).val() == ''){
				$("#password").show(); 
				$("#password").val("");
				$("#password_show").hide(); 
			}
		});
		
		function isRepeat(arr){
			for(var i=0;i<arr.length;i++){
				if(arr[i] == arr[i+1]){
					return arr[i];
					break;
				}
			}
		}
		
		//保存
		$("#btnsave").click(function(){
			
			//表单验证
			if(!validationF()){
				return false;
			}
			var data = {};
			/***代理商基本信息***/
			var id = $("#id").val();
			//登录名
			var loginName = $.trim($(":input[name='loginName']").val());
			//邮箱
			var mailbox = $.trim($(":input[name='mailbox']").val());
			//姓名
			var dealerName = $.trim($(":input[name='dealerName']").val());
			//生日
			var birthday = $.trim($(":input[name='birthday']").val());
			//合作状态
			var conStatus = $("#conStatusS").val();
			//代理级别
			var dealerGrade = $("#dealerGradeS").val();
			//身份证号
			var idCard = $.trim($(":input[name='idCard']").val());
			//银行卡号
			var bankCarNumber = $.trim($(":input[name='bankCarNumber']").val());
			//密码
			var password = $.trim($(":input[name='password']").val());
			//电话
			var cellPhone = $.trim($(":input[name='cellPhone']").val());
			//性别
			var gender = $("#sex").val();
			//地区
			var dealerArea = $.trim($(":input[name='dealerArea']").val());
			//代理类型
			//var dealerType = $("#dealerTypeS").val();
			var dealerType = $('input[name="dealerType"]:checked').val()
			//详细地址
			var detailAddress = $.trim($(":input[name='detailAddress']").val());
			//银行卡信息
			var bankCode = $.trim($(":input[name='bankCode']").val());
			//代理商上级
			var parentId = $("#parentId").val();
			//企业名
			var companyName = $.trim($(":input[name='companyName']").val());
			//征信建议
			var zxjy = $("#zxjy_value").val();
			//统一社会信用代码 
			var xyCode = $.trim($(":input[name='xyCode']").val());
			
			/****关联的设备信息*****/
			var leng = $("#equipment_table tbody tr").length;
			var arr_no = new Array(leng);
			var arr_lo = new Array(leng);
			var arr_id = new Array(leng);
			var index = 0;
			$("#equipment_table tbody tr").each(function(){
				var vendCode = "";
				var location = "";
				var hidden_id = "";
				 vendCode = $("#"+index+"vendCode option:selected").text();
				 //location = $("#"+index+"location option:selected").val();
				 location = $("#"+index+"location").val();
				 hidden_id = $("#"+index+"id").val();
				 if(vendCode != '请选择' && vendCode != 'undefined'){
					 arr_no[index] = vendCode;
				 }
				 if(location != '' && location != 'undefined'){
					 arr_lo[index] = location;
				 }
				 if(hidden_id != '' && hidden_id != 'undefined'){
					 arr_id[index] = hidden_id;
				 }
				index++;
			});
			data.loginName = loginName;
			data.mailbox = mailbox;
			data.dealerName = dealerName;
			data.birthday = birthday;
			data.conStatus = conStatus;
			data.dealerGrade = dealerGrade;
			data.idCard = idCard;
			data.bankCarNumber = bankCarNumber;
			data.password = password;
			data.cellPhone = cellPhone;
			data.gender = gender;
			data.dealerArea = dealerArea;
			data.dealerType = dealerType;
			data.detailAddress = detailAddress;
			data.bankCode = bankCode;
			data.id = id;
			data.parentId = parentId;
			
			data.companyName = companyName;
			data.zxjy = zxjy;
			data.xyCode = xyCode;
			
			data.arr_no = arr_no;
			data.arr_lo = arr_lo;
			data.arr_id = arr_id;
			
			var repeatVendCode = isRepeat(arr_no);
			if(repeatVendCode != null && repeatVendCode != ""){
				$.jBox.tip("机械码:<font color='red'><strong>"+repeatVendCode+"</strong></font>不能重复关联!");
				return false;
			}
			var  url ='${ctx}/hcf/dealerManagerment/save';
		    if(data.id != '' && data.id != undefined){
		    	   url ='${ctx}/hcf/dealerManagerment/update';
		    }
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(data),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		if(data.code=="0"){
			       		$.jBox.tip(data.msg);
			         	closeLoading()
		       			window.location.href="${ctx}/hcf/dealerManagerment/list";
		       		}else{
		       			$.jBox.tip(data.msg);
		       		}
		       	 }
			});  
		});
		$("#type_xyCard_tex").hide();
		//$("#zxjy").html("");
		$("#zxjy_tex").hide();
		$("#company_tr").hide();
		//点击代理商类型
		$(":radio").click(function(){
			//alert(this.value);
			if(this.value == 1){
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
			}else if(this.value == 2){
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
		});
		
		//征信查询结果  
		$("#zxjg").click(function(){
			var loginName = $.trim($(":input[name='loginName']").val());
			if(loginName != ''){
				var iTop = (window.screen.availHeight - 668) / 2;
				var iLeft = (window.screen.availWidth - 951) / 2;
				var wd = openWin("${ctx}/hcf/dealerManagerment/dealerFileUpload?type=2&loginName="+loginName+"","window","width=951px,height=668px,top="+iTop+",left="+iLeft+",status=no,scroll=no,help=no,resizable=no,toolbar=no,menubar=no","2",loginName);
			}else{
				$.jBox.tip('登录名不能为空!');
			}
		});
		//证件照片
		$("#zjzp").click(function(){
			var loginName = $.trim($(":input[name='loginName']").val());
			if(loginName != ''){
				var iTop = (window.screen.availHeight - 668) / 2;
				var iLeft = (window.screen.availWidth - 951) / 2;
				var wd = openWin("${ctx}/hcf/dealerManagerment/dealerFileUpload?type=1&loginName="+loginName+"","window","width=951px,height=668px,top="+iTop+",left="+iLeft+",status=no,scroll=no,help=no,resizable=no,toolbar=no,menubar=no","1",loginName); 
			}else{
				$.jBox.tip('登录名不能为空!');
			}
		});
		
		$("#addDivi").click(function(){
			var iTop = (window.screen.availHeight - 668) / 2;
			var iLeft = (window.screen.availWidth - 951) / 2;
			
			//登录名
			var loginName = $.trim($(":input[name='loginName']").val());
			//姓名
			var dealerName = $.trim($(":input[name='dealerName']").val());
			//代理类型
			var dealerType = $('input[name="dealerType"]:checked').val();
			
			if(loginName == ''){
				$.jBox.tip('登录不能为空!');
				return false;
			}
			if(dealerName == ''){
				$.jBox.tip('姓名不能为空!');
				return false;
			}
			if(dealerType == ''){
				$.jBox.tip('代理类型不能为空!');
				return false;
			}
			var wd = openWins("${ctx}/hcf/dealerManagerment/diviSet?dealerType="+dealerType+"&loginName="+loginName+"&dealerName="+dealerName+"","window","width=951px,height=668px,top="+iTop+",left="+iLeft+",status=no,scroll=no,help=no,resizable=no,toolbar=no,menubar=no",loginName); 
		});
		
	});
	
	function openWin(url,text,winInfo,type,loginName){  
	    var winObj = window.open(url,text,winInfo);  
	    var loop = setInterval(function() {       
	        if(winObj.closed) {      
	            clearInterval(loop);      
	            var url ='${ctx}/hcf/dealerManagerment/refreshFileList';
				$.ajax({
			       	 type:'post',
			       	 url:url,
			       	 data:loginName,
			       	 dataType:"json",
			       	 contentType:"application/json",
			       	 success:function(data){
			       		if(data.code=="0"){
				       		var zxcxjgUrl = data.data.cxUrl;
				       		var zjzpUrl = data.data.zPUrl;
				       		var zxjgFileName = data.data.zxjgFileName;
						    var zjzpFileName = data.data.zjzpFileName;
			       			var div_html = "<ul>";
			       			if(type == "1"){
			       				//证件照片
							    if(zjzpUrl != '' && zjzpUrl != undefined){
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
							    }else{
							    	$("#div_zjzp").html("");
							    }
			       			}else if(type == 2){
			       				//征信查询结果
			       				if(zxcxjgUrl != '' && zxcxjgUrl != undefined){
							    	if(zxcxjgUrl.indexOf(",") != -1){
								    	var zxcxjgUrls = zxcxjgUrl.split(",");
								    	var zxjgFileNames = zxjgFileName.split(",");
								    	for(var i=0;i<zxcxjgUrls.length;i++){
								    		var file_name = zxcxjgUrls[i].substring(zxcxjgUrls[i].lastIndexOf("/")+1,zxcxjgUrls[i].length);
								    		div_html += "<li>"+zxjgFileNames[i]+"</br><a href='${ctx}/hcf/dealerManagerment/download?filePath="+zxcxjgUrls[i]+"&fileName="+zxjgFileNames[i]+"')>下载</a>&nbsp;&nbsp;<a href=javascript:deleteFile('"+zxcxjgUrls[i]+"')>删除</a></li>";
								    	}
								    }else{
								    	var file_name = zxcxjgUrl.substring(zxcxjgUrl.lastIndexOf("/")+1,zxcxjgUrl.length);
								    	div_html += "<li>"+zxjgFileName+"</br><a href='${ctx}/hcf/dealerManagerment/download?filePath="+zxcxjgUrl+"&fileName="+zxjgFileName+"')>下载</a>&nbsp;&nbsp;<a href=javascript:deleteFile('"+zxcxjgUrl+"')>删除</a></li>";
								    }
								    div_html += "</ul>"
								    $("#div_zxjg").html(div_html);
							    }else{
							    	$("#div_zxjg").html("");
							    }
					       			
			       			}
			       		}
			       	 }
				}); 
	        }      
	    }, 1);     
	}  
	
	//分帐设置打开窗体
	function openWins(url,text,winInfo,loginName){  
	    var winObj = window.open(url,text,winInfo);  
	    var loop = setInterval(function() {       
	        if(winObj.closed) {      
	            clearInterval(loop); 
	            refreshTemplateList(loginName);
	        }      
	    }, 1);     
	} 
	
	//刷新模版列表
	function refreshTemplateList(loginName){
		$.ajax({
	       	 type:'post',
	       	url:'${ctx}/hcf/dealerManagerment/refreshTemplateList',
	       	 data:{"loginName":loginName},
	       	 dataType:"json",
	       	 success:function(data){
	       		var dvList = data.tempList;
				var html_t = "";
				for(var i=0;i<dvList.length;i++){
					if(i==0)
						mbStr = "<font color='red'>当前模版</font>";
					else
						mbStr = "历史模版";
					html_t += "<tr><td><a href='javascript:showTemplate("+dvList[i].templateId+")'>"+dvList[i].templateName+"</a></td><td>"+dvList[i].useTimeStr+"</td><td>"+dvList[i].operator+"</td><td>"+mbStr+"</td></tr>";
				}
				$("#fenZhangSz_body").html(html_t);
	       	 }
		});
	}
	
	//查看模版
	function showTemplate(templateId){
		var iTop = (window.screen.availHeight - 668) / 2;
		var iLeft = (window.screen.availWidth - 951) / 2;
		window.open("${ctx}/hcf/dealerManagerment/showTemplate?templateId="+templateId,"window","width=951px,height=668px,top="+iTop+",left="+iLeft+",status=no,scroll=no,help=no,resizable=no,toolbar=no,menubar=no");
	}
	
</script>
<style>
<!--
.td_style_1 {
	width: 40%;
	text-align: center;
}

.td_style_2 {
	width: 30%;
	text-align: center;
}
.span_type{
	margin: 0;
	padding: 0;
	line-height: 2px;
}
#div_zxjg{padding:0px;margin: 0px;}
#div_zxjg ul{padding:0px;margin: 0px;}
#div_zxjg ul li{list-style-type: none;}

#div_zjzp{padding:0px;margin: 0px;}
#div_zjzp ul{padding:0px;margin: 0px;}
#div_zjzp ul li{list-style-type: none;}

#fenZhangSz{margin-top: 5px;}
-->
</style>
<form:form id="dealerForm" modelAttribute="dealerManagermentVo"
	action="#" method="post" class="breadcrumb form-search">
	<input id="id" name="id" type="hidden" value="" />
	<input id="loginNameIsOk" name="id" type="hidden" value="" />
	<table class="table table-striped table-bordered">
		<tr class="person_type">
			<td class="td_style_1"><font size="4" color="red">*</font>登录名</td>
			<td class="td_style_2"><input id="loginName" type="text" name="loginName" /><span id="isOk"></span></td>
			<td class="td_style_1"><font size="4" color="red">*</font>密码</td>
			<td class="td_style_2">
				<input id="password" type="text" name="password" maxlength="12" onkeyup="value=value.replace(/[\W]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
				<input id="password_show" type="password" name="password_show" maxlength="12" onkeyup="value=value.replace(/[\W]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
			</td>
		</tr>
		<tr>
			<td class=""><font size="4" color="red">*</font>代理商类型</td>
			<td class="">
				<input type="radio" name="dealerType" value="1" checked="checked">个人代理商&nbsp;&nbsp;
				<input type="radio" name="dealerType" value="2" >公司代理商
			</td>
			<td id="company_name"><font size="4" color="red">*</font>企业名</td>
			<td>
				<input id="companyName" type="text" name="companyName" />
			</td>
		</tr>
		<tr>
			<td id="type_email"><font size='4' color='red'>*</font>邮箱</td>
			<td><input id="mailbox" type="text" name="mailbox" /></td>
			<td id="type_call"><font size='4' color='red'>*</font>电话</td>
			<td><input id="cellPhone" type="text" name="cellPhone" maxlength="11" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/></td>
		</tr>
		<tr>
			<td id="type_name"><font size='4' color='red'>*</font>姓名</td>
			<td><input id="dealerName" type="text" name="dealerName" /></td>
			<td>性别</td>
			<td>
				<div class="controls">
					<select id="sex" name="sex" style="width: 220px" class="form-control"
						placeholder="选择性别"><option value="">请选择</option>
						<c:forEach items="${sexList}" var="model" varStatus="indexStatus">
							<option value="${model.value}">${model.label}</option>
						</c:forEach>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td id="type_brith">生日</td>
			<td><input id="birthday" name="birthday" type="text" maxlength="20"
				class="input-medium Wdate "
				value="<fmt:formatDate value="${uploadAdVo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
				readonly="readonly" /></td>
			<td>地区</td>
			<td>
				<input id="dealerArea" type="text" name="dealerArea" />
			</td>
		</tr>
		<tr>
			<td>合作状态</td>
			<td>
				<div class="controls">
					<select id="conStatusS" name="conStatusS"  style="width: 220px" class="form-control"
						placeholder="选择合作状态"><option value="">请选择</option>
						<c:forEach items="${conStatusList}" var="model" varStatus="indexStatus">
							<option value="${model.value}">${model.label}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td id="zxjy">征信建议</td>
			<td>
				<div class="controls" id="zxjy_tex">
					<select id="zxjy_value" name="zxjy"  style="width: 220px" class="form-control"
						placeholder="选择征信建议">
						<option value="">请选择</option>
						<option value="1">信用良好,建议合作</option>
						<option value="2">有信用风险，建议终止合作</option>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td>代理级别</td>
			<td>
				<div class="controls">
					<select id="dealerGradeS" name="dealerGradeS"  style="width: 220px" class="form-control" onchange="superiorAgent(this)"
						placeholder="选择代理级别"><option value="">请选择</option>
						<c:forEach items="${dealerGradeList}" var="model" varStatus="indexStatus">
							<option value="${model.value}">${model.label}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td>代理商上级</td>
			<td>
				<div class="controls">
					<select id="parentId" name="parentId"  style="width: 220px" class="form-control"
						placeholder="请选择"><option value="">请选择</option>
						<c:forEach items="${superiorAgentList}" var="model" varStatus="indexStatus">
							<option value="${model.id}">${model.dealerName}</option>
						</c:forEach>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td id="type_card">身份证号</td>
			<td id="type_idCard_tex">
				<input id="idCard" type="text" name="idCard" maxlength="18" onkeyup="value=value.replace(/[\W]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
			</td>
			<td id="type_xyCard_tex">
				<input id="xyCode" type="text" name="xyCode" maxlength="18" onkeyup="value=value.replace(/[\W]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
			</td>
			<td><font size='4' color='red'>*</font>开户行信息</td>
			<td><!-- <input id="bankCarInfo" type="text" name="bankCarInfo" /> -->
				<form:select path="bankCode" class="input-xlarge required" style="width:220px;" id="bankCode">
					 	<form:option value="" label="请选择"/>
					 	<c:forEach items="${bankNameList}" var="model" varStatus="indexStatus">
								<form:option value="${model.value }" label="${model.label }"/>
						</c:forEach>
				</form:select>
			</td>
		</tr>
		<tr>
			<td><font size='4' color='red'>*</font>银行卡号</td>
			<td><input id="bankCarNumber" type="text" name="bankCarNumber" maxlength="19" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/></td>
			<td>详细地址</td>
			<td><input id="detailAddress" type="text" name="detailAddress" /></td>
		</tr>
		<!-- 企业显示字段   开始 -->
		<tr id="company_tr">
			<td>征信查询结果</td>
			<td style="text-align: center;">
				<input id="zxjg" type="button" class="btn btn-primary" style="width: 120px;" name="zxjg" value="上传"/>
				<div id="div_zxjg"></div>
			</td>
			<td>证件照片</td>
			<td style="text-align: center;">
				<input id="zjzp" type="button" class="btn btn-primary" style="width: 120px;" name="zjzp" value="上传"/>
				<div id="div_zjzp"></div>
			</td>
		</tr>
		<!-- 企业显示字段   结束 -->
		<tr>
			<td><input id="addEquipment" class="btn btn-primary"
				type="button" value="新增设备" /></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	
	<table id="equipment_table" class="table table-striped table-bordered">
		<thead>
			<tr>
				<td>序号</td>
				<td>机器编码</td>
				<td>投放位置</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody id="equipment_body">
		</tbody>
	</table>
	
	<label>
		<input id="addDivi" class="btn btn-primary" type="button" value="分帐设置" />
	</label>
	<div id="fenZhangSz">
		<table id="fenZhangSz_table" class="table table-striped table-bordered">
		<thead>
			<tr>
				<td>应用分账模板</td>
				<td>应用时间</td>
				<td>操作人</td>
				<td>状态</td>
			</tr>
		</thead>
		<tbody id="fenZhangSz_body">
		</tbody>
	</table>
	</div>

	<div class="form-horizontal">
		<br> <br>
		<div class="pure-g" style="margin-left: 50px">
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
<script type="text/javascript">  

</script> 

