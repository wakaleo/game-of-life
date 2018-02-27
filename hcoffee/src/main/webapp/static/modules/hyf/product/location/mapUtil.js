	//解决IE8不兼容
	if (!Function.prototype.bind) {  
	Function.prototype.bind = function (oThis) {  
	if (typeof this !== "function") {  
	throw new TypeError("Function.prototype.bind - what is trying to be bound is not callable");  
	}  
	var aArgs = Array.prototype.slice.call(arguments, 1),  
	fToBind = this,  
	fNOP = function () {},  
	fBound = function () {  
	return fToBind.apply(this instanceof fNOP && oThis  
	? this 
	: oThis,  
	aArgs.concat(Array.prototype.slice.call(arguments)));  
	};  
	fNOP.prototype = this.prototype;  
	fBound.prototype = new fNOP();  
	return fBound;  
	};  
	}
 //初始化地图
	function init(){
		//var map = new BMap.Map("allmap");		
		var point = new BMap.Point(113.947645,22.530347);
		//addMarkerLabelAndCenter(point,"汇联宜家",14)		
		var marker = new BMap.Marker(point);
		marker.disableMassClear();
		GlobalVar.HLPoint = 	point;
		var opts = {type: BMAP_NAVIGATION_CONTROL_ZOOM};
		map.addControl(new BMap.NavigationControl(opts));
		map.addControl(new BMap.GeolocationControl());	
		map.addControl(new BMap.ScaleControl());    
		map.addControl(new BMap.OverviewMapControl());    
		map.addControl(new BMap.MapTypeControl()); 			
		map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
		map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用		
	  map.enableKeyboard(); //键盘放大
		map.enablePinchToZoom();	
			
		GlobalVar.map = map;//将map变量存储在全局
		GlobalVar.currenPoint = point;
		GlobalVar.currenMarker = marker;	
		
		//getMyAddressByHtml5();
	}

//通过HTML5来获取用户当前位置
function getMyAddressByHtml5(){
	var options={};
	try{
		if(navigator.geolocation){
		  	navigator.geolocation.getCurrentPosition(success,error,options);
		}else{
			//alert("你的浏览器不支持HTML5定位功能");
			//getMyAddressByBaidu();
		}			
	}catch(e){
			//alert("浏览器定位功能未开启！");
			//getMyAddressByBaidu();
	}
}

//地址转换成功后的回掉函数
function success(position)	{
		var lng = position.coords.longitude;
		var lat = position.coords.latitude;    
	  var point = new BMap.Point(lng, lat);
//	  addMarkerLabelAndCenter(point,"未转换的GPS坐标（错误）",16)
	  //将gps坐标转换为百度地址坐标
	  var convertor = new BMap.Convertor();
	  var pointArr = [];
	  pointArr.push(point);
	  convertor.translate(pointArr, 1, 5, translateCallback);
}

	//GPS转换百度坐标
function translateCallback(point) {
    if(point.status == 0){  	
    	pt = point.points[0];
    	GlobalVar.myAddressPoint = r.point;
      addDragendMarker(pt,"我的位置");     
	  }else{
	  		alert("GPS转换百度坐标失败");
	  		getMyAddressByBaidu();
	  }
}	

	//通过百度地图API来获取用户当前位置
function getMyAddressByBaidu(){		
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r){
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			GlobalVar.myAddressPoint = r.point;
			 addDragendMarker(r.point,"我的位置"); 
		}
		else {
			alert('failed'+this.getStatus());
		}        
	},{enableHighAccuracy: true});
}

