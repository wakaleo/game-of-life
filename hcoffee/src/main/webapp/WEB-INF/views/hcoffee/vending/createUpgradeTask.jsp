  <%@ page contentType="text/html;charset=UTF-8" %>
<script  type="text/javascript">
			$(function(){
				
				$("#taskbtnsave").bind("click",function(){
					  var data = {}; 
					 
					   if($("#taskAppVersion").val()==''){
						   return  alert("请选择app版本");
					   }
					   if($("#taskRemark").val()=='' || $("#taskRemark").val()==' ' || $("#taskRemark").val()==null){
						   return  alert("请填写升级内容");
					    }
					   loading('正在提交，请稍等...');
					    data.remark = $("#taskRemark").val();
					    data.vendCode = $("#taskVendCode").val();
					    data.versionId = $("#taskAppVersion").val();
					    data.version = $("#taskAppVersion").select2("data").text;
					 
					    
						$.ajax({
					       	 type:'post',
					       	 url:'${ctx}/hcf/vendingVersion/upgrade',
					       	 data:JSON.stringify(data),
					       	 dataType:"json",
					       	 contentType:"application/json",
					       	 success:function(data){
					       		alert(data.msg);
					         	 closeLoading() 
					       		if(data.code=="0"){
					       			window.location.href="${ctx}/hcf/vendingVersion/versionList";
					       		}
					       	 	
	 
					       	 }
						});  
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
	<form:form id="appUpgradeTaskForm" modelAttribute="appUpgradeTask"
		action="#" method="post"
		class="breadcrumb form-search">
		 <input id="taskVendCode" name="taskVendCode" type="hidden" value=""/>

		<div class="form-horizontal">
			<ul class="nav nav-tabs">
				<li>新建升级任务</li>
			</ul>
 
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">app版本：</label>
						<div class="controls">
							<select id="taskAppVersion" name="taskAppVersion" style="width: 220px" class="form-control select2"
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
							 <textarea rows="3"  name="taskRemark"  id="taskRemark"></textarea>
						</div>
					</div>
				</div>
			</div>

			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<div class="controls">
							<input id="taskbtnsave" name="taskbtnsave" class="btn" type="button" value="保存"   />
						</div>
					</div>
				</div>
			</div>

		</div>
	</form:form>
 