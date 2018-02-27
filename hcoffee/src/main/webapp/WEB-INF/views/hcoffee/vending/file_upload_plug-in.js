/* 
    无刷新异步上传插件 
    2013-10-16 Devotion Created 
*/  
(function ($) {  
    var defaultSettings = {  
        url: "",                                 //上传地址  
        buttonFeature: true,                    //true:点击按钮时仅选择文件； false:选择完文件后立即上传  
        fileSuffixs: ["jpg", "png"],             //允许上传的文件后缀名列表  
        errorText: "不能上传后缀为 {0} 的文件！", //错误提示文本，其中{0}将会被上传文件的后缀名替换  
        onCheckUpload: function (text) { //上传时检查文件后缀名不包含在fileSuffixs属性中时触发的回调函数，(text为错误提示文本)  
            alert(text);  
        },  
        onComplete: function (msg) { //上传完成后的回调函数[不管成功或失败，它都将被触发](msg为服务端的返回字符串)  
        },  
        onAllComplete: function () {  
        },//全部文件上传完成触发的事件  
  
        onChosen: function (file, obj, fileSize, errorText) { //选择文件后的回调函数，(file为选中文件的本地路径;obj为当前的上传控件实例;fileSize为当前文件的大小,errorText为获取文件大小时的错误提示文本)  
            //alert(file);  
            return true;//在此回调中返回false将取消当前选择的文件  
        },  
        maximumFilesUpload: 5,//最大文件选择数(当此属性大于1时，buttonFeature属性只能为true)  
        submitFilesNum: 3,//最大提交上传数（当触发submitUpload方法时，文件上传的个数）  
        onSubmitHandle: function (uploadFileNumber) { //提交上传时的回调函数，uploadFileNumber为当前上传的文件数量  
            //在此回调中返回false上传提交将被阻止  
            return true;  
        },  
        onSameFilesHandle: function (file) { //当重复选择相同的文件时触发  
            //在此回调中返回false当前选择的文件将从上传队列中取消  
            return true;  
        },  
        isGetFileSize: false,//是否获取文件大小，默认为false  
  
        isSaveErrorFile: true,//是否保存上传失败的文件，默认true  
  
        perviewElementId: "",//用于预览的元素id（请传入一个div元素的id）  
  
        perviewImgStyle: null//用于设置图片预览时的样式（可不设置，在不设置的情况下多文件上传时只能显示一张图片），如{ width: '100px', height: '100px', border: '1px solid #ebebeb' }  
    };  
  
    $.fn.uploadFile = function (settings) {  
  
        settings = $.extend({}, defaultSettings, settings || {});  
  
        if (settings.perviewElementId) {  
            //设置图片预览元素的必须样式  
            if (!settings.perviewImgStyle) {  
                var perviewImg = document.getElementById(settings.perviewElementId);  
                perviewImg.style.overflow = "hidden";  
            }  
        }  
  
        return this.each(function () {  
            var self = $(this);  
  
            var upload = new UploadAssist(settings);  
  
            upload.createIframe(this);  
  
            //绑定当前按钮点击事件  
            self.bind("click", function (e) {  
                upload.chooseFile();  
            });  
  
            //将上传辅助类的实例，存放到当前对象中，方便外部获取  
            self.data("uploadFileData", upload);  
  
  
        });  
    };  
})(jQuery);  
  
//上传辅助类  
function UploadAssist(settings) {  
    //保存设置  
    this.settings = settings;  
    //已选择文件的路径集合  
    this.choseFilePath = [];  
    //上传错误文件集合  
    this.uploadError = [];  
    //创建的iframe唯一名称  
    this.iframeName = "upload" + this.getTimestamp();  
    //提交状态  
    this.submitStatus = true;  
    //已经上传的文件数  
    this.uploadFilesNum = 0;  
    //上传完成计数  
    this.uploadNum = 0;  
    //针对IE上传获取文件大小时的错误提示文本  
    this.errorText = "请设置浏览器一些参数后再上传文件，方法如下（设置一次即可）：\n请依次点击浏览器菜单中的\n'工具->Internet选项->安全->可信站点->自定义级别'\n在弹出的自定义级别窗口中找到 'ActiveX控件和插件' 项，将下面的子项全部选为 '启用' 后，点击确定。\n此时不要关闭当前窗口，再点击 '站点' 按钮，在弹出的窗口中将下面复选框的 '√' 去掉，然后点击 '添加' 按钮并关闭当前窗口。\n最后一路 '确定' 完成并刷新当前页面。";  
    return this;  
}  
  