function addressToMap(address,city){
	address = document.getElementById("cmbProvince").value+document.getElementById("cmbCity").value+document.getElementById("cmbArea").value;
	city = document.getElementById("cmbCity").value;
	//alert(address);
	// 创建地址解析器实例
	var myGeo = new BMap.Geocoder();
	// 将地址解析结果显示在地图上,并调整地图视野
	myGeo.getPoint(address, function(point){
		if (point) {
		//	map.centerAndZoom(point, 16);
		//	map.addOverlay(new BMap.Marker(point));
		map.clearOverlays();
		//addMarkerLabelAndCenter(GlobalVar.HLPoint,"汇联宜家",14)
		//addDragendMarker(GlobalVar.myAddressPoint,"我的位置",14)
		GlobalVar.currenPoint = point;
		addDragendMarker(point,"定位结果");
		}else{
			alert("您选择地址没有解析到结果!");
		}
	}, city);
}

//右键单击marker出现右键菜单事件
function rightClickHandler(marker,txtMenuItem){ 
	txtMenuItem = txtMenuItem ? txtMenuItem : GlobalVar.txtMenuItem; 
 var markerMenu=new BMap.ContextMenu();
 for(var i=0; i < txtMenuItem.length; i++){
		markerMenu.addItem(new BMap.MenuItem(txtMenuItem[i].text,txtMenuItem[i].callback.bind(marker),100));
 };
  marker.addContextMenu(markerMenu);//给标记添加右键菜单
  marker.addEventListener("click", function(){
  GlobalVar.currenPoint = this.getPosition();
  });
}
 
//添加标记、标签按、菜单、设置视图中心和放大倍数
function addMarkerLabelAndCenter(point,tip,num){
	var marker = new BMap.Marker(point);
	emptyTip = (tip == null || tip == undefined || tip == "");
	if(!emptyTip){
		var label = new BMap.Label(tip,{offset:new BMap.Size(20,-10)});		
  	marker.setLabel(label); 
	} 		
	rightClickHandler(marker,GlobalVar.txtMenuItem);
	map.addOverlay(marker);
	map.panTo(point);
	map.centerAndZoom(point,num);
}

//添加事件
function addEvent(){
 	map.addEventListener("click",function(e){
		alert(e.point.lng + "," + e.point.lat);
		addMarkerLabelAndCenter(e.point,"",16);
	});	
	//map.addEventListener("longpress", showWindow);
}

// 初始化多关键字搜索
	var initSearch = function (){
	  GlobalVar.searchResult = "";
    GlobalVar.searchResultList = [];//搜索下拉的列表
    GlobalVar.movedMarker = [];//移动过的标注 //---by lizx 20161014 -3
    GlobalVar.indexNumber = 0;
    prePoi = {};
    first = 0;
    GlobalVar.map = new BMap.Map("allmap");
  }

