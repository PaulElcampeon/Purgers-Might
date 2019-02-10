package com.purgersmight.purgersmightapp.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CreateAccountPage extends BasePageObject {

    protected String url = "http://localhost:";
    public static WebElement element = null;
    private int portNo;

    public CreateAccountPage(WebDriver driver, int portNo) {
        super(driver);
        this.portNo = portNo;
        url += (portNo + "/create-account");
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

    public static WebElement confirmPassword(WebDriver webDriver) {
        element = webDriver.findElement(By.name("confirmPassword"));
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

    public LoginPage getLoginPage() {
        return new LoginPage(driver, portNo);
    }

//    public static WebElement createAccountLink(WebDriver webDriver){
//
//        element = webDriver.findElement(By.linkText("blah bal blah"));
//
//        return element;
//    }
//
//    public static WebElement errorMessage(WebDriver webDriver){
//
//        element = webDriver.findElement(By.cssSelector("blah bal blah"));
//
//        return element;
//    }
}
