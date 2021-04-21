package com.testvagrant.ekam.atoms.web;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.testvagrant.ekam.reports.ReportLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebPage implements QueryFunctions {

  @Inject
  @Named("persona")
  private String persona;

  @Inject protected BrowserDriver browserDriver;

  @Inject protected WebDriver driver;

  @Inject private Textbox textBox;
  @Inject private Element element;

  protected Element element(By locator) {
    return new Element(driver).locate(locator);
  }

  protected Textbox textbox(By locator) {
    return (Textbox) new Textbox(driver).locate(locator);
  }

  public void log(String message) {
    ReportLogger.log(persona, message);
  }
}
