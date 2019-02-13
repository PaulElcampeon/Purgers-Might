package com.purgersmight.purgersmightapp.webBrower;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTestMultiPlayer {
    protected WebDriver driver1;
    protected WebDriver driver2;


    @Before
    public void setUp() {
        System.out.println("Setting up driver");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver1 = new ChromeDriver();
        driver2 = new ChromeDriver();
    }

    @After
    public void tearDown() {
        System.out.println("Closing driver");
        driver1.quit();
        driver2.quit();

    }
}
