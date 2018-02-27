<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>${welcomeToSoloLabel} Solo!</title>
        <meta name="keywords" content="Solo,Java 博客,开源" />
        <meta name="description" content="An open source blog with Java. Java 开源博客" />
        <meta name="owner" content="B3log Team" />
        <meta name="author" content="B3log Team" />
        <meta name="generator" content="Solo" />
        <meta name="copyright" content="B3log" />
        <meta name="revised" content="B3log, ${year}" />
        <meta name="robots" content="noindex, follow" />
        <meta http-equiv="Window-target" content="_top" />
        <link type="text/css" rel="stylesheet" href="${staticServePath}/css/default-init${miniPostfix}.css?${staticResourceVersion}" charset="utf-8" />
        <link rel="icon" type="image/png" href="${staticServePath}/favicon.png" />
        <style>
            *,html,body {
                margin: 0;
                padding: 0;
            }

            html {
                height: 100%;
                overflow: hidden;
            }

            body {
                background-color: #F3F1E5;
                color: #4D505D;
                font-family: \5fae\8f6f\96c5\9ed1;
                font-size: small;
                height: 100%;
                 overflow: hidden;
            }

            .wrapper {
                height: 400px;
                min-height: 100%;
                position: relative;
            }

            .contentError {
                background-color: #FFFFFF;
                border: 1px solid #E6E5D9;
                height: 300px;
                margin: 0 auto;
                padding: 50px;
                position: relative;
                top: 60px;
                width: 600px;
            }

            .footerWrapper {
                background-color: #FFFFFF;
                border-top: 1px solid #E6E5D9;
                bottom: 0;
                padding: 12px 0;
                position: absolute;
                text-align: center;
                width: 100%;
            }

            .footerWrapper a {
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <div class="wrap">
                <div class="content" id="main">
                    <div class="logo">
                        <a href="http://b3log.org" target="_blank">
                            <img border="0" width="153" height="56" alt="B3log" title="B3log" src="${staticServePath}/images/logo.jpg"/>
                        </a>
                    </div>
                    <div class="main">
                        <h2>
                            <span>${welcomeToSoloLabel}</span>
                            <a target="_blank" href="http://b3log.org">
                                <span class="solo">&nbsp;Solo</span>
                            </a>
                        </h2>
                        <div id="init">
                            <div id="user" class="form">
                                <label for="userEmail">
                                    ${commentEmail1Label}
                                </label>
                                <input id="userEmail" />
                                <label for="userName">
                                    ${userName1Label}
                                </label>
                                <input id="userName" />
                                <label for="userPassword">
                                    ${userPassword1Label}
                                </label>
                                <input type="password" id="userPassword" />
                                <label for="userPasswordConfirm">
                                    ${userPasswordConfirm1Label}
                                </label>
                                <input type="password" id="userPasswordConfirm" />
                                <button onclick='getUserInfo();'>${nextStepLabel}</button>
                                <span id="tip"></span>
                            </div>
                            <div id="sys" class="none">
                                ${initIntroLabel}
                                <button onclick='initSys();' id="initButton">${initLabel}</button>
                                <button onclick='returnTo();'>${previousStepLabel}</button>
                                <span id="tipInit"></span>
                                <span class="clear"></span>
                            </div>
                        </div>
                        <a href="http://b3log.org" target="_blank">
                            <img border="0" class="icon" alt="B3log" title="B3log" src="${staticServePath}/favicon.png"/>
                        </a>
                    </div>
                    <span class="clear"></span>
                </div>
            </div>

            <div class="footerWrapper">
                <div class="footer">
                    &copy; ${year}
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
                                        } else if ($("#userPassword").val().replace(/\s/g, "") === "") {
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
                                            $("#init").animate({
                                                "top": -178
                                            });

                                            $("#user").animate({
                                                "opacity": 0
                                            });

                                            $("#sys").css({
                                                "display": "block",
                                                "opacity": 1
                                            });

                                            $(window).unbind().keydown(function(e) {
                                                if (e.keyCode === 27) {// esc
                                                    returnTo();
                                                    $(window).unbind();
                                                } else if (e.keyCode === 13) {// enter
                                                    initSys();
                                                }
                                            });
                                        }
                                    };

                                    var returnTo = function() {
                                        $("#init").animate({
                                            "top": 81
                                        });

                                        $("#user").animate({
                                            "opacity": 1
                                        });

                                        $("#sys").animate({
                                            "opacity": 0
                                        }, 800, function() {
                                            this.style.display = "none";
                                        });
                                    };

                                    var initSys = function() {
                                        var requestJSONObject = {
                                            "userName": $("#userName").val(),
                                            "userEmail": $("#userEmail").val(),
                                            "userPassword": $("#userPassword").val()
                                        };

                                        if (confirm("${confirmInitLabel}")) {
                                            $(window).unbind();
                                            $("#tipInit").html("<img src='${staticServePath}/images/loading.gif'/> loading...");
                                            
                                            $.ajax({
                                                url: "${servePath}/init",
                                                type: "POST",
                                                data: JSON.stringify(requestJSONObject),
                                                success: function(result, textStatus) {
                                                    if (!result.sc) {
                                                        $("#tipInit").text(result.msg);
                                                        return;
                                                    }

                                                    window.location.href = "${servePath}/admin-index.do#main";
                                                }
                                            });
                                        }
                                    };

                                    (function() {
                                        try {
                                            $("#userEmail").focus();
                                            $("input").keypress(function(event) {
                                                if (event.keyCode === 13) {
                                                    event.preventDefault();
                                                }
                                            });

                                            $("#userPasswordConfirm").keypress(function(event) {
                                                if (event.keyCode === 13) {
                                                    getUserInfo();
                                                }
                                            });
                                        } catch (e) {
                                            document.getElementById("main").innerHTML = "${staticErrorLabel}";
                                            document.getElementById("main").className = "contentError";
                                        }

                                        // if no JSON, add it.
                                        try {
                                            JSON
                                        } catch (e) {
                                            document.write("<script src=\"${staticServePath}/js/lib/json2.js\"><\/script>");
                                        }
                                    })();

        </script>
    </body>
</html>
