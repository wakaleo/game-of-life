
	/**
	 * 绑定所有单选项，选择显示 事件
	 */
	function radioSwitchForAll(){
		radioSwitchAndShowDiv(".radioSwitch");
	}

	/**
	 * 单选项和对应显示或隐藏属性
	 * template: <div id='radioSwitchId'>
	 *                 <div id='radioSwitchDiv'> radios ....</div>
	 *                 <div ='radioSwitchShowDiv'>  show something</div>
	 *           </div>
	 *       显示属性     id =　radioSwitchShowDiv　+ radios.val()
	 *  radioSwitchIdOrName 必须包含 . 或 # 已区别 id 或 class 没有带默认 Id
	 */
	function radioSwitchAndShowDiv(radioSwitchIdOrName){
		var prefix = radioSwitchIdOrName.substring(0,1);
		var radioSwitchId = radioSwitchIdOrName;
		if(prefix == '.'){
			radioSwitchId = radioSwitchIdOrName.substring(1);
		}else if(prefix == '#'){
			radioSwitchId = radioSwitchIdOrName.substring(1);
		}else{
			prefix = '#';
		}
		$(prefix+radioSwitchId).find("input[type='radio']").change(function(obj){
			var radioVal = $(this).val();
			var $radioSwitchDiv = $(this).parents(prefix+radioSwitchId);
			var $radioSwitchShowDivs = $radioSwitchDiv.find(prefix+radioSwitchId+"ShowDiv").children("div");
			$radioSwitchShowDivs.removeClass("show");
			$radioSwitchShowDivs.addClass("hide");
			var $radioSwitchShowDiv = $radioSwitchDiv.find(prefix+radioSwitchId+"ShowDiv_"+radioVal);
			$radioSwitchShowDiv.removeClass("hide");
			$radioSwitchShowDiv.addClass("show");
		});
	}
	
	
	
	
	/**
	 * 请求数据
	 */
	function request(ajaxURL,param,callback,method){
		if(method){
		}else{
			method = "POST";
		}
	    $.ajax({
	        url: ajaxURL,
	        type: method,
	        dataType: "json",
	        data : param,
	        success: function (data, status) {
	        	if(status == "success" || status == "200"){
	        		callback(data);
	        	}
	        },
	        error: function (status) {
	        	showMsg("request fail");
	        }
	    });
	};
	/**
	 * 组装<select><option> html 
	 */
	function appendOptionHtml(selectData,selectId,optionShowValue,optionValue){
		var $selectHtml = $("#"+selectId);
		$selectHtml.empty();
		$selectHtml.children().remove();
		if(selectData == null || selectData == "undefined"){
			return "";
		}
		var len = selectData.length;
		if(len==0){
			return;
		}
		$selectHtml.append("<option value>-请选择-</option>");
		var selelectedFlag = false;
		for(var i = 0 ;i < len ;i++){
			var selectOne = selectData[i];
			var selelectedHtml = '';
			var defautValue = $selectHtml.attr("val");
			if(defautValue == selectOne[optionValue]){
				selelectedHtml = "selected='selected'"; 
				selelectedFlag = true;
				if($("#s2id_"+selectId+" .select2-chosen")){
					$("#s2id_"+selectId+" .select2-chosen").text(selectOne[optionShowValue]);
					$selectHtml.removeAttr("val");
				}
			}
			if(selectId == "projectidSelect"){
				$selectHtml.append("<option areaname = '"+selectOne['areaName']+"'  value='" + selectOne[optionValue] + "'" + selelectedHtml+">" + selectOne[optionShowValue]+ "</option>");
			}else{
				$selectHtml.append("<option value='" + selectOne[optionValue] + "'" + selelectedHtml+">" + selectOne[optionShowValue]+ "</option>");
			}
		}
		
		if(!selelectedFlag){
			if($("#s2id_"+selectId+" .select2-chosen")){
				$("#s2id_"+selectId+" .select2-chosen").text("请选择");
			}
		}

	}
	

	/**
	 * Pikaday时间格式化方法，默认选择当天以后的时间
	 * format 默认 YYYY-MM-DD
	 * notNow 指定值(任意值)，可选择一年前时间
	 */
	function initPikaday(id,format,notNow){
		var obj = document.getElementById(id);
		if(obj == null || obj == 'undefined'){
			return;
		}
		var now = new Date();
		var begin = (now.getMonth() + 1) + "-" + now.getDate();
		var end = begin;
		var nowYear = now.getFullYear();
		var beginYear = nowYear;
		var endYear = nowYear + 2;
		var defaultDate = defaultDate = obj.value;
		if(defaultDate == null || defaultDate == ''){
			defaultDate = beginYear +"-"+ begin;
		}
		if(notNow){
			beginYear = nowYear - 1;
		}
		begin = beginYear +"-"+ begin;
		end = endYear +"-"+ end;
		if(format == null){
			format = 'YYYY-MM-DD';
		}
		return new Pikaday(
			    {
			        field: document.getElementById(id),
			        firstDay: 1,
			        minDate: new Date(begin),
			        maxDate: new Date(end),
			        yearRange: [beginYear,endYear],
			        defaultDate : defaultDate,
			        format: format
		});
	}
	function showMsg(msg){
		$.jBox.tip(msg,"success");
	}
