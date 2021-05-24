package com.testvagrant.ekam.commons.cache;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.testvagrant.ekam.commons.exceptions.NoSuchKeyException;
import com.testvagrant.optimus.cache.DataCache;

import java.util.Objects;
import java.util.function.Predicate;

public class DataSetsCache extends DataCache<String, Object> {

  private final LoadingCache<String, Object> masterTestContextCache;
  private final LoadingCache<String, Object> availableTestContextCache;
  private final LoadingCache<String, Object> engagedTestContextCache;

  public DataSetsCache() {
    masterTestContextCache = build(new TestContextCacheLoader());
    availableTestContextCache = build(new TestContextCacheLoader());
    engagedTestContextCache = build(new TestContextCacheLoader());
  }

  public synchronized void put(String key, Object value) {
    masterTestContextCache.put(key, value);
    availableTestContextCache.put(key, value);
  }

  @Override
  public synchronized boolean isPresent(Predicate<Object> predicate) {
    return anyMatch(masterTestContextCache, predicate);
  }

  public synchronized boolean isPresent(String key) {
    return anyMatch(masterTestContextCache, key);
  }

  public boolean isAvailable(String key) {
    return anyMatch(availableTestContextCache, key);
  }

  public boolean isEngaged(String key) {
    return anyMatch(engagedTestContextCache, key);
  }

  @Override
  public synchronized Object get(Predicate<Object> predicate) {
    throw new UnsupportedOperationException();
  }

  public synchronized Object get(String key, boolean lockRecord) throws NoSuchKeyException {
    if (!isPresent(key)) {
      throw new NoSuchKeyException(String.format("%s is not found, please add it first", key));
    }
    Object value = getObject(availableTestContextCache, key);
    if (Objects.isNull(value))
      throw new NoSuchKeyException(
          String.format("%s is a shared key and is engaged, please release to access it", key));
    if (lockRecord) lock(key);
    return value;
  }

  public synchronized Object get(String key) throws NoSuchKeyException {
    return get(key, false);
  }

  private Object getObject(LoadingCache<String, Object> testContextCache, String key) {
    return testContextCache.getIfPresent(key);
  }

  @Override
  protected synchronized void lock(String key) {
    Object value = getObject(availableTestContextCache, key);
    engagedTestContextCache.put(key, value);
    availableTestContextCache.invalidate(key);
  }

  @Override
  public synchronized void release(String key) {
    Object value = getObject(engagedTestContextCache, key);
    availableTestContextCache.put(key, value);
    engagedTestContextCache.invalidate(key);
  }

  @Override
  public long size() {
    return masterTestContextCache.size();
  }

  protected boolean anyMatch(LoadingCache<String, Object> cache, String key) {
    return cache.asMap().containsKey(key);
  }

  private class TestContextCacheLoader extends CacheLoader<String, Object> {
    @Override
    public Object load(String key) {
      return availableTestContextCache.getIfPresent(key);
    }
  }
}
