package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import vn.shopping.project.models.Product;

import java.util.List;

public class Base {
    protected WebDriver driver;
    protected List<Product> products;

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
