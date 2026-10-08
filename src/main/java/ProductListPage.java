package com.automation.pages;

import com.automation.base.BasePage;
import com.automation.utils.GestureUtils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductListPage extends BasePage {

    /*
     * Locator for the item text.
     *
     * ApiDemos uses Android TextView elements for
     * the items on the Views screen.
     */
    private final By listItems =
            By.className("android.widget.TextView");

    public ProductListPage(AppiumDriver driver) {
        super(driver);
    }


    /**
     * Scroll until the requested item becomes visible.
     */
    public void scrollToItem(String itemText) {

        By itemLocator =
                By.xpath(
                        "//android.widget.TextView[@text='"
                                + itemText
                                + "']"
                );

        GestureUtils.scrollUntilVisible(
                driver,
                itemLocator,
                10
        );
    }


    /**
     * Finds an item and performs a 1500 ms long press.
     */
    public void longPressItem(String itemText) {

        By itemLocator =
                By.xpath(
                        "//android.widget.TextView[@text='"
                                + itemText
                                + "']"
                );

        List<WebElement> elements =
                driver.findElements(itemLocator);

        if (elements.isEmpty()) {
            throw new RuntimeException(
                    "Item not found: " + itemText
            );
        }

        WebElement item = elements.get(0);

        if (!item.isDisplayed()) {
            throw new RuntimeException(
                    "Item is not displayed: " + itemText
            );
        }

        GestureUtils.longPress(
                driver,
                item,
                1500
        );
    }


    /**
     * Returns the number of currently visible list items.
     */
    public int getVisibleItemCount() {

        List<WebElement> elements =
                driver.findElements(listItems);

        int visibleCount = 0;

        for (WebElement element : elements) {

            if (element.isDisplayed()) {
                visibleCount++;
            }
        }

        return visibleCount;
    }


    /**
     * Returns the currently visible item texts.
     *
     * This is useful for comparing the list before
     * and after scrolling.
     */
    public List<String> getVisibleItemTexts() {

        List<WebElement> elements =
                driver.findElements(listItems);

        return elements.stream()
                .filter(WebElement::isDisplayed)
                .map(WebElement::getText)
                .filter(text -> !text.isBlank())
                .toList();
    }

    package com.automation.pages;

import com.automation.base.BasePage;
import com.automation.utils.GestureUtils;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

    public class ProductListPage extends BasePage {

        private final By listItems =
                By.className("android.widget.TextView");


        public ProductListPage(AppiumDriver driver) {
            super(driver);
        }


        /**
         * Scrolls until the requested item is visible.
         */
        public void scrollToItem(String itemText) {

            By itemLocator =
                    By.xpath(
                            "//android.widget.TextView[@text='"
                                    + itemText
                                    + "']"
                    );

            GestureUtils.scrollUntilVisible(
                    driver,
                    itemLocator,
                    10
            );
        }


        /**
         * Long presses the requested item for 1500 ms.
         */
        public void longPressItem(String itemText) {

            By itemLocator =
                    By.xpath(
                            "//android.widget.TextView[@text='"
                                    + itemText
                                    + "']"
                    );

            List<WebElement> elements =
                    driver.findElements(itemLocator);

            if (elements.isEmpty()) {
                throw new RuntimeException(
                        "Item not found: " + itemText
                );
            }

            WebElement item = elements.get(0);

            GestureUtils.longPress(
                    driver,
                    item,
                    1500
            );
        }


        /**
         * Performs one upward swipe.
         */
        public void swipeUp() {
            GestureUtils.swipeUp(driver);
        }


        /**
         * Returns the number of visible list items.
         */
        public int getVisibleItemCount() {

            List<WebElement> elements =
                    driver.findElements(listItems);

            int count = 0;

            for (WebElement element : elements) {

                if (element.isDisplayed()
                        && !element.getText().isBlank()) {

                    count++;
                }
            }

            return count;
        }


        /**
         * Returns the text of all currently visible items.
         */
        public List<String> getVisibleItemTexts() {

            return driver.findElements(listItems)
                    .stream()
                    .filter(WebElement::isDisplayed)
                    .map(WebElement::getText)
                    .filter(text -> !text.isBlank())
                    .toList();
        }
    }

}