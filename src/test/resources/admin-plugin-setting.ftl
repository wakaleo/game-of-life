

<textarea id="jsoneditor" rows="10" cols="110">
	${setting}
</textarea>

<input type="hidden" id="pluginId" value="${oId}">
<button class="marginRight12" id="updateSetting" onclick="updateSetting()">save</button>

<script type="text/javascript">
	
	
	function updateSetting(){
	
		var pluginId = $("#pluginId").val();
	 	var json = $("#jsoneditor").val();
	 	alert(json);
		
		$("#loadMsg").text(Label.loadingLabel);
          var requestJSONObject = {
            "oId": pluginId,
            "setting":json
        };
        
        $.ajax({
            url: latkeConfig.servePath + "/console/plugin/updateSetting",
            type: "POST",
            cache: false,
            data: JSON.stringify(requestJSONObject),
            success: function(result, textStatus){
                $("#tipMsg").text(result.msg);
            }
        });
	}
</script>
