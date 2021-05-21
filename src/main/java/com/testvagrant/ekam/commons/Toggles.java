package com.testvagrant.ekam.commons;

public enum Toggles {
  MOCK(SystemProperties.MOCK),
  LOGS(SystemProperties.LOGS),
  SWITCH_VIEW(SystemProperties.SWITCH_VIEW),
  TIMELINE(SystemProperties.TIMELINE),
  SLACK_NOTIF(SystemProperties.SLACK_NOTIF),
  SLACK_NOTIFY_ME_EVERYTIME(SystemProperties.SLACK_NOTIFY_ME_EVERYTIME),
  PUBLISH_TO_DASHBOARD(SystemProperties.PUBLISH_TO_DASHBOARD);

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
