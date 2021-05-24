package com.testvagrant.ekam.atoms.web;

import com.google.inject.Inject;
import com.testvagrant.ekam.reports.ReportLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebPage implements QueryFunctions {

  @Inject protected BrowserDriver browserDriver;

  @Inject protected WebDriver driver;

  @Inject private Element element;

  protected Element element(By locator) {
    return new Element(driver).locate(locator);
  }

  protected ElementCollection elementCollection(By locator) {
    return new ElementCollection(driver).locate(locator);
  }

  public void log(String message) {
    ReportLogger.log(message);
  }
}
