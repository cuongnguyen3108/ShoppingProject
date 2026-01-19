package tests.inventory.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import tests.inventory.ui.InventoryPageUI;
import tests.login.ui.LoginPageUI;
import vn.shopping.project.enums.SortCondition;
import vn.shopping.project.models.Product;
import vn.shopping.project.utils.WaitElement;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage {
    WebDriver driver;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<Product> addToCart(int quantity) {
        List<WebElement> listProduct = WaitElement.visibleElements(driver, LoginPageUI.INVENTORY_ITEM, 10);
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            Product product = new Product();
            WebElement btnAddToCartProduct = listProduct.get(i).findElement(InventoryPageUI.ADD_TO_CART_BUTTON);
            btnAddToCartProduct.click();

            WebElement nameProduct = listProduct.get(i).findElement(InventoryPageUI.NAME_PRODUCT_ITEM);
            product.setName(nameProduct.getText());
            WebElement priceProduct = listProduct.get(i).findElement(InventoryPageUI.PRICE_PRODUCT_ITEM);
            String priceNumber = priceProduct.getText().replace("$", "");
            product.setPrice(Double.parseDouble(priceNumber));
            WebElement descProduct = listProduct.get(i).findElement(InventoryPageUI.DESC_PRODUCT_ITEM);
            product.setDesc(descProduct.getText());
            int quantityProduct = 1;
            product.setQuantity(quantityProduct);
            products.add(product);
        }
        return products;
    }

    public void onclickYourCar() {
        WaitElement.visible(driver, InventoryPageUI.TOTAL_SHOPPING_CART, 10).click();
    }

    public void removeProductAddedToCart(List<Product> products, int quantity) {
        List<WebElement> listProduct = WaitElement.visibleElements(driver, LoginPageUI.INVENTORY_ITEM, 10);
        for (int j = 0; j < quantity; j++) {
            WebElement btnRemoveProductAddedToCart = listProduct.get(j).findElement(InventoryPageUI.ADD_TO_CART_BUTTON);
            btnRemoveProductAddedToCart.click();

            WebElement nameProduct = listProduct.get(j).findElement(By.xpath("./descendant::div[@class=\"inventory_item_name \"]"));

            products.removeIf(p -> p.getName().equals(nameProduct.getText()));
        }
    }

    public void setSortByCondition(SortCondition condition) {
        WebElement sortProduct = WaitElement.clickable(driver, InventoryPageUI.SORT_PRODUCT, 10);
        Select selectSortProduct = new Select(sortProduct);
        selectSortProduct.selectByValue(condition.getDropdownText());
    }

    public List<Product> getListProduct() {
        List<WebElement> listProduct = WaitElement.visibleElements(driver, LoginPageUI.INVENTORY_ITEM, 10);
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            Product product = new Product();
            WebElement nameProduct = listProduct.get(i).findElement(InventoryPageUI.NAME_PRODUCT_ITEM);
            product.setName(nameProduct.getText());
            WebElement priceProduct = listProduct.get(i).findElement(InventoryPageUI.PRICE_PRODUCT_ITEM);
            String priceNumber = priceProduct.getText().replace("$", "");
            product.setPrice(Double.parseDouble(priceNumber));
            WebElement descProduct = listProduct.get(i).findElement(InventoryPageUI.DESC_PRODUCT_ITEM);
            product.setDesc(descProduct.getText());
            int quantityProduct = 1;
            product.setQuantity(quantityProduct);
            products.add(product);
        }
        return products;
    }
}
