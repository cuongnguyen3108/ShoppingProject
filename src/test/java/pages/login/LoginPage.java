package pages.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import vn.shopping.project.utils.ElementValidate;
import vn.shopping.project.utils.WaitElement;

public class LoginPage extends BasePage {

    // Locators
    private final By userNameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        WebElement userName = WaitElement.visible(driver, userNameInput, 10);
        ElementValidate.clearAndType(userName, username);
    }

    public void enterPassword(String password) {
        WebElement passwordEl = WaitElement.visible(driver, passwordInput, 10);
        ElementValidate.clearAndType(passwordEl, password);
    }

    public void clickLogin() {
        WebElement loginBtn = WaitElement.clickable(driver, loginButton, 10);
        loginBtn.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}
