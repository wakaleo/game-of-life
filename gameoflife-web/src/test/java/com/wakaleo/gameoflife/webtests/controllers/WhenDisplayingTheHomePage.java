package com.wakaleo.gameoflife.webtests.controllers;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class WhenDisplayingTheHomePage {

	@Test
	public void theHomeUrlShouldDisplayTheHomePage() {
		HomePageController controller = new HomePageController();
		ModelAndView homeView = controller.home();
		assertThat(homeView.getViewName(), is("home"));
	}
	
	@Test
	public void theIndexUrlShouldDisplayTheHomePage() {
		HomePageController controller = new HomePageController();
		ModelAndView homeView = controller.index();
		assertThat(homeView.getViewName(), is("home"));
	}
}	

