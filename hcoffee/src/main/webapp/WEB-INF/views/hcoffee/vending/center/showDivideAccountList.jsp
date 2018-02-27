<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<head>
	<title>分帐模版查看</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(function(){
			var id = ${templateId};
			show(id);
		});
		function setCheckState(val){
			if(val.indexOf(",") != -1){
				var types = val.split(",");
				for(var i=0;i<types.length;i++){
					$(":input[name='probjectType']:checkbox").each(function(){
						if($(this).val() == types[i]){
							$(this).attr("checked","checked");
						}
					});
				}
			}else{
				$(":input[name='probjectType']:checkbox").each(function(){
					if($(this).val() == val){
						$(this).attr("checked","checked");
					}
				});
			}
			
		}
		//查看
		function show(id){
			$.ajax({
				type:"post",
				url:'${ctx}/hcf/financialCenter/show',
				data: JSON.stringify(id),
				dataType:"json",
				contentType:"application/json",
				success:function(data){
					var diVo = data.diVo;
					console.log("--diVo--"+$.parseJSON(diVo));
					var probjectType = diVo.probjectType;
					$(":input[name='id']").val(diVo.id);
					//模版名称
					$(":input[name='templateName']").val(diVo.templateName);
					//结算方式
					$(":input[name='settlementWay'][value='"+diVo.settlementWay+"']").attr("checked",true);
					//项目
					setCheckState(probjectType);
					//参数设置
					
					$(":input[name='fristMonthOne_sale']").val(diVo.fristMonthOne_sale);
					$(":input[name='fristMonthTwo_sale']").val(diVo.fristMonthTwo_sale);
					$(":input[name='secondMonthOne_sale']").val(diVo.secondMonthOne_sale);
					$(":input[name='secondMonthTwo_sale']").val(diVo.secondMonthTwo_sale);
					$(":input[name='threeMonthOne_sale']").val(diVo.threeMonthOne_sale);
					$(":input[name='threeMonthTwo_sale']").val(diVo.threeMonthTwo_sale);
					$(":input[name='fourthMonthOne_sale']").val(diVo.fourthMonthOne_sale);
					$(":input[name='fourthMonthTwo_sale']").val(diVo.fourthMonthTwo_sale);
					
					$(":input[name='saleOne']").val(diVo.saleOne);
					
					$(":input[name='fristSaleValue_1']").val(diVo.fristSaleValue_1);
					$(":input[name='secondSaleValue_1']").val(diVo.secondSaleValue_1);
					$(":input[name='threeSaleValue_1']").val(diVo.threeSaleValue_1);
					$(":input[name='fourthSaleValue_1']").val(diVo.fourthSaleValue_1);
					
					$(":input[name='saleSecond_1']").val(diVo.saleSecond_1);
					$(":input[name='saleSecond_2']").val(diVo.saleSecond_2);
					
					$(":input[name='fristSaleValue_2']").val(diVo.fristSaleValue_2);
					$(":input[name='secondSaleValue_2']").val(diVo.secondSaleValue_2);
					$(":input[name='threeSaleValue_2']").val(diVo.threeSaleValue_2);
					$(":input[name='fourthSaleValue_2']").val(diVo.fourthSaleValue_2);
					
					$(":input[name='saleThree']").val(diVo.saleThree);
					$(":input[name='fristSaleValue_3']").val(diVo.fristSaleValue_3);
					$(":input[name='secondSaleValue_3']").val(diVo.secondSaleValue_3);
					$(":input[name='threeSaleValue_3']").val(diVo.threeSaleValue_3);
					$(":input[name='fourthSaleValue_3']").val(diVo.fourthSaleValue_3);
					/*售货机销售分成 -- 参数设置*/
					/*售货机销售分成 -- 机器补贴--start--*/
					$(":input[name='fristMonthOne_vend']").val(diVo.fristMonthOne_vend);
					$(":input[name='fristMonthTwo_vend']").val(diVo.fristMonthTwo_vend);
					$(":input[name='secondMonthOne_vend']").val(diVo.secondMonthOne_vend);
					$(":input[name='secondMonthTwo_vend']").val(diVo.secondMonthTwo_vend);
					$(":input[name='threeMonthOne_vend']").val(diVo.threeMonthOne_vend);
					$(":input[name='threeMonthTwo_vend']").val(diVo.threeMonthTwo_vend);
					$(":input[name='fourthMonthOne_vend']").val(diVo.fourthMonthOne_vend);
					$(":input[name='fourthMonthTwo_vend']").val(diVo.fourthMonthTwo_vend);
					
					$(":input[name='fristSaleValue_vend']").val(diVo.fristSaleValue_vend);
					$(":input[name='secondSaleValue_vend']").val(diVo.secondSaleValue_vend);
					$(":input[name='threeSaleValue_vend']").val(diVo.threeSaleValue_vend);
					$(":input[name='fourthSaleValue_vend']").val(diVo.fourthSaleValue_vend);
					
					/*售货机销售分成 -- 机器补贴--end--*/
					
					/*售货机销售分成 -- 广告分成--start--*/
					$(":input[name='advertMoney']").val(diVo.advertMoney);
					/*售货机销售分成 -- 广告分成--end--*/
					
					//加载关联该模版的信息
					var list = data.list;
					var tr_html = "";
					var typeStr = "";
					for(var i=0;i<list.length;i++){
						tr_html += "<tr><td>"+(i+1)+"</td><td>"+list[i].loginName+"</td><td>"+list[i].dealerName+"</td>";
						if(list[i].dealerType == 1)
							typeStr = "个人代理";
						if(list[i].dealerType == 2)
							typeStr = "公司代理";
						tr_html += "<td>"+typeStr+"</td><td>"+list[i].useTimeStr+"</td>";
					}
					$("#gl_list").html(tr_html);
					
					if(diVo.settlementWay == 1){
						$("#parm_div").hide();
						$("#data_div").hide();
					}
				}
			});
		}
	</script>
	<style>
	.hide {
		display:block;
	}
	.show {
		display:none;
	}
	.grayBar{
		background-color:#efefee
	}
	#btnMenu{display:none;}
	.input_stye{width: 23px;}
	.input_month{width: 15px;}
	.input_month_value{width: 40px;}
	</style>
