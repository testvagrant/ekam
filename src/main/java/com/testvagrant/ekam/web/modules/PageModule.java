package com.testvagrant.ekam.web.modules;

import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.annotations.StaleHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.testvagrant.ekam.commons.interceptors.ScreenshotInterceptor;
import com.testvagrant.ekam.commons.interceptors.ScreenshotTaker;
import com.testvagrant.ekam.commons.interceptors.StaleHandlerInterceptor;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public class PageModule extends AbstractModule {

    @Inject
    ScreenshotTaker screenshotTaker;

    @Override
    public void configure() {
        ScreenshotInterceptor screenshotInterceptor = new ScreenshotInterceptor();
        StaleHandlerInterceptor staleHandlerInterceptor = new StaleHandlerInterceptor();
        requestInjection(screenshotInterceptor);
        binder().bindInterceptor(any(), annotatedWith(Screenshot.class), screenshotInterceptor);
        binder().bindInterceptor(any(), annotatedWith(StaleHandler.class), staleHandlerInterceptor);
    }
}
