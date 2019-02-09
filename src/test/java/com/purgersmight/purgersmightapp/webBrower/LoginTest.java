package com.purgersmight.purgersmightapp.webBrower;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.models.User;
import com.purgersmight.purgersmightapp.services.UserService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.purgersmight.purgersmightapp.repositories.LoginPage;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

    @LocalServerPort
    private int port;

    public String baseUrl = "http://localhost:";
    public ChromeDriver driver;

    @Autowired
    private UserService userService;

    @Before
    public void setup(){
        baseUrl+=port;
        System.out.println(baseUrl);
        System.setProperty("webdriver.chrome.driver", "/Users/PaulO/Downloads/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(baseUrl);
    }

    @After
    public void tearDown(){
        userService.removeAllUsers();
    }

    @Test
    public void loginSuccess() throws InterruptedException {
        User user = new User("Angie1", "123456");
        userService.addUser(user);
        LoginPage.username(driver).sendKeys("Angie1");
        Thread.sleep(3000);
        LoginPage.password(driver).sendKeys("123456");
        Thread.sleep(3000);
        LoginPage.submit(driver).click();
        Thread.sleep(5000);
//        String message = LoginPage.errorMessage(driver).getText();
//        String expectedMessage = "Welcome back";
//        Assert.assertEquals(message,expectedMessage);

    driver.quit();
    }
}
