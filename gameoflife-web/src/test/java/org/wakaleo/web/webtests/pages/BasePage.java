package org.wakaleo.web.webtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {

    protected WebDriver driver = null;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getText() {
        WebElement body = driver.findElement(By.tagName("body"));
        return body.getText();
    }

    public static By aLinkCalled(String linkText) {
        return By.linkText(linkText);
    }

    public static By aButtonNamed(String name) {
        return By.name(name);
    }

    public void clickOn(By byElementLocator) {
        driver.findElement(byElementLocator).click();
    }


}
