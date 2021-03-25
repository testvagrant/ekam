package com.testvagrant.ekam.commons.interceptors;

import com.testvagrant.ekam.commons.retry.RetryEngine;
import org.aopalliance.intercept.MethodInvocation;
import org.openqa.selenium.StaleElementReferenceException;
import org.springframework.retry.RetryCallback;

import java.util.concurrent.atomic.AtomicReference;

public abstract class SiteInterceptor {


    protected AtomicReference<Object> retryObjInvoke(MethodInvocation invocation) {
        AtomicReference<Object> proceed = new AtomicReference<>();
        RetryCallback retryCallback = retry -> {
            try {
                proceed.set(invocation.proceed());
                return proceed.get();
            } catch (StaleElementReferenceException e) {
                throw e;
            }
        };
        RetryEngine retryEngine = new RetryEngine(StaleElementReferenceException.class);
        retryEngine.execute(retryCallback);
        return proceed;
    }
}
