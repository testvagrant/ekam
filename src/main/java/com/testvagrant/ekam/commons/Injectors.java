package com.testvagrant.ekam.commons;

public enum Injectors {
  MOBILE_DRIVER("optimusCloudMobileDriver"),
  DRIVER_INJECTOR("driver_injector"),
  WEB_PAGE_INJECTOR("web_page_injector"),
  MOBILE_PAGE_INJECTOR("mobile_page_injector"),
  DRIVER("driver"),
  LOG_FOLDER("logFolder");

  private final String injector;

  Injectors(String injector) {
    this.injector = injector;
  }

  public String getInjector() {
    return injector;
  }
}
