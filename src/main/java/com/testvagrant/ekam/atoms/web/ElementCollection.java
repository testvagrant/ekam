package com.testvagrant.ekam.atoms.web;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ElementCollection {

  @Inject WebDriver driver;
  private final By locator;

  private ElementCollection(By locator) {
    this.locator = locator;
  }

  public static ElementCollection ElementCollection(By locator) {
    return new ElementCollection(locator);
  }

  public List<String> getTextValues() {
    return getMatchingElements().stream().map(WebElement::getText).collect(Collectors.toList());
  }

  public List<String> getAttributeValues(String attribute) {
    return getMatchingElements().stream()
        .map(webElement -> webElement.getAttribute(attribute))
        .collect(Collectors.toList());
  }

  private List<WebElement> getMatchingElements() {
    Wait<WebDriver> wait =
        new FluentWait<>(driver)
            .ignoring(NoSuchElementException.class)
            .withTimeout(Duration.ofSeconds(10));

    wait.until(
        driver -> {
          List<WebElement> elements = driver.findElements(locator);
          return !elements.isEmpty();
        });

    return driver.findElements(locator);
  }
}
