package com.testvagrant.ekam.commons.interceptors;

import com.testvagrant.ekam.commons.LayoutInitiator;
import com.testvagrant.ekam.commons.annotations.MobileStep;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.atomic.AtomicReference;

public class MobileStepInterceptor extends StepInterceptor implements MethodInterceptor {
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
    MobileStep stepAnnotation = invocation.getMethod().getAnnotation(MobileStep.class);
    com.testvagrant.ekam.dashboard.models.Step step = buildStep(stepAnnotation);
    LayoutInitiator.getInstance().addStep(step);
    recordAllureStep(
        stepAnnotation.keyword(),
        stepAnnotation.persona(),
        stepAnnotation.description(),
        LayoutInitiator.getInstance().captureMobileScreenshot());
  }

  private com.testvagrant.ekam.dashboard.models.Step buildStep(MobileStep stepAnnotation) {
    return com.testvagrant.ekam.dashboard.models.Step.builder()
        .name(stepAnnotation.description())
        .keyword(stepAnnotation.keyword())
        .error_message(getMessage())
        .status(getStatus())
        .build();
  }
}
