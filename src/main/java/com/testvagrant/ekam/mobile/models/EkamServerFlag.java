package com.testvagrant.ekam.mobile.models;

import io.appium.java_client.service.local.flags.ServerArgument;

public enum EkamServerFlag implements ServerArgument {
  WDA_PORT("--webdriveragent-port"),
  /** This will print the appium server logs to stdout */
  ENABLE_CONSOLE_LOGS("--enable-console-logs");

  private final String arg;

  EkamServerFlag(String arg) {
    this.arg = arg;
  }

  @Override
  public String getArgument() {
    return arg;
  }
}
