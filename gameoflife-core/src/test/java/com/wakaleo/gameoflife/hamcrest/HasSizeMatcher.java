package com.wakaleo.gameoflife.hamcrest;

import java.util.Collection;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class HasSizeMatcher extends TypeSafeMatcher<Collection<? extends Object>> {
    private int expectedSize;

    public HasSizeMatcher(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    public boolean matchesSafely(Collection<? extends Object> collection) {
        return (collection.size() == expectedSize);
    }

    public void describeTo(Description description) {
        description.appendText("a collection of size ").appendValue(expectedSize);
    }
}