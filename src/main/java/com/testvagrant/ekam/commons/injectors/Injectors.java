package com.testvagrant.ekam.commons.injectors;

public enum Injectors {
  WEB_PAGE_INJECTOR("web_page_injector"),
  MOBILE_PAGE_INJECTOR("mobile_page_injector"),
  API_INJECTOR("api_injector"),
  LOG_FOLDER("logFolder");

  private final String injector;

  Injectors(String injector) {
    this.injector = injector;
  }

  public String getInjector() {
    return injector;
  }

  public String getInjector(String uniqueRef) {
    return injector + "_" + uniqueRef;
  }

  public String getInjector(int uniqueRef) {
    return getInjector(String.valueOf(uniqueRef));
  }
}
