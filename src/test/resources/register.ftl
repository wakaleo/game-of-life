<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>${blogTitle}</title>
        <meta name="keywords" content="Solo,Java 博客,开源" />
        <meta name="description" content="An open source blog with Java. Java 开源博客" />
        <meta name="owner" content="B3log Team" />
        <meta name="author" content="B3log Team" />
        <meta name="generator" content="Solo" />
        <meta name="copyright" content="B3log" />
        <meta name="revised" content="B3log, ${year}" />
        <meta http-equiv="Window-target" content="_top" />
        <link type="text/css" rel="stylesheet" href="${staticServePath}/css/default-init${miniPostfix}.css?${staticResourceVersion}" charset="utf-8" />
        <link rel="icon" type="image/png" href="${staticServePath}/favicon.png" />
    </head>
    <body>
        <div class="wrapper">
            <div class="wrap">
                <div class="content">
                    <div class="logo">
                        <a href="http://b3log.org" target="_blank">
                            <img border="0" width="153" height="56" alt="B3log" title="B3log" src="${staticServePath}/images/logo.jpg"/>
                        </a>
                    </div>
                    <div class="main register">
                        <h2>${registerSoloUserLabel}</h2>
                        <div class="form">
                            <label for="userEmail">
                                ${commentEmail1Label}
                            </label>
                            <input id="userEmail" />
                            <label for="userName">
                                ${userName1Label}
                            </label>
                            <input id="userName" />
                            <label for="userURL">
                                ${userURL1Label}
                            </label>
                            <input id="userURL" />
                            <label for="userPassword">
                                ${userPassword1Label}
                            </label>
                            <input type="password" id="userPassword" />
                            <label for="userPasswordConfirm">
                                ${userPasswordConfirm1Label}
                            </label>
                            <input type="password" id="userPasswordConfirm" />
                            <button onclick='getUserInfo();'>${saveLabel}</button>
                            <span id="tip" ></span>
                        </div>
                    </div>
                    <span class="clear"></span>
                </div>
            </div>

            <div class="footerWrapper">
                <div class="footer">
                    &copy; ${year} - <a href="${servePath}">${blogTitle}</a><br/>
                    Powered by <a href="http://b3log.org" target="_blank">B3log 开源</a> • <a href="http://b3log.org/services/#solo" target="_blank">Solo</a> ${version}
                </div>
            </div>
        </div>
        <script type="text/javascript" src="${staticServePath}/js/lib/jquery/jquery.min.js" charset="utf-8"></script>
        <script type="text/javascript">
                                var validate = function() {
                                    var userName = $("#userName").val().replace(/(^\s*)|(\s*$)/g, "");
                                    if (!/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test($("#userEmail").val())) {
                                        $("#tip").text("${mailInvalidLabel}");
                                        $("#userEmail").focus();
                                    } else if (2 > userName.length || userName.length > 20) {
                                        $("#tip").text("${nameTooLongLabel}");
                                        $("#userName").focus();
                                    } else if ($("#userPassword").val() === "") {
                                        $("#tip").text("${passwordEmptyLabel}");
                                        $("#userPassword").focus();
                                    } else if ($("#userPassword").val() !== $("#userPasswordConfirm").val()) {
                                        $("#tip").text("${passwordNotMatchLabel}");
                                        $("#userPasswordConfirm").focus();
                                    } else {
                                        $("#tip").text("");
                                        return true;
                                    }
                                    return false;
                                };

                                var getUserInfo = function() {
                                    if (validate()) {
                                        var requestJSONObject = {
                                            "userName": $("#userName").val(),
                                            "userEmail": $("#userEmail").val(),
                                            "userURL": $("#userURL").val(),
                                            "userPassword": $("#userPassword").val()
                                        };

                                        $("#tip").html("<img src='${staticServePath}/images/loading.gif'/> loading...")
                                        $.ajax({
                                            url: "${servePath}" + "/console/user/",
                                            type: "POST",
                                            cache: false,
                                            data: JSON.stringify(requestJSONObject),
                                            success: function(result, textStatus) {
                                                $("#tip").text(result.msg);
                                                if (!result.sc) {
                                                    return;
                                                }
                                                setTimeout(function() {
                                                    window.location.href = "${servePath}";
                                                }, 1000);
                                            }
                                        })
                                    }
                                }

                                $(function() {
                                    $("#userPasswordConfirm").keypress(function(event) {
                                        if (event.keyCode === 13) {
                                            getUserInfo();
                                        }
                                    });
                                });

        </script>
    </body>
</html>