</head>
</head>
<body>
<table class="table table-striped table-bordered">
		<tr>
			<td>模版名称：</td>
			<td colspan="9">
				<input id="templateNames" type="text" name="templateName" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td>结算方式：</td>
			<td colspan="9">
				<input type="radio" name="settlementWay" value="1" disabled="disabled"/>人工结算&nbsp;&nbsp;<input type="radio" name="settlementWay" value="2" disabled="disabled"/>自动结算
			</td>
		</tr>
	</table>
	
	<!-- 参数设置说明 -->
	<div id="parm_div">
	<div>
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
					<input type="checkbox" name="probjectType" value="1" disabled="disabled"/>售货机销售分成
				</td>
				<td>销售额区间（单位：元）</td>
				<td colspan="16">
					给付系数%<br/>
					第<input type="text" name="fristMonthOne_sale" class="input_month" value="" readonly="readonly"/>-<input type="text" name="fristMonthTwo_sale" class="input_month" value="" readonly="readonly"/>个月&nbsp;&nbsp;
					第<input type="text" name="secondMonthOne_sale" class="input_month" value="" readonly="readonly"/>-<input type="text" name="secondMonthTwo_sale" class="input_month" value="" readonly="readonly"/>个月&nbsp;&nbsp;
					第<input type="text" name="threeMonthOne_sale" class="input_month" value="" readonly="readonly"/>-<input type="text" name="threeMonthTwo_sale" class="input_month" value="" readonly="readonly"/>个月&nbsp;&nbsp;
					第<input type="text" name="fourthMonthOne_sale" class="input_month" value="" readonly="readonly"/>-<input type="text" name="fourthMonthTwo_sale" class="input_month" value="" readonly="readonly"/>个月
				</td>
			</tr>
			<tr >
				<td>销售额&nbsp;&nbsp;&le;&nbsp;&nbsp;<input type="text" name="saleOne" class="input_stye" value="" readonly="readonly"/></td>
				<td text_align="center" colspan="16">
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fristSaleValue_1" class="input_month_value" readonly="readonly"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="secondSaleValue_1" class="input_month_value" value="" readonly="readonly"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="threeSaleValue_1" class="input_month_value" value="" readonly="readonly"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fourthSaleValue_1" class="input_month_value" value="" readonly="readonly"/>
				</td>
			</tr>
			<tr >
				<td><input type="text" name="saleSecond_1" class="input_stye" value="" readonly="readonly"/>&nbsp;&nbsp;&lt;&nbsp;&nbsp;销售额&nbsp;&nbsp;&le;&nbsp;&nbsp;<input type="text" name="saleSecond_2" class="input_stye" value="" readonly="readonly"/></td>
				<td text_align="center" colspan="16">
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fristSaleValue_2" class="input_month_value" readonly="readonly"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="secondSaleValue_2" class="input_month_value" value="" readonly="readonly"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="threeSaleValue_2" class="input_month_value" value="" readonly="readonly"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fourthSaleValue_2" class="input_month_value" value="" readonly="readonly"/>
				</td>
			</tr>
			<tr >
				<td><input type="text" name="saleThree" class="input_stye" value="" readonly="readonly"/>&nbsp;&nbsp;&lt;&nbsp;&nbsp;销售额</td>
				<td text_align="center" colspan="16">
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fristSaleValue_3" class="input_month_value" readonly="readonly"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="secondSaleValue_3" class="input_month_value" value="" readonly="readonly"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="threeSaleValue_3" class="input_month_value" value="" readonly="readonly"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fourthSaleValue_3" class="input_month_value" value="" readonly="readonly"/>
				</td>
			</tr>
			
			<tr >
				<td colspan="2" rowspan="2">
					<input type="checkbox" name="probjectType" value="2" readonly="readonly" disabled="disabled"/>机器补贴
				</td>
				<td colspan="17">
					(元/月/台)<br/>
					第<input type="text" name="fristMonthOne_vend" class="input_month" value="" readonly="readonly"/>-<input type="text" name="fristMonthTwo_vend" class="input_month" value="" readonly="readonly"/>个月&nbsp;&nbsp;
					第<input type="text" name="secondMonthOne_vend" class="input_month" value="" readonly="readonly"/>-<input type="text" name="secondMonthTwo_vend" class="input_month" value="" readonly="readonly"/>个月&nbsp;&nbsp;
					第<input type="text" name="threeMonthOne_vend" class="input_month" value="" readonly="readonly"/>-<input type="text" name="threeMonthTwo_vend" class="input_month" value="" readonly="readonly"/>个月&nbsp;&nbsp;
					第<input type="text" name="fourthMonthOne_vend" class="input_month" value="" readonly="readonly"/>-<input type="text" name="fourthMonthTwo_vend" class="input_month" value="" readonly="readonly"/>个月
				</td>
			</tr>
			<tr >
				<td text_align="center" colspan="17">
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fristSaleValue_vend" class="input_month_value" value="" readonly="readonly"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="secondSaleValue_vend" class="input_month_value" value="" readonly="readonly"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="threeSaleValue_vend" class="input_month_value" value="" readonly="readonly"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fourthSaleValue_vend" class="input_month_value" value="" readonly="readonly"/>
				</td>
			</tr>
			
			<tr >
				<td colspan="2">
					<input type="checkbox" name="probjectType" value="3" disabled="disabled"/>广告分成
				</td>
				<td colspan="17">
					<input type="text" name="advertMoney" readonly="readonly"/>&nbsp;&nbsp;<span>元/周/支</span>
				</td>
			</tr>
			
		</thead>
	</table>
	</div>
	<!-- 模板应用代理商列表 -->
	<label>模板应用代理商列表：（排序：按绑定时间倒叙排序）</label>	
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>序号</th><th>登录名</th><th>姓名</th><th>代理类型</th><th>应用时间</th>
			</tr>
		</thead>
		<tbody id="gl_list"></tbody>
	</table>
</body>
</html>