  <%@ page contentType="text/html;charset=UTF-8" %>
<script  type="text/javascript">
			$(function(){
				$("#btnsave1").bind("click",function(){
					  var data = {}; 
					  if($("#schemeNo").val()==''){
						   return  alert("请输入活动编码");
					   }
					  
					  if($("#channel").val()==''){
						   return  alert("请输入渠道编码");
					   }
					  
					   if($("#schemeName").val()==''){
						   return  alert("请输入活动方案名称");
					    }
					   loading('正在提交，请稍等...');
					   	data.id = $("#id").val();
					    data.schemeNo = $("#schemeNo").val();
					    data.channel = $("#channel").val();
					    data.schemeName =$("#schemeName").val();
					    
					    var  url ='${ctx}/hcf/activityManagement/save';
					    if(data.id!=''){
					    	   url ='${ctx}/hcf/activityManagement/update';
					    }
						$.ajax({
					       	 type:'post',
					       	 url:url,
					       	 data:JSON.stringify(data),
					       	 dataType:"json",
					       	 contentType:"application/json",
					       	 success:function(data){
					       		alert(data.msg);
					       		closeLoading()
					       		if(data.code=="0"){
					       			window.location.href="${ctx}/hcf/activityManagement/list";
					       		}
					       	 	
	 
					       	 }
						});  
				});
	 

			 
			})
	</script>
	<form:form id="channelForm" modelAttribute="activityManageVo"
		action="#" method="post"
		class="breadcrumb form-search">
		 <input id="id" name="id" type="hidden" value=""/>

		<div class="form-horizontal">
			<ul class="nav nav-tabs">
				<li>活动管理</li>
			</ul>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">活动编码：</label>
						<div class="controls">
							<input id="schemeNo" name="schemeNo" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">渠道编码：</label>
						<div class="controls">
							<input id="channel" name="channel" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">活动方案名称：</label>
						<div class="controls">
							<input id="schemeName" name="schemeName" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			

			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<div class="controls">
							<input id="btnsave1" name="btnsave1" class="btn" type="button" value="保存"   />
						</div>
					</div>
				</div>
			</div>

		</div>
	</form:form>
 