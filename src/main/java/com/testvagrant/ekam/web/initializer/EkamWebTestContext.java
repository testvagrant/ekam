package com.testvagrant.ekam.web.initializer;

import com.testvagrant.ekam.commons.injectors.InjectorCreator;
import com.testvagrant.ekam.commons.testContext.EkamTestDetails;
import org.openqa.selenium.WebDriver;

import static com.testvagrant.ekam.commons.cache.InjectorsCacheProvider.injectorsCache;

public class EkamWebTestContext {

  private final EkamTestDetails ekamTestDetails;

  public EkamWebTestContext(EkamTestDetails ekamTestDetails) {
    this.ekamTestDetails = ekamTestDetails;
  }

  public void init() {
    new InjectorCreator(ekamTestDetails).createWebInjector(false);
  }

  public void dispose() {
    injectorsCache().getInjector().getInstance(WebDriver.class).quit();
  }
}
