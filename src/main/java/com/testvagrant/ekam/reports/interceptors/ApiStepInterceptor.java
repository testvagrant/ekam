package com.testvagrant.ekam.reports.interceptors;

import com.testvagrant.ekam.commons.LayoutInitiator;
import com.testvagrant.ekam.reports.annotations.APIStep;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.testvagrant.ekam.logger.EkamLogger.ekamLogger;

public class ApiStepInterceptor extends StepInterceptor implements MethodInterceptor {
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
    APIStep stepAnnotation = invocation.getMethod().getAnnotation(APIStep.class);
    com.testvagrant.ekam.dashboard.models.Step step = buildStep(stepAnnotation);
    LayoutInitiator.layoutInitiator().addStep(step);
    ekamLogger().info("Executing step {}", step.getName());
    recordAllureStep(
        stepAnnotation.keyword(), stepAnnotation.persona(), stepAnnotation.description());
  }

  private com.testvagrant.ekam.dashboard.models.Step buildStep(APIStep stepAnnotation) {
    return com.testvagrant.ekam.dashboard.models.Step.builder()
        .name(stepAnnotation.description())
        .keyword(stepAnnotation.keyword())
        .error_message(getMessage())
        .status(getStatus())
        .build();
  }

  @io.qameta.allure.Step("{keyword} {persona} {description}")
  protected void recordAllureStep(String keyword, String persona, String description)
      throws Throwable {
    if (Objects.nonNull(throwable)) {
      throw throwable;
    }
  }
}
