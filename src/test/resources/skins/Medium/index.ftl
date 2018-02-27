<#include "macro-head.ftl">
<!DOCTYPE html>
<html>
<head>
<@head title="${blogTitle}">
    <#if metaKeywords??>
        <meta name="keywords" content="${metaKeywords}"/>
    </#if>
    <#if metaDescription??>
        <meta name="description" content="${metaDescription}"/>
    </#if>
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
            <#include "article-list.ftl">
    </div>
    <#include "bottom2.ftl">
</div>
<#include "footer.ftl">
</body>
</html>