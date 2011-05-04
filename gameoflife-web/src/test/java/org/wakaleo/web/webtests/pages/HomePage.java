package org.wakaleo.web.webtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open(String url) {
        driver.get(url);
    }
 
    public EnterGridPage clickOnNewGameLink() {
        clickOn(aLinkCalled("New Game"));
        return new EnterGridPage(driver);
    }


}
