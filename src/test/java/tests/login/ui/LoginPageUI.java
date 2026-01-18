package tests.login.ui;

import org.openqa.selenium.By;

public class LoginPageUI {
    public static final By USERNAME_FIELD = By.id("user-name");
    public static final By PASSWORD_FIELD = By.id("password");
    public static final By LOGIN_BUTTON   = By.id("login-button");
    public static final By ERROR_MESSAGE  = By.cssSelector("[data-test='error']");
    public static final By INVENTORY_ITEM = By.className("inventory_item");
    public static final By INVENTORY_PRODUCT_SORT =  By.className("product_sort_container");
}
