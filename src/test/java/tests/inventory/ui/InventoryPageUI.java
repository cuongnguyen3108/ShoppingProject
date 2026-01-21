package tests.inventory.ui;

import org.openqa.selenium.By;

public class InventoryPageUI {
    public static final By BUTTON_PRODUCT_ITEM = By.xpath("./descendant::button");
    public static final By NAME_PRODUCT_ITEM = By.xpath("./descendant::div[@class=\"inventory_item_name \"]");
    public static final By PRICE_PRODUCT_ITEM = By.xpath("./descendant::div[@class=\"inventory_item_price\"]");
    public static final By DESC_PRODUCT_ITEM = By.xpath("./descendant::div[@class=\"inventory_item_desc\"]");
    public static final By TOTAL_SHOPPING_CART = By.className("shopping_cart_link");
    public static final By SORT_PRODUCT = By.xpath("//select[@class=\"product_sort_container\"]");
    public static final By CART_TITLE = By.xpath("//span[@class=\"title\"]");
}