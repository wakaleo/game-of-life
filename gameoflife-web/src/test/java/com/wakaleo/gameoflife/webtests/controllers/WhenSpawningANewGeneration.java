package com.wakaleo.gameoflife.webtests.controllers;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;


public class WhenSpawningANewGeneration {

    @Test
    public void whenGeneratingTheNextGenerationAnUpdatedUniverseShouldBeProduced() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        request.setAttribute("rows", "3");
        request.setAttribute("columns", "3");
        GameController controller = new GameController();
        ModelAndView nextView = controller.nextGeneration(3, 3, request);

        assertThat(nextView.getModel().get("universe"), is(not(nullValue())));
    }

    @Test
    public void theIndexUrlShouldDisplayTheHomePage() {
        HomePageController controller = new HomePageController();
        ModelAndView homeView = controller.index();
        assertThat(homeView.getViewName(), is("home"));
    }
}	

