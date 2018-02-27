<div class="footer__tag wrapper">
<#if 0 != mostUsedCategories?size>
    <#list mostUsedCategories as category>
        <a class="tag pipe-tooltipped pipe-tooltipped--n"
           aria-label="${category.categoryTagCnt} ${cntLabel}${tagsLabel}"
           href="${servePath}/category/${category.categoryURI}">${category.categoryTitle}</a>
    </#list>
</#if>
<#if 0 != mostUsedTags?size>
    <#list mostUsedTags as tag>
        <a rel="tag"
           class="tag pipe-tooltipped pipe-tooltipped--n"
           aria-label="${tag.tagPublishedRefCount} ${countLabel}${articleLabel}"
           href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}">${tag.tagTitle}</a>
    </#list>
</#if>
</div>