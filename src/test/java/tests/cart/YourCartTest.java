package tests.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.cart.AddToCartPage;
import pages.login.LoginPage;
import tests.Base;
import vn.shopping.project.utils.WaitElement;

import java.util.List;

public class YourCartTest extends Base {
    @BeforeMethod
    @Override
    protected void setUp() {
        super.setUp();
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        AddToCartPage addToCartPage = new AddToCartPage(driver);
        products = addToCartPage.addToCart(4);
        addToCartPage.onclickYourCar();
    }

    @Test
    public void testValidShoppingCartInformation() {
        List<WebElement> listElementProduct = WaitElement.visibleElements(driver, By.xpath("//div[@class=\"cart_item\"]"), 10);
        Assert.assertEquals(products.size(), listElementProduct.size(), "incorrect quantity");
        for (int i = 0; i < products.size(); i++) {
            WebElement nameProduct = listElementProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name\"]"));
            Assert.assertEquals(products.get(i).getName(), nameProduct.getText(), "Product name number " + (i + 1) + " is incorrect.");
            WebElement descProduct = listElementProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
            Assert.assertEquals(products.get(i).getDesc(), descProduct.getText(), "Product desc number " + (i + 1) + " is incorrect.");

            WebElement priceProduct = listElementProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
            Assert.assertEquals("$" + products.get(i).getPrice(), priceProduct.getText(), "Product price number " + (i + 1) + " is incorrect.");

            WebElement quantityProduct = listElementProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"cart_quantity\"]"));
            Assert.assertEquals("" + products.get(i).getQuantity(), quantityProduct.getText(), "Product quantity number " + (i + 1) + " is incorrect.");
        }
        WebElement totalShoppingCart = WaitElement.visible(driver, By.className("shopping_cart_link"), 10);
        Assert.assertEquals(totalShoppingCart.getText(), products.size() + "", "❌ The total number off products in the cart is incorrect!");
    }

    @Test
    public void testRemoveAllProductFromShoppingCart() {
        int size=products.size();
        for (int i = 0; i < size; i++) {
            List<WebElement> listElementProductBeforeRemove = WaitElement.visibleElements(driver, By.xpath("//div[@class=\"cart_item\"]"), 10);
            Assert.assertTrue(products.size() == listElementProductBeforeRemove.size(), "incorrect quantity");
            WebElement nameProduct = listElementProductBeforeRemove.get(0).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name\"]"));
            WebElement btnRemove = listElementProductBeforeRemove.get(0).findElement(By.xpath("./descendant::button"));
            products.removeIf(product -> product.getName().equals(nameProduct.getText()));
            btnRemove.click();
        }
        WebElement listElementProductAfterRemove = WaitElement.present(driver, By.xpath("//div[@class=\"removed_cart_item\"]"), 10);
        Assert.assertEquals(listElementProductAfterRemove.getText(), "", "incorrect quantity");
        WebElement totalShoppingCar = WaitElement.visible(driver, By.className("shopping_cart_link"), 10);
        Assert.assertEquals(totalShoppingCar.getText(), "", "❌ The total number off products in the cart is incorrect!");
    }

    @Test
    public void testRemoveProductFromShoppingCart() {
        List<WebElement> listElementProductBeforeRemove = WaitElement.visibleElements(driver, By.xpath("//div[@class=\"cart_item\"]"), 10);
        Assert.assertTrue(products.size() == listElementProductBeforeRemove.size(), "incorrect quantity");

        WebElement nameProduct = listElementProductBeforeRemove.get(0).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name\"]"));
        WebElement btnRemove = listElementProductBeforeRemove.get(0).findElement(By.xpath("./descendant::button"));
        products.removeIf(product -> product.getName().equals(nameProduct.getText()));
        btnRemove.click();

        List<WebElement> listElementProductAfterRemove = WaitElement.visibleElements(driver, By.xpath("//div[@class=\"cart_item\"]"), 10);
        Assert.assertTrue(listElementProductAfterRemove.get(0).isDisplayed(), "incorrect quantity");
        WebElement totalShoppingCar = WaitElement.visible(driver, By.className("shopping_cart_link"), 10);
        Assert.assertTrue(Integer.parseInt(totalShoppingCar.getText()) == products.size(), "❌ The total number off products in the cart is incorrect!");
        for (int i = 0; i < products.size(); i++) {
            WebElement nameProductAfterRemove = listElementProductAfterRemove.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name\"]"));
            Assert.assertEquals(products.get(i).getName(), nameProductAfterRemove.getText(), "Product name number " + (i + 1) + " is incorrect.");
            WebElement descProductAfterRemove = listElementProductAfterRemove.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
            Assert.assertEquals(products.get(i).getDesc(), descProductAfterRemove.getText(), "Product desc number " + (i + 1) + " is incorrect.");

            WebElement priceProductAfterRemove = listElementProductAfterRemove.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
            Assert.assertEquals("$" + products.get(i).getPrice(), priceProductAfterRemove.getText(), "Product price number " + (i + 1) + " is incorrect.");

            WebElement quantityProductAfterRemove = listElementProductAfterRemove.get(i).findElement(By.xpath("./descendant::div[@class=\"cart_quantity\"]"));
            Assert.assertEquals("" + products.get(i).getQuantity(), quantityProductAfterRemove.getText(), "Product quantity number " + (i + 1) + " is incorrect.");
        }
    }

    @Test
    public void testClickButtonCheckout() {
        WebElement btnCheckout = WaitElement.clickable(driver, By.id("checkout"), 15);
        Assert.assertTrue(btnCheckout.isDisplayed(), "button checkout not displayed");
        btnCheckout.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html", "Unable to switch pages.");
        WebElement titlePage = WaitElement.visible(driver, By.className("title"), 10);
        Assert.assertEquals(titlePage.getText(), "Checkout: Your Information", "Unable to switch pages.");
    }

    @Test
    public void testClickButtonContinueShopping() {
        WebElement btnContinueShopping = WaitElement.clickable(driver, By.id("continue-shopping"), 15);
        Assert.assertTrue(btnContinueShopping.isDisplayed(), "button continue shopping not displayed");

        btnContinueShopping.click();

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
