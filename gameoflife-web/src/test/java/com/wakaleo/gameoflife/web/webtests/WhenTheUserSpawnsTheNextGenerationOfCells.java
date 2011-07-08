package com.wakaleo.gameoflife.web.webtests;

import static com.wakaleo.gameoflife.web.webtests.pages.WebPage.aLinkCalled;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.wakaleo.gameoflife.web.webtests.pages.EnterGridPage;
import com.wakaleo.gameoflife.web.webtests.pages.HomePage;
import com.wakaleo.gameoflife.web.webtests.pages.ShowGridPage;
import com.wakaleo.gameoflife.web.webtests.pages.WebPage;

public class WhenTheUserSpawnsTheNextGenerationOfCells {
     
    WebDriver driver;

   private EnterGridPage goToNewGamePage() {
       WebDriver driver = new HtmlUnitDriver();
       HomePage homePage = new HomePage(driver);
       String baseUrl = System.getProperty("webtest.home");
       System.out.println("Testing against URL:" + baseUrl );
       homePage.open(baseUrl);
       return homePage.clickOnNewGameLink();
    }

   
   @Test
   public void anEmptyGridShouldProduceAnEmptyGrid() {
       EnterGridPage newGamePage = goToNewGamePage();

       String[][] expectedGrid  = new String[][]  {{".", ".", "."},
                                                   {".", ".", "."},
                                                   {".", ".", "."}};

       ShowGridPage showGridPage = newGamePage.clickOnGoButton(); 
       ShowGridPage nextGenerationPage = showGridPage.clickOnNextGenerationButton();
       assertThat(nextGenerationPage.getDisplayedGrid(), is(expectedGrid));
   }
   
   @Test
   public void aGridWithOneCellShouldProduceAnEmptyGrid() {
       EnterGridPage newGamePage = goToNewGamePage();

       newGamePage.clickOnCellAt(1,1);
       String[][] expectedGrid  = new String[][]  {{".", ".", "."},
                                                   {".", ".", "."},
                                                   {".", ".", "."}};

       ShowGridPage showGridPage = newGamePage.clickOnGoButton(); 
       ShowGridPage nextGenerationPage = showGridPage.clickOnNextGenerationButton();
       assertThat(nextGenerationPage.getDisplayedGrid(), is(expectedGrid));
   }

   @Test
   public void aGridWithTwoCellsShouldProduceAnEmptyGrid() {
       EnterGridPage newGamePage = goToNewGamePage();

       newGamePage.clickOnCellAt(1,1);
       newGamePage.clickOnCellAt(0,1);
       String[][] expectedGrid  = new String[][]  {{".", ".", "."},
                                                   {".", ".", "."},
                                                   {".", ".", "."}};

       ShowGridPage showGridPage = newGamePage.clickOnGoButton(); 
       ShowGridPage nextGenerationPage = showGridPage.clickOnNextGenerationButton();
       assertThat(nextGenerationPage.getDisplayedGrid(), is(expectedGrid));
   }

   @Test
    public void aStableCellSetShouldProduceTheSameSetOfCells() {
        EnterGridPage newGamePage = goToNewGamePage();
        newGamePage.clickOnCellAt(0,0);
        newGamePage.clickOnCellAt(0,1);
        newGamePage.clickOnCellAt(1,0);
        newGamePage.clickOnCellAt(1,1);

        String[][] expectedGrid  = new String[][]  {{"*", "*", "."},
                                                    {"*", "*", "."},
                                                    {".", ".", "."}};

        ShowGridPage showGridPage = newGamePage.clickOnGoButton(); 
        ShowGridPage nextGenerationPage = showGridPage.clickOnNextGenerationButton();
        assertThat(nextGenerationPage.getDisplayedGrid(), is(expectedGrid));
    }
    
    @Test
    public void aRotatingCellSetShouldProduceTheExpectedNewSetOfCells() {
        EnterGridPage newGamePage = goToNewGamePage();
        newGamePage.clickOnCellAt(1,0);
        newGamePage.clickOnCellAt(1,1);
        newGamePage.clickOnCellAt(1,2);

        String[][] expectedGrid  = new String[][]  {{".", "*", "."},
                                                    {".", "*", "."},
                                                    {".", "*", "."}};

        ShowGridPage showGridPage = newGamePage.clickOnGoButton(); 
        ShowGridPage nextGenerationPage = showGridPage.clickOnNextGenerationButton();
        assertThat(nextGenerationPage.getDisplayedGrid(), is(expectedGrid));
    }

    @Test
    public void aRotatingCellSetShouldProduceTheOriginalSetOfCellsAfterTwoGenerations() {
        EnterGridPage newGamePage = goToNewGamePage();
        newGamePage.clickOnCellAt(1,0);
        newGamePage.clickOnCellAt(1,1);
        newGamePage.clickOnCellAt(1,2);

        String[][] expectedGrid  = new String[][]  {{".", ".", "."},
                                                    {"*", "*", "*"},
                                                    {".", ".", "."}};

        ShowGridPage showGridPage = newGamePage.clickOnGoButton(); 
        ShowGridPage nextGenerationPage = showGridPage.clickOnNextGenerationButton();
        nextGenerationPage = nextGenerationPage.clickOnNextGenerationButton();
        assertThat(nextGenerationPage.getDisplayedGrid(), is(expectedGrid));
    }

    @Test
    public void aRotatingCellSetShouldProduceTheOriginalSetOfCellsAfterThreeGenerations() {
        EnterGridPage newGamePage = goToNewGamePage();
        newGamePage.clickOnCellAt(1,0);
        newGamePage.clickOnCellAt(1,1);
        newGamePage.clickOnCellAt(1,2);

        String[][] expectedGrid  = new String[][]  {{".", "*", "."},
                                                    {".", "*", "."},
                                                    {".", "*", "."}};

        ShowGridPage showGridPage = newGamePage.clickOnGoButton(); 
        ShowGridPage nextGenerationPage = showGridPage.clickOnNextGenerationButton();
        nextGenerationPage = nextGenerationPage.clickOnNextGenerationButton();
        nextGenerationPage = nextGenerationPage.clickOnNextGenerationButton();
        assertThat(nextGenerationPage.getDisplayedGrid(), is(expectedGrid));
    }
    @Test
    public void shouldHaveALinkBackToTheHomePage() {
    	WebPage showGridPage = goToNewGamePage().clickOnGoButton(); 
        WebPage homePage = showGridPage.clickOn(aLinkCalled("home"));
        assertThat(homePage.getText(), containsString("Welcome to Conway's Game Of Life"));        
    }    
}
