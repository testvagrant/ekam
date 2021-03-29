package com.testvagrant.ekam.commons;

import com.google.inject.Injector;
import com.testvagrant.ekam.atoms.mobile.android.BaseActivity;
import com.testvagrant.ekam.atoms.web.BasePage;
import org.testng.Reporter;

public class PageInitiator {

  // Sugar coated methods for readability
  public static <Page extends BasePage> Page getWebPage(Class<Page> tPage) {
    return getPageInstance(tPage, Injectors.WEB_PAGE_INJECTOR);
  }

  public static <T> T getPageInstance(Class<T> tClass, Injectors injectorType) {
    Injector pageInjector =
        (Injector) Reporter.getCurrentTestResult().getAttribute(injectorType.getInjector());
    return pageInjector.getInstance(tClass);
  }

  public static <T> T getActivity(Class<T> tActivity) {
    return getPageInstance(tActivity, Injectors.MOBILE_PAGE_INJECTOR);
  }

  public static <Activity extends BaseActivity> Activity getActivityInstance(
      Class<Activity> tClass, Injectors injectorType) {
    Injector activityInjector =
        (Injector) Reporter.getCurrentTestResult().getAttribute(injectorType.getInjector());
    return activityInjector.getInstance(tClass);
  }
}
