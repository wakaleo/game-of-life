<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>配置卡券</title>
	<meta name="decorator" content="default"/>
    <link href="${ctxStatic}/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
	<%@ include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					var ids = [], nodes = tree.getCheckedNodes(true);
					console.info("ids:"+ids)
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					$("#couponNum").val(ids);
					loading('正在提交，请稍等...');
					form.submit();
				},
				rules:{  
					couponNum:{
						required:true,
					},
					couponValidityStartTime:{
						required:true,  
					},
					couponValidityEndTime:{
						required:true,  
					},
					couponUseCondition:{
						required:true,  
			            digits:true,
			            min:1
					},
					couponValidityDay:{  
			            required:true,  
			            digits:true,
			            min:1
			        }
			    },  
			    messages:{
			    	couponNum:{  
			        	required:'请选择卡券类型',  
			        },
			        couponValidityStartTime:{  
			        	required:'卡券开始时间不能为空',  
			        },
			        couponValidityEndTime:{  
			        	required:'卡券结束时间不能为空',  
			        },
			    	couponUseCondition:{  
			            required:'商品满条件不能为空',  
			            digits:'只能输入整数', 
			            min:'不能小于1'
			        },
			        couponValidityDay:{  
			            required:'按照卡券发放日期后天数不能为空',  
			            digits:'只能输入整数',
			            min:'不能小于1'
			        } 
			    },  
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			couponUseConditionOnChange();
			couponValidityTypeOnChange();
			/* var isUseCondition = ${activityCouponMapModel.couponIsUseCondition};
			if(isUseCondition !== null || isUseCondition !== undefined || isUseCondition !== ''){
				couponUseConditionOnChange();
			}
			
			var validityDay = ${activityCouponMapModel.couponValidityDay};
			if(validityDay !== null || validityDay !== undefined || validityDay !== ''){
				couponValidityTypeOnChange();
			} */
			
		}); 
		function searchGoodsPoolFun() {
			loading('加载商品池，请稍等...');
	    	$("#inputForm").attr("action","");
	    	$("#inputForm").submit();
    	}
		
		function couponUseConditionOnChange(){
	        var obj  = document.getElementsByName('couponIsUseCondition');
	        for(var i=0;i<obj.length;i++){
	            if(obj[i].checked==true){
	                if(obj[i].value=='0'){
	                	document.getElementById('couponUseCondition').disabled=true;
	                }else if(obj[i].value=='1'){
	                    document.getElementById('couponUseCondition').disabled=false;
	                }
	            }
	        }
	    }
		function couponValidityTypeOnChange(){
	        var obj  = document.getElementsByName('couponValidityType');
	        for(var i=0;i<obj.length;i++){
	            if(obj[i].checked==true){
	                if(obj[i].value=='0'){
	                	document.getElementById('couponValidityTypeTime').hidden="";
	                    document.getElementById('couponValidityTypeDay').hidden="hidden";
	                }else if(obj[i].value=='1'){
	                    document.getElementById('couponValidityTypeTime').hidden="hidden";
	                    document.getElementById('couponValidityTypeDay').hidden="";
	                }
	            }
	        }
	    }
		
	</script>
	<SCRIPT type="text/javascript">
	var setting = {    
            check:{
                enable:true,
                chkStyle: "radio",
				radioType: "all"
            },
            data:    {
                simpleData:{
                    enable:true
                }
            },
            callback:{
                onCheck:onCheck
            }
        };
        var zNodes =[<c:forEach items="${treeList}" var="obj">{id:"${obj.id}", pId:"${not empty obj.pId?obj.pId:0}", name:"${not empty obj.pId?obj.name:'权限列表'}",chkDisabled:"${not empty obj.chkDisabled?obj.chkDisabled:'false'}"},
 		            </c:forEach>];
        function disabledNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("menuTree"),
			disabled = e.data.disabled,
			nodes = zTree.getSelectedNodes();
			if (nodes.length == 0) {
				alert("请先选择一个节点");
			}
			for (var i=0, l=nodes.length; i<l; i++) {
				zTree.setChkDisabled(nodes[i], disabled);
			}
		}
        $(document).ready(function(){
            $.fn.zTree.init($("#menuTree"), setting, zNodes);
            $("#disabledTrue").bind("click", {disabled: true}, disabledNode);
            $("#disabledFalse").bind("click", {disabled: false}, disabledNode);
	        var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
					
			var ids = "${activityCouponMapModel.couponNum}".split(",");
			$("#couponNum").val(ids);
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}
			
			// 不选择父节点
			tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
	        tree.expandAll(true);
        });
        function onCheck(e,treeId,treeNode){
	        var treeObj=$.fn.zTree.getZTreeObj("menuTree"),
	        nodes=treeObj.getCheckedNodes(true),
	        v="";
	        for(var i=0;i<nodes.length;i++){
		        v+=nodes[i].name + ",";
		        $("#couponNum").val(nodes[i].id);
	        }
        }
            
	</SCRIPT>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/marketing/activity/settingCoupon">配置卡券</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="activityCouponMapModel" action="${ctx}/marketing/activity/settingCoupon/save" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<input hidden="hidden" name="editType" value="${editType}"/>
		<input hidden="hidden" name="activityNum" value="${activityCouponMapModel.activityNum}"/>
		<input hidden="hidden" name="num" value="${activityCouponMapModel.num}"/>
		
		<input hidden="hidden"  name="activityStartTime" type="text"  value="${activityStartTime}" />
		<input hidden="hidden"  name="activityEndTime" type="text"  value="${activityEndTime}" />
		<table style="width:100% ;">
			<tr>
				<td>
					<div >
						<label class="control-label">卡券类型:</label>
						<div class="controls">
						
							<div class="zTreeDemoBackground left">
								<ul id="menuTree" class="ztree"></ul>
								<input style="height:1px;width:0px;padding:0px;margin:0px;border-style: solid; border-width: 0" id="couponNum" name="couponNum"/>
							</div>
						</div> 
					</div>
				</td>
			</tr>
		</table>
		<div class="control-group"></div>
		<div class="control-group">
			<label class="control-label">使用规则:</label> 
			<div class="controls">
				<label class="checkbox inline"><input type="radio" checked="checked" name="couponUseRole" <c:if test="${activityCouponMapModel.couponUseRole=='0'}">checked="checked"</c:if> value='0'>平台通用</label>
				<label class="checkbox inline"><input type="radio" name="couponUseRole" <c:if test="${activityCouponMapModel.couponUseRole=='1'}">checked="checked"</c:if> value='1' onclick="searchGoodsPoolFun()">选择商品池</label>
			</div>
		</div>
		
		<table>
			<tr>
				<td>
					<div class="control-group">
						<label class="control-label">设置使用门槛:</label>
						<div class="controls">
							<label class="checkbox inline"><input onclick="couponUseConditionOnChange()" type="radio" checked="checked" name="couponIsUseCondition" value='0'>无门槛</label>
							<label class="checkbox inline"><input onclick="couponUseConditionOnChange()" type="radio" <c:if test="${not empty activityCouponMapModel.couponUseCondition}">checked="checked"</c:if> name="couponIsUseCondition" value='1'>设置金额门槛</label>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group">
						<label class="control-label">商品满:</label>
						<div class="controls">
 							<form:input disabled="true" id="couponUseCondition" name="couponUseCondition" path="couponUseCondition" placeholder="请填写商品满条件"  htmlEscape="false" maxlength="10" onkeyUp="this.value=this.value.replace(/[^0-9\.]/g,'')" class="input-xlarge" />
						</div>
					</div>
				</td>
			</tr>
		</table>
		<div class="control-group">
			<label class="control-label">卡券有效期:</label> 
			<div class="controls">
				<label class="checkbox inline"><input onclick="couponValidityTypeOnChange()" type="radio" checked="checked" name="couponValidityType" value='0'>按照自然日</label>
				<label class="checkbox inline"><input onclick="couponValidityTypeOnChange()" type="radio" <c:if test="${not empty activityCouponMapModel.couponValidityDay}">checked="checked"</c:if> name="couponValidityType" value='1'>按照卡券发放日期后</label>
			</div>
		</div>
			
		<div class="control-group" id="couponValidityTypeTime">
			<div class="controls">
				 <div class="form-group">
			        <input name="couponValidityStartTime" type="text" class="form-control form_datetime input-xlarge" readonly="readonly" value="<fmt:formatDate value="${activityCouponMapModel.couponValidityStartTime}" type="both"/>" id="couponValidityStartTime" placeholder="开始时间">至
			        <input name="couponValidityEndTime" type="text" class="form-control form_datetime input-xlarge" readonly="readonly" value="<fmt:formatDate value="${activityCouponMapModel.couponValidityEndTime}" type="both"/>" id="couponValidityEndTime" placeholder="结束时间">
			     </div>
			</div>
		</div>
		
		<div class="control-group" id="couponValidityTypeDay" hidden="hidden">
			<div class="controls">
				<form:input path="couponValidityDay" id="couponValidityDay" htmlEscape="false" maxlength="6" onkeyUp="this.value=this.value.replace(/[^0-9\.]/g,'')" placeholder="请输入天数" class="input-xlarge" />日
			</div>
		</div>
		
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary"   type="submit" value="完成" />
			<a href="${ctx}/marketing/activity/activityEdit?activityNum=${activityCouponMapModel.activityNum}"><input id="btnCancel" class="btn" type="button" value="返 回" /></a>
		</div>
	</form:form>
	<script type="text/javascript" src="${ctxStatic}/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${ctxStatic}/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript">
	$('#couponValidityStartTime').datetimepicker({
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
        var transferdate=transferDate($("#couponValidityStartTime").val());//转时间日期
        $('#couponValidityEndTime').datetimepicker('remove');
        $('#couponValidityEndTime').datetimepicker({
            format:'yyyy-mm-dd hh:ii',
            language:  'zh-CN',
            //minView:2,
            autoclose: 1,
            'startDate':transferdate
        }).on("changeDate",function(ev){
            var enddate=$("#couponValidityEndTime").val();
            setEndTime(enddate);
        });
    });
    $('#couponValidityEndTime').datetimepicker({
        format:'yyyy-mm-dd hh:ii',
        language:  'zh-CN',
        minView:2,
        autoclose: 1
    }).on("changeDate",function(ev){
        var enddate=$("#couponValidityEndTime").val();
        setEndTime(enddate);
    });
    function setEndTime(enddate){
        $('#couponValidityStartTime').datetimepicker('remove');
            $('#couponValidityStartTime').datetimepicker({
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