UploadAssist.prototype = {  
    //辅助类构造器  
    constructor: UploadAssist,  
  
    //创建iframe  
    createIframe: function (/*插件中指定的dom对象*/elem) {  
  
        var html = "<html>"  
                + "<head>"  
                + "<title>upload</title>"  
                + "<script>"  
                + "function getDCMT(iframeName){return window.frames[iframeName].document;}"  
                + "</" + "script>"  
                + "</head>"  
                + "<body>"  
                + "</body>"  
                + "</html>";  
  
        this.iframe = $("<iframe name='" + this.iframeName + "'></iframe>")[0];  
        this.iframe.style.width = "0px";  
        this.iframe.style.height = "0px";  
        this.iframe.style.border = "0px solid #fff";  
        this.iframe.style.margin = "0px";  
        elem.parentNode.insertBefore(this.iframe, elem);  
        var iframeDocument = this.getIframeContentDocument();  
        iframeDocument.write(html);  
    },  
  
    //获取时间戳  
    getTimestamp: function () {  
        return (new Date()).valueOf();  
    },  
  
    //创建上传控件到创建的iframe中  
    createInputFile: function () {  
        var that = this;  
        var dcmt = this.getIframeContentDocument();  
        var input = dcmt.createElement("input");  
        var randomNum = this.getTimestamp();  
        input.type = "file";  
        $(input).attr("name", "input" + randomNum);  
        $(input).attr("id", input.name);  
  
        input.onchange = function () {  
  
            //保存已经选择的文件路径  
            that.choseFilePath.push({ "name": this.name, "value": this.value });  
  
            var fileSuf = this.value.substring(this.value.lastIndexOf(".") + 1);  
  
            //检查是否为允许上传的文件  
            if (!that.checkFileIsUpload(fileSuf, that.settings.fileSuffixs)) {  
                that.removeFile(this.name);  
                that.settings.onCheckUpload(that.settings.errorText.replace("{0}", fileSuf));  
                return;  
            }  
  
            var fileSize;  
            var errorTxt = null;  
            //是否获取上传文件大小  
            if (that.settings.isGetFileSize) {  
                fileSize = perviewImage.getFileSize(this, dcmt);  
                if (fileSize == "error") {  
                    fileSize = 0;  
                    errorTxt = that.errorText;  
                }  
            }  
  
            //选中后的回调  
            var chosenStatus = that.settings.onChosen(this.value, this, fileSize, errorTxt);  
            if (typeof chosenStatus === "boolean" && !chosenStatus) {  
                that.removeFile(this.name);  
                return;  
            }  
  
            if (that.checkFileIsExist(this.value)) {  
                var status = that.settings.onSameFilesHandle(this.value);  
                if (typeof status === "boolean" && !status) {  
                    that.removeFile(this.name);  
                    return;  
                }  
            }  
  
            //是否开启了图片预览  
            if (that.settings.perviewElementId) {  
                if (!that.settings.perviewImgStyle) {  
                    perviewImage.beginPerview(this, that.settings.perviewElementId, dcmt, fileSuf);  
                } else {  
                    var ul = perviewImage.getPerviewRegion(that.settings.perviewElementId);  
                    var main = perviewImage.createPreviewElement(this.name, this.value, that.settings.perviewImgStyle);  
                    var li = document.createElement("li");  
                    if ($.browser.msie) {  
                        li.style.styleFloat = "left";  
                    }  
                    else {  
                        li.style.cssFloat = "left";  
                    }  
  
                    li.style.margin = "5px";  
                    li.appendChild(main);  
                    ul.appendChild(li);  
                    var div = $(main).children("div").get(0);  
                    $(main).find("img[name]").hover(function () {  
                        this.src = perviewImage.closeImg.after;  
                    }, function () {  
                        this.src = perviewImage.closeImg.before;  
                    }).click(function () {  
                        that.removeFile($(this).attr("name"));  
                        $(this).parents("li").fadeOut(200, function () {  
                            $(this).remove();  
                        });  
                    });  
  
                    perviewImage.beginPerview(this, div, dcmt, fileSuf);  
                }  
            }  
  
            if (!that.settings.buttonFeature) {  
                that.submitUpload();  
            }  
        };  
  
        var formName = "form" + randomNum;  
        var form = $('<form method="post" target="iframe' + randomNum + '" enctype="multipart/form-data" action="' + that.settings.url + '" name="' + formName + '"></form>');  
        form.append(input);  
  
        $(dcmt.body).append($("<div></div>").append(form)  
            .append($("<iframe name='iframe" + randomNum + "'></iframe>").on("load", function () {  
                var dcmt1 = that.getInsideIframeContentDocument(this.name);  
                if (dcmt1.body.innerHTML) {  
                    //开始上传下一个文件  
                    that.insideOperation();  
                    that.uploadNum++;  
  
                    //注意：上传失败的响应文本默认为"error"  
                    var responseText = $(dcmt1.body).text();  
  
                    if (responseText == "error" && that.settings.isSaveErrorFile) {  
                        //保存上传失败的文件  
                        that.uploadError.push(this.name.replace("iframe", "input"));  
                    }  
  
                    var obj = that.getObjectByName(this.name.replace("iframe", "input"));  
                    if (obj) {  
                        //是否开启了预览  
                        if (that.settings.perviewElementId) {  
                            var closeImg = $("#" + that.settings.perviewElementId).find("img[name='" + obj.name + "']");  
                            closeImg.next().hide();  
                            if (responseText !== "error") {  
                                //对于上传成功的文件，将它从预览中删除  
                                closeImg.parents("li").fadeOut("slow", function () {  
                                    $(this).remove();  
                                });  
                            } else {  
                                //上传失败的文件，加亮显示  
                                closeImg.css("visibility", "visible").parents("li").css({  
                                    "border": "1px solid #ff9999",  
                                    "background-color": "#ffdddd"  
                                });  
                            }  
                        }  
                    }  
  
                    if (that.settings.onComplete) {  
                        that.settings.onComplete(dcmt1.body.innerHTML);  
                    }  
  
                    if (that.uploadNum == that.uploadFilesNum) {  
                        that.submitStatus = true;  
                        that.clearUploadQueue();  
                        that.uploadFilesNum = 0;  
                        that.uploadNum = 0;  
                        that.settings.onAllComplete();  
                    }  
  
                    dcmt1.body.innerHTML = "";  
                }  
            })));  
        return input;  
    },  
  
    //获取创建的iframe中的document对象  
    getIframeContentDocument: function () {  
        return this.iframe.contentDocument || this.iframe.contentWindow.document;  
    },  
  
    //获取创建的iframe所在的window对象  
    getIframeWindow: function () {  
        return this.iframe.contentWindow || this.iframe.contentDocument.parentWindow;  
    },  
  
    //获取创建的iframe内部iframe的document对象  
    getInsideIframeContentDocument: function (iframeName) {  
        return this.getIframeWindow().getDCMT(iframeName);  
    },  
  
    //获取上传input控件  
    getUploadInput: function () {  
        var inputs = this.getIframeContentDocument().getElementsByTagName("input");  
        var len = inputs.length;  
  
        if (len > 0) {  
            if (!inputs[len - 1].value) {  
                return inputs[len - 1];  
            } else {  
                return this.createInputFile();  
            }  
        }  
        return this.createInputFile();  
    },  
  
    //forEach迭代函数  
    forEach: function (/*数组*/arr, /*代理函数*/fn) {  
        var len = arr.length;  
        for (var i = 0; i < len; i++) {  
            var tmp = arr[i];  
            if (fn.call(tmp, i, tmp) == false) {  
                break;  
            }  
        }  
    },  
  
    //提交上传  
    submitUpload: function () {  
        var status = this.settings.onSubmitHandle(this.choseFilePath.length);  
        if (typeof status === "boolean") {  
            if (!status) {  
                return;  
            }  
        }  
        this.clearedNotChooseFile();  
  
        var sbmtNum = this.settings.submitFilesNum;  
        var len = this.choseFilePath.length;  
        var dcmt = this.getIframeContentDocument();  
        var that = this;  
  
        if (!len) return;  
        if (!this.submitStatus) return;  
        this.filesNum = len;  
  
        //设置有效上传数量，有可能选择的文件小于设置的提交数量  
        var advisableSubmitNum = sbmtNum < len ? sbmtNum : len;  
  
        this.uploadFilesNum = advisableSubmitNum;  
  
        this.submitStatus = false;  
        for (var i = 0; i < advisableSubmitNum; i++) {  
            (function (n) {  
                var time = (n + 1) * 500;  
                window.setTimeout(function () {  
                    var obj = that.choseFilePath[n];  
                    var formName = obj.name.replace("input", "form");  
                    that.forEach(dcmt.forms, function () {  
                        if (this.name == formName) {  
                            this.submit();  
                            return false;  
                        }  
                    });  
                    if (that.settings.perviewElementId) {  
                        //用于设置上传loading图片显示   
                        var imgclose = $("#" + that.settings.perviewElementId).find("img[name='" + obj.name + "']");  
                        imgclose.next().show();  
                        imgclose.css("visibility", "hidden");  
                    }  
                }, time);  
            })(i);  
        }  
    },  
    //内部提交操作，外部不能调用  
    insideOperation: function () {  
        var len = this.choseFilePath.length;  
        var dcmt = this.getIframeContentDocument();  
        var that = this;  
  
        if (!len) return;  
        var obj = this.choseFilePath[this.uploadFilesNum];  
  
        if (obj && obj.name) {  
            this.uploadFilesNum++;  
            (function (o) {  
                window.setTimeout(function () {  
                    var formName = o.name.replace("input", "form");  
  
                    that.forEach(dcmt.forms, function (i) {  
                        if (this.name == formName) {  
                            this.submit();  
                            return false;  
                        }  
                    });  
  
                    if (that.settings.perviewElementId) {  
                        //用于设置上传loading图片显示   
                        var imgclose = $("#" + that.settings.perviewElementId).find("img[name='" + o.name + "']");  
                        imgclose.next().show();  
                        imgclose.css("visibility", "hidden");  
                    }  
                }, 300);  
            })(obj);  
        }  
    },  
    //检查文件是否可以上传  
    checkFileIsUpload: function (fileSuf, suffixs) {  
  
        var status = false;  
        this.forEach(suffixs, function (i, n) {  
            if (fileSuf.toLowerCase() === n.toLowerCase()) {  
                status = true;  
                return false;  
            }  
        });  
        return status;  
    },  
  
    //检查上传的文件是否已经存在上传队列中  
    checkFileIsExist: function (/*当前上传的文件*/file) {  
  
        var status = false;  
        this.forEach(this.choseFilePath, function (i, n) {  
            if (n.value == file) {  
                status = true;  
                return false;  
            }  
        });  
        return status;  
    },  
  
    //清除未选择文件的上传控件  
    clearedNotChooseFile: function () {  
        var files = this.getIframeContentDocument().getElementsByTagName("input");  
  
        this.forEach(files, function (i, n) {  
            if (!n.value) {  
                var div = n.parentNode.parentNode;  
                div.parentNode.removeChild(div);  
                return false;  
            }  
        });  
    },  
  
    //将指定上传的文件从上传队列中删除  
    removeFile: function (name) {  
        var that = this;  
        var files = this.getIframeContentDocument().getElementsByTagName("input");  
        this.forEach(this.choseFilePath, function (i, n) {  
            if (n.name == name) {  
                that.forEach(files, function (j, m) {  
                    if (m.name == n.name) {  
                        var div = m.parentNode.parentNode;  
                        div.parentNode.removeChild(div);  
                        return false;  
                    }  
                });  
                that.choseFilePath.splice(i, 1);  
                return false;  
            }  
        });  
    },  
    //获取选择的上传文件对象  
    getObjectByName: function (name) {  
        var obj, that = this;  
        this.forEach(this.choseFilePath, function (i) {  
            if (this.name === name) {  
                obj = that.choseFilePath[i];  
                return false;  
            }  
        });  
        return obj;  
    },  
    //清空上传队列  
    clearUploadQueue: function () {  
        var len = this.uploadError.length;  
        var that = this;  
        if (!len) {  
            this.choseFilePath.length = 0;  
            this.getIframeContentDocument().body.innerHTML = "";  
        } else {  
            var errorFiles = this.uploadError.join();  
            var newArr = this.choseFilePath.slice(0);  
            this.forEach(newArr, function () {  
                if (errorFiles.indexOf(this.name) == -1) {  
                    that.removeFile(this.name);  
                }  
            });  
        }  
        this.uploadError.length = 0;  
    },  
  
    //选择上传文件  
    chooseFile: function () {  
        var uploadfile;  
        if (!this.choseFilePath.length && this.settings.perviewElementId) {  
            $("#" + this.settings.perviewElementId).find("ul").empty();  
        }  
        if (this.choseFilePath.length == this.settings.maximumFilesUpload) {  
            if (this.settings.maximumFilesUpload <= 1) {  
                this.choseFilePath.length = 0;  
                var files = this.getIframeContentDocument().getElementsByTagName("input");  
                if (!files.length) {  
                    uploadfile = this.getUploadInput();  
                    $(uploadfile).click();  
                    return;  
                } else {  
                    uploadfile = files[0];  
                    $(uploadfile).click();  
                    return;  
                }  
            } else {  
                return;  
            }  
        }  
        uploadfile = this.getUploadInput();  
        $(uploadfile).click();  
    }  
};  
  
