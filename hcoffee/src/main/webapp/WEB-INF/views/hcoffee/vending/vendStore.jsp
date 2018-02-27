  <%@ page contentType="text/html;charset=UTF-8" %>
	<script  type="text/javascript">
	$(function(){
		
		$("#btnsave").bind("click",function(){
			   var data = {}; 
			   debugger; 
			   var vendCode= $("#vendCodes").val();
			   var id= $("#id").val();
			   var storeGoodsId= $("#storeGoodsId").val();
			   var shelf= $("#shelf").val();
			   var goodsName= $("#goodsNames").val();
			   var amount= $("#amount").val();
			   var goodsID= $("#goodsID").val();
			   if(goodsID=="" || goodsID ==null){
				     alert("请输贩卖机商品ID");
				     return;
			    }
			   if(vendCode=="" || vendCode ==null){
				     alert("请输机器编码");
				     return;
			    }
			   if(storeGoodsId=="" || storeGoodsId ==null){
				     alert("请输入免息id");
				     return;
			    }
			   if(goodsName=="" || goodsName ==null){
				     alert("请输入商品名称");
				     return;
			    }
			   
			   if(shelf==" " ||shelf=="" || shelf==null){
				     alert("请输入货道信息");
				     return;
			    }
			   loading('正在提交，请稍等...');
			   data.id = $("#id").val();
			   data.vendCode = $("#vendCodes").val();
			   data.shelf = $("#shelf").val();
			   data.goodsName = $("#goodsNames").val();
			   data.amount = $("#amount").val();
			   data.storeGoodsId = $("#storeGoodsId").val();
			   data.storeGoodsLink = $("#storeGoodsLink").val();
			   data.goodsID = $("#goodsID").val();
			   var  url ='${ctx}/hcf/vendStore/save';
			    if(data.id!=''){
			    	   url ='${ctx}/hcf/vendStore/update';
			    }
	             $.ajax({
	            	 type:'post',
			       	 url:url,
			       	 data:JSON.stringify(data),
			       	 dataType:"json",
			       	 contentType:"application/json",
			       	 success:function(data){
			       		alert(data.msg);
			       		closeLoading()
			       		if(data.code=="0"){
			       			window.location.href="${ctx}/hcf/vendStore/list";
			       		}
			       	 	

			       	 }
				});  
			});
			   /*  data.version = version;
			   data.description = description;
			   data.status = $("#status").val(); 
			    var  url ='${ctx}/hcf/vendingVersion/save';
			    if(data.id!=''){
			    	   url ='${ctx}/hcf/vendingVersion/update';
			    }
				$.ajax({
			       	 type:'post',
			       	 url:url,
			       	 data:JSON.stringify(data),
			       	 dataType:"json",
			       	 contentType:"application/json",
			       	 success:function(data){
			       		alert(data.msg);
			       		if(data.code=="0"){
			       			window.location.href="${ctx}/hcf/vendingVersion/list";
			       		}
			       	 	

			       	 }
				});  
			  
		}); */
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

	</script>
	<form:form id="communityForm" modelAttribute="communityVo"
		action="#" method="post"
		class="breadcrumb form-search">
		 <input id="id" name="id" type="hidden" value=""/>
		 <input id="vendCode" name="vendCode" type="hidden" value=""/>

		<div class="form-horizontal">
			<ul class="nav nav-tabs">
				<li>售货机商城管理</li>
			</ul>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">机器编码：</label>
						<div class="controls">
							<input type="text" name="vendCode" id="vendCodes" />
						</div> 
					</div>
				</div>
			</div>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">商城商品Id：</label>
						<div class="controls">
							<input type="text" name="storeGoodsId" id="storeGoodsId" />
						</div> 
					</div>
				</div>
			</div>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">贩卖机商品ID：</label>
						<div class="controls">
							<input type="text" name="goodsID" id="goodsID" />
						</div> 
					</div>
				</div>
			</div>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">贩卖机货道：</label>
						<div class="controls">
							<input type="text" name="shelf" id="shelf" />
						</div> 
					</div>
				</div>
			</div>
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">商品名称：</label>
						<div class="controls">
							<input type="text" name="goodsName" id="goodsNames" />
						</div> 
					</div>
				</div>
			</div>
			
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">金额：</label>
						<div class="controls">
							<input type="text" name="amount" id="amount" />
						</div> 
					</div>
				</div>
			</div>
		
			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<label class="control-label">商品链接：</label>
						<div class="controls">
							 <textarea rows="3"  name="storeGoodsLink"  id="storeGoodsLink"></textarea>

						</div>
					</div>
				</div>
			</div>

			<div class="pure-g">
				<div class="pure-u-2-3">
					<div class="control-group">
						<div class="controls">
							<input id="btnsave" name="btnsave" class="btn" type="button" value="保存"   />
						</div>
					</div>
				</div>
			</div>

		</div>
	</form:form>
 