package tests.cart.ui;

import org.openqa.selenium.By;

public class YourCartPageUI {
    public static final By CART_ITEM = By.xpath("//div[@class=\"cart_item\"]");
    public static final By NAME_PRODUCT_ITEM = By.xpath("./descendant::div[@class=\"inventory_item_name\"]");
    public static final By PRICE_PRODUCT_ITEM =By.xpath("./descendant::div[@class=\"inventory_item_price\"]");
    public static final By DESC_PRODUCT_ITEM = By.xpath("./descendant::div[@class=\"inventory_item_desc\"]");
    public static final By QUANTITY_PRODUCT_ITEM =By.xpath("./descendant::div[@class=\"cart_quantity\"]");
    public static final By LIST_ITEM_PRODUCT_AFTER_REMOVE=By.xpath("//div[@class=\"removed_cart_item\"]");
    public static final By BUTTON_CHECKOUT= By.id("checkout");
    public static final By TITLE_PAGE_CHECKOUT_STEP_ONE= By.className("title");
    public static final By BUTTON_CONTINUE =   By.id("continue-shopping");

}
