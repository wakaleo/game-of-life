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

import java.util.Random;

/**
 * Generate random stuff, <p> currently only support random alpha and digital
 * string, whose length is also random between 8 and 16.
 *
 * @author <a href="mailto:dongxu.wang@acm.org">Dongxu Wang</a>
 * @version 1.0.0.1, Mar 11, 2013
 */
public class Randoms {

    /**
     * String's length should be positive.
     */
    private static final int LEN_LIM = 1;

    /**
     * String's length maximum limit.
     */
    private static final int MAX_LEN = 16;

    /**
     * String's length minimum limit.
     */
    private static final int MIN_LEN = 8;

    /**
     * String's random length.
     */
    private static final int RANDOM_LEN = new Random().nextInt(MAX_LEN - MIN_LEN + LEN_LIM) + MIN_LEN;

    /**
     * Characters set table, can be extended.
     */
    private final char[] table;

    /**
     * String's random seed.
     */
    private final Random random = new Random();

    /**
     * String's random characters buffer.
     */
    private final char[] buf;

    /**
     * Generate a random string with a random length [8, 16].
     */
    public Randoms() {
        this(RANDOM_LEN);
    }

    /**
     * Generate a random string with given length.
     *
     * @param length string's length
     */
    public Randoms(final int length) {
        if (length < LEN_LIM) {
            throw new IllegalArgumentException("length < 1: " + length);
        }
        table = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        buf = new char[length];
    }

    /**
     * Generate next random string, whose length is also random between 8 and
     * 16.
     *
     * @return next random string
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = table[random.nextInt(table.length)];
        }
        return new String(buf);
    }

    /**
     * Generate next random MD5 string.
     *
     * @return next random string with MD5
     */
    public String nextStringWithMD5() {
        return MD5.hash(nextString());
    }
}
