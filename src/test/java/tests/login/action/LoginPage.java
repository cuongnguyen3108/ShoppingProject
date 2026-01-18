package tests.login.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import tests.login.ui.LoginPageUI;
import vn.shopping.project.utils.ElementActions;
import vn.shopping.project.utils.WaitElement;

public class LoginPage {
    protected WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        WebElement usernameElement = WaitElement.visible(driver, LoginPageUI.USERNAME_FIELD, 10);
        ElementActions.clearAndType(usernameElement, username);
    }

    public void enterPassword(String password) {
        WebElement passwordElement = WaitElement.visible(driver, LoginPageUI.PASSWORD_FIELD, 10);
        ElementActions.clearAndType(passwordElement, password);
    }

    public void clickLogin() {
        WaitElement.clickable(driver, LoginPageUI.LOGIN_BUTTON, 10).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

}
