package com.wakaleo.gameoflife.webtests;

import com.wakaleo.gameoflife.webtests.requirements.GameOfLifeApplication.History.ViewHistory;
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
@Story(ViewHistory.class)
public class WhenTheUserViewsGameHistory {

    @Managed
    WebDriver driver;

    @ManagedPages(defaultUrl = "http://localhost:9090")
    public Pages pages;

    @Steps
    PlayerSteps player;

    @Test
    @Pending
    public void theUserShouldBeAbleToAccessGameHistoryFromTheHomePage() {
    }

    @Test
    @Pending
    public void theHistoryPageShouldInitiallyBeEmpty() {
    }

    @Test
    @Pending
    public void whenTheUserPlaysAGameItShouldThenAppearInTheHistoryPage() {
    }

}
