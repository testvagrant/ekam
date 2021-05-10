package com.testvagrant.ekam.web.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.web.drivers.WebDriverDetailsProvider;
import com.testvagrant.ekam.web.drivers.WebDriverProvider;
import com.testvagrant.optimus.core.models.web.WebDriverDetails;
import org.openqa.selenium.WebDriver;

public class WebModule extends AbstractModule {

  @Override
  public void configure() {
    // bind driver
    bind(WebDriverDetails.class).toProvider(WebDriverDetailsProvider.class).asEagerSingleton();
    bind(WebDriver.class).toProvider(WebDriverProvider.class).asEagerSingleton();
  }
}
