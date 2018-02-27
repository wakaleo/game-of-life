<%@ page contentType="text/html;charset=UTF-8"%>
<style>
.input_stye{width: 23px;}
.input_month{width: 15px;}
.input_month_value{width: 40px;}
</style>
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


