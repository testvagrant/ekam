package com.testvagrant.ekam.commons.interceptors.web;

import com.google.inject.Inject;
import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.interceptors.SiteInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.atomic.AtomicReference;

public class ScreenshotInterceptor extends SiteInterceptor implements MethodInterceptor {

  @Inject ScreenshotTaker screenshotTaker;
  @Override
  public Object invoke(MethodInvocation invocation) {
    AtomicReference<Object> proceed = invokeMethod(invocation);
    if (Toggles.TIMELINE.isActive()) screenshotTaker.saveScreenshot();
    return proceed.get();
  }
}