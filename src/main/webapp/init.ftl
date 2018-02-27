<#include "macro-common-page.ftl">

<@commonPage "${welcomeToSoloLabel}!">
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
        <input id="userEmail"/>
        <label for="userName">
        ${userName1Label}
        </label>
        <input id="userName"/>
        <label for="userPassword">
        ${userPassword1Label}
        </label>
        <input type="password" id="userPassword"/>
        <label for="userPasswordConfirm">
        ${userPasswordConfirm1Label}
        </label>
        <input type="password" id="userPasswordConfirm"/>
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
<script type="text/javascript" src="${staticServePath}/js/lib/jquery/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript">
    var validate = function () {
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

    var getUserInfo = function () {
        if (validate()) {
            $("#init").animate({
                "top": -$('#user').height() + ($('.main h2').offset().top + 10)
            });

            $("#user").animate({
                "opacity": 0
            });

            $("#sys").css({
                "display": "block",
                "opacity": 1
            });

            $(window).unbind().keydown(function (e) {
                if (e.keyCode === 27) {// esc
                    returnTo();
                    $(window).unbind();
                } else if (e.keyCode === 13) {// enter
                    initSys();
                }
            });
        }
    };

    var returnTo = function () {
        $("#init").animate({
            "top": $('.main h2').offset().top + 42
        });

        $("#user").animate({
            "opacity": 1
        });

        $("#sys").animate({
            "opacity": 0
        }, 800, function () {
            this.style.display = "none";
        });
    };

    var initSys = function () {
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
                success: function (result, textStatus) {
                    if (!result.sc) {
                        $("#tipInit").text(result.msg);
                        return;
                    }

                    window.location.href = "${servePath}/admin-index.do#main";
                }
            });
        }
    };

    (function () {
        try {
            $("#userEmail").focus();
            $("input").keypress(function (event) {
                if (event.keyCode === 13) {
                    event.preventDefault();
                }
            });

            $("#userPasswordConfirm").keypress(function (event) {
                if (event.keyCode === 13) {
                    getUserInfo();
                }
            });
        } catch (e) {
            document.querySelector('.main').innerHTML = "${staticErrorLabel}<br><br><br><br><br>";
        }

        // if no JSON, add it.
        try {
            JSON
        } catch (e) {
            document.write("<script src=\"${staticServePath}/js/lib/json2.js\"><\/script>");
        }

        $('.main').css({
            height: '350px',
            'overflow': 'hidden'
        })
    })();

</script>
</@commonPage>