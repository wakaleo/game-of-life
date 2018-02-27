<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代理商文件上传</title>
    <link href="${pageContext.request.contextPath}/static/plugin/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugin/sbb/css/default.css">
    <link href="${pageContext.request.contextPath}/static/plugin/sbb/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/static/plugin/sbb/js/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/plugin/sbb/js/fileinput.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/plugin/sbb/js/locales/zh.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/plugin/sbb/js/bootstrap.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    	$(function(){
    		$(".file-drop-zone-title").html("请上传文件");
    	});
    </script>
</head>
<body>
<div class="container kv-main">
 <form enctype="multipart/form-data">
     <div class="form-group">
         <input id="file_f" class="file" type="file" name="file" multiple>
     </div>
 </form>
</div>
</body>
<script type="text/javascript">
$("#file_f").fileinput({
    uploadUrl: '${ctx}/hcf/dealerManagerment/fileUpload?type=2&loginName=${loginName }',
    //allowedFileExtensions : ['jpg', 'png','gif'],
    overwriteInitial: false,
    maxFileSize: 1000,
    maxFilesNum: 10,
    //allowedFileTypes: ['image', 'video', 'flash'],
    slugCallback: function(filename) {
    	return filename.replace('(', '_').replace(']', '_');
    }
});
</script>
</html>