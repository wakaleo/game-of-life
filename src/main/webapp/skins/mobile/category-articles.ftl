<#include "macro-head.ftl">
<!DOCTYPE html>
<html>
    <head>
        <@head title="${category.categoryTitle} - ${blogTitle}">
        <meta name="keywords" content="${metaKeywords},${category.categoryTitle}"/>
        <meta name="description" content="<#list articles as article>${article.articleTitle}<#if article_has_next>,</#if></#list>"/>
        </@head>
    </head>
    <body class="classic-wptouch-bg ">
        <#include "header.ftl">
		<div class="content single">
            <div class="post">
                <h2 >${categoryLabel}
                    <a rel="alternate" href="${servePath}/category/${category.categoryURI}">
                        ${category.categoryTitle}
                    (${category.categoryTagCnt})</a>
                </h2>
        	</div>
        </div>
        <#include "article-list.ftl">
        <#include "footer.ftl">
    </body>
</html>
