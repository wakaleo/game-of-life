package com.wakaleo.gameoflife.web.webtests.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebPage {

    protected WebDriver driver = null;
    
    public WebPage(WebDriver driver) {
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

    public WebPage clickOn(By byElementLocator) {
        driver.findElement(byElementLocator).click();
        return this;
    }

    public String[][] getTableWithId(String tableId) {        
        
        WebElement gridTable = driver.findElement(By.id(tableId));
        List<WebElement> rows = gridTable.findElements(By.tagName("tr"));
        List<String[]> tableContents = new ArrayList<String[]>();
        int numberOfColumns = 0;
        for (WebElement row : rows) {
            String[] rowContents = getCellsIn(row);
            numberOfColumns = rowContents.length;
            tableContents.add(rowContents);
        }
        return tableContentsAsAnArray(tableContents, numberOfColumns);
    }

    private String[][] tableContentsAsAnArray(List<String[]> tableContents,
            int numberOfColumns) {
        int numberOfRows = tableContents.size();
        return tableContents.toArray(new String[numberOfRows][numberOfColumns]);
    }

    private String[] getCellsIn(WebElement row) {
        List<WebElement> cells = row.findElements(By.tagName("td"));
        List<String> rowContents = new ArrayList<String>();
        for (WebElement cell : cells) {
            String cellValue = cell.getText();
            rowContents.add(cellValue);
        }
        return rowContents.toArray(new String[rowContents.size()]);
    }
       
}
