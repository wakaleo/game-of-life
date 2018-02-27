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
                ${archiveDates?size}
                <span class="ft-green ft-12">${cntMonthLabel}</span>
                ${statistic.statisticPublishedBlogArticleCount}
                <span class="ft-green ft-12">${cntArticleLabel}</span>
            </span>
        </div>
    <#if 0 != archiveDates?size>
        <#list archiveDates as archiveDate>
            <div class="page__item">
                <h3>
                    <a class="ft-gray"
                       href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}">
                        <#if "en" == localeString?substring(0, 2)>
                        ${archiveDate.monthName} ${archiveDate.archiveDateYear}
                        <#else>
                        ${archiveDate.archiveDateYear} ${yearLabel} ${archiveDate.archiveDateMonth} ${monthLabel}
                        </#if>
                        <span class="ft-green">
                            ${archiveDate.archiveDatePublishedArticleCount}
                            <span class="ft-12">${cntArticleLabel}</span>
                        </span>
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
