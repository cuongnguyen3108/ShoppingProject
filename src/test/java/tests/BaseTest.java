package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.login.LoginPage;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    protected void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterMethod
    protected void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
