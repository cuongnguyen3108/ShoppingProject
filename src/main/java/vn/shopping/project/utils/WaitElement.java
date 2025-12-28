package vn.shopping.project.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitElement {

    public static WebElement visible(WebDriver d, By b, int s) {
        return waitFor(d, ExpectedConditions.visibilityOfElementLocated(b), s);
    }

    public static WebElement present(WebDriver d, By b, int s) {
        return waitFor(d, ExpectedConditions.presenceOfElementLocated(b), s);
    }

    public static WebElement clickable(WebDriver d, By b, int s) {
        return waitFor(d, ExpectedConditions.elementToBeClickable(b), s);
    }
    public static <T> T waitFor(
            WebDriver driver,
            ExpectedCondition<T> condition,
            int seconds
    ) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(condition);
    }

    public static List<WebElement> visibleElements(
            WebDriver driver,
            By by,
            int seconds
    ) {
        return waitFor(
                driver,
                ExpectedConditions.visibilityOfAllElementsLocatedBy(by),
                seconds
        );
    }

}
