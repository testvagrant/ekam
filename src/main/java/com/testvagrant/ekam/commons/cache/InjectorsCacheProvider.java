package com.testvagrant.ekam.commons.cache;

public class InjectorsCacheProvider {

  private static InjectorsCache injectorsCacheProvider;

  private InjectorsCacheProvider() {}

  public static InjectorsCache injectorsCache() {
    if (injectorsCacheProvider == null) {
      synchronized (InjectorsCacheProvider.class) {
        if (injectorsCacheProvider == null) {
          injectorsCacheProvider = new InjectorsCacheProvider().get();
        }
      }
    }
    return injectorsCacheProvider;
  }

  public InjectorsCache get() {
    return new InjectorsCache();
  }
}
