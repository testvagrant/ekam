package com.testvagrant.ekam.commons;

import com.google.inject.Injector;
import com.testvagrant.ekam.atoms.mobile.MobileScreen;
import com.testvagrant.optimus.core.screenshots.OptimusRunTarget;
import com.testvagrant.optimus.dashboard.StepRecorder;
import com.testvagrant.optimus.dashboard.models.Step;
import org.testng.Reporter;

import java.nio.file.Path;

public class ActivityInitiator {

  public static ActivityInitiator Activity() {
    return new ActivityInitiator();
  }

  public static <Activity extends MobileScreen> Activity Screen(Class<Activity> tActivity) {
    return Activity().getInstance(tActivity);
  }

  private <Activity extends MobileScreen> Activity getInstance(Class<Activity> tActivity) {
    Injector activityInjector =
        (Injector)
            Reporter.getCurrentTestResult()
                .getAttribute(Injectors.MOBILE_PAGE_INJECTOR.getInjector());
    return activityInjector.getInstance(tActivity);
  }

  public Path captureScreenshot() {
    Injector pageInjector =
        (Injector)
            Reporter.getCurrentTestResult()
                .getAttribute(Injectors.MOBILE_PAGE_INJECTOR.getInjector());
    OptimusRunTarget optimusRunTarget = pageInjector.getInstance(OptimusRunTarget.class);
    return optimusRunTarget.captureScreenshot();
  }

  public void addStep(Step step) {
    Injector pageInjector =
        (Injector)
            Reporter.getCurrentTestResult()
                .getAttribute(Injectors.MOBILE_PAGE_INJECTOR.getInjector());
    StepRecorder stepRecorder = pageInjector.getInstance(StepRecorder.class);
    stepRecorder.addStep(step);
  }
}
