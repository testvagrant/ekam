package com.testvagrant.ekam.atoms.mobile;

import com.google.inject.Inject;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class EkamMobileElement {

  private final AppiumDriver<MobileElement> driver;
  private final ConditionFactory wait;
  private By locator;
  private final TouchAction<?> touchAction;

  @Inject
  public EkamMobileElement(AppiumDriver<MobileElement> driver) {
    this.driver = driver;
    this.wait = buildFluentWait(Duration.ofSeconds(30)); // TODO: Read default timeout from config
    this.touchAction = new TouchAction<>(driver);
  }

  public String getTextValue() {
    return getElement().getText();
  }

  public EkamMobileElement locate(By locator) {
    this.locator = locator;
    return this;
  }

  public String getAttributeValue(String attribute) {
    return getElement().getAttribute(attribute);
  }

  @Screenshot // TODO: Take screenshot for specific actions
  public void click() {
    waitUntilPresent();
    wait.until(
        () -> {
          getElement().click();
          return true;
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
    waitUntilCondition(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public void waitUntilInvisible() {
    waitUntilCondition(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  public void waitUntilPresent() {
    waitUntilCondition(ExpectedConditions.presenceOfElementLocated(locator));
  }

  private void waitUntilCondition(ExpectedCondition<?> webElementExpectedCondition) {
    wait.until(() -> webElementExpectedCondition.apply(driver) != null);
  }

  public void waitUntilTextToBePresent(String text) {
    waitUntilCondition(ExpectedConditions.textToBePresentInElementLocated(locator, text));
  }

  public void waitUntilTextToBePresent() {
    wait.until(() -> !getTextValue().trim().isEmpty());
  }

  public void waitUntilTextNotToBe(String text) {
    wait.until(
        () -> {
          String textValue = getTextValue();
          return !textValue.contains(text);
        });
  }

  // TODO: Add global support for wait duration
  public MobileElement getElement() {
    wait.until(() -> driver.findElement(locator) != null);
    return driver.findElement(locator);
  }

  public void tap(MobileElement mobileElement) {
    touchAction.tap(ElementOption.element(getElement())).perform();
  }

  public void longPress() {
    AndroidTouchAction touchAction = new AndroidTouchAction(driver);
    touchAction
        .longPress(ElementOption.element(getElement()))
        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
        .perform();
  }

  private ConditionFactory buildFluentWait(Duration duration) {
    return Awaitility.await()
        .atMost(duration)
        .ignoreException(NoSuchElementException.class)
        .ignoreException(ElementNotInteractableException.class)
        .ignoreException(TimeoutException.class)
        .ignoreException(StaleElementReferenceException.class)
        .ignoreException(ElementClickInterceptedException.class);
  }
}
