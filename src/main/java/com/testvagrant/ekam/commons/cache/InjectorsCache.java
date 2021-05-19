package com.testvagrant.ekam.commons.cache;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Injector;
import com.testvagrant.optimus.cache.DataCache;

import java.util.function.Predicate;

public class InjectorsCache extends DataCache<String, Injector> {
  LoadingCache<String, Injector> injectorsCache;

  public InjectorsCache() {
    injectorsCache = build(new InjectorCacheLoader());
  }

  @Override
  public void put(String s, Injector injector) {
    injectorsCache.put(s, injector);
  }

  @Override
  public boolean isPresent(Predicate<Injector> predicate) {
    return false;
  }

  @Override
  public Injector get(Predicate<Injector> predicate) {
    throw new UnsupportedOperationException();
  }

  public Injector get(String injectorName) {
    return injectorsCache.getIfPresent(injectorName);
  }

  @Override
  protected void lock(String value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void release(String value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long size() {
    return injectorsCache.size();
  }

  private class InjectorCacheLoader extends CacheLoader<String, Injector> {
    @Override
    public Injector load(String injectorName) {
      return injectorsCache.getIfPresent(injectorName);
    }
  }
}
