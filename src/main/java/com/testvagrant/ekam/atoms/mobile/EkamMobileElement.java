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

  public boolean isPresent(Duration duration) {
    try {
      waitUntilCondition(ExpectedConditions.presenceOfElementLocated(locator), duration);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  public boolean isDisplayed() {
    try {
      return getElement().isDisplayed();
    } catch (Exception ex) {
      return false;
    }
  }

  public void waitUntilDisplayed() {
    try {
      waitUntilCondition(ExpectedConditions.visibilityOfElementLocated(locator));
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element with selector: %s to be displayed.", locator));
    }
  }

  public void waitUntilDisplayed(Duration duration) {
    try {
      waitUntilCondition(ExpectedConditions.visibilityOfElementLocated(locator), duration);
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element with selector: %s to be displayed.", locator));
    }
  }

  public void waitUntilInvisible() {
    try {
      waitUntilCondition(ExpectedConditions.invisibilityOfElementLocated(locator));
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element with selector: %s to be invisible.", locator));
    }
  }

  public void waitUntilInvisible(Duration duration) {
    try {
      waitUntilCondition(ExpectedConditions.invisibilityOfElementLocated(locator), duration);
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element with selector: %s to be invisible.", locator));
    }
  }

  public void waitUntilPresent() {
    try {
      waitUntilCondition(ExpectedConditions.presenceOfElementLocated(locator));
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public void waitUntilPresent(Duration duration) {
    try {
      waitUntilCondition(ExpectedConditions.presenceOfElementLocated(locator), duration);
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public void waitUntilTextToBePresent(String text) {
    try {
      waitUntilCondition(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text: '%s' to be present in element with selector: %s",
              text, locator));
    }
  }

  public void waitUntilTextToBePresent(String text, Duration duration) {
    try {
      waitUntilCondition(
          ExpectedConditions.textToBePresentInElementLocated(locator, text), duration);
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text: '%s' to be present in element with selector: %s",
              text, locator));
    }
  }

  public void waitUntilTextToBePresent() {
    try {
      wait.until(() -> !getTextValue().trim().isEmpty());
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text to be present in element with selector: %s.", locator));
    }
  }

  public void waitUntilTextToBePresent(Duration duration) {
    try {
      wait.atMost(duration).until(() -> !getTextValue().trim().isEmpty());
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text to be present in element with selector: %s.", locator));
    }
  }

  public void waitUntilTextNotToBe(String text, boolean partial) {
    try {
      wait.until(
          () ->
              partial
                  ? !getTextValue().toLowerCase().contains(text.toLowerCase())
                  : !getTextValue().toLowerCase().contentEquals(text.toLowerCase()));
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text not to be '%s' in element with selector: %s.",
              text, locator));
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

  protected MobileElement getElement() {
    try {
      wait.atMost(Duration.ofSeconds(5)).until(() -> driver.findElement(locator) != null);
      return driver.findElement(locator);
    } catch (Exception ex) {
      throw new RuntimeException(String.format("Element with selector: %s not found", locator));
    }
  }

  private <T> void waitUntilCondition(ExpectedCondition<T> webElementExpectedCondition) {
    wait.until(() -> webElementExpectedCondition.apply(driver) != null);
  }

  private <T> void waitUntilCondition(
      ExpectedCondition<T> webElementExpectedCondition, Duration duration) {
    wait.atMost(duration).until(() -> webElementExpectedCondition.apply(driver) != null);
  }

  private ConditionFactory buildFluentWait(Duration duration) {
    return Awaitility.await().atMost(duration).ignoreExceptions();
  }
}
