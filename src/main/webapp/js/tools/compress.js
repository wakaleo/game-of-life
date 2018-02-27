var fs = require("fs"),
path = require('path'),
exec = require("child_process").exec;

var getCompressFiles = function (root) {
    var res = [],
    files = fs.readdirSync(root);  
    
    files.forEach(function(file) {
        var pathname = root + '/' + file,
        stat = fs.lstatSync(pathname);
        
        if (!stat.isDirectory()) {
            if (pathname.indexOf("mobile") < 0 && path.basename(pathname).indexOf(".min") < 0) {
                if (path.dirname(pathname).indexOf("css") > -1) {
                    res.push("css-" + pathname);
                } 
                
                if (path.dirname(pathname).indexOf("js") > -1) {
                    res.push("js-" + pathname);
                }
            }
        } else {
            res = res.concat(getCompressFiles(pathname));
        }
    });
    return res;
};

(function () {
    var compressFiles = getCompressFiles("../../skins");
    
    for (var i = 0; i < compressFiles.length; i++) {
        // skin js compress
        if (compressFiles[i].indexOf("js-") > -1) {
            var pathname = compressFiles[i].split("js-")[1];
            exec("uglifyjs " + pathname + " > " + path.dirname(pathname) + "/" + path.basename(pathname, ".js") + ".min.js", function (error, stdout, stderr) {
                if (error !== null) {
                    console.log(error);
                } 	
            });
        }
        // skin css compress
        if (compressFiles[i].indexOf("css-") > -1) {
            var pathname = compressFiles[i].split("css-")[1];
            exec("lessc -compress " + pathname + " > " + path.dirname(pathname) + "/" + path.basename(pathname, ".css") + ".min.css", function (error, stdout, stderr) {
                if (error !== null) {
                    console.log(error);
                } 	
            });
        }
    }
})();
