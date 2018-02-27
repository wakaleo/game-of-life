//////定义上传方法函数
var id="1";
function PreviewImage(imgFile) { 
    var pattern = /(\.*.jpg$)|(\.*.png$)|(\.*.jpeg$)|(\.*.gif$)|(\.*.bmp$)/;      
    if(!pattern.test(imgFile.value)) { 
      alert("系统仅支持jpg/jpeg/png/gif/bmp格式的照片！");  
      imgFile.focus(); 
    }else{
       //定义图片路径 
       var path;
       //添加显示图片的HTML元素
       id += 1;
       $(".img-cont").append("<div><div id='"+id+"'><img src='' /></div><a class='hide delete-btn' >删除</a></div>");
       //判断浏览器类型
       if(document.all){ 
       //兼容IE
        imgFile.select(); 
        path = document.selection.createRange().text;
        document.getElementById(id).innerHTML=""; 
        document.getElementById(id).style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\"" + path + "\")";//使用滤镜效果 
       }else{
        //兼容其他浏览器 
        path = URL.createObjectURL(imgFile.files[0]);
        document.getElementById(id).innerHTML = "<img src='"+path+"' width='300' height='220' />"; 
       }
       //重置表单
       resetForm(imgFile); 
    } 
}  
function videoPreview(file) { 
	 if (file.files && file.files[0]) {
			var path = file.value.toString();
			var perfix = path.substring(path.indexOf('.') + 1);
			var regExp = /(mp4)/gi;
			if (!perfix.match(regExp)) {
				document.getElementById("progressPercent").style.display="none"; 
				alert('文件格式不对，请重新选择');
				return false;

			}
			if (file.files[0].size > 1024 * 1024 * 50) {
				document.getElementById("progressPercent").style.display="none"; 
				alert("文件太大，请重新选择!");
				return false;
			}
		//定义图片路径 
		var path;
		//添加显示图片的HTML元素
		id += 1;
		$(".img-cont").append("<div><div id='"+id+"'><video img src='' /></div><a class='hide delete-btn' >删除</a></div>");
		//判断浏览器类型
		if(document.all){ 
			//兼容IE
			file.select(); 
			path = document.selection.createRange().text;
			document.getElementById(id).innerHTML=""; 
			document.getElementById(id).style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\"" + path + "\")";//使用滤镜效果 
		}else{
			//兼容其他浏览器 
			path = URL.createObjectURL(file.files[0]);
			document.getElementById(id).innerHTML = "<video img src='"+path+"' width='300' height='220' />"; 
			/*var videoDiv = document.getElementById(id);
		
				videoDiv.innerHTML = "<video img src='"+evt.target.result+"' width='300' height='220' />"; 
			
			reader.readAsDataURL(file.files[0]);*/
		
		}
		//重置表单
		reader.readAsDataURL(file.files[0]);
	} 
}  

//重置表单,允许用户连续添加相同的图片
function resetForm(imgFile){
  $(imgFile).parent()[0].reset();
}

//控制"按钮"显示与隐藏
$(".img-cont").off("mouseenter","div").on("mouseenter","div",function(){
    var that=this;
    var dom=$(that).children("a");
    dom.removeClass("hide");
    //为点击事件解绑，防止重复执行
    dom.off("click");
    dom.on("click",function(){
    	//删除当前图片
     	dom.parent().remove();
     });
}).off("mouseleave","div").on("mouseleave","div",function(){
    var that=this;
    $(that).children("a").addClass("hide");
})

 
   
