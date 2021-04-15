package com.testvagrant.ekam.atoms.mobile;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Set;

public class DeviceDriver {

  @Inject protected AppiumDriver<MobileElement> driver;
  private final Actions actions;

  @Inject
  public DeviceDriver(AppiumDriver<MobileElement> driver) {
    this.driver = driver;
    this.actions = new Actions(driver);
  }

  public void openDeeplink(String link) {
    driver.get(link);
  }

  public void switchToWebView() {
    Set<String> contextHandles = driver.getContextHandles();
    contextHandles.forEach(driver::context);
  }

  public void navigateBack() {
    driver.navigate().back();
  }

  public void hideKeyboard() {
    driver.hideKeyboard();
  }

  public void sendKeys(String value) {
    int valueLength = value.length();
    for (int counter = 0; counter < valueLength; counter++) {
      actions.sendKeys(String.valueOf(value.charAt(counter))).build().perform();
    }
  }
}
