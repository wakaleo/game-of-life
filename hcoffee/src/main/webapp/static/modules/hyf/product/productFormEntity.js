/**
 * 
 */
$(document).ready(function() {
	var baseUrl = $("#baseUrl").val();
	
	capitalReq();
	
	productChannel();
	var load = $("#load").val();
	getAllProductType(load);
	
	initDateTypeProperty();
	
	productRevenueRuleShow();
	
	radioSwitchForAll();
	
	bingReturnRate();
	
	bingReturnRate2();
	
	loadThirthInfo();
	
	var projectDefautValue = $("#projectidSelect").attr("val");
	if(projectDefautValue == null || projectDefautValue == ''){
	}else{
		var productTypeId = $("#producttype").attr("val");
		if(productTypeId){
			getProductInfoByProductType(productTypeId);
		}
	}
	/**
	 * 获取所有产品类型
	 */
	function getAllProductType(load){
		var ajaxURL =  baseUrl + "/business/list";
		var param = {"pageSize" : -1};
		var appendHtml = function (data){
			appendProductTypeOptionHtml(data,"producttype","typeName","typeId",load);
		}
		request(ajaxURL,param,appendHtml);
	}
	/**
	 * 绑定产品类型 change事件，级联查询对应产品类型的产品
	 */
	$("#producttype").change(function(obj){
		$("#projectidSelect").empty();
		$("#s2id_projectidSelect .select2-chosen").text("");
		$("#producttitle").val("");
		var subtitle = $(this).find("option:selected").attr("subtitle");
		if(subtitle && subtitle != "undefined"){
			$("#subtitle").val(subtitle);
		}
		var promotion = $(this).find("option:selected").attr("promotion");
		if(promotion && promotion != "undefined"){
			$("#promotion").val(promotion);
		}
		var productTypeId = $("#producttype").val();
		if(productTypeId){
			getProductInfoByProductType(productTypeId);
		}
	});
	function loadThirthInfo(){
		var thirdpartyParentNo = $("#cop_developer").attr("val");
		if(thirdpartyParentNo && thirdpartyParentNo != ''){
			getThirthInfoByDeveplor(thirdpartyParentNo);
		}
		
		var thirdpartyBuilingNo = $("#developer-builing").attr("val");
		if(thirdpartyBuilingNo && thirdpartyBuilingNo != ''){
			getSchemeByDeveplor(thirdpartyBuilingNo);
		}
	}
	$(".addLadderRateRule").click(function(obj){
		addLadderRateRule(this);
	});
	
	$(".deleteLadderRateRule").click(function(obj){
		deleteLadderRateRule(this);
	});
	/**
	 * 根据产品类型获取所有产品
	 * @param productTypeId
	 */
	function getProductInfoByProductType(productTypeId){
		var ajaxURL =  baseUrl + "/productinfo/listJson";
		var appendHtml = function (data){
			appendOptionHtml(data,"projectidSelect","productName","productId");
		}
		var param = {
				"businessTypeValue":productTypeId,
				"pageSize": -1
		}
		request(ajaxURL,param,appendHtml);
	}
	/**
	 * 绑定 产品 change事件，级联更新标的名称
	 */
	$("#projectidSelect").change(function(){
		$("#producttitle").empty();
		var producttitle = $("#projectidSelect option:selected").text();
		$("#producttitle").val(producttitle);
		$("#projectidArea").text($("#projectidSelect option:selected").attr("areaname"));
		queryBuildingByProductInfoId();
	});
	
	/**
	 * 绑定 开发商 change事件，查询楼盘
	 */
	$("#cop_developer").change(function(){
		var thirdpartyParentNo = $("#cop_developer").val();
		if(thirdpartyParentNo == "-1" || thirdpartyParentNo == '' ||  thirdpartyParentNo.indexOf("请选择") >= 0){
			$("#developer-builing").empty();
			$("#s2id_developer-builing .select2-chosen").text("");
			$("#developer-builing").val("");
			
			$("#builing-scheme").empty();
			$("#s2id_builing-scheme .select2-chosen").text("");
			$("#builing-scheme").val("");
			
			$("#btnUpdateBuilding").hide();
			
			$("#btnUpdateBuildingScheme").hide();
			return;
		}
		$("#btnUpdateBuilding").show();
		getThirthInfoByDeveplor(thirdpartyParentNo);
	});
	$("#btnUpdateBuilding").click(function(obj){
		$("#btnUpdateBuilding").attr("disabled","disabled");
		var ajaxURL =  baseUrl + "/product/update";
		request(ajaxURL,null,updateBuildingBack);
	});
	function updateBuildingBack(data){
		$("#btnUpdateBuilding").removeAttr("disabled");
		showMsg(data.msg);
	}
	
	$("#btnUpdateBuildingScheme").click(function(obj){
		$("#builing-scheme").empty();
		$("#s2id_builing-scheme .select2-chosen").text("");
		$("#builing-scheme").val("");
		var thirdpartyRelateInfoNo =  $("#developer-builing").val();
		if(thirdpartyRelateInfoNo == "-1" || thirdpartyRelateInfoNo == '' ||  thirdpartyRelateInfoNo.indexOf("请选择") >= 0){
			return;
		}
		$("#btnUpdateBuildingScheme").attr("disabled","disabled");
		var ajaxURL =  baseUrl + "/product/updateScheme";
		var param = {
				"thirdpartyRelateInfoNo":thirdpartyRelateInfoNo
		}
		request(ajaxURL,param,updateBuildingSchemeBack);
	});
	function updateBuildingSchemeBack(data){
		$("#btnUpdateBuildingScheme").removeAttr("disabled");
		showMsg(data.msg);
		var thirdpartyParentNo = $("#developer-builing").val();
		getSchemeByDeveplor(thirdpartyParentNo);
	}
	
	/**
	 * 绑定 楼盘 change事件，级联查询方案
	 */
	$("#developer-builing").change(function(){
		var thirdpartyParentNo = $("#developer-builing").val();
		getSchemeByDeveplor(thirdpartyParentNo);
	});
	
	/**
	 * 绑定 上线时间 blur事件，级联更新 开标时间
	 */
    function bingOnlinedate(){
		var onlinedate = $("#onlinedate").val();
		if(onlinedate == null) return;
		document.getElementById("investstartdate").value = onlinedate;
	}
	/**
	 * 获取所有资金需求方
	 */
	function capitalReq(){
		var ajaxURL =  baseUrl + "/partner/capitalreq/json/getAll";
		var appendHtml = function (data){
			appendOptionHtml(data,"capitalreqSelect","reqname","id");
		}
		request(ajaxURL,null,appendHtml,"GET");
	}
	
	/**
	 * 获取所有渠道商
	 */
	function productChannel(){
		var ajaxURL =  baseUrl + "/partner/productchannel/json/getAll";
		var appendHtml = function (data){
			appendOptionHtml(data,"partnersiteSelect","name","name");
		}
		request(ajaxURL,null,appendHtml,"GET");
	}
	
	
	/**
	 * @param productTypeId
	 */
	function getThirthInfoByDeveplor(thirdpartyParentNo){
		if(thirdpartyParentNo == "-1" || thirdpartyParentNo == '' ||  thirdpartyParentNo.indexOf("请选择") >= 0){
			$("#builing-scheme").empty();
			$("#s2id_builing-scheme .select2-chosen").text("");
			$("#builing-scheme").val("");
			return;
		}
		var ajaxURL =  baseUrl + "/product/getThirdRelationInfo";
		var appendHtml = function (data){
			appendOptionHtml(data,"developer-builing","buildingName","thirdpartyRelateInfoNo");
		}
		var param = {
				"thirdpartyParentNo":thirdpartyParentNo,
				"pageSize": 2000
		}
		request(ajaxURL,param,appendHtml);
	}
	
	/**	
	 * @param productTypeId
	 */
	function getSchemeByDeveplor(thirdpartyRelateInfoNo){
		if(thirdpartyRelateInfoNo == "-1" || thirdpartyRelateInfoNo == '' ||  thirdpartyRelateInfoNo.indexOf("请选择") >= 0){
			return;
		}
		var ajaxURL =  baseUrl + "/product/getScheme";
		var appendHtml = function (data){
			appendOptionHtml(data,"builing-scheme","schemeName","schemeNo");
		}
		$("#btnUpdateBuildingScheme").show();
		var param = {
				"thirdpartyRelateInfoNo":thirdpartyRelateInfoNo,
				"pageSize": 2000
		}
		request(ajaxURL,param,appendHtml);
	}
	/**
	 * 初始化 有关【标的】 时间 
	 */
	function initDateTypeProperty(){
		// 开标时间
		//initPikaday("investstartdate");
		// 满标时间
		initPikaday("investlimitdateInput");
		// 产品到期结算时间 lifecycleenddateInput
		initPikaday("lifecycleenddateInput");
		// 过期时间 expireddateInput
		initPikaday("expireddateInput");
//		initPikaday("buildingsaledate.description");
	}
	
	function bingReturnRate(){
		$("#returnrate").blur(function(){
			var returnrate = $(this).val();
			if(returnrate == null) return;
			returnrate= parseFloat(returnrate);
			if(returnrate > 0.15){
				showMsg("注意：年化收益"+returnrate*100+"%,已大于15%");
			}
			document.getElementById("productpaymentpropert.displayinterestcashrate").value = "预期年化收益"+returnrate*100+"%";
		});
	}
	
	function bingReturnRate2(){
		$(".returnrate").blur(function(){
			var returnrate = $(this).val();
			if(returnrate == null) return;
			returnrate= parseFloat(returnrate);
			if(returnrate > 0.15){
				showMsg("注意：年化收益"+returnrate*100+"%,已大于15%");
			}
		});
	}
	function queryBuildingByProductInfoId(){
		document.getElementById("buildingaddress.description").value = null;
		var productInfoId= $("#projectidSelect").val();
		if(productInfoId){
			var ajaxURL =  baseUrl + "/building/buildinginfo/getBuildingByProductInfoId";
			var initBuildingData = function (data){
				if(data.data){
					
					document.getElementById("buildingId").value = data.data.buildingId;
					if(data.data.buildingAddress){
						document.getElementById("buildingaddress.description").value = data.data.buildingAddress;
					}
					if(data.data.openTime){
						document.getElementById("buildingsaledate.description").value = data.data.openTime;
					}else{
//						var now = new Date();
//						var nowDate = now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate();
//						document.getElementById("buildingsaledate.description").value = nowDate;
					}
					if(data.data.buildingPrice){
						document.getElementById("buildingprice.description").value = data.data.buildingPrice;
					}
				}
			}
			var param ={
					"productInfoId":productInfoId
			}
			request(ajaxURL,param,initBuildingData);
		}
	}
	/**
	 * 绑定所有单选项，选择显示 事件
	 */
	function radioSwitchForAll(){
		radioSwitchAndShowDiv(".radioSwitch");
	}
	/**
	 * 标的利息利率显示切换
	 */
	function productRevenueRuleShow(){
		var radioSwitchIdOrName = ".productRevenueRule_div";
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
//			$radioSwitchShowDiv.addClass("show");
			var otherRadioVal = null;
			if(radioVal == 1){
				otherRadioVal = 2;
				var $otherRadioShowInput = $radioSwitchShowDivs.parent("div").find(prefix+radioSwitchId+"ShowDiv_"+otherRadioVal).find("input");
				var otherRadioShowInputName = $otherRadioShowInput.attr("name");
				$otherRadioShowInput.attr("name",otherRadioVal + otherRadioShowInputName);
				var $radioShowInput = $radioSwitchShowDiv.find("input");
				var radioShowInputName = $radioShowInput.attr("name");
				var inputPrefix = radioShowInputName.substring(0,1);
				if(inputPrefix == radioVal){
					radioShowInputName = radioShowInputName.substring(1);
					$radioShowInput.attr("name",radioShowInputName);
				}
				$radioSwitchDiv.find(".ladderRevenueRuleTypeValue").val(9999);
			}else if(radioVal == 2){
				otherRadioVal = 1;
				var $otherRadioShowInput = $radioSwitchShowDivs.parent("div").find(prefix+radioSwitchId+"ShowDiv_"+otherRadioVal).find("input");
				var otherRadioShowInputName = $otherRadioShowInput.attr("name");
				$otherRadioShowInput.attr("name",otherRadioVal + otherRadioShowInputName);
				var $radioShowInput = $radioSwitchShowDiv.find("input");
				var radioShowInputName = $radioShowInput.attr("name");
				var inputPrefix = radioShowInputName.substring(0,1);
				if(inputPrefix == radioVal){
					radioShowInputName = radioShowInputName.substring(1);
					$radioShowInput.attr("name",radioShowInputName);
				}
				$radioSwitchDiv.find(".ladderRevenueRuleTypeValue").val(9999);
			}else if(radioVal == 3){
				$radioSwitchDiv.find(".ladderRevenueRuleTypeValue").val(3);
				otherRadioVal = 2;
				var $otherRadioShowInput = $radioSwitchShowDivs.parent("div").find(prefix+radioSwitchId+"ShowDiv_"+otherRadioVal).find("input");
				var otherRadioShowInputName = $otherRadioShowInput.attr("name");
				$otherRadioShowInput.attr("name",otherRadioVal + otherRadioShowInputName);
				var $radioShowInput = $radioSwitchShowDiv.find("input");
				var radioShowInputName = $radioShowInput.attr("name");
				var inputPrefix = radioShowInputName.substring(0,1);
				if(inputPrefix == radioVal){
					radioShowInputName = radioShowInputName.substring(1);
					$radioShowInput.attr("name",radioShowInputName);
				}
				otherRadioVal = 1;
				var $otherRadioShowInput = $radioSwitchShowDivs.parent("div").find(prefix+radioSwitchId+"ShowDiv_"+otherRadioVal).find("input");
				var otherRadioShowInputName = $otherRadioShowInput.attr("name");
				$otherRadioShowInput.attr("name",otherRadioVal + otherRadioShowInputName);
				var $radioShowInput = $radioSwitchShowDiv.find("input");
				var radioShowInputName = $radioShowInput.attr("name");
				var inputPrefix = radioShowInputName.substring(0,1);
				if(inputPrefix == radioVal){
					radioShowInputName = radioShowInputName.substring(1);
					$radioShowInput.attr("name",radioShowInputName);
				}
			}
		});
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
	
	function addLadderRateRule(obj){
		var productType = $(obj).attr("producttype");
		var ladderRateRuleIndex = $("#ladderRateRuleIndex").val();
		ladderRateRuleIndex = parseInt(ladderRateRuleIndex) + 1;
		var html = "<tr  class='name_izi'>";
		html = html + "<td><input type='hidden' value='"+productType+"' name='ladderRateRules["+ladderRateRuleIndex+"].productRevenueType'>";
		html = html + "<input type='hidden' class='ladderRevenueRuleTypeValue' name='ladderRateRules["+ladderRateRuleIndex+"].revenueRuleType' value='3' /></td>";
		html = html + "    <td><input name='ladderRateRules["+ladderRateRuleIndex+"].beginTimeValue'  maxlength='2' class='input-mini required digits'/></td>";
		html = html + "	<td ><label>月——</label></td>";
		html = html + "	<td><input name='ladderRateRules["+ladderRateRuleIndex+"].endTimeValue' maxlength='2' class='input-mini required digits'/></td>";
		html = html + "	<td><label>月</label></td>";
		html = html + "	<td><label>年化利率</label></td>";
		html = html + " <td><input name='ladderRateRules["+ladderRateRuleIndex+"].revenueRuleValue'  maxlength='12' class='input-mini required number returnrate'/></td>";
		html = html + " </tr>";
		$("#ladder_rate_table_"+productType).append(html);
		$("#ladderRateRuleIndex").val(ladderRateRuleIndex);
	};
	
	
	function deleteLadderRateRule(obj){
		var productType = $(obj).attr("producttype");
		var ladderRateRuleIndex = $("#ladderRateRuleIndex").val();
		ladderRateRuleIndex = parseInt(ladderRateRuleIndex) - 1;
		$("#ladder_rate_table_"+productType+" tr:last").html();
		$("#ladder_rate_table_"+productType+" tr:last").remove();
		$("#ladderRateRuleIndex").val(ladderRateRuleIndex);
	};
	
	
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
	        		 if (typeof callback === "function"){
	        			 callback(data);
	        		 }
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
	
	
	function appendProductTypeOptionHtml(selectData,selectId,optionShowValue,optionValue,load){
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
			var categoriesId = selectOne['categoriesId'];
			if(load != "all"){
				if(load){
					if(categoriesId != 5355881){
						continue;
					}
				}else{
					if(categoriesId == 5355881){
						continue;
					}
				}
			}
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
			$selectHtml.append("<option promotion='"+selectOne['promotion']+"' subtitle='"+selectOne['subtitle']+"' value='" + selectOne[optionValue] + "'" + selelectedHtml+">" + selectOne[optionShowValue]+ "</option>");
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
});
