package com.testvagrant.ekam.atoms.web;

import com.google.inject.Inject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class Element {

  @Inject WebDriver driver;

  protected By locator;
  private final int explicitTimeoutInSeconds = 30;
  private final int implicitTimeoutInSeconds = 5;

  protected Element(By locator) {
    this.locator = locator;
  }

  public static Element Element(By locator) {
    return new Element(locator);
  }

  public String getTextValue() {
    return getElement().getText();
  }

  public String getAttributeValue(String attribute) {
    return getElement().getAttribute(attribute);
  }

  public void click() {
    Wait<WebDriver> wait =
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
    Wait<WebDriver> wait =
        new FluentWait<>(driver)
            .ignoring(NoSuchElementException.class)
            .ignoring(ElementNotInteractableException.class)
            .ignoring(StaleElementReferenceException.class)
            .withTimeout(Duration.ofSeconds(explicitTimeoutInSeconds));

    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public void waitUntilInvisible() {
    Wait<WebDriver> wait =
        new FluentWait<>(driver)
            .ignoring(NoSuchElementException.class)
            .ignoring(ElementNotInteractableException.class)
            .ignoring(StaleElementReferenceException.class)
            .withTimeout(Duration.ofSeconds(explicitTimeoutInSeconds));

    wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  public void waitUntilPresent() {
    Wait<WebDriver> wait =
        new FluentWait<>(driver)
            .ignoring(NoSuchElementException.class)
            .ignoring(ElementNotInteractableException.class)
            .ignoring(StaleElementReferenceException.class)
            .withTimeout(Duration.ofSeconds(explicitTimeoutInSeconds));

    wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  public void waitUntilTextToBePresent(String text) {
    Wait<WebDriver> wait =
        new FluentWait<>(driver)
            .ignoring(NoSuchElementException.class)
            .ignoring(ElementNotInteractableException.class)
            .ignoring(StaleElementReferenceException.class)
            .withTimeout(Duration.ofSeconds(explicitTimeoutInSeconds));

    wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
  }

  protected WebElement getElement() {
    Wait<WebDriver> wait =
        new FluentWait<>(driver)
            .ignoring(NoSuchElementException.class)
            .withTimeout(Duration.ofSeconds(implicitTimeoutInSeconds));

    return wait.until(driver -> driver.findElement(locator));
  }
}
