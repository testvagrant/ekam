package com.testvagrant.ekam.reports;

import org.testng.Reporter;

public class ReportLogger {

  public static void log(String message) {
    Reporter.log(String.format("%s %s", "user", message), true);
  }
}
