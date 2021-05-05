package com.testvagrant.ekam.commons.interceptors.web;

import com.google.inject.Inject;
import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.annotations.Step;
import com.testvagrant.ekam.commons.interceptors.SiteInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ScreenshotInterceptor extends SiteInterceptor implements MethodInterceptor {

  @Inject ScreenshotTaker screenshotTaker;
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    try {
      AtomicReference<Object> proceed = invokeMethod(invocation);
      initScreenshot(invocation);
      return proceed.get();
    } catch (Throwable e) {
      initScreenshot(invocation);
      throw e;
    }
  }

  private void initScreenshot(MethodInvocation invocation) {
    Step step = invocation.getMethod().getAnnotation(Step.class);
    if (Objects.isNull(step) && Toggles.TIMELINE.isActive()) screenshotTaker.saveScreenshot();
  }
}
