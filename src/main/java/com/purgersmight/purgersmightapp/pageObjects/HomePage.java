package com.purgersmight.purgersmightapp.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePageObject {

    private String url = "http://localhost:";

    public static WebElement element = null;

    private int portNo;

    public HomePage(WebDriver driver, int portNo) {

        super(driver);

        this.portNo = portNo;

        url += (portNo + "/home");
    }

    public void open() {

        openUrl(url);
    }

    public static WebElement username(WebDriver webDriver) {

        element = webDriver.findElement(By.name("username"));

        return element;
    }

    public static WebElement level(WebDriver webDriver) {

        element = webDriver.findElement(By.name("level"));

        return element;
    }

    public static WebElement kenjaPoints(WebDriver webDriver) {

        element = webDriver.findElement(By.name("kenjaPoints"));

        return element;
    }

    public static WebElement healthText(WebDriver webDriver) {

        element = webDriver.findElement(By.name("healthText"));

        return element;
    }

    public static WebElement mannaText(WebDriver webDriver) {

        element = webDriver.findElement(By.name("mannaText"));

        return element;
    }

    public static WebElement experienceText(WebDriver webDriver) {

        element = webDriver.findElement(By.name("experienceText"));

        return element;
    }

    public static WebElement healthBtn(WebDriver webDriver) {

        element = webDriver.findElement(By.name("healthBtn"));

        return element;
    }

    public static WebElement mannaBtn(WebDriver webDriver) {

        element = webDriver.findElement(By.name("mannaBtn"));

        return element;
    }

    public static WebElement pvpBtn(WebDriver webDriver) {

        element = webDriver.findElement(By.name("pvpBtn"));

        return element;
    }

    public static WebElement upgradeSpellBtn(WebDriver webDriver) {

        element = webDriver.findElement(By.name("upgradeSpellBtn"));

        return element;
    }

    public static WebElement bagBtn(WebDriver webDriver) {

        element = webDriver.findElement(By.name("bagBtn"));

        return element;
    }

    public static WebElement armourBtn(WebDriver webDriver) {

        element = webDriver.findElement(By.name("armourBtn"));

        return element;
    }

    public static WebElement spellBookBtn(WebDriver webDriver) {

        element = webDriver.findElement(By.name("spellBookBtn"));

        return element;
    }

    public static WebElement battleReceiptsBtn(WebDriver webDriver) {

        element = webDriver.findElement(By.name("battleReceiptsBtn"));

        return element;
    }

    public static WebElement leaderBoardBtn(WebDriver webDriver) {

        element = webDriver.findElement(By.name("leaderBoardBtn"));

        return element;
    }

    public static WebElement battleStatsBtn(WebDriver webDriver) {

        element = webDriver.findElement(By.name("battleStatsBtn"));

        return element;
    }

    public static WebElement weaponBtn(WebDriver webDriver) {

        element = webDriver.findElement(By.name("weaponBtn"));

        return element;
    }

    public static WebElement warchatBtn(WebDriver webDriver) {

        element = webDriver.findElement(By.name("warchatBtn"));

        return element;
    }

}
