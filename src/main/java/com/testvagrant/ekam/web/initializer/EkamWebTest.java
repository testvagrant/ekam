package com.testvagrant.ekam.web.initializer;

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
    injectorsCache().getInjector().getInstance(WebDriver.class).quit();
  }
}
