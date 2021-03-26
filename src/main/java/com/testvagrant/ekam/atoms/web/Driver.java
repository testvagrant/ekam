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

public class Driver {

  @Inject WebDriver driver;
  @Inject WebDriverWait webDriverWait;

  public static Driver Driver() {
    return new Driver();
  }

  public void get(String url) {
    driver.get(url);
  }

  public String getUrl() {
    return driver.getCurrentUrl();
  }

  public void deleteCookies() {
    driver.manage().deleteAllCookies();
  }

  public void switchTab() {
    String currentWindowHandle = driver.getWindowHandle();
    Optional<String> tab =
        driver.getWindowHandles().stream()
            .filter(handle -> !handle.equals(currentWindowHandle))
            .findFirst();
    tab.ifPresent(s -> driver.switchTo().window(s));
  }

  public String title() {
    return driver.getTitle();
  }

  private JavascriptExecutor jsDriver() {
    return ((JavascriptExecutor) driver);
  }

  public void switchToDefaultContent() {
    driver.switchTo().defaultContent();
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
    webDriverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameId));
  }

  public void waitForFrameToLoad(Integer index) {
    webDriverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
  }

  public void waitForPageToLoad() {
    waitUntil(() -> this.jsDriver().executeScript("return document.readyState").equals("complete"));
  }

  public void navigateBack() {
    driver.navigate().back();
  }
}
