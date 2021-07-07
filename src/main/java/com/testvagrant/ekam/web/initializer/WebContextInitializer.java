package com.testvagrant.ekam.web.initializer;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.cache.InjectorsCacheProvider;
import com.testvagrant.ekam.commons.injectors.InjectorCreator;
import com.testvagrant.ekam.commons.testContext.EkamTestContext;
import org.openqa.selenium.WebDriver;

public class WebContextInitializer {

  private EkamTestContext ekamTestContext;

  public WebContextInitializer(EkamTestContext ekamTestContext) {
    this.ekamTestContext = ekamTestContext;
  }

  public void init() {
    new InjectorCreator(ekamTestContext).createWebInjector();
  }

  public void dispose() {
    Injector webDriverInjector = getInjector();
    webDriverInjector.getInstance(WebDriver.class).quit();
  }

  private Injector getInjector() {
    return InjectorsCacheProvider.getInstance().get();
  }
}
