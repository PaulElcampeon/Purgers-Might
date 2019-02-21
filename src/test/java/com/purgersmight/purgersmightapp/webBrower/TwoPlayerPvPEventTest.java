package com.purgersmight.purgersmightapp.webBrower;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.models.Avatar;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.pageObjects.LoginPage;
import com.purgersmight.purgersmightapp.services.AvatarService;
import com.purgersmight.purgersmightapp.services.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TwoPlayerPvPEventTest extends BaseTestMultiPlayer {

    @Autowired
    private UserService userService;

    @Autowired
    private AvatarService avatarService;

    @LocalServerPort
    private int portNo;

    @Test
    public void testWidgetWorks_Test1() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver1, 10);

        LoginPage loginPage1 = new LoginPage(driver1, portNo);

        loginPage1.open();

        User user1 = new User("Angie1", "123456");

        userService.addUser(user1);

        Avatar avatar1 = Avatar.getStarterAvatar("Angie1");

        avatarService.addAvatar(avatar1);

        loginPage1.username(driver1).sendKeys("Angie1");

        loginPage1.password(driver1).sendKeys("123456");

        loginPage1.submit(driver1).click();


        LoginPage loginPage2 = new LoginPage(driver2, portNo);

        loginPage2.open();

        User user2 = new User("Angie2", "123456");

        userService.addUser(user2);

        Avatar avatar2 = Avatar.getStarterAvatar("Angie2");

        avatarService.addAvatar(avatar2);

        loginPage2.username(driver2).sendKeys("Angie2");

        loginPage2.password(driver2).sendKeys("123456");

        loginPage2.submit(driver2).click();
//        HomePage homePage = loginPage.getHomePage();
//
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
//
//        homePage.healthBtn(driver1).click();
//
//        homePage.mannaBtn(driver1).click();
//
//        wait.wait(1000);
//
//        String healthText = homePage.healthText(driver1).getText();
//
//        String mannaText = homePage.mannaText(driver1).getText();
//
//        String kenjaPoints = homePage.kenjaPoints(driver1).getText();
//
//        userService.removeAllUsers();
//
//        avatarService.removeAllAvatars();
//
//        assertEquals("100/100", healthText);
//
//        assertEquals("60/60", mannaText);
//
//        assertEquals("0", kenjaPoints);
    }

}
