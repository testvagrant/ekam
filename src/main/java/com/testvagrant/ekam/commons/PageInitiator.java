package com.testvagrant.ekam.commons;

import com.google.inject.Injector;
import org.testng.Reporter;

public class PageInitiator {

  // Sugar coated methods for readability
  public static <T> T getWebPage(Class<T> tPage) {
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
}
