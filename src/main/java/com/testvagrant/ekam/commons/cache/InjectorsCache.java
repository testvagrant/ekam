package com.testvagrant.ekam.commons.cache;

import com.google.inject.Injector;

public class InjectorsCache extends DataCache<Injector> {

  public void put(Injector injector) {
    put(String.valueOf(Thread.currentThread().getId()), injector);
  }

  public Injector get() {
    return get(String.valueOf(Thread.currentThread().getId()))
        .orElseThrow(() -> new RuntimeException("Cannot find injector"));
  }
}
