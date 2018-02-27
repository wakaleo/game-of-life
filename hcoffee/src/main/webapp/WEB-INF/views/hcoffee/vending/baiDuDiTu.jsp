<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
		#l-map{height:100%;width:78%;float:left;border-right:2px solid #bcbcbc;}
		#r-result{height:100%;width:20%;float:left;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=DS54EWgn9WuZjaOSxDYkH1gcF0KGUlqP"></script>
	<script src="/hkfsysadmin/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="/hkfsysadmin/static/jquery/jquery.blockUI.max.js" type="text/javascript"></script>

<script src="/hkfsysadmin/static/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>

<script src="/hkfsysadmin/static/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>

<script src="/hkfsysadmin/static/jquery-validation/1.14.0/dist/jquery.validate.js" type="text/javascript"></script>
<script src="/hkfsysadmin/static/jquery-validation/1.14.0/dist/additional-methods.min.js" type="text/javascript"></script>

<script src="/hkfsysadmin/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>

<script src="/hkfsysadmin/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="/hkfsysadmin/static/common/mustache.min.js" type="text/javascript"></script>


<script src="/hkfsysadmin/static/common/jet.js" type="text/javascript"></script>
<script src="/hkfsysadmin/static/common/common.js" type="text/javascript"></script>
<script src="/hkfsysadmin/static/momentJs/moment.min.js"></script>
<script src="/hkfsysadmin/static/Pikaday-1.3.2/pikaday.js"></script>

<script type="text/javascript">var ctx = '/hkfsysadmin/a', ctxStatic='/hkfsysadmin/static';</script>
	
	<title>添加多个标注点</title>
</head>
<body style="background:#CBE1FF">

     
    <form:form 
		  
		action="#" method="post"
		class="breadcrumb form-search">
		 <input id="vendCode" name="vendCode" type="hidden" value="${vendingMachine.vendCode }"/>
		 <input id="channel" name="channel" type="hidden" value="${vendingMachine.channel }"/>
		 <input id="channelName" name="channelName" type="hidden" value="${vendingMachine.channelName }"/>
		
		 
		    <div style="width:730px;margin:auto;">  
   
        <nobr>
        输入售货机地址：<input id="suggestId" type="text" value=" " style="margin-right:100px;"/>
   <input id="zuobiao" type="text" value=" "   hidden=" "/> 
        <input type="button" value="查询" onclick="searchByStationName();"/> &nbsp &nbsp 
        <input type="button" value="保存" onclick="btnsave();"/>
        </nobr>
        <div id="container" 
            style="position: absolute;
                margin-top:30px; 
                width: 730px; 
                height: 590px; 
                top: 50; 
                border: 1px solid gray;
                overflow:hidden;">
        </div>
    </div> 
   
 </form:form>
   
</body>
<script type="text/javascript">
    var map = new BMap.Map("container");
    map.centerAndZoom("广州", 12);
    
    var ac = new BMap.Autocomplete(    //建立一个自动完成的对象

    		{"input" : "suggestId"

    		,"location" : map

    		}); 
    
    map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
    map.addControl(new BMap.OverviewMapControl({ isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT }));   //右下角，打开

    var localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小
function searchByStationName() {
    map.clearOverlays();//清空原来的标注
    var keyword = document.getElementById("suggestId").value;
    localSearch.setSearchCompleteCallback(function (searchResult) {
        var poi = searchResult.getPoi(0);
        document.getElementById("zuobiao").value = poi.point.lng + "," + poi.point.lat;
        map.centerAndZoom(poi.point, 13);
        var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
        var point = new BMap.Point(poi.point.lng, poi.point.lat)

        map.centerAndZoom(point,14);

        var geoc = new BMap.Geocoder();    
        
        map.addOverlay(marker);
        marker.enableDragging();  
        
        marker.addEventListener("dragend", function(e){  //拖动事件 

        	var pt = e.point;

        	var dizhi;

        	geoc.getLocation(pt, function(rs){

        	var addComp = rs.addressComponents;

        	   dizhi = addComp.city + addComp.district + addComp.street + addComp.streetNumber;

        	document.getElementById('suggestId').value = dizhi;//更新地址数据

        	       var content = dizhi + "<br/><br/>经度：" + e.point.lng + "<br/>纬度：" + e.point.lat;

        	        var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>"); 

        	marker.openInfoWindow(infoWindow,map.getCenter());//将经纬度信息显示在提示框内

        	         });

        	        document.getElementById("zuobiao").value = e.point.lng + ", " + e.point.lat;//打印拖动结束坐标  

        	    }); 
        	    
        
        
    /*     
        var content = document.getElementById("text_").value + "<br/><br/>经度：" + poi.point.lng + "<br/>纬度：" + poi.point.lat;
        
       
        var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
        marker.addEventListener("click", function () { this.openInfoWindow(infoWindow); }); */
        // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    });
    localSearch.search(keyword);
} 
    
function btnsave(){
	
	
	
	var points =$("#zuobiao").val().split(",");
	var laction =$("#suggestId").val().split(",");
	//alert("地址"+dizhis);
	var lactions=laction[0];
	var lang=points[0];
	var lat=points[1];
	if(points==''){
		alert("请删掉提示框内在保存")
	}
	
	
	var vendCode= $("#vendCode").val();
	
	
	var channel= $("#channel").val();
	var channelName= $("#channelName").val();
	$.ajax({
		type:"post",
		url:'${ctx}/hcf/vendingMachine/saveTubiao',
		 data:JSON.stringify({"lang":lang,"lat":lat,"vendCode":vendCode,"channel":channel,"channelName":channelName,"laction":lactions}),
       	 dataType:"json",
      	 contentType:"application/json",  
		success:function(data){
			if(data.code=="0"){
       			window.location.href="${ctx}/hcf/vendingMachine/list";
       		}
		}
	});  
	
}

    
    
</script>
</html>