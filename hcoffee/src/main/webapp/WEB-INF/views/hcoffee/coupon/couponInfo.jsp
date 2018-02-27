<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>卡券信息</title>
	<meta name="decorator" content="default"/>
	<style>
	</style>
	<script type="text/javascript">
	var numReg = /^[0-9]+(.[0-9]{1,2})?$/;
	$(function(){
		$("#businessSel").change(function(){
			$.ajax({
				url:"${ctx}/marketing/coupon/getCouponTypeList",
				data:{"businessNum":$("#businessSel").val()},
				dataType:"json",
				async:false,
				success:function(data){
					var obj = eval('(' + data+ ')');;
					if("0" == obj.state){
						var selHtml = "";
						for(var i = 0;i< obj.list.length;i++){
							selHtml = selHtml + '<option value="'+obj.list[i].typeNum+'">'+obj.list[i].couponName+'</option>';
						}
						$("#conponTypeSel").html(selHtml);
					}
				}
					
			});
			<c:if test="${not empty obj }">
			$("#conponTypeSel").val("${obj.couponTypeNum }");
			</c:if>
			$("#s2id_conponTypeSel a span").first().html($("#conponTypeSel").find("option:selected").text());
		});
		$("#businessSel").change();
		$("#addBtn").click(function(){
			if($("#businessSel").val() == "-1"){
				alert("请选择业务类型");
				return;
			}
			if($("#conponTypeSel").val() == "-1" || typeof($("#conponTypeSel").val()) == "undefined"){
				alert("请选择卡券类型");
				return;
			}
			if($("#couponContent").val() == null || $("#couponContent").val() == ""){
				alert("请选择或输入卡券属性");
				return;
			}
			if(!numReg.test($("#couponContent").val())){
				alert("卡券属性必须是数字类型且最多支持2位小数");
				return;
			}
			$("#businessNum").val($("#businessSel").val());
			$("#couponName").val($("#conponTypeSel").find("option:selected").text());
			$("#couponTypeNum").val($("#conponTypeSel").val());
			var param = {};
			param["businessNum"]=$("#businessNum").val();
			param["couponTypeNum"]=$("#couponTypeNum").val();
			param["couponContent"]=$("#couponContent").val();
			var check = false;
			$.ajax({
				url:"${ctx}/marketing/coupon/checkCoupon",
				data:param,
				dataType:"json",
				async:false,
				success:function(data){
					var obj = eval('(' + data+ ')');;
					if("0" == obj.state){
						check = true;
					}else if("-1" == obj.state){
						alert("卡券属性填写不正确");
					}else if("-2" == obj.state){
						alert("卡券已存在，请重新输入");
					}
				}
			});
			if(!check){
				return;
			}			
			$("#updateForm").submit();
			
		});
	});
	</script>
</head>
<body>
	
	<sys:message content="${message}"/>
	<ul class="nav nav-tabs">
		<li class="active"><a>卡券信息</a></li>
	</ul>
	<br/>
	<div class="form-horizontal">
		<form:form id="updateForm" action="${ctx}/marketing/coupon/saveCoupon" method="post" modelAttribute="obj">
			<input type="hidden" value="" id="couponName" name="couponName">
			<input type="hidden" value="" id="businessNum" name="businessNum">
			<input type="hidden" value="" id="couponTypeNum" name="couponTypeNum">
			<c:if test="${not empty obj }">
			<input type="hidden" value="${obj.id }" id="id" name="id">
			<div class="control-group">
				<label class="control-label">卡券编号: </label>
				<div class="controls"><input type="text" value="${obj.couponNum }" name="couponNum" readonly="readonly"/></div>
			</div>
			</c:if>
			<div class="control-group">
				<label class="control-label">选择业务类别: </label>
				<div class="controls">
					<select id="businessSel" name="businessSel">
						<c:forEach items="${businessList }" var="bean">
						<option value="${bean.businessNum }" <c:if test="${bean.businessNum == obj.businessNum }">selected="selected"</c:if> >${bean.businessName }</option>	
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">选择卡券类别: </label>
				<div class="controls">
					<select id="conponTypeSel" name="conponTypeSel">
						<option value="-1">选择卡券类别</option>	
						<c:forEach items="${couponTypeList }" var="bean">
						<option value="${bean.typeNum }" <c:if test="${obj.couponTypeNum == bean.typeNum }">selected="selected"</c:if>>${bean.couponName }</option>	
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">请配置卡券属性: </label>
				<div class="controls"><input type="text" id="couponContent" name="couponContent" value="${obj.couponContent }"/></div>
			</div>
			</form:form>
	</div>
	<div class="form-actions">
		<c:if test="${empty obj }">
		<input id="addBtn" class="btn btn-primary" type="button" value="保存"/>
		</c:if>
		<input id="btnCancel" class="btn" type="button" value="返 回"
			onclick="history.go(-1)" />
	</div>
</body>
</html>
