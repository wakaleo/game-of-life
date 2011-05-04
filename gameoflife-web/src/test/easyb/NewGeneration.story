import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import com.wakaleo.gameoflife.web.webtests.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

tags ["acceptance", "regression"]

before_each "assume that the user has seeded the universe", {
	given "that the application is up and running", {
		homePage = new HomePage(new HtmlUnitDriver())         
	}
	and "that the user has opened the 'new game' page", {
		homePage.open("http://localhost:9090/");
		newGridPage = homePage.clickOnNewGameLink()
	}
}

scenario "Generating a new generation of cells",{
	given "that the user enters a stable set of living cells", {
		newGridPage.clickOnCellAt(0,0)
		newGridPage.clickOnCellAt(0,1)
		newGridPage.clickOnCellAt(1,0)
		newGridPage.clickOnCellAt(1,1)
		gridDisplayPage = newGridPage.clickOnGoButton()
	}
	when "the user clicks on 'Next Generation'", {
		gridDisplayPage = gridDisplayPage.clickOnNextGenerationButton()
	} 
	then "the application will display a universe containing the same cells", { 
		String[][] theInitialGrid = 
		[["*", "*", "."],
 		 ["*", "*", "."],
		 [".", ".", "."]]
		gridDisplayPage.displayedGrid.shouldBe theInitialGrid
	}
}

scenario "Generating a new generation of cells (v2)",{
	given "that the user enters a stable set of living cells"
	when "the user clicks on 'Next Generation'"
	then "the application will display a universe containing the same cells"
}

scenario "Generating a new generation of cells",{
	given "that the user enters a set of living cells", {
		newGridPage.clickOnCellAt(0,1)
		newGridPage.clickOnCellAt(1,1)
		newGridPage.clickOnCellAt(2,1)
		gridDisplayPage = newGridPage.clickOnGoButton()
	}
	when "the user clicks on 'Next Generation'", {
		gridDisplayPage = gridDisplayPage.clickOnNextGenerationButton()
	} 
	then "the application will display a universe containing the same cells", { 
		String[][] theExpectedGrid = 
		[[".", ".", "."],
 		 ["*", "*", "*"],
		 [".", ".", "."]]
		gridDisplayPage.displayedGrid.shouldBe theExpectedGrid
	}
}

