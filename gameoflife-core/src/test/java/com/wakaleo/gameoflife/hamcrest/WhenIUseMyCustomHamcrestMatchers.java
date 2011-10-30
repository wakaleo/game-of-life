package com.wakaleo.gameoflife.hamcrest;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static com.wakaleo.gameoflife.hamcrest.MyMatchers.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WhenIUseMyCustomHamcrestMatchers {

	@Test
	public void thehasSizeMatcherShouldMatchACollectionWithExpectedSize() {
		List<String> items = new ArrayList<String>();
		items.add("java");
		assertThat(items, hasSize(1));
	}

	@Test
	public void weCanUseCustomMatchersWithOtherMatchers() {
		List<String> items = new ArrayList<String>();
		items.add("java");
		assertThat(items, allOf(hasSize(1), hasItem("java")));
	}
}

