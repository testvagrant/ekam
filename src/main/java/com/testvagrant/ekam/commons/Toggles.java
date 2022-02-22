package com.testvagrant.ekam.commons;

public enum Toggles {
  TIMELINE(Boolean.parseBoolean(System.getProperty("timeline", "true"))),
  LOGS(Boolean.parseBoolean(System.getProperty("enableLogs", "true"))),
  BROWSER_STACK_CACHE_LOCK(Boolean.parseBoolean(System.getProperty("cloud.browserstack.cache.lock", "true")));

  private final Boolean active;

  Toggles(Boolean active) {
    this.active = active;
  }

  public boolean isOn() {
    return active;
  }

  public boolean isActive() {
    return active;
  }

  public boolean isOff() {
    return !active;
  }
}
