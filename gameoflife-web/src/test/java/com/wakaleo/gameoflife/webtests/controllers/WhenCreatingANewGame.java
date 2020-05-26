package com.wakaleo.gameoflife.webtests.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;


public class WhenCreatingANewGame {

    GameController controller = null;

    @Before
    public void initializeController() {
        controller = new GameController();
    }

    @Test
    public void anEmptyUniverseShouldBeAddedToTheSession() {
        ModelAndView homeView = controller.newGame();
        assertThat(homeView.getModel().get("universe"), is(not(nullValue())));
    }

    @Test
    public void whenTheUserCreatesTheFirstGenerationAnEmptyUniverseShouldBeAddedToTheSession() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        ModelAndView homeView = controller.firstGeneration(5, 5, request);
        assertThat(homeView.getModel().get("universe"), is(not(nullValue())));
    }

    @Test
    public void whenTheUserCreatesTheFirstGenerationTheUniverseDimensionsShouldBeAddedToTheSession() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        ModelAndView homeView = controller.firstGeneration(3, 5, request);
        assertThat((Integer) homeView.getModel().get("rows"), is(3));
        assertThat((Integer) homeView.getModel().get("columns"), is(5));

    }
}	

