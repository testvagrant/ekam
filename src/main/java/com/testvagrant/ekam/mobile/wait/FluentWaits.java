package com.testvagrant.ekam.mobile.wait;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.testvagrant.ekam.commons.annotations.WaitDuration;
import com.testvagrant.optimuscloud.entities.MobileDriverDetails;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class FluentWaits implements Provider<Wait> {
  private final Wait wait;

  @Inject
  public FluentWaits(MobileDriverDetails mobileDriverDetails, @WaitDuration String waitDuration) {
    wait =
        new FluentWait<>(mobileDriverDetails.getMobileDriver())
            .ignoring(NoSuchElementException.class)
            .ignoring(ElementNotInteractableException.class)
            .ignoring(TimeoutException.class)
            .ignoring(StaleElementReferenceException.class)
            .ignoring(ElementClickInterceptedException.class)
            .withTimeout(Duration.ofSeconds(Long.parseLong(waitDuration)));
  }

  @Override
  public Wait get() {
    return wait;
  }
}