//多关键字搜索
/**
*<searchInput>key</searchInput> 搜索输入框ID，默认为"key"
*<splitChar>" "</splitChar> 多个关键字分隔符，默认为空格“ ”
*<searchResultPanel>r-result</searchResultPanel> 搜索结果展示DIV，默认为"r-result"
*<map>map<map> 地图对象，默认为自定义全局变量GlobalVar.map
*/
var search = function(searchInput,splitChar,searchResultPanel,map){
	initSearch(); 
	searchInput = searchInput ? searchInput : "key";
	splitChar = splitChar ? splitChar : " ";
	searchResultPanel = searchResultPanel ? searchResultPanel : "r-result";
	map = map ? map : GlobalVar.map;
	var myKeyString = document.getElementById(searchInput).value; 
	var myKeys = myKeyString.split(splitChar);
	if(GlobalVar.City.indexOf("市")>0){
		var city =GlobalVar.City.substring(0,GlobalVar.City.length-1);
	}
	//alert(GlobalVar.City);
	var localSearch = new BMap.LocalSearch(city, {
		renderOptions:{map: map, panel:searchResultPanel},
		pageCapacity:5,
		onResultsHtmlSet:function(container){
			//	container.onclick= alert(container.childNodes);
				var liOption = container.firstChild.firstChild.childNodes;
				for(var i = 0; i<liOption.length; i++){
					var opt = liOption[i].lastChild;
					opt.onclick = function(opt,m){
						return function(opt){
							GlobalVar.indexNumber = m;//记录当前点击的下拉列表选项 by lizx 20161014
		  				GlobalVar.currenPoint = GlobalVar.searchResult[m].marker.getPosition();
		  			};
		  		}(opt,i);
				}
			}
	});
	// ---by 20161013
		// 点击搜索下拉列表，显示标注动画（弹跳），并可以移动---by 20161013
	localSearch.setInfoHtmlSetCallback(function(poi,html){
		if(first == 0){
			first = 1;//记录是否默认的自动选择第一个结果
			prePoi = poi;
			return ;
		}
		var firstClick = !GlobalVar.searchResultList[GlobalVar.indexNumber];
		prePoi.marker.setAnimation();//上次的选择结果停止动作
		prePoi.marker.disableDragging();
		prePoi = poi;
		if(firstClick){
			GlobalVar.searchResultList[GlobalVar.indexNumber] = poi;
			addDragendMarker2(poi.marker);
		}else{
			if(GlobalVar.movedMarker[GlobalVar.indexNumber]){
				 GlobalVar.searchResultList[GlobalVar.indexNumber].marker.closeInfoWindow();//关闭信息窗;//---by lizx 20161014 -7
			}
			addDragendMarker2(GlobalVar.searchResultList[GlobalVar.indexNumber].marker);
		}
			prePoi = poi;
	});
	// ---by 20161013
	localSearch.searchInBounds(myKeys, map.getBounds());
	localSearch.setMarkersSetCallback(function(searchResult){
		GlobalVar.searchResult = searchResult;
		for(var i=0; i < searchResult.length; i++){			
			var marker = searchResult[i].marker; 
				rightClickHandler(marker,GlobalVar.txtMenuItem);
				GlobalVar.currenPoint = "";
		}	
	});
	
}	

//自动补全
/**
*<input>suggestId</input> 搜索输入框
*<searchResultPanel>searchResultPanel</searchResultPanel> 结果展示DIV
*<map>new BMap.Map("allmap")--Map对象</map>
*/
function autoComplete(input,searchResultPanel,map){
	input = input ? input :"suggestId";
	searchResultPanel = searchResultPanel ? searchResultPanel :"searchResultPanel";
	map = map ? map : GlobalVar.map;
	// 百度地图API功能
	function G(id) {
		return document.getElementById(id);
	}

	var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
		{"input" : input
		,"location" : map
	});

	ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
	var str = "";
		var _value = e.fromitem.value;
		var value = "";
		if (e.fromitem.index > -1) {
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
		
		value = "";
		if (e.toitem.index > -1) {
			_value = e.toitem.value;
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
		G(searchResultPanel).innerHTML = str;
	});

	var myValue;
	ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
	var _value = e.item.value;
		myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		G(searchResultPanel).innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
		setPlace();
	});

	function setPlace(){
		map.clearOverlays();    //清除地图上所有覆盖物
		
		function myFun(){
			var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
			
			//addMarkerLabelAndCenter(GlobalVar.HLPoint,"汇联宜家",14);
			//addDragendMarker(GlobalVar.myAddressPoint,"我的位置",14)
			addDragendMarker(pp,"搜索结果：");
			GlobalVar.currenPoint = pp;
		
		//	map.centerAndZoom(pp, 18);
		//	map.addOverlay(new BMap.Marker(pp));    //添加标注
		}
		var local = new BMap.LocalSearch(map, { //智能搜索
		  onSearchComplete: myFun
		});
		local.search(myValue);
	}
	
}



