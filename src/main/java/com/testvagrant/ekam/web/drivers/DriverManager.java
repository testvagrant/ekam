package com.testvagrant.ekam.web.drivers;

import com.google.inject.Provider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Arrays;
import java.util.List;

public abstract class DriverManager implements Driver, Provider<WebDriver> {

    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @Override
    public void setup() {
        throw new UnsupportedOperationException();
    }

    @Override
    public WebDriver getDriver() {
        return driver.get();
    }

    public List<String> headlessArguments() {
        return Arrays.asList("disable-dev-shm-usage",
                "disable-gpu",
                "no-sandbox",
                "user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36");
    }

    protected WebDriver launchDriver(RemoteWebDriver webDriver) {
        driver.set(webDriver);
        driver.get().manage().window().maximize();
        return driver.get();
    }
}
