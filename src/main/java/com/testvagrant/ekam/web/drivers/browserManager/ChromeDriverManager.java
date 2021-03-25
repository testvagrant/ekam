package com.testvagrant.ekam.web.drivers.browserManager;

import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.Target;
import com.testvagrant.ekam.web.drivers.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ChromeDriverManager extends DriverManager {

    @Override
    public void setup() {
        WebDriverManager.chromedriver().setup();
    }

    @Override
    public WebDriver setupDriver() {
        setup();
        return launchDriver(new ChromeDriver(browserOptions()));
    }

    @Override
    public void terminateDriver() {
        driver.get().quit();
    }

    @Override
    public ChromeOptions browserOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        enableHeadless(chromeOptions);
        return chromeOptions;
    }

    private void enableHeadless(ChromeOptions chromeOptions) {
        if(SystemProperties.TARGET.equals(Target.HEADLESS)) {
            chromeOptions.setHeadless(true);
            chromeOptions.addArguments(headlessArguments());
        }
    }

    @Override
    public WebDriver get() {
        setupDriver();
        return driver.get();
    }
}
