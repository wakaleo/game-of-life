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

import org.b3log.latke.util.MD5;

import java.util.ResourceBundle;

/**
 * Thumbnail utilities.
 * <p>
 * By using <a href="http://gravatar.com">Gravatar</a> for user thumbnail.
 * </p>
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.0, May 19, 2015
 * @since 0.6.1
 */
public final class Thumbnails {

    /**
     * Gravatar address.
     */
    public static final String GRAVATAR;

    static {
        final ResourceBundle b3log = ResourceBundle.getBundle("b3log");

        GRAVATAR = b3log.getString("gravatar");
    }

    /**
     * Gets the Gravatar URL for the specified email with the specified size.
     *
     * @param email the specified email
     * @param size  the specified size
     * @return the Gravatar URL
     */
    public static String getGravatarURL(final String email, final String size) {
        return Thumbnails.GRAVATAR + MD5.hash(email) + "?s=" + size;
    }

    /**
     * Private constructor.
     */
    private Thumbnails() {
    }
}
