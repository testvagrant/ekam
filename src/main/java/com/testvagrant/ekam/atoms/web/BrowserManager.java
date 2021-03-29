package com.testvagrant.ekam.atoms.web;

import com.google.inject.Inject;
import org.awaitility.Awaitility;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Supplier;

public class BrowserManager {

    @Inject
    private WebDriver driver;

    public static BrowserManager BrowserManager() {
        return new BrowserManager();
    }

    public void get(String url) {
        this.driver.get(url);
    }

    public String getUrl() {
        return this.driver.getCurrentUrl();
    }

    public void deleteCookies() {
        this.driver.manage().deleteAllCookies();
    }

    public void switchTab() {
        String currentWindowHandle = this.driver.getWindowHandle();
        Optional<String> tab =
                this.driver.getWindowHandles().stream()
                        .filter(handle -> !handle.equals(currentWindowHandle))
                        .findFirst();
        tab.ifPresent(s -> this.driver.switchTo().window(s));
    }

    public String title() {
        return this.driver.getTitle();
    }

    private JavascriptExecutor jsDriver() {
        return ((JavascriptExecutor) this.driver);
    }

    public void switchToDefaultContent() {
        this.driver.switchTo().defaultContent();
    }

    public void waitUntil(Supplier<Boolean> condition) {
        waitUntil(condition, 30);
    }

    public void waitUntil(Supplier<Boolean> condition, long timeOutInSeconds) {
        try {
            Awaitility.await()
                    .ignoreExceptions()
                    .timeout(Duration.ofSeconds(timeOutInSeconds))
                    .until(condition::get);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void waitForFrameToLoad(String frameId) {
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, 30);
        webDriverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameId));
    }

    public void waitForFrameToLoad(Integer index) {
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, 30);
        webDriverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
    }

    public void waitForPageToLoad() {
        waitUntil(() -> this.jsDriver().executeScript("return document.readyState").equals("complete"));
    }

    public void navigateBack() {
        this.driver.navigate().back();
    }
}
