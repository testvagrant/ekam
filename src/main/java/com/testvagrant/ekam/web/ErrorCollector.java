package com.testvagrant.ekam.web;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.io.ResourcePaths;
import com.testvagrant.ekam.commons.path.PathBuilder;
import com.testvagrant.ekam.internal.executiontimeline.models.EkamTest;
import com.testvagrant.ekam.internal.logger.EkamLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;

public class ErrorCollector {

  private final Logger logger = LogManager.getLogger(ErrorCollector.class);

  public LogEntries collect() {
    Injector injector = injectorsCache().getInjector();
    WebDriver driver = injector.getInstance(WebDriver.class);
    return driver.manage().logs().get("browser");
  }

  public void logConsoleMessages() {
    Injector injector = injectorsCache().getInjector();
    EkamTest ekamTest = injector.getInstance(EkamTest.class);
    String logFilePath =
        new PathBuilder(ResourcePaths.getTestPath(ekamTest.getFeature(), ekamTest.getScenario()))
            .append("logs")
            .append("consoleLogs.log")
            .toString();

    new EkamLogger(logFilePath)
        .init(org.apache.logging.log4j.Level.DEBUG.toString(), Collections.singletonList("file"));

    List<LogEntry> logEntries = collect().getAll();
    logEntries.forEach(
        entry -> {
          Level logType = entry.getLevel();
          String log = entry.getMessage();
          logger.warn(String.format("%s: %s\n", logType, log));
        });
  }
}
