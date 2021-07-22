package com.testvagrant.ekam.internal.injectors;

import com.google.inject.Injector;
import com.testvagrant.ekam.commons.cache.DataCache;

public class InjectorsCache extends DataCache<Injector> {

  public void put(Injector injector) {
    put(String.valueOf(Thread.currentThread().getId()), injector);
  }

  public Injector getInjector() {
    return get(String.valueOf(Thread.currentThread().getId()))
        .orElseThrow(() -> new RuntimeException("Cannot get injector"));
  }
}
