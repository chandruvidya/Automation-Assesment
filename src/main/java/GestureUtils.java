package com.automation.utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

public final class GestureUtils {

    /*
     * Private constructor because this is a utility class.
     * All methods are static.
     */
    private GestureUtils() {
    }

    /**
     * Swipes from approximately 75% screen height
     * to approximately 25% screen height.
     *
     * This produces a bottom-to-top finger movement,
     * which normally scrolls content downward.
     */
    public static void swipeUp(AppiumDriver driver) {

        Dimension screenSize =
                driver.manage().window().getSize();

        int width = screenSize.getWidth();
        int height = screenSize.getHeight();

        int startX = width / 2;
        int startY = (int) (height * 0.75);

        int endX = width / 2;
        int endY = (int) (height * 0.25);

        PointerInput finger =
                new PointerInput(
                        PointerInput.Kind.TOUCH,
                        "finger"
                );

        Sequence swipe =
                new Sequence(finger, 1);

        swipe.addAction(
                finger.createPointerMove(
                        Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        startX,
                        startY
                )
        );

        swipe.addAction(
                finger.createPointerDown(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        );

        swipe.addAction(
                finger.createPointerMove(
                        Duration.ofMillis(600),
                        PointerInput.Origin.viewport(),
                        endX,
                        endY
                )
        );

        swipe.addAction(
                finger.createPointerUp(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        );

        driver.perform(List.of(swipe));
    }


    /**
     * Swipes from approximately 25% screen height
     * to approximately 75% screen height.
     *
     * This produces a top-to-bottom finger movement,
     * which normally scrolls content upward.
     */
    public static void swipeDown(AppiumDriver driver) {

        Dimension screenSize =
                driver.manage().window().getSize();

        int width = screenSize.getWidth();
        int height = screenSize.getHeight();

        int startX = width / 2;
        int startY = (int) (height * 0.25);

        int endX = width / 2;
        int endY = (int) (height * 0.75);

        PointerInput finger =
                new PointerInput(
                        PointerInput.Kind.TOUCH,
                        "finger"
                );

        Sequence swipe =
                new Sequence(finger, 1);

        swipe.addAction(
                finger.createPointerMove(
                        Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        startX,
                        startY
                )
        );

        swipe.addAction(
                finger.createPointerDown(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        );

        swipe.addAction(
                finger.createPointerMove(
                        Duration.ofMillis(600),
                        PointerInput.Origin.viewport(),
                        endX,
                        endY
                )
        );

        swipe.addAction(
                finger.createPointerUp(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        );

        driver.perform(List.of(swipe));
    }


    /**
     * Performs a long press at the centre of the supplied
     * WebElement.
     *
     * @param driver      Appium driver
     * @param element     element to long press
     * @param durationMs  duration of the long press
     */
    public static void longPress(
            AppiumDriver driver,
            WebElement element,
            int durationMs) {

        if (durationMs <= 0) {
            throw new IllegalArgumentException(
                    "durationMs must be greater than zero"
            );
        }

        /*
         * Get the centre point of the element.
         */
        int centerX =
                element.getRect().getX()
                        + element.getRect().getWidth() / 2;

        int centerY =
                element.getRect().getY()
                        + element.getRect().getHeight() / 2;

        PointerInput finger =
                new PointerInput(
                        PointerInput.Kind.TOUCH,
                        "finger"
                );

        Sequence longPress =
                new Sequence(finger, 1);

        /*
         * Move finger to the centre of the element.
         */
        longPress.addAction(
                finger.createPointerMove(
                        Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        centerX,
                        centerY
                )
        );

        /*
         * Finger touches the screen.
         */
        longPress.addAction(
                finger.createPointerDown(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        );

        /*
         * IMPORTANT:
         * Keep the finger down for the requested duration.
         */
        longPress.addAction(
                new Pause(
                        finger,
                        Duration.ofMillis(durationMs)
                )
        );

        /*
         * Release the finger.
         */
        longPress.addAction(
                finger.createPointerUp(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        );

        driver.perform(List.of(longPress));
    }


    /**
     * Repeatedly performs swipeUp until the element
     * matching the locator becomes visible.
     *
     * @param driver       Appium driver
     * @param locator      element locator
     * @param maxAttempts  maximum number of swipes
     */
    public static void scrollUntilVisible(
            AppiumDriver driver,
            By locator,
            int maxAttempts) {

        if (maxAttempts <= 0) {
            throw new IllegalArgumentException(
                    "maxAttempts must be greater than zero"
            );
        }

        for (int attempt = 0;
             attempt < maxAttempts;
             attempt++) {

            List<WebElement> elements =
                    driver.findElements(locator);

            /*
             * If the element exists and is displayed,
             * scrolling is complete.
             */
            for (WebElement element : elements) {

                if (element.isDisplayed()) {
                    return;
                }
            }

            /*
             * Element not found yet.
             */
            swipeUp(driver);
        }

        /*
         * Perform one final check before failing.
         */
        List<WebElement> elements =
                driver.findElements(locator);

        for (WebElement element : elements) {

            if (element.isDisplayed()) {
                return;
            }
        }

        throw new RuntimeException(
                "Element was not visible after "
                        + maxAttempts
                        + " swipe attempts. Locator: "
                        + locator
        );
    }
}