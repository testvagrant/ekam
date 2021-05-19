package com.testvagrant.ekam.commons.listeners;

import com.testvagrant.ekam.commons.cache.TestContextCacheProvider;
import com.testvagrant.ekam.commons.exceptions.NoSuchKeyException;
import com.testvagrant.ekam.commons.injectors.Injectors;
import com.testvagrant.ekam.commons.logs.LogWriter;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import java.util.List;

public class ReportListener implements IReporter {

  @Override
  @SuppressWarnings("unchecked")
  public void generateReport(
      List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
    suites.forEach(
        suite -> {
          suite
              .getResults()
              .forEach(
                  (key, value) ->
                      value
                          .getTestContext()
                          .getPassedTests()
                          .getAllResults()
                          .forEach(
                              iTestResult -> {
                                List<String> logs = Reporter.getOutput(iTestResult);
                                String logFolder = null;
                                try {
                                  logFolder =
                                      (String)
                                          TestContextCacheProvider.getInstance()
                                              .get(Injectors.LOG_FOLDER.getInjector());
                                } catch (NoSuchKeyException e) {
                                  logFolder = new LogWriter().createLogFolder();
                                }
                                LogWriter.writeLog(logFolder, iTestResult.getName(), logs);
                              }));
        });
  }
}
