package com.testvagrant.ekam.commons.interceptors;

import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.reports.AllureAttachment;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;

public class StepInterceptor extends InvocationInterceptor implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    return invocation.proceed();
  }

  protected String getMessage() {
    if (Objects.isNull(throwable)) {
      return "";
    }
    return ExceptionUtils.getStackTrace(throwable);
  }

  protected String getStatus() {
    if (Objects.isNull(throwable)) {
      return "passed";
    }
    return "failed";
  }

  @io.qameta.allure.Step("{keyword} {persona} {description}")
  protected void recordAllureStep(String keyword, String persona, String description, Path path)
      throws Throwable {
    if (Toggles.TIMELINE.isOn() || Objects.nonNull(throwable)) {
      String screenShotName = LocalDateTime.now().toString();
      new AllureAttachment().attachScreenshot(screenShotName, path);
    }
    if (Objects.nonNull(throwable)) {
      throw throwable;
    }
  }
}
