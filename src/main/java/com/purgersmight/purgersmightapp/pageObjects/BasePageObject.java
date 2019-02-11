package com.purgersmight.purgersmightapp.pageObjects;

import org.openqa.selenium.WebDriver;

public class BasePageObject {

    protected WebDriver driver;

    public BasePageObject(WebDriver driver) {

        this.driver = driver;
    }

    protected void openUrl(String url) {

        System.out.printf("%nOpening page %s", url);

        driver.get(url);
    }
}
