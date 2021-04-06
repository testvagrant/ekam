package com.testvagrant.ekam.web.listeners;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.Injectors;
import com.testvagrant.ekam.commons.clients.SiteClient;
import com.testvagrant.ekam.commons.logs.LogWriter;
import com.testvagrant.ekam.commons.modules.PropertyModule;
import com.testvagrant.ekam.web.modules.SiteModule;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import static com.testvagrant.ekam.reports.ReportLogger.log;

public class WebSiteListener implements ISuiteListener {

  @Override
  public void onStart(ISuite suite) {
    Injector siteInjector =
        suite.getParentInjector().createChildInjector(new SiteModule(), new PropertyModule());
    log("Verifying if site is up");
    siteInjector.getInstance(SiteClient.class).terminateIfSiteIsDown();
    String logFolder = siteInjector.getInstance(LogWriter.class).createLogFolder();
    suite.setAttribute(Injectors.LOG_FOLDER.getInjector(), logFolder);
  }

  @Override
  public void onFinish(ISuite suite) {}
}
