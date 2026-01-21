package tests.cart.feature;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.Base;
import tests.cart.action.YourCartPage;
import tests.cart.ui.YourCartPageUI;
import tests.inventory.action.InventoryPage;
import tests.inventory.ui.InventoryPageUI;
import tests.login.action.LoginPage;
import tests.login.ui.LoginPageUI;
import vn.shopping.project.models.Product;
import vn.shopping.project.utils.WaitElement;
import vn.shopping.project.utils.validation.Validator;

import java.util.List;

public class YourCartTest extends Base {
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
    }

    @Test
    public void userTestValidShoppingCartInformation() {
        List<Product> productList = yourCartPage.getAddedProducts();
        Assert.assertTrue(yourCartPage.validateProductsEqual(products, productList), "❌ The product list is displayed incorrectly");
        WebElement totalShoppingCart = WaitElement.visible(driver, InventoryPageUI.TOTAL_SHOPPING_CART, 10);
        Assert.assertTrue(Validator.isTextEqual(totalShoppingCart.getText(), products.size() + ""), "❌ The total number off products in the cart is incorrect!");
    }

    @Test
    public void userTestRemoveAllProductFromShoppingCart() {
        yourCartPage.removeProductInShoppingCart(products, products.size());
        WebElement listElementProductAfterRemove = WaitElement.present(driver, YourCartPageUI.LIST_ITEM_PRODUCT_AFTER_REMOVE, 10);
        Assert.assertTrue(Validator.isTextEqual(listElementProductAfterRemove.getText(), ""), "incorrect quantity");
        WebElement totalShoppingCar = WaitElement.visible(driver, InventoryPageUI.TOTAL_SHOPPING_CART, 10);
        Assert.assertTrue(Validator.isTextEqual(totalShoppingCar.getText(), ""), "❌ The total number off products in the cart is incorrect!");
    }

    @Test
    public void userTestRemoveProductFromShoppingCart() {
        yourCartPage.removeProductInShoppingCart(products, 1);
        List<Product> productList = yourCartPage.getAddedProducts();
        Assert.assertTrue(yourCartPage.validateProductsEqual(products, productList), "❌ The product list is displayed incorrectly");
        WebElement totalShoppingCart = WaitElement.visible(driver, InventoryPageUI.TOTAL_SHOPPING_CART, 10);
        Assert.assertTrue(Validator.isTextEqual(totalShoppingCart.getText(), products.size() + ""), "❌ The total number off products in the cart is incorrect!");
    }

    @Test
    public void userTestClickButtonCheckout() {
        Assert.assertTrue(Validator.validateElementDisplayed(WaitElement.clickable(driver, YourCartPageUI.BUTTON_CHECKOUT, 15)), "button checkout not displayed");
        yourCartPage.clickButtonCheckout();
        Assert.assertTrue(Validator.isTextEqual(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html"), "Unable to switch pages.");
        WebElement titlePage = WaitElement.visible(driver, YourCartPageUI.TITLE_PAGE_CHECKOUT_STEP_ONE, 10);
        Assert.assertTrue(Validator.isTextEqual(titlePage.getText(), "Checkout: Your Information"), "Unable to switch pages.");
    }

    @Test
    public void userTestClickButtonContinueShopping() {
        Assert.assertTrue(Validator.validateElementDisplayed(WaitElement.clickable(driver, YourCartPageUI.BUTTON_CONTINUE, 15)), "button continue shopping not displayed");
        yourCartPage.clickButtonContinueShopping();

        Assert.assertTrue(Validator.isTextEqual(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html"), "Unable to switch pages.");

        Assert.assertTrue(Validator.validateElementDisplayed(WaitElement.clickable(driver, LoginPageUI.APP_LOGO, 10)), "Logo not displayed!");

        int itemCount = driver.findElements(LoginPageUI.INVENTORY_ITEM).size();
        Assert.assertTrue(Validator.isNumberGreaterThan(itemCount, 0), "No products displayed!");
    }

    @AfterMethod
    @Override
    protected void tearDown() {
        super.tearDown();
    }
}
