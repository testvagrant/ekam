package com.testvagrant.ekam.commons.injectors;

public enum Injectors {
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
