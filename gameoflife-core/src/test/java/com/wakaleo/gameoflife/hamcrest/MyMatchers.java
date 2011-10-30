package com.wakaleo.gameoflife.hamcrest;

import java.util.Collection;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class MyMatchers {

    @Factory
    public static Matcher<Collection<? extends Object>> hasSize( int expectedSize ) {
        return new HasSizeMatcher(expectedSize);
    }
}
