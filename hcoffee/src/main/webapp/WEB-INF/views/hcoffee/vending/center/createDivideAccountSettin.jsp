<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">

//清空
function clearContent(){
	
	$(":input[name='id']").val("");
	
	$("#templateNames").val("");
	$(":input[name='probjectType']:checkbox").each(function(){
		$(this).attr("checked",false);
	});
	$(":input[name='fristMonthOne_sale']").val("");
	$(":input[name='fristMonthTwo_sale']").val("");
	$(":input[name='secondMonthOne_sale']").val("");
	$(":input[name='secondMonthTwo_sale']").val("");
	$(":input[name='threeMonthOne_sale']").val("");
	$(":input[name='threeMonthTwo_sale']").val("");
	$(":input[name='fourthMonthOne_sale']").val("");
	$(":input[name='fourthMonthTwo_sale']").val("");
	
	$(":input[name='saleOne']").val("");
	
	$(":input[name='fristSaleValue_1']").val("");
	$(":input[name='secondSaleValue_1']").val("");
	$(":input[name='threeSaleValue_1']").val("");
	$(":input[name='fourthSaleValue_1']").val("");
	
	$(":input[name='saleSecond_1']").val("");
	$(":input[name='saleSecond_2']").val("");
	
	$(":input[name='fristSaleValue_2']").val("");
	$(":input[name='secondSaleValue_2']").val("");
	$(":input[name='threeSaleValue_2']").val("");
	$(":input[name='fourthSaleValue_2']").val("");
	
	$(":input[name='saleThree']").val("");
	
	$(":input[name='fristSaleValue_3']").val("");
	$(":input[name='secondSaleValue_3']").val("");
	$(":input[name='threeSaleValue_3']").val("");
	$(":input[name='fourthSaleValue_3']").val("");
	
	$(":input[name='fristMonthOne_vend']").val("");
	$(":input[name='fristMonthTwo_vend']").val("");
	$(":input[name='secondMonthOne_vend']").val("");
	$(":input[name='secondMonthTwo_vend']").val("");
	$(":input[name='threeMonthOne_vend']").val("");
	$(":input[name='threeMonthTwo_vend']").val("");
	$(":input[name='fourthMonthOne_vend']").val("");
	$(":input[name='fourthMonthTwo_vend']").val("");
	
	$(":input[name='fristSaleValue_vend']").val("");
	$(":input[name='secondSaleValue_vend']").val("");
	$(":input[name='threeSaleValue_vend']").val("");
	$(":input[name='fourthSaleValue_vend']").val("");
	
	$(":input[name='advertMoney']").val("");
}


