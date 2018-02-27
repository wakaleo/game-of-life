<script type="text/javascript">
    (function () {
        var $groups =  $("a[rel=group]"),
        $fancybox = $(".fancybox");
        if ($groups.length > 0 || $fancybox.length > 0) {
            if (document.createStyleSheet) {
                document.createStyleSheet("${staticServePath}/plugins/fancybox/js/jquery.fancybox.css");
            } else {
                $("head").append($("<link rel='stylesheet' href='${staticServePath}/plugins/fancybox/js/jquery.fancybox.css' type='text/css' charset='utf-8' />"));
            } 
            
            $.ajax({
                url: "${staticServePath}/plugins/fancybox/js/jquery.fancybox.pack.js",
                dataType: "script",
                cache: true,
                complete: function() {
                    $groups.fancybox({
                        openEffect  : 'none',
                        closeEffect : 'none',
                        prevEffect : 'none',
                        nextEffect : 'none'
                    });
                    $fancybox.fancybox({
                        openEffect  : 'none',
                        closeEffect : 'none',
                        prevEffect : 'none',
                        nextEffect : 'none'
                    });
                }
            });
        }
    })();
</script>
