package com.testvagrant.ekam.commons.cache;

public class InjectorsCacheProvider {
  private static InjectorsCache injectorsCacheProvider;

  private InjectorsCacheProvider() {}

  public static InjectorsCache getInstance() {
    if (injectorsCacheProvider == null) {
      synchronized (InjectorsCacheProvider.class) {
        if (injectorsCacheProvider == null) {
          injectorsCacheProvider = new InjectorsCacheProvider().get();
        }
      }
    }
    return injectorsCacheProvider;
  }

  public static <T> T getInstance(String injector, Class<T> tClass) {
    if (injectorsCacheProvider == null) {
      synchronized (InjectorsCacheProvider.class) {
        if (injectorsCacheProvider == null) {
          injectorsCacheProvider = new InjectorsCacheProvider().get();
        }
      }
    }
    return injectorsCacheProvider.get(injector).getInstance(tClass);
  }

  public InjectorsCache get() {
    return new InjectorsCache();
  }
}
