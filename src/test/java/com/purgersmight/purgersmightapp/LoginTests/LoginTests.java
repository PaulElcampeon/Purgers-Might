package com.purgersmight.purgersmightapp.LoginTests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.test.context.junit4.SpringRunner;

import com.purgersmight.purgersmightapp.repositories.LoginPage;

@RunWith(SpringRunner.class)
public class LoginTests {

    public String baseUrl = "";
    public ChromeDriver driver;

    @BeforeClass
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "/Users/paul.oladele/Downloads/chromedriver");
        driver = new ChromeDriver();
        driver.get(baseUrl);
    }

    @Test
    public void loginSuccess() throws InterruptedException {
        LoginPage.username(driver).sendKeys("Angie1");
        LoginPage.password(driver).sendKeys("123456");
        LoginPage.submit(driver).click();
        Thread.sleep(5000);
        String message = LoginPage.errorMessage(driver).getText();
        String expectedMessage = "Welcome back";
        Assert.assertEquals(message,expectedMessage);


    }
}
