package com.wakaleo.gameoflife.web.webtests.pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends WebPage {

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
