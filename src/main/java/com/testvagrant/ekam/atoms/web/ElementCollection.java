package com.testvagrant.ekam.atoms.web;

import com.google.inject.Inject;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ElementCollection {

  private final WebDriver driver;

  private final ConditionFactory wait;

  private By locator;

  @Inject
  public ElementCollection(WebDriver driver) {
    this.driver = driver;
    this.wait = buildFluentWait(Duration.ofSeconds(10)); // Default Timeout
  }

  public ElementCollection locate(By locator) {
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

  public List<WebElement> get() {
    wait.until(
        () -> {
          List<WebElement> elements = driver.findElements(locator);
          return !elements.isEmpty();
        });

    return driver.findElements(locator);
  }

  private ConditionFactory buildFluentWait(Duration duration) {
    return Awaitility.await().atMost(duration).ignoreExceptions();
  }
}
