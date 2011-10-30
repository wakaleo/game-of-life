package com.wakaleo.gameoflife.web.webtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EnterGridPage extends WebPage {

    public EnterGridPage(WebDriver driver) {
        super(driver);
    }

    public ShowGridPage clickOnGoButton() {
        clickOn(By.id("submit"));
        return new ShowGridPage(driver);
    }

    public void clickOnCellAt(int row, int column) {
        String cellName = "cell_" + row + "_" + column;
        driver.findElement(By.name(cellName)).click();
    }



}
