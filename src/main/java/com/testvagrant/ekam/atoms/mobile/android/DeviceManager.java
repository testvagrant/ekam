package com.testvagrant.ekam.atoms.mobile.android;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

import static java.lang.Runtime.getRuntime;

public class DeviceManager {

  @Inject private AppiumDriver<MobileElement> driver;

  @Inject private WebDriverWait webDriverWait;

  public void openDeeplink(String link) {
    driver.get(link);
  }

  public JavascriptExecutor jsDriver() {
    return ((JavascriptExecutor) driver);
  }

  public void scrollToBottomInWeb() {
    jsDriver().executeScript("window.scrollTo(0, document.body.scrollHeight)");
  }

  public void switchToWebView() {
    Set<String> contextHandles = driver.getContextHandles();
    contextHandles.forEach(driver::context);
  }

  public void executeCommand(String command) {
    try {
      Process process = getRuntime().exec(command);
      process.waitFor();
      System.out.println(process.exitValue());
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  public void navigateBack() {
    driver.navigate().back();
  }

  public void hideKeyboard() {
    driver.hideKeyboard();
  }

  public boolean allowPermissionPopup() {
    By allowXpath = By.xpath("//*[@text='Allow' or @name = 'Allow']");
    WebElement acceptElement =
        webDriverWait.until(ExpectedConditions.elementToBeClickable(allowXpath));
    acceptElement.click();
    acceptElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(allowXpath));
    acceptElement.click();
    return true;
  }
}
