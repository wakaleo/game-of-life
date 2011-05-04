import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import com.wakaleo.gameoflife.web.webtests.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

tags "acceptance"

before_each "assume that the application is running", {
	given "that the application is up and running", {
		homePage = new HomePage(new HtmlUnitDriver())         
	}
}

scenario "The user can seed the universe with an initial grid",{
    given "that the user is on the home page", {
		homePage.open("http://localhost:9090/");
	}
	when "the user chooses to start a new game", {
		homePage.clickOnNewGameLink()
	} 
	then "the user is invited to enter the initial state of the universe", { 
		homePage.text.shouldHave "Please seed your universe"				
	}
}

scenario "The user can seed the universe with an initial grid",{
	given "that the user has opened the 'new game' page", {
		homePage.open("http://localhost:9090/");
		newGridPage = homePage.clickOnNewGameLink()
	}
	when "the user clicks on the 'Go' button", {
		newGridPage = newGridPage.clickOnGoButton()
	} 
	then "the application will display an empty universe", { 
		String[][] anEmptyGrid = 
		              [[".", ".", "."],
		               [".", ".", "."],
					   [".", ".", "."]]
		newGridPage.displayedGrid.shouldBe anEmptyGrid
	}
}

scenario "The user can seed the universe with an initial grid",{
	given "that the user has opened the 'new game' page", {
		homePage.open("http://localhost:9090/");
		newGridPage = homePage.clickOnNewGameLink()
	}
	when "the user enters a simple set of living cells", {
		newGridPage.clickOnCellAt(0,0)
        newGridPage.clickOnCellAt(0,1)
        newGridPage.clickOnCellAt(1,0)
        newGridPage.clickOnCellAt(1,1)
        gridDisplayPage = newGridPage.clickOnGoButton()
	} 
	then "the application will display a universe containing the correct next generation of cells", { 
		String[][] anEmptyGrid = 
		[["*", "*", "."],
		["*", "*", "."],
		[".", ".", "."]]
		gridDisplayPage.displayedGrid.shouldBe anEmptyGrid
	}
}

