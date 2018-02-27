<#include "macro-head.ftl">
<!DOCTYPE html>
<html>
<head>
<@head title="${archiveDate.archiveDateMonth} ${archiveDate.archiveDateYear} (${archiveDate.archiveDatePublishedArticleCount}) - ${blogTitle}">
    <meta name="keywords" content="${metaKeywords},${archiveDate.archiveDateYear}${archiveDate.archiveDateMonth}"/>
    <meta name="description"
          content="<#list articles as article>${article.articleTitle}<#if article_has_next>,</#if></#list>"/>
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
                <#if "en" == localeString?substring(0, 2)>
                    ${archiveDate.archiveDateMonth} ${archiveDate.archiveDateYear}
                <#else>
                    ${archiveDate.archiveDateYear} ${yearLabel} ${archiveDate.archiveDateMonth} ${monthLabel}
                </#if>
                <span class="ft-green">
                    ${archiveDate.archiveDatePublishedArticleCount}
                    <span class="ft-12">${cntArticleLabel}</span>
                </span>
            </span>
        </div>
        <#include "article-list.ftl">
    </div>
<#include "bottom2.ftl">
</div>
<#include "footer.ftl">
</body>
</html>
