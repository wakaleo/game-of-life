<#include "macro-head.ftl">
<#include "macro-comments.ftl">
<!DOCTYPE html>
<html>
<head>
<@head title="${page.pageTitle} - ${blogTitle}">
    <meta name="keywords" content="${metaKeywords},${page.pageTitle}"/>
    <meta name="description" content="${metaDescription}"/>
</@head>
</head>
<body>
<#include "header.ftl">
<#include "nav.ftl">
<div class="main">
<#if noticeBoard??>
    <div class="board">
    ${noticeBoard}
    </div>
</#if>
    <div class="wrapper content">
        <article class="post">
            <section class="content-reset">
            ${page.pageContent}
            </section>

        </article>
    </div>
</div>
<div class="article__bottom">
<@comments commentList=pageComments article=page></@comments>
</div>
<div style="margin: 0 20px">
<#include "bottom.ftl">
<#include "footer.ftl">
</div>
<@comment_script oId=page.oId></@comment_script>
</body>
</html>
