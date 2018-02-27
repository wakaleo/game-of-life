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
 * @fileoverview util and every page should be used.
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.3.1.1, Jan 29, 2018
 */

/**
 * @description Util
 * @static
 */
var Util = {
  /**
   * 按需加载 MathJax 及 flow
   * @returns {undefined}
   */
  parseMarkdown: function (className) {
    var hasMathJax = false;
    var hasFlow = false;
    var className = className || 'article-body';
    $('.' + className).each(function () {
      $(this).find('p').each(function () {
        if ($(this).text().indexOf('$\\') > -1 || $(this).text().indexOf('$$') > -1) {
          hasMathJax = true;
        }
      });
      if ($(this).find('code.lang-flow, code.language-flow').length > 0) {
        hasFlow = true
      }
    });

    if (hasMathJax) {
      var initMathJax = function () {
        MathJax.Hub.Config({
          tex2jax: {
            inlineMath: [['$', '$'], ["\\(", "\\)"]],
            displayMath: [['$$', '$$']],
            processEscapes: true,
            processEnvironments: true,
            skipTags: ['pre', 'code', 'script']
          }
        });
        MathJax.Hub.Typeset();
      };

      if (typeof MathJax !== 'undefined') {
        initMathJax();
      } else {
        $.ajax({
          method: "GET",
          url: "https://cdn.staticfile.org/MathJax/MathJax-2.6-latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML&_=1473258780393",
          dataType: "script",
          cache: true
        }).done(function () {
          initMathJax();
        });
      }
    }

    if (hasFlow) {
      var initFlow = function () {
        $('.' + className + ' code.lang-flow, .' + className + ' code.language-flow').each(function (index) {
          var $it = $(this);
          var id = 'symFlow' + (new Date()).getTime() + index;
          $it.hide();
          var diagram = flowchart.parse($.trim($it.text()));
          $it.parent().after('<div style="text-align: center" id="' + id + '"></div>')
          diagram.drawSVG(id);
          $it.parent().remove();
          $('#' + id).find('svg').height('auto').width('auto');
        });
      };

      if (typeof (flowchart) !== 'undefined') {
        initFlow();
      } else {
        $.ajax({
          method: "GET",
          url: latkeConfig.staticServePath + '/js/lib/flowchart/flowchart.min.js',
          dataType: "script",
          cache: true
        }).done(function () {
          initFlow()
        });
      }
    }
  },
  /**
   * @description 是否登录
   * @returns {Boolean} 是否登录
   */
  isLoggedIn: function () {
    if (($("#admin").length === 1 && $("#admin").data("login")) || latkeConfig.isLoggedIn === "true") {
      return true;
    } else {
      return false;
    }
  },
  /**
   * @description 获取用户名称
   * @returns {String} 用户名称
   */
  getUserName: function () {
    if ($("#adminName").length === 1) {
      return $("#adminName").text();
    } else {
      return latkeConfig.userName;
    }
  },
  /**
   * @description 检测页面错误
   */
  error: function () {
    $("#tipMsg").text("Error: " + arguments[0] +
      " File: " + arguments[1] + "\nLine: " + arguments[2] +
      " please report this issue on https://github.com/b3log/solo/issues/new");
    $("#loadMsg").text("");
  },
  /**
   * @description IE6/7，跳转到 kill-browser 页面
   */
  killIE: function () {
    var addKillPanel = function () {
      if (Cookie.readCookie("showKill") === "") {
        var left = ($(window).width() - 701) / 2,
          top1 = ($(window).height() - 420) / 2;
        $("body").append("<div style='display: block; height: 100%; width: 100%; position: fixed; background-color: rgb(0, 0, 0); opacity: 0.6; top: 0px;z-index:11'></div>"
          + "<iframe style='left:" + left + "px;z-index:20;top: " + top1 + "px; position: fixed; border: 0px none; width: 701px; height: 420px;' src='" + latkeConfig.servePath + "/kill-browser'></iframe>");
      }
    };

    if ($.browser.msie) {
      // kill IE6 and IE7
      if ($.browser.version === "6.0" || $.browser.version === "7.0") {
        addKillPanel();
        return;
      }

      // 后台页面 kill 360
      if (window.external && window.external.twGetRunPath) {
        var path = external.twGetRunPath();
        if (path && path.toLowerCase().indexOf("360se") > -1 &&
          window.location.href.indexOf("admin-index") > -1) {
          addKillPanel();
          return;
        }
      }
    }
  },
  /**
   * @description 替换[emXX] 为图片
   * @param {String} str 替换字符串
   * @returns {String} 替换后的字符
   */
  replaceEmString: function (str) {
    var commentSplited = str.split("[em");
    if (commentSplited.length === 1) {
      return str;
    }

    str = commentSplited[0];
    for (var j = 1; j < commentSplited.length; j++) {
      var key = commentSplited[j].substr(0, 2);
      str += "<img width='20' src='" + latkeConfig.staticServePath + "/images/emotions/em" + key + ".png' alt='" +
        Label["em" + key + "Label"] + "' title='" +
        Label["em" + key + "Label"] + "'/> " + commentSplited[j].substr(3);
    }
    return str;
  },
  /**
   * @description URL 没有协议头，则自动加上 http://
   * @param {String} url URL 地址
   * @returns {String} 添加后的URL
   */
  proessURL: function (url) {
    if (!/^\w+:\/\//.test(url)) {
      url = "http://" + url;
    }
    return url;
  },
  /**
   * @description 切换到手机版
   * @param {String} skin 切换前的皮肤名称
   */
  switchMobile: function (skin) {
    Cookie.createCookie("btouch_switch_toggle", skin, 365);
    setTimeout(function () {
      location.reload();
    }, 1250);
  },
  /**
   * @description topbar 相关事件
   */
  setTopBar: function () {
    var $top = $("#top");
    if ($top.length === 1) {
      var $showTop = $("#showTop");
      $showTop.click(function () {
        $top.slideDown();
        $showTop.hide();
      });
      $("#hideTop").click(function () {
        $top.slideUp();
        $showTop.show();
      });
    }
  },
  /**
   * @description 回到顶部
   */
  goTop: function () {
    $('html, body').animate({scrollTop: 0}, 800);
  },
  /**
   * @description 回到底部
   */
  goBottom: function (bottom) {
    if (!bottom) {
      bottom = 0;
    }
    var wHeight = $("body").height() > $(document).height() ? $("body").height() : $(document).height();
    window.scrollTo(0, wHeight - $(window).height() - bottom);
  },
  /**
   * @description 页面初始化执行的函数
   */
  init: function () {
  //window.onerror = Util.error;
    Util.killIE();
    Util.setTopBar();
    Util.parseMarkdown();
  },
  /**
   * @description 替换侧边栏表情为图片
   * @param {Dom} comments 评论内容元素
   */
  replaceSideEm: function (comments) {
    for (var i = 0; i < comments.length; i++) {
      var $comment = $(comments[i]);
      $comment.html(Util.replaceEmString($comment.html()));
    }
  },
  /**
   * @description 根据 tags，穿件云效果
   * @param {String} [id] tags 根元素 id，默认为 tags
   */
  buildTags: function (id) {
    id = id || "tags";
    // 根据引用次数添加样式，产生云效果
    var classes = ["tags1", "tags2", "tags3", "tags4", "tags5"],
      bList = $("#" + id + " b").get();
    var max = parseInt($("#" + id + " b").last().text());
    var distance = Math.ceil(max / classes.length);
    for (var i = 0; i < bList.length; i++) {
      var num = parseInt(bList[i].innerHTML);
      // 算出当前 tag 数目所在的区间，加上 class
      for (var j = 0; j < classes.length; j++) {
        if (num > j * distance && num <= (j + 1) * distance) {
          bList[i].parentNode.className = classes[j];
          break;
        }
      }
    }

    // 按字母或者中文拼音进行排序
    $("#" + id).html($("#" + id + " li").get().sort(function (a, b) {
      var valA = $(a).find("span").text().toLowerCase();
      var valB = $(b).find("span").text().toLowerCase();
      // 对中英文排序的处理
      return valA.localeCompare(valB);
    }));
  },
  /**
   * @description 时间戳转化为时间格式
   * @param {String} time 时间
   * @param {String} format 格式化后日期格式
   * @returns {String} 格式化后的时间
   */
  toDate: function (time, format) {
    var dateTime = new Date(time);
    var o = {
      "M+": dateTime.getMonth() + 1, //month
      "d+": dateTime.getDate(), //day
      "H+": dateTime.getHours(), //hour
      "m+": dateTime.getMinutes(), //minute
      "s+": dateTime.getSeconds(), //second
      "q+": Math.floor((dateTime.getMonth() + 3) / 3), //quarter
      "S": dateTime.getMilliseconds() //millisecond
    }

    if (/(y+)/.test(format)) {
      format = format.replace(RegExp.$1, (dateTime.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
      if (new RegExp("(" + k + ")").test(format)) {
        format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
      }
    }
    return format;
  },
  /**
   * @description 获取窗口高度
   * @returns {Inter} 窗口高度
   */
  getWinHeight: function () {
    if (window.innerHeight) {
      return window.innerHeight;
    }
    if (document.compatMode === "CSS1Compat") {
      return window.document.documentElement.clientHeight;
    }
    return window.document.body.clientHeight;
  }
};
if (!Cookie) {
  /**
   * @description Cookie 相关操作
   * @static
   */
  var Cookie = {
    /**
     * @description 读取 cookie
     * @param {String} name cookie key
     * @returns {String} 对应 key 的值，如 key 不存在则返回 ""
     */
    readCookie: function (name) {
      var nameEQ = name + "=";
      var ca = document.cookie.split(';');
      for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ')
          c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0)
          return decodeURIComponent(c.substring(nameEQ.length, c.length));
      }
      return "";
    },
    /**
     * @description 清除 Cookie
     * @param {String} name 清除 key 为 name 的该条 Cookie
     */
    eraseCookie: function (name) {
      this.createCookie(name, "", -1);
    },
    /**
     * @description 创建 Cookie
     * @param {String} name 每条 Cookie 唯一的 key
     * @param {String} value 每条 Cookie 对应的值，将被 UTF-8 编码
     * @param {Int} days Cookie 保存时间
     */
    createCookie: function (name, value, days) {
      var expires = "";
      if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toGMTString();
      }
      document.cookie = name + "=" + encodeURIComponent(value) + expires + "; path=/";
    }
  };
}