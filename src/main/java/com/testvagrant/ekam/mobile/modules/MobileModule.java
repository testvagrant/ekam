package com.testvagrant.ekam.mobile.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.interceptors.mobile.ScreenshotInterceptor;
import com.testvagrant.ekam.mobile.driver.DriverProvider;
import com.testvagrant.ekam.mobile.driver.wait.FluentWaits;
import com.testvagrant.ekam.mobile.driver.wait.MobileWaits;
import com.testvagrant.optimuscloud.entities.MobileDriverDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public class MobileModule extends AbstractModule {

  @Override
  protected void configure() {
    // bind driver
    MobileDriverDetails mobileDriverDetails = new DriverProvider().get();
    bind(MobileDriverDetails.class).toInstance(mobileDriverDetails);
    bind(new TypeLiteral<AppiumDriver<MobileElement>>() {})
        .toInstance((AppiumDriver<MobileElement>) mobileDriverDetails.getMobileDriver());

    // bind explicit and fluent waits
    bind(WebDriverWait.class).toProvider(MobileWaits.class).asEagerSingleton();
    bind(new TypeLiteral<FluentWait<AppiumDriver<MobileElement>>>() {})
        .toProvider(FluentWaits.class)
        .asEagerSingleton();

    // bind screenshot listener
    ScreenshotInterceptor screenshotInterceptor = new ScreenshotInterceptor();
    requestInjection(screenshotInterceptor);
    binder().bindInterceptor(any(), annotatedWith(Screenshot.class), screenshotInterceptor);
  }
}
