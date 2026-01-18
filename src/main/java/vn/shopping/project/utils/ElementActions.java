package vn.shopping.project.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class ElementActions {

    public static void clearAndType(WebElement element, String text) {

        try {
            // Scroll element vào giữa màn hình
            ((JavascriptExecutor) ((WrapsDriver) element).getWrappedDriver())
                    .executeScript(
                            "arguments[0].scrollIntoView({block:'center'});",
                            element
                    );

            element.click();

        } catch (ElementClickInterceptedException e) {
            // Fallback click bằng JS nếu bị che
            ((JavascriptExecutor) ((WrapsDriver) element).getWrappedDriver())
                    .executeScript("arguments[0].click();", element);
        }

        // Clear chuẩn cho Mac & Windows
        Keys selectAllKey = System.getProperty("os.name").toLowerCase().contains("mac")
                ? Keys.COMMAND
                : Keys.CONTROL;

        element.sendKeys(Keys.chord(selectAllKey, "a"), Keys.DELETE);

        if (text != null && !text.isBlank()) {
            element.sendKeys(text);
        }
    }

    public static void clearAndClickCheckbox(WebElement checkbox) {
        if (checkbox == null) {
            throw new IllegalArgumentException("Checkbox is null");
        }

        // Nếu checkbox đã được check → bỏ check
        if (checkbox.isSelected()) {
            checkbox.click();
        }

        // Click lại để đảm bảo được check
        checkbox.click();
    }

    public static void selectByVisibleText(WebElement selectElement, String text) {

        JavascriptExecutor js =
                (JavascriptExecutor) ((WrapsDriver) selectElement).getWrappedDriver();

        Select select = new Select(selectElement);

        // 🔥 TỰ ĐỘNG GÁN NAME NẾU CHƯA CÓ
        js.executeScript(
                """
                        if (!arguments[0].getAttribute('data-field-name')) {
                            let name =
                                arguments[0].getAttribute('aria-label') ||
                                arguments[0].getAttribute('name') ||
                                arguments[0].getAttribute('id') ||
                                'Interest';
                            arguments[0].setAttribute('data-field-name', name);
                        }
                        """,
                selectElement
        );

        // 🔥 CLEAR select
        if (text == null || text.isBlank()) {
            js.executeScript(
                    """
                            arguments[0].selectedIndex = 0;
                            arguments[0].dispatchEvent(new Event('change'));
                            """,
                    selectElement
            );
            return;
        }

        try {
            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    selectElement
            );

            select.selectByVisibleText(text);

        } catch (ElementClickInterceptedException e) {
            js.executeScript(
                    """
                            const select = arguments[0];
                            const value = arguments[1];
                            for (let option of select.options) {
                                if (option.text.trim() === value.trim()) {
                                    option.selected = true;
                                    select.dispatchEvent(new Event('change'));
                                    break;
                                }
                            }
                            """,
                    selectElement, text
            );
        }
    }

}
