<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>新建营销活动</title>
	<meta name="decorator" content="default"/>
    <link href="${ctxStatic}/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
	    <!-- <link href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" media="screen"> -->
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('暂存活动信息，请稍等...');
					form.submit();
				},
				errorContainer:"#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			to_change();
			actionOrGroupTypeShowFun();
		});
		//获取radio值公共方法
		function getRadioBoxValue(radioName) {
			var obj = document.getElementsByName(radioName); //这个是以标签的name来取控件
			for (i = 0; i < obj.length; i++) {
				if (obj[i].checked) {
					return obj[i].value;
				}
			}
			return "undefined";
		}

		/**活动内容保存  提交事件*/
		function btnSubmitFun(SubStatus) {
			var activityName = document.getElementById('activityName').value;
			if (activityName == "") {
				alert("请先完善活动名称！");
				return;
			}
			if (getRadioBoxValue('actionOrGroupType') == '1') {
				if (!$(':radio[name=userGroupNum]:checked').length) {
					alert("请先选择分组用户！");
					return;
				}
			}
			//当下发方式为兑换，一定要填写限制条件
			var distributeWayNum = $('#distributeWayId option:selected') .val();
			if(distributeWayNum !="" && distributeWayNum=='XF002'){
				if (getRadioBoxValue('isQuantityLimit') != '1') {
					alert("下发方式为兑换，数量限制请选择“是”，并设置数量！");
					return;
				}
			}
			//数量限制
			if (getRadioBoxValue('isQuantityLimit') == '1') {
				var quantityLimit = document.getElementById('quantityLimit').value;
				if (quantityLimit == "" || activityEndTime == "undefined") {
					alert("请填写数量限制！");
					return;
				}else if(quantityLimit <= 0){
					alert("数量限制不能为小于0！");
					return;
				}
			}
			//判断活动时间不能为空
			var activityStartTime = document.getElementById('activityStartTime').value;
			var activityEndTime = document.getElementById('activityEndTime').value;
			if (activityStartTime == "" || activityEndTime == "") {
				alert("请先完善活动时间！");
				return;
			}
			//判断活动结束日期不能小于活动开始日期
			var sDate = new Date(activityStartTime.replace(/-/g, "//"));
			var eDate = new Date(activityEndTime.replace(/-/g, "//"));
			if (sDate > eDate) {
				alert("活动结束日期不能小于活动开始日期！");
				return;
			}
			
			
			loading('暂存活动信息，请稍等...'); 
			//活动发布 提交事件
			if(SubStatus !="" && SubStatus == "publishActivity"){
				$("#inputForm").attr("action","${ctx}/marketing/activity/activityPublish?editType=update");
			//活动内容保存  提交事件
			}else if(SubStatus !="" && SubStatus == "saveActivity"){
				$("#inputForm").attr("action","${ctx}/marketing/activity/activitySave");
			}
			$("#inputForm").submit();
		}
		/**是否限制数量事件*/
		function to_change() {
			var obj = document.getElementsByName('isQuantityLimit');
			for (var i = 0; i < obj.length; i++) {
				if (obj[i].checked == true) {
					if (obj[i].value == '0') {
						document.getElementById('quantityLimit').disabled = true;
					} else if (obj[i].value == '1') {
						document.getElementById('quantityLimit').disabled = false;
					}
				}
			}
		}
		/**用户属性与用户动作切换*/
		function actionOrGroupTypeShowFun() {
			var actionOrGroupType = document.getElementsByName("actionOrGroupType");
			var div = document.getElementById("attAction").getElementsByTagName("div");
			for (i = 0; i < div.length; i++) {
				if (actionOrGroupType[i].checked) {
					div[i].style.display = "block";
				} else {
					div[i].style.display = "none";
				}
			}
		}
		/*下发方式为兑换则必需输入数量*/
		function distributeWayFun(distributeWayNum) {
			if(distributeWayNum !="" && distributeWayNum=='XF002'){
				document.getElementById('isQuantityLimit1').checked = true;
				document.getElementById('quantityLimit').disabled = false;
			}
		}
		/*用户渠道接口调用*/
		function channelUserFun(channelType){
			var urlPath = '';
			if(channelType){
				if(channelType == '1'){
					urlPath = "${ctx}/marketing/activity/getChannelUserApi";
				}else if(channelType == '2'){
					urlPath = "";
				}else if(channelType == '3'){
					urlPath = "";
				}else if(channelType == '0'){
					urlPath = "";
				}
				$.ajax({
				        url: urlPath,
				        type: "POST",
				        async: false,
				        success: function (datas) {
				        	var channelSpanCont = document.getElementById('channelSpan');
				        	var html = '';
				            for (var i = 0; i < datas.length; i++) {
				            	var dataMap = datas[i];
			        			if(dataMap.channelId != null){
					            	html += '<label style="width: 250px;"><input type="checkbox" name="channelId" value='+dataMap.channelId+'>'+dataMap.channelName+'</label>';
					            	if((i+1) % 6 == 0 && (i+1) == 6){
					            		html += '<p>';
									}
			        			}
				            }
							channelSpanCont.innerHTML = html;
				        },
				        fail: function (status) {
				            // 此处放失败后执行的代码
				        }
				    });
				}
			}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/marketing/activity/activityEdit">新建营销活动</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="activityModel" action="${ctx}/marketing/activity/activitySave" method="post" class="form-horizontal">
		<input hidden="hidden" name="editType" value="${editType}"/>
		<input hidden="hidden" name="oldUserGroupNum" value="${activityUserGroup.userGroupNum}"/>
		<sys:message content="${message}"/>
		<table style="width:100%">
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">活动编号:</label>
						<div class="controls">
							<form:input path="activityNum" readonly="true" htmlEscape="false" maxlength="50" class="input-xlarge" />
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">活动名称:</label>
						<div class="controls">
							<form:input path="activityName" htmlEscape="false" maxlength="30" class="input-xlarge" />
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">活动平台:</label>
						<div class="controls">
							<select id="conponTypeSel" name="activityPlatform" class="input-xlarge">
								<option selected="selected" value="3">汇生活</option>	
								<%-- <option <c:if test="${activityModel.activityPlatform == 1 }">selected="selected"</c:if> value="1">汇有房</option>	
								<option <c:if test="${activityModel.activityPlatform == 2 }">selected="selected"</c:if> value="2">汇理财</option>	
								<option <c:if test="${activityModel.activityPlatform == 4 }">selected="selected"</c:if> value="4">汇联E家</option> --%>	
							</select>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">下发方式:</label>
						<div class="controls">
							<select id="distributeWayId" name="distributeWayId" class="input-xlarge" onchange="distributeWayFun(this.value)">
								<c:forEach items="${distributeWayList }" var="bean">
								<option value="${bean.distributeWayNum }" <c:if test="${bean.distributeWayNum == activityModel.distributeWayId }">selected="selected"</c:if>>${bean.distributeWay }</option>	
								</c:forEach>
							</select>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">数量限制:</label>
						<div class="controls">
							<label class="checkbox inline"><input type="radio" checked="checked" name="isQuantityLimit" id="isQuantityLimit0" value='0' onclick="to_change()">否</label>
				  			<label class="checkbox inline"><input type="radio" <c:if test="${activityModel.quantityLimit > 0}">checked="checked"</c:if> name="isQuantityLimit" id="isQuantityLimit1" value='1' onclick="to_change()">是</label>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">请填写限制条件:</label>
						<div class="controls">
							<form:input disabled="true" type="text" id="quantityLimit" path="quantityLimit"  placeholder="请填写限制数量"  htmlEscape="false" maxlength="7" onkeyUp="this.value=this.value.replace(/[^0-9\.]/g,'')" class="input-xlarge" />
						</div>
					</div>
				</td>
			</tr>
		</table>
		
		<div class="control-group">
			<label class="control-label">属性动作&分组用户:</label> 
			<div class="controls">
				<label class="checkbox inline"><input name="actionOrGroupType" type="radio" value="0" checked="checked" onclick="actionOrGroupTypeShowFun();" />属性动作</label>
				<label class="checkbox inline"><input name="actionOrGroupType" type="radio" <c:if test="${not empty activityUserGroup.userGroupNum}">checked="checked"</c:if> value="1" onclick="actionOrGroupTypeShowFun();" />分组用户</label>
			</div>
		</div>
		<div class="control-group">
			<div id="attAction">
				<div class="controls" class="c1">
					<label >用户属性:</label> 
					<label class="checkbox inline">
						<input type="radio" checked="checked" name="channelType" id="channelType0" value='0' onclick="channelUserFun(this.value)">全部
					</label>
					<label class="checkbox inline">
						<input type="radio" name="channelType" id="channelType1" value='1' onclick="channelUserFun(this.value)">汇生活渠道用户
					</label>
					<label class="checkbox inline">
						<input type="radio" name="channelType" id="channelType2" value='2' onclick="channelUserFun(this.value)">汇理财渠道用户
					</label>
					<label class="checkbox inline">
						<input type="radio" name="channelType" id="channelType3" value='3' onclick="channelUserFun(this.value)">汇有房渠道用户
					</label><br/>
					<span id="channelSpan"><p>
					</span>
					<label >用户动作:</label> 
					<label class="checkbox inline"><input type="radio" checked="checked" name="actionType" value='0'>默认</label>
					<label class="checkbox inline"><input type="radio" name="actionType" <c:if test="${activityModel.actionType=='1'}">checked="checked"</c:if> value='1'>注册</label>
					<label class="checkbox inline"><input type="radio" name="actionType" <c:if test="${activityModel.actionType=='2'}">checked="checked"</c:if> value='2'>分期购用户首次还款</label>
					<label class="checkbox inline"><input type="radio" name="actionType" <c:if test="${activityModel.actionType=='3'}">checked="checked"</c:if> value='3'>成功授信激活（风控）</label>
					<label class="checkbox inline"><input type="radio" name="actionType" <c:if test="${activityModel.actionType=='4'}">checked="checked"</c:if> value='4'>成功完成投资送订单</label>
				</div>
				
				<div class="controls" class="c2" style="display: none;">
					<c:forEach items="${userGroupList }" var="bean" varStatus="status">
						<label style="width: 250px;" ><input type="radio" <c:if test="${bean.userGroupNum == activityUserGroup.userGroupNum }">checked="checked"</c:if> name="userGroupNum" value=${bean.userGroupNum }>${bean.userGroupName }</label>
						<c:if test="${status.count+1 % 5 == 0 && status.count+1  == 5}">
						<p>
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>
		
		<%-- 
			<div class="control-group" class="c1">
				<label class="control-label">用户属性:</label> 
				<div class="controls">
					<label class="checkbox inline"><input type="radio" checked="checked" name="userAttribute" value='0'>全部</label>
					<label class="checkbox inline"><input type="radio" name="userAttribute" value='1'>渠道用户</label>
					<label class="checkbox inline"><input type="radio" name="userAttribute" value='2'>用户属性A</label>
					<label class="checkbox inline"><input type="radio" name="userAttribute" value='3'>用户属性B</label>
				</div>
			</div>
			
			<div class="control-group" class="c2" style="display: none;">
				<label class="control-label">用户动作:</label> 
				<div class="controls">
					<label class="checkbox inline"><input type="radio" checked="checked" name="actionType" value='0'>默认</label>
					<label class="checkbox inline"><input type="radio" name="actionType" <c:if test="${activityModel.actionType=='1'}">checked="checked"</c:if> value='1'>注册</label>
					<label class="checkbox inline"><input type="radio" name="actionType" <c:if test="${activityModel.actionType=='2'}">checked="checked"</c:if> value='2'>分期购用户首次还款</label>
					<label class="checkbox inline"><input type="radio" name="actionType" <c:if test="${activityModel.actionType=='3'}">checked="checked"</c:if> value='3'>成功授信激活（风控）</label>
					<label class="checkbox inline"><input type="radio" name="actionType" <c:if test="${activityModel.actionType=='4'}">checked="checked"</c:if> value='4'>成功完成投资送订单</label>
				</div>
			</div> --%>
		
		<%-- <div class="control-group" id="userGroupDiv" >
			<label class="control-label">分组用户:</label> 
			<div class="controls">
				<c:forEach items="${userGroupList }" var="bean">
					<label class="checkbox inline"><input type="radio" <c:if test="${bean.userGroupNum == activityUserGroup.userGroupNum }">checked="checked"</c:if> name="userGroupNum" value=${bean.userGroupNum }>${bean.userGroupName }</label>
				</c:forEach>
			</div>
		</div> --%>
			
		<div class="control-group">
			<label class="control-label">活动时间:</label> 
			<div class="controls">
				 <div class="form-group">
				 
			        <input type="text" class="form-control form_datetime" readonly="readonly" value="<fmt:formatDate value="${activityModel.activityStartTime}" type="both"/>" name="activityStartTime" id="activityStartTime" placeholder="开始时间">至
			        <input type="text" class="form-control form_datetime " value="<fmt:formatDate value="${activityModel.activityEndTime}" type="both"/>" readonly="readonly" name="activityEndTime" id="activityEndTime" placeholder="结束时间">
			    </div>
			</div>
		</div>
		</form:form>
		
		<form:form id="couponForm" modelAttribute="activityCouponMapModel" action="${ctx}/marketing/activity/activityCouponMapModelSave?editType=insert" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">卡券配置:</label>
			<div class="controls">
				<c:forEach items="${couponDetaillVoList }" var="bean">
					<label style="border:1px dashed #ccc;margin-top: ;">
						<ul class="list-group">
							<li >${bean.businessName}_${bean.couponName}_${bean.couponContent}</li>
						    <li >${bean.couponUseCondition}</li>
						    <li >${bean.couponUseRole}</li>
						    <li >${bean.couponValidityTime}</li>
						    <c:if test="${viewType != '1'}">
						    <li ><a href="${ctx}/marketing/activity/settingCoupon?activityNum=${bean.activityNum}&num=${bean.num}&activityStartTime=${activityModel.activityStartTime}&activityEndTime=${activityModel.activityEndTime}&editType=update">编辑</a>&nbsp;<a href="${ctx}/marketing/activity/settingCoupon/save?activityNum=${bean.activityNum }&num=${bean.num }&dataStatus=1&editType=update&viewType=${viewType}" onclick="return confirmx('确定要删除该卡券吗？', this.href)">删除</a></li>
						    </c:if>
						</ul>
					</label>
				</c:forEach>
				<c:if test="${viewType != '1'}">
				<input id="btnSubmit" class="btn btn-primary" type="button" onclick="btnSubmitFun('saveActivity');" value="+" />
				</c:if>
			</div>
		</div>
		<div class="form-actions">
		<c:if test="${viewType != '1'}">
		<input  <c:if test="${couponDetaillVoList == null || fn:length(couponDetaillVoList) == 0}">disabled="disabled"</c:if> class="btn btn-primary" id="btnPulish" onclick="btnSubmitFun('publishActivity');" type="button" value="发布" />
		</c:if>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
 	<script type="text/javascript" src="${ctxStatic}/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${ctxStatic}/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript">
	$('#activityStartTime').datetimepicker({
        format:'yyyy-mm-dd hh:ii',
        language:  'zh-CN',
        //weekStart: 1,
        //todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        //minView:2,
        forceParse: 0,
        //showMeridian: 1
    }).on("changeDate",function(ev){
        var transferdate=transferDate($("#activityStartTime").val());//转时间日期
        $('#activityEndTime').datetimepicker('remove');
        $('#activityEndTime').datetimepicker({
            format:'yyyy-mm-dd hh:ii',
            language:  'zh-CN',
            //minView:2,
            autoclose: 1,
            'startDate':transferdate
        }).on("changeDate",function(ev){
            var enddate=$("#activityEndTime").val();
            setEndTime(enddate);
        });
    });
    $('#activityEndTime').datetimepicker({
        format:'yyyy-mm-dd hh:ii',
        language:  'zh-CN',
        minView:2,
        autoclose: 1
    }).on("changeDate",function(ev){
        var enddate=$("#activityEndTime").val();
        setEndTime(enddate);
    });
    function setEndTime(enddate){
        $('#activityStartTime').datetimepicker('remove');
            $('#activityStartTime').datetimepicker({
                format:'yyyy-mm-dd hh:ii',
                language:  'zh-CN',
                autoclose: 1,
                todayHighlight: 1,
                startView: 2,
                forceParse: 0,
                'endDate':transferDate(enddate)
        });
    }
    //将时间字符串转为date
    function transferDate(data){
        var start_time=data;
        var newTime= start_time.replace(/-/g,"-");
        var transferdate = new Date(newTime);
        return transferdate;
    }
    function transferTime(str){
        var newstr=str.replace(/-/g,'-');
        var newdate=new Date(newstr);
        var time=newdate.getTime();
        return time;
    }
	</script>  
</body>
</html>