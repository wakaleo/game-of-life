<div class="footer__tag wrapper fn-flex">
<#if 0 != mostUsedCategories?size>
    <div class="fn-flex-1">
        <div class="module__title">
            <span>${categoryLabel}</span>
        </div>
        <div>
            <#list mostUsedCategories as category>
                <a class="tag pipe-tooltipped pipe-tooltipped--n"
                   aria-label="${category.categoryTagCnt} ${cntLabel}${tagsLabel}"
                   href="${servePath}/category/${category.categoryURI}">${category.categoryTitle}</a>
            </#list>
        </div>
    </div>
</#if>
<#if 0 != mostUsedTags?size>
    <div class="fn-flex-1">
        <div class="module__title">
            <span>${tagsLabel}</span>
        </div>
        <div>
            <#list mostUsedTags as tag>
                <a rel="tag"
                   class="tag pipe-tooltipped pipe-tooltipped--n"
                   aria-label="${tag.tagPublishedRefCount} ${countLabel}${articleLabel}"
                   href="${servePath}/tags/${tag.tagTitle?url('UTF-8')}">${tag.tagTitle}</a>
            </#list>
        </div>
    </div>
</#if>
</div>
