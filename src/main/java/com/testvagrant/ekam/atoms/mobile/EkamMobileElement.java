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
  private final Duration timeout;

  @Inject
  public EkamMobileElement(AppiumDriver<MobileElement> driver) {
    this.driver = driver;
    this.timeout = Duration.ofSeconds(30);
    this.wait = buildFluentWait(timeout); // Default Timeout
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

  public EkamMobileElement click() {
    waitUntilPresent();
    wait.until(
        () -> {
          getElement().click();
          return true;
        });
    return this;
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

  public EkamMobileElement waitUntilDisplayed() {
    try {
      waitUntilCondition(ExpectedConditions.visibilityOfElementLocated(locator));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element with selector: %s to be displayed.", locator));
    }
  }

  public EkamMobileElement waitUntilDisplayed(Duration duration) {
    try {
      waitUntilCondition(ExpectedConditions.visibilityOfElementLocated(locator), duration);
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element with selector: %s to be displayed.", locator));
    }
  }

  public EkamMobileElement waitUntilInvisible() {
    try {
      waitUntilCondition(ExpectedConditions.invisibilityOfElementLocated(locator));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element with selector: %s to be invisible.", locator));
    }
  }

  public EkamMobileElement waitUntilInvisible(Duration duration) {
    try {
      waitUntilCondition(ExpectedConditions.invisibilityOfElementLocated(locator), duration);
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element with selector: %s to be invisible.", locator));
    }
  }

  public EkamMobileElement waitUntilPresent() {
    try {
      waitUntilCondition(ExpectedConditions.presenceOfElementLocated(locator));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public EkamMobileElement waitUntilPresent(Duration duration) {
    try {
      waitUntilCondition(ExpectedConditions.presenceOfElementLocated(locator), duration);
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public EkamMobileElement waitUntilTextToBePresent(String text) {
    try {
      waitUntilCondition(ExpectedConditions.textToBePresentInElementLocated(locator, text));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text: '%s' to be present in element with selector: %s",
              text, locator));
    }
  }

  public EkamMobileElement waitUntilTextToBePresent(String text, Duration duration) {
    try {
      waitUntilCondition(
          ExpectedConditions.textToBePresentInElementLocated(locator, text), duration);
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text: '%s' to be present in element with selector: %s",
              text, locator));
    }
  }

  public EkamMobileElement waitUntilTextToBePresent() {
    try {
      wait.until(() -> !getTextValue().trim().isEmpty());
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text to be present in element with selector: %s.", locator));
    }
  }

  public EkamMobileElement waitUntilTextToBePresent(Duration duration) {
    try {
      wait.atMost(duration).until(() -> !getTextValue().trim().isEmpty());
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text to be present in element with selector: %s.", locator));
    }
  }

  public EkamMobileElement waitUntilTextNotToBe(String text, boolean partial) {
    try {
      wait.until(
          () ->
              partial
                  ? !getTextValue().toLowerCase().contains(text.toLowerCase())
                  : !getTextValue().toLowerCase().contentEquals(text.toLowerCase()));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text not to be '%s' in element with selector: %s.",
              text, locator));
    }
  }

  public EkamMobileElement tap() {
    touchAction.tap(ElementOption.element(getElement())).perform();
    return this;
  }

  public EkamMobileElement longPress() {
    touchAction
        .longPress(ElementOption.element(getElement()))
        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
        .perform();

    return this;
  }

  protected MobileElement getElement() {
    try {
      wait.atMost(Duration.ofSeconds(5)).until(() -> driver.findElement(locator) != null);
      return driver.findElement(locator);
    } catch (Exception ex) {
      throw new RuntimeException(String.format("Element with selector: %s not found", locator));
    }
  }

  private <T> void waitUntilCondition(ExpectedCondition<T> expectedCondition) {
    waitUntilCondition(expectedCondition, timeout);
  }

  private <T> void waitUntilCondition(ExpectedCondition<T> expectedCondition, Duration duration) {
    wait.atMost(duration)
        .until(
            () -> {
              Object result = expectedCondition.apply(driver);
              return result != null
                      && result.getClass().getTypeName().toLowerCase().contains("boolean")
                  ? (boolean) result
                  : result != null;
            });
  }

  private ConditionFactory buildFluentWait(Duration duration) {
    return Awaitility.await().atMost(duration).ignoreExceptions();
  }
}
