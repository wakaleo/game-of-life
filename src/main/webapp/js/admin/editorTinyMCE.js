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
 * @fileoverview tinyMCE editor
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @version 1.1.0.6, May 30, 2015
 */
admin.editors.tinyMCE = {
    /*
     * @description 初始化编辑器
     * @param conf 编辑器初始化参数
     * @param conf.kind 编辑器类型
     * @param conf.id 编辑器渲染元素 id
     * @param conf.fun 编辑器首次加载完成后回调函数
     */
    init: function (conf) {
        var language = Label.localeString.substring(0, 2);
        if (language === "zh") {
            language = "zh-cn";
        }

        if (conf.kind && conf.kind === "simple") {
            try {
                tinyMCE.init({
                    // General options
                    language: language,
                    mode: "exact",
                    elements: conf.id,
                    theme: "advanced",
                    plugins: "media",
                    // Theme options
                    theme_advanced_buttons1: "bold,italic,underline,strikethrough,|,undo,redo,|,bullist,numlist,|,code",
                    theme_advanced_buttons2: "",
                    theme_advanced_buttons3: "",
                    theme_advanced_toolbar_location: "top",
                    theme_advanced_toolbar_align: "left",
                    valid_children: "+body[style]"
                });
            } catch (e) {
                $("#tipMsg").text("TinyMCE load fail");
            }
        } else {
            try {
                tinyMCE.init({
                    // General options
                    language: language,
                    mode: "exact",
                    elements: conf.id,
                    theme: "advanced",
                    plugins: "autosave,style,advhr,advimage,advlink,preview,inlinepopups,media,paste,syntaxhl,wordcount",
                    // Theme options
                    theme_advanced_buttons1: "formatselect,fontselect,fontsizeselect,|,bold,italic,underline,strikethrough,forecolor,|,advhr,blockquote,syntaxhl,",
                    theme_advanced_buttons2: "undo,redo,|,bullist,numlist,outdent,indent,|,justifyleft,justifycenter,justifyright,justifyfull,|,pastetext,pasteword,|,link,unlink,image,iespell,media,|,cleanup,code,preview,",
                    theme_advanced_buttons3: "",
                    theme_advanced_toolbar_location: "top",
                    theme_advanced_toolbar_align: "left",
                    theme_advanced_resizing: true,
                    theme_advanced_statusbar_location: "bottom",
                    extended_valid_elements: "link[type|rel|href|charset],pre[name|class],iframe[src|width|height|name|align],+a[*]",
                    valid_children: "+body[style]",
                    relative_urls: false,
                    remove_script_host: false,
                    oninit: function () {
                        if (typeof (conf.fun) === "function") {
                            conf.fun();
                        }
                    }
                });
            } catch (e) {
                $("#tipMsg").text("TinyMCE load fail");
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
            content = tinyMCE.get(id).getContent();
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
            if (tinyMCE.get(id)) {
                tinyMCE.get(id).setContent(content);
            } else {
                $("#" + id).val(content);
            }
        } catch (e) {
            $("#" + id).val(content);
        }
    },
    /*
     * @description 移除编辑器
     * @param {string} id 编辑器 id
     */
    remove: function (id) {
        tinyMCE.get(id).remove();
    }
};
