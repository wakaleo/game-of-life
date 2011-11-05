package com.wakaleo.gameoflife.web.webtests.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("http://localhost:9090/home")
public class HomePage extends GameOfLifePage {

    @FindBy(linkText = "New Game")
    WebElement newGameLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickOnNewGameLink() {
        newGameLink.click();
    }
}
