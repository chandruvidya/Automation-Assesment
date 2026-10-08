package com.automation.tests;

import com.automation.pages.ProductListPage;
import com.automation.utils.GestureUtils;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ProductListTest {

    private AppiumDriver driver;
    private ProductListPage productListPage;


    @BeforeMethod
    public void setUp() throws MalformedURLException {

        /*
         * Appium server URL.
         */
        URL appiumServer =
                new URL("http://127.0.0.1:4723");

        /*
         * In a real project, capabilities should ideally
         * be kept in a configuration class or properties file.
         *
         * These capabilities assume the ApiDemos.apk is
         * available on the test machine.
         */

        /*
         * Replace this section with the UiAutomator2Options
         * configuration appropriate to your installed
         * Appium Java Client version.
         */

        io.appium.java_client.android.options
                .UiAutomator2Options options =
                new io.appium.java_client.android.options
                        .UiAutomator2Options();

        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");

        options.setDeviceName("Android Emulator");

        /*
         * Use the path to your ApiDemos.apk.
         */
        options.setApp(
                System.getProperty("user.dir")
                        + "/apps/ApiDemos.apk"
        );

        options.setNoReset(false);

        driver =
                new AppiumDriver(
                        appiumServer,
                        options
                );

        productListPage =
                new ProductListPage(driver);

        /*
         * Navigate from the ApiDemos home screen
         * to Views.
         */
        WebElement views =
                driver.findElement(
                        By.xpath(
                                "//android.widget.TextView[@text='Views']"
                        )
                );

        views.click();
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }


    /**
     * Test 1:
     * Scroll until a specific item is visible.
     */
    @Test
    public void testScrollToSpecificItem() {

        String itemName = "Animation";

        productListPage.scrollToItem(itemName);

        WebElement item =
                driver.findElement(
                        By.xpath(
                                "//android.widget.TextView[@text='"
                                        + itemName
                                        + "']"
                        )
                );

        Assert.assertTrue(
                item.isDisplayed(),
                "Expected item '" + itemName
                        + "' should be displayed after scrolling."
        );
    }


    /**
     * Test 2:
     *
     * Long press an item for 1500 milliseconds.
     *
     * For ApiDemos, long pressing a simple Views list
     * item does not necessarily display a context menu.
     *
     * Therefore this test demonstrates the gesture itself
     * and verifies that the item remains available/displayed.
     *
     * If your ApiDemos version displays a context menu,
     * replace the final assertion with an assertion for
     * that menu.
     */
    @Test
    public void testLongPressRevealMenu() {

        String itemName = "Animation";

        productListPage.scrollToItem(itemName);

        productListPage.longPressItem(itemName);

        /*
         * Verify the item is still present after the
         * long-press gesture.
         */
        WebElement item =
                driver.findElement(
                        By.xpath(
                                "//android.widget.TextView[@text='"
                                        + itemName
                                        + "']"
                        )
                );

        Assert.assertTrue(
                item.isDisplayed(),
                "Item should remain displayed after long press."
        );
    }


    /**
     * Test 3:
     *
     * Capture visible items before scrolling.
     * Perform one swipe.
     * Capture items again.
     * Verify that items are visible and at least one
     * item differs.
     */
    @Test
    public void testItemCountBeforeAndAfterScroll() {

        int countBefore =
                productListPage.getVisibleItemCount();

        List<String> itemsBefore =
                productListPage.getVisibleItemTexts();

        /*
         * One gesture directly through GestureUtils.
         *
         * The assignment permits the test to perform
         * the gesture, but the test does not call
         * findElement(). ProductListPage owns the
         * element operations.
         */
        GestureUtils.swipeUp(driver);

        int countAfter =
                productListPage.getVisibleItemCount();

        List<String> itemsAfter =
                productListPage.getVisibleItemTexts();

        Assert.assertTrue(
                countAfter > 0,
                "At least one item should be visible after scrolling."
        );

        boolean differentItems =
                itemsBefore.stream()
                        .anyMatch(item ->
                                !itemsAfter.contains(item));

        Assert.assertTrue(
                differentItems,
                "At least one visible item should differ after scrolling."
        );

        System.out.println(
                "Visible items before scroll: "
                        + countBefore
        );

        System.out.println(
                "Visible items after scroll: "
                        + countAfter
        );

        System.out.println(
                "Items before: "
                        + itemsBefore
        );

        System.out.println(
                "Items after: "
                        + itemsAfter
        );
    }
    /**
     * Performs one upward swipe on the product list.
     */
    public void swipeUp() {
        GestureUtils.swipeUp(driver);
    }
    Then your third test becomes:

    @Test
    public void testItemCountBeforeAndAfterScroll() {

        int countBefore =
                productListPage.getVisibleItemCount();

        List<String> itemsBefore =
                productListPage.getVisibleItemTexts();

        productListPage.swipeUp();

        int countAfter =
                productListPage.getVisibleItemCount();

        List<String> itemsAfter =
                productListPage.getVisibleItemTexts();

        Assert.assertTrue(
                countAfter > 0,
                "Visible item count after scrolling should be greater than zero."
        );

        boolean differentItems =
                itemsBefore.stream()
                        .anyMatch(item ->
                                !itemsAfter.contains(item));

        Assert.assertTrue(
                differentItems,
                "At least one item should differ after scrolling."
        );

        System.out.println(
                "Count before = " + countBefore
        );

        System.out.println(
                "Count after = " + countAfter
        );

        System.out.println(
                "Items before = " + itemsBefore
        );

        System.out.println(
                "Items after = " + itemsAfter
        );
    }

    public void swipeUp() {
        GestureUtils.swipeUp(driver);
    }
    and then changing the third test from:

            GestureUtils.swipeUp(driver);
    to:

            productListPage.swipeUp();
    Also, the test should not call findElement().
}
