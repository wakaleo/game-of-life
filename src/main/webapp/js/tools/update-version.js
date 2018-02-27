var version = "",
newVersion = "";

process.argv.forEach(function (val, index) {  
    if (index === 2) {
        version = val;
    }

    if (index === 3) {
        newVersion = val;
    }
});  

var fs = require("fs"),
path = require('path');
var getPropertiesFiles = function (root) {
    var res = [],
    files = fs.readdirSync(root);
    
    files.forEach(function (file) {
        var pathname = root + '/' + file,
        stat = fs.lstatSync(pathname);
        
        if (!stat.isDirectory()) {
            if (path.basename(pathname) === "skin.properties") {
                res.push(pathname);
            }
        } else {
            res = res.concat(getPropertiesFiles(pathname));
        }
    });
    return res;
};

(function () {
    var skins = getPropertiesFiles("../../skins");

    for (var i = 0; i < skins.length; i++) {
        var file = fs.readFileSync(skins[i], "UTF-8");
        fs.writeFileSync(skins[i], file.replace("forSolo=" + version, "forSolo=" + newVersion), "UTF-8");
    }
})();
