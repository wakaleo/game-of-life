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
                <div class="content" style="top:-6px">
                    <div class="logo">
                        <a href="http://b3log.org" target="_blank">
                            <img border="0" width="153" height="56" alt="B3log" title="B3log" src="${staticServePath}/images/logo.jpg"/>
                        </a>
                    </div>
                    <div class="main kill" style="height: 385px;">
                        ${killBrowserLabel}
                        <br/>
                         &nbsp; &nbsp;&nbsp; <button onclick="closeIframe();">${closeLabel}</button> &nbsp; &nbsp; 
                        <button onclick="closeIframeForever();">${closeForeverLabel}</button>
                        <img src='${staticServePath}/images/kill-browser.png' title='Kill IE6' alt='Kill IE6'/>
                        <a href="http://b3log.org" target="_blank">
                            <img border="0" class="icon" alt="B3log" title="B3log" src="${staticServePath}/favicon.png"/>
                        </a>
                    </div>
                    <span class="clear"></span>
                </div>
            </div>
        </div>
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
    </body>
</html>
