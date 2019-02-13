package com.purgersmight.purgersmightapp.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage1 extends BasePageObject {

    protected String url = "http://localhost:";

    public static WebElement element = null;

    private int portNo;

    public LoginPage1(WebDriver driver, int portNo) {

        super(driver);

        url += (portNo + "/login");

        this.portNo = portNo;
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

    public static WebElement createAccountLink(WebDriver webDriver){

        element = webDriver.findElement(By.linkText("createAccount"));

        return element;
    }

    public HomePage getHomePage() {

        return new HomePage(driver, portNo);
    }

}

