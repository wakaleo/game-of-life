<#include "macro-head.ftl">
<!DOCTYPE html>
<html>
    <head>
        <@head title="${authorName} - ${blogTitle}">
        <meta name="keywords" content="${metaKeywords},${authorName}"/>
        <meta name="description" content="<#list articles as article>${article.articleTitle}<#if article_has_next>,</#if></#list>"/>
        </@head>
    </head>
    <body class="classic-wptouch-bg ">
        <#include "header.ftl">
		<div class="content single">
            <div class="post">
                <h2 >
					${author1Label}${authorName}
                </h2>
        	</div>
        </div>
        <#include "article-list.ftl">
        <#include "footer.ftl">
    </body>
</html>
