package com.wakaleo.gameoflife.webtests;

import com.wakaleo.gameoflife.webtests.requirements.GameOfLifeApplication;
import com.wakaleo.gameoflife.webtests.steps.PlayerSteps;
import net.thucydides.core.annotations.Feature;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Story;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.pages.Pages;
import net.thucydides.junit.runners.ThucydidesRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(ThucydidesRunner.class)
@WithTag("Starting a new simulation")
public class WhenTheUserEntersAnInitialGrid {

    @Managed(uniqueSession = true)
    WebDriver driver;

    @ManagedPages(defaultUrl = "http://localhost:9090")
    public Pages pages;

    @Steps
    PlayerSteps player;


    final static String[][] EMPTY_GRID
            = new String[][]{{".", ".", "."},
            {".", ".", "."},
            {".", ".", "."}};


    @Test
    public void userShouldBeAbleChooseToCreateANewGameOnTheHomePage() {
        player.opens_home_page();
        player.chooses_to_start_a_new_game();
        player.should_see_a_page_containing_text("Please seed your universe");

    }

    @Test
    public void userShouldBeAbleToSeedAnEmptyGridOnTheNewGamePage() {
        player.opens_home_page();
        player.chooses_to_start_a_new_game();
        player.starts_simulation();
        player.should_see_grid(EMPTY_GRID);
    }

    @Test
    public void theGridDisplayPageShouldContainANextGenerationButton() {
        player.opens_home_page();
        player.chooses_to_start_a_new_game();
        player.starts_simulation();
        player.should_see_a_page_containing_text("Next Generation");
    }

    @Test
    public void userShouldBeAbleToEnterOneLiveCellInTheGrid() {
        player.opens_home_page();
        player.chooses_to_start_a_new_game();
        player.clicks_on_cell_at(1, 1);
        player.starts_simulation();

        String[][] expectedGrid = new String[][]{{".", ".", "."},
                {".", "*", "."},
                {".", ".", "."}};

        player.should_see_grid(expectedGrid);
    }

    @Test
    public void userShouldBeAbleToEnterLiveCellsInTheGrid() {
        player.opens_home_page();
        player.chooses_to_start_a_new_game();
        player.clicks_on_cell_at(0, 0);
        player.clicks_on_cell_at(0, 1);
        player.clicks_on_cell_at(1, 1);
        player.starts_simulation();

        String[][] expectedGrid = new String[][]{{"*", "*", "."},
                {".", "*", "."},
                {".", ".", "."}};

        player.should_see_grid(expectedGrid);
    }


    @Test
    public void theGridPageShouldHaveALinkBackToTheHomePage() {
        player.opens_home_page();
        player.chooses_to_start_a_new_game();
        player.clicks_on_home();
        player.should_see_a_page_containing_text("Welcome to Conway's Game Of Life");
    }
}
