package com.testvagrant.ekam.commons;

public enum Toggles {
  TIMELINE(Boolean.parseBoolean(System.getProperty("timeline", "true")));

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
