package com.wakaleo.gameoflife.web.webtests.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("http://localhost:9090/new")
public class EnterGridPage extends GameOfLifePage {

    @FindBy(id="submit")
    WebElement goButton;

    public EnterGridPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnGoButton() {
        clickOn(goButton);
    }

    public void clickOnCellAt(int row, int column) {
        String cellName = "cell_" + row + "_" + column;
        getDriver().findElement(By.name(cellName)).click();
    }


}
