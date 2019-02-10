package com.purgersmight.purgersmightapp.repositories;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    public static WebElement element = null;

    public static WebElement username(WebDriver webDriver){

        element = webDriver.findElement(By.name("username"));

        return element;
    }

    public static WebElement password(WebDriver webDriver){

        element = webDriver.findElement(By.name("password"));

        return element;
    }

    public static WebElement submit(WebDriver webDriver){

        element = webDriver.findElement(By.name("submitButton"));

        return element;
    }

    public static WebElement createAccountLink(WebDriver webDriver){

        element = webDriver.findElement(By.linkText("blah bal blah"));

        return element;
    }

    public static WebElement errorMessage(WebDriver webDriver){

        element = webDriver.findElement(By.cssSelector("blah bal blah"));

        return element;
    }


}
