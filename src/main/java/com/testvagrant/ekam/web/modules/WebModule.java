package com.testvagrant.ekam.web.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.annotations.Step;
import com.testvagrant.ekam.commons.interceptors.StepInterceptor;
import com.testvagrant.ekam.commons.interceptors.web.ScreenshotInterceptor;
import com.testvagrant.ekam.mobile.driver.AppiumDriverProvider;
import com.testvagrant.ekam.mobile.driver.MobileDriverDetailsProvider;
import com.testvagrant.ekam.web.drivers.WebDriverDetailsProvider;
import com.testvagrant.ekam.web.drivers.WebDriverProvider;
import com.testvagrant.optimus.core.models.mobile.MobileDriverDetails;
import com.testvagrant.optimus.core.models.web.WebDriverDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebDriver;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public class WebModule extends AbstractModule {

    @Override
    public void configure() {
        // bind driver
        bind(WebDriverDetails.class).toProvider(WebDriverDetailsProvider.class).asEagerSingleton();
        bind(WebDriver.class).toProvider(WebDriverProvider.class).asEagerSingleton();

        // bind screenshot listener
        ScreenshotInterceptor screenshotInterceptor = new ScreenshotInterceptor();
        requestInjection(screenshotInterceptor);
        binder().bindInterceptor(any(), annotatedWith(Screenshot.class), screenshotInterceptor);

        binder().bindInterceptor(any(), annotatedWith(Step.class), new StepInterceptor());
    }

}
