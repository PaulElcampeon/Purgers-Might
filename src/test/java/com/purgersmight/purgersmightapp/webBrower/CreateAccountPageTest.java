package com.purgersmight.purgersmightapp.webBrower;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.pageObjects.CreateAccountPage;
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
public class CreateAccountPageTest extends BaseTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @Autowired
    private AvatarService avatarService;

    @Test
    public void createAccount_success_Test1() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        CreateAccountPage createAccountPage = new CreateAccountPage(driver, port);
        createAccountPage.open();

        userService.removeAllUsers();

        CreateAccountPage.username(driver).sendKeys("Angie1");
        CreateAccountPage.password(driver).sendKeys("123456");
        CreateAccountPage.confirmPassword(driver).sendKeys("123456");
        CreateAccountPage.submit(driver).click();

        LoginPage loginPage = createAccountPage.getLoginPage();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("createdMsg")));

        String createdMsg = loginPage.createdMsg(driver).getText();

        userService.removeAllUsers();
        avatarService.removeAllAvatars();

        assertEquals("Account created successfully.", createdMsg);
    }

    @Test
    public void createAccount_failure_Test2() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        CreateAccountPage createAccountPage = new CreateAccountPage(driver, port);
        createAccountPage.open();

        userService.removeAllUsers();

        CreateAccountPage.username(driver).sendKeys("Angie1");
        CreateAccountPage.password(driver).sendKeys("1234");
        CreateAccountPage.confirmPassword(driver).sendKeys("1234");
        CreateAccountPage.submit(driver).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("errorMsg")));

        String errorMsg = CreateAccountPage.errorMsg(driver).getText();

        userService.removeAllUsers();
        avatarService.removeAllAvatars();

        assertEquals("Invalid username or password.", errorMsg);
    }
}
