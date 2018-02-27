<div class="wrapper">
    <footer class="footer">
        <div class="fn-clear">
        ${blogSubtitle}
            <div class="fn-right">
            ${blogTitle} &copy; ${year} ${footerContent}
            </div>
        </div>
        <div class="fn-clear">
        ${statistic.statisticPublishedBlogArticleCount} ${articleLabel} &nbsp;
        ${statistic.statisticPublishedBlogCommentCount} ${commentLabel} &nbsp;
        ${statistic.statisticBlogViewCount} ${viewLabel} &nbsp;
        ${onlineVisitorCnt} ${onlineVisitorLabel}
            <div class="fn-right">
                Powered by <a href="http://b3log.org" target="_blank">B3log 开源</a> •
                <a href="https://hacpai.com/tag/Solo" target="_blank">Solo</a> •
                Theme <a rel="friend" href="https://github.com/b3log/solo-skins" target="_blank">Medium</a>
            </div>
        </div>
    </footer>
</div>

<script type="text/javascript" src="${staticServePath}/js/lib/jquery/jquery.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${staticServePath}/js/common${miniPostfix}.js?${staticResourceVersion}"
        charset="utf-8"></script>
<script type="text/javascript"
        src="${staticServePath}/skins/${skinDirName}/js/common${miniPostfix}.js?${staticResourceVersion}"
        charset="utf-8"></script>
<script type="text/javascript">
    var latkeConfig = {
        "servePath": "${servePath}",
        "staticServePath": "${staticServePath}",
        "isLoggedIn": "${isLoggedIn?string}",
        "userName": "${userName}"
    };

    var Label = {
        "skinDirName": "${skinDirName}",
        "em00Label": "${em00Label}",
        "em01Label": "${em01Label}",
        "em02Label": "${em02Label}",
        "em03Label": "${em03Label}",
        "em04Label": "${em04Label}",
        "em05Label": "${em05Label}",
        "em06Label": "${em06Label}",
        "em07Label": "${em07Label}",
        "em08Label": "${em08Label}",
        "em09Label": "${em09Label}",
        "em10Label": "${em10Label}",
        "em11Label": "${em11Label}",
        "em12Label": "${em12Label}",
        "em13Label": "${em13Label}",
        "em14Label": "${em14Label}"
    };

    Util.parseMarkdown('content-reset');
</script>
${plugins}
