  <%@ page contentType="text/html;charset=UTF-8" %>
<script  type="text/javascript">
			$(function(){
				
				$("#btnsave").bind("click",function(){
					  var data = {}; 
					  var value =$("#stateS").val();
					  var remark = $.trim($("#remark").val());
					  if(value==''){
						 $.jBox.tip("请输入跟进状态");
						 return false;
					  }
					  if(remark == ''){
						  $.jBox.tip("请输入备注");
						  return false;
					  }
					  loading('正在提交，请稍等...');
					  //代理商的id
					  data.id = $("#id").val();
					  //跟进状态
					  data.state = value;
					  data.remark = remark;
					  if(data.id!=''){
					    url ='${ctx}/hcf/LeadManagement/update';
					  }
					  $.ajax({
					       	 type:'post',
					       	 url:url,
					       	 data:JSON.stringify(data),
					       	 dataType:"json",
					       	 contentType:"application/json",
					       	 success:function(data){
					       		$.jBox.tip(data.msg);
					       		closeLoading()
					       		if(data.code=="0"){
					       			window.location.href="${ctx}/hcf/LeadManagement/list";
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
	<form:form id="communityForm" modelAttribute="LeadManagenmentVo"
		action="#" method="post"
		class="breadcrumb form-search">
		 <input id="id" name="id" type="hidden" value=""/>
		<table class="table table-striped table-bordered">
			<tr>
				<td>订单状态</td>
				<td>
					
					<select id="stateS" name="state"  style="width: 220px" class="form-control"
						placeholder=""><option value="">请选择</option>
						<c:forEach items="${stateList}" var="model" varStatus="indexStatus">
							<option value="${model.value}">${model.label}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>备注</td>
				<td><input id="remark" type="text" name="remark" /></td>
			</tr>
		</table>
		
		<label>历史操作</label>
		<table id="historyList" class="table table-striped table-bordered">
		</table>
		
		<div class="form-horizontal">
		<br> <br>
			<div class="pure-g" style="margin-left: 50px">
				<div class="pure-u-2-3">
					<div class="control-group">
						<div class="controls">
							<input id="btnsave" name="btnsave" class="btn" type="button" 
								value="保存" />&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp
							<button type="button" class="btn btn-default" data-dismiss="modal">取消
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
 