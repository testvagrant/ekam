package com.testvagrant.ekam.commons.interceptors;

import com.testvagrant.ekam.commons.LayoutInitiator;
import com.testvagrant.ekam.commons.Toggles;
import com.testvagrant.ekam.commons.annotations.Screenshot;
import com.testvagrant.ekam.commons.annotations.Step;
import com.testvagrant.ekam.commons.injectors.Injectors;
import com.testvagrant.ekam.reports.AllureAttachment;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class StepInterceptor extends InvocationInterceptor implements MethodInterceptor {

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
    com.testvagrant.optimus.dashboard.models.Step step =
        com.testvagrant.optimus.dashboard.models.Step.builder()
            .name(stepAnnotation.description())
            .keyword(stepAnnotation.keyword())
            .error_message(getMessage())
            .status(getStatus())
            .build();
    LayoutInitiator.getInstance().addStep(step);
    Screenshot screenshot = invocation.getMethod().getAnnotation(Screenshot.class);
    recordAllureStep(screenshot);
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
  protected void recordAllureStep(Screenshot screenshot) throws Throwable {
    recordAllureStep(screenshot, Injectors.WEB_PAGE_INJECTOR);
  }

  @io.qameta.allure.Step("{keyword} {persona} {description}")
  protected void recordAllureStep(Screenshot screenshot, Injectors injectors) throws Throwable {
    if (Toggles.TIMELINE.isOn() || Objects.nonNull(throwable)) {
      Path path = getScreenshotPath(injectors);
      String screenShotName =
          Objects.isNull(screenshot) ? LocalDateTime.now().toString() : screenshot.name();
      new AllureAttachment().attachScreenshot(screenShotName, path);
    }
    if (Objects.nonNull(throwable)) {
      throw throwable;
    }
  }

  @io.qameta.allure.Step("{keyword} {persona} {description}")
  protected void recordAllureStep(String keyword, String persona, String description) throws Throwable {
    if (Objects.nonNull(throwable)) {
      throw throwable;
    }
  }

  private Path getScreenshotPath(Injectors injectors) {
    switch (injectors) {
      case WEB_PAGE_INJECTOR:
        return LayoutInitiator.getInstance().captureWebScreenshot(injectors);
      case MOBILE_PAGE_INJECTOR:
        return LayoutInitiator.getInstance().captureMobileScreenshot(injectors);
      default:
        return LayoutInitiator.getInstance().captureScreenshot(injectors);
    }
  }
}
