<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctxStatic}/highcharts/highcharts.js"></script>
<script src="${ctxStatic}/highcharts/data.js"></script>
<script src="${ctxStatic}/highcharts/drilldown.js"></script>
<script type="text/javascript">




function init(){
	
	$('#container').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '2017代理商销售统计'
        },
        tooltip: {
            headerFormat: '{series.name}<br>',
            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            type: 'pie',
            name: '浏览器访问量占比',
            data: [
                ['销售金额(元)',45.0],
                ['销售数量',26.8],
                {
                    name: '机器数',
                    y: 12.8,
                    sliced: true,
                    selected: true
                },
                //['机器数',    8.5],
                //['Opera',     6.2],
                //['其他',   0.7]
            ]
        }]
    });
}

$(function () {
	init();
    //饼状图
    $("#pieChart").click(function(){
    	init();
    });
    
    //3D柱状图
    $("#3Dhistogram").click(function(){
    	$('#container').highcharts({
            chart: {
                type: 'column',
                margin: 75,
                options3d: {
                    enabled: true,
                    alpha: 10,
                    beta: 25,
                    depth: 70
                }
            },
            title: {
                text: '包含空值的3D柱状图'
            },
            subtitle: {
                text: '请注意值为 0 和 null 的区别'
            },
            plotOptions: {
                column: {
                    depth: 25
                }
            },
            xAxis: {
                categories: Highcharts.getOptions().lang.shortMonths
            },
            yAxis: {
                title: {
                    text: null
                }
            },
            series: [{
                name: '销售',
                data: [2, 3, null, 4, 0, 5, 1, 4, 6, 3]
            }]
        });
    	$("#container").show();
    });
    
    //折线图
    $("#lineChart").click(function(){
    	var chart = Highcharts.chart('container', {
    	    title: {
    	        text: '2010 ~ 2016 年太阳能行业就业人员发展情况'
    	    },
    	    subtitle: {
    	        text: '数据来源：thesolarfoundation.com'
    	    },
    	    yAxis: {
    	        title: {
    	            text: '就业人数'
    	        }
    	    },
    	    legend: {
    	        layout: 'vertical',
    	        align: 'right',
    	        verticalAlign: 'middle'
    	    },
    	    plotOptions: {
    	        series: {
    	            label: {
    	                connectorAllowed: false
    	            },
    	            pointStart: 2010
    	        }
    	    },
    	    series: [{
    	        name: '安装，实施人员',
    	        data: [43934, 52503, 57177, 69658, 97031, 119931, 137133, 154175]
    	    }, {
    	        name: '工人',
    	        data: [24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]
    	    }, {
    	        name: '销售',
    	        data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
    	    }, {
    	        name: '项目开发',
    	        data: [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
    	    }, {
    	        name: '其他',
    	        data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
    	    }],
    	    responsive: {
    	        rules: [{
    	            condition: {
    	                maxWidth: 500
    	            },
    	            chartOptions: {
    	                legend: {
    	                    layout: 'horizontal',
    	                    align: 'center',
    	                    verticalAlign: 'bottom'
    	                }
    	            }
    	        }]
    	    }
    	});
    });
    
    //条形图
    $("#barChart").click(function(){
    	$('#container').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: '月平均商品销量'
            },
            subtitle: {
                text: '数据来源: toback.huicoffee.com.cn'
            },
            xAxis: {
                categories: [
                    '一月',
                    '二月',
                    '三月',
                    '四月',
                    '五月',
                    '六月',
                    '七月',
                    '八月',
                    '九月',
                    '十月',
                    '十一月',
                    '十二月'
                ],
                crosshair: true
            },
            yAxis: {
                min: 0,
                title: {
                    text: '商品销量 (件)'
                }
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            series: [{
                name: '可口可乐',
                data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
            }, {
                name: '娃哈哈',
                data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]
            }, {
                name: '康师傅',
                data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]
            }, {
                name: '尹妹妹',
                data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]
            }]
        });
    });
    
  	//动态数据
    $("#dynamicData").click(function(){
    	$.ajax({
    		type:"post",
    		url:'${ctx}/hcf/saleManagement/graphicStatistical',
    		data:JSON.stringify(new Date()),
    		dataType:"json",
    		contentType:"application/json",
    		success:function(data){
    			dynamicDatas(data.saleMoney,data.saleCount,data.vendCount);
    		}
    	});
    });
    
  	//总的市场份额
    $("#sumMark").click(function(){
    	
	    	$.ajax({
	    		type:"post",
	    		url:'${ctx}/hcf/saleManagement/graphicStatistical1',
	    		data:JSON.stringify(new Date()),
	    		dataType:"json",
	    		contentType:"application/json",
	    		success:function(resultData){
	    			//dynamicData = resultData.dynamicData;
	    			//console.log("====>"+JSON.parse(resultData.dynamicData));
	    			console.log("====>"+JSON.parse(resultData.dynamicData));
	    			
	    			var dynamicData = JSON.parse(resultData.dynamicData);
	    			var dynamicSeries = resultData.dynamicSeries;
	    			//var sp = JSON.stringify(dynamicSeries);
	    			//dynamicSeries = $.parseJSON(sp);
	    			//console.log("==111= dynamicSeries=>"+sp);
	    			//console.log("==222= dynamicSeries=>"+$.parseJSON(sp));
	    			console.log("--333-dynamicSeries--" + dynamicSeries);
	    			//var tt = dynamicSerieS.substring(0,dynamicSerieS.length-1);
	    			//console.log("-----JSON.stringify-----"+JSON.stringify(dynamicSerieS));
	    			//console.log("-----JJSON.parse-----"+JSON.parse(dynamicSeries));
	    			//console.log("-----JSON.parseJSON-----"+$.parseJSON(JSON.stringify(dynamicSerieS)));
	    			//console.log("dynamicSerieS:"+dynamicSerieS);
	    			//console.log("--666-dynamicSerieS--" + JSON.stringify(dynamicSerieS));
	    			//var jsonStr = JSON.stringify(dynamicSerieS);
	    			//var ss = $.parseJSON(jsonStr);
	    			//console.log("--ss--"+J);
	    			//var dynamicSeries = [{name:'Microsoft Internet Explorer',id:'Microsoft Internet Explorer',data:[['v11.0',24.13],['v8.0',17.2],['v9.0',8.11],['v10.0',5.33],['v6.0',1.06],['v7.0',0.5]]},{name:'Chrome',id:'Chrome',data:[['v40.0',5],['v41.0',4.32],['v42.0',3.68],['v39.0',2.96],['v36.0',2.53],['v43.0',1.45],['v31.0',1.24],['v35.0',0.85],['v38.0',0.6],['v32.0',0.55],['v37.0',0.38],['v33.0',0.19],['v34.0',0.14],['v30.0',0.14]]},{name:'Firefox',id:'Firefox',data:[['v35',2.76],['v36',2.32],['v37',2.31],['v34',1.27],['v38',1.02],['v31',0.33],['v33',0.22],['v32',0.15]]},{name:'Safari',id:'Safari',data:[['v8.0',2.56],['v7.1',0.77],['v5.1',0.42],['v5.0',0.3],['v6.1',0.29],['v7.0',0.26],['v6.2',0.17]]},{name:'Opera',id:'Opera',data:[[],[],['v27',0.17],['v29',0.16]]},{name:'banner',id:'banner',data:[['v12.x',0.34],['v28',0.24],['v27',0.17],['v29',0.16]]},{name:'coffee',id:'coffee',data:[['v12.x',0.34],['v28',0.24],['v27',0.17],['v29',0.16]]}];
	    				  //dynamicSeries = [{name:'Microsoft Internet Explorer',id:'Microsoft Internet Explorer',data:[['v11.0',24.13],['v8.0',17.2],['v9.0',8.11],['v10.0',5.33],['v6.0',1.06],['v7.0',0.5]]},{name:'Chrome',id:'Chrome',data:[['v40.0',5],['v41.0',4.32],['v42.0',3.68],['v39.0',2.96],['v36.0',2.53],['v43.0',1.45],['v31.0',1.24],['v35.0',0.85],['v38.0',0.6],['v32.0',0.55],['v37.0',0.38],['v33.0',0.19],['v34.0',0.14],['v30.0',0.14]]},{name:'Firefox',id:'Firefox',data:[['v35',2.76],['v36',2.32],['v37',2.31],['v34',1.27],['v38',1.02],['v31',0.33],['v33',0.22],['v32',0.15]]},{name:'Safari',id:'Safari',data:[['v8.0',2.56],['v7.1',0.77],['v5.1',0.42],['v5.0',0.3],['v6.1',0.29],['v7.0',0.26],['v6.2',0.17]]},{name:'Opera',id:'Opera',data:[[],[],['v27',0.17],['v29',0.16]]},{name:'banner',id:'banner',data:[['v12.x',0.34],['v28',0.24],['v27',0.17],['v29',0.16]]},{name:'coffee',id:'coffee',data:[['v12.x',0.34],['v28',0.24],['v27',0.17],['v29',0.16]]}];
	    				//dynamicSeries = [{name:'Microsoft Internet Explorer',id:'Microsoft Internet Explorer',data:[['v11.0',24.13],['v8.0',17.2],['v9.0',8.11],['v10.0',5.33],['v6.0',1.06],['v7.0',0.5]]},{name:'Chrome',id:'Chrome',data:[['v40.0',5],['v41.0',4.32],['v42.0',3.68],['v39.0',2.96],['v36.0',2.53],['v43.0',1.45],['v31.0',1.24],['v35.0',0.85],['v38.0',0.6],['v32.0',0.55],['v37.0',0.38],['v33.0',0.19],['v34.0',0.14],['v30.0',0.14]]},{name:'Firefox',id:'Firefox',data:[['v35',2.76],['v36',2.32],['v37',2.31],['v34',1.27],['v38',1.02],['v31',0.33],['v33',0.22],['v32',0.15]]},{name:'Safari',id:'Safari',data:[['v8.0',2.56],['v7.1',0.77],['v5.1',0.42],['v5.0',0.3],['v6.1',0.29],['v7.0',0.26],['v6.2',0.17]]},{name:'Opera',id:'Opera',data:[[],[],['v27',0.17],['v29',0.16]]},{name:'banner',id:'banner',data:[['v12.x',0.34],['v28',0.24],['v27',0.17],['v29',0.16]]},{name:'coffee',id:'coffee',data:[['v12.x',0.34],['v28',0.24],['v27',0.17],['v29',0.16]]}];
	    				//console.log("--777-dynamicSeries--" + dynamicSeries);
	    				//console.log("--888-dynamicSeries--" + JSON.stringify(dynamicSeries));
	    				//dynamicData = [{"drilldown":"Microsoft Internet Explorer","name":"Microsoft Internet Explorer","y":56.33},{"drilldown":"Chrome","name":"Chrome","y":24.03},{"drilldown":"Firefox","name":"Firefox","y":10.38},{"drilldown":"Safari","name":"Safari","y":4.77},{"drilldown":"Opera","name":"Opera","y":0.91},{"drilldown":"banner","name":"banner","y":50.55},{"drilldown":"coffee","name":"coffee","y":65}];
	    			//dynamicSeries = [{'name': 'Microsoft Internet Explorer', id: 'Microsoft Internet Explorer',data: [['v11.0',24.13],['v8.0',17.2],['v9.0',8.11],['v10.0',5.33],['v6.0',1.06],['v7.0',0.5]]},{'name': 'Chrome',id: 'Chrome',data: [ ['v40.0',5],['v41.0',4.32],['v42.0',3.68],['v39.0',2.96],['v36.0',2.53],['v43.0',1.45],['v31.0',1.24],['v35.0',0.85],['v38.0',0.6],['v32.0',],['v37.0',0.38],['v33.0', 0.19],['v34.0', 0.14],['v30.0', 0.14]]},{'name': 'Firefox',id: 'Firefox',data: [['v35',2.76],['v36',2.32],['v37',2.31],['v34',1.27],['v38',1.02],['v31',0.33],['v33', 0.22],['v32', 0.15]]},{'name': 'Safari',id: 'Safari', data: [ ['v8.0', 2.56], ['v7.1',0.77],['v5.1',0.42],['v5.0',0.3], ['v6.1',0.29],['v7.0',0.26],['v6.2',0.17]]},{'name': 'Opera',id: 'Opera',data: [[],[],['v27',0.17],['v29',0.16]]},{'name': 'banner',id: 'banner',data: [ ['v12.x',0.34],['v28',0.24],['v27',0.17],['v29',0.16]]},{'name': 'coffee',id: 'coffee',data: [['v12.x',0.34],['v28',0.24],['v27',0.17],['v29',0.16]]}];
	    		  //dynamicSeries = [{"name":"Microsoft Internet Explorer","id":"Microsoft Internet Explorer","data":[['v11.0',24.13],['v8.0',17.2],['v9.0',8.11],['v10.0',5.33],['v6.0',1.06],['v7.0',0.5]]},{"name": "Chrome","id": "Chrome","data": [ ['v40.0',5],['v41.0',4.32],['v42.0',3.68],['v39.0',2.96],['v36.0',2.53],['v43.0',1.45],['v31.0',1.24],['v35.0',0.85],['v38.0',0.6],['v32.0',],['v37.0',0.38],['v33.0', 0.19],['v34.0', 0.14],['v30.0', 0.14]]},{'name': 'Firefox',id: 'Firefox',data: [['v35',2.76],['v36',2.32],['v37',2.31],['v34',1.27],['v38',1.02],['v31',0.33],['v33', 0.22],['v32', 0.15]]},{'name': 'Safari',id: 'Safari', data: [ ['v8.0', 2.56], ['v7.1',0.77],['v5.1',0.42],['v5.0',0.3], ['v6.1',0.29],['v7.0',0.26],['v6.2',0.17]]},{'name': 'Opera',id: 'Opera',data: [[],[],['v27',0.17],['v29',0.16]]},{'name': 'banner',id: 'banner',data: [ ['v12.x',0.34],['v28',0.24],['v27',0.17],['v29',0.16]]},{'name': 'coffee',id: 'coffee',data: [['v12.x',0.34],['v28',0.24],['v27',0.17],['v29',0.16]]}];
	    		    //dynamicSeries = [{"name":"Microsoft Internet Explorer","id":"Microsoft Internet Explorer","data":[['v11.0',24.13],['v8.0',17.2],['v9.0',8.11],['v10.0',5.33],['v6.0',1.06],['v7.0',0.5]]},{"name":"Chrome","id":"Chrome","data":"[['v40.0',5],['v41.0',4.32],['v42.0',3.68],['v39.0',2.96],['v36.0',2.53],['v43.0',1.45],['v31.0',1.24],['v35.0',0.85],['v38.0',0.6],['v32.0',0.55],['v37.0',0.38],['v33.0',0.19],['v34.0',0.14],['v30.0',0.14]]"},{"name":"Firefox","id":"Firefox","data":"[['v35',2.76],['v36',2.32],['v37',2.31],['v34',1.27],['v38',1.02],['v31',0.33],['v33',0.22],['v32',0.15]]"},{"name":"Safari","id":"Safari","data":"[['v8.0',2.56],['v7.1',0.77],['v5.1',0.42],['v5.0',0.3],['v6.1',0.29],['v7.0',0.26],['v6.2',0.17]]"},{"name":"Opera","id":"Opera","data":"[[],[],['v27',0.17],['v29',0.16],]"},{"name":"banner","id":"banner","data":"[['v12.x',0.34],['v28',0.24],['v27',0.17],['v29',0.16],]"},{"name":"coffee","id":"coffee","data":"[['v12.x',0.34],,['v28',0.24],['v27',0.17],['v29',0.16],]"}]
	    		  //dynamicSeries = [{"name":"Microsoft Internet Explorer","id":"Microsoft Internet Explorer","data":"[['v11.0',24.13]['v8.0',17.2]['v9.0',8.11]['v10.0',5.33]['v6.0',1.06]['v7.0',0.5]]"},{"name":"Chrome","id":"Chrome","data":"[['v40.0',5]['v41.0',4.32]['v42.0',3.68]['v39.0',2.96]['v36.0',2.53]['v43.0',1.45]['v31.0',1.24]['v35.0',0.85]['v38.0',0.6]['v32.0',]['v37.0',0.38]['v33.0', 0.19]['v34.0', 0.14]['v30.0', 0.14]]"},{"name":"Firefox","id":"Firefox","data":"[['v35',2.76]['v36',2.32]['v37',2.31]['v34',1.27]['v38',1.02]['v31',0.33]['v33', 0.22]['v32', 0.15]]"},{"name":"Safari","id":"Safari","data":"[['v8.0', 2.56]['v7.1',0.77]['v5.1',0.42]['v5.0',0.3]['v6.1',0.29]['v7.0',0.26]['v6.2',0.17]]"},{"name":"Opera","id":"Opera","data":"[[][]['v27',0.17]['v29',0.16]]"},{"name":"banner","id":"banner","data":"[['v12.x',0.34]['v28',0.24]['v27',0.17]['v29',0.16]]"}];
	    						  //[{"name":"辣鱼仔","id":"辣鱼仔","data":[["1970-01-01",2],["2017-07-20",4],["2017-07-21",2],["2019-04-08",1]]}]
	    		    //dynamicSeries = [{"name":"Microsoft Internet Explorer","id":"Microsoft Internet Explorer","data":"[['v11.0',24.13],['v8.0',17.2],['v9.0',8.11],['v10.0',5.33],['v6.0',1.06],['v7.0',0.5]]"},{"name":"Chrome","id":"Chrome","data":"[['v40.0',5],['v41.0',4.32],['v42.0',3.68],['v39.0',2.96],['v36.0',2.53],['v43.0',1.45],['v31.0',1.24],['v35.0',0.85],['v38.0',0.6],['v32.0',0.55],['v37.0',0.38],['v33.0',0.19],['v34.0',0.14],['v30.0',0.14]]"},{"name":"Firefox","id":"Firefox","data":"[['v35',2.76],['v36',2.32],['v37',2.31],['v34',1.27],['v38',1.02],['v31',0.33],['v33',0.22],['v32',0.15]]"},{"name":"Safari","id":"Safari","data":"[['v8.0',2.56],['v7.1',0.77],['v5.1',0.42],['v5.0',0.3],['v6.1',0.29],['v7.0',0.26],['v6.2',0.17]]"},{"name":"Opera","id":"Opera","data":"[[],[],['v27',0.17],['v29',0.16],]"},{"name":"banner","id":"banner","data":"[['v12.x',0.34],['v28',0.24],['v27',0.17],['v29',0.16],]"},{"name":"coffee","id":"coffee","data":"[['v12.x',0.34],,['v28',0.24],['v27',0.17],['v29',0.16]]}];
	    			//console.log("==444= dynamicSeries=>"+dynamicSeries);
	    			//$.jBox.info("开发中...dynamicData.."+dynamicData);
	    	    	 Highcharts.chart('container', {
	    	    	        chart: {
	    	    	            type: 'column'
	    	    	        },
	    	    	        title: {
	    	    	            text: '2015年1月-5月，各浏览器的市场份额'
	    	    	        },
	    	    	        subtitle: {
	    	    	            text: '点击可查看具体的版本数据，数据来源: <a href="https://netmarketshare.com">netmarketshare.com</a>.'
	    	    	        },
	    	    	        xAxis: {
	    	    	            type: 'category'
	    	    	        },
	    	    	        yAxis: {
	    	    	            title: {
	    	    	                text: '总的市场份额'
	    	    	            }
	    	    	        },
	    	    	        legend: {
	    	    	            enabled: false
	    	    	        },
	    	    	        plotOptions: {
	    	    	            series: {
	    	    	                borderWidth: 0,
	    	    	                dataLabels: {
	    	    	                    enabled: true,
	    	    	                    format: '{point.y:.1f}%'
	    	    	                }
	    	    	            }
	    	    	        },
	    	    	        tooltip: {
	    	    	            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	    	    	            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
	    	    	        },
	    	    	        series: [{
	    	    	            name: '浏览商品品牌',
	    	    	            colorByPoint: true,
	    	    	            data:dynamicData
	    	    	        }],
	    	    	        drilldown: {
	    	    	        	series:dynamicSeries
	    	    	        } 
	    	    	    });
	    		}
	    	});

    	});
    /* $("#sumMark").click(function(){
    	 Highcharts.chart('container', {
    	        chart: {
    	            type: 'column'
    	        },
    	        title: {
    	            text: '2015年1月-5月，各浏览器的市场份额'
    	        },
    	        subtitle: {
    	            text: '点击可查看具体的版本数据，数据来源: <a href="https://netmarketshare.com">netmarketshare.com</a>.'
    	        },
    	        xAxis: {
    	            type: 'category'
    	        },
    	        yAxis: {
    	            title: {
    	                text: '总的市场份额'
    	            }
    	        },
    	        legend: {
    	            enabled: false
    	        },
    	        plotOptions: {
    	            series: {
    	                borderWidth: 0,
    	                dataLabels: {
    	                    enabled: true,
    	                    format: '{point.y:.1f}%'
    	                }
    	            }
    	        },
    	        tooltip: {
    	            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
    	            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
    	        },
    	        series: [{
    	            name: '浏览商品品牌',
    	            colorByPoint: true,
    	            data: [{
    	                name: 'Microsoft Internet Explorer',
    	                y: 56.33,
    	                drilldown: 'Microsoft Internet Explorer'
    	            }, {
    	                name: 'Chrome',
    	                y: 24.03,
    	                drilldown: 'Chrome'
    	            }, {
    	                name: 'Firefox',
    	                y: 10.38,
    	                drilldown: 'Firefox'
    	            }, {
    	                name: 'Safari',
    	                y: 4.77,
    	                drilldown: 'Safari'
    	            }, {
    	                name: 'Opera',
    	                y: 0.91,
    	                drilldown: 'Opera'
    	            }, {
    	                name: 'banner',
    	                y: 50.55,
    	                drilldown: 'banner'
    	            }, {
    	                name: 'coffee',
    	                y: 65,
    	                drilldown: 'coffee'
    	            }]
    	        }],
    	        drilldown: {
    	            series: [{
    	                name: 'Microsoft Internet Explorer',
    	                id: 'Microsoft Internet Explorer',
    	                data: [
    	                    [
    	                        'v11.0',
    	                        24.13
    	                    ],
    	                    [
    	                        'v8.0',
    	                        17.2
    	                    ],
    	                    [
    	                        'v9.0',
    	                        8.11
    	                    ],
    	                    [
    	                        'v10.0',
    	                        5.33
    	                    ],
    	                    [
    	                        'v6.0',
    	                        1.06
    	                    ],
    	                    [
    	                        'v7.0',
    	                        0.5
    	                    ]
    	                ]
    	            }, {
    	                name: 'Chrome',
    	                id: 'Chrome',
    	                data: [
    	                    [
    	                        'v40.0',
    	                        5
    	                    ],
    	                    [
    	                        'v41.0',
    	                        4.32
    	                    ],
    	                    [
    	                        'v42.0',
    	                        3.68
    	                    ],
    	                    [
    	                        'v39.0',
    	                        2.96
    	                    ],
    	                    [
    	                        'v36.0',
    	                        2.53
    	                    ],
    	                    [
    	                        'v43.0',
    	                        1.45
    	                    ],
    	                    [
    	                        'v31.0',
    	                        1.24
    	                    ],
    	                    [
    	                        'v35.0',
    	                        0.85
    	                    ],
    	                    [
    	                        'v38.0',
    	                        0.6
    	                    ],
    	                    [
    	                        'v32.0',
    	                        0.55
    	                    ],
    	                    [
    	                        'v37.0',
    	                        0.38
    	                    ],
    	                    [
    	                        'v33.0',
    	                        0.19
    	                    ],
    	                    [
    	                        'v34.0',
    	                        0.14
    	                    ],
    	                    [
    	                        'v30.0',
    	                        0.14
    	                    ]
    	                ]
    	            }, {
    	                name: 'Firefox',
    	                id: 'Firefox',
    	                data: [
    	                    [
    	                        'v35',
    	                        2.76
    	                    ],
    	                    [
    	                        'v36',
    	                        2.32
    	                    ],
    	                    [
    	                        'v37',
    	                        2.31
    	                    ],
    	                    [
    	                        'v34',
    	                        1.27
    	                    ],
    	                    [
    	                        'v38',
    	                        1.02
    	                    ],
    	                    [
    	                        'v31',
    	                        0.33
    	                    ],
    	                    [
    	                        'v33',
    	                        0.22
    	                    ],
    	                    [
    	                        'v32',
    	                        0.15
    	                    ]
    	                ]
    	            }, {
    	                name: 'Safari',
    	                id: 'Safari',
    	                data: [
    	                    [
    	                        'v8.0',
    	                        2.56
    	                    ],
    	                    [
    	                        'v7.1',
    	                        0.77
    	                    ],
    	                    [
    	                        'v5.1',
    	                        0.42
    	                    ],
    	                    [
    	                        'v5.0',
    	                        0.3
    	                    ],
    	                    [
    	                        'v6.1',
    	                        0.29
    	                    ],
    	                    [
    	                        'v7.0',
    	                        0.26
    	                    ],
    	                    [
    	                        'v6.2',
    	                        0.17
    	                    ]
    	                ]
    	            }, {
    	                name: 'Opera',
    	                id: 'Opera',
    	                data: [
    	                    [
    	                        'v12.x',
    	                        0.34
    	                    ],
    	                    [
    	                        'v28',
    	                        0.24
    	                    ],
    	                    [
    	                        'v27',
    	                        0.17
    	                    ],
    	                    [
    	                        'v29',
    	                        0.16
    	                    ]
    	                ]
    	            },{
    	                name: 'banner',
    	                id: 'banner',
    	                data: [
    	                    [
    	                        'v12.x',
    	                        0.34
    	                    ],
    	                    [
    	                        'v28',
    	                        0.24
    	                    ],
    	                    [
    	                        'v27',
    	                        0.17
    	                    ],
    	                    [
    	                        'v29',
    	                        0.16
    	                    ]
    	                ]
    	            }, {
    	                name: 'coffee',
    	                id: 'coffee',
    	                data: [
    	                    [
    	                        'v12.x',
    	                        0.34
    	                    ],
    	                    [
    	                        'v28',
    	                        0.24
    	                    ],
    	                    [
    	                        'v27',
    	                        0.17
    	                    ],
    	                    [
    	                        'v29',
    	                        0.16
    	                    ]
    	                ]
    	            }]
    	        }
    	    });
    	}); */
});

function dynamicDatas(saleMoney,saleCount,vendCount){
	$('#container').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '2017代理商销售统计'
        },
        tooltip: {
            headerFormat: '{series.name}<br>',
            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            type: 'pie',
            name: '浏览器访问量占比',
            data: [
                ['销售金额(元)',saleMoney],
                ['销售数量',saleCount],
                {
                    name: '机器数',
                    y: vendCount,
                    sliced: true,
                    selected: true
                },
                //['机器数',    8.5],
                //['Opera',     6.2],
                //['其他',   0.7]
            ]
        }]
    });
}

	
</script>
<style>
<!--
.td_style_1 {
	width: 40%;
	text-align: center;
}

.td_style_2 {
	width: 30%;
	text-align: center;
}
-->
</style>
<div id="container" style="min-width:400px;height:400px"></div>
