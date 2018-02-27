
<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		$("#btnsave")
				.bind(
						"click",
						function() {
							var data = {};
							var reg = new RegExp("^\\d+(\\.\\d+)?$");
							var obj = document.getElementById("minPrice");
							var maxObject = document.getElementById("maxPrice");
							/*  if ($("#schemeId").val() == '') {
								return alert("请输入活动类型");
							}  */
							if ($("#schemeName").val() == '') {
								return alert("请输入活动名称");
							}
							if ($("#scheme").val() == '') {
								return alert("请输入活动编码");
							}

							if ($("#minPrice").val() == '') {
								return alert("请输入最低价格");
							}else if (!reg.test(obj.value)){
								return alert("输入的最低礼品价格不合法");
							}
							
							if ($("#maxPrice").val() == '') {
								return alert("请输入最高价格");
							}else if(!reg.test(maxObject.value)){
								return alert("输入的最高礼品价格不合法");
							}
							loading('正在提交，请稍等...');
							data.id = $("#id").val();
							data.schemeName = $("#schemeName").val();
							data.schemeNo = $("#scheme").val();
							data.minPrice = $("#minPrice").val();
							data.maxPrice = $("#maxPrice").val();
							
							data.activityType = $("#schemeId").val();
							
							data.url = $("#url").val();
							var urls = '${ctx}/hcf/activityManagement/save';
							if (data.id != '') {
								urls = '${ctx}/hcf/activityManagement/updatePrice';
							}
							$
									.ajax({
										type : 'post',
										url : urls,
										data : JSON.stringify(data),
										dataType : "json",
										contentType : "application/json",
										success : function(data) {
											alert(data.msg);
											closeLoading()
											if (data.code == "0") {
												window.location.href = "${ctx}/hcf/activityManagement/list";
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
	
	

   function editactivity(){
				var schemeNo= $("#schemeId").val()
			
				var  url ='${ctx}/hcf/activityManagement/selectImg';
				$.ajax({
			       	 type:'post',
			       	 url:url,
			     	data: schemeNo,
			       	 dataType:"json",
			       	 contentType:"application/json",
			       	 success:function(data){
			       		 
			       		 if(data.maxImg!=""){
			       			 var imgMaxImg = document.getElementById('file'); 
					       		imgMaxImg.innerHTML = '<img src=${ctxFile}/'+ data.maxImg +' />';  
			       			 
			       		 }
			       		 if(data.minImg!=""){
			       			 var imgMaxImg = document.getElementById('file'); 
					       		imgMaxImg.innerHTML = '<img src=${ctxFile}/'+ data.minImg +' />';  
			       			 
			       		 }
			       		
			       		 
			       	 }
				});  
				
			}

	 
</script>

<form:form id="uploadAdForm" modelAttribute="activityManageVo"
	action="${ctx}/hcf/activityManagement/save" method="post"
	enctype="multipart/form-data" class="breadcrumb form-search">

	<input id="id" name="id" type="hidden" value="" />
	<div class="form-horizontal">
	 	<div class="pure-g">
			<div class="pure-u-2-3">
				<div class="control-group">
					<label class="control-label">活动类型：</label>
					<div class="controls">
						<select id="schemeId" name="schemeId" style="width: 220px" onchange="editactivity()"
							class="form-control " placeholder="选择类型">
							<option value="">请选择类型</option>
							<c:forEach items="${activityTypeList}" var="type"
								varStatus="indexStatus">
			      	<option value="${type.activityType}">${type.activityType}</option>			
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</div> 
		
	

			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">活动名称：</label>
						<div class="controls">
							<input id="schemeName" name="schemeName" class="form-control"
								type="text" />
						</div>
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">活动编码：</label>
						<div class="controls">
							<input id="scheme" name="schemeNo" class="form-control"
								type="text" />
						</div>
					</div>
				</div>
			</div>
			
				<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">最低礼品价格(单位:元)：</label>
						<div class="controls">
							<input id="minPrice" name="minPrice" class="form-control"
								type="text" />
						</div>
					</div>
				</div>
			</div>

			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">最高礼品价格(单位:元)：</label>
						<div class="controls">
							<input id="maxPrice" name="maxPrice" class="form-control"
								type="text" />
						</div>
					</div>
				</div>
			</div>

			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">url地址：</label>
						<div class="controls">
							<input id="url" name="url" class="form-control" type="text"  />
						</div>
					</div>
				</div>
			</div>
	
	
	
	
		<div class="pure-g" id="imgDiv">
			<div class="pure-u-2-3">
				<div class="control-group">
					<div class="boxs" style="margin-left: 60px">
						<div class="box-left" id="file"
							style="margin-left: 20px; border-left-style: solid;"></div>
						
				</div>
			</div>
		</div>
	</div> 
	
		
		<div class="pure-g" style="margin-left: 40px">
			<div class="pure-u-2-3">
				<div class="control-group">
					<div class="controls">
						<input id="btnsave" name="btnsave" class="btn" type="button"
							value="保存" />
					</div>
				</div>
			</div>
		</div>
  </div>
</form:form>
