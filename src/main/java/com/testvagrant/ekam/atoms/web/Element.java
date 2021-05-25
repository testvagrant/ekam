package com.testvagrant.ekam.atoms.web;

import com.google.inject.Inject;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class Element {

  protected final WebDriver driver;
  private final ConditionFactory wait;
  private By locator;
  private final Duration timeout;

  @Inject
  public Element(WebDriver driver) {
    this.driver = driver;
    this.timeout = Duration.ofSeconds(30);
    this.wait = buildFluentWait(timeout); // Default Timeout
  }

  public String getTextValue() {
    return getElement().getText();
  }

  public Element locate(By locator) {
    this.locator = locator;
    return this;
  }

  public String getAttributeValue(String attribute) {
    return getElement().getAttribute(attribute);
  }

  public Element click() {
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

  public Element waitUntilDisplayed() {
    try {
      waitUntilCondition(ExpectedConditions.visibilityOfElementLocated(locator));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element with selector: %s to be displayed.", locator));
    }
  }

  public Element waitUntilDisplayed(Duration duration) {
    try {
      waitUntilCondition(ExpectedConditions.visibilityOfElementLocated(locator), duration);
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element with selector: %s to be displayed.", locator));
    }
  }

  public Element waitUntilInvisible() {
    try {
      waitUntilCondition(ExpectedConditions.invisibilityOfElementLocated(locator));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element with selector: %s to be invisible.", locator));
    }
  }

  public Element waitUntilInvisible(Duration duration) {
    try {
      waitUntilCondition(ExpectedConditions.invisibilityOfElementLocated(locator), duration);
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element with selector: %s to be invisible.", locator));
    }
  }

  public Element waitUntilPresent() {
    try {
      waitUntilCondition(ExpectedConditions.presenceOfElementLocated(locator));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public Element waitUntilPresent(Duration duration) {
    try {
      waitUntilCondition(ExpectedConditions.presenceOfElementLocated(locator), duration);
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format("Error waiting for element presence with selector: %s.", locator));
    }
  }

  public Element waitUntilTextToBePresent(String text) {
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

  public Element waitUntilTextToBePresent(String text, Duration duration) {
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

  public Element waitUntilTextToBePresent() {
    try {
      wait.until(() -> !getTextValue().trim().isEmpty());
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text to be present in element with selector: %s.", locator));
    }
  }

  public Element waitUntilTextToBePresent(Duration duration) {
    try {
      wait.atMost(duration).until(() -> !getTextValue().trim().isEmpty());
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text to be present in element with selector: %s.", locator));
    }
  }

  public Element waitUntilTextNotToBe(String text, boolean partial) {
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

  public Element waitUntilAttributeNotToBe(String attribute, String text, boolean partial) {
    try {
      wait.until(
          () ->
              partial
                  ? !getAttributeValue(attribute).toLowerCase().contains(text.toLowerCase())
                  : !getAttributeValue(attribute).toLowerCase().contentEquals(text.toLowerCase()));
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text not to be '%s' in element with selector: %s.",
              text, locator));
    }
  }

  public Element waitUntilAttributeNotToBe(String attribute, String value) {
    try {
      waitUntilAttributeNotToBe(attribute, value, false);
      return this;
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text not to be '%s' in element with selector: %s.",
              value, locator));
    }
  }

  protected WebElement getElement() {
    try {
      wait.atMost(Duration.ofSeconds(5)).until(() -> driver.findElement(locator) != null);
      return driver.findElement(locator);
    } catch (Exception ex) {
      throw new RuntimeException(String.format("Element with selector: %s not found", locator));
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
