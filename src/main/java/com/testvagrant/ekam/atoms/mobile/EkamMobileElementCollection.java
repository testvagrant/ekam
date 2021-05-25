package com.testvagrant.ekam.atoms.mobile;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class EkamMobileElementCollection {
  private final AppiumDriver<MobileElement> driver;

  private final ConditionFactory wait;

  private By locator;

  private final Duration timeout;

  @Inject
  public EkamMobileElementCollection(AppiumDriver<MobileElement> driver) {
    this.driver = driver;
    this.timeout = Duration.ofSeconds(30);
    this.wait = buildFluentWait(timeout); // Default Timeout
  }

  public EkamMobileElementCollection locate(By locator) {
    this.locator = locator;
    return this;
  }

  public List<String> getTextValues() {
    return get().stream().map(WebElement::getText).collect(Collectors.toList());
  }

  public List<String> getAttributeValues(String attribute) {
    return get().stream()
        .map(webElement -> webElement.getAttribute(attribute))
        .collect(Collectors.toList());
  }

  public List<MobileElement> get() {
    wait.atMost(Duration.ofSeconds(10))
        .until(
            () -> {
              List<MobileElement> elements = driver.findElements(locator);
              return !elements.isEmpty();
            });

    return driver.findElements(locator);
  }

  public EkamMobileElementCollection waitUntilPresent() {
    try {
      waitUntilCondition(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public EkamMobileElementCollection waitUntilPresent(Duration duration) {
    try {
      waitUntilCondition(ExpectedConditions.presenceOfAllElementsLocatedBy(locator), duration);
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public EkamMobileElementCollection waitUntilVisible() {
    try {
      waitUntilCondition(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public EkamMobileElementCollection waitUntilVisible(Duration duration) {
    try {
      waitUntilCondition(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator), duration);
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public EkamMobileElementCollection waitUntilInVisible() {
    try {
      waitUntilCondition(ExpectedConditions.invisibilityOfElementLocated(locator));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public EkamMobileElementCollection waitUntilInVisible(Duration duration) {
    try {
      waitUntilCondition(ExpectedConditions.invisibilityOfElementLocated(locator), duration);
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public EkamMobileElementCollection waitUntilElementCountToBeMoreThan(
      int elementCountToBeGreaterThan) {
    try {
      waitUntilCondition(
          ExpectedConditions.numberOfElementsToBeMoreThan(locator, elementCountToBeGreaterThan));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public EkamMobileElementCollection waitUntilElementCountToBeMoreThan(
      int elementCountToBeGreaterThan, Duration duration) {
    try {
      waitUntilCondition(
          ExpectedConditions.numberOfElementsToBeMoreThan(locator, elementCountToBeGreaterThan),
          duration);
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public EkamMobileElementCollection waitUntilElementCountToBeLessThan(
      int elementCountToBeLessThan) {
    try {
      waitUntilCondition(
          ExpectedConditions.numberOfElementsToBeLessThan(locator, elementCountToBeLessThan));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public EkamMobileElementCollection waitUntilElementCountToBeLessThan(
      int elementCountToBeLessThan, Duration duration) {
    try {
      waitUntilCondition(
          ExpectedConditions.numberOfElementsToBeLessThan(locator, elementCountToBeLessThan),
          duration);
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  private ConditionFactory buildFluentWait(Duration duration) {
    return Awaitility.await().atMost(duration).ignoreExceptions();
  }

  private <T> void waitUntilCondition(ExpectedCondition<T> webElementExpectedCondition) {
    waitUntilCondition(webElementExpectedCondition, timeout);
  }

  private <T> void waitUntilCondition(
      ExpectedCondition<T> webElementExpectedCondition, Duration duration) {
    wait.atMost(duration)
        .until(
            () -> {
              Object result = webElementExpectedCondition.apply(driver);
              return result != null
                      && result.getClass().getTypeName().toLowerCase().contains("boolean")
                  ? (boolean) result
                  : result != null;
            });
  }
}
