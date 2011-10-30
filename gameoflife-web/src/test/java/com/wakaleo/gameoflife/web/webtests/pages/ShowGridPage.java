package com.wakaleo.gameoflife.web.webtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShowGridPage extends WebPage {

    public ShowGridPage(WebDriver driver) {
        super(driver);
    }

    public String[][] getDisplayedGrid() {
        return getTableWithId("grid");
    }

    public ShowGridPage clickOnNextGenerationButton() {
        clickOn(By.id("submit"));
        return new ShowGridPage(driver);
    }


}
