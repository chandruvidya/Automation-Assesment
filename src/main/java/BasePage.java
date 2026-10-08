package com.automation.base;

import io.appium.java_client.AppiumDriver;

public class BasePage {

    protected final AppiumDriver driver;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
    }
}