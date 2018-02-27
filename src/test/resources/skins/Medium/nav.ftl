<nav id="headerNav" class="header__nav">
    <div class="wrapper">
        <a href="${servePath}">
            <svg>
                <use xlink:href="#icon-home"></use>
            </svg>
        ${indexLabel}
        </a>
        <a href="${servePath}/tags.html" rel="section">
            <svg>
                <use xlink:href="#icon-tag"></use>
            </svg> ${allTagsLabel}
        </a>
        <a href="${servePath}/archives.html">
            <svg>
                <use xlink:href="#icon-bookmark"></use>
            </svg> ${archiveLabel}
        </a>

    <#list pageNavigations as page>
        <a href="${page.pagePermalink}" target="${page.pageOpenTarget}" rel="section">
            <#if page.pageIcon != ''><img src="${page.pageIcon}"></#if> ${page.pageTitle}
        </a>
    </#list>

        <a rel="archive" href="${servePath}/links.html">
            <svg>
                <use xlink:href="#icon-link"></use>
            </svg> ${linkLabel}
        </a>

        <a rel="alternate" href="${servePath}/blog-articles-rss.do" rel="section">
            <svg>
                <use xlink:href="#icon-feed"></use>
            </svg>
            RSS
        </a>

    <#if isLoggedIn>
        <a href="${servePath}/admin-index.do#main">
            <svg>
                <use xlink:href="#icon-setting"></use>
            </svg> ${adminLabel}
        </a>
    <#else>
        <a href="${loginURL}">
            <svg>
                <use xlink:href="#icon-enter"></use>
            </svg> ${loginLabel}
        </a>
    </#if>
    </div>
</nav>