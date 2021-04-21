package com.testvagrant.ekam.mobile.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.interceptors.mobile.ScreenshotInterceptor;
import com.testvagrant.ekam.mobile.driver.AppiumDriverProvider;
import com.testvagrant.ekam.mobile.driver.DriverProvider;
import com.testvagrant.optimus.core.models.mobile.MobileDriverDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public class MobileModule extends AbstractModule {

    @Override
    protected void configure() {
        // bind driver
        bind(MobileDriverDetails.class).toProvider(DriverProvider.class).asEagerSingleton();
        bind(new TypeLiteral<AppiumDriver<MobileElement>>() {
        }).toProvider(AppiumDriverProvider.class).asEagerSingleton();

        // bind screenshot listener
        ScreenshotInterceptor screenshotInterceptor = new ScreenshotInterceptor();
        requestInjection(screenshotInterceptor);
        binder().bindInterceptor(any(), annotatedWith(Screenshot.class), screenshotInterceptor);
    }
}
