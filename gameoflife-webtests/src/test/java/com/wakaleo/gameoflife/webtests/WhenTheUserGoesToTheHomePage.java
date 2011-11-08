package com.wakaleo.gameoflife.webtests;

import com.wakaleo.gameoflife.webtests.requirements.GameOfLifeApplication.LearnApplication.ViewHomePage;
import com.wakaleo.gameoflife.webtests.steps.PlayerSteps;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Story;
import net.thucydides.core.pages.Pages;
import net.thucydides.junit.runners.ThucydidesRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(ThucydidesRunner.class)
@Story(ViewHomePage.class)
public class WhenTheUserGoesToTheHomePage {

    @Managed
    WebDriver driver;

    @ManagedPages(defaultUrl = "http://localhost:9090")
    public Pages pages;

    @Steps
    PlayerSteps player;

    @Test
    public void homePageShouldDisplayWelcomeMessage() {

        player.opens_home_page();
        player.should_see_title_of("The Game Of Life");
    }


    @Test
    @Pending
    public void homePageShouldDisplayHistoryLink() {
        //player.opens_home_page();
        //player.should_see_a_page_containing_text("History");
    }

}
