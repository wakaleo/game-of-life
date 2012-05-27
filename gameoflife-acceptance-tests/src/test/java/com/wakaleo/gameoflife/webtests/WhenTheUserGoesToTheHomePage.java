package com.wakaleo.gameoflife.webtests;

import com.wakaleo.gameoflife.webtests.requirements.GameOfLifeApplication.RunSimulations.RunASimulation;
import com.wakaleo.gameoflife.webtests.steps.PlayerSteps;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Story;
import net.thucydides.core.pages.Pages;
import net.thucydides.junit.runners.ThucydidesRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(ThucydidesRunner.class)
@Story(RunASimulation.class)
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
}
