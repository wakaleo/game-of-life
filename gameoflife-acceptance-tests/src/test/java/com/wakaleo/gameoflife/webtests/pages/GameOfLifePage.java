package com.wakaleo.gameoflife.webtests.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("http://localhost:9090/gameoflife")
public class GameOfLifePage extends PageObject {

    @FindBy(linkText = "home")
    WebElement homeLink;

    public GameOfLifePage(WebDriver driver) {
        super(driver);
    }

    public void clickOnHome() {
        homeLink.click();
    }
}
