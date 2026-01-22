package tests.checkout.ui;

import org.openqa.selenium.By;

public class CheckoutPageUI {
    public static final By FIRST_NAME_INPUT = By.id("first-name");
    public static final By LAST_NAME_INPUT = By.id("last-name");
    public static final By POSTAL_CODE_INPUT = By.id("postal-code");
    public static final By CONTINUE_BUTTON = By.id("continue");
    public static final By CANCEL_BUTTON =By.id("cancel");
    public static final By ERROR_MSG =By.xpath("//h3");
    public static final By FINISH_BUTTON = By.id("finish");
    public static final By BACK_HOME_BUTTON = By.id("back-to-products");
    public static final By CHECKOUT_COMPLETE_TITLE = By.className("title");
    public static final By CHECKOUT_COMPLETE_HEADER_PAGE =  By.xpath("//h2[@class=\"complete-header\"]");
    public static final By CHECKOUT_COMPLETE_TEXT_PAGE =   By.xpath("//div[@class=\"complete-text\"]");
    public static final By PAYMENT_INFORMATION =   By.xpath("//div[@data-test=\"payment-info-value\"]");
    public static final By SHIPPING_INFORMATION = By.xpath("//div[@data-test=\"shipping-info-value\"]") ;
    public static final By ITEM_TOTAL =   By.className("summary_subtotal_label");
    public static final By TAX=  By.className("summary_tax_label");
    public static final By TOTAL =  By.className("summary_total_label") ;


}
