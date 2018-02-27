<#include "macro-head.ftl">
<!DOCTYPE html>
<html>
<head>
<@head title="${blogTitle}">
    <meta name="keywords" content="${metaKeywords},${archiveLabel}"/>
    <meta name="description" content="${metaDescription},${archiveLabel}"/>
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
        <div class="module__title">
            <span>
                ${links?size}
                    <span class="ft-green ft-12">${linkLabel}</span>
            </span>
        </div>
    <#if 0 != links?size>
        <#list links as link>
            <div class="page__item">
                <h3>
                    <a rel="friend" class="ft-gray" href="${link.linkAddress}" target="_blank">
                    ${link.linkTitle}
                        <span class="ft-12 ft-green">${link.linkDescription}</span>
                    </a>
                </h3>

            </div>
        </#list>
    </#if>
    </div>
<#include "bottom.ftl">
</div>
<#include "footer.ftl">
</body>
</html>
