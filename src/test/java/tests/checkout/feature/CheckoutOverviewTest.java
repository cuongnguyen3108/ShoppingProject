package tests.checkout.feature;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.Base;
import tests.cart.action.YourCartPage;
import tests.checkout.action.CheckoutPage;
import tests.checkout.ui.CheckoutPageUI;
import tests.inventory.action.InventoryPage;
import tests.inventory.ui.InventoryPageUI;
import tests.login.action.LoginPage;
import tests.login.ui.LoginPageUI;
import vn.shopping.project.models.Product;
import vn.shopping.project.utils.WaitElement;
import vn.shopping.project.utils.validation.Validator;

import java.util.List;

public class CheckoutOverviewTest extends Base {
    CheckoutPage checkoutPage;
    YourCartPage yourCartPage;
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
        yourCartPage = new YourCartPage(driver);
        yourCartPage.clickButtonCheckout();
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterYourInformation("cuong", "nguyen", "123456");
    }

    @Test
    public void userTestValidProductInformationForTheDesiredOrder() {
        List<Product> productList = yourCartPage.getAddedProducts();
        Assert.assertTrue(yourCartPage.validateProductsEqual(products, productList), "❌ The product list is displayed incorrectly");
        WebElement totalShoppingCart = WaitElement.visible(driver, InventoryPageUI.TOTAL_SHOPPING_CART, 10);
        Assert.assertTrue(Validator.isTextEqual(totalShoppingCart.getText(), products.size() + ""), "❌ The total number off products in the cart is incorrect!");
    }

    @Test
    public void userTestVerifyCheckoutSummaryInformation() {
        WebElement paymentInformation = WaitElement.visible(driver, CheckoutPageUI.PAYMENT_INFORMATION, 10);
        Assert.assertTrue(Validator.isTextEqual(paymentInformation.getText(), "SauceCard #31337"), "Incorrect information displayed");
        WebElement shippingInformation = WaitElement.visible(driver, CheckoutPageUI.SHIPPING_INFORMATION, 10);
        Assert.assertTrue(Validator.isTextEqual(shippingInformation.getText(), "Free Pony Express Delivery!"), "Incorrect information displayed");
        double sumPriceProducts = checkoutPage.sumPriceProducts(products);

        WebElement itemTotal = WaitElement.visible(driver, CheckoutPageUI.ITEM_TOTAL, 10);
        Assert.assertEquals(Double.parseDouble(itemTotal.getText().replace("Item total: $", "").trim()), sumPriceProducts, "The total amount of the products is incorrect.");
        WebElement taxElement = WaitElement.visible(driver, CheckoutPageUI.TAX, 10);
        Assert.assertTrue(taxElement.isDisplayed(), "tax not displayed");
        double tax = Double.parseDouble(taxElement.getText().replace("Tax: $", "").trim());
        WebElement totalElement = WaitElement.visible(driver, CheckoutPageUI.TOTAL, 10);
        double total = Double.parseDouble((totalElement.getText().replace("Total: $", "").trim()));
        Assert.assertTrue(Validator.isNumberEqual(checkoutPage.getTotalPayableAmount(sumPriceProducts, tax), total), "The total is incorrect.");

    }

    @Test
    public void userTestClickButtonCancel() {
        Assert.assertTrue(Validator.validateElementDisplayed(WaitElement.clickable(driver, CheckoutPageUI.CANCEL_BUTTON, 10)), "The button cancel is not displayed.");
        checkoutPage.onClickButtonCancel();
        Assert.assertTrue(Validator.isTextEqual(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html"), "Unable to switch pages.");

        Assert.assertTrue(Validator.validateElementDisplayed(WaitElement.clickable(driver, LoginPageUI.APP_LOGO, 10)), "Logo not displayed!");

        int itemCount = driver.findElements(LoginPageUI.INVENTORY_ITEM).size();
        Assert.assertTrue(Validator.isNumberGreaterThan(itemCount, 0), "No products displayed!");
    }

    @Test
    public void userTestClickButtonFinish() {
        Assert.assertTrue(Validator.validateElementDisplayed(WaitElement.clickable(driver, CheckoutPageUI.FINISH_BUTTON, 10)), "The button finish is not displayed.");
        checkoutPage.onClickButtonFinish();
        Assert.assertTrue(Validator.isTextEqual(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-complete.html"), "Unable to switch pages.");

        WebElement titlePage = WaitElement.visible(driver, CheckoutPageUI.CHECKOUT_COMPLETE_TITLE, 10);
        Assert.assertTrue(Validator.isTextEqual(titlePage.getText(), "Checkout: Complete!"), "Unable to switch pages.");
        WebElement completeHeaderPage = WaitElement.visible(driver, CheckoutPageUI.CHECKOUT_COMPLETE_HEADER_PAGE, 10);
        Assert.assertTrue(Validator.isTextEqual(completeHeaderPage.getText(), "Thank you for your order!"), "Incorrect display");
        WebElement completeTextPage = WaitElement.visible(driver, CheckoutPageUI.CHECKOUT_COMPLETE_TEXT_PAGE, 10);
        Assert.assertTrue(Validator.isTextEqual(completeTextPage.getText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!"), "Incorrect display");
    }

    @AfterMethod
    @Override
    protected void tearDown() {
        super.tearDown();
    }
}
