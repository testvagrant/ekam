package com.testvagrant.ekam.atoms.web;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.testvagrant.ekam.atoms.QueryFunctions;
import com.testvagrant.ekam.reports.ReportLogger;
import org.openqa.selenium.By;

public class BasePage implements QueryFunctions {

  @Inject
  @Named("persona")
  private String persona;

  @Inject protected BrowserManager browserManager;

  @Inject private Textbox textBox;
  @Inject private Element element;
  @Inject private ElementCollection elementCollection;

  protected Textbox TextBox(By locator) {
    textBox.locate(locator);
    return textBox;
  }

  protected Element Element(By locator) {
    element.locate(locator);
    return element;
  }

  protected ElementCollection ElementCollection(By locator) {
    elementCollection.locate(locator);
    return elementCollection;
  }

  public void log(String message) {
    ReportLogger.log(persona, message);
  }
}
