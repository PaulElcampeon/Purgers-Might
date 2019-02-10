package com.purgersmight.purgersmightapp.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePageObject {
    protected String url = "http://localhost:";
    public static WebElement element = null;

    public LoginPage(WebDriver driver, int portNo) {
        super(driver);
        url += (portNo + "/login");
    }

    public void open() {
        openUrl(url);
    }

    public static WebElement username(WebDriver webDriver) {
        element = webDriver.findElement(By.name("username"));
        return element;
    }

    public static WebElement password(WebDriver webDriver) {
        element = webDriver.findElement(By.name("password"));
        return element;
    }

    public static WebElement submit(WebDriver webDriver) {
        element = webDriver.findElement(By.name("submitButton"));
        return element;
    }

    public static WebElement errorMsg(WebDriver webDriver) {
        element = webDriver.findElement(By.name("errorMsg"));
        return element;
    }

    public static WebElement createdMsg(WebDriver webDriver) {
        element = webDriver.findElement(By.name("createdMsg"));
        return element;
    }

    public static WebElement logoutMsg(WebDriver webDriver) {
        element = webDriver.findElement(By.name("logoutMsg"));
        return element;
    }

//    public static WebElement createAccountLink(WebDriver webDriver){
//
//        element = webDriver.findElement(By.linkText("blah bal blah"));
//
//        return element;
//    }
//
}

