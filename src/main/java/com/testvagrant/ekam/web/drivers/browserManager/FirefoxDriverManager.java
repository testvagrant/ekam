package com.testvagrant.ekam.web.drivers.browserManager;

import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.Target;
import com.testvagrant.ekam.web.drivers.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxDriverManager extends DriverManager {
    @Override
    public void setup() {
        WebDriverManager.firefoxdriver().setup();
    }

    @Override
    public WebDriver setupDriver() {
        setup();
        return launchDriver(new FirefoxDriver(browserOptions()));
    }

    @Override
    public void terminateDriver() {
        driver.get().quit();
    }

    @Override
    public FirefoxOptions browserOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        enableHeadless(firefoxOptions);
        return firefoxOptions;
    }

    private void enableHeadless(FirefoxOptions firefoxOptions) {
        if(SystemProperties.TARGET.equals(Target.HEADLESS)) {
            firefoxOptions.setHeadless(Boolean.TRUE);
            firefoxOptions.addArguments(headlessArguments());
        }
    }

    @Override
    public WebDriver get() {
        setupDriver();
        return driver.get();
    }
}
