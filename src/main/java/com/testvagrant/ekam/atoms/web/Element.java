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

  @Inject
  public Element(WebDriver driver) {
    this.driver = driver;
    this.wait = buildFluentWait(Duration.ofSeconds(30)); // Default Timeout
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

  public void waitUntilAttributeNotToBe(String attribute, String text, boolean partial) {
    try {
      wait.until(
          () ->
              partial
                  ? !getAttributeValue(attribute).toLowerCase().contains(text.toLowerCase())
                  : !getAttributeValue(attribute).toLowerCase().contentEquals(text.toLowerCase()));
    } catch (Exception ex) {
      throw new RuntimeException(
          String.format(
              "Error waiting for text not to be '%s' in element with selector: %s.",
              text, locator));
    }
  }

  public void waitUntilAttributeNotToBe(String attribute, String value) {
    try {
      waitUntilAttributeNotToBe(attribute, value, false);
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
    wait.until(() -> webElementExpectedCondition.apply(driver) != null);
  }

  private <T> void waitUntilCondition(
      ExpectedCondition<T> webElementExpectedCondition, Duration duration) {
    wait.atMost(duration).until(() -> webElementExpectedCondition.apply(driver) != null);
  }
}
