<#include "macro-head.ftl">
<!DOCTYPE html>
<html>
    <head>
        <@head title="${archiveDate.archiveDateMonth} ${archiveDate.archiveDateYear} (${archiveDate.archiveDatePublishedArticleCount}) - ${blogTitle}">
        <meta name="keywords" content="${metaKeywords},${archiveDate.archiveDateYear}${archiveDate.archiveDateMonth}"/>
        <meta name="description" content="<#list articles as article>${article.articleTitle}<#if article_has_next>,</#if></#list>"/>
        </@head>
    </head>
    <body class="classic-wptouch-bg ">
      	<#include "header.ftl">
		<div class="content single">
            <div class="post">
                <h2 class="marginLeft12 marginBottom12">${archive1Label}
                    <#if "en" == localeString?substring(0, 2)>
                    ${archiveDate.archiveDateMonth} ${archiveDate.archiveDateYear} (${archiveDate.archiveDatePublishedArticleCount})
                    <#else>
                    ${archiveDate.archiveDateYear} ${yearLabel} ${archiveDate.archiveDateMonth} ${monthLabel} (${archiveDate.archiveDatePublishedArticleCount})
                    </#if>
                </h2>
        	</div>
        </div>
        <#include "article-list.ftl">
     	<#include "footer.ftl">
    </body>
</html>
