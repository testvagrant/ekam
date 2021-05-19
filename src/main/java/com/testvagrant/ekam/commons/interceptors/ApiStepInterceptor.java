package com.testvagrant.ekam.commons.interceptors;

import com.testvagrant.ekam.commons.LayoutInitiator;
import com.testvagrant.ekam.commons.annotations.APIStep;
import com.testvagrant.ekam.commons.annotations.MobileStep;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.injectors.Injectors;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.concurrent.atomic.AtomicReference;

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
    com.testvagrant.optimus.dashboard.models.Step step = buildStep(stepAnnotation);
    LayoutInitiator.getInstance().addStep(step, Injectors.API_INJECTOR);
    recordAllureStep(stepAnnotation.keyword(), stepAnnotation.persona(), stepAnnotation.description());
  }

  private com.testvagrant.optimus.dashboard.models.Step buildStep(APIStep stepAnnotation) {
    return com.testvagrant.optimus.dashboard.models.Step.builder()
        .name(stepAnnotation.description())
        .keyword(stepAnnotation.keyword() + " on " + stepAnnotation.platform() + " ")
        .error_message(getMessage())
        .status(getStatus())
        .build();
  }
}
