package com.testvagrant.ekam.commons.modules;

import com.google.inject.AbstractModule;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.annotations.Step;
import com.testvagrant.ekam.commons.interceptors.ScreenshotInterceptor;
import com.testvagrant.ekam.commons.interceptors.StepInterceptor;

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
        binder().bindInterceptor(any(), annotatedWith(Step.class), new StepInterceptor());
    }
}
