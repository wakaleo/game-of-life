<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=DS54EWgn9WuZjaOSxDYkH1gcF0KGUlqP"></script>
		<script src="/hkfsysadmin/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	
	<title>显示售货机分布的标注图</title>
</head>
<body>
	<div id="allmap"></div>

	<form:form 
		action="#" method="post">
		 <input id="piontList" name="piontList" type="hidden" value="${piontList}"/>
		 
	 </form:form>
</body>
</html>
<script type="text/javascript">

// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.centerAndZoom("广东省",12); 
 
map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用// 初始化地图,设置城市和地图级别。
map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
map.addControl(new BMap.OverviewMapControl({ isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT }));   //右下角，打开
var localSearch = new BMap.LocalSearch(map);
localSearch.enableAutoViewport(); //允许自动调节窗体大小
/*  var points = [
    [114.067112,22.530844],
    [114.047493,22.53934]
 ];  */
 // 向地图添加标注
 var str=($("#piontList").val()); 
	 var strs=str.substring(0,str.length-1);
	
	 var points=strs.split(",");
	 
 if(points.length%2>=0 ){
    for( var i = 0;i < points.length; i=i+2){
		  
	     var myIcon = new BMap.Icon( new BMap.Size(23, 25), {
	          // 指定定位位置
	         offset: new BMap.Size(10, 25),
	         // 当需要从一幅较大的图片中截取某部分作为标注图标时，需要指定大图的偏移位置   
	         imageOffset: new BMap.Size(0, 0 - i * 25) // 设置图片偏移   
	     });
	     
	   
	     var point = new BMap.Point(points[i],points[i+1]);
	   
	     // 创建标注对象并添加到地图 
	     var marker = new BMap.Marker(point);
	    map.addOverlay(marker);
	 };
		 
 }
	  
	 
 
 
</script>
