package com.testvagrant.ekam.commons.interceptors;

import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.atomic.AtomicReference;

public abstract class InvocationInterceptor {

  protected Throwable throwable;

  protected AtomicReference<Object> invokeMethod(MethodInvocation invocation) throws Throwable {
    AtomicReference<Object> proceed = new AtomicReference<>();
    try {
      proceed.set(invocation.proceed());
    } catch (Throwable throwable) {
      this.throwable = throwable;
      throw throwable;
    }
    return proceed;
  }
}
