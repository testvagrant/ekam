package com.testvagrant.ekam.reports.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.reports.annotations.APIStep;
import com.testvagrant.ekam.reports.annotations.MobileStep;
import com.testvagrant.ekam.reports.annotations.Screenshot;
import com.testvagrant.ekam.reports.annotations.WebStep;
import com.testvagrant.ekam.reports.interceptors.ApiStepInterceptor;
import com.testvagrant.ekam.reports.interceptors.MobileStepInterceptor;
import com.testvagrant.ekam.reports.interceptors.ScreenshotInterceptor;
import com.testvagrant.ekam.reports.interceptors.WebStepInterceptor;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class InterceptorModule extends AbstractModule {

  @Override
  protected void configure() {
    // bind screenshot listener
    ScreenshotInterceptor screenshotInterceptor = new ScreenshotInterceptor();
    requestInjection(screenshotInterceptor);
    binder().bindInterceptor(any(), annotatedWith(Screenshot.class), screenshotInterceptor);
    ekamLogger().info("Binding Screenshot interceptor");
    // bind Step listener
    binder().bindInterceptor(any(), annotatedWith(WebStep.class), new WebStepInterceptor());
    ekamLogger().info("Binding WebStep interceptor");
    binder().bindInterceptor(any(), annotatedWith(MobileStep.class), new MobileStepInterceptor());
    ekamLogger().info("Binding MobileStep interceptor");
    binder().bindInterceptor(any(), annotatedWith(APIStep.class), new ApiStepInterceptor());
    ekamLogger().info("Binding ApiStep interceptor");
  }
}
