package com.purgersmight.purgersmightapp.webBrower;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.pageObjects.HomePage;
import com.purgersmight.purgersmightapp.pageObjects.LoginPage;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomePageTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AvatarService avatarService;

    @LocalServerPort
    private int portNo;

    @Test
    public void restoreHealthAndManna_testingButtonsWorkAsExpected_Test1() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        LoginPage loginPage = new LoginPage(driver, portNo);

        loginPage.open();

        User user = new User("Angie1", "123456");

        userService.addUser(user);

        Avatar avatar = Avatar.getStarterAvatar("Angie1");

        avatar.getHealth().setRunning(50);

        avatar.getManna().setRunning(40);

        avatar.setKenjaPoints(2);

        avatarService.addAvatar(avatar);

        loginPage.username(driver).sendKeys("Angie1");

        loginPage.password(driver).sendKeys("123456");

        loginPage.submit(driver).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("level")));

        HomePage.healthBtn(driver).click();

        Thread.sleep(1000);

        HomePage.mannaBtn(driver).click();

        Thread.sleep(1000);

        String healthText = HomePage.healthText(driver).getText();

        String mannaText = HomePage.mannaText(driver).getText();

        String kenjaPoints = HomePage.kenjaPoints(driver).getText();

        userService.removeAllUsers();

        avatarService.removeAllAvatars();

        assertEquals("100/100", healthText);

        assertEquals("60/60", mannaText);

        assertEquals("Kenja Points: 0", kenjaPoints);
    }

}
