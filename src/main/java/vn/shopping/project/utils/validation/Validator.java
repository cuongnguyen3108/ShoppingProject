package vn.shopping.project.utils.validation;

import org.openqa.selenium.WebElement;

public class Validator {
    public static boolean isTextEqual(String actual, String expected) {
        return actual != null && expected != null && actual.equals(expected);
    }

    public static boolean isNumberEqual(Number actual, Number expected) {
        return actual != null && expected != null &&
                Double.compare(actual.doubleValue(), expected.doubleValue()) == 0;
    }

    public static boolean isNumberGreaterThan(Number actual, Number expected) {
        return actual != null
                && expected != null
                && actual.doubleValue() > expected.doubleValue();
    }

    public static boolean isNumberLessThan(Number actual, Number expected) {
        return actual != null
                && expected != null
                && actual.doubleValue() < expected.doubleValue();
    }

    public static boolean validateElementDisplayed(WebElement element) {
        return element != null && element.isDisplayed();
    }

}
