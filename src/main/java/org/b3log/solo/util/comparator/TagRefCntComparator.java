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
package org.b3log.solo.util.comparator;

import org.b3log.solo.model.Tag;
import org.json.JSONObject;

import java.util.Comparator;

/**
 * Tag comparator by reference count descent.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.2, Jul 24, 2014
 */
public final class TagRefCntComparator implements Comparator<JSONObject> {

    /**
     * Package default constructor.
     */
    TagRefCntComparator() {
    }

    @Override
    public int compare(final JSONObject tag1, final JSONObject tag2) {
        try {
            final Integer refCnt1 = tag1.getInt(Tag.TAG_REFERENCE_COUNT);
            final Integer refCnt2 = tag2.getInt(Tag.TAG_REFERENCE_COUNT);

            return refCnt2.compareTo(refCnt1);
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
