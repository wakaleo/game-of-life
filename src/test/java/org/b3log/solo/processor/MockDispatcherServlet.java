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
package org.b3log.solo.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HttpControl;
import org.b3log.latke.servlet.handler.AdviceHandler;
import org.b3log.latke.servlet.handler.ArgsHandler;
import org.b3log.latke.servlet.handler.Handler;
import org.b3log.latke.servlet.handler.MethodInvokeHandler;
import org.b3log.latke.servlet.handler.RequestDispatchHandler;
import org.b3log.latke.servlet.handler.RequestPrepareHandler;
import org.b3log.latke.servlet.renderer.AbstractHTTPResponseRenderer;
import org.b3log.latke.servlet.renderer.HTTP404Renderer;
import org.b3log.latke.servlet.renderer.HTTP500Renderer;

/**
 * Mock dispatcher servlet for unit tests.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Apr 22, 2017
 * @since 1.7.0
 */
public class MockDispatcherServlet {

    /**
     * the holder of all the sys-handler.
     */
    private static final List<Handler> SYS_HANDLER = new ArrayList<>();

    public void init() throws ServletException {
        SYS_HANDLER.add(new RequestPrepareHandler());
        SYS_HANDLER.add(new RequestDispatchHandler());
        SYS_HANDLER.add(new ArgsHandler());
        SYS_HANDLER.add(new AdviceHandler());
        SYS_HANDLER.add(new MethodInvokeHandler());
    }

    public void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final HTTPRequestContext httpRequestContext = new HTTPRequestContext();

        httpRequestContext.setRequest(req);
        httpRequestContext.setResponse(resp);
        final HttpControl httpControl = new HttpControl(SYS_HANDLER.iterator(), httpRequestContext);

        try {
            httpControl.nextHandler();
        } catch (final Exception e) {
            httpRequestContext.setRenderer(new HTTP500Renderer(e));
        }

        result(httpRequestContext);
    }

    /**
     * To http repsonse.
     *
     * @param context {@link HTTPRequestContext}
     * @throws IOException IOException
     */
    public static void result(final HTTPRequestContext context) throws IOException {
        final HttpServletResponse response = context.getResponse();

        if (response.isCommitted()) { // Response sends redirect or error
            return;
        }

        AbstractHTTPResponseRenderer renderer = context.getRenderer();

        if (null == renderer) {
            renderer = new HTTP404Renderer();
        }

        renderer.render(context);
    }
}
