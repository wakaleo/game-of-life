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
 * @fileoverview article for admin
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.4.6.7, Oct 19, 2017
 */
admin.article = {
    currentEditorType: '',
    // 当发文章，取消发布，更新文章时设置为 false。不需在离开编辑器时进行提示。
    isConfirm: true,
    status: {
        id: undefined,
        isArticle: undefined,
        articleHadBeenPublished: undefined
    },
    content: "",
    // 自动保存草稿定时器
    autoSaveDraftTimer: "",
    // 自动保存间隔
    AUTOSAVETIME: 1000 * 60,
    /**
     * 初始化上传组建
     */
    initUploadFile: function (id) {
        var filename = "";
        $('#' + id).fileupload({
            multipart: true,
            url: "https://up.qbox.me",
            add: function (e, data) {
                filename = data.files[0].name;

                data.submit();

                $('#' + id + ' span').text('uploading...');
            },
            formData: function (form) {
                var data = form.serializeArray();
                var ext = filename.substring(filename.lastIndexOf(".") + 1);

                data.push({name: 'key', value: getUUID() + "." + ext});
                data.push({name: 'token', value: qiniu.qiniuUploadToken});

                return data;
            },
            done: function (e, data) {
                $('#' + id + ' span').text('');
                var qiniuKey = data.result.key;
                if (!qiniuKey) {
                    alert("Upload error, please check Qiniu configurations");

                    return;
                }

                $('#' + id).after('<div>![' + data.files[0].name + '](http://'
                        + qiniu.qiniuDomain + qiniuKey + ')</div>');
            },
            fail: function (e, data) {
                $('#' + id + ' span').text("Upload error, please check Qiniu configurations [" + data.errorThrown + "]");
            }
        }).on('fileuploadprocessalways', function (e, data) {
            var currentFile = data.files[data.index];
            if (data.files.error && currentFile.error) {
                alert(currentFile.error);
            }
        });
    },
    /**
     * @description 获取文章并把值塞入发布文章页面 
     * @param {String} id 文章 id
     * @param {Boolean} isArticle 文章或者草稿
     */
    get: function (id, isArticle) {
        this.status.id = id;
        this.status.isArticle = isArticle;
        admin.selectTab("article/article");
    },
    /**
     * @description 获取文章内容
     */
    getAndSet: function () {
        $("#loadMsg").text(Label.loadingLabel);
        $("#tipMsg").text("");
        $.ajax({
            url: latkeConfig.servePath + "/console/article/" + admin.article.status.id,
            type: "GET",
            cache: false,
            success: function (result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }

                // set default value for article.
                $("#title").val(result.article.articleTitle);
                admin.article.status.articleHadBeenPublished = result.article.articleHadBeenPublished;

                if (admin.article.currentEditorType !== result.article.articleEditorType) {
                    admin.editors.articleEditor.remove();
                    admin.editors.abstractEditor.remove();

                    admin.article.currentEditorType = result.article.articleEditorType;
                    admin.editors.articleEditor.init(result.article.articleEditorType);
                    admin.editors.abstractEditor.init(result.article.articleEditorType);
                }

                admin.editors.articleEditor.setContent(result.article.articleContent);
                admin.editors.abstractEditor.setContent(result.article.articleAbstract);
                admin.article.content = admin.editors.articleEditor.getContent();

                var tags = result.article.articleTags,
                        tagsString = '';
                for (var i = 0; i < tags.length; i++) {
                    if (0 === i) {
                        tagsString = tags[i].tagTitle;
                    } else {
                        tagsString += "," + tags[i].tagTitle;
                    }
                }

                $("#tag").val(tagsString);
                $("#permalink").val(result.article.articlePermalink);
                $("#viewPwd").val(result.article.articleViewPwd);

                $("#articleCommentable").prop("checked", result.article.articleCommentable);

                // signs
                var signs = result.article.signs;
                $(".signs button").each(function (i) {
                    if (parseInt(result.article.articleSignId) === parseInt(signs[i].oId)) {
                        $("#articleSign" + signs[i].oId).addClass("selected");
                    } else {
                        $("#articleSign" + signs[i].oId).removeClass("selected");
                    }
                });

                admin.article.setStatus();
                $("#loadMsg").text("");
            }
        });
    },
    /**
     * @description 删除文章
     * @param {String} id 文章 id
     * @param {String} fromId 文章来自草稿夹(draft)/文件夹(article)
     * @param {String} title 文章标题
     */
    del: function (id, fromId, title) {
        var isDelete = confirm(Label.confirmRemoveLabel + Label.articleLabel + '"' + title + '"?');
        if (isDelete) {
            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");

            $.ajax({
                url: latkeConfig.servePath + "/console/article/" + id,
                type: "DELETE",
                cache: false,
                success: function (result, textStatus) {
                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        $("#loadMsg").text("");
                        return;
                    }

                    admin[fromId + "List"].getList(1);
                }
            });
        }
    },
    /**
     * @@description 添加文章
     * @param {Boolean} articleIsPublished 文章是否发布过
     * @param {Boolean} isAuto 是否为自动保存
     */
    add: function (articleIsPublished, isAuto) {
        if (admin.article.validate()) {
            var that = this;
            that._addDisabled();

            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");
            var signId = "";
            $(".signs button").each(function () {
                if (this.className === "selected") {
                    signId = this.id.substr(this.id.length - 1, 1);
                }
            });

            var articleContent = admin.editors.articleEditor.getContent(),
                    articleAbstract = admin.editors.abstractEditor.getContent();

            var requestJSONObject = {
                "article": {
                    "articleTitle": $("#title").val(),
                    "articleContent": articleContent,
                    "articleAbstract": articleAbstract,
                    "articleTags": this.trimUniqueArray($("#tag").val()).toString(),
                    "articlePermalink": $("#permalink").val(),
                    "articleIsPublished": articleIsPublished,
                    "articleSignId": signId,
                    "postToCommunity": $("#postToCommunity").prop("checked"),
                    "articleCommentable": $("#articleCommentable").prop("checked"),
                    "articleViewPwd": $("#viewPwd").val()
                }
            };

            $.ajax({
                url: latkeConfig.servePath + "/console/article/",
                type: "POST",
                cache: false,
                data: JSON.stringify(requestJSONObject),
                success: function (result, textStatus) {
                    if (isAuto) {
                        $("#tipMsg").text(Label.autoSaveLabel);
                        admin.article.status.id = result.oId;
                        return;
                    }

                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        return;
                    }

                    if (articleIsPublished) {
                        admin.article.status.id = undefined;
                        admin.selectTab("article/article-list");
                    } else {
                        admin.selectTab("article/draft-list");
                    }

                    admin.article.isConfirm = false;
                },
                complete: function (jqXHR, textStatus) {
                    that._removeDisabled();
                    $("#loadMsg").text("");
                    if (jqXHR.status === 403) {
                        $.get("/admin-index.do");
                        that.add(articleIsPublished);
                    }
                }
            });
        }
    },
    /**
     * @description 更新文章
     * @param {Boolean} articleIsPublished 文章是否发布过
     * @param {Boolean} isAuto 是否为自动保存
     */
    update: function (articleIsPublished, isAuto) {
        if (admin.article.validate()) {
            var that = this;
            that._addDisabled();

            $("#loadMsg").text(Label.loadingLabel);
            $("#tipMsg").text("");
            var signId = "";
            $(".signs button").each(function () {
                if (this.className === "selected") {
                    signId = this.id.substr(this.id.length - 1, 1);
                }
            });

            var articleContent = admin.editors.articleEditor.getContent(),
                    articleAbstract = admin.editors.abstractEditor.getContent();

            var requestJSONObject = {
                "article": {
                    "oId": this.status.id,
                    "articleTitle": $("#title").val(),
                    "articleContent": articleContent,
                    "articleAbstract": articleAbstract,
                    "articleTags": this.trimUniqueArray($("#tag").val()).toString(),
                    "articlePermalink": $("#permalink").val(),
                    "articleIsPublished": articleIsPublished,
                    "articleSignId": signId,
                    "articleCommentable": $("#articleCommentable").prop("checked"),
                    "articleViewPwd": $("#viewPwd").val(),
                    "postToCommunity": $("#postToCommunity").prop("checked"),
                    "articleEditorType": admin.article.currentEditorType
                }
            };

            $.ajax({
                url: latkeConfig.servePath + "/console/article/",
                type: "PUT",
                cache: false,
                data: JSON.stringify(requestJSONObject),
                success: function (result, textStatus) {
                    if (isAuto) {
                        $("#tipMsg").text(Label.autoSaveLabel);
                        return;
                    }

                    $("#tipMsg").text(result.msg);
                    if (!result.sc) {
                        return;
                    }

                    if (articleIsPublished) {
                        admin.selectTab("article/article-list");
                    } else {
                        admin.selectTab("article/draft-list");
                    }

                    $("#tipMsg").text(Label.updateSuccLabel);

                    admin.article.status.id = undefined;
                    admin.article.isConfirm = false;
                },
                complete: function (jqXHR, textStatus) {
                    that._removeDisabled();
                    $("#loadMsg").text("");
                    if (jqXHR.status === 403) {
                        $.get("/admin-index.do");
                        that.update(articleIsPublished);
                    }
                }
            });
        }
    },
    /**
     * @description 发布文章页面设置文章按钮、发布到社区等状态的显示
     */
    setStatus: function () {
        $.ajax({// Gets all tags
            url: latkeConfig.servePath + "/console/tags",
            type: "GET",
            cache: false,
            success: function (result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }

                if (0 >= result.tags.length) {
                    return;
                }

                $("#tagCheckboxPanel>span").remove("");

                var spans = "";
                for (var i = 0; i < result.tags.length; i++) {
                    spans += "<span>" + result.tags[i].tagTitle + "</span>";
                }
                $("#tagCheckboxPanel").html(spans + '<div class="clear"></div>');

                $("#loadMsg").text("");
            }
        });

        // set button status
        if (this.status) {
            if (this.status.isArticle) {
                $("#unSubmitArticle").show();
                $("#saveArticle").hide();
                $("#submitArticle").show();
            } else {
                $("#submitArticle").show();
                $("#unSubmitArticle").hide();
                $("#saveArticle").show();
            }
            if (this.status.articleHadBeenPublished) {
                $("#postToCommunityPanel").hide();
            } else {
                // 1.0.0 开始默认会发布到社区
                // $("#postToCommunityPanel").show();
            }
        } else {
            $("#submitArticle").show();
            $("#unSubmitArticle").hide();
            $("#saveArticle").show();
            // 1.0.0 开始默认会发布到社区
            // $("#postToCommunityPanel").show();
        }

        $("#postToCommunity").attr("checked", "checked");
    },
    /**
     * @description 清除发布文章页面的输入框的内容
     */
    clear: function () {
        this.status = {
            id: undefined,
            isArticle: undefined,
            articleHadBeenPublished: undefined
        };
        this.setStatus();

        $("#title").val("");

        admin.editors.articleEditor.setContent("");
        admin.editors.abstractEditor.setContent("");

        // reset tag
        $("#tag").val("");
        $("#tagCheckboxPanel").hide().find("span").removeClass("selected");

        $("#permalink").val("");
        $("#articleCammentable").prop("checked", true);
        $("#postToCommunity").prop("checked", true);
        $(".signs button").each(function (i) {
            if (i === 0) {
                this.className = "selected";
            } else {
                this.className = "";
            }
        });

        $(".editor-preview-active").html("").removeClass('editor-preview-active');
        $("#uploadContent").remove();
    },
    /**
     * @description 初始化发布文章页面
     * @param {Function} fun 切面函数
     */
    init: function (fun) {
        this.currentEditorType = Label.editorType;

        // Inits Signs.
        $(".signs button").click(function (i) {
            $(".signs button").removeClass('selected');
            $(this).addClass('selected');
        });

        // For tag auto-completion
        $.ajax({// Gets all tags
            url: latkeConfig.servePath + "/console/tags",
            type: "GET",
            cache: false,
            success: function (result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }

                if (0 >= result.tags.length) {
                    return;
                }

                var tags = [];
                for (var i = 0; i < result.tags.length; i++) {
                    tags.push(result.tags[i].tagTitle);
                }

                $("#tag").completed({
                    height: 160,
                    buttonText: Label.selectLabel,
                    data: tags
                }).innerWidth($("#tag").parent().width() - 68);

                $("#loadMsg").text("");
            }
        });

        // submit action
        $("#submitArticle").click(function () {
            if (admin.article.status.id) {
                admin.article.update(true);
            } else {
                admin.article.add(true);
            }
        }
        );
        $("#saveArticle").click(function () {
            if (admin.article.status.id) {
                admin.article.update(admin.article.status.isArticle);
            } else {
                admin.article.add(false);
            }
        });

        this.initUploadFile('articleUpload');

        // editor
        admin.editors.articleEditor = new SoloEditor({
            id: "articleContent",
            kind: "all",
            fun: fun,
            height: 500
        });

        admin.editors.abstractEditor = new SoloEditor({
            id: "abstract",
            kind: "simple",
            height: 200
        });

        admin.article.clearDraftTimer();
        admin.article.autoSaveDraftTimer = setInterval(function () {
            admin.article._autoSaveToDraft();
        }, admin.article.AUTOSAVETIME);
    },
    /**
     * @description 自动保存草稿件
     */
    _autoSaveToDraft: function () {
        if ($("#title").val().replace(/\s/g, "") === "" ||
                admin.editors.articleEditor.getContent().replace(/\s/g, "") === "" ||
                $("#tag").val().replace(/\s/g, "") === "") {
            return;
        }
        if (admin.article.status.id) {
            if (!admin.article.status.isArticle) {
                admin.article.update(false, true);
            }
        } else {
            admin.article.add(false, true);
            admin.article.status.isArticle = false;
        }
    },
    /**
     * @description 关闭定时器
     */
    clearDraftTimer: function () {
        if (admin.article.autoSaveDraftTimer !== "") {
            window.clearInterval(admin.article.autoSaveDraftTimer);
            admin.article.autoSaveDraftTimer = "";
        }
    },
    /**
     * @description 验证发布文章字段的合法性
     */
    validate: function () {
        var articleContent = admin.editors.articleEditor.getContent();

        if ($("#title").val().replace(/\s/g, "") === "") {
            $("#tipMsg").text(Label.titleEmptyLabel);
            $("#title").focus().val("");
        } else if (articleContent.replace(/\s/g, "") === "") {
            $("#tipMsg").text(Label.contentEmptyLabel);
        } else if ($("#tag").val().replace(/\s/g, "") === "") {
            $("#tipMsg").text(Label.tagsEmptyLabel);
            $("#tag").focus().val("");
        } else {
            return true;
        }
        return false;
    },
    /**
     * @description 取消发布 
     * @param {Boolean} isAuto 是否为自动保存
     */
    unPublish: function (isAuto) {
        var that = this;
        that._addDisabled();
        $.ajax({
            url: latkeConfig.servePath + "/console/article/unpublish/" + admin.article.status.id,
            type: "PUT",
            cache: false,
            success: function (result, textStatus) {
                if (isAuto) {
                    $("#tipMsg").text(Label.autoSaveLabel);
                    return;
                }

                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    return;
                }

                admin.selectTab("article/draft-list");
                admin.article.status.id = undefined;
                admin.article.isConfirm = false;
            },
            complete: function (jqXHR, textStatus) {
                that._removeDisabled();
                $("#loadMsg").text("");
                if (jqXHR.status === 403) {
                    $.get("/admin-index.do");
                    that.unPublish();
                }
            }
        });
    },
    /**
     * @description 数组中无重复
     * @param {String} str 被解析的字符串
     * @returns {String} 无重复的字符串
     */
    trimUniqueArray: function (str) {
        str = str.toString();
        var arr = str.split(",");
        for (var i = 0; i < arr.length; i++) {
            arr[i] = arr[i].replace(/(^\s*)|(\s*$)/g, "");
            if (arr[i] === "") {
                arr.splice(i, 1);
                i--;
            }
        }
        var unique = $.unique(arr);
        return unique.toString();
    },
    /**
     * @description 点击发文文章时的处理
     */
    prePost: function () {
        $("#loadMsg").text(Label.loadingLabel);
        admin.article.content = "";
        if (!admin.editors.articleEditor.getContent) {
            return;
        }

        var articleContent = admin.editors.articleEditor.getContent();

        if (window.location.hash === "#article/article" &&
                articleContent.replace(/\s/g, '') !== "") {
            if (confirm(Label.editorPostLabel)) {
                admin.article.clear();
            }
        }
        $("#tipMsg").text("");
        $("#loadMsg").text("");

        if (admin.article.currentEditorType !== Label.editorType) {
            admin.editors.articleEditor.remove();
            admin.editors.abstractEditor.remove();

            admin.article.currentEditorType = Label.editorType;
            admin.editors.articleEditor.init(Label.editorType);
            admin.editors.abstractEditor.init(Label.editorType);
        }
    },
    /**
     * @description: 仿重复提交，点击一次后，按钮设置为 disabled
     */
    _addDisabled: function () {
        $("#unSubmitArticle").attr("disabled", "disabled");
        $("#saveArticle").attr("disabled", "disabled");
        $("#submitArticle").attr("disabled", "disabled");
    },
    /**
     * @description: 仿重复提交，当后台有数据返回后，按钮移除 disabled 状态
     */
    _removeDisabled: function () {
        $("#unSubmitArticle").removeAttr("disabled");
        $("#saveArticle").removeAttr("disabled");
        $("#submitArticle").removeAttr("disabled");
    }
};

/**
 * @description 注册到 admin 进行管理 
 */
admin.register.article = {
    "obj": admin.article,
    "init": admin.article.init,
    "refresh": function () {
        admin.editors.abstractEditor.setContent('');
        admin.editors.articleEditor.setContent('');
        $("#loadMsg").text("");
        $("#tipMsg").text("");
    }
};

function getUUID() {
    var d = new Date().getTime();

    var ret = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });

    ret = ret.replace(new RegExp("-", 'g'), "");

    return ret;
}
;
