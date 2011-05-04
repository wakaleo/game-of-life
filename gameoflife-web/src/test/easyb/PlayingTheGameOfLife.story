import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.wakaleo.gameoflife.web.webtests.pages.EnterGridPage;
import com.wakaleo.gameoflife.web.webtests.pages.ShowGridPage;
import com.wakaleo.gameoflife.web.webtests.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

tags ["acceptance", "sprint-1"]

before_each "assume that the application is running", {
	given "that the application is up and running", {
		homePage = new HomePage(new HtmlUnitDriver())		
	}
	and "that the user is on the home page", {
		homePage.open "http://localhost:9090/"
	}
}

scenario "An empty grid should produce an empty grid",{
	when "the user chooses to start a new game", {
		newGamePage = homePage.clickOnNewGameLink()
	} 
	then "the user is invited to enter the initial state of the universe", { 
		newGamePage.text.shouldHave "Please seed your universe"				
	}
}

scenario "The user can seed the universe with an initial grid",{
	given "the user is on the new grid page", {
		newGridPage = homePage.clickOnNewGameLink()
	}
	when "that the user clicks on Go without picking any cells", {
        gridDisplayPage = newGridPage.clickOnGoButton()
	}
	then "the application will display an empty universe", { 
		String[][] anEmptyGrid = 
		              [[".", ".", "."],
		               [".", ".", "."],
					   [".", ".", "."]]
		gridDisplayPage.displayedGrid.shouldBe anEmptyGrid
	}
}


/*scenario "An empty grid should produce an empty grid",{
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
	when "that the user clicks on Go without picking any cells", {
        gridDisplayPage = newGridPage.clickOnGoButton()
	}
	then "the application will display an empty universe", { 
		String[][] anEmptyGrid = 
		              [[".", ".", "."],
		               [".", ".", "."],
					   [".", ".", "."]]
		newGridPage.displayedGrid.shouldBe anEmptyGrid
	}
}
*/
scenario "A grid with one cell should produce an empty grid", {
	when "the user activates one cell", {
		newGridPage = homePage.clickOnNewGameLink()
		newGridPage.clickOnCellAt(1,1)
	}
	and "the user clicks on 'Go'", {
		gridDisplayPage = newGridPage.clickOnGoButton()
	}
	and "the user clicks on 'Next Generation'", {
		gridDisplayPage = gridDisplayPage.clickOnNextGenerationButton()	
	}
	then "the application will display an empty universe", { 
		String[][] anEmptyGrid = 
		              [[".", ".", "."],
		               [".", ".", "."],
					   [".", ".", "."]]
		gridDisplayPage.displayedGrid.shouldBe anEmptyGrid
	}
}

scenario "A grid with two cells should produce an empty grid", {
	when "the user activates two cells", {
		newGridPage = homePage.clickOnNewGameLink()
		newGridPage.clickOnCellAt(1,1)
	}
	and "the user clicks on 'Go'", {
		gridDisplayPage = newGridPage.clickOnGoButton()
	}
	and "the user clicks on 'Next Generation'", {
		gridDisplayPage = gridDisplayPage.clickOnNextGenerationButton()	
	}
	then "the application will display an empty universe", { 
		String[][] anEmptyGrid = 
		              [[".", ".", "."],
		               [".", ".", "."],
					   [".", ".", "."]]
		gridDisplayPage.displayedGrid.shouldBe anEmptyGrid
	}
}

scenario "A stable cell set should produce the same set of cells", {
	when "the user activates a set of stable cells", {
		newGridPage = homePage.clickOnNewGameLink()
		newGridPage.clickOnCellAt(0,0)
		newGridPage.clickOnCellAt(0,1)
		newGridPage.clickOnCellAt(1,1)
		newGridPage.clickOnCellAt(1,0)
	}
	and "the user clicks on 'Go'", {
		gridDisplayPage = newGridPage.clickOnGoButton()
	}
	and "the user clicks on 'Next Generation'", {
		gridDisplayPage = gridDisplayPage.clickOnNextGenerationButton()	
	}
	then "the application will display the same cells", {
		String[][] anEmptyGrid = 
		              [["*", "*", "."],
		               ["*", "*", "."],
					   [".", ".", "."]]
		gridDisplayPage.displayedGrid.shouldBe anEmptyGrid
	}
}

scenario "A stable cell set should produce the same set of cells", {
	when "the user activates a set of stable cells"
	and "the user clicks on 'Go'"
	then "the application will display the same cells"
}

scenario "A rotating cell set should produce the rodated set of cells after one generations", {
	when "the user activates a set of rotating cells"
	and "the user clicks on 'Go'"
	then "the application will display the rotated cells"
}
scenario "A rotating cell set should produce the original set of cells after two generations", {
	when "the user activates a set of rotating cells"
	and "the user clicks on 'Go' twice times"
	then "the application will display the original cells"
}