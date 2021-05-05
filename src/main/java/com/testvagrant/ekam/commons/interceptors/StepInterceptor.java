package com.testvagrant.ekam.commons.interceptors;


import com.testvagrant.ekam.commons.PageInitiator;
import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.annotations.Step;
import com.testvagrant.ekam.reports.AllureAttachment;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class StepInterceptor extends SiteInterceptor implements MethodInterceptor {


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            AtomicReference<Object> proceed = invokeMethod(invocation);
            addStep(invocation);
            return proceed.get();
        } catch (Throwable e) {
            addStep(invocation);
            throw e;
        }
    }

    private void addStep(MethodInvocation invocation) throws Throwable {
        Step stepAnnotation = invocation.getMethod().getAnnotation(Step.class);
        com.testvagrant.optimus.dashboard.models.Step step = com.testvagrant.optimus.dashboard.models.Step.builder()
                .name(stepAnnotation.description())
                .keyword(stepAnnotation.keyword())
                .error_message(getMessage())
                .status(getStatus())
                .build();
        PageInitiator.getInstance().addStep(step);
        Screenshot screenshot = invocation.getMethod().getAnnotation(Screenshot.class);
        recordAllureStep(stepAnnotation.keyword(), stepAnnotation.description(), screenshot);
    }

    private String getMessage() {
        if(Objects.isNull(throwable)) {
            return "";
        }
        return ExceptionUtils.getStackTrace(throwable);
    }

    private String getStatus() {
        if(Objects.isNull(throwable)) {
            return "passed";
        }
        return "failed";
    }


    @io.qameta.allure.Step("{keyword} {description}")
    public void recordAllureStep(String keyword, String description, Screenshot screenshot) throws Throwable {
        if((Objects.nonNull(screenshot) && Toggles.TIMELINE.isOn()) || Objects.nonNull(throwable)) {
            Path path = PageInitiator.getInstance().captureScreenshot();
            new AllureAttachment().attachScreenshot(screenshot.name(), path);
        }
        if(Objects.nonNull(throwable)) {
            throw throwable;
        }

    }
}
