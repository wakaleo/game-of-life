  <%@ page contentType="text/html;charset=UTF-8" %>
<script  type="text/javascript">
			$(function(){
				
				$("#btnsave").bind("click",function(){
					  var data = {}; 
					  
					  var value =$("#name").val();
					  //value = $.trim(value);
					  value=value.replace(/\s+/g, "");
					  if(value==''){
						   return  alert("请输入社区名称");
					   }
					  
					  var name =$("#communityId").val();
					  name=name.replace(/\s+/g, "");
					 
					   if(name==''){
						   return  alert("请输入社区编码");
					    }
					   loading('正在提交，请稍等...');
					   	data.id = $("#id").val();
					    data.communityId = name;
					    data.communityName =value;
					    var  url ='${ctx}/hcf/community/save';
					    if(data.id!=''){
					    	   url ='${ctx}/hcf/community/update';
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
					       			window.location.href="${ctx}/hcf/community/list";
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
	<form:form id="communityForm" modelAttribute="communityVo"
		action="#" method="post"
		class="breadcrumb form-search">
		 <input id="id" name="id" type="hidden" value=""/>

		<div class="form-horizontal">
			<ul class="nav nav-tabs">
				<li>社区管理</li>
			</ul>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">社区名称：</label>
						<div class="controls">
							<input id="name" name="name" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">社区编码：</label>
						<div class="controls">
							<input id="communityId" name="communityId" class="form-control" type="text"  />
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
 