//图片预览操作  
var perviewImage = {  
    timers: [],  
    closeImg: {  
        before: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAOVSURBVHjaYtTUdGdAA4K/fv3U+Pv3rw+QLfT//3+Gf//+vWNiYtjCwsJyAyj2HiT2//8/sGKAAGJB1gkU9Pj581eNnJyctaamMgM/Py8DIyMDw+fPXxlu3rxfdfPmjaPMzIwtTEzMO2B6AAKIBaH5fw4LC1tHeHgQt7u7PYOOjhIDNzcb2IBfv/4x3LjxiGHr1n3WK1duXPPx45sKJiamKSB9AAHECPIC0GZ3ZmbWzQkJkazu7rYMLCyMDD9//gYZCzWcgYGVlRUozsxw9Oh5hv7+Gb8/fXrnC+TvBAggZhERZb7fv3/PdnCwV7C3twT69w+DlpYcw5s3HxkeP34FdP53IPsDg6qqNAMXFxvQIA4GoGXMFy9eVgK6eg1AADH9/ftbW0hIxEpFRQms0MBAlYGDg51BQ0OegZ2dneH58zdAMRUGKSlhBnFxQYY7dx4CvfSHQVBQyAqkFyCAmIWEFDOlpaVtgQHH8O7dB4aXLz8wqKjIMHBysoE1SUqKMCgoSIC90te3lGHNmu0MDx8+Yfjx4xvQmz9eAgQQCzAwhBiBIfX69RugwC+GR4+eAl3yliEx0Y+Bl5eDQU5ODBwG3d0LGdau3QH0AjMwLFiBruQEBjCTEEAAsYBC+du3HwxPnjxnAMY90JCfoLBlePXqLdAAabDNX778AHvl37+/QP9DYubfP0haAAggJlAi+fr1M8Pbt2+Bml4z8PBwMxQURDMoK0uDbf78+QfYJY2N2Qy2thZA//8CGsIMtOg70MI/7wACiAkYkluAfmH48+cPMOHwMbS1FTJoaspB/bwYqHE6w4cP3xn4+DgYWltzgAGqywCMNbABQBdsAQggJmAsX/3+/esxkPNAoX7jxgNQomKYMWMtw65dRxkuXLjGMHHiEobv338x3Lv3DEhDLAO6+hjQq1cBAohRWdkOqOGvOwcHz2Z1dU1WcXEJBgkJYYbbtx+AExIogH/9+s2gra0KDOgPwLTxmOHKlfO/v3z55AtM0jsBAggYjfKg0Lz769eP958/f7FnZ2djAyYUBhERQWBUcgLDhItBWFiY4f37j8AYeshw/frVr1++fCwFal4O8iZAAIENAKdpRoZTwLg99/Llc8VPnz7JffnyFWQwMAa+Mdy/fw+YmW4w3Lp1/eiPH19zgJqXwfIQQACBvQDNiaBsC/K/IDCQNICKfNjYWIVAYQNMH++AIb4FGPrg7IycgwECDADIUZC5UWvTuwAAAABJRU5ErkJggg==",  
        after: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA3XAAAN1wFCKJt4AAAAB3RJTUUH1wwbFhkQHxvdFgAAArpJREFUeNplz1to1QUAx/HP/3/O2c7OrudsR8vmamOL2hw5LaQHgxKCLvQSRVgPFhJKo4cyMsmxKNQgfOuhfKxICQTpocvAIFhai/WSTNIcnK3pbu12tnO2cwsfbZ+H3+MXfgGbfURDgn0xHgiIFskUuPgOE/7njsBxahsYqAnDN1p3765N9/QIYzGL4+Myw8PlbC53fp23j5HZFBhgSxPft27f3tfX36+ure2O+sbKiitnzrg2MjK/xjNH+RUCeJbo41xsSyb3PtR/WLQuLhIGVAJKlEsUSyUBrp8966+xsdkl+o7zTwiP8mqSvffu6ZMLs+565YAVWRvL1xSWrpufvmHr/v3y1dVu30pHIuk4pxCEiCQ4nIyEYk15heakmm3bdR06Zrq0LjM3ruPNI+o7O8V7uy1NTkjV10vw4ns0Rw6Q7uCTdLwS1LfMyM+Mytxa0r73aVse2adp52Oau7pd/eGCq4MHNUxPKc4sy1dEylwOt3FPNUH09gTrWhILSr+d9N3pQfGGpHRXjz9/+tHoqRe0t8yqasyK1lKNKlrDdYpVCEqUixRLLBZTep98DkD7zofFO3pVGqlqJlJDDDEK4TkmQlbXc+QWGZ9P2TMwpHXHLpnL541+dVRtMuWp00NmGncpxCkUCLDGWDjJWp6LG0XmJtn6xOu2PrjLzUvn3fr6JdVXP3btmyNqGlN2vHbC/Dxry5SZ+pTfIyh3MN3Jyxt54cKVYYXVKZPn3peIFkTK5G5ckp34W+bLE7IjeWGOOT78gp8DQPwzBtt5dymkkqTmbuIpYnUEAblZsldIrLLB0AGeX2QlAEDD5xy7n7eWia2GlKsREhaIb9CEZS58wKFRplGJAGD9W36JMXwf9cmKdF1RIlGgrmS1zOU/GDjIyZv8iwoENguRQKqblnqiI8yUWUAWJQD4D4Cg/5i7WltRAAAAAElFTkSuQmCC"  
    },  
    loading: "data:image/gif;base64,R0lGODlhEAAQAKIGAMLY8YSx5HOm4Mjc88/g9Ofw+v///wAAACH/C05FVFNDQVBFMi4wAwEAAAAh+QQFCgAGACwAAAAAEAAQAAADMGi6RbUwGjKIXCAA016PgRBElAVlG/RdLOO0X9nK61W39qvqiwz5Ls/rRqrggsdkAgAh+QQFCgAGACwCAAAABwAFAAADD2hqELAmiFBIYY4MAutdCQAh+QQFCgAGACwGAAAABwAFAAADD1hU1kaDOKMYCGAGEeYFCQAh+QQFCgAGACwKAAIABQAHAAADEFhUZjSkKdZqBQG0IELDQAIAIfkEBQoABgAsCgAGAAUABwAAAxBoVlRKgyjmlAIBqCDCzUoCACH5BAUKAAYALAYACgAHAAUAAAMPaGpFtYYMAgJgLogA610JACH5BAUKAAYALAIACgAHAAUAAAMPCAHWFiI4o1ghZZJB5i0JACH5BAUKAAYALAAABgAFAAcAAAMQCAFmIaEp1motpDQySMNFAgA7",  
    fileImg: "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAYAAABccqhmAAAEn0lEQVR4nO3aPY4cRQCG4QJt4IjIwV5phYwQlyEi8hEcEiFESgAXQBbHcYiILETASv5h/6Znequ73+dJOpnSfNErlVRfjDl+nPS/bN/vY4zfZo+o+HL2APjMqzHGN7NHVAgAWyQCz0QA2CoReAYCwJaJwMoEgK0TgRUJAHsgAisRAPZCBFYgAOyJCFzY1ewBD3g/xng3ewSruD7j7Kvbr8dCF7DlALwbY/wwewSreDPGeHHGeRG4EFcA9sp14AIEgD0TgTMJAHsnAmcQAI5ABBYSAI5CBBYQALbqnwVnROBEAsBW/TXG+GXBORE4gQCwZX+MMX5ecE4EnkgA2Lq3QwRWIwDsgQisRADYCxFYgQCwJyJwYQLA3pwTgW8vvGX3BIA9WhqBr4cIfEIA2CsRuAABYM9E4EwCwN6JwBkEgCMQgYUEgKMQgQUEgCMRgRMJAEcjAicQAI5IBJ5IADgqEXgCAeDIROARAsDRvR1j/LTgXCICAkDBn0ME7iQAVIjAHQSAEhH4jABQIwIfEQCKROCWAFAlAkMAaMtHQACoS0dAACAcAQGA/yQjIADwQS4CAgCfSkVAAOD/MhEQALhbIgICAPc7fAQEAB526AgIADzusBEQAHiaQ0ZAAODpDhcBAYDTnBOB7y685WwCAKdbGoGbsbEICAAsc4gICAAst/sIXM0eAPf4aozxZvaIFd3cfn+dOUIA2LIXswesbHoEXAFgrqnXAQGA+W4e/8k6BADCBADCBADCBADCBADCvANghr9nD3hGm37LIADM8P3sAc/o9RjjevaI+7gCQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQJgAQNjV7AEPeDnGeD17BJzp5ewBD9lyAK7GGNezR8CRuQJAmABAmABAmABAmABAmABAmABAmABA2L+QtW4QG9di8wAAAABJRU5ErkJggg==",  
    //获取预览元素  
    getElementObject: function (elem) {  
        if (elem.nodeType && elem.nodeType === 1) {  
            return elem;  
        } else {  
            return document.getElementById(elem);  
        }  
    },  
    //开始图片预览  
    beginPerview: function (/*文件上传控件实例*/file, /*需要显示的元素id或元素实例*/perviewElemId,/*上传页面所在的document对象*/ dcmt,/*文件后缀名*/ fileSuf) {  
        var imgSufs = ",jpg,jpeg,bmp,png,gif,";  
        var isImage = imgSufs.indexOf("," + fileSuf.toLowerCase() + ",") > -1;//检查是否为图片  
  
        if (isImage) {  
            this.imageOperation(file, perviewElemId, dcmt);  
        } else {  
            this.fileOperation(perviewElemId, fileSuf);  
        }  
    },  
    //一般文件显示操作  
    fileOperation: function (/*需要显示的元素id或元素实例*/perviewElemId,/*文件后缀名*/ fileSuf) {  
        var preview_div = this.getElementObject(perviewElemId);  
  
        var MAXWIDTH = preview_div.clientWidth;  
        var MAXHEIGHT = preview_div.clientHeight;  
        var img = document.createElement("img");  
        preview_div.appendChild(img);  
        img.style.visibility = "hidden";  
        img.src = this.fileImg;  
        img.onload = function () {  
            var rect = perviewImage.clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);  
            img.style.width = rect.width + 'px';  
            img.style.height = rect.height + 'px';  
            img.style.marginLeft = rect.left + 'px';  
            img.style.marginTop = rect.top + 'px';  
            img.style.visibility = "visible";  
        }  
        var txtTop = 0 - (MAXHEIGHT * 2 / 3);  
        $('<div style="text-align:center; position:relative; z-index:100; color:#404040;font: 13px/27px Arial,sans-serif;"></div>')  
            .text(fileSuf + "文件").css("top", txtTop + "px").appendTo(preview_div);  
  
    },  
    //图片预览操作  
    imageOperation: function (/*文件上传控件实例*/file, /*需要显示的元素id或元素实例*/perviewElemId,/*上传页面所在的document对象*/ dcmt) {  
        for (var t = 0; t < this.timers.length; t++) {  
            window.clearInterval(this.timers[t]);  
        }  
        this.timers.length = 0;  
  
        var preview_div = this.getElementObject(perviewElemId);  
  
        var MAXWIDTH = preview_div.clientWidth;  
        var MAXHEIGHT = preview_div.clientHeight;  
  
        if (file.files && file.files[0]) { //此处为Firefox，Chrome以及IE10的操作  
            preview_div.innerHTML = "";  
            var img = document.createElement("img");  
            preview_div.appendChild(img);  
            img.style.visibility = "hidden";  
            img.onload = function () {  
                var rect = perviewImage.clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);  
                img.style.width = rect.width + 'px';  
                img.style.height = rect.height + 'px';  
                img.style.marginLeft = rect.left + 'px';  
                img.style.marginTop = rect.top + 'px';  
                img.style.visibility = "visible";  
            }  
  
            var reader = new FileReader();  
            reader.onload = function (evt) {  
                img.src = evt.target.result;  
            }  
            reader.readAsDataURL(file.files[0]);  
        }  
        else {//此处为IE6，7，8，9的操作  
            file.select();  
            var src = dcmt.selection.createRange().text;  
  
            var div_sFilter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + src + "')";  
            var img_sFilter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src='" + src + "')";  
  
            preview_div.innerHTML = "";  
            var img = document.createElement("div");  
            preview_div.appendChild(img);  
            img.style.filter = img_sFilter;  
            img.style.visibility = "hidden";  
            img.style.width = "100%";  
            img.style.height = "100%";  
  
            function setImageDisplay() {  
                var rect = perviewImage.clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);  
                preview_div.innerHTML = "";  
                var div = document.createElement("div");  
                div.style.width = rect.width + 'px';  
                div.style.height = rect.height + 'px';  
                div.style.marginLeft = rect.left + 'px';  
                div.style.marginTop = rect.top + 'px';  
                div.style.filter = div_sFilter;  
  
                preview_div.appendChild(div);  
            }  
  
            //图片加载计数  
            var tally = 0;  
  
            var timer = window.setInterval(function () {  
                if (img.offsetHeight != MAXHEIGHT) {  
                    window.clearInterval(timer);  
                    setImageDisplay();  
                } else {  
                    tally++;  
                }  
                //如果超过两秒钟图片还不能加载，就停止当前的轮询  
                if (tally > 20) {  
                    window.clearInterval(timer);  
                    setImageDisplay();  
                }  
            }, 100);  
  
            this.timers.push(timer);  
        }  
    },  
    //按比例缩放图片  
    clacImgZoomParam: function (maxWidth, maxHeight, width, height) {  
        var param = { width: width, height: height };  
        if (width > maxWidth || height > maxHeight) {  
            var rateWidth = width / maxWidth;  
            var rateHeight = height / maxHeight;  
  
            if (rateWidth > rateHeight) {  
                param.width = maxWidth;  
                param.height = Math.round(height / rateWidth);  
            } else {  
                param.width = Math.round(width / rateHeight);  
                param.height = maxHeight;  
            }  
        }  
  
        param.left = Math.round((maxWidth - param.width) / 2);  
        param.top = Math.round((maxHeight - param.height) / 2);  
        return param;  
    },  
    //创建图片预览元素  
    createPreviewElement: function (/*关闭图片名称*/name,/*上传时的文件名*/file, /*预览时的样式*/style) {  
        var img = document.createElement("div");  
        img.title = file;  
        img.style.overflow = "hidden";  
        for (var s in style) {  
            img.style[s] = style[s];  
        }  
  
        var text = document.createElement("div");  
        text.style.width = style.width;  
        text.style.overflow = "hidden";  
        text.style.textOverflow = "ellipsis";  
        text.style.whiteSpace = "nowrap";  
        text.innerHTML = file;  
  
  
        var top = 0 - window.parseInt(style.width) - 15;  
        var right = 0 - window.parseInt(style.width) + 14;  
        var close = document.createElement("img");  
        close.setAttribute("name", name);  
        close.src = this.closeImg.before;  
        close.style.position = "relative";  
        close.style.top = top + "px";  
        close.style.right = right + "px";  
        close.style.cursor = "pointer";  
  
        var loadtop = (0 - window.parseInt(style.height)) / 2 - 26;  
        var loadright = (0 - window.parseInt(style.width)) / 2 + 22;  
        var imgloading = document.createElement("img");  
        imgloading.src = this.loading;  
        imgloading.style.position = "relative";  
        imgloading.style.top = loadtop + "px";  
        imgloading.style.right = loadright + "px";  
        imgloading.style.display = "none";  
  
        var main = document.createElement("div");  
        main.appendChild(img);  
        main.appendChild(text);  
        main.appendChild(close);  
        main.appendChild(imgloading);  
        return main;  
    },  
  
    //获取预览区域  
    getPerviewRegion: function (elem) {  
        var perview = $(this.getElementObject(elem));  
        if (!perview.find("ul").length) {  
            var ul = document.createElement("ul");  
            ul.style.listStyleType = "none";  
            ul.style.margin = "0px";  
            ul.style.padding = "0px";  
  
            var div = document.createElement("div");  
            div.style.clear = "both";  
            perview.append(ul).append(div);  
            return ul;  
        } else {  
            return perview.children("ul").get(0);  
        }  
    },  
    //获取上传文件大小  
    getFileSize: function (/*上传控件dom对象*/file, /*上传控件所在的document对象*/dcmt) {  
        var fileSize;  
        if (file.files && file.files[0]) {  
            fileSize = file.files[0].size;  
        } else {  
            file.select();  
            var src = dcmt.selection.createRange().text;  
            try {  
                var fso = new ActiveXObject("Scripting.FileSystemObject");  
                var fileObj = fso.getFile(src);  
                fileSize = fileObj.size;  
            } catch (e) {  
                return "error";  
            }  
        }  
        fileSize = ((fileSize / 1024) + "").split(".")[0];  
        return fileSize;  
    }  
}  