package org.wakaleo.web.webtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EnterGridPage extends BasePage {

    public EnterGridPage(WebDriver driver) {
        super(driver);
    }

    public ShowGridPage clickOnNextGeneration() {
        clickOn(By.id("submit"));  
        return new ShowGridPage(driver);
    }


}
