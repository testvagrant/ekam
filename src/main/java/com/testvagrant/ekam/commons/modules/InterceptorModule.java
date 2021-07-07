package com.testvagrant.ekam.commons.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.commons.annotations.APIStep;
import com.testvagrant.ekam.commons.annotations.MobileStep;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.annotations.WebStep;
import com.testvagrant.ekam.commons.interceptors.ApiStepInterceptor;
import com.testvagrant.ekam.commons.interceptors.MobileStepInterceptor;
import com.testvagrant.ekam.commons.interceptors.ScreenshotInterceptor;
import com.testvagrant.ekam.commons.interceptors.WebStepInterceptor;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public class InterceptorModule extends AbstractModule {

  @Override
  protected void configure() {
    // bind screenshot listener
    ScreenshotInterceptor screenshotInterceptor = new ScreenshotInterceptor();
    requestInjection(screenshotInterceptor);
    binder().bindInterceptor(any(), annotatedWith(Screenshot.class), screenshotInterceptor);

    // bind Step listener
    binder().bindInterceptor(any(), annotatedWith(WebStep.class), new WebStepInterceptor());
    binder().bindInterceptor(any(), annotatedWith(MobileStep.class), new MobileStepInterceptor());
    binder().bindInterceptor(any(), annotatedWith(APIStep.class), new ApiStepInterceptor());
  }
}