$(function(){
	
	$("input[name='settlementWay']").click(function(){
		var tt = $(this).val();
		if(tt == 1){
			$("#parm_div").hide();
			$("#data_div").hide();
		}else{
			$("#parm_div").show();
			$("#data_div").show();
		}
	});
	
	//保存
	$("#btnsave").click(function(){
		var id = $(":input[name='id']").val();
		if(id != '' && id != undefined){
			$.jBox.confirm("若保存会对已应用该模板的所有代理商生效，确认要保存修改？","系统提示",function(v,h,f){
				if(v =="ok"){
					//模版名称
					var templateName = $.trim($("#templateNames").val());
					//项目类型
					var proType = new Array();
					$("#data_div :input[name='probjectType']:checked").each(function(){
						proType.push($(this).val());
					});
					//结算方式
					var settlementWay = $("input[name='settlementWay']:checked").val();
					/*----售货机销售分成--参数设置-start-*/
					var fristMonthOne_sale = $.trim($(":input[name='fristMonthOne_sale']").val());
					var fristMonthTwo_sale = $.trim($(":input[name='fristMonthTwo_sale']").val());
					var secondMonthOne_sale = $.trim($(":input[name='secondMonthOne_sale']").val());
					var secondMonthTwo_sale = $.trim($(":input[name='secondMonthTwo_sale']").val());
					var threeMonthOne_sale = $.trim($(":input[name='threeMonthOne_sale']").val());
					var threeMonthTwo_sale = $.trim($(":input[name='threeMonthTwo_sale']").val());
					var fourthMonthOne_sale = $.trim($(":input[name='fourthMonthOne_sale']").val());
					var fourthMonthTwo_sale = $.trim($(":input[name='fourthMonthTwo_sale']").val());
					
					//销售额  ≤
					var saleOne = $.trim($(":input[name='saleOne']").val());
					/*月份对应的值*/
					var fristSaleValue_1 = $.trim($(":input[name='fristSaleValue_1']").val());
					var secondSaleValue_1 = $.trim($(":input[name='secondSaleValue_1']").val());
					var threeSaleValue_1 = $.trim($(":input[name='threeSaleValue_1']").val());
					var fourthSaleValue_1 = $.trim($(":input[name='fourthSaleValue_1']").val());
					
					//<  销售额  ≤ 
					var saleSecond_1 = $.trim($(":input[name='saleSecond_1']").val());
					var saleSecond_2 = $.trim($(":input[name='saleSecond_2']").val());
					/*月份对应的值*/
					var fristSaleValue_2 = $.trim($(":input[name='fristSaleValue_2']").val());
					var secondSaleValue_2 = $.trim($(":input[name='secondSaleValue_2']").val());
					var threeSaleValue_2 = $.trim($(":input[name='threeSaleValue_2']").val());
					var fourthSaleValue_2 = $.trim($(":input[name='fourthSaleValue_2']").val());
					
					//销售额  <
					var saleThree = $.trim($(":input[name='saleThree']").val());
					/*月份对应的值*/
					var fristSaleValue_3 = $.trim($(":input[name='fristSaleValue_3']").val());
					var secondSaleValue_3 = $.trim($(":input[name='secondSaleValue_3']").val());
					var threeSaleValue_3 = $.trim($(":input[name='threeSaleValue_3']").val());
					var fourthSaleValue_3 = $.trim($(":input[name='fourthSaleValue_3']").val());
					
					
					/*----售货机销售分成--参数设置-end-*/
					
					/*----机器补贴--参数设置-start-*/
					
					/*月份*/
					var fristMonthOne_vend = $.trim($(":input[name='fristMonthOne_vend']").val());
					var fristMonthTwo_vend = $.trim($(":input[name='fristMonthTwo_vend']").val());
					var secondMonthOne_vend = $.trim($(":input[name='secondMonthOne_vend']").val());
					var secondMonthTwo_vend = $.trim($(":input[name='secondMonthTwo_vend']").val());
					var threeMonthOne_vend = $.trim($(":input[name='threeMonthOne_vend']").val());
					var threeMonthTwo_vend = $.trim($(":input[name='threeMonthTwo_vend']").val());
					var fourthMonthOne_vend = $.trim($(":input[name='fourthMonthOne_vend']").val());
					var fourthMonthTwo_vend = $.trim($(":input[name='fourthMonthTwo_vend']").val());
					
					/*补贴*/
					var fristSaleValue_vend = $.trim($(":input[name='fristSaleValue_vend']").val());
					var secondSaleValue_vend = $.trim($(":input[name='secondSaleValue_vend']").val());
					var threeSaleValue_vend = $.trim($(":input[name='threeSaleValue_vend']").val());
					var fourthSaleValue_vend = $.trim($(":input[name='fourthSaleValue_vend']").val());
					
					/*----机器补贴--参数设置-end-*/
					
					//广告费
					var advertMoney = $.trim($(":input[name='advertMoney']").val());
					
					var obj = {};
					obj.id = id;
					
					obj.templateName = templateName;
					obj.probjectType = proType!= "" ? proType.toString() : "";
					obj.settlementWay = settlementWay; 
					
					obj.fristMonthOne_sale = fristMonthOne_sale;
					obj.fristMonthTwo_sale = fristMonthTwo_sale;
					obj.secondMonthOne_sale = secondMonthOne_sale;
					obj.secondMonthTwo_sale = secondMonthTwo_sale;
					obj.threeMonthOne_sale = threeMonthOne_sale;
					obj.threeMonthTwo_sale = threeMonthTwo_sale;
					obj.fourthMonthOne_sale = fourthMonthOne_sale;
					obj.fourthMonthTwo_sale = fourthMonthTwo_sale;
					
					obj.saleOne = saleOne;
					obj.fristSaleValue_1 = fristSaleValue_1;
					obj.secondSaleValue_1 = secondSaleValue_1;
					obj.threeSaleValue_1 = threeSaleValue_1;
					obj.fourthSaleValue_1 = fourthSaleValue_1;
					
					obj.saleSecond_1 = saleSecond_1;
					obj.saleSecond_2 = saleSecond_2;
					obj.fristSaleValue_2 = fristSaleValue_2;
					obj.secondSaleValue_2 = secondSaleValue_2;
					obj.threeSaleValue_2 = threeSaleValue_2;
					obj.fourthSaleValue_2 = fourthSaleValue_2;
					
					obj.saleThree = saleThree;
					obj.fristSaleValue_3 = fristSaleValue_3;
					obj.secondSaleValue_3 = secondSaleValue_3;
					obj.threeSaleValue_3 = threeSaleValue_3;
					obj.fourthSaleValue_3 = fourthSaleValue_3;
					/*补贴月份*/
					obj.fristMonthOne_vend = fristMonthOne_vend;
					obj.fristMonthTwo_vend = fristMonthTwo_vend;
					obj.secondMonthOne_vend = secondMonthOne_vend;
					obj.secondMonthTwo_vend = secondMonthTwo_vend;
					obj.threeMonthOne_vend = threeMonthOne_vend;
					obj.threeMonthTwo_vend = threeMonthTwo_vend;
					obj.fourthMonthOne_vend = fourthMonthOne_vend;
					obj.fourthMonthTwo_vend = fourthMonthTwo_vend;
					
					/*补贴值*/
					obj.fristSaleValue_vend = fristSaleValue_vend;
					obj.secondSaleValue_vend = secondSaleValue_vend;
					obj.threeSaleValue_vend = threeSaleValue_vend;
					obj.fourthSaleValue_vend = fourthSaleValue_vend;
					
					//广告费
					obj.advertMoney = advertMoney;
					
					var  url ='${ctx}/hcf/financialCenter/save';
				    if(obj.id != '' && obj.id != undefined){
				    	url ='${ctx}/hcf/financialCenter/update';
				    }
					$.ajax({
				       	 type:'post',
				       	 url:url,
				       	 data:JSON.stringify(obj),
				       	 dataType:"json",
				       	 contentType:"application/json",
				       	 success:function(data){
				       		if(data.code=="0"){
					       		$.jBox.tip(data.msg);
					         	closeLoading()
				       			window.location.href="${ctx}/hcf/financialCenter/list";
				       		}else{
				       			$.jBox.tip(data.msg);
				       		}
				       	 }
					}); 
				}
			});	   
		}else{
			//模版名称
			var templateName = $.trim($("#templateNames").val());
			//项目类型
			var probjectType = new Array();
			$(":input[name='probjectType']:checkbox:checked").each(function(){
				probjectType.push($(this).val());
			});
			//结算方式
			var settlementWay = $("input[name='settlementWay']:checked").val();
			/*----售货机销售分成--参数设置-start-*/
			var fristMonthOne_sale = $.trim($(":input[name='fristMonthOne_sale']").val());
			var fristMonthTwo_sale = $.trim($(":input[name='fristMonthTwo_sale']").val());
			var secondMonthOne_sale = $.trim($(":input[name='secondMonthOne_sale']").val());
			var secondMonthTwo_sale = $.trim($(":input[name='secondMonthTwo_sale']").val());
			var threeMonthOne_sale = $.trim($(":input[name='threeMonthOne_sale']").val());
			var threeMonthTwo_sale = $.trim($(":input[name='threeMonthTwo_sale']").val());
			var fourthMonthOne_sale = $.trim($(":input[name='fourthMonthOne_sale']").val());
			var fourthMonthTwo_sale = $.trim($(":input[name='fourthMonthTwo_sale']").val());
			
			//销售额  ≤
			var saleOne = $.trim($(":input[name='saleOne']").val());
			/*月份对应的值*/
			var fristSaleValue_1 = $.trim($(":input[name='fristSaleValue_1']").val());
			var secondSaleValue_1 = $.trim($(":input[name='secondSaleValue_1']").val());
			var threeSaleValue_1 = $.trim($(":input[name='threeSaleValue_1']").val());
			var fourthSaleValue_1 = $.trim($(":input[name='fourthSaleValue_1']").val());
			
			//<  销售额  ≤ 
			var saleSecond_1 = $.trim($(":input[name='saleSecond_1']").val());
			var saleSecond_2 = $.trim($(":input[name='saleSecond_2']").val());
			/*月份对应的值*/
			var fristSaleValue_2 = $.trim($(":input[name='fristSaleValue_2']").val());
			var secondSaleValue_2 = $.trim($(":input[name='secondSaleValue_2']").val());
			var threeSaleValue_2 = $.trim($(":input[name='threeSaleValue_2']").val());
			var fourthSaleValue_2 = $.trim($(":input[name='fourthSaleValue_2']").val());
			
			//销售额  <
			var saleThree = $.trim($(":input[name='saleThree']").val());
			/*月份对应的值*/
			var fristSaleValue_3 = $.trim($(":input[name='fristSaleValue_3']").val());
			var secondSaleValue_3 = $.trim($(":input[name='secondSaleValue_3']").val());
			var threeSaleValue_3 = $.trim($(":input[name='threeSaleValue_3']").val());
			var fourthSaleValue_3 = $.trim($(":input[name='fourthSaleValue_3']").val());
			
			
			/*----售货机销售分成--参数设置-end-*/
			
			/*----机器补贴--参数设置-start-*/
			
			/*月份*/
			var fristMonthOne_vend = $.trim($(":input[name='fristMonthOne_vend']").val());
			var fristMonthTwo_vend = $.trim($(":input[name='fristMonthTwo_vend']").val());
			var secondMonthOne_vend = $.trim($(":input[name='secondMonthOne_vend']").val());
			var secondMonthTwo_vend = $.trim($(":input[name='secondMonthTwo_vend']").val());
			var threeMonthOne_vend = $.trim($(":input[name='threeMonthOne_vend']").val());
			var threeMonthTwo_vend = $.trim($(":input[name='threeMonthTwo_vend']").val());
			var fourthMonthOne_vend = $.trim($(":input[name='fourthMonthOne_vend']").val());
			var fourthMonthTwo_vend = $.trim($(":input[name='fourthMonthTwo_vend']").val());
			
			/*补贴*/
			var fristSaleValue_vend = $.trim($(":input[name='fristSaleValue_vend']").val());
			var secondSaleValue_vend = $.trim($(":input[name='secondSaleValue_vend']").val());
			var threeSaleValue_vend = $.trim($(":input[name='threeSaleValue_vend']").val());
			var fourthSaleValue_vend = $.trim($(":input[name='fourthSaleValue_vend']").val());
			
			/*----机器补贴--参数设置-end-*/
			
			//广告费
			var advertMoney = $.trim($(":input[name='advertMoney']").val());
			
			var obj = {};
			obj.id = id;
			
			obj.templateName = templateName;
			obj.probjectType = probjectType!= "" ? probjectType.toString() : "";
			obj.settlementWay = settlementWay; 
			
			obj.fristMonthOne_sale = fristMonthOne_sale;
			obj.fristMonthTwo_sale = fristMonthTwo_sale;
			obj.secondMonthOne_sale = secondMonthOne_sale;
			obj.secondMonthTwo_sale = secondMonthTwo_sale;
			obj.threeMonthOne_sale = threeMonthOne_sale;
			obj.threeMonthTwo_sale = threeMonthTwo_sale;
			obj.fourthMonthOne_sale = fourthMonthOne_sale;
			obj.fourthMonthTwo_sale = fourthMonthTwo_sale;
			
			obj.saleOne = saleOne;
			obj.fristSaleValue_1 = fristSaleValue_1;
			obj.secondSaleValue_1 = secondSaleValue_1;
			obj.threeSaleValue_1 = threeSaleValue_1;
			obj.fourthSaleValue_1 = fourthSaleValue_1;
			
			obj.saleSecond_1 = saleSecond_1;
			obj.saleSecond_2 = saleSecond_2;
			obj.fristSaleValue_2 = fristSaleValue_2;
			obj.secondSaleValue_2 = secondSaleValue_2;
			obj.threeSaleValue_2 = threeSaleValue_2;
			obj.fourthSaleValue_2 = fourthSaleValue_2;
			
			obj.saleThree = saleThree;
			obj.fristSaleValue_3 = fristSaleValue_3;
			obj.secondSaleValue_3 = secondSaleValue_3;
			obj.threeSaleValue_3 = threeSaleValue_3;
			obj.fourthSaleValue_3 = fourthSaleValue_3;
			/*补贴月份*/
			obj.fristMonthOne_vend = fristMonthOne_vend;
			obj.fristMonthTwo_vend = fristMonthTwo_vend;
			obj.secondMonthOne_vend = secondMonthOne_vend;
			obj.secondMonthTwo_vend = secondMonthTwo_vend;
			obj.threeMonthOne_vend = threeMonthOne_vend;
			obj.threeMonthTwo_vend = threeMonthTwo_vend;
			obj.fourthMonthOne_vend = fourthMonthOne_vend;
			obj.fourthMonthTwo_vend = fourthMonthTwo_vend;
			
			/*补贴值*/
			obj.fristSaleValue_vend = fristSaleValue_vend;
			obj.secondSaleValue_vend = secondSaleValue_vend;
			obj.threeSaleValue_vend = threeSaleValue_vend;
			obj.fourthSaleValue_vend = fourthSaleValue_vend;
			
			//广告费
			obj.advertMoney = advertMoney;
			
			var  url ='${ctx}/hcf/financialCenter/save';
		    if(obj.id != '' && obj.id != undefined){
		    	url ='${ctx}/hcf/financialCenter/update';
		    }
			$.ajax({
		       	 type:'post',
		       	 url:url,
		       	 data:JSON.stringify(obj),
		       	 dataType:"json",
		       	 contentType:"application/json",
		       	 success:function(data){
		       		if(data.code=="0"){
			       		$.jBox.tip(data.msg);
			         	closeLoading()
		       			window.location.href="${ctx}/hcf/financialCenter/list";
		       		}else{
		       			$.jBox.tip(data.msg);
		       		}
		       	 }
			});  
		}
		
	});
});
</script>
<style>
.input_s{width: 50px;}
.input_month{width: 15px;}
.input_month_value{width: 40px;}
</style>
<form:form modelAttribute="divideAccountVo"
	action="#" method="post" class="breadcrumb form-search">
	<input id="id" name="id" type="hidden" value="" />
	<table class="table table-striped table-bordered">
		<tr>
			<td>模版名称：</td>
			<td colspan="9">
				<input id="templateNames" type="text" name="templateName" />
			</td>
		</tr>
		<tr>
			<td>结算方式：</td>
			<td colspan="9">
				<input type="radio" name="settlementWay" value="1"/>人工结算&nbsp;&nbsp;<input type="radio" name="settlementWay" value="2"/>自动结算
			</td>
		</tr>
	</table>
	
	<!-- 参数设置说明 -->
	<div id="parm_div">
		<p>参数设置说明:</p>
		<p>1、勾选项目，结算时会给代理商按项目发放</p>
		<p>2、<font color="red">若中途变更模板的系数，则从未结算的当月开始以新模板第1个月开始计算；</font></p>
		<p><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;变更模板后会对所有应用该模板的所有代理商均生效，请谨慎修改！</font></p>
		<p>3、给付系数、补贴数、广告分成请填整数。</p>
		<p>4、系统根据设置的月份数依次计算，如若设置为销售额<=3000元，第1-3个月给付系数为20%，第4-6个月给付系数为30%；</p>
		   <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;销售额>3000元，则第1-3个月给付系数为50%，第4-6个月给付系数为60%；</p>
		   <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;代理商当第一个月销售为3000元，则可得分成为600元，第二个月销售额为6000，则可得分成为3000元，</p>
		   <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第三个月销售额将为3000元，则可得分成为600元；</p>
		   <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第四个月销售额为6000元，则可得分成为3600元</p>
	</div>
	
	<!-- 参数表格 -->
	<div id="data_div">
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th align="center" colspan="2">项目</th>
				<th align="center" colspan="16">参数设置</th>
			</tr>
			<tr >
				<td colspan="2" rowspan="4">
					<input type="checkbox" name="probjectType" value="1"/>售货机销售分成
				</td>
				<td>销售额区间（单位：元）</td>
				<td colspan="16">
					给付系数%<br/>
					第<input type="text" name="fristMonthOne_sale" class="input_month" value=""/>-<input type="text" name="fristMonthTwo_sale" class="input_month" value=""/>个月&nbsp;&nbsp;
					第<input type="text" name="secondMonthOne_sale" class="input_month" value=""/>-<input type="text" name="secondMonthTwo_sale" class="input_month" value=""/>个月&nbsp;&nbsp;
					第<input type="text" name="threeMonthOne_sale" class="input_month" value=""/>-<input type="text" name="threeMonthTwo_sale" class="input_month" value=""/>个月&nbsp;&nbsp;
					第<input type="text" name="fourthMonthOne_sale" class="input_month" value=""/>-<input type="text" name="fourthMonthTwo_sale" class="input_month" value=""/>个月
				</td>
			</tr>
			<tr >
				<td>销售额&nbsp;&nbsp;&le;&nbsp;&nbsp;<input type="text" name="saleOne" class="input_s" value=""/></td>
				<td text_align="center" colspan="16">
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fristSaleValue_1" class="input_month_value"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="secondSaleValue_1" class="input_month_value" value=""/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="threeSaleValue_1" class="input_month_value" value=""/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fourthSaleValue_1" class="input_month_value" value=""/>
				</td>
			</tr>
			<tr >
				<td><input type="text" name="saleSecond_1" class="input_s" value=""/>&nbsp;&nbsp;&lt;&nbsp;&nbsp;销售额&nbsp;&nbsp;&le;&nbsp;&nbsp;<input type="text" name="saleSecond_2" class="input_s" value=""/></td>
				<td text_align="center" colspan="16">
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fristSaleValue_2" class="input_month_value"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="secondSaleValue_2" class="input_month_value" value=""/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="threeSaleValue_2" class="input_month_value" value=""/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fourthSaleValue_2" class="input_month_value" value=""/>
				</td>
			</tr>
			<tr >
				<td><input type="text" name="saleThree" class="input_s" value=""/>&nbsp;&nbsp;&lt;&nbsp;&nbsp;销售额</td>
				<td text_align="center" colspan="16">
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fristSaleValue_3" class="input_month_value"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="secondSaleValue_3" class="input_month_value" value=""/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="threeSaleValue_3" class="input_month_value" value=""/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fourthSaleValue_3" class="input_month_value" value=""/>
				</td>
			</tr>
			
			<tr >
				<td colspan="2" rowspan="2">
					<input type="checkbox" name="probjectType" value="2"/>机器补贴
				</td>
				<td colspan="17">
					(元/月/台)<br/>
					第<input type="text" name="fristMonthOne_vend" class="input_month" value=""/>-<input type="text" name="fristMonthTwo_vend" class="input_month" value=""/>个月&nbsp;&nbsp;
					第<input type="text" name="secondMonthOne_vend" class="input_month" value=""/>-<input type="text" name="secondMonthTwo_vend" class="input_month" value=""/>个月&nbsp;&nbsp;
					第<input type="text" name="threeMonthOne_vend" class="input_month" value=""/>-<input type="text" name="threeMonthTwo_vend" class="input_month" value=""/>个月&nbsp;&nbsp;
					第<input type="text" name="fourthMonthOne_vend" class="input_month" value=""/>-<input type="text" name="fourthMonthTwo_vend" class="input_month" value=""/>个月
				</td>
			</tr>
			<tr >
				<td text_align="center" colspan="17">
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fristSaleValue_vend" class="input_month_value" value=""/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="secondSaleValue_vend" class="input_month_value" value=""/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="threeSaleValue_vend" class="input_month_value" value=""/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fourthSaleValue_vend" class="input_month_value" value=""/>
				</td>
			</tr>
			
			<tr >
				<td colspan="2">
					<input type="checkbox" name="probjectType" value="3"/>广告分成
				</td>
				<td colspan="17">
					<input type="text" name="advertMoney"/>&nbsp;&nbsp;<span>元/周/支</span>
				</td>
			</tr>
			
		</thead>
	</table>
	</div>
	<div class="form-horizontal">
		<br> <br>
		<center>
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
		</center>
	</div>
	
</form:form>
<script type="text/javascript">  

</script> 

