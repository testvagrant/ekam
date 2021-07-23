package com.testvagrant.ekam.web.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.web.providers.WebDriverProvider;
import org.openqa.selenium.WebDriver;

public class WebModule extends AbstractModule {

  @Override
  public void configure() {
    bind(WebDriver.class).toProvider(WebDriverProvider.class).asEagerSingleton();
  }
}
