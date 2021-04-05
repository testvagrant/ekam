package com.testvagrant.ekam.commons.logs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.logging.LogEntries;

import java.util.TimerTask;
import java.util.stream.StreamSupport;

public class AppiumLogRecorder extends TimerTask {

  private final AppiumDriver<MobileElement> driver;

  public AppiumLogRecorder(AppiumDriver<MobileElement> driver) {
    this.driver = driver;
  }

  @Override
  public void run() {
    LogEntries logs = driver.manage().logs().get("server");
    StreamSupport.stream(logs.spliterator(), false).forEach(System.out::println);
  }
}
