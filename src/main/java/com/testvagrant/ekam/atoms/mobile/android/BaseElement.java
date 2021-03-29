package com.testvagrant.ekam.atoms.mobile.android;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

abstract class BaseElement {
  @Inject AppiumDriver<MobileElement> driver;

  protected By locator;
  private final int explicitTimeoutInSeconds = 30;
  private final int implicitTimeoutInSeconds = 5;

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
    Wait<AppiumDriver<MobileElement>> wait =
        new FluentWait<>(driver)
            .ignoring(NoSuchElementException.class)
            .ignoring(ElementNotInteractableException.class)
            .ignoring(TimeoutException.class)
            .ignoring(StaleElementReferenceException.class)
            .ignoring(ElementClickInterceptedException.class)
            .withTimeout(Duration.ofSeconds(implicitTimeoutInSeconds));

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
    Wait<AppiumDriver<MobileElement>> wait =
        new FluentWait<>(driver)
            .ignoring(NoSuchElementException.class)
            .ignoring(ElementNotInteractableException.class)
            .ignoring(StaleElementReferenceException.class)
            .withTimeout(Duration.ofSeconds(explicitTimeoutInSeconds));

    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public void waitUntilInvisible() {
    Wait<AppiumDriver<MobileElement>> wait =
        new FluentWait<>(driver)
            .ignoring(NoSuchElementException.class)
            .ignoring(ElementNotInteractableException.class)
            .ignoring(StaleElementReferenceException.class)
            .withTimeout(Duration.ofSeconds(explicitTimeoutInSeconds));

    wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  public void waitUntilPresent() {
    Wait<AppiumDriver<MobileElement>> wait =
        new FluentWait<>(driver)
            .ignoring(NoSuchElementException.class)
            .ignoring(ElementNotInteractableException.class)
            .ignoring(StaleElementReferenceException.class)
            .withTimeout(Duration.ofSeconds(explicitTimeoutInSeconds));

    wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  public void waitUntilTextToBePresent(String text) {
    Wait<AppiumDriver<MobileElement>> wait =
        new FluentWait<>(driver)
            .ignoring(NoSuchElementException.class)
            .ignoring(ElementNotInteractableException.class)
            .ignoring(StaleElementReferenceException.class)
            .withTimeout(Duration.ofSeconds(explicitTimeoutInSeconds));

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
    Wait<AppiumDriver<MobileElement>> wait =
        new FluentWait<>(driver)
            .ignoring(NoSuchElementException.class)
            .withTimeout(Duration.ofSeconds(implicitTimeoutInSeconds));

    return wait.until(driver -> driver.findElement(locator));
  }
}
