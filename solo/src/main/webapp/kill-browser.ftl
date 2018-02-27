<#include "macro-common-page.ftl">

<@commonPage "403 Forbidden!">
${killBrowserLabel}
<br/>
&nbsp; &nbsp;&nbsp; <button onclick="closeIframe();">${closeLabel}</button> &nbsp; &nbsp;
<button onclick="closeIframeForever();">${closeForeverLabel}</button>
<img src='${staticServePath}/images/kill-browser.png' title='Kill IE6' style="float: right;
    margin: -171px 0 0 0;" alt='Kill IE6'/>
<script>
    var closeIframe = function () {
        window.parent.$("iframe").prev().remove();
        window.parent.$("iframe").remove();
    };

    var closeIframeForever = function () {
        window.parent.Cookie.createCookie("showKill", true, 365);
        closeIframe();
    };
</script>
</@commonPage>