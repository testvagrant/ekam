package com.testvagrant.ekam.commons.listeners;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.Injectors;
import com.testvagrant.ekam.commons.logs.LogWriter;
import com.testvagrant.ekam.web.modules.SiteModule;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import static com.testvagrant.ekam.reports.ReportLogger.log;

public class LogListener implements ISuiteListener {

  @Override
  public void onStart(ISuite suite) {
    //TODO: Why sitemodule is required
    Injector logInjector =
        suite.getParentInjector().createChildInjector(new SiteModule());
    String logFolder = logInjector.getInstance(LogWriter.class).createLogFolder();
    suite.setAttribute(Injectors.LOG_FOLDER.getInjector(), logFolder);
  }

  @Override
  public void onFinish(ISuite suite) {}
}
