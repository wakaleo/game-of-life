import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import com.wakaleo.gameoflife.web.webtests.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

tags "regression"
scenario "The user can view the home page via a web browser",{
	given "The application is up and running", {
		homePage = new HomePage(new HtmlUnitDriver())		    
	}
	when "The user opens the home page", {
		homePage.open("http://localhost:9090/")	
	}
	then "The browser should display an appropriate title bar", {
	    homePage.title.shouldBe "The Game Of Life"
	} 
	and "The user should see the application welcome message", { 
		homePage.text.shouldHave "Welcome to Conway's Game Of Life"
	}
}