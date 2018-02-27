<div id="tabPreference" class="sub-tabs fn-clear">
    <ul>
        <li>
            <div id="tabPreference_config">
                <a class="tab-current" href="#tools/preference/config">${configSettingsLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_skins">
                <a href="#tools/preference/skins">${skinLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_signs">
                <a href="#tools/preference/signs">${signLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_setting">
                <a href="#tools/preference/setting">${paramSettingsLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_qiniu">
                <a href="#toos/preference/qiniu">${qiniuLabel}</a>
            </div>
        </li>
        <li>
            <div id="tabPreference_solo">
                <a href="#tools/preference/solo">B3log</a>
            </div>
        </li>
    </ul>
</div>
<div id="tabPreferencePanel" class="sub-tabs-main">
    <div id="tabPreferencePanel_config">
        <table class="form" width="100%" cellpadding="0" cellspacing="9px">
            <tbody>
                <tr>
                    <td colspan="2" align="right">
                        <button onclick="admin.preference.update()">${updateLabel}</button>
                    </td>
                </tr>
                <tr>
                    <th width="234px">
                        <label for="blogTitle">${blogTitle1Label}</label>
                    </th>
                    <td>
                        <input id="blogTitle" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="blogSubtitle">${blogSubtitle1Label}</label>
                    </th>
                    <td>
                        <input id="blogSubtitle" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="blogHost">${blogHost1Label}</label>
                    </th>
                    <td>
                        <input id="blogHost" type="text" value="${servePath}" readonly="true" />
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="metaKeywords">${metaKeywords1Label}</label>
                    </th>
                    <td>
                        <input id="metaKeywords" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="metaDescription">${metaDescription1Label}</label>
                    </th>
                    <td>
                        <input id="metaDescription" type="text" />
                    </td>
                </tr>
                <tr>
                    <th valign="top">
                        <label for="htmlHead">${htmlhead1Label}</label>
                    </th>
                    <td>
                        <textarea rows="6" id="htmlHead"></textarea>
                    </td>
                </tr>
                <tr>
                    <th valign="top">
                        <label for="noticeBoard">${noticeBoard1Label}</label>
                    </th>
                    <td>
                        <textarea rows="6" id="noticeBoard"></textarea>
                    </td>
                </tr>
                <tr>
                    <th valign="top">
                        <label for="footerContent">${footerContent1Label}</label>
                    </th>
                    <td>
                        <textarea rows="2" id="footerContent"></textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="right">
                        <button onclick="admin.preference.update()">${updateLabel}</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div id="tabPreferencePanel_solo" class="none">
        <table class="form" width="100%" cellpadding="0" cellspacing="9px">
            <tbody>
                <tr>
                    <th width="80px">
                        <label for="keyOfSolo">${keyOfSolo1Label}</label>
                    </th>
                    <td>
                        <input id="keyOfSolo" class="normalInput" type="text" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <a href="https://hacpai.com/article/1457158841475" target="_blank">${APILabel}</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div id="tabPreferencePanel_setting" class="none">
        <table class="form" width="100%" cellpadding="0" cellspacing="9px">
            <tbody>
                <tr>
                    <td colspan="2" align="right">
                        <button onclick="admin.preference.update()">${updateLabel}</button>
                    </td>
                </tr>
                <tr>
                    <th width="234px">
                        <label for="localeString">${localeString1Label}</label>
                    </th>
                    <td>
                        <select id="localeString">
                            <option value="zh_CN">简体中文</option>
                            <option value="en_US">Englisth(US)</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th width="234px">
                        <label for="timeZoneId">${timeZoneId1Label}</label>
                    </th>
                    <td>
                        <select id="timeZoneId">
                            ${timeZoneIdOptions}
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="editorType">${editType1Label}</label>
                    </th>
                    <td>
                        <select id="editorType">
                            <option value="tinyMCE">TinyMCE</option>
                            <option value="CodeMirror-Markdown">CodeMirror(Markdown)</option>
                            <option value="KindEditor">KindEditor</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="articleListDisplay">${articleListDisplay1Label}</label>
                    </th>
                    <td>
                        <select id="articleListDisplay">
                            <option value="titleOnly">${titleOnlyLabel}</option>
                            <option value="titleAndAbstract">${titleAndAbstractLabel}</option>
                            <option value="titleAndContent">${titleAndContentLabel}</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="mostUsedTagDisplayCount">${indexTagDisplayCnt1Label}</label>
                    </th>
                    <td>
                        <input id="mostUsedTagDisplayCount" class="normalInput" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="recentCommentDisplayCount">${indexRecentCommentDisplayCnt1Label}</label>
                    </th>
                    <td>
                        <input id="recentCommentDisplayCount" class="normalInput" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="mostCommentArticleDisplayCount">${indexMostCommentArticleDisplayCnt1Label}</label>
                    </th>
                    <td>
                        <input id="mostCommentArticleDisplayCount" class="normalInput" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="mostViewArticleDisplayCount">${indexMostViewArticleDisplayCnt1Label}</label>
                    </th>
                    <td>
                        <input id="mostViewArticleDisplayCount" class="normalInput" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="articleListDisplayCount">${pageSize1Label}</label>
                    </th>
                    <td>
                        <input id="articleListDisplayCount" class="normalInput" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="articleListPaginationWindowSize">${windowSize1Label}</label>
                    </th>
                    <td>
                        <input id="articleListPaginationWindowSize" class="normalInput" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="randomArticlesDisplayCount">${randomArticlesDisplayCnt1Label}</label>
                    </th>
                    <td>
                        <input id="randomArticlesDisplayCount" class="normalInput" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="relevantArticlesDisplayCount">${relevantArticlesDisplayCnt1Label}</label>
                    </th>
                    <td>
                        <input id="relevantArticlesDisplayCount" class="normalInput" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="externalRelevantArticlesDisplayCount">${externalRelevantArticlesDisplayCnt1Label}</label>
                    </th>
                    <td>
                        <input id="externalRelevantArticlesDisplayCount" class="normalInput" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="enableArticleUpdateHint">${enableArticleUpdateHint1Label}</label>
                    </th>
                    <td>
                        <input id="enableArticleUpdateHint" type="checkbox" class="normalInput"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="allowVisitDraftViaPermalink">${allowVisitDraftViaPermalink1Label}</label>
                    </th>
                    <td>
                        <input id="allowVisitDraftViaPermalink" type="checkbox" class="normalInput"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="commentable">${allowComment1Label}</label>
                    </th>
                    <td>
                        <input id="commentable" type="checkbox" class="normalInput"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="allowRegister">${allowRegister1Label}</label>
                    </th>
                    <td>
                        <input id="allowRegister" type="checkbox" class="normalInput"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="feedOutputMode">${feedOutputModel1Label}</label>
                    </th>
                    <td>
                        <select id="feedOutputMode">
                            <option value="abstract">${abstractLabel}</option>
                            <option value="fullContent">${fullContentLabel}</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="feedOutputCnt">${feedOutputCnt1Label}</label>
                    </th>
                    <td>
                        <input id="feedOutputCnt" class="normalInput" type="text"/>
                    </td>
                </tr>

                <tr>
                    <th colspan="2">
                        <button onclick="admin.preference.update()">${updateLabel}</button>
                    </th>
                </tr>
            </tbody>
        </table>
    </div>
    <div id="tabPreferencePanel_skins" class="none form">
        <button onclick="admin.preference.update()" class="right">${updateLabel}</button>
        <div class="clear"></div>
        <div id="skinMain">
        </div>
        <button onclick="admin.preference.update()" class="right">${updateLabel}</button>
        <div class="clear"></div>
    </div>
    <div id="tabPreferencePanel_signs" class="none">
        <table class="form" width="100%" cellpadding="0" cellspacing="9px">
            <tbody>
                <tr>
                    <th colspan="2">
                        <button onclick="admin.preference.update()" class="right">${updateLabel}</button>
                    </th>
                </tr>
                <tr>
                    <th valign="top" width="80">
                        <button id="preferenceSignButton1">${signLabel}1</button>
                    </th>
                    <td>
                        <textarea rows="8" id="preferenceSign1"></textarea>
                    </td>
                </tr>
                <tr>
                    <th valign="top">
                        <button id="preferenceSignButton2">${signLabel}2</button>
                    </th>
                    <td>
                        <textarea rows="8" id="preferenceSign2"></textarea>
                    </td>
                </tr>
                <tr>
                    <th valign="top">
                        <button id="preferenceSignButton3">${signLabel}3</button>
                    </th>
                    <td>
                        <textarea rows="8" id="preferenceSign3"></textarea>
                    </td>
                </tr>
                <tr>
                    <th colspan="2">
                        <button onclick="admin.preference.update()" class="right">${updateLabel}</button>
                    </th>
                </tr>
            </tbody>
        </table>
    </div>
    <div id="tabPreferencePanel_qiniu" class="none">
        <table class="form" width="100%" cellpadding="0" cellspacing="9px">
            <tbody>
                <tr>
                    <th colspan="2">
                        <button onclick="admin.preference.updateQiniu()">${updateLabel}</button>
                    </th>
                </tr>
                <tr>
                    <th width="120">
                        <label for="qiniuAccessKey">${accessKey1Label}</label>
                    </th>
                    <td>
                        <input id="qiniuAccessKey" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="qiniuSecretKey">${secretKey1Label}</label>
                    </th>
                    <td>
                        <input id="qiniuSecretKey" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="qiniuDomain">${domain1Label}</label>
                    </th>
                    <td>
                        <input id="qiniuDomain" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <label for="qiniuBucket">${bucket1Label}</label>
                    </th>
                    <td>
                        <input id="qiniuBucket" type="text"/>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
${plugins}