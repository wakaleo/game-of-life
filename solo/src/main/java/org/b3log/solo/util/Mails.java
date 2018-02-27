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

import org.apache.commons.lang.StringUtils;

import java.util.ResourceBundle;

/**
 * Mail utilities.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Jul 20, 2017
 * @since 2.3.0
 */
public final class Mails {

    /**
     * Mail configuration (mail.properties).
     */
    private static final ResourceBundle mailConf = ResourceBundle.getBundle("mail");

    /**
     * Private constructor.
     */
    private Mails() {
    }

    /**
     * Whether user configures the mail.properties.
     *
     * @return {@code true} if user configured, returns {@code false} otherwise
     */
    public static boolean isConfigured() {
        try {
            return StringUtils.isNotBlank(mailConf.getString("mail.user")) &&
                    StringUtils.isNotBlank(mailConf.getString("mail.password")) &&
                    StringUtils.isNotBlank(mailConf.getString("mail.smtp.host")) &&
                    StringUtils.isNotBlank(mailConf.getString("mail.smtp.port"));
        } catch (final Exception e) {
            return false;
        }
    }
}
