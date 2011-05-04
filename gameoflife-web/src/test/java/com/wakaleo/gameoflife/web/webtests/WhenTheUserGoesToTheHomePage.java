package com.wakaleo.gameoflife.web.webtests;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.wakaleo.gameoflife.web.webtests.pages.HomePage;

public class WhenTheUserGoesToTheHomePage {
     
    @Test
    public void homePageShouldDisplayWelcomeMessage() {
        WebDriver driver = new HtmlUnitDriver();
        HomePage homePage = new HomePage(driver);
        homePage.open("http://localhost:9090/");
        assertThat(homePage.getTitle(), is("The Game Of Life"));
        assertThat(homePage.getText(), containsString("Welcome to Conway's Game Of Life"));
    }    
}
