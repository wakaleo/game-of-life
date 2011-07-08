package com.wakaleo.gameoflife.web.webtests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.wakaleo.gameoflife.web.webtests.pages.WebPage;
import com.wakaleo.gameoflife.web.webtests.pages.EnterGridPage;
import com.wakaleo.gameoflife.web.webtests.pages.HomePage;
import com.wakaleo.gameoflife.web.webtests.pages.ShowGridPage;
import static com.wakaleo.gameoflife.web.webtests.pages.WebPage.aLinkCalled;

public class WhenTheUserEntersAnInitialGrid {
     
    WebDriver driver;
    EnterGridPage enterGridPage;
    
    final static String[][] EMPTY_GRID 
        = new String[][] {{".", ".", "."},
                          {".", ".", "."},
                          {".", ".", "."}};

    
   private EnterGridPage goToNewGamePage() {
       WebDriver driver = new HtmlUnitDriver();
       HomePage homePage = new HomePage(driver);
       String baseUrl = System.getProperty("webtest.home");
       homePage.open(baseUrl);
       return homePage.clickOnNewGameLink();
    }

    @Test
    public void userShouldBeAbleChooseToCreateANewGameOnTheHomePage() {
        EnterGridPage newGamePage = goToNewGamePage();
        assertThat(newGamePage.getText(), containsString("Please seed your universe"));
    }
    
    @Test
    public void userShouldBeAbleToSeedAnEmptyGridOnTheNewGamePage() {
        EnterGridPage newGamePage = goToNewGamePage();
        ShowGridPage showGridPage = newGamePage.clickOnGoButton();   
        assertThat(showGridPage.getDisplayedGrid(), is(EMPTY_GRID));
    }

    @Test
    public void theGridDisplayPageShouldContainANextGenerationButton() {
        EnterGridPage newGamePage = goToNewGamePage();
        ShowGridPage showGridPage = newGamePage.clickOnGoButton();   
        assertThat(showGridPage.getText(), containsString("Next Generation"));
    }

    @Test
    public void userShouldBeAbleToEnterOneLiveCellInTheGrid() {
        EnterGridPage newGamePage = goToNewGamePage();
        newGamePage.clickOnCellAt(1,1);

        String[][] expectedGrid  = new String[][]  {{".", ".", "."},
                                                    {".", "*", "."},
                                                    {".", ".", "."}};

        ShowGridPage showGridPage = newGamePage.clickOnGoButton();   
        assertThat(showGridPage.getDisplayedGrid(), is(expectedGrid));
    }

    @Test
    public void userShouldBeAbleToEnterLiveCellsInTheGrid() {
        EnterGridPage newGamePage = goToNewGamePage();
        newGamePage.clickOnCellAt(0,0);
        newGamePage.clickOnCellAt(0,1);
        newGamePage.clickOnCellAt(1,1);

        String[][] expectedGrid  = new String[][]  {{"*", "*", "."},
                                                    {".", "*", "."},
                                                    {".", ".", "."}};
        ShowGridPage showGridPage = newGamePage.clickOnGoButton();           
        assertThat(showGridPage.getDisplayedGrid(), is(expectedGrid));
    }
    
    
    @Test
    public void theGridPageShouldHaveALinkBackToTheHomePage() {
        WebPage page = goToNewGamePage();
        page.clickOn(aLinkCalled("home"));
        assertThat(page.getText(), containsString("Welcome to Conway's Game Of Life"));        
    }
}
