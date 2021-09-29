package com.testvagrant.ekam.reports.interceptors;

import com.testvagrant.ekam.reports.annotations.MobileStep;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.atomic.AtomicReference;

import static com.testvagrant.ekam.commons.LayoutInitiator.layoutInitiator;
import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class MobileStepInterceptor extends StepInterceptor implements MethodInterceptor {
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    try {
      AtomicReference<Object> proceed = invokeMethod(invocation);
      addStep(invocation);
      return proceed.get();
    } catch (Throwable e) {
      ekamLogger().error("Failed to invoke method {} in class {}", invocation.getMethod().getName(), invocation.getMethod().getDeclaringClass());
      addStep(invocation);
      throw e;
    }
  }

  private void addStep(MethodInvocation invocation) throws Throwable {
    MobileStep stepAnnotation = invocation.getMethod().getAnnotation(MobileStep.class);
    com.testvagrant.ekam.dashboard.models.Step step = buildStep(stepAnnotation);
    layoutInitiator().addStep(step);
    ekamLogger().info("Executing step {}", step.getName());
    recordAllureStep(
        stepAnnotation.keyword(),
        stepAnnotation.persona(),
        stepAnnotation.description(),
        layoutInitiator().captureScreenshot());
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
