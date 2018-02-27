  <%@ page contentType="text/html;charset=UTF-8" %>
  
<script  type="text/javascript">
		
			$(function(){
				$("#btnsave").bind("click",function(){
					
					  var data = {}; 
					 
					   if($("#template").val()==''){
						   return  alert("请选择模板名称");
					   }
					   if($("#templateId").val()==''){
						   return  alert("请选择模板编号");
					   }
					
					    if($("#schemeNo").val()=='' && $("#minId").val()=='' && $("#actId").val()=='' && $("#daiId").val()==''){
						   return  alert("请选择活动");
					    }  
					    
					    var ids = "";
						$("#tableId td[type='aaa']").each(function(){
							if (ids == "") {
								ids = $(this).attr("value");
							} else {
								ids = ids + "," + $(this).attr("value");
							}
						});
					    
					    data.channelList=ids;
					    data.templateName=$("#template").val();
					    data.vendCode=$("#vendCode").val();
					    data.templateId=$("#templateId").val();
					    data.remark=$("#remark").val();
					    
					   	data.id = $("#id").val();
					   	data.oldTemplateId = $("#oldTemplateId").val();
					   	
				        loading('正在提交，请稍等...');
					    
					    var  url ='${ctx}/hcf/activitySheBeiMagement/save';
					 
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
					       			window.location.href="${ctx}/hcf/activitySheBeiMagement/list";
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
			
			
	function createMinTypeDai(curObj){

		var curId = $(curObj).attr("id");
		var id = $("#" + curId).val();
		var text = $("#" + curId).find("option:selected").text();
		$("#tableId").append("<tr><td type='aaa' value='" + id +"'>" + text +"</td><td><input type='button' value='置顶' onclick='botten(this)'><td><input type='button' value='X' onclick='deleteRow(this)'></td></tr>")	
	}

	function deleteRow(obj){
		$(obj).parent().parent().remove();
	}
			
	function botten(obj){
		
		   $("#tableId").on("click","input",function(){
	            $(this).parent().parent().prependTo($("#tableId"));
	        })
	
	}
			
	</script>
	<form:form id="vendingMachineForm" modelAttribute="anctivityTempleVo"
		action="#" method="post"
		class="breadcrumb form-search">
		 <input id="id" name="id" type="hidden" value=""/>
		 <input id="oldTemplateId" name="oldTemplateId" type="hidden" value=""/>
		 <input id="templateId" name="templateId" type="hidden" value=""/>
		 <input id="vendCode" name="vendCode" type="hidden" value=""/>
			
	<table width="788" height="588" border="1" align="center" cellpadding="2" cellspacing="2" bordercolor="#999999">
			  <tr> 
	  <td width="70%" valign="top">
			  <table id="anctivityTempleVoForm" modelAttribute="anctivityTempleVo"
					action="#" method="post"
					class="breadcrumb form-search">
					 <input id="id" name="id" type="hidden" value=""/>
						<br>
						<div class="controls">
						<label class="control-label">注册类大图：</label>	
						<select id="schemeNo" name="schemeName" style="width: 150px" class="form-control select2" onchange="createMinTypeDai(this);"
								placeholder="请添加活动"><option value="">请添加活动</option>
								<c:forEach items="${activityTypeMaxImg}" var="vor" varStatus="indexStatus">
									<option value="${vor.schemeNo}">${vor.schemeName}</option>
									
								</c:forEach>
							</select>
							
				
				<br>
				<br>
				<br>
			
						
						<div class="controls">
						<label class="control-label">注册类小图：</label>	
						<select id="minId" name="minId" style="width: 150px" class="form-control select2" onchange="createMinTypeDai(this);"
								placeholder="请添加活动"><option value="">请添加活动</option>
								<c:forEach items="${activityTypeMinImg}" var="vor" varStatus="indexStatus">
									<option value="${vor.schemeNo}">${vor.schemeName}</option>
								</c:forEach>
							</select>
					
			
				<br>
				<br>
				<br>
						
						<div class="controls">
						<label class="control-label">广  告  类：</label>	
						<select id="actId" name="actId" style="width: 150px" class="form-control select2" onchange="createMinTypeDai(this);"
								placeholder="请添加活动"><option value="">请添加活动</option>
								<c:forEach items="${activityTypes}" var="vor" varStatus="indexStatus">
									<option value="${vor.schemeNo}">${vor.schemeName}</option>
								</c:forEach>
							</select>
			
			<br>
			<br>
			<br>
						
				<div class="controls">
						<label class="control-label">贷  款  类：</label>
							<select id="daiId" name="daiId" style="width: 150px" class="form-control select2" onchange="createMinTypeDai(this);"
								placeholder="请添加活动"><option value="">请添加活动</option>
								<c:forEach items="${activityTypeDai}" var="vor" varStatus="indexStatus">
									<option value="${vor.schemeNo}">${vor.schemeName}</option>
								</c:forEach>
							</select>
						
				
		</table>
			   
			   
			   
	   </td>
      <td width="70%" valign="top">
			   
			   
			   <table id="tableId" class="table table-striped table-bordered table-condensed">
		  
		  </table>
			   
			   
			   
			   </td>
			  </tr>
			 
			</table> 
	
    <br>
    <br>
			<div class="pure-g" style="margin-left: 140px">
				<div class="pure-u-2-3">
					<div class="control-group">
						<div class="controls" style="margin-left: 70px">
							<input id="btnsave" name="btnsave" class="btn" type="button" value="保存"   />&nbsp &nbsp &nbsp &nbsp  &nbsp &nbsp &nbsp    
							<button type="button" class="btn btn-default" data-dismiss="modal">取消
					</button>
					</div>
				</div>
			</div>
		</div>
		
		

	</form:form>
 