package com.testvagrant.ekam.commons.interceptors;

import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.atomic.AtomicReference;

public abstract class SiteInterceptor {

  protected AtomicReference<Object> invokeMethod(MethodInvocation invocation) {
    AtomicReference<Object> proceed = new AtomicReference<>();
    try {
      proceed.set(invocation.proceed());
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
    return proceed;
  }
}
