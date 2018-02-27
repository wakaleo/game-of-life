    
   /**
    * TODO
    * 1.图片预览，和一般文件预览
    * 2.文件删除
    */

	$(document).ready(function(){
		 $("#uploadFileButt").live('click',function(){
			 var fileGourp = $("#file_upload_file_group").val();
			 var fileType = $("#file_upload_file_type").val();
			 var callback = $("#file_upload_callback").val();
			 fileUploadCommon(fileGourp+"_"+fileType,callback);
		 });
		 
		 function fileUploadCommon(id,callback){
				if(id == 'undefined'){
					id= "";
				}
				var uploadFile= $("#uploadFile_file_"+id).val();//<input type="file"/>
				if(uploadFile==null||""==uploadFile){
					showMsg("请选择文件！");
					return;
				}
				var test1 = uploadFile.lastIndexOf("/"); //对路径进行截取
				var test2 = uploadFile.lastIndexOf("\\"); //对路径进行截取
				var test= Math.max(test1, test2);
				var fileType="";
				 if(test<0){ 
					 if(uploadFile.lastIndexOf(".")<0){
						 showMsg("上传的文件格式不对！");
							return;
					 }
					 fileType= uploadFile.substring(uploadFile.lastIndexOf(".")+1,uploadFile.length);
					 
				 }else{
					var temp= uploadFile.substring(test + 1); //赋值文件名
					if(temp.lastIndexOf(".")<0){
						showMsg("上传的文件格式不对！");
							return;
					 }
					fileType=temp.substring(temp.lastIndexOf(".")+1,temp.length);
				}
				if(fileType!='jpg'&&fileType!='png'&&fileType!='rar'&&fileType!='zip'&&fileType!='doc'&&fileType!='docx'&&fileType!='pdf'&&fileType!='ppt'&&fileType!='pptx'&&fileType!='JPG'&&fileType!='PNG'&&fileType!='RAR'&&fileType!='ZIP'&&fileType!='DOC'&&fileType!='DOCX'&&fileType!='PDF'&&fileType!='PPT'&&fileType!='PPTX'){
					showMsg("上传的文件格式不对,只能传*.jpg,*.png,*.rar,*.zip,*.doc,*.docx,*.pdf,*.ppt,*.pptx格式的文件");
					return;
				 }
				ajaxFileUpload(id,callback);//调用上传文件方法 
		 }
		 var ctx = $("#ctx").val();
		 var ctxFile = $("#ctxFile").val();
		 function ajaxFileUpload(id,callback) {
				if(id == 'undefined'){
					id= "";
				}
				$("#fileDiv_file_full_path").remove();
				$("#fileDiv_file_"+id).append("<input id='fileDiv_file_full_path' type = 'hidden' value='true' name = 'fullPath' />");
				$.ajaxFileUpload
		         	(
		             {
		                 url: ctx + '/file/upload',
		                 secureuri: false, //一般设置为false
		                 fileElementId: 'fileDiv_file_'+id,
		                 dataType: 'json', //返回值类型 一般设置为json
		                 success: function (ret, status)  //服务器成功响应处理函数
		                 {  
		                 	$("#uploadFile_file_"+id).val("");
		             	    if(ret.code==1){
		             		  createShowingTable(ret);
		             		  if (typeof callback === "function"){
		             			   callback(ret);
		             		    }else if(typeof callback === "string"){
		             		    	var funCallback = eval(callback);
		             		    	new funCallback(ret);
		             		    }
		             	       } else {
		             	    	  showMsg(ret.msg);
		             	     }
		                 },
		                 error: function (ret, status, e)//服务器响应失败处理函数
		                 {
		                	 showMsg("上传失败"+e);
		                 }
		             }
		         )
		   }
			
		     //动态的创建一个table，同时将后台获取的数据动态的填充到相应的单元格
		   function createShowingTable(dataArray){
		    	 var obj = $("#tab_files");
		    	 if(obj){
		       	  var tdhtml = "";
		    	  tdhtml = "<tr id='file_info' fileName='"+dataArray.fileName+"' filePath='"+dataArray.path+"' align='center'>";
		    	  tdhtml += "<td>"+ dataArray.fileName +"</td><td>";
		    	  tdhtml += "";
		    	  tdhtml += "</td><td>";
		    	  tdhtml += "</td><td>";
			      var fileurl = dataArray.path;
					if(fileurl.endWith("png") || fileurl.endWith("pdf") || fileurl.endWith("jpeg") || fileurl.endWith("jpg") || fileurl.endWith("bmp") || fileurl.endWith("JPG") || fileurl.endWith("JPEG")){
						tdhtml += "<a href='"+dataArray.fullPath+"' style='color: blue;' class = 'view' target='_blank'>预览</a>&nbsp;&nbsp;";
					};
		    	  tdhtml += '<a href="'+ctx+"/file/download?filePath="+fileurl+"&fileName="+dataArray.fileName+'" >下载</a>';
		    	  tdhtml += "</td></tr>";
		    	  var tbodys = obj.find("tbody");
		    	  if(tbodys){
		    		  var tblen = tbodys.length;
		    		  $(tbodys[tblen-1]).append(tdhtml);
		    	  }
		    	 }
		     }
		     
		     String.prototype.endWith=function(s){
		    	  if(s==null||s==""||this.length==0||s.length>this.length)
		    	     return false;
		    	  if(this.substring(this.length-s.length)==s)
		    	     return true;
		    	  else
		    	     return false;
		    	  return true;
		     }
		     function toPreview(filePath){
		    	 
		     }
		     
		   	 function toDelte(obj){
		 	  	var filePath = $(obj).attr("data");
		 		var param = {
					"filePath" : filePath
		 		};
		 		jQuery.ajax({
		 			type : "post", // 请求方式
		 			url : ctx + '/file/deleteFile', // Ajax访问地址
		 			data : param, // 参数
		 			dataType : "json", // 指定返回数据类型
		 			error : function() { // 出现错误时运行
		 				showMsg("文件删除失败！");
		 			},
		 			success : function(data) { // 返回成功时运行，主要接受结果
		 				if(data.code==1){
		 					$(obj).closest("tr[id='file_info']").remove();//删除当前行
		 					showMsg(data.msg);
		 				}else{
		 					showMsg(data.msg);
		 				}
		 			}
		 		});
		 	}
		   	 
		    function showMsg(msg){
		 		$.jBox.tip(msg,"success");
		 	}
	});


