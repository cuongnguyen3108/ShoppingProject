package vn.shopping.project.utils.validation;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ElementValidate {

    public static String validate(
            WebDriver driver,
            WebElement field,
            String fieldName,
            By errorLocator
    ) {
        List<WebElement> errors =
                (field != null) ? field.findElements(errorLocator)
                        : driver.findElements(errorLocator);

        if (!errors.isEmpty()) {
            return "❌ Error " + fieldName + ": '" + errors.get(0).getText() + "'";
        }

        if (field != null) {
            String tag = field.getTagName();

            // input / textarea
            if ("input".equalsIgnoreCase(tag) || "textarea".equalsIgnoreCase(tag)) {

                String value = field.getAttribute("value");

                if (value == null || value.trim().isEmpty()) {
                    return "❌ Error " + fieldName + ": 'This field is required.'";
                }

                return "✅ Field " + fieldName + ": " + value;
            }


            // select (thêm required check)
            if ("select".equalsIgnoreCase(tag)) {
                Select select = new Select(field);
                WebElement option = select.getFirstSelectedOption();
                String value = option.getAttribute("value");

                if (value == null || value.isEmpty()) {
                    return "❌ Error " + fieldName + ": 'This field is required.'";
                }

                return "✅ Field " + fieldName + ": " + option.getText();
            }

            // div hoặc element khác
            return "✅ Field " + fieldName + ": " + field.getText();
        }

        return "✅ Field " + fieldName + ": successfully";
    }
}
