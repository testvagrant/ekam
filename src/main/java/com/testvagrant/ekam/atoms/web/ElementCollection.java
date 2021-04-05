package com.testvagrant.ekam.atoms.web;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ElementCollection {

  @Inject private WebDriver driver;
  @Inject private FluentWait<WebDriver> wait;

  private By locator;

  protected void locate(By locator) {
    this.locator = locator;
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
    FluentWait<WebDriver> fluentWait = wait.withTimeout(Duration.ofSeconds(10));

    fluentWait.until(
        driver -> {
          List<WebElement> elements = driver.findElements(locator);
          return !elements.isEmpty();
        });

    return driver.findElements(locator);
  }
}
