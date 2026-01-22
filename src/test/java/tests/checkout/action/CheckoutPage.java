package tests.checkout.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import tests.checkout.ui.CheckoutPageUI;
import vn.shopping.project.models.Product;
import vn.shopping.project.utils.ElementActions;
import vn.shopping.project.utils.NumberUtils;
import vn.shopping.project.utils.WaitElement;

import java.util.List;

public class CheckoutPage {
    protected WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    private void enterFirstName(String firstName) {
        WebElement inputFirstName = WaitElement.visible(driver, CheckoutPageUI.FIRST_NAME_INPUT, 10);
        ElementActions.clearAndType(inputFirstName, firstName);
    }

    private void enterLastName(String lastName) {
        WebElement inputLastName = WaitElement.visible(driver, CheckoutPageUI.LAST_NAME_INPUT, 10);
        ElementActions.clearAndType(inputLastName, lastName);
    }

    private void enterPostalCode(String postalCode) {
        WebElement inputPostalCode = WaitElement.visible(driver, CheckoutPageUI.POSTAL_CODE_INPUT, 10);
        ElementActions.clearAndType(inputPostalCode, postalCode);
    }

    private void clickContinueBTN() {
        WaitElement.clickable(driver, CheckoutPageUI.CONTINUE_BUTTON, 10).click();
    }

    public void enterYourInformation(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        clickContinueBTN();
    }

    public void onClickButtonFinish() {
        WaitElement.clickable(driver, CheckoutPageUI.FINISH_BUTTON, 10).click();
    }

    public void onClickButtonBackHome() {
        WaitElement.clickable(driver, CheckoutPageUI.BACK_HOME_BUTTON, 10).click();
    }

    public void onClickButtonCancel() {
        WaitElement.clickable(driver, CheckoutPageUI.CANCEL_BUTTON, 10).click();
    }

    public double sumPriceProducts(List<Product> products) {
        double sum = 0.0;
        for (Product product : products) {
            sum += product.getPrice();
        }
        return sum;
    }

    public Number getTotalPayableAmount(Number productSubtotal, Number taxAmount) {
        return NumberUtils.toBigDecimal(productSubtotal)
                .add(NumberUtils.toBigDecimal(taxAmount));
    }

}
