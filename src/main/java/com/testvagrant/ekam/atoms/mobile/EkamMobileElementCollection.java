package com.testvagrant.ekam.atoms.mobile;

import com.google.inject.Inject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class EkamMobileElementCollection {
  private final AppiumDriver<MobileElement> driver;

  private final ConditionFactory wait;

  private By locator;

  @Inject
  public EkamMobileElementCollection(AppiumDriver<MobileElement> driver) {
    this.driver = driver;
    this.wait = buildFluentWait(Duration.ofSeconds(10)); // Default Timeout
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
    wait.until(
        () -> {
          List<MobileElement> elements = driver.findElements(locator);
          return !elements.isEmpty();
        });

    return driver.findElements(locator);
  }

  private ConditionFactory buildFluentWait(Duration duration) {
    return Awaitility.await().atMost(duration).ignoreExceptions();
  }
}
