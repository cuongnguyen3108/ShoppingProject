package tests.inventory.feature;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.Base;
import tests.inventory.action.InventoryPage;
import tests.inventory.ui.InventoryPageUI;
import tests.login.action.LoginPage;
import vn.shopping.project.enums.SortCondition;
import vn.shopping.project.models.Product;
import vn.shopping.project.utils.WaitElement;

import java.util.List;

public class InventoryTest extends Base {
    InventoryPage inventoryPage;

    @BeforeMethod
    @Override
    protected void setUp() {
        super.setUp();
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(driver);
    }

    @Test
    public void userTestAddToCartSuccessfulOneProduct() {
        products = inventoryPage.addToCart(1);

        WebElement totalShoppingCart = WaitElement.visible(driver, InventoryPageUI.TOTAL_SHOPPING_CART, 10);
        Assert.assertEquals(totalShoppingCart.getText(), products.size() + "", "❌ The total number off products in the cart is incorrect!");
        inventoryPage.onclickYourCar();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "Unable to switch pages.");
        WebElement title = WaitElement.visible(driver, InventoryPageUI.CART_TITLE, 10);
        Assert.assertEquals(title.getText(), "Your Cart", "Unable to switch pages.");
    }

    @Test
    public void userTestAddToCartSuccessfulMultipleProducts() {
        products = inventoryPage.addToCart(1);

        WebElement totalShoppingCart = WaitElement.visible(driver, InventoryPageUI.TOTAL_SHOPPING_CART, 10);
        Assert.assertEquals(totalShoppingCart.getText(), products.size() + "", "❌ The total number off products in the cart is incorrect!");
        inventoryPage.onclickYourCar();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "Unable to switch pages.");
        WebElement title = WaitElement.visible(driver, InventoryPageUI.CART_TITLE, 10);
        Assert.assertEquals(title.getText(), "Your Cart", "Unable to switch pages.");
    }

    @Test
    public void userTestAddToCartNoProducts() {
        products = inventoryPage.addToCart(0);

        WebElement totalShoppingCart = WaitElement.visible(driver, InventoryPageUI.TOTAL_SHOPPING_CART, 10);
        Assert.assertEquals(totalShoppingCart.getText(), "", "❌ The total number off products in the cart is incorrect!");
        inventoryPage.onclickYourCar();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "Unable to switch pages.");
        WebElement title = WaitElement.visible(driver, InventoryPageUI.CART_TITLE, 10);
        Assert.assertEquals(title.getText(), "Your Cart", "Unable to switch pages.");
    }

    @Test
    public void userTestAdd5ProductAndRemove3ProductCart() {
        products = inventoryPage.addToCart(5);

        WebElement totalShoppingCartAdd = WaitElement.visible(driver, InventoryPageUI.TOTAL_SHOPPING_CART, 10);
        Assert.assertEquals(products.size(), Integer.parseInt(totalShoppingCartAdd.getText()), "❌ The total number off products in the cart is incorrect!");

        inventoryPage.removeProductAddedToCart(products, 3);

        WebElement totalShoppingCartAddAfterRemove = WaitElement.visible(driver, InventoryPageUI.TOTAL_SHOPPING_CART, 10);
        Assert.assertEquals(totalShoppingCartAddAfterRemove.getText(), products.size() + "", "❌ The total number off products in the cart is incorrect!");

    }

    @Test
    public void userTestAdd5ProductAndRemove5ProductCart() {
        products = inventoryPage.addToCart(5);

        WebElement totalShoppingCartAdd = WaitElement.visible(driver, InventoryPageUI.TOTAL_SHOPPING_CART, 10);
        Assert.assertEquals(products.size(), Integer.parseInt(totalShoppingCartAdd.getText()), "❌ The total number off products in the cart is incorrect!");

        inventoryPage.removeProductAddedToCart(products, 5);

        WebElement totalShoppingCartAddAfterRemove = WaitElement.visible(driver, InventoryPageUI.TOTAL_SHOPPING_CART, 10);
        Assert.assertEquals(totalShoppingCartAddAfterRemove.getText(), "", "❌ The total number off products in the cart is incorrect!");
    }


    @Test
    public void userTestAddToCartSortByNameFromZToA() {
        List<Product> productsBeforeSort = inventoryPage.getListProduct();

        inventoryPage.setSortByCondition(SortCondition.NAME_FROM_Z_TO_A);
        List<Product> productsAfterSort = inventoryPage.getListProduct();

        for (int i = 0; i < productsAfterSort.size(); i++) {
            Assert.assertEquals(productsBeforeSort.get(i).getPrice(), productsAfterSort.get(i).getPrice(), "The position remains unchanged.");
            Assert.assertEquals(productsAfterSort.get(i).getName(), productsBeforeSort.get(i).getName(), "The position remains unchanged.");
            Assert.assertEquals(productsAfterSort.get(i).getDesc(), productsBeforeSort.get(i).getDesc(), "The position remains unchanged.");
        }
    }

    @Test
    public void userTestAddToCartSortByPriceFromLowToHigh() {
        List<Product> productsBeforeSort = inventoryPage.getListProduct();

        inventoryPage.setSortByCondition(SortCondition.PRICE_FROM_LOW_TO_HIGH);
        List<Product> productsAfterSort = inventoryPage.getListProduct();

        for (int i = 0; i < productsAfterSort.size(); i++) {
            Assert.assertEquals(productsBeforeSort.get(i).getPrice(), productsAfterSort.get(i).getPrice(), "The position remains unchanged.");
            Assert.assertEquals(productsAfterSort.get(i).getName(), productsBeforeSort.get(i).getName(), "The position remains unchanged.");
            Assert.assertEquals(productsAfterSort.get(i).getDesc(), productsBeforeSort.get(i).getDesc(), "The position remains unchanged.");
        }
    }

    @Test
    public void userTestAddToCartSortByPriceFromHighToLow() {
        List<Product> productsBeforeSort = inventoryPage.getListProduct();

        inventoryPage.setSortByCondition(SortCondition.PRICE_FROM_HIGH_TO_LOW);
        List<Product> productsAfterSort = inventoryPage.getListProduct();

        for (int i = 0; i < productsAfterSort.size(); i++) {
            Assert.assertEquals(productsBeforeSort.get(i).getPrice(), productsAfterSort.get(i).getPrice(), "The position remains unchanged.");
            Assert.assertEquals(productsAfterSort.get(i).getName(), productsBeforeSort.get(i).getName(), "The position remains unchanged.");
            Assert.assertEquals(productsAfterSort.get(i).getDesc(), productsBeforeSort.get(i).getDesc(), "The position remains unchanged.");
        }
    }

    @AfterMethod
    @Override
    protected void tearDown() {
        super.tearDown();
    }
}
