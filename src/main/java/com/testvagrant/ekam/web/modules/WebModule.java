package com.testvagrant.ekam.web.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.web.drivers.Browser;
import com.testvagrant.ekam.web.drivers.browserManager.ChromeDriverManager;
import com.testvagrant.ekam.web.drivers.browserManager.FirefoxDriverManager;
import com.testvagrant.ekam.web.drivers.wait.FluentWaits;
import com.testvagrant.ekam.web.drivers.wait.WebWaits;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.LinkedHashMap;
import java.util.Map;

public class WebModule extends AbstractModule {

  @Override
  public void configure() {
    bind(WebDriver.class).toProvider(getBrowserManager()).asEagerSingleton();
    bind(WebDriverWait.class).toProvider(WebWaits.class).asEagerSingleton();
    bind(Wait.class).toProvider(FluentWaits.class).asEagerSingleton();
  }

  private Class getBrowserManager() {
    Browser browser = Browser.valueOf(SystemProperties.BROWSER.toUpperCase());
    return browserProviderMap().get(browser);
  }

  private Map<Browser, Class> browserProviderMap() {
    Map<Browser, Class> browserProviderMap = new LinkedHashMap<>();
    browserProviderMap.put(Browser.CHROME, ChromeDriverManager.class);
    browserProviderMap.put(Browser.FIREFOX, FirefoxDriverManager.class);
    return browserProviderMap;
  }
}
