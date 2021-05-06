package com.testvagrant.ekam.atoms.mobile;

import com.google.inject.Inject;
import com.testvagrant.ekam.commons.SystemProperties;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;
import org.openqa.selenium.By;
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
    this.wait = buildFluentWait(Duration.ofSeconds(30)); // Default Timeout
    this.touchAction =
        SystemProperties.TARGET.equalsIgnoreCase("ios")
            ? new IOSTouchAction(driver)
            : new AndroidTouchAction(driver);
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

  public MobileElement getElement() {
    try {
      wait.atMost(Duration.ofSeconds(5)).until(() -> driver.findElement(locator) != null);
      return driver.findElement(locator);
    } catch (Exception ex) {
      throw new RuntimeException("Unable to find Element: " + locator);
    }
  }

  public void tap() {
    touchAction.tap(ElementOption.element(getElement())).perform();
  }

  public void longPress() {
    touchAction
        .longPress(ElementOption.element(getElement()))
        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
        .perform();
  }

  private <T> void waitUntilCondition(ExpectedCondition<T> webElementExpectedCondition) {
    wait.until(() -> webElementExpectedCondition.apply(driver) != null);
  }

  private ConditionFactory buildFluentWait(Duration duration) {
    return Awaitility.await().atMost(duration).ignoreExceptions();
  }
}
