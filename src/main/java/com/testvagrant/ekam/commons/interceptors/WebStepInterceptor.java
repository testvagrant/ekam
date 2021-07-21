package com.testvagrant.ekam.commons.interceptors;

import com.testvagrant.ekam.commons.annotations.WebStep;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.atomic.AtomicReference;

import static com.testvagrant.ekam.commons.LayoutInitiator.layoutInitiator;

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
    com.testvagrant.ekam.dashboard.models.Step step = buildStep(stepAnnotation);
    layoutInitiator().addStep(step);
    recordAllureStep(
        stepAnnotation.keyword(),
        stepAnnotation.persona(),
        stepAnnotation.description(),
        layoutInitiator().captureScreenshot());
  }

  private com.testvagrant.ekam.dashboard.models.Step buildStep(WebStep stepAnnotation) {
    return com.testvagrant.ekam.dashboard.models.Step.builder()
        .name(stepAnnotation.description())
        .keyword(stepAnnotation.keyword())
        .error_message(getMessage())
        .status(getStatus())
        .build();
  }
}