//--- ---by 20161013
function addDragendMarker2(marker){
		point = marker.point;
		map.addOverlay(marker);
		rightClickHandler(marker)		
    marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画  
    marker.enableDragging();//设置点可拖动  
    marker.addEventListener("dragend",function(){
    	var currentLabel = "";
    	return function(){
    		point = marker.point;
    		GlobalVar.currenPoint = point;
    		map.centerAndZoom(point,18); //---by lizx 20161014 -4
    		GlobalVar.movedMarker[GlobalVar.indexNumber] = marker; //---by lizx 20161014 -5
    		preMarker = prePoi.marker;
    		if(preMarker){//---by lizx 20161014 -4
    				preMarker.setLabel();    		
    				preMarker.setAnimation();
    				map.addOverlay(preMarker);
    		}    	
    		preMarker = marker;
    		var geoc = new BMap.Geocoder();  
	   		 geoc.getLocation(point, function(rs){  
		        var addComp = rs.addressComponents;  
		        var address = "地址：" + addComp.province + "," + addComp.city + "," + addComp.district + "," + addComp.street + "," + addComp.streetNumber+", 经纬度："+point.lng + "," +point.lat;   
		        currentLabel = currentLabel || new BMap.Label("", { offset: new BMap.Size(20, -10) });  
		        currentLabel.setContent(address);
		        marker.setLabel(currentLabel); //添加百度标注   
		        marker.closeInfoWindow();//关闭信息窗
		        map.centerAndZoom(point,18);
		    }); 
    	}
    }(marker));  
}
// ---by 20161013

//添加拖到标签
function addDragendMarker(point,labelText,showText,endCallBack){		
		labelText = labelText ? labelText : "目的地";
//		showText = showText ? showText : "<br/><a href='http://api.map.baidu.com/geocoder/v2/?ak=mHdfy1fLVVzuzMXHwamfqRA14nhuPwlr&address="+address+"&city=北京市'>发送到汇联</a>";
		showText = showText ? showText : "";
		endCallBack = endCallBack ? endCallBack : getAttr;
			
		marker = new BMap.Marker(point);
		GlobalVar.currenPoint = point;
		GlobalVar.currenMarker = marker;	
		GlobalVar.map.addOverlay(marker);
		var label = new BMap.Label(labelText,{offset:new BMap.Size(25,-10)});
		rightClickHandler(marker,GlobalVar.txtMenuItem)		
  	marker.setLabel(label);   
    marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画  
    marker.enableDragging();//设置点可拖动  
  //  marker.addEventListener("dragend",endCallBack(marker.getPosition()));  
  	marker.addEventListener("dragend",getAttr);
    map.centerAndZoom(point,18); 
   getPosit(point); 
   
   function getAttr(object){  
//    var p = marker.getPosition();       //获取marker的位置  
    var p = object.point;
    GlobalVar.currenPoint = p;
    marker = this;
    var labelText =  object.target.zc.innerText;
    getPosit(new BMap.Point(p.lng, p.lat));  
	}  
  
  
	function getPosit(obj){  
    var geoc = new BMap.Geocoder();  
    geoc.getLocation(obj, function(rs){  
        var addComp = rs.addressComponents;  
        var address = labelText + ":" + addComp.province + "," + addComp.city + "," + addComp.district + "," + addComp.street + "," + addComp.streetNumber+", 经纬度："+obj.lng + "," +obj.lat;   
        address = address + showText;
        var labelbaidu = new BMap.Label(address, { offset: new BMap.Size(25, -10) });  
        marker.setLabel(labelbaidu); //添加百度标注   
    });  
	}      
}

//通过经纬度获取地址,并封传递给回调函数的参数
function getAddressByPoint(point,callback){
	var geoc = new BMap.Geocoder();  
    geoc.getLocation(GlobalVar.currenPoint, function(rs){  
        var addComp = rs.addressComponents;  
        var resultAddress = addComp.province + "," + addComp.city + "," + addComp.district + "," + addComp.street + "," + addComp.streetNumber;   
      	var result = {};
      	result.address = resultAddress;
      	result.city = addComp.city;
      	result.point = GlobalVar.currenPoint;
      	result.longitude = GlobalVar.currenPoint.lng;
      	result.latitude = GlobalVar.currenPoint.lat;
       	callback(result);
		});
}