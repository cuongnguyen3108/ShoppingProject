package pages.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;
import vn.shopping.project.models.Product;
import vn.shopping.project.utils.WaitElement;

import java.util.ArrayList;
import java.util.List;

public class AddToCartPage extends BasePage {

    public AddToCartPage(WebDriver driver) {
        super(driver);
    }

    public void onclickYourCar() {
        WaitElement.visible(driver, By.className("shopping_cart_link"), 10).click();
    }

    private List<WebElement> getListElementProduct() {
        return WaitElement.visibleElements(driver, By.xpath("//div[@class='inventory_item']"), 10);
    }

    public List<Product> addToCart(int number) {
        List<WebElement> listProduct = getListElementProduct();
        List<Product> products = new ArrayList<>();
        int count = Math.min(number, listProduct.size());
        for (int i = 0; i < count; i++) {
            Product product = new Product();
            WebElement btnBeforeAddToCartProduct = listProduct.get(i).findElement(By.xpath("./descendant::button"));
            btnBeforeAddToCartProduct.click();
            WaitElement.waitFor(driver, ExpectedConditions.presenceOfNestedElementLocatedBy(listProduct.get(i), By.xpath(".//button[text()='Remove']")), 10);
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
        return products;
    }
}
