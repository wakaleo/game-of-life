  <%@ page contentType="text/html;charset=UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
  
  	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="author" content="http://github.com/hlej"/>
<meta name="renderer" content="webkit"><meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta http-equiv="Expires" content="0"><meta http-equiv="Cache-Control" content="no-cache"><meta http-equiv="Cache-Control" content="no-store">
<script src="/hkfsysadmin/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="/hkfsysadmin/static/jquery/jquery.blockUI.max.js" type="text/javascript"></script>

<link href="/hkfsysadmin/static/bootstrap/2.3.1/css_flat/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script src="/hkfsysadmin/static/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>

<link href="/hkfsysadmin/static/jquery-select2/3.4/select2.min.css" rel="stylesheet" />
<script src="/hkfsysadmin/static/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>

<link href="/hkfsysadmin/static/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="/hkfsysadmin/static/jquery-validation/1.14.0/dist/jquery.validate.js" type="text/javascript"></script>
<script src="/hkfsysadmin/static/jquery-validation/1.14.0/dist/additional-methods.min.js" type="text/javascript"></script>

<link href="/hkfsysadmin/static/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="/hkfsysadmin/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>

<script src="/hkfsysadmin/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="/hkfsysadmin/static/common/mustache.min.js" type="text/javascript"></script>

<link href="/hkfsysadmin/static/common/jet.css" type="text/css" rel="stylesheet" />

<script src="/hkfsysadmin/static/common/jet.js" type="text/javascript"></script>
<script src="/hkfsysadmin/static/common/common.js" type="text/javascript"></script>
<link href="/hkfsysadmin/static/purecss/pure-min.css" rel="stylesheet" />
<link rel="stylesheet" href="/hkfsysadmin/static/Pikaday-1.3.2/css/pikaday.css">
<script src="/hkfsysadmin/static/momentJs/moment.min.js"></script>
<script src="/hkfsysadmin/static/Pikaday-1.3.2/pikaday.js"></script>

<script type="text/javascript">var ctx = '/hkfsysadmin/a', ctxStatic='/hkfsysadmin/static';</script>

	
<script  type="text/javascript">


function createMaxImg(){
  var data = {};
	var schemeNo=$("#ids").val();
	
	alert("schemeNo"+schemeNo)
	data.schemeNo=$("#ids").val()
	var  url ='${pageContext.request.contextPath}${fns:getAdminPath()}/hcf/activityTemplatet/selectSchemeName';
	$.ajax({
       	 type:'post',
       	 url:url,
     	data: JSON.stringify(data),
       	 dataType:"json",
       	 contentType:"application/json",
       	 success:function(data){
       		if(data.id!=''){
       		 alert(data.schemeName)
       			$("#schemeName").val(data.schemeName);
       			
       		
       		}
       		
       
       	 }
	})
 
}
function createMinImg(){
  var data = {};
	var schemeNo=$("#minId").val();
	
	alert("schemeNo"+schemeNo)
	data.schemeNo=$("#minId").val()
	var  url ='${pageContext.request.contextPath}${fns:getAdminPath()}/hcf/activityTemplatet/selectSchemeName';
	$.ajax({
       	 type:'post',
       	 url:url,
     	data: JSON.stringify(data),
       	 dataType:"json",
       	 contentType:"application/json",
       	 success:function(data){
       		if(data.id!=''){
       		 alert(data.schemeName)
       			$("#schemeName").val(data.schemeName);
       			
       		
       		}
       		
       
       	 }
	})
 
}
function createMinTypes(){
  var data = {};
	var schemeNo=$("#actId").val();
	
	alert("schemeNo"+schemeNo)
	data.schemeNo=$("#actId").val()
	var  url ='${pageContext.request.contextPath}${fns:getAdminPath()}/hcf/activityTemplatet/selectSchemeName';
	$.ajax({
       	 type:'post',
       	 url:url,
     	data: JSON.stringify(data),
       	 dataType:"json",
       	 contentType:"application/json",
       	 success:function(data){
       		if(data.id!=''){
       		 alert(data.schemeName)
       			$("#schemeName").val(data.schemeName);
       			
       		
       		}
       		
       
       	 }
	})
 
}
function createMinTypeDai(){
  var data = {};
	var schemeNo=$("#daiId").val();
	
	alert("schemeNo"+schemeNo)
	data.schemeNo=$("#daiId").val()
	var  url ='${pageContext.request.contextPath}${fns:getAdminPath()}/hcf/activityTemplatet/selectSchemeName';
	$.ajax({
       	 type:'post',
       	 url:url,
     	data: JSON.stringify(data),
       	 dataType:"json",
       	 contentType:"application/json",
       	 success:function(data){
       		if(data.id!=''){
       		 alert(data.schemeName)
       			$("#schemeName").val(data.schemeName);
       			
       		
       		}
       		
       
       	 }
	})
 
}
	</script>
		<style>
.divcss5{width:100px;height:50px;border:1px solid #F00}
</style>
	<form:form id="anctivityTempleVoForm" modelAttribute="anctivityTempleVo"
		action="#" method="post"
		class="breadcrumb form-search">
		 <input id="id" name="id" type="hidden" value=""/>
          
		<div class="form-horizontal" >
		
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
				
						<label class="control-label">注册类大图：</label>
						<div class="controls">
							<select id="ids" name="ids" style="width: 150px" class="form-control select2" onchange="createMaxImg()"
								placeholder="请添加活动"><option value="">请添加活动</option>
								<c:forEach items="${activityTypeMaxImg}" var="vor" varStatus="indexStatus">
									<option value="${vor.schemeNo}">${vor.schemeName}</option>
								</c:forEach>
							</select>
							
						</div>
					</div>
				</div>
			</div>
				<br>
				<br>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">注册类小图：</label>
						<div class="controls">
							<select id="minId" name="minId" style="width: 150px" class="form-control select2" onchange="createMinImg()"
								placeholder="请添加活动"><option value="">请添加活动</option>
								<c:forEach items="${activityTypeMinImg}" var="vor" varStatus="indexStatus">
									<option value="${vor.schemeNo}">${vor.schemeName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			
				<br>
				<br>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">广告类：</label>
						<div class="controls">
							<select id="actId" name="actId" style="width: 150px" class="form-control select2" onchange="createMinTypes()"
								placeholder="请添加活动"><option value="">请添加活动</option>
								<c:forEach items="${activityTypes}" var="vor" varStatus="indexStatus">
									<option value="${vor.schemeNo}">${vor.schemeName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			<br>
			<br>
		
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
					<nobr>
						<label class="control-label">贷款类：</label>
						<div class="controls">
							<select id="daiId" name="daiId" style="width: 150px" class="form-control select2" onchange="createMinTypeDai()"
								placeholder="请添加活动"><option value="">请添加活动</option>
								<c:forEach items="${activityTypeDai}" var="vor" varStatus="indexStatus">
									<option value="${vor.schemeNo}">${vor.schemeName}</option>
								</c:forEach>
							</select>
							</nobr>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>



		
		
		
	</form:form>
 