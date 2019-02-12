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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest extends BaseTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @Autowired
    private AvatarService avatarService;

    @Test
    public void login_success_Test1() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        LoginPage loginPage = new LoginPage(driver, port);
        loginPage.open();

        userService.removeAllUsers();

        User user = new User("Angie1", "123456");

        userService.addUser(user);

        avatarService.addAvatar(Avatar.getStarterAvatar("Angie1"));

        LoginPage.username(driver).sendKeys("Angie1");
        LoginPage.password(driver).sendKeys("123456");
        LoginPage.submit(driver).click();

        HomePage homePage = loginPage.getHomePage();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));

        String username = homePage.username(driver).getText();

        String level = homePage.level(driver).getText();

        String kenjaPoints = homePage.kenjaPoints(driver).getText();

        String healthText = homePage.healthText(driver).getText();

        String mannaText = homePage.mannaText(driver).getText();

        String experienceText = homePage.experienceText(driver).getText();

        userService.removeAllUsers();
        avatarService.removeAllAvatars();

        assertEquals("Username: Angie1", username);

        assertEquals("Level: 1", level);

        assertEquals("Kenja Points: 0", kenjaPoints);

        assertEquals("100/100", healthText);

        assertEquals("60/60", mannaText);

        assertEquals("0/100", experienceText);
    }

    @Test
    public void login_error_Test2() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        LoginPage loginPage = new LoginPage(driver, port);
        loginPage.open();

        LoginPage.username(driver).sendKeys("Angie1");
        LoginPage.password(driver).sendKeys("1234");
        LoginPage.submit(driver).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("errorMsg")));

        String errorMsg = LoginPage.errorMsg(driver).getText();

        assertEquals("Invalid username or password.", errorMsg);
    }
}
