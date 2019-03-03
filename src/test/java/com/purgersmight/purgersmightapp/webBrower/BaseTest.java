package com.purgersmight.purgersmightapp.webBrower;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp() {
        System.out.println("Setting up driver");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriverMac");
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        System.out.println("Closing driver");
        driver.quit();
    }
}
