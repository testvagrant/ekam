package com.testvagrant.ekam.atoms.mobile.android;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

abstract class BaseElement {
  @Inject private AppiumDriver<MobileElement> driver;
  @Inject private FluentWait<AppiumDriver<MobileElement>> wait;
  private By locator;

  protected void locate(By locator) {
    this.locator = locator;
  }

  public String getTextValue() {
    return getElement().getText();
  }

  public String getAttributeValue(String attribute) {
    return getElement().getAttribute(attribute);
  }

  public void click() {
    wait.until(
        (driver) -> {
          try {
            getElement().click();
            return true;
          } catch (Exception ex) {
            return false;
          }
        });
  }

  public boolean hasAttribute(String attribute) {
    String value = getElement().getAttribute(attribute);
    return value != null;
  }

  public boolean isEnabled() {
    return getElement().isEnabled();
  }

  public void waitUntilDisplayed() {
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public void waitUntilInvisible() {
    wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  public void waitUntilPresent() {
    wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  public void waitUntilTextToBePresent(String text) {
    wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
  }

  public void tap() {
    AndroidTouchAction touchAction = new AndroidTouchAction(driver);
    touchAction.tap(ElementOption.element(getElement())).perform();
  }

  public void longPress() {
    AndroidTouchAction touchAction = new AndroidTouchAction(driver);
    touchAction.longPress(ElementOption.element(getElement())).perform();
  }

  public MobileElement getElement() {
    int implicitTimeoutInSeconds = 5;
    FluentWait<AppiumDriver<MobileElement>> fluentWait =
        wait.withTimeout(Duration.ofSeconds(implicitTimeoutInSeconds));

    return fluentWait.until(driver -> driver.findElement(locator));
  }
}
