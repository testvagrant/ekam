package com.testvagrant.ekam.web.initializer;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.injectors.InjectorCreator;
import com.testvagrant.ekam.commons.models.EkamTest;
import org.openqa.selenium.WebDriver;

import static com.testvagrant.ekam.commons.cache.InjectorsCacheProvider.injectorsCache;

public class EkamWebTest {

  private final EkamTest ekamTest;

  public EkamWebTest(EkamTest ekamTest) {
    this.ekamTest = ekamTest;
  }

  public void init(boolean enableMobile) {
    new InjectorCreator(ekamTest).createWebInjector(enableMobile);
  }

  public void dispose() {
    Injector injector = injectorsCache().getInjector();
    WebDriver webDriver = injector.getInstance(WebDriver.class);
    if (webDriver != null) webDriver.quit();
  }
}
