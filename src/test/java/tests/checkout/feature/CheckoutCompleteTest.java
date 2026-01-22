package tests.checkout.feature;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.Base;
import tests.cart.action.YourCartPage;
import tests.checkout.action.CheckoutPage;
import tests.checkout.ui.CheckoutPageUI;
import tests.inventory.action.InventoryPage;
import tests.login.action.LoginPage;
import tests.login.ui.LoginPageUI;
import vn.shopping.project.utils.WaitElement;
import vn.shopping.project.utils.validation.Validator;

public class CheckoutCompleteTest extends Base {
    CheckoutPage checkoutPage;

    @BeforeMethod
    @Override
    protected void setUp() {
        super.setUp();
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        InventoryPage inventoryPage = new InventoryPage(driver);
        products = inventoryPage.addToCart(5);
        inventoryPage.onclickYourCar();
        YourCartPage yourCartPage = new YourCartPage(driver);
        yourCartPage.clickButtonCheckout();
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterYourInformation("cuong", "nguyen", "123456");
        checkoutPage.onClickButtonFinish();
    }
    @Test
    public void userTestClickButtonBackHome(){
        Assert.assertTrue(Validator.validateElementDisplayed(WaitElement.present(driver, CheckoutPageUI.BACK_HOME_BUTTON, 10)), "The button back home is not displayed.");
        checkoutPage.onClickButtonBackHome();
        Assert.assertTrue(Validator.isTextEqual(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html"), "Unable to switch pages.");

        Assert.assertTrue(Validator.validateElementDisplayed(WaitElement.visible(driver, LoginPageUI.APP_LOGO, 10)), "Logo not displayed!");

        int itemCount = driver.findElements(LoginPageUI.INVENTORY_ITEM).size();
        Assert.assertTrue(Validator.isNumberGreaterThan(itemCount, 0), "No products displayed!");
    }
    @AfterMethod
    @Override
    protected void tearDown() {
        super.tearDown();
    }
}
