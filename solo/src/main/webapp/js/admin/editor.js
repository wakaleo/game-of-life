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
 * @fileoverview editor
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @version 1.1.0.5, Nov 8, 2016
 */

admin.editors = {};

/*
 * @description Create SoloEditor can use all editor. 
 *                e.g: TinyMCE, wnd 
 * @constructor
 * @param conf 编辑器初始化参数
 * @param conf.kind 编辑器类型 simple/all
 * @param conf.id 编辑器渲染元素 id
 * @param conf.language 编辑器使用语言
 * @param conf.type 编辑器种类
 * @param conf.codeMirrorLanguage codeMirror 编辑器当前解析语言
 */
var SoloEditor = function (conf) {
    this._defaults = {
        type: "tinyMCE",
        kind: "",
        id: "",
        language: ""
    };
    conf.type = Label.editorType;
    this.conf = conf;
    this._init();
};

$.extend(SoloEditor.prototype, {
    /*
     * @description 初始化
     */
    _init: function () {
        this.init();
    },
    /*
     * @description 初始化编辑器
     */
    init: function (type) {
        var conf = this.conf;
        if (type) {
            conf.type = type;
        }
        
        var types = conf.type.split("-");
        if (types.length === 2) {
            conf.codeMirrorLanguage = types[1];
            conf.type = types[0];
        }

        admin.editors[conf.type].init(conf);
    },
    /*
     * @description 获取编辑器值
     * @returns {string} 编辑器值
     */
    getContent: function () {
        var conf = this.conf;
        return admin.editors[conf.type].getContent(conf.id);
    },
    /*
     * @description 设置编辑器值
     * @param {string} content 编辑器回填内容 
     */
    setContent: function (content) {
        var conf = this.conf;
        admin.editors[conf.type].setContent(conf.id, content);
    },
    /*
     * @description 移除编辑器值
     */
    remove: function () {
        var conf = this.conf;
        admin.editors[conf.type].remove(conf.id);
    }
});

admin.editors.articleEditor = {};
admin.editors.abstractEditor = {};
admin.editors.pageEditor = {};
