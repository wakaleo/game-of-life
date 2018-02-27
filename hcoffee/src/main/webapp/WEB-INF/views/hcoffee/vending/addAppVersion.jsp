  <%@ page contentType="text/html;charset=UTF-8" %>
	<script  type="text/javascript">
	$(function(){
		
		$("#btnsave").bind("click",function(){
			   var data = {}; 
			   debugger; 
			   var description= $("#description").val();
			   var version= $("#versions").val();
			   if(version=="" || version ==null){
				     alert("请输入APP版本名称");
				     return;
			    }
			   if(version.length>20){
				   alert("APP版本名称不能超过20字节！");
				     return;
			   }
			   if(description==" " ||description=="" || description==null){
				     alert("请输入APP版本内容");
				     return;
			    }
			   loading('正在提交，请稍等...');
			   data.id = $("#id").val();
			   data.description = $("#description").val();
			   data.version = $("#versions").val();
		
			  var form = new FormData(document.getElementById("appVersionForm"));
			   var  url ='${ctx}/hcf/vendingVersion/save';
			    if(data.id!=''){
			    	   url ='${ctx}/hcf/vendingVersion/update';
			    }
	             $.ajax({
	                url:url,
	                type:"post",
	                data:form,
	                processData:false,
	                contentType:false,
	                success:function(data){
	                	alert("成功")
	                	window.location.href="${ctx}/hcf/vendingVersion/list";
	                }
	            });    
			});
			   /*  data.version = version;
			   data.description = description;
			   data.status = $("#status").val(); 
			    var  url ='${ctx}/hcf/vendingVersion/save';
			    if(data.id!=''){
			    	   url ='${ctx}/hcf/vendingVersion/update';
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
			       			window.location.href="${ctx}/hcf/vendingVersion/list";
			       		}
			       	 	

			       	 }
				});  
			  
		}); */
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
	<form:form id="appVersionForm"  action="" method="post" enctype="multipart/form-data" class="breadcrumb form-search">
	<input id="id" name="id" type="hidden" value=""/>
		<div class="form-horizontal">
			<ul class="nav nav-tabs">
				<li>售货机版本编辑</li>
			</ul>
		
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">版本名称：</label>
						<div class="controls">
							<input type="text" name="version" id="versions" />
						</div> 
					</div>
				</div>
			</div>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">上传版本：</label>
						<div class="controls">
							 <input type="file" name="file" />
						</div> 
					</div>
				</div>
			</div>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">版本内容：</label>
						<div class="controls">
							 <textarea rows="3"  name="description"  id="description"></textarea>

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
 