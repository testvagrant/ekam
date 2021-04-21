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

  public void waitUntilDisplayed() {
    waitUntilCondition(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public void waitUntilInvisible() {
    waitUntilCondition(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  public void waitUntilPresent() {
    waitUntilCondition(ExpectedConditions.presenceOfElementLocated(locator));
  }

  private void waitUntilCondition(ExpectedCondition webElementExpectedCondition) {
    wait.until(
        () -> {
          Object value = webElementExpectedCondition.apply(driver);
          System.out.println(value);
          return value != null;
        });
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

  public void waitUntilAttributeNotToBe(String attribute, String value) {
    wait.until(
        () -> {
          String attributeValue = getAttributeValue(attribute);
          return !attributeValue.contains(value);
        });
  }

  // TODO: Add global support for wait duration
  public WebElement getElement() {
    wait.until(() -> driver.findElement(locator) != null);
    return driver.findElement(locator);
  }

  private ConditionFactory buildFluentWait(Duration duration) {
    return Awaitility.await().atMost(duration).ignoreExceptions();
  }
}
