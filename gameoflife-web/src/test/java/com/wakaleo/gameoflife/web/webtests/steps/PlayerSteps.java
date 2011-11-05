package com.wakaleo.gameoflife.web.webtests.steps;

import com.wakaleo.gameoflife.web.webtests.pages.EnterGridPage;
import com.wakaleo.gameoflife.web.webtests.pages.GameOfLifePage;
import com.wakaleo.gameoflife.web.webtests.pages.HomePage;
import com.wakaleo.gameoflife.web.webtests.pages.ShowGridPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.AnyPage;
import net.thucydides.core.pages.Pages;
import org.hamcrest.Matcher;

import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class PlayerSteps {

    private Pages pages;

    @Step
    public void opens_home_page() {
        onHomePage().open();
    }

    public void should_see_title_of(String expectedTitle) {
        assertThat(currentPage().getTitle()).contains(expectedTitle);
    }

    private HomePage onHomePage() {
        return pages.currentPageAt(HomePage.class);
    }

    private EnterGridPage onEnterGridPage() {
        return pages.currentPageAt(EnterGridPage.class);
    }

    private ShowGridPage onShowGridPage() {
        return pages.currentPageAt(ShowGridPage.class);
    }

    private GameOfLifePage currentPage() {
        return pages.get(GameOfLifePage.class);
    }

    @Step
    public void chooses_to_start_a_new_game() {
        onHomePage().clickOnNewGameLink();
    }

    public void should_see_a_page_containing_text(String expectedText) {
        currentPage().shouldContainText(expectedText);
    }

    public void should_see_grid(String[][] expectedGrid) {
        assertThat(onShowGridPage().getDisplayedGrid()).isEqualTo(expectedGrid);
    }



    public void starts_simulation() {
        onEnterGridPage().clickOnGoButton();
    }

    public void clicks_on_cell_at(int row, int column) {
        onEnterGridPage().clickOnCellAt(row, column);
    }

    public void clicks_on_home() {
        currentPage().clickOnHome();
    }
}
