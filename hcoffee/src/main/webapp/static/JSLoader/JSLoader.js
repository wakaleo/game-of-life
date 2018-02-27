var GLOBAL = this, JSLoader = function(config) {
	config = config || {};
	if (!config.lang) {
		var w = window, pw = w.parent != w ? w.parent : (w.opener != w ? w.opener : null), args;
		try {
			if (pw && pw.jsloader) {
				config.lang = pw.jsloader.getLang();
			} else if ((args = w.dialogArguments) && typeof args != 'string') {
				var loader = null, len, arg;
				if ((len = args.length) && (arg = args[len - 1]) && arg != w) {
					loader = arg.jsloader;
				} else if (args.alertDialog) {
					loader = args.alertDialog.getJSLoader();
				}
				config.lang = loader && loader.getLang() || '';
			}
		} catch (e) {
		}
	}
	var baseURL = config.baseURL || '';
	var document = window.document, rnotwhite = /\S/, globalEval = function(data) {
		if (data && rnotwhite.test(data)) {
			(GLOBAL.execScript || function(data) {
				GLOBAL['eval'].call(GLOBAL, data);
			})(data);
		}
	};

	var getXHR = ( function() {
		var query = null;
		if (window.bof_offline_xhr) {
			return function() {
				return window.bof_offline_xhr();
			};
		}
		if (window.XMLHttpRequest) {
			query = function() {
				return new XMLHttpRequest();
			};
		} else {
			var i = 0, id, ids = [ 'Msxml2.XMLHTTP', 'Microsoft.XMLHTTP', 'Msxml2.XMLHTTP.4.0' ];
			for (; (id = ids[i++]);) {
				try {
					new ActiveXObject(id);
					break;
				} catch (e) {
				}
			}
			query = function() {
				try {
					return new ActiveXObject(id);
				} catch (e) {
				}
			};
		}
		return query;
	})();
	var _localStorage = ( function() {
		try {
			if (typeof sessionStorage != 'undefined' && typeof localStorage != 'undefined'
					&& localStorage && sessionStorage) {
				return localStorage;
			} else {
				return null;
			}
		} catch(e) {
			return null;
		}
	})();
	try {
		if (_localStorage) {
			var localVersion = _localStorage.getItem('versionTextStr'), winxhr = null;
			var versionTxt = window.versionTxt;
			if (versionTxt === null) {
				var savePassword = _localStorage.getItem("FQSavepassword");
				var encryptedPassword = _localStorage.getItem("FQPassword");
				var encryptedUser = _localStorage.getItem("FQUser");
				_localStorage.clear();
				_localStorage.setItem("FQUser", encryptedUser);
				_localStorage.setItem("FQPassword", encryptedPassword);
				_localStorage.setItem("FQSavepassword", savePassword);
			} else {
				if (!versionTxt) {
					winxhr = getXHR();
					winxhr.open('GET', 'version.txt', false);
					winxhr.send('');
				}
				if (versionTxt || winxhr.status == 200) {
					window._enable_localStorage = true;
					var serviceVersion = versionTxt || winxhr.responseText;
					if (localVersion != serviceVersion) {
						var savePassword = _localStorage.getItem("FQSavepassword");
						var encryptedPassword = _localStorage.getItem("FQPassword");
						var encryptedUser = _localStorage.getItem("FQUser");
						_localStorage.clear();
						_localStorage.setItem("FQUser", encryptedUser);
						_localStorage.setItem("FQPassword", encryptedPassword);
						_localStorage.setItem("FQSavepassword", savePassword);
						try {
							_localStorage.setItem('versionTextStr', serviceVersion);
						} catch (e) {
						}
						
					}
				} else {
					var savePassword = _localStorage.getItem("FQSavepassword");
					var encryptedPassword = _localStorage.getItem("FQPassword");
					var encryptedUser = _localStorage.getItem("FQUser");
					_localStorage.clear();
					_localStorage.setItem("FQUser", encryptedUser);
					_localStorage.setItem("FQPassword", encryptedPassword);
					_localStorage.setItem("FQSavepassword", savePassword);
				}
			}
		}
	} catch(e) {
	}

	var Getter = function() {
		var doGet = function(url) {
			var xhr = getXHR();
			try {
				xhr.open('GET', url, false);
				xhr.send('');
				if (xhr.status != 200)
					throw new Error('Get fail.');
				return xhr.responseText;
			} catch (ex) {
				throw new Error(ex.message + ': ' + url);
			}
		};
		var name2url = function(name) {
			return 'js/' + name.replace(/\./g, '/') + '.js';
		};
		return {
			cacheFiles : window.cacheFiles || [],
			resolve : function(name, useGlobal) {
				var bofClassName = name, url = null, useGbk = true;
				if (name.match(/^.+ServiceStub$/)) {
					if (window.BOF_UI_DEBUG) {
						url = name2url(name + 'Fake');
					} else {
						url = name2url(name) + '.stub';
						useGbk = false;
					}
				} else {
					url = name2url(name);
				}
				var className = name.substring(name.lastIndexOf('.') + 1);
				var __url = url;
				url = baseURL + (useGbk ? 'gbk.jsp?' + (useGlobal ? 'useglobal=true&' : '') + 'name=vision/' : '') + url;
				if (config.lang)
					url += (/\?/.test(url) ? '&' : '?') + 'l=' + config.lang;
				var classText = this.cacheFiles[name];
				if (!classText) {
					if (window._enable_localStorage && _localStorage) {
						var key = config.lang ? config.lang + '&' + name : name;
						var localStorageFile = _localStorage.getItem(key) + '';
						if (!localStorageFile || localStorageFile == 'null' || localStorageFile == 'undefined') {
							classText = doGet(url);
							this.cacheFiles[name] = classText;
							try {
								_localStorage.setItem(key, classText);
							} catch (e) {
							}
						} else {
							classText = localStorageFile;
							this.cacheFiles[name] = classText;
						}
					} else {
						classText = doGet(url);
						this.cacheFiles[name] = classText;
					}
				}
				if (!classText || /^HTTP Status 404/.test(classText)) {
					throw new Error(classText ? classText : 'Get fail: ' + __url);
					// return 0;
				} else if (useGlobal === true) {
					globalEval(classText);
					return 1;
				} else {
					eval(classText);
				}
				var rtn = eval(className);
				if (rtn.patchedSubClasses)
					rtn = rtn.patchedSubClasses[rtn.patchedSubClasses.length - 1];
				if ('function' == typeof rtn)
					rtn.prototype.bofClassName = bofClassName;
				else
					rtn.bofClassName = bofClassName;
				return rtn;
			},
			resolveMany : function(names) {
				var namesToGet = [];
				for ( var i = 0, len = names.length; i < len; i++) {
					var found = false;
					var name = names[i];
					if (this.cacheFiles[name] != null)
						found = true;
					else if (window._enable_localStorage && _localStorage) {
						var key = config.lang ? config.lang + '&' + name : name;
						var localStorageFile = _localStorage.getItem(key);
						if (localStorageFile)
							found = true;
					}
					if (!found)
						namesToGet.push(name);
				}
				if (namesToGet.length == 0)
					return;
				var result = [], classListText;
				var url = baseURL + 'gbk.jsp?names=' + namesToGet.join(',');
				if (config.lang)
					url += '&l=' + config.lang;
				classListText = doGet(url);
				var classList = classListText.split('!@#$%^&*()');
				for ( var i = 0, len = namesToGet.length; i < len; i++) {
					var name = namesToGet[i];
					var classText = classList[i];
					this.cacheFiles[name] = classText;
					if (window._enable_localStorage && _localStorage) {
						var key = config.lang ? config.lang + '&' + name : name;
						try {
							_localStorage.setItem(key, classText);
						} catch (e) {
						}
					}
				}
				return result;
			}
		};
	};

	var Buffer = function() {
		var root = {};
		var findPackage = function(name, fCreate) {
			var nameParts = name.split('.'), pack = root;
			for ( var i = 0, len = nameParts.length; i < len - 1; i++) {
				var np = nameParts[i];
				if (pack[np])
					;
				else if (fCreate)
					pack[np] = {};
				else
					return null;
				pack = pack[np]
			}
			return pack;
		};
		return {
			get : function(name) {
				var pack = findPackage(name, false);
				var objName = name.substring(name.lastIndexOf('.') + 1);
				return (pack && pack[objName]);
			},
			set : function(name, obj) {
				var pack = findPackage(name, true);
				var objName = name.substring(name.lastIndexOf('.') + 1);
				pack[objName] = obj;
			},
			resolve : function(name) {
				return this.get(name);
			}
		};
	};

	var jsloader = function() {
		var loaderChain = [];
		loaderChain.push(new Buffer()); // buffer as the first loader
		// --StaticLoaderBegin-- //
		loaderChain.push(new Getter());
		loaderChain[1].resolveMany([ 'freequery.lang.lang', 'freequery.lang.domutils',
				'freequery.lang.EventAgent', 'freequery.lang.eventutil', 'freequery.lang.registry',
				'freequery.lang.CustomEvent', 'freequery.common.util', 'freequery.control.logger',
				'thirdparty.jquery.jquery', 'thirdparty.layer.layer' ]);
		// --StaticLoaderEnd-- //
		var isValidName = function(name) {
			return name && /^[a-zA-Z_$][a-zA-Z0-9_$]*(\.[a-zA-Z_$][a-zA-Z0-9_$]*)*$/.test(name);
		};
		return {
			baseHref : JSLoader.baseHref,
			baseURL : baseURL,
			getXHR : getXHR,
			startTime : (new Date).getTime(),
			getCacheFile : function() {
				return loaderChain[1].cacheFiles;
			},
			setCacheFile : function(obj) {
				loaderChain[1].cacheFiles = obj;
			},
			resolve : function(name, useGlobal) {
				if (!useGlobal && !isValidName(name))
					throw new Error('Not a valid name: ' + name);
				for ( var i = 0, len = loaderChain.length; i < len; i++) {
					var obj = loaderChain[i].resolve(name, !!useGlobal);
					if (null == obj)
						continue;
					if (i > 0)
						loaderChain[0].set(name, obj);
					return obj;
				}
				return null;
			},
			resolveMany : function(names) {
				var array = [];
				var buff = loaderChain[0];
				for ( var i = 0, len = names.length; i < len; i++) {
					if (!buff.resolve(names[i]))
						array.push(names[i]);
				}
				var result = loaderChain[1].resolveMany(array);
			},
			// constraints:
			// 1) can not use static members
			// 2) can not be used in inheritance
			// 3) can not use instanceof operator
			// 4) at most ten arguments can be used
			imports : function(className) {
				var factoryMethod = function() {
					return new (jsloader.resolve(className))(arguments[0], arguments[1],
							arguments[2], arguments[3], arguments[4], arguments[5], arguments[6],
							arguments[7], arguments[8], arguments[9]);
				};
				factoryMethod.getInstance = function() {
					var klass = jsloader.resolve(className);
					if ('object' == typeof klass) {
						return klass;
					}
					if (!klass.instance) {
						klass.instance = factoryMethod.apply(null, arguments);
					}
					return klass.instance;
				};
				factoryMethod.bofClassName = className;
				return factoryMethod;
			},
			prepare : function(name, object) {
				loaderChain[0].set(name, object);
			},
			getLang : function() {
				return config.lang;
			}
		};
	}();

	// --DefaultImportsBegin--//
	var lang = jsloader.resolve('freequery.lang.lang');
	var domutils = jsloader.resolve('freequery.lang.domutils');
	var registry = jsloader.resolve('freequery.lang.registry');
	var CustomEvent = jsloader.resolve('freequery.lang.CustomEvent');
	var boflog = jsloader.resolve('freequery.control.logger');
	var util = jsloader.resolve("freequery.common.util");
	// --DefaultImportsEnd--//
	var imports = jsloader.imports;
	var resolve = jsloader.resolve;
	var resolveMany = jsloader.resolveMany;
	if (domutils.isFirefox()) {
		HTMLElement.prototype.__defineGetter__('innerText', function() {
			var text = new Array();
			var child = this.firstChild;
			while (child) {
				if (child.nodeType == 1) {
					if (child.tagName == 'BR')
						text.push('\n');
					else
						text.push(child.innerText);
				} else if (child.nodeType == 3)
					text.push(child.nodeValue);
				child = child.nextSibling;
			}
			return text.join('');
		});

		HTMLElement.prototype.__defineSetter__('innerText', function(sText) {
			this.textContent = sText;
		});
	}
	return jsloader;
};

// set docBase
/*(function(doc){
	if (!window.bof_offline_xhr) { //离线的时候不要设docbase
		var url = (doc.URL.match('[a-zA-Z]+://.+?/vision') || [ '.' ])[0] + '/';
		var base = doc.createElement('base');
		base.href = JSLoader.baseHref = url;
		doc.getElementsByTagName('head')[0].appendChild(base);
	}
})(document);*/