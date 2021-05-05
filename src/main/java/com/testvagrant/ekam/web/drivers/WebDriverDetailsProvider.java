package com.testvagrant.ekam.web.drivers;

import com.google.inject.Provider;
import com.testvagrant.optimus.core.models.web.WebDriverDetails;
import com.testvagrant.optimus.core.web.WebDriverManager;

public class WebDriverDetailsProvider implements Provider<WebDriverDetails> {

  @Override
  public WebDriverDetails get() {
    return createDriver();
  }

  private WebDriverDetails createDriver() {
    return new WebDriverManager().createDriverDetails();
  }
}
