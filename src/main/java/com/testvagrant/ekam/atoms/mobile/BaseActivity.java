package com.testvagrant.ekam.atoms.mobile;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.testvagrant.ekam.atoms.QueryFunctions;
import com.testvagrant.ekam.reports.ReportLogger;

public class BaseActivity implements QueryFunctions {
  @Inject
  @Named("persona")
  private String persona;

  public String ignoreSpaceAndCase(String value) {
    return String.format(
        "normalize-space(translate(@text, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')) = '%1$s'",
        value.toLowerCase());
  }

  public void log(String message) {
    ReportLogger.log(persona, message);
  }

  public String ignoreSpaceAndCase(String value, Boolean partial) {
    if (!partial) {
      return ignoreSpaceAndCase(value);
    }

    return String.format(
        "contains(normalize-space(translate(@text, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')) , '%1$s')",
        value.toLowerCase());
  }
}
