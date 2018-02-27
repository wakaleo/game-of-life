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
 * @fileoverview KindEditor
 * @description 修改点：plugins/image/image.js 注释 173-176
 *                     plugins/media/media.js 注释 26 & 28
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @version 1.1.0.2, May 30, 2015
 */
admin.editors.KindEditor = {
    /*
     * @description 初始化编辑器
     * @param conf 编辑器初始化参数
     * @param conf.kind 编辑器类型
     * @param conf.id 编辑器渲染元素 id
     * @param conf.fun 编辑器首次加载完成后回调函数
     */
    init: function (conf) {
        var language = "zh_CN";
        if ("en_US" === Label.localeString) {
            language = "en"
        }

        if (conf.kind && conf.kind === "simple") {
            try {
                this[conf.id] = KindEditor.create('#' + conf.id, {
                    langType: language,
                    resizeType: 0,
                    items: ["bold", "italic", "underline", "strikethrough", "|", "undo", "redo", "|",
                        "insertunorderedlist", "insertorderedlist", "|", "source"
                    ]
                });
            } catch (e) {
                $("#tipMsg").text("KindEditor load fail");
            }
        } else {
            try {
                this[conf.id] = KindEditor.create('#' + conf.id, {
                    'uploadJson' : 'kindeditor/php/upyunUpload.php',
                    langType: language,
                    items: ["formatblock", "fontname", "fontsize", "|", "bold", "italic", "underline", "strikethrough", "forecolor", "|",
                        "link", "unlink", "image", "media", "|", "pagebreak", "emoticons", "code", "/",
                        "undo", "redo", "|", "insertunorderedlist", "insertorderedlist", "indent", "outdent", "|",
                        "justifyleft", "justifycenter", "justifyright", "justifyfull", "|", "plainpaste", "wordpaste", "|",
                        "clearhtml", "source", "preview"
                    ],
                    afterCreate: function () {
                        if (typeof (conf.fun) === "function") {
                            conf.fun();
                        }
                    }
                });
            } catch (e) {
                $("#tipMsg").text("KindEditor load fail");
            }
        }
    },
    /*
     * @description 获取编辑器值
     * @param {string} id 编辑器id
     * @returns {string} 编辑器值
     */
    getContent: function (id) {
        var content = "";
        try {
            content = this[id].html();
        } catch (e) {
            content = $("#" + id).val();
        }
        return content;
    },
    /*
     * @description 设置编辑器值
     * @param {string} id 编辑器 id
     * @param {string} content 设置编辑器值
     */
    setContent: function (id, content) {
        try {
            this[id].html(content);
        } catch (e) {
            $("#" + id).val(content);
        }
    },
    /*
     * @description 移除编辑器
     * @param {string} id 编辑器 id
     */
    remove: function (id) {
        this[id].remove();
    }
};
