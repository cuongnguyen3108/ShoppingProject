package tests.cart.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import tests.cart.ui.YourCartPageUI;
import tests.inventory.ui.InventoryPageUI;
import vn.shopping.project.models.Product;
import vn.shopping.project.utils.WaitElement;

import java.util.ArrayList;
import java.util.List;

public class YourCartPage {
    WebDriver driver;

    public YourCartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean validateProductsEqual(
            List<Product> actual,
            List<Product> expected
    ) {
        if (actual.size() != expected.size()) {
            return false;
        }
        for (int i = 0; i < actual.size(); i++) {
            Product a = actual.get(i);
            Product e = expected.get(i);

            if (!a.getName().equals(e.getName())
                    || a.getPrice() != e.getPrice()
                    || a.getQuantity() != e.getQuantity()) {

                return false;
            }
        }
        return true;
    }

    public List<Product> getAddedProducts() {
        List<WebElement> listElementProduct = WaitElement.visibleElements(driver, YourCartPageUI.CART_ITEM, 10);
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < listElementProduct.size(); i++) {
            Product product = new Product();
            WebElement nameProduct = listElementProduct.get(i).findElement(YourCartPageUI.NAME_PRODUCT_ITEM);
            product.setName(nameProduct.getText());
            WebElement descProduct = listElementProduct.get(i).findElement(YourCartPageUI.DESC_PRODUCT_ITEM);
            product.setDesc(descProduct.getText());

            WebElement priceProduct = listElementProduct.get(i).findElement(YourCartPageUI.PRICE_PRODUCT_ITEM);
            String priceNumber = priceProduct.getText().replace("$", "");
            product.setPrice(Double.parseDouble(priceNumber));

            WebElement quantityProduct = listElementProduct.get(i).findElement(YourCartPageUI.QUANTITY_PRODUCT_ITEM);
            product.setQuantity(Integer.parseInt(quantityProduct.getText()));
            productList.add(product);
        }
        return productList;
    }

    public void removeProductInShoppingCart(List<Product> products, int count) {
        for (int i = 0; i < count; i++) {
            List<WebElement> listProduct = WaitElement.visibleElements(driver, YourCartPageUI.CART_ITEM, 10);
            WebElement nameProduct = listProduct.get(0).findElement(YourCartPageUI.NAME_PRODUCT_ITEM);
            WebElement btnRemove = listProduct.get(0).findElement(InventoryPageUI.BUTTON_PRODUCT_ITEM);
            products.removeIf(product -> product.getName().equals(nameProduct.getText()));
            btnRemove.click();
        }
    }

    public void clickButtonContinueShopping() {
        WaitElement.clickable(driver, YourCartPageUI.BUTTON_CONTINUE, 15).click();
    }

    public void clickButtonCheckout() {
        WaitElement.clickable(driver, YourCartPageUI.BUTTON_CHECKOUT, 15).click();
    }
}
