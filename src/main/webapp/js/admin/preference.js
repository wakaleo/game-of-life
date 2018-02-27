/*
 * Copyright (c) 2010-2018, b3log.org & hacpai.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * preference for admin.
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.2.1.10, Nov 15, 2016
 */

/* preference 相关操作 */
admin.preference = {
    locale: "",
    editorType: "",
    /*
     * 初始化
     */
    init: function () {
        $("#tabPreference").tabs();

        $.ajax({
            url: latkeConfig.servePath + "/console/preference/",
            type: "GET",
            cache: false,
            success: function (result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }

                var preference = result.preference;

                $("#metaKeywords").val(preference.metaKeywords),
                        $("#metaDescription").val(preference.metaDescription),
                        $("#blogTitle").val(preference.blogTitle),
                        $("#blogSubtitle").val(preference.blogSubtitle),
                        $("#mostCommentArticleDisplayCount").val(preference.mostCommentArticleDisplayCount);
                $("#mostViewArticleDisplayCount").val(preference.mostViewArticleDisplayCount),
                        $("#recentCommentDisplayCount").val(preference.recentCommentDisplayCount);
                $("#mostUsedTagDisplayCount").val(preference.mostUsedTagDisplayCount);
                $("#articleListDisplayCount").val(preference.articleListDisplayCount);
                $("#articleListPaginationWindowSize").val(preference.articleListPaginationWindowSize);
                $("#localeString").val(preference.localeString);
                $("#timeZoneId").val(preference.timeZoneId);
                $("#noticeBoard").val(preference.noticeBoard);
                $("#footerContent").val(preference.footerContent);
                $("#htmlHead").val(preference.htmlHead);
                $("#externalRelevantArticlesDisplayCount").val(preference.externalRelevantArticlesDisplayCount);
                $("#relevantArticlesDisplayCount").val(preference.relevantArticlesDisplayCount);
                $("#randomArticlesDisplayCount").val(preference.randomArticlesDisplayCount);
                $("#keyOfSolo").val(preference.keyOfSolo);
                
                "true" === preference.enableArticleUpdateHint ? $("#enableArticleUpdateHint").attr("checked", "checked") : $("#enableArticleUpdateHint").removeAttr("checked");
                "true" === preference.allowVisitDraftViaPermalink ? $("#allowVisitDraftViaPermalink").attr("checked", "checked") : $("allowVisitDraftViaPermalink").removeAttr("checked");
                "true" === preference.allowRegister ? $("#allowRegister").attr("checked", "checked") : $("#allowRegister").removeAttr("checked");
                "true" === preference.commentable ? $("#commentable").attr("checked", "checked") : $("commentable").removeAttr("checked");

                admin.preference.locale = preference.localeString;
                admin.preference.editorType = preference.editorType;

                // skin
                $("#skinMain").data("skinDirName", preference.skinDirName);
                var skins = eval('(' + preference.skins + ')');
                var skinsHTML = "";
                for (var i = 0; i < skins.length; i++) {
                    var selectedClass = "";
                    if (skins[i].skinName === preference.skinName
                            && skins[i].skinDirName === preference.skinDirName) {
                        selectedClass += " selected";
                    }
                    skinsHTML += "<div title='" + skins[i].skinDirName
                            + "' class='left skinItem" + selectedClass + "'><img class='skinPreview' src='"
                            + latkeConfig.staticServePath + "/skins/" + skins[i].skinDirName
                            + "/preview.png'/><div>" + skins[i].skinName + "</div></div>";
                }
                $("#skinMain").append(skinsHTML + "<div class='clear'></div>");

                $(".skinItem").click(function () {
                    $(".skinItem").removeClass("selected");
                    $(this).addClass("selected");
                    $("#skinMain").data("skinDirName", this.title);
                });

                // sign
                var signs = eval('(' + preference.signs + ')');
                for (var j = 1; j < signs.length; j++) {
                    $("#preferenceSign" + j).val(signs[j].signHTML);
                }

                $("#articleListDisplay").val(preference.articleListStyle);
                $("#editorType").val(preference.editorType);
                $("#feedOutputMode").val(preference.feedOutputMode);
                $("#feedOutputCnt").val(preference.feedOutputCnt);
                
                $("#loadMsg").text("");
            }
        });

        $.ajax({
            url: latkeConfig.servePath + "/console/preference/qiniu",
            type: "GET",
            cache: false,
            success: function (result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }

                $("#qiniuAccessKey").val(result.qiniu.qiniuAccessKey);
                $("#qiniuSecretKey").val(result.qiniu.qiniuSecretKey);
                $("#qiniuDomain").val(result.qiniu.qiniuDomain);
                $("#qiniuBucket").val(result.qiniu.qiniuBucket);
            }
        });
    },
    /* 
     * @description 参数校验
     */
    validate: function () {
        if (!/^\d+$/.test($("#mostUsedTagDisplayCount").val())) {
            $("#tipMsg").text("[" + Label.paramSettingsLabel + " - " + Label.indexTagDisplayCntLabel + "] " + Label.nonNegativeIntegerOnlyLabel);
            $("#mostUsedTagDisplayCount").focus();
            return false;
        } else if (!/^\d+$/.test($("#recentCommentDisplayCount").val())) {
            $("#tipMsg").text("[" + Label.paramSettingsLabel + " - " + Label.indexRecentCommentDisplayCntLabel + "] " + Label.nonNegativeIntegerOnlyLabel);
            $("#recentCommentDisplayCount").focus();
            return false;
        } else if (!/^\d+$/.test($("#mostCommentArticleDisplayCount").val())) {
            $("#tipMsg").text("[" + Label.paramSettingsLabel + " - " + Label.indexMostCommentArticleDisplayCntLabel + "] " + Label.nonNegativeIntegerOnlyLabel);
            $("#mostCommentArticleDisplayCount").focus();
            return false;
        } else if (!/^\d+$/.test($("#mostViewArticleDisplayCount").val())) {
            $("#tipMsg").text("[" + Label.paramSettingsLabel + " - " + Label.indexMostViewArticleDisplayCntLabel + "] " + Label.nonNegativeIntegerOnlyLabel);
            $("#mostViewArticleDisplayCount").focus();
            return false;
        } else if (!/^\d+$/.test($("#articleListDisplayCount").val())) {
            $("#tipMsg").text("[" + Label.paramSettingsLabel + " - " + Label.pageSizeLabel + "] " + Label.nonNegativeIntegerOnlyLabel);
            $("#articleListDisplayCount").focus();
            return false;
        } else if (!/^\d+$/.test($("#articleListPaginationWindowSize").val())) {
            $("#tipMsg").text("[" + Label.paramSettingsLabel + " - " + Label.windowSizeLabel + "] " + Label.nonNegativeIntegerOnlyLabel);
            $("#articleListPaginationWindowSize").focus();
            return false;
        } else if (!/^\d+$/.test($("#randomArticlesDisplayCount").val())) {
            $("#tipMsg").text("[" + Label.paramSettingsLabel + " - " + Label.randomArticlesDisplayCntLabel + "] " + Label.nonNegativeIntegerOnlyLabel);
            $("#randomArticlesDisplayCount").focus();
            return false;
        } else if (!/^\d+$/.test($("#relevantArticlesDisplayCount").val())) {
            $("#tipMsg").text("[" + Label.paramSettingsLabel + " - " + Label.relevantArticlesDisplayCntLabel + "] " + Label.nonNegativeIntegerOnlyLabel);
            $("#relevantArticlesDisplayCount").focus();
            return false;
        } else if (!/^\d+$/.test($("#externalRelevantArticlesDisplayCount").val())) {
            $("#tipMsg").text("[" + Label.paramSettingsLabel + " - " + Label.externalRelevantArticlesDisplayCntLabel + "] " + Label.nonNegativeIntegerOnlyLabel);
            $("#externalRelevantArticlesDisplayCount").focus();
            return false;
        }
        return true;
    },
    /*
     * @description 更新
     */
    update: function () {
        if (!admin.preference.validate()) {
            return;
        }

        $("#tipMsg").text("");
        $("#loadMsg").text(Label.loadingLabel);
        var signs = [{
                "oId": 0,
                "signHTML": ""
            }, {
                "oId": 1,
                "signHTML": $("#preferenceSign1").val()
            }, {
                "oId": 2,
                "signHTML": $("#preferenceSign2").val()
            }, {
                "oId": 3,
                "signHTML": $("#preferenceSign3").val()
            }];

        var requestJSONObject = {
            "preference": {
                "metaKeywords": $("#metaKeywords").val(),
                "metaDescription": $("#metaDescription").val(),
                "blogTitle": $("#blogTitle").val(),
                "blogSubtitle": $("#blogSubtitle").val(),
                "mostCommentArticleDisplayCount": $("#mostCommentArticleDisplayCount").val(),
                "mostViewArticleDisplayCount": $("#mostViewArticleDisplayCount").val(),
                "recentCommentDisplayCount": $("#recentCommentDisplayCount").val(),
                "mostUsedTagDisplayCount": $("#mostUsedTagDisplayCount").val(),
                "articleListDisplayCount": $("#articleListDisplayCount").val(),
                "articleListPaginationWindowSize": $("#articleListPaginationWindowSize").val(),
                "skinDirName": $("#skinMain").data("skinDirName"),
                "localeString": $("#localeString").val(),
                "timeZoneId": $("#timeZoneId").val(),
                "noticeBoard": $("#noticeBoard").val(),
                "footerContent": $("#footerContent").val(),
                "htmlHead": $("#htmlHead").val(),
                "externalRelevantArticlesDisplayCount": $("#externalRelevantArticlesDisplayCount").val(),
                "relevantArticlesDisplayCount": $("#relevantArticlesDisplayCount").val(),
                "randomArticlesDisplayCount": $("#randomArticlesDisplayCount").val(),
                "enableArticleUpdateHint": $("#enableArticleUpdateHint").prop("checked"),
                "signs": signs,
                "keyOfSolo": $("#keyOfSolo").val(),
                "allowVisitDraftViaPermalink": $("#allowVisitDraftViaPermalink").prop("checked"),
                "articleListStyle": $("#articleListDisplay").val(),
                "editorType": $("#editorType").val(),
                "feedOutputMode": $("#feedOutputMode").val(),
                "feedOutputCnt": $("#feedOutputCnt").val(),
                "commentable": $("#commentable").prop("checked"),
                "allowRegister": $("#allowRegister").prop("checked")
            }
        };

        $.ajax({
            url: latkeConfig.servePath + "/console/preference/",
            type: "PUT",
            cache: false,
            data: JSON.stringify(requestJSONObject),
            success: function (result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }

                if ($("#localeString").val() !== admin.preference.locale ||
                        $("#editorType").val() !== admin.preference.editorType) {
                    window.location.reload();
                }

                // update article and preferences signs
                for (var i = 1; i < signs.length; i++) {
                    if ($("#articleSign" + signs[i].oId).length === 1) {
                        $("#articleSign" + signs[i].oId).tip("option", "content",
                                signs[i].signHTML === "" ? Label.signIsNullLabel : signs[i].signHTML.replace(/\n/g, "").replace(/<script.*<\/script>/ig, ""));
                    }
                }

                $("#loadMsg").text("");
            }
        });
    },
    /*
     * @description 更新 Qiniu 参数
     */
    updateQiniu: function () {
        $("#tipMsg").text("");
        $("#loadMsg").text(Label.loadingLabel);

        var requestJSONObject = {
            "qiniuAccessKey": $("#qiniuAccessKey").val(),
            "qiniuSecretKey": $("#qiniuSecretKey").val(),
            "qiniuDomain": $("#qiniuDomain").val(),
            "qiniuBucket": $("#qiniuBucket").val()
        };

        $.ajax({
            url: latkeConfig.servePath + "/console/preference/qiniu",
            type: "PUT",
            cache: false,
            data: JSON.stringify(requestJSONObject),
            success: function (result, textStatus) {
                if (result.sc) {
                    window.location.reload();
                }
                
                $("#tipMsg").text(result.msg);
                $("#loadMsg").text("");
            }
        });
    }
};

/*
 * 注册到 admin 进行管理 
 */
admin.register["preference"] = {
    "obj": admin.preference,
    "init": admin.preference.init,
    "refresh": function () {
        admin.clearTip();
    }
};
