package tests.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.cart.AddToCartPage;
import pages.cart.YourCartPage;
import pages.checkout.CheckoutOverviewPage;
import pages.checkout.CheckoutYourInformationPage;
import tests.Base;
import tests.login.action.LoginPage;
import vn.shopping.project.utils.WaitElement;

public class CheckoutCompleteTest extends Base {
    @BeforeMethod
    @Override
    protected void setUp() {
        super.setUp();
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        AddToCartPage addToCartPage = new AddToCartPage(driver);
        products = addToCartPage.addToCart(5);
        addToCartPage.onclickYourCar();
        YourCartPage yourCartPage = new YourCartPage(driver);
        yourCartPage.clickBTNCheckout();
        CheckoutYourInformationPage yourInformationPage = new CheckoutYourInformationPage(driver);
        yourInformationPage.enterYourInformation("cuong", "nguyen", "123456");
        CheckoutOverviewPage overviewPage=new CheckoutOverviewPage(driver);
        overviewPage.clickBTNFinish();
    }
    @Test
    public void testClickButtonBackHome(){
        WebElement btnBackHome= WaitElement.clickable(driver, By.id("back-to-products"),10);
        Assert.assertTrue(btnBackHome.isDisplayed(),"The button back home is not displayed.");
        btnBackHome.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Unable to switch pages.");

        WebElement appLogo = WaitElement.clickable(driver, By.className("app_logo"), 10);
        Assert.assertTrue(appLogo.isDisplayed(), "Logo not displayed!");

        int itemCount = driver.findElements(By.className("inventory_item")).size();
        Assert.assertTrue(itemCount > 0, "No products displayed!");
    }
    @AfterMethod
    @Override
    protected void tearDown() {
        super.tearDown();
    }
}
