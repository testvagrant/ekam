package com.testvagrant.ekam.commons.retry;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

public class RetryEngine {
    private RetryTemplate retryTemplate;

    public RetryEngine(int numberOfRetries, Class<? extends Throwable> exceptionToRetry) {
        Map<Class<? extends Throwable>, Boolean> map = new HashMap<>();
        map.put(exceptionToRetry, true);
        retryTemplate = initRetryTemplate(numberOfRetries, map);
    }

    public RetryEngine(Class<? extends Throwable> exceptionToRetry) {
        this(100, exceptionToRetry);
    }

    public RetryEngine(int numberOfRetries, Map<Class<? extends Throwable>, Boolean> exceptionMap) {
        retryTemplate = initRetryTemplate(numberOfRetries, exceptionMap);
    }

    public RetryEngine(Map<Class<? extends Throwable>, Boolean> exceptionMap) {
        this(10, exceptionMap);
    }

    private RetryTemplate initRetryTemplate(int numberOfRetries, Map<Class<? extends Throwable>, Boolean> exceptionMap) {
        RetryTemplate retryTemplate = new RetryTemplate();
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(5000);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(numberOfRetries, exceptionMap);
        retryTemplate.setRetryPolicy(simpleRetryPolicy);
        return retryTemplate;
    }

    public void execute(RetryCallback retryCallback) {
        retryTemplate.execute(retryCallback);
    }
}
