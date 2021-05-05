package com.testvagrant.ekam.web.drivers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.testvagrant.optimus.core.models.web.WebDriverDetails;
import org.openqa.selenium.WebDriver;

public class WebDriverProvider implements Provider<WebDriver> {

  private final WebDriverDetails webDriverDetails;

  @Inject
  public WebDriverProvider(WebDriverDetails webDriverDetails) {
    this.webDriverDetails = webDriverDetails;
  }

  @Override
  public WebDriver get() {
    return webDriverDetails.getDriver();
  }
}
