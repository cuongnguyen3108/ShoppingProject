package pages.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;
import vn.shopping.project.utils.WaitElement;

public class CheckoutOverviewPage extends BasePage {
    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public void clickBTNFinish() {
        WaitElement.clickable(driver, By.id("finish"), 10).click();
    }
}
