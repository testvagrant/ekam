package com.testvagrant.ekam.commons;

import com.google.inject.Injector;
import com.testvagrant.ekam.atoms.mobile.MobileScreen;
import com.testvagrant.ekam.atoms.web.WebPage;
import com.testvagrant.optimus.core.screenshots.OptimusRunTarget;
import com.testvagrant.optimus.dashboard.StepRecorder;
import com.testvagrant.optimus.dashboard.models.Step;
import org.testng.Reporter;

import java.nio.file.Path;
import java.util.Objects;

public class LayoutInitiator {

  public static LayoutInitiator getInstance() {
    return new LayoutInitiator();
  }

  public static <Page extends WebPage> Page Page(Class<Page> tPage) {
    return getInstance().createWebLayout(tPage);
  }

  public static <Activity extends MobileScreen> Activity Screen(Class<Activity> tActivity) {
    return getInstance().createMobileLayout(tActivity);
  }

  public Path captureScreenshot() {
    OptimusRunTarget optimusRunTarget = getInjector().getInstance(OptimusRunTarget.class);
    return optimusRunTarget.captureScreenshot();
  }

  public void addStep(Step step) {
    StepRecorder stepRecorder = getInjector().getInstance(StepRecorder.class);
    stepRecorder.addStep(step);
  }

  private <Page extends WebPage> Page createWebLayout(Class<Page> tPage) {
    Injector pageInjector = getInjector(Injectors.WEB_PAGE_INJECTOR);
    return pageInjector.getInstance(tPage);
  }

  private <Activity extends MobileScreen> Activity createMobileLayout(Class<Activity> tActivity) {
    Injector activityInjector = getInjector(Injectors.MOBILE_PAGE_INJECTOR);
    return activityInjector.getInstance(tActivity);
  }

  private Injector getInjector() {
    Injector webInjector = (Injector)
            Reporter.getCurrentTestResult().getAttribute(Injectors.WEB_PAGE_INJECTOR.getInjector());
    Injector mobileInjector = (Injector)
            Reporter.getCurrentTestResult().getAttribute(Injectors.MOBILE_PAGE_INJECTOR.getInjector());
    return Objects.isNull(webInjector)? mobileInjector: webInjector;
  }

  private Injector getInjector(Injectors injector) {
    return (Injector)
            Reporter.getCurrentTestResult().getAttribute(injector.getInjector());
  }
}
