package com.testvagrant.ekam.web.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.interceptors.web.ScreenshotInterceptor;
import com.testvagrant.ekam.web.drivers.Browser;
import com.testvagrant.ekam.web.drivers.browserManager.ChromeDriverManager;
import com.testvagrant.ekam.web.drivers.browserManager.FirefoxDriverManager;
import com.testvagrant.ekam.web.drivers.wait.FluentWaits;
import com.testvagrant.ekam.web.drivers.wait.WebWaits;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public class WebModule extends AbstractModule {

  @Override
  public void configure() {
    // bind driver
    bind(WebDriver.class).toProvider(getBrowserManager()).asEagerSingleton();

    // bind explicit and fluent waits
    bind(WebDriverWait.class).toProvider(WebWaits.class).asEagerSingleton();
    bind(new TypeLiteral<FluentWait<WebDriver>>() {})
        .toProvider(FluentWaits.class)
        .asEagerSingleton();

    // bind screenshot listener
    ScreenshotInterceptor screenshotInterceptor = new ScreenshotInterceptor();
    requestInjection(screenshotInterceptor);
    binder().bindInterceptor(any(), annotatedWith(Screenshot.class), screenshotInterceptor);
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
