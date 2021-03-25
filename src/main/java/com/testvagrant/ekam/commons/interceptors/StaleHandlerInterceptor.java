package com.testvagrant.ekam.commons.interceptors;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.atomic.AtomicReference;

public class StaleHandlerInterceptor extends SiteInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        AtomicReference<Object> proceed = retryObjInvoke(invocation);
        return proceed.get();
    }

}
