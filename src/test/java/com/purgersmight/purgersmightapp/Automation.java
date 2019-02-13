package com.purgersmight.purgersmightapp;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
public class Automation {

    @Test
    public void des() throws InterruptedException {
//        System.setProperty("webdriver.chrome.driver1", "/Users/paul.oladele/Downloads/chromedriver");
        System.setProperty("webdriver.chrome.driver1", "C:\\Users\\PaulO\\Downloads\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("http://www.google.com");

        WebElement searchBox = driver.findElement(By.name("q"));
//        WebElement searchBox = ((ChromeDriver) driver1).findElementById(By.name("q"));
//        WebElement searchBox = ((ChromeDriver) driver1).findElementByCssSelector("#tsf > div:nth-child(2) > div > div.RNNXgb > div > div.a4bIc > input");

        Thread.sleep(3000);
        searchBox.sendKeys("pigs flying");

        WebElement searchButton = driver.findElement(By.name("btnK"));
//        String searchButtonText = searchButton.getText();

//        searchBox.sendKeys(searchButtonText);


        searchButton.click();

//        searchBox.sendKeys("pigs flying", Keys.RETURN);


        WebElement exampleoflinkOrATag = driver.findElement(By.linkText("whatever_the_link_text_is"));

        Thread.sleep(3000);

//        System.out.println(driver1.getTitle());
//        Thread.sleep(3000);
        driver.quit();

        Select tools = new Select(driver.findElement(By.id("tools")));

        List actualList = new ArrayList();

        List<WebElement> mytools = tools.getOptions();//this would be like a drop down list

        mytools.forEach(webElement -> {
            String daata = webElement.getText();
            actualList.add(daata);
        });
    }
}
