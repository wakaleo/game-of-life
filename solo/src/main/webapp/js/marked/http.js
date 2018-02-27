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
 * @fileoverview marked HTTP server.
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.0, May 20, 2017
 * @since 1.7.0
 */

var PORT = 8250;

var http = require('http');
var marked = require('marked');
var renderer = new marked.Renderer();

renderer.listitem = function (text, level) {
    if (text.indexOf('[ ] ') === 0 && text.replace(/\s/g, '') !== '[]') {
        text = '<input type="checkbox" disabled>' + text.replace('[ ]', '');
        return `<li class="task-item">${text}</li>`;
    } else if (text.indexOf('[x] ') === 0 && text.replace(/\s/g, '') !== '[x]') {
        text = '<input type="checkbox" checked disabled>' + text.replace('[x]', '');
        return `<li class="task-item">${text}</li>`;
    }

    return `<li>${text}</li>`;
};

marked.setOptions({
    renderer: renderer,
    gfm: true,
    tables: true,
    breaks: true,
    smartLists: true
});

process.on('uncaughtException', function (err) {
    console.log(err);
});

var server = http.createServer(function (request, response) {
    var mdContent = '';

    request.on('data', function (data) {
        mdContent += data;
    });

    request.on('end', function () {
        response.write(marked(mdContent));

        response.end();
    });
});

server.listen(PORT);
console.log("Marked engine is running at port: " + PORT);
