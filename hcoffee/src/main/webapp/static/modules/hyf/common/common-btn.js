	$(document).ready(function() {
		var maxCount = 5000;
		var exportUrl = $("#btnExport").attr("url");
		if(exportUrl){
			$("#btnExport").attr("title","注意：最多导出数据"+maxCount+"条");
	        $("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？【请耐心等待1-5分钟，期间不要重复点击导出按钮。】 ","系统提示(最多导出数据"+maxCount+"条)",function(v,h,f){
					if(v=="ok"){
						$("#btnExport").attr("disabled","disabled");
						$("#pageNo").val(1);
						$("#pageSize").val(maxCount);
						$("#searchForm").attr("action",exportUrl);
						$("#searchForm").submit();
						$.blockUI({message : "<h1>正在导出,请稍后.....!</h1>",css : {textAlign:'center',top : '50%' , left : '50%' , marginLeft : '-200px' , width : '400px' ,color:'white',border:'3px solid #aaa', backgroundColor:'#08c'}});
						var time = 1000;
						if($("#rowcount")){
							var rowcount = $("#rowcount").text();
							if(rowcount > maxCount){
								rowcount = maxCount;
							}
							var mod = rowcount/1000;
							time  = mod * time;
							if(time > 10000){
								time = 10000;
							}
						}
			            window.setTimeout(function () {$.unblockUI();}, time); 
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		}
		var sumbitUrl = $("#btnSubmit").attr("url");
		if(sumbitUrl){
			$("#btnSubmit").click(function(){
				$("#pageNo").val(1);
				$("#pageSize").val(15);
				$("#searchForm").attr("action",sumbitUrl);
				$("#searchForm").submit();
			});
		}
		if($("#btnClean")){
			$("#btnClean").click(function(){
				$("#tab").find("input[type=text]").val("");
				$("#orderStatus").get(0).selectedIndex = 0;
				$("#orderStatusType").get(0).selectedIndex = 0;
				$("#posType").get(0).selectedIndex = 0;
				$(".select2-chosen").html("全部");
				$("#pageNo").val(1);
			});		
		}
	});

