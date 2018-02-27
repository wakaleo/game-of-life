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
package org.b3log.solo.util;

import org.apache.commons.io.IOUtils;
import org.b3log.latke.util.Stopwatchs;
import org.b3log.latke.util.Strings;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

/**
 * {@link org.b3log.solo.util.Markdowns} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.1.4, Dec 16, 2017
 * @since 0.4.5
 */
public final class MarkdownsTestCase {

    /**
     * Test method for {@linkplain Markdowns#toHTML(java.lang.String)}.
     *
     * @throws Exception exception
     */
    @Test
    public void toHTML() throws Exception {
        String markdownText = "";
        String html = Markdowns.toHTML(markdownText);

        Assert.assertEquals(html, "");

        markdownText = "# Solo Markdown Editor";
        html = Markdowns.toHTML(markdownText);

        final URL testFile = MarkdownsTestCase.class.getResource("/markdown_syntax.text");
        final String path = URLDecoder.decode(testFile.getPath(), "UTF-8");
        //System.out.println(path);

        final StringBuilder markdownTextBuilder = new StringBuilder();
        @SuppressWarnings("unchecked") final List<String> lines = IOUtils.readLines(new FileInputStream(path));

        for (final String line : lines) {
            markdownTextBuilder.append(line).append(Strings.LINE_SEPARATOR);
        }

        markdownText = markdownTextBuilder.toString();
        //System.out.println(markdownText);

        Stopwatchs.start("Markdowning");
        html = Markdowns.toHTML(markdownText);
        Stopwatchs.end();

        //System.out.println(html);

        //System.out.println("Stopwatch: ");
        //System.out.println(Stopwatchs.getTimingStat());

        // HTML entity test
        markdownText = "The first: &#39; <br/> The second: &AElig;";
        html = Markdowns.toHTML(markdownText);

        Assert.assertEquals(html, "<p>The first: ' <br> The second: Æ</p>");
    }
}
