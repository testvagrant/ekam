package com.testvagrant.ekam.mobile.driver.wait;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.testvagrant.ekam.commons.annotations.WaitDuration;
import com.testvagrant.optimuscloud.entities.MobileDriverDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class FluentWaits implements Provider<FluentWait<AppiumDriver<MobileElement>>> {
  private final FluentWait<AppiumDriver<MobileElement>> wait;

  @Inject
  public FluentWaits(MobileDriverDetails mobileDriverDetails, @WaitDuration String waitDuration) {
    wait =
        new FluentWait<>((AppiumDriver<MobileElement>) mobileDriverDetails.getMobileDriver())
            .ignoring(NoSuchElementException.class)
            .ignoring(ElementNotInteractableException.class)
            .ignoring(TimeoutException.class)
            .ignoring(StaleElementReferenceException.class)
            .ignoring(ElementClickInterceptedException.class)
            .withTimeout(Duration.ofSeconds(Long.parseLong(waitDuration)));
  }

  @Override
  public FluentWait<AppiumDriver<MobileElement>> get() {
    return wait;
  }
}
