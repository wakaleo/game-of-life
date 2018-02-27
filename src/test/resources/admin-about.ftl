<style>
    .about-developer .developer-title{
        float: left;
        width: 300px;
        margin-left: 12px;
    }
    .about-developer .contributor-title{
        margin-left: 18px;
        float: left;
        width: 300px;
        margin-left: 12px;
    }
    .about-developer .developer-body{
        float: left;
        width: 300px;
    }
    .about-developer .contributor-body{
        margin-left: 18px;
        float: left;
        width: 300px;
    }
    .about-developer .about-body ul{
        width: 230px;
    }
    .about-developer .about-body ul li{
        width: 100px;
        float: left;
        display: block;
    }
</style>

<div class="module-panel">
    <div class="module-header">
        <h2>${aboutLabel}</h2>
    </div>
    <div class="module-body padding12">
        <div class="about-logo">
            <a href="http://b3log.org" target="_blank">
                <img src="${staticServePath}/images/logo.png" alt="Solo" title="Solo" />
            </a>
        </div>
        <div class="left" style="width: 73%">
            <div id="aboutLatest" class="about-margin">${checkingVersionLabel}</div>
            ${aboutContentLabel}
        </div>
        <span class="clear" />
    </div>
    <div class="module-body padding12 about-developer">
        <div class="about-logo">
            <!--            <a href="http://b3log.org" target="_blank">
                            <img src="${staticServePath}/images/developers.jpg" alt="Solo" title="Solo" />
                        </a>-->
            <div style="width: 156px; height: 56px;"></div>
        </div>
        <div class="about-body">
            <div class="left" style="width: 73%">
                <div class="about-margin developer-title">${developersLabel}</div>
                <div class="about-margin contributor-title">${contributorsLabel}</div>
            </div>
            <div class="left" style="width: 73%">
                <div class="developer-body">
                    <ul>
                        <li><a target="_blank" href="http://88250.b3log.org">D</a></li>
                        <li><a target="_blank" href="http://vanessa.b3log.org">V</a></li>
                        <li><a target="_blank" href="mailto:wmainlove@gmail.com">mainlove</a></li>
                        <li><a target="_blank" href="http://people.apache.org/%7Edongxu">DX</a></li>
                        <li><a target="_blank" href="http://mizhichashao.com">大叔</a></li>
                        <li><a target="_blank" href="http://www.jiangzezhou.com">javen.jiang</a></li>
                    </ul>
                </div>
                <div class="contributor-body">
                    <ul>
                        <li><a target="_blank" href="http://www.ansen.org">An Shen</a></li>
                        <li><a target="_blank" href="http://www.bestck.net">Chevo</a></li>
                        <li><a target="_blank" href="https://github.com/paul-luo">破生</a></li>
                        <li><a target="_blank" href="http://xxk.b3log.org">宋诗献</a></li>
                        <li><a target="_blank" href="http://www.mynah.org">Lamb</a></li>
                        <li><a target="_blank" href="https://github.com/xiaomogui">大姨夫</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <span class="clear" />
    </div>
</div>
${plugins}
