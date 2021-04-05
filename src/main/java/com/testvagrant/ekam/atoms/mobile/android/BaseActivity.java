package com.testvagrant.ekam.atoms.mobile.android;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.testvagrant.ekam.reports.ReportLogger;
import org.openqa.selenium.By;

public class BaseActivity implements QueryFunctions {
  @Inject
  @Named("persona")
  private String persona;

  @Inject protected DeviceManager deviceManager;
  @Inject private Element element;

  protected Element Element(By locator) {
    element.locate(locator);
    return element;
  }

  public void log(String message) {
    ReportLogger.log(persona, message);
  }
}
