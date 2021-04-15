package com.testvagrant.ekam.commons;

import com.google.inject.Injector;
import com.testvagrant.ekam.atoms.mobile.MobileScreen;
import org.testng.Reporter;

public class ActivityInitiator {

  public static ActivityInitiator Activity() {
    return new ActivityInitiator();
  }

  public <Activity extends MobileScreen> Activity getInstance(Class<Activity> tActivity) {
    Injector activityInjector =
        (Injector)
            Reporter.getCurrentTestResult()
                .getAttribute(Injectors.MOBILE_PAGE_INJECTOR.getInjector());
    return activityInjector.getInstance(tActivity);
  }
}
