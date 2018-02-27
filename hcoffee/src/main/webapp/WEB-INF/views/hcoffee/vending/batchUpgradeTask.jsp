  <%@ page contentType="text/html;charset=UTF-8" %>
<script  type="text/javascript">
			$(function(){
				
				$("#batchUpgradebtnsave").bind("click",function(){
					  var data = {}; 
					 debugger;
					   if($("#AppUpgradeVersion").val()==''){
						   return  alert("请选择app版本");
					   }
					   if($("#AppUpgradeRemark").val()==' '){
						   return  alert("请填写升级内容");
					    }
					   var addType = $("#addType").val();
					   if(addType == 1){
						   $.jBox.confirm("确定升级查询的所有机器吗?","系统提示",function(v,h,f){
								if(v=="ok"){
									data.remark = $("#AppUpgradeRemark").val();
								    data.versionId = $("#AppUpgradeVersion").val();
								    data.version = $("#AppUpgradeVersion").select2("data").text;
								    loading('正在提交，请稍等...');
								    $.ajax({
								       	 type:'post',
								       	 url:'${ctx}/hcf/vendingVersion/allUpdate',
								       	 data:JSON.stringify(data),
								       	 dataType:"json",
								       	 contentType:"application/json",
								       	 success:function(data){
								       		$.jBox.tip(data.msg);
								       		closeLoading();
								       		if(data.code=="0"){
								       			window.location.href="${ctx}/hcf/vendingVersion/versionList";
								       		}
								       	 }
									});  
								}
							},{buttonsFocus:1});
							top.$('.jbox-body .jbox-icon').css('top','55px');
					   }else{
						   
						   $.jBox.confirm("确定升级所选售货机?"+$("#batchVendCode").val(),"系统提示",function(v,h,f){
								if(v=="ok"){
									data.remark = $("#AppUpgradeRemark").val();
								    data.batchVendCode = $("#batchVendCode").val();
								    data.versionId = $("#AppUpgradeVersion").val();
								    data.version = $("#AppUpgradeVersion").select2("data").text;
								    loading('正在提交，请稍等...');
								    $.ajax({
								       	 type:'post',
								       	 url:'${ctx}/hcf/vendingVersion/batchUpgrade',
								       	 data:JSON.stringify(data),
								       	 dataType:"json",
								       	 contentType:"application/json",
								       	 success:function(data){
								       		$.jBox.tip(data.msg);
								       		closeLoading();
								       		if(data.code=="0"){
								       			window.location.href="${ctx}/hcf/vendingVersion/versionList";
								       		}
								       	 }
									});  
								}
							},{buttonsFocus:1});
							top.$('.jbox-body .jbox-icon').css('top','55px');
					   }
				});
	 
     		document.onkeydown = function() {
				    var evt = window.event || arguments[0];
				    if (evt && evt.keyCode == 13) {
			        if (typeof evt.cancelBubble != "undefined")
			            evt.cancelBubble = true;
			        if (typeof evt.stopPropagation == "function")
			            evt.stopPropagation();
			          }
			    
			        }	
			 
			})
	</script>
		<form:form id="batchUpgradeForm"  action="" method="post" enctype="multipart/form-data" class="breadcrumb form-search">
		 <input id="batchVendCode" name="batchVendCode" type="hidden" value=""/>

		<div class="form-horizontal">
			<ul class="nav nav-tabs">
				<li>新建批量升级任务:</li>
			</ul>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">app版本：</label>
						<div class="controls">
							<select id="AppUpgradeVersion" name="taskAppVersion" style="width: 220px" class="form-control select2"
								placeholder="选择APP版本"><option value="">选择APP版本</option>
								<c:forEach items="${appVersionList}" var="app" varStatus="indexStatus">
									<option value="${app.id}">${app.version}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">升级内容：</label>
						<div class="controls">
							 <textarea rows="3"  name="taskRemark"  id="AppUpgradeRemark"></textarea>
						</div>
					</div>
				</div>
			</div>

			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<div class="controls">
							<input id="batchUpgradebtnsave" name="batchUpgradebtnsave" class="btn" type="button" value="保存"   />
						</div>
					</div>
				</div>
			</div>

		</div>
	</form:form>
 