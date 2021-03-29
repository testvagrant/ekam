package com.testvagrant.ekam.atoms.mobile.android;

import com.google.inject.Inject;
import com.testvagrant.optimuscloud.entities.MobileDriverDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

abstract class BaseElement {
  @Inject MobileDriverDetails mobileDriverDetails;
  @Inject Wait wait;

  private final AppiumDriver<MobileElement> driver =
      (AppiumDriver<MobileElement>) mobileDriverDetails.getMobileDriver();

  protected By locator;

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
    TouchAction touchAction = new TouchAction(driver);
    touchAction.tap(ElementOption.element(getElement())).perform();
  }

  public void longPress() {
    TouchAction touchAction = new TouchAction(driver);
    touchAction.longPress(ElementOption.element(getElement())).perform();
  }

  public MobileElement getElement() {
    int implicitTimeoutInSeconds = 5;
    Wait<AppiumDriver<MobileElement>> wait =
        new FluentWait<>(driver)
            .ignoring(NoSuchElementException.class)
            .withTimeout(Duration.ofSeconds(implicitTimeoutInSeconds));

    return wait.until(driver -> driver.findElement(locator));
  }
}
