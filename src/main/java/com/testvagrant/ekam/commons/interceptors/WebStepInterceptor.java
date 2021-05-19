package com.testvagrant.ekam.commons.interceptors;

import com.testvagrant.ekam.commons.LayoutInitiator;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.annotations.WebStep;
import com.testvagrant.ekam.commons.injectors.Injectors;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.atomic.AtomicReference;

public class WebStepInterceptor extends StepInterceptor implements MethodInterceptor {
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
    WebStep stepAnnotation = invocation.getMethod().getAnnotation(WebStep.class);
    com.testvagrant.optimus.dashboard.models.Step step = buildStep(stepAnnotation);
    LayoutInitiator.getInstance().addStep(step, Injectors.WEB_PAGE_INJECTOR);
    Screenshot screenshot = invocation.getMethod().getAnnotation(Screenshot.class);
    recordAllureStep(screenshot, Injectors.WEB_PAGE_INJECTOR);
  }

  private com.testvagrant.optimus.dashboard.models.Step buildStep(WebStep stepAnnotation) {
    return com.testvagrant.optimus.dashboard.models.Step.builder()
        .name(stepAnnotation.description())
        .keyword(stepAnnotation.keyword() + " on " + stepAnnotation.platform() + " ")
        .error_message(getMessage())
        .status(getStatus())
        .build();
  }
}
