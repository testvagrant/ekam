package com.testvagrant.ekam.web;

import com.google.inject.Inject;
import com.testvagrant.ekam.atoms.web.*;
import com.testvagrant.ekam.reports.allure.ReportLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebPage extends QueryFunctions {

  @Inject protected BrowserDriver browserDriver;
  @Inject protected WebDriver driver;

  protected Element element(By locator) {
    return new Element(driver, locator);
  }

  protected Textbox textbox(By locator) {
    return new Textbox(driver, locator);
  }

  protected Dropdown dropdown(By locator) {
    return new Dropdown(driver, locator);
  }

  protected ElementCollection elementCollection(By locator) {
    return new ElementCollection(driver, locator);
  }

  public void log(String message) {
    ReportLogger.log(message);
  }
}
