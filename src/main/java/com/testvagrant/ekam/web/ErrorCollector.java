package com.testvagrant.ekam.web;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.path.PathBuilder;
import com.testvagrant.ekam.config.models.LogConfig;
import com.testvagrant.ekam.internal.logger.EkamLogger;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;

import java.util.List;
import java.util.logging.Level;

import static com.testvagrant.ekam.internal.injectors.InjectorsCacheProvider.injectorsCache;

public class ErrorCollector {

  public void logConsoleMessages(String testDirectory, LogConfig logConfig) {
    Injector injector = injectorsCache().getInjector();
    WebDriver driver = injector.getInstance(WebDriver.class);
    List<LogEntry> logEntries = driver.manage().logs().get("browser").getAll();

    String logFilePath =
        new PathBuilder(testDirectory).append("logs").append("consoleLogs.log").toString();

    Logger logger = new EkamLogger(logFilePath, logConfig).init();

    logEntries.forEach(
        entry -> {
          Level logType = entry.getLevel();
          String log = entry.getMessage();
          logger.warn(String.format("%s: %s", logType, log));
        });
  }
}
