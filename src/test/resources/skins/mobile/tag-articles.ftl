<#include "macro-head.ftl">
<!DOCTYPE html>
<html>
    <head>
        <@head title="${tag.tagTitle} - ${blogTitle}">
        <meta name="keywords" content="${metaKeywords},${tag.tagTitle}"/>
        <meta name="description" content="<#list articles as article>${article.articleTitle}<#if article_has_next>,</#if></#list>"/>
        </@head>
    </head>
    <body class="classic-wptouch-bg ">
        <#include "header.ftl">
		<div class="content single">
            <div class="post">
                <h2 >${tag1Label}
                    <a rel="alternate" href="${servePath}/tag-articles-feed.do?oId=${tag.oId}"><span id="tagArticlesTag">
                        ${tag.tagTitle}
                    </span>(${tag.tagPublishedRefCount})</a>
                </h2>
        	</div>
        </div>
        <#include "article-list.ftl">
        <#include "footer.ftl">
    </body>
</html>
