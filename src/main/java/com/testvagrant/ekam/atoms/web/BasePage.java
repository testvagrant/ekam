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

  public void log(String message) {
    ReportLogger.log(persona, message);
  }
}
