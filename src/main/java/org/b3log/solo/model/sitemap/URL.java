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
package org.b3log.solo.model.sitemap;


import org.b3log.latke.util.Strings;


/**
 * Sitemap URL.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.1.0.0, Sep 22, 2011
 * @since 0.3.1
 */
public final class URL {

    /**
     * Start URL element.
     */
    private static final String START_URL_ELEMENT = "<url>";

    /**
     * End URL element.
     */
    private static final String END_URL_ELEMENT = "</url>";

    /**
     * Start loc element.
     */
    private static final String START_LOC_ELEMENT = "<loc>";

    /**
     * End loc element.
     */
    private static final String END_LOC_ELEMENT = "</loc>";

    /**
     * Start last mod element.
     */
    private static final String START_LAST_MOD_ELEMENT = "<lastmod>";

    /**
     * End last mod element.
     */
    private static final String END_LAST_MOD_ELEMENT = "</lastmod>";

    /**
     * Loc.
     */
    private String loc;

    /**
     * Last mod.
     */
    private String lastMod;

    /**
     * Gets the last modified.
     * 
     * @return last modified
     */
    public String getLastMod() {
        return lastMod;
    }

    /**
     * Sets the last modified with the specified last modified.
     * 
     * @param lastMod the specified modified
     */
    public void setLastMod(final String lastMod) {
        this.lastMod = lastMod;
    }

    /**
     * Gets the loc.
     * 
     * @return loc
     */
    public String getLoc() {
        return loc;
    }

    /**
     * Sets the loc with the specified loc.
     * 
     * @param loc the specified loc
     */
    public void setLoc(final String loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(START_URL_ELEMENT);

        stringBuilder.append(START_LOC_ELEMENT);
        stringBuilder.append(loc);
        stringBuilder.append(END_LOC_ELEMENT);

        if (!Strings.isEmptyOrNull(lastMod)) {
            stringBuilder.append(START_LAST_MOD_ELEMENT);
            stringBuilder.append(lastMod);
            stringBuilder.append(END_LAST_MOD_ELEMENT);
        }

        stringBuilder.append(END_URL_ELEMENT);

        return stringBuilder.toString();
    }
}
