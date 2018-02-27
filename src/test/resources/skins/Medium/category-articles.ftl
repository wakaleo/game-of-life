<#include "macro-head.ftl">
<!DOCTYPE html>
<html>
<head>
<@head title="${category.categoryTitle} - ${blogTitle}">
    <meta name="keywords" content="${metaKeywords},${category.categoryTitle}"/>
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
                ${category.categoryTitle}
                <span class="ft-green ft-12">
                    ${category.categoryDescription}
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
