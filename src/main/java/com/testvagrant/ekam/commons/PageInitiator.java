package com.testvagrant.ekam.commons;

import com.google.inject.Injector;
import com.testvagrant.ekam.atoms.web.WebPage;
import com.testvagrant.optimus.core.screenshots.OptimusRunTarget;
import com.testvagrant.optimus.dashboard.StepRecorder;
import com.testvagrant.optimus.dashboard.models.Step;
import org.testng.Reporter;

import java.nio.file.Path;

public class PageInitiator {

  public static PageInitiator getInstance() {
    return new PageInitiator();
  }

  public static <Page extends WebPage> Page WebPage(Class<Page> tPage) {
    return new PageInitiator().createInstance(tPage);
  }

  private <Page extends WebPage> Page createInstance(Class<Page> tPage) {
    Injector pageInjector =
        (Injector)
            Reporter.getCurrentTestResult().getAttribute(Injectors.WEB_PAGE_INJECTOR.getInjector());
    return pageInjector.getInstance(tPage);
  }

  public Path captureScreenshot() {
    Injector pageInjector =
        (Injector)
            Reporter.getCurrentTestResult().getAttribute(Injectors.WEB_PAGE_INJECTOR.getInjector());
    OptimusRunTarget optimusRunTarget = pageInjector.getInstance(OptimusRunTarget.class);
    return optimusRunTarget.captureScreenshot();
  }

  public void addStep(Step step) {
    Injector pageInjector =
        (Injector)
            Reporter.getCurrentTestResult().getAttribute(Injectors.WEB_PAGE_INJECTOR.getInjector());
    StepRecorder stepRecorder = pageInjector.getInstance(StepRecorder.class);
    stepRecorder.addStep(step);
  }
}
