package tests.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;
import pages.login.LoginPage;
import vn.shopping.project.models.Product;
import vn.shopping.project.utils.WaitElement;

import java.util.ArrayList;
import java.util.List;

public class AddToCartMultiCaseTest extends BasePage {

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch!");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "URL mismatch!");

        LoginPage loginPage = new LoginPage(driver);
        String username = "standard_user";
        String password = "secret_sauce";
        loginPage.login(username, password);
    }

    @Test
    public void addToCartSuccessfulOneProduct() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed!");

        WebElement appLogo = WaitElement.clickable(driver, By.className("app_logo"), 10);
        Assert.assertTrue(appLogo.isDisplayed(), "Logo not displayed!");

        List<WebElement> listProduct = WaitElement.visibleElements(driver, By.xpath("//div[@class='inventory_item']"), 10);
        Assert.assertTrue(listProduct.get(0).isDisplayed(), "No products displayed!");
        List<Product> products = new ArrayList<>();

        Product product = new Product();
        WebElement btnBeforeAddToCartProduct = listProduct.get(0).findElement(By.xpath("./descendant::button"));
        btnBeforeAddToCartProduct.click();
        WaitElement.waitFor(driver, ExpectedConditions.presenceOfNestedElementLocatedBy(
                listProduct.get(0),
                By.xpath(".//button[text()='Remove']")
        ), 10);
        WebElement btnAfterAddToCartProduct =
                listProduct.get(0).findElement(By.xpath(".//button"));
        Assert.assertEquals(btnAfterAddToCartProduct.getText().trim(), "Remove", "Unable to add the product.");

        WebElement nameProduct = listProduct.get(0).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name \"]"));
        product.setName(nameProduct.getText());
        WebElement priceProduct = listProduct.get(0).findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
        String priceNumber = priceProduct.getText().replace("$", "");
        product.setPrice(Double.parseDouble(priceNumber));
        WebElement descProduct = listProduct.get(0).findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
        product.setDesc(descProduct.getText());
        int quantityProduct = 1;
        product.setQuantity(quantityProduct);
        products.add(product);
        WebElement totalShoppingCart = WaitElement.visible(driver, By.className("shopping_cart_link"), 10);
        Assert.assertEquals(totalShoppingCart.getText(), products.size() + "", "❌ The total number off products in the cart is incorrect!");
        totalShoppingCart.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "Unable to switch pages.");
        WebElement title = WaitElement.visible(driver, By.xpath("//span[@class=\"title\"]"), 10);
        Assert.assertEquals(title.getText(), "Your Cart", "Unable to switch pages.");
    }

    @Test
    public void addToCartSuccessfulMultipleProducts() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed!");

        WebElement appLogo = WaitElement.clickable(driver, By.className("app_logo"), 10);
        Assert.assertTrue(appLogo.isDisplayed(), "Logo not displayed!");

        List<WebElement> listProduct = WaitElement.visibleElements(driver, By.xpath("//div[@class='inventory_item']"), 10);
        Assert.assertTrue(listProduct.get(0).isDisplayed(), "No products displayed!");
        int quantity = 6;
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < quantity - 1; i++) {
            Product product = new Product();
            WebElement btnBeforeAddToCartProduct = listProduct.get(i).findElement(By.xpath("./descendant::button"));
            btnBeforeAddToCartProduct.click();
            WaitElement.waitFor(driver, ExpectedConditions.presenceOfNestedElementLocatedBy(
                    listProduct.get(i),
                    By.xpath(".//button[text()='Remove']")
            ), 10);
            WebElement btnAfterAddToCartProduct =
                    listProduct.get(i).findElement(By.xpath(".//button"));
            Assert.assertEquals(btnAfterAddToCartProduct.getText().trim(), "Remove", "Unable to add the product.");

            WebElement nameProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name \"]"));
            product.setName(nameProduct.getText());
            WebElement priceProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
            String priceNumber = priceProduct.getText().replace("$", "");
            product.setPrice(Double.parseDouble(priceNumber));
            WebElement descProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
            product.setDesc(descProduct.getText());
            int quantityProduct = 1;
            product.setQuantity(quantityProduct);
            products.add(product);
        }
        WebElement totalShoppingCart = WaitElement.visible(driver, By.className("shopping_cart_link"), 10);
        Assert.assertEquals(totalShoppingCart.getText(), products.size() + "", "❌ The total number off products in the cart is incorrect!");
        totalShoppingCart.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "Unable to switch pages.");
        WebElement title = WaitElement.visible(driver, By.xpath("//span[@class=\"title\"]"), 10);
        Assert.assertEquals(title.getText(), "Your Cart", "Unable to switch pages.");
    }

    @Test
    public void addToCartNoProducts() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed!");

        WebElement appLogo = WaitElement.clickable(driver, By.className("app_logo"), 10);
        Assert.assertTrue(appLogo.isDisplayed(), "Logo not displayed!");

        List<WebElement> listProduct = WaitElement.visibleElements(driver, By.xpath("//div[@class='inventory_item']"), 10);
        Assert.assertTrue(listProduct.get(0).isDisplayed(), "No products displayed!");

        WebElement totalShoppingCart = WaitElement.visible(driver, By.className("shopping_cart_link"), 10);
        Assert.assertEquals(totalShoppingCart.getText(), "", "❌ The total number off products in the cart is incorrect!");

        totalShoppingCart.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html", "Unable to switch pages.");
        WebElement title = WaitElement.visible(driver, By.xpath("//span[@class=\"title\"]"), 10);
        Assert.assertEquals(title.getText(), "Your Cart", "Unable to switch pages.");
    }

    @Test
    public void add5ProductAndRemove3ProductCart() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed!");

        WebElement appLogo = WaitElement.clickable(driver, By.className("app_logo"), 10);
        Assert.assertTrue(appLogo.isDisplayed(), "Logo not displayed!");

        List<WebElement> listProduct = WaitElement.visibleElements(driver, By.xpath("//div[@class='inventory_item']"), 10);
        Assert.assertTrue(listProduct.get(0).isDisplayed(), "No products displayed!");
        int quantityAdd = 5;
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < quantityAdd - 1; i++) {
            Product product = new Product();
            WebElement btnBeforeAddToCartProduct = listProduct.get(i).findElement(By.xpath("./descendant::button"));
            btnBeforeAddToCartProduct.click();
            WaitElement.waitFor(driver, ExpectedConditions.presenceOfNestedElementLocatedBy(
                    listProduct.get(i),
                    By.xpath(".//button[text()='Remove']")
            ), 10);
            WebElement btnAfterAddToCartProduct =
                    listProduct.get(i).findElement(By.xpath(".//button"));
            Assert.assertEquals(btnAfterAddToCartProduct.getText().trim(), "Remove", "Unable to add the product.");

            WebElement nameProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name \"]"));
            product.setName(nameProduct.getText());
            WebElement priceProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
            String priceNumber = priceProduct.getText().replace("$", "");
            product.setPrice(Double.parseDouble(priceNumber));
            WebElement descProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
            product.setDesc(descProduct.getText());
            int quantityProduct = 1;
            product.setQuantity(quantityProduct);
            products.add(product);
        }
        WebElement totalShoppingCartAdd = WaitElement.visible(driver, By.className("shopping_cart_link"), 10);
        Assert.assertEquals(totalShoppingCartAdd.getText(), products.size() + "", "❌ The total number off products in the cart is incorrect!");
        int quantityRemove = 3;
        for (int j = 0; j <= quantityRemove - 1; j++) {
            WebElement btnBeforeAddToCartProduct = listProduct.get(j).findElement(By.xpath("./descendant::button"));
            btnBeforeAddToCartProduct.click();
            WaitElement.waitFor(driver, ExpectedConditions.presenceOfNestedElementLocatedBy(
                    listProduct.get(j),
                    By.xpath(".//button[text()='Add to cart']")
            ), 10);
            WebElement btnAfterAddToCartProduct =
                    listProduct.get(j).findElement(By.xpath(".//button"));
            WebElement nameProduct = listProduct.get(j).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name \"]"));

            Assert.assertEquals(btnAfterAddToCartProduct.getText(), "Add to cart", "Unable to remove the product.");
            products.removeIf(p -> p.getName().equals(nameProduct.getText()));
        }
        WebElement totalShoppingCartAddAfterRemove = WaitElement.visible(driver, By.className("shopping_cart_link"), 10);
        Assert.assertEquals(totalShoppingCartAddAfterRemove.getText(), products.size() + "", "❌ The total number off products in the cart is incorrect!");

        Assert.assertEquals(totalShoppingCartAddAfterRemove.getText(), products.size() + "", "❌ The total number off products in the cart is incorrect!");

    }

    @Test
    public void addToCartSortByNameFromZToA() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed!");

        WebElement appLogo = WaitElement.clickable(driver, By.className("app_logo"), 10);
        Assert.assertTrue(appLogo.isDisplayed(), "Logo not displayed!");

        List<WebElement> listProduct = WaitElement.visibleElements(driver, By.xpath("//div[@class='inventory_item']"), 10);
        Assert.assertTrue(listProduct.get(0).isDisplayed(), "No products displayed!");
        List<Product> productsBeforeSort = new ArrayList<>();

        for (int i = 0; i < productsBeforeSort.size(); i++) {
            Product product = new Product();
            WebElement nameProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name \"]"));
            product.setName(nameProduct.getText());
            WebElement priceProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
            String priceNumber = priceProduct.getText().replace("$", "");
            product.setPrice(Double.parseDouble(priceNumber));
            WebElement descProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
            product.setDesc(descProduct.getText());
            int quantityProduct = 1;
            product.setQuantity(quantityProduct);
            productsBeforeSort.add(product);
        }
        WebElement sortProduct = WaitElement.clickable(driver, By.xpath("//select[@class=\"product_sort_container\"]"), 10);
        Select selectSortProduct = new Select(sortProduct);
        selectSortProduct.selectByValue("za");
        List<Product> productsAfterSort = new ArrayList<>();
        for (int i = 0; i < productsAfterSort.size(); i++) {
            Product product = new Product();
            WebElement nameProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name \"]"));
            product.setName(nameProduct.getText());
            WebElement priceProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
            String priceNumber = priceProduct.getText().replace("$", "");
            product.setPrice(Double.parseDouble(priceNumber));
            WebElement descProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
            product.setDesc(descProduct.getText());
            int quantityProduct = 1;
            product.setQuantity(quantityProduct);
            productsAfterSort.add(product);
        }
        for (int i = 0; i < productsAfterSort.size(); i++) {
            Assert.assertTrue(productsAfterSort.get(i).getPrice() == productsBeforeSort.get(i).getPrice(), "The position remains unchanged.");
            Assert.assertEquals(productsAfterSort.get(i).getName(), productsBeforeSort.get(i).getName(), "The position remains unchanged.");
            Assert.assertEquals(productsAfterSort.get(i).getDesc(), productsBeforeSort.get(i).getDesc(), "The position remains unchanged.");
        }

    }

    @Test
    public void addToCartSortByPriceFromLowToHigh() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed!");

        WebElement appLogo = WaitElement.clickable(driver, By.className("app_logo"), 10);
        Assert.assertTrue(appLogo.isDisplayed(), "Logo not displayed!");

        List<WebElement> listProduct = WaitElement.visibleElements(driver, By.xpath("//div[@class='inventory_item']"), 10);
        Assert.assertTrue(listProduct.get(0).isDisplayed(), "No products displayed!");
        List<Product> productsBeforeSort = new ArrayList<>();

        for (int i = 0; i < productsBeforeSort.size(); i++) {
            Product product = new Product();
            WebElement nameProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name \"]"));
            product.setName(nameProduct.getText());
            WebElement priceProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
            String priceNumber = priceProduct.getText().replace("$", "");
            product.setPrice(Double.parseDouble(priceNumber));
            WebElement descProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
            product.setDesc(descProduct.getText());
            int quantityProduct = 1;
            product.setQuantity(quantityProduct);
            productsBeforeSort.add(product);
        }
        WebElement sortProduct = WaitElement.clickable(driver, By.xpath("//select[@class=\"product_sort_container\"]"), 10);
        Select selectSortProduct = new Select(sortProduct);
        selectSortProduct.selectByValue("lohi");
        List<Product> productsAfterSort = new ArrayList<>();
        for (int i = 0; i < productsAfterSort.size(); i++) {
            Product product = new Product();
            WebElement nameProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name \"]"));
            product.setName(nameProduct.getText());
            WebElement priceProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
            String priceNumber = priceProduct.getText().replace("$", "");
            product.setPrice(Double.parseDouble(priceNumber));
            WebElement descProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
            product.setDesc(descProduct.getText());
            int quantityProduct = 1;
            product.setQuantity(quantityProduct);
            productsAfterSort.add(product);
        }
        for (int i = 0; i < productsAfterSort.size(); i++) {
            Assert.assertTrue(productsAfterSort.get(i).getPrice() == productsBeforeSort.get(i).getPrice(), "The position remains unchanged.");
            Assert.assertEquals(productsAfterSort.get(i).getName(), productsBeforeSort.get(i).getName(), "The position remains unchanged.");
            Assert.assertEquals(productsAfterSort.get(i).getDesc(), productsBeforeSort.get(i).getDesc(), "The position remains unchanged.");
        }

    }

    @Test
    public void addToCartSortByPriceFromHighToLow() {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed!");

        WebElement appLogo = WaitElement.clickable(driver, By.className("app_logo"), 10);
        Assert.assertTrue(appLogo.isDisplayed(), "Logo not displayed!");

        List<WebElement> listProduct = WaitElement.visibleElements(driver, By.xpath("//div[@class='inventory_item']"), 10);
        Assert.assertTrue(listProduct.get(0).isDisplayed(), "No products displayed!");
        List<Product> productsBeforeSort = new ArrayList<>();

        for (int i = 0; i < productsBeforeSort.size(); i++) {
            Product product = new Product();
            WebElement nameProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name \"]"));
            product.setName(nameProduct.getText());
            WebElement priceProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
            String priceNumber = priceProduct.getText().replace("$", "");
            product.setPrice(Double.parseDouble(priceNumber));
            WebElement descProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
            product.setDesc(descProduct.getText());
            int quantityProduct = 1;
            product.setQuantity(quantityProduct);
            productsBeforeSort.add(product);
        }
        WebElement sortProduct = WaitElement.clickable(driver, By.xpath("//select[@class=\"product_sort_container\"]"), 10);
        Select selectSortProduct = new Select(sortProduct);
        selectSortProduct.selectByValue("hilo");
        List<Product> productsAfterSort = new ArrayList<>();
        for (int i = 0; i < productsAfterSort.size(); i++) {
            Product product = new Product();
            WebElement nameProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name \"]"));
            product.setName(nameProduct.getText());
            WebElement priceProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_price\"]"));
            String priceNumber = priceProduct.getText().replace("$", "");
            product.setPrice(Double.parseDouble(priceNumber));
            WebElement descProduct = listProduct.get(i).findElement(By.xpath("./descendant::div[@class=\"inventory_item_desc\"]"));
            product.setDesc(descProduct.getText());
            int quantityProduct = 1;
            product.setQuantity(quantityProduct);
            productsAfterSort.add(product);
        }
        for (int i = 0; i < productsAfterSort.size(); i++) {
            Assert.assertTrue(productsAfterSort.get(i).getPrice() == productsBeforeSort.get(i).getPrice(), "The position remains unchanged.");
            Assert.assertEquals(productsAfterSort.get(i).getName(), productsBeforeSort.get(i).getName(), "The position remains unchanged.");
            Assert.assertEquals(productsAfterSort.get(i).getDesc(), productsBeforeSort.get(i).getDesc(), "The position remains unchanged.");
        }

    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
