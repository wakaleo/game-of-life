  <%@ page contentType="text/html;charset=UTF-8" %>
<script  type="text/javascript">
			$(function(){
				
				$("#btnsave").bind("click",function(){
					  var data = {}; 
					  if($("#channelName").val()==''){
						   return  alert("请输入渠道名称");
					   }
					  if($("#channelId").val()==''){
						   return  alert("请输入渠道编码");
					   }
					  
					   if($("#dataStatus").val()==''){
						   return  alert("请输入渠道状态");
					    }
					   loading('正在提交，请稍等...');
					    data.statusList=$("input[type='radio']:checked").val();
					   	data.id = $("#id").val();
					    data.channelId = $("#channelId").val();
					    data.channelName =$("#channelName").val();
					    data.mark = $("#mark").val();
					    data.remark =$("#remark").val()
					    var  url ='${ctx}/hcf/channelManagement/save';
					    if(data.id!=''){
					    	   url ='${ctx}/hcf/channelManagement/update';
					    }
						$.ajax({
					       	 type:'post',
					       	 url:url,
					       	 data:JSON.stringify(data),
					       	 dataType:"json",
					       	 contentType:"application/json",
					       	 success:function(data){
					       		alert(data.msg);
					       		if(data.code=="0"){
					       		closeLoading()
					       			window.location.href="${ctx}/hcf/channelManagement/list";
					       		}
					       	 	
	 
					       	 }
						});  
				});
				
				//设置全局点击回车键按钮时不刷新原来的页面
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
	<form:form id="channelForm" modelAttribute="ChannelVo"
		action="#" method="post"
		class="breadcrumb form-search">
		 <input id="id" name="id" type="hidden" value=""/>

		<div class="form-horizontal">
			<ul class="nav nav-tabs">
				<li>渠道管理</li>
			</ul>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">渠道名称：</label>
						<div class="controls">
							<input id="channelName" name="channelName" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">渠道编码：</label>
						<div class="controls">
							<input id="channelId" name="channelId" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">渠道标识：</label>
						<div class="controls">
							<input id="mark" name="mark" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">渠道备注：</label>
						<div class="controls">
							<input id="remark" name="remark" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			<div class="pure-g">  
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">渠道状态：</label>
						<div class="controls" id="dataId">
							<input type="radio" id="dataStatus" name="dataStatus"   value="开启"  /> 开启
							<input type="radio" id="dataStatus" name="dataStatus"   value="关闭"/> 关闭
						</div>
					</div>
				</div>
			</div>

			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<div class="controls">
							<input id="btnsave" name="btnsave" class="btn" type="button" value="保存"   />
						</div>
					</div>
				</div>
			</div>

		</div>
	</form:form>
 