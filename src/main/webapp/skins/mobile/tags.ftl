<#include "macro-head.ftl">
<!DOCTYPE html>
<html>
    <head>
        <@head title="${allTagsLabel} - ${blogTitle}">
        <meta name="keywords" content="${metaKeywords},${allTagsLabel}"/>
        <meta name="description" content="<#list tags as tag>${tag.tagTitle}<#if tag_has_next>,</#if></#list>"/>
        </@head>
    </head>
    <body class="classic-wptouch-bg ">
        ${topBarReplacement}
        <#include "header.ftl">
		<div class="content single">
            <div class="post">
                <ul id="tags">
                    <#list tags as tag>
                    <span>
                        <a rel="tag" data-count="${tag.tagPublishedRefCount}"
                           href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}" title="${tag.tagTitle}">
                            <span>${tag.tagTitle}</span>
                            (<b>${tag.tagPublishedRefCount}</b>)
                        </a>
                    </span>&nbsp;&nbsp;
                    </#list>
                </ul>
        	</div>
        </div>
        <#include "footer.ftl">
    </body>
</html>
