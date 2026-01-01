package pages.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;
import vn.shopping.project.utils.WaitElement;

public class YourCartPage extends BasePage {
    public YourCartPage(WebDriver driver) {
        super(driver);
    }
    public void clickBTNCheckout(){
        WaitElement.clickable(driver, By.id("checkout"), 15).click();
    }
}
