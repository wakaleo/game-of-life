/*
 * Symphony - A modern community (forum/SNS/blog) platform written in Java.
 * Copyright (C) 2012-2017,  b3log.org & hacpai.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @file frontend tool.
 * 
 * @author <a href="mailto:liliyuan@fangstar.net">Liyuan Li</a>
 * @version 1.4.2.0, Jan 21, 20167
 */

'use strict';

var gulp = require("gulp");
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var cleanCSS = require('gulp-clean-css');

gulp.task('default', function () {
    // min css
    gulp.src('./src/main/webapp/js/lib/CodeMirrorEditor/codemirror.css')
            .pipe(cleanCSS())
            .pipe(concat('codemirror.min.css'))
            .pipe(gulp.dest('./src/main/webapp/js/lib/CodeMirrorEditor/'));
    
    // concat js
    var jsJqueryUpload = ['./src/main/webapp/js/lib/jquery/jquery.min.js',
        './src/main/webapp/js/lib/jquery/file-upload-9.10.1/vendor/jquery.ui.widget.js',
        './src/main/webapp/js/lib/jquery/file-upload-9.10.1/jquery.iframe-transport.js',
        './src/main/webapp/js/lib/jquery/file-upload-9.10.1/jquery.fileupload.js',
        './src/main/webapp/js/lib/jquery/jquery.bowknot.min.js',
        // codemirror
        './src/main/webapp/js/lib/CodeMirrorEditor/codemirror.js',
        './src/main/webapp/js/lib/CodeMirrorEditor/placeholder.js',
        './src/main/webapp/js/overwrite/codemirror/addon/hint/show-hint.js',
        './src/main/webapp/js/lib/CodeMirrorEditor/editor.js',
        './src/main/webapp/js/lib/to-markdown.js',
        './src/main/webapp/js/lib/highlight.js-9.6.0/highlight.pack.js'];
    gulp.src(jsJqueryUpload)
            .pipe(uglify())
            .pipe(concat('admin-lib.min.js'))
            .pipe(gulp.dest('./src/main/webapp/js/lib/compress/'));

});