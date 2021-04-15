package com.testvagrant.ekam.commons;

import com.google.inject.Injector;
import com.testvagrant.ekam.atoms.web.WebPage;
import org.testng.Reporter;

public class PageInitiator {

  public static PageInitiator Page() {
    return new PageInitiator();
  }

  public <Page extends WebPage> Page getInstance(Class<Page> tPage) {
    Injector pageInjector =
        (Injector)
            Reporter.getCurrentTestResult().getAttribute(Injectors.WEB_PAGE_INJECTOR.getInjector());
    return pageInjector.getInstance(tPage);
  }
